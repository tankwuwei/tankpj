package com.ft.dbserver;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;

import engine.db.DBObject;
import engine.db.client.DBType;
import engine.log.LogUtil;
import engine.util.Utility;
import redis.clients.jedis.Jedis;

/**
 * 一个数据库实例。 一个DB对应数据库中的一个database，一个redis中的db
 * 
 * @author cxz
 *
 */
public class DBNew {
	int dbtype;
	String DBName;
	Jedis jedis;
	DBSaver saver;
	private SessionFactory factory;
	private HashMap<String, DataTable<? extends DBObject>> all;

	public DBNew(int dbType) {
		this.dbtype = dbType;
		DBName = DBType.GetDBName(dbType);

		initRedis();
		initMysql();
		initAllTables();
	}

	private void initRedis() {
		jedis = RedisUtil.getJedis();
		jedis.select(dbtype);
	}

	private void initMysql() {

		String[] source = { "config/" + DBName + ".cfg.xml", "config/" + DBName + ".mapping.xml" };
		StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder();
		Configuration configuration = new Configuration();
		for (int i = 0; i < source.length; i++) {
			try {
				configuration.configure(source[i]);
			} catch (HibernateException e) {
				LogUtil.info(e.getMessage());
				LogUtil.info("not config:" + source[i]);
			}
		}
		// �滻����
		String user = DBServer.configs.GetStrConfig(DBName + ".user", "root");
		String password = DBServer.configs.GetStrConfig(DBName + ".password", "123321");
		String ip = DBServer.configs.GetStrConfig(DBName + ".ip", "127.0.0.1");
		String port = DBServer.configs.GetStrConfig(DBName + ".port", "3306");
		String dbname = DBServer.configs.GetStrConfig(DBName + ".dbname", DBName);
		String url = "jdbc:mysql://" + ip + ":" + port + "/" + dbname + "?Unicode=true&amp;characterEncoding=utf-8";
		configuration.setProperty("hibernate.hikari.dataSource.user", user);
		configuration.setProperty("hibernate.hikari.dataSource.password", password);
		configuration.setProperty("hibernate.hikari.dataSource.url", url);
		// �滻����
		builder.applySettings(configuration.getProperties());
		while (true) {
			try {
				factory = configuration.buildSessionFactory(builder.build());
				break;
			} catch (Exception e) {
				LogUtil.error("wait for mysql ....", e);
			}
			Utility.sleep(2);
		}
	}

	private void initAllTables() {
		all = new HashMap<String, DataTable<? extends DBObject>>();
		List<String> list = Utility.readTextFile("config/" + DBName.toLowerCase() + ".mapping.xml");
		try {
			for (String string : list) {
				if (!StringUtils.startsWith(StringUtils.trim(string), "<mapping"))
					continue;
				int startIndex = StringUtils.indexOf(string, "class") + 7;
				int endIndex = StringUtils.indexOf(string, "\"", startIndex);
				String className = StringUtils.substring(string, startIndex, endIndex);
				Class<? extends DBObject> c = (Class<? extends DBObject>) Class.forName(className);
				initTable(c);
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	private <T extends DBObject> void initTable(Class<T> c) {
		DataTable<T> table = new DataTable<T>(c, this);
		all.put(c.getName(), table);
	}

	public DataTable<? extends DBObject> getTable(String c) {
		return all.get(c);
	}

	////////////////////////////////////// Redis 方法
	////////////////////////////////////// //////////////////////////////////////////

	// 对象序列化为字符串
	public String objectSerialiable(Object obj) {
		String serStr = null;
		try {
			ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
			ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
			objectOutputStream.writeObject(obj);
			serStr = byteArrayOutputStream.toString("ISO-8859-1");
			serStr = java.net.URLEncoder.encode(serStr, "UTF-8");

			objectOutputStream.close();
			byteArrayOutputStream.close();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return serStr;
	}

	// 字符串反序列化为对象
	public Object objectDeserialization(String serStr) {
		Object newObj = null;
		try {
			String redStr = java.net.URLDecoder.decode(serStr, "UTF-8");
			ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(redStr.getBytes("ISO-8859-1"));
			ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream);
			newObj = objectInputStream.readObject();
			objectInputStream.close();
			byteArrayInputStream.close();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return newObj;
	}

	public String set(String key, String content) {
		if (jedis == null) {
			return null;
		}
		return jedis.set(key, content);
	}

	/**
	 * 根据key模糊查找，返回第一条的内容。 用于唯一key查找
	 * 
	 * @param key
	 * @return
	 */
	public String getOne(String key) {
		if (jedis == null) {
			return null;
		}
		Set<String> keys = jedis.keys(key);
		for (String str : keys) {
			return get(str);
		}
		return null;
	}

	/**
	 * 依据key进行模糊查找
	 * 
	 * @param key
	 * @return
	 */
	public List<String> getAll(String key) {
		if (jedis == null) {
			return null;
		}
		List<String> ret = new ArrayList<>();
		Set<String> keys = jedis.keys(key);
		for (String str : keys) {
			ret.add(get(str));
		}
		return ret;
	}

	// 根据完整的key获取内容
	private String get(String key) {
		if (jedis == null) {
			return null;
		}
		return jedis.get(key);
	}

	public void deleteAll(String key) {
		if (jedis == null) {
			return;
		}
		Set<String> keys = jedis.keys(key);
		for (String str : keys) {
			jedis.del(str);
		}

	}

	///////////////////////////////////// MySQL方法
	///////////////////////////////////// /////////////////////////////////////////
	public long getMaxId(Class<?> c) {
		String hql = "select max(id) from " + c.getSimpleName();
		Session session = open(factory);
		try {
			Object o = session.createQuery(hql).uniqueResult();
			return o != null ? (Long) o : 0;
		} finally {
			close(session);
		}
	}

	public void clear(String table) {
		Session session = open(factory);
		try {
			StringBuilder builder = new StringBuilder("truncate ").append(table);
			Query q = session.createSQLQuery(builder.toString());
			q.executeUpdate();
		} finally {
			close(session);
		}
	}
	
	
	public void deleteByIds(Class<?> c, long[] ids) {
		Session session = open(factory);
		StringBuilder hql = new StringBuilder("delete ").append(c.getName()).append(" where id in (");
		for (int i = 0; i < ids.length; i++) {
			if (i > 0) {
				hql.append(",");
			}
			hql.append("?");
		}
		hql.append(")");
		try {
			Query query = session.createQuery(hql.toString());
			for (int i = 0; i < ids.length; i++) {
				query.setParameter(i, ids[i]);
			}
			query.executeUpdate();
		} finally {
			close(session);
		}
	}

	private Session open(SessionFactory factory) {
		Session session = factory.openSession();
		session.beginTransaction();
		return session;
	}

	private void close(Session session) {
		try {
			session.getTransaction().commit();
		} catch (Exception e) {
			session.getTransaction().rollback();
			LogUtil.error(e);
			throw new RuntimeException();
		} finally {
			session.close();
		}
	}
}
