package engine.db;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import engine.common.TimeCreator;
import engine.core.IDBObject;
import engine.db.client.Condition;
import engine.db.client.DBDoor;
import engine.db.client.DBDoorByCache;
import engine.db.client.DBType;

/**
 * DB数据管理
 * 
 * @ClassName: DBManager
 * @Description: 逻辑处理调用本类的接口
 * @author cxz
 */

public class DBManager {

	private static Map<Integer, DBDoor> dbs = new HashMap<>();

	private static ArrayList<DBHandler> all = new ArrayList<>();

	public static void init(int dbtype, String addr, int port, int threadcount) {
		DBDoor db = new DBDoorByCache(threadcount, addr, port, dbtype);
		dbs.put(dbtype, db);
		if (dbtype == DBType.GameDB) {
			for (DBHandler handler : all) {
				handler.initDB();
			}
			for (DBHandler handler : all) {
				handler.atfterInitDB();
			}
		}
	}

	public static void regist(DBHandler handler) {
		all.add(handler);
	}

	public static void regist(DBHandler handler, boolean ahead) {
		System.out.println("ahead handler " + handler);
		all.add(0, handler); // 加到开头
	}

	private static DBDoor GetDB(int dbtype) {
		return dbs.get(dbtype);
	}

	/**
	 * 更新单个DB对象
	 * 
	 * @param obj
	 *            需要更新的DBObject
	 */
	public static void update(IDBObject obj, int dbtype) {
		DBDoor db = GetDB(dbtype);
		if (db != null) {
			db.update(obj);
		}
	}

	/**
	 * 批量更新DB对象 List中应该只放同一类型的实例
	 * 
	 * @param objs
	 */
	public static <T extends IDBObject> void update(List<T> objs, int dbtype) {
		DBDoor db = GetDB(dbtype);
		if (db != null) {
			db.update(objs);
		}
	}

	public static <K, T extends IDBObject> void update(Map<K, T> objs, int dbtype) {
		DBDoor db = GetDB(dbtype);
		if (db != null) {
			db.update(objs);
		}
	}

	/**
	 * 获取单个字段的所有的值
	 */
	public static List<Object> getFieldValues(Class<? extends IDBObject> c, String propertyName, int dbtype) {
		DBDoor db = GetDB(dbtype);
		if (db != null) {
			return db.getFieldValues(c, propertyName);
		}
		return null;

	}

	/**
	 * 条件查询 此方法不使用DbServer的缓存,在返回结果有切只有一个的时候使用此方法
	 * 
	 * @return 返回单个结果
	 */
	public static <T extends IDBObject> T getUniqu(Class<T> c, String propertyName, String value, int dbtype) {
		DBDoor db = GetDB(dbtype);
		if (db != null) {
			return db.getUniqu(c, propertyName, value);
		}
		return null;
	}

	public static <T extends IDBObject> T getUniqu(Class<T> c, String propertyName, long value, int dbtype) {
		DBDoor db = GetDB(dbtype);
		if (db != null) {
			return db.getUniqu(c, propertyName, value);
		}
		return null;
	}

	public static <T extends IDBObject> T getUniqu(Class<T> c, String propertyName, int value, int dbtype) {
		DBDoor db = GetDB(dbtype);
		if (db != null) {
			return db.getUniqu(c, propertyName, value);
		}
		return null;
	}

	/**
	 * 条件查询、多条件查询 此方法不使用DbServer的缓存
	 * 
	 * @return 返回查询结果集合
	 */
	public static <T extends IDBObject> List<T> get(Class<T> c, int dbtype, Condition... conditions) {
		DBDoor db = GetDB(dbtype);
		if (db != null) {
			return db.get(c, conditions);
		}
		return null;

	}


	public static <T> List<T> getbyhql(Class<T> c, int dbtype, String hql) {
		DBDoor db = GetDB(dbtype);
		if (db != null) {
			return db.getbyhql(c, hql);
		}
		return null;

	}
	
	/**
	 * 通过key取对象
	 * 
	 * @param c
	 */
	public static <T extends IDBObject> T get(Class<T> c, long id, int dbtype) {
		DBDoor db = GetDB(dbtype);
		if (db != null) {
			return db.get(c, id);
		}
		return null;
	}

	public static <T extends IDBObject> List<T> get(Class<T> c, String propertyName, int value, int dbtype) {
		DBDoor db = GetDB(dbtype);
		if (db != null) {
			return db.get(c, propertyName, value);
		}
		return null;
	}

	public static <T extends IDBObject> List<T> get(Class<T> c, String propertyName, long value, int dbtype) {
		DBDoor db = GetDB(dbtype);
		if (db != null) {
			return db.get(c, propertyName, value);
		}
		return null;
	}

	public static <T extends IDBObject> List<T> get(Class<T> c, String propertyName, String value, int dbtype) {
		DBDoor db = GetDB(dbtype);
		if (db != null) {
			return db.get(c, propertyName, value);
		}
		return null;
	}

	/**
	 * 通过EQ字段值取对象列表, 此方法使用DbServer缓存
	 * 
	 * @param List
	 */
	public static <T extends IDBObject> List<T> get(Class<T> c, String field, Object fvalue, int dbtype) {
		DBDoor db = GetDB(dbtype);
		if (db != null) {
			return db.get(c, field, fvalue);
		}
		return null;
	}

	/**
	 * @param c
	 * @param limit
	 *            列表长度 0表示全部拿取
	 * @return
	 */
	public static <T extends IDBObject> List<T> getAll(Class<T> c, int limit, int dbtype) {
		DBDoor db = GetDB(dbtype);
		if (db != null) {
			return db.getAll(c, limit);
		}
		return null;
	}

	/**
	 * 更新单个实例
	 */
	public static void saveOrUpdate(IDBObject obj, int dbtype) {
		DBDoor db = GetDB(dbtype);
		if (db == null || obj == null) {
			return;
		}
		db.saveOrUpdate(obj);
	}

	public static void saveLog(LogDBObject log) {
		log.setTime(TimeCreator.GetTimeStamp());
		SaveDirect(log, DBType.LogDB);
	}

	/*
	 * 添加记录于globaldb中的log
	 */
	public static void saveGlobalLog(LogDBObject log) {
		log.setTime(TimeCreator.GetTimeStamp());
		SaveDirect(log, DBType.GameDB);
	}

	private static void SaveDirect(IDBObject obj, int dbtype) {
		DBDoor db = GetDB(dbtype);
		if (db == null) {
			return;
		}
		db.SaveDirect(obj);
	}

	static boolean log = false;

	/**
	 * 更新对象列表
	 */
	public static void saveOrUpdate(List<? extends IDBObject> objs, int dbtype) {
		DBDoor db = GetDB(dbtype);
		if (db == null || objs == null) {
			return;
		}
		db.saveOrUpdate(objs);
	}

	public static void saveOrUpdate(Set<? extends IDBObject> objs, int dbtype) {
		DBDoor db = GetDB(dbtype);
		if (db == null || objs == null) {
			return;
		}
		db.saveOrUpdate(objs);
	}

	public static boolean delete(Class<?> c, String field, String fvalue, int dbtype) {
		DBDoor db = GetDB(dbtype);
		if (db == null) {
			return false;
		}
		return db.delete(c, field, fvalue);
	}

	public static void delete(Class<?> c, long id, int dbtype) {
		DBDoor db = GetDB(dbtype);
		if (db == null) {
			return;
		}
		db.delete(c, id);
	}

	public static void delete(Class<?> c, long[] ids, int dbtype) {
		DBDoor db = GetDB(dbtype);
		if (db == null) {
			return;
		}
		db.delete(c, ids);
	}

	public static void delete(Class<?> c, List<? extends IDBObject> list, int dbtype) {
		DBDoor db = GetDB(dbtype);
		if (db == null) {
			return;
		}
		db.delete(c, list);
	}

	public static void clear(Class<?> c, int dbtype) {
		DBDoor db = GetDB(dbtype);
		if (db == null) {
			return;
		}
		db.clear(c);
	}

	public static long getlastid(Class<?> c, int dbtype) {
		DBDoor db = GetDB(dbtype);
		if (db == null) {
			return 0;
		}
		return db.getlastid(c);
	}
}
