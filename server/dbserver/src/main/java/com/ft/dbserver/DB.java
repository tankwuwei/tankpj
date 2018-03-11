package com.ft.dbserver;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;

import engine.core.IDBObject;
import engine.db.client.DBType;
import engine.log.LogUtil;
import engine.util.Utility;

@SuppressWarnings({ "unchecked", "rawtypes" })
public class DB {
	private String DBName;
	DBSaver saver;
	private HashMap<String, Storage<? extends IDBObject>> storages;


	public DB(int dbtype) {

		this.DBName = DBType.GetDBName(dbtype);
		saver = new DBSaver();
		saver.init(this);
		init();
		InitStorage();
		
	}

	public String GetName() {
		return DBName;
	}

	
	private void InitStorage() {
		storages = new HashMap<String, Storage<? extends IDBObject>>();
		List<String> list = Utility.readTextFile("config/" + DBName.toLowerCase() + ".mapping.xml");
		try {
			for (String string : list) {
				if (!StringUtils.startsWith(StringUtils.trim(string), "<mapping"))
					continue;
				int startIndex = StringUtils.indexOf(string, "class") + 7;
				int endIndex = StringUtils.indexOf(string, "\"", startIndex);
				String className = StringUtils.substring(string, startIndex, endIndex);
				Class<? extends IDBObject> c = (Class<? extends IDBObject>) Class.forName(className);
				initStoarge(c);
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

	}

	private <T extends IDBObject> void initStoarge(Class<T> c) {
		Storage<T> storage = new Storage<T>(c, this);
		storages.put(c.getName(), storage);
	}

	public Storage<? extends IDBObject> getStorage(String c) {
		return storages.get(c);
	}

	private SessionFactory factory;

	private void init() {

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
		String url = "jdbc:mysql://" + ip + ":" + port + "/" + dbname + "?characterEncoding=utf-8";
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

	public <T> T get(Class<T> c, Serializable key) {
		Session session = open(factory);
		try {
			return (T) session.get(c, key);
		} finally {
			close(session);
		}
	}

	public <T> List<T> getList(Class<T> c, Order order) {
		Session session = open(factory);
		try {
			return session.createCriteria(c).addOrder(order).list();
		} finally {
			close(session);
		}
	}

	public <T> List<T> getList(Class<T> c, Criterion criterion) {
		Session session = open(factory);
		try {
			return session.createCriteria(c).add(criterion).list();
		} finally {
			close(session);
		}
	}

	public <T> List<T> getList(Class<T> c, List<Criterion> criterions, List<Order> orders, int limit) {
		Session session = open(factory);
		try {
			Criteria criteria = session.createCriteria(c);
			if (criterions != null) {
				for (Criterion criterion : criterions) {
					criteria.add(criterion);
				}
			}
			if (orders != null) {
				for (Order order : orders) {
					criteria.addOrder(order);
				}
			}
			if (limit > 0) {
				criteria.setMaxResults(limit);
			}
			return criteria.list();
		} finally {
			close(session);
		}
	}

	
	public <T> List<T> getListbyhql(String hql) {
		Session session = open(factory);
		try {
			Query query;
			String limit = "LIMIT.^*^.";
			if (hql.indexOf(limit) == -1) {
				query = session.createQuery(hql);
			} else {
				query = session.createQuery(hql.substring(0, hql.indexOf(limit)));
				String[] arr = hql.substring(hql.indexOf(limit) + 10).split("#");
				query.setFirstResult(Integer.parseInt(arr[0]));
				query.setMaxResults(Integer.parseInt(arr[1]));
			}
			return query.list();
		} finally {
			close(session);
		}
	}
	
	
	public <T> T getUnique(Class<T> c, String field, Object value) {
		Session session = open(factory);
		try {
			String sql = new StringBuilder().append(" from ").append(c.getSimpleName()).append(" where ").append(field)
					.append("=:").append(field).toString();
			Query query = session.createQuery(sql);
			query.setParameter(field, value);
			return (T) query.uniqueResult();
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			close(session);
		}
		return null;
	}

	public <T> T getUnique(Class<T> c, String[] fields, Object[] values) {
		Session session = open(factory);
		try {
			StringBuilder sb = new StringBuilder().append(" from ").append(c.getSimpleName()).append(" where ");
			for (int i = 0; i < fields.length; i++) {
				sb.append(fields[i]).append("=:").append(fields[i]);
				if (i != fields.length - 1)
					sb.append(" and ");
			}
			Query q = session.createQuery(sb.toString());
			for (int i = 0; i < fields.length; i++)
				q.setParameter(fields[i], values[i]);
			return (T) q.uniqueResult();
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			close(session);
		}
		return null;
	}

	public List getList(String sql) {
		Session session = open(factory);
		try {
			return session.createSQLQuery(sql).list();
		} finally {
			close(session);
		}
	}

	public <T> List<T> getList(Class<T> c) {
		Session session = open(factory);
		try {
			return (List<T>) session.createQuery("from " + c.getSimpleName()).list();
		} finally {
			close(session);
		}
	}

	public <T> List<T> getList(Class<T> c, int first, int maxLength) {
		long tick = System.nanoTime();
		Session session = open(factory);
		try {
			Query q = session.createQuery("from " + c.getSimpleName());
			q.setFirstResult(first);
			q.setMaxResults(maxLength);
			return q.list();
		} finally {
			close(session);
			System.out.println("---------getList:" + first + "-" + maxLength + " cost time:" + (System.nanoTime() - tick));
		}
	}

	public List<Object> getFields(Class<?> c, long[] ids, String[] fields) {
		Session session = open(factory);
		try {
			StringBuilder builder = new StringBuilder();
			builder.append("select ");
			for (int i = 0; i < fields.length; i++) {
				builder.append(fields[i]);
				if (i != fields.length - 1)
					builder.append(",");
			}
			builder.append(" from ").append(c.getSimpleName()).append(" where id in(");
			for (int i = 0; i < ids.length; i++) {
				builder.append(ids[i]);
				if (i != ids.length - 1)
					builder.append(",");
			}
			builder.append(")");
			return session.createQuery(builder.toString()).list();
		} finally {
			close(session);
		}
	}

	public long getMaxId(Class<?> c) {
		String hql = "select max(id) from " + c.getSimpleName();
		Session session = open(factory);
		try {
			LogUtil.debug(hql);
			Object o = session.createQuery(hql).uniqueResult();
			return o != null ? (Long) o : 0;
		} finally {
			close(session);
		}
	}

	public void updateOneField(Class<?> c, Serializable id, String field, Object value) {
		StringBuilder builder = new StringBuilder();
		builder.append("update ").append(c.getName()).append(" set ");
		builder.append(field).append("=:").append(field);
		builder.append(" where id=").append(id);
		Session session = open(factory);
		try {
			Query query = session.createQuery(builder.toString());
			if (value instanceof byte[]) {
				query.setBinary(field, (byte[]) value);
			} else {
				query.setParameter(field, value);
			}
			query.executeUpdate();
		} finally {
			close(session);
		}
	}

	public void updateFields(Class<?> c, Serializable id, Map<String, Object> fields) {
		StringBuilder builder = new StringBuilder();
		builder.append("update ").append(c.getName()).append(" set ");
		String[] fieldNames = fields.keySet().toArray(new String[0]);
		int len = fieldNames.length;
		for (int i = 0; i < len; i++) {
			builder.append(fieldNames[i]).append("=:").append(fieldNames[i]);
			if (i != len - 1)
				builder.append(", ");
		}
		builder.append(" where id=").append(id);
		Session session = open(factory);
		try {
			Query query = session.createQuery(builder.toString());
			for (int i = 0; i < len; i++) {
				Object value = fields.get(fieldNames[i]);
				if (value instanceof byte[]) {
					query.setBinary(fieldNames[i], (byte[]) value);
				} else {
					query.setParameter(fieldNames[i], value);
				}
			}
			query.executeUpdate();
		} finally {
			close(session);
		}
	}

	public <T> List<T> getAllByOwner(Class<T> c, long owner) {
		Session session = open(factory);
		try {
			String sql = "select e from " + c.getSimpleName() + " e where ownerId=:v";
			return (List<T>) session.createQuery(sql).setParameter("v", owner).list();
		} finally {
			close(session);
		}
	}

	public <T> List<T> getListByOwner(Class<T> c, long owner, String ownerField, int from, int to) {
		Session session = open(factory);
		try {
			String sql = "select e from " + c.getSimpleName() + " e where " + ownerField + "=:v";
			return (List<T>) session.createQuery(sql).setParameter("v", owner).setFirstResult(from)
					.setMaxResults(to - from).list();
		} finally {
			close(session);
		}
	}

	public <T> List<T> getListByOwner(Class<T> c, long owner, String ownerField) {
		Session session = open(factory);
		try {
			String sql = "select e from " + c.getSimpleName() + " e where " + ownerField + "=:v";
			return (List<T>) session.createQuery(sql).setParameter("v", owner).list();
		} finally {
			close(session);
		}
	}

	public void saveOrUpdate(Object obj) {
		Session session = open(factory);
		try {
			session.saveOrUpdate(obj);
		} finally {
			close(session);
		}
	}

	public void saveOrUpdate(List list) {
		Session session = open(factory);
		try {
			for (Object obj : list) {
//				IDBObject dbobj = (IDBObject) obj;
				session.saveOrUpdate(obj);
			}
		} catch (Exception e) {
			LogUtil.error(e);
		} finally {
			close(session);
		}
	}

	public void saveOrUpdate(Object[] list) {
		Session session = open(factory);
		try {
			for (Object obj : list) {
				session.saveOrUpdate(obj);
			}
		} finally {
			close(session);
		}
	}

	public Serializable save(Object o) {
		Session session = open(factory);
		try {
			return session.save(o);
		} finally {
			close(session);
		}
	}

	public void update(Object o) {
		Session session = open(factory);
		try {
			session.update(o);
		} finally {
			close(session);
		}
	}

	public void updateBySQL(String sql) {
		Session session = open(factory);
		try {
			Query q = session.createSQLQuery(sql);
			q.executeUpdate();
		} finally {
			close(session);
		}
	}

	public void update(Class<?> c, Serializable id, String[] fieldNames, Object[] values) {
		Session session = open(factory);
		try {
			StringBuilder builder = new StringBuilder("update ");
			builder.append(c.getSimpleName()).append(" set ");
			int length = fieldNames.length;
			for (int i = 0; i < length; i++) {
				builder.append(fieldNames[i]).append("=:").append(fieldNames[i]);
				if (i != length - 1)
					builder.append(",");
			}
			builder.append(" where id=").append(id);
			Query q = session.createQuery(builder.toString());
			for (int i = 0; i < length; i++)
				q.setParameter(fieldNames[i], values[i]);
			q.executeUpdate();
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

	public void deleteById(Class<?> c, long id) {
		deleteByIds(c, new long[] { id });
	}

	public void deleteByIds(Class<?> c, long[] ids) {
		Session session = open(factory);
		StringBuilder hql = new StringBuilder();
		if (ids.length == 1) {
			hql.append("delete ").append(c.getName()).append(" where id = ?");
		} else {
			hql.append("delete ").append(c.getName()).append(" where id in (");
			for (int i = 0; i < ids.length; i++) {
				if (i > 0) {
					hql.append(",");
				}
				hql.append("?");
			}
			hql.append(")");
		}

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

	public void delete(Object o) {
		Session session = open(factory);
		try {
			session.delete(o);
		} finally {
			close(session);
		}
	}

	public void delete(String field, Object obj) {
		Session session = open(factory);
		try {
			session.delete(field, obj);
		} finally {
			close(session);
		}
	}

	public void delete(List list) {
		Session session = open(factory);
		try {
			for (Object obj : list) {
				session.saveOrUpdate(obj);
			}
		} finally {
			close(session);
		}
	}

	public List<Object> getFieldValues(String className, String propertyName) {
		Session session = open(factory);
		try {
			StringBuilder b = new StringBuilder();
			b.append("select ").append(propertyName).append(" from ").append(className);
			return session.createQuery(b.toString()).list();
		} finally {
			close(session);
		}
	}

	public List<Object> getFieldValues(String className, String[] propertyNames) {
		Session session = open(factory);
		try {
			StringBuilder b = new StringBuilder();
			b.append("select ");
			int index = 0;
			for (String propertyName : propertyNames) {
				if (index > 0) {
					b.append(",");
				}
				b.append(propertyName);
				index++;
			}
			b.append(" from ").append(className);
			return session.createQuery(b.toString()).list();
		} finally {
			close(session);
		}
	}

	public void truncate(Class<?> c) {
		Session session = open(factory);
		try {
			StringBuilder builder = new StringBuilder("truncate table").append(c.getName());
			session.createSQLQuery(builder.toString()).executeUpdate();
		} finally {
			close(session);
		}
	}

	public long GetLastID(String tablename) {
		Session session = open(factory);
		try {
			String sql = "Insert into " + tablename + " values()";
			session.createSQLQuery(sql).executeUpdate();
			sql = "select LAST_INSERT_ID()";
			Object ret = session.createSQLQuery(sql).uniqueResult();
			return ret == null ? 0 : ((BigInteger) ret).longValue();
		} finally {
			close(session);
		}
	}

	public Object queryBySql(String sql) {
		Session session = open(factory);
		try {
			return session.createSQLQuery(sql).uniqueResult();
		} finally {
			close(session);
		}
	}

}
