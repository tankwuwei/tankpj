package com.ft.dbserver;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.locks.ReentrantLock;

import javax.persistence.Table;

import org.hibernate.criterion.Restrictions;

import engine.annotation.Stable;
import engine.core.IDBObject;
import engine.log.LogUtil;
import engine.net.FieldReaderWriter;
import engine.util.MathUtils;
import engine.util.ObjectCountor;
import io.netty.buffer.ByteBuf;

/**
 * db缓存 所有访问都基于playerId进行，对PlayerData是单线程的，对全局是并发的。
 * 
 * @author cxz
 *
 */
public class Storage<T extends IDBObject> {
	/** 分配ID */
	private AtomicLong atomicId = new AtomicLong();
	/**
	 * 搜索器是用来匹配某个db对象的专属字段，此对象必须唯一，如此才能和id匹配
	 * 
	 * @author xjf
	 *
	 */
	private class Finder {
		Map<Object, Set<Long>> keys;
		Map<Long, Object> id_Keys;
		Field field;
		boolean stable; // 如果stable=true，表示key不会被改变

		public Finder(String field, Class<T> c) {
			keys = new HashMap<Object, Set<Long>>();
			if (!stable)
				id_Keys = new HashMap<>();
			try {
				this.field = c.getField(field);
				Stable st = this.field.getAnnotation(Stable.class);
				if (st != null) {
					LogUtil.info("stable field " + field + ", in class " + c.getName());
					stable = true;
				}
			} catch (Exception e) {
				LogUtil.error(e);
			}
		}

		private void addData(Object obj, long id) {
			try {
				if (!stable) {
					Object old = id_Keys.get(id);
					if (old != null) {
						// remove old value from keys;
						Set<Long> l = keys.get(old);
						l.remove(id);
						id_Keys.remove(id);
					}
				}
				// add new key id
				Object key = field.get(obj);
				Set<Long> l = keys.get(key);
				if (l == null) {
					l = new HashSet<Long>();
					keys.put(key, l);
				}
				l.add(id);
				if (!stable)
					id_Keys.put(id, key);

			} catch (Exception e) {
				LogUtil.error(e);
			}
		}

		public List<Long> getId(Object key) throws Exception {
			if (!keys.containsKey(key)) {
				loadData(key);
			}
			if (keys.containsKey(key)) {
				return new ArrayList<Long>(keys.get(key));
			}
			return null;
		}

		public List<Long> getId(String key) throws Exception {
			Class<?> c = field.getType();
			Object obj = StringConvertor.convert(c, key);
			return getId(obj);
		}

		public void loadData(Object key) throws Exception {
			getData(field.getName(), key);
		}
	}

	private HashMap<Long, ReentrantLock> locks;
	private HashMap<Long, T> datas;
	private HashMap<String, FieldReaderWriter<?>> rs;
	private Map<String, Finder> finders = new HashMap<String, Storage<T>.Finder>();
	private Class<T> c;
	private String tableName;
	private DB db;

	public Storage(Class<T> t, DB db) {
		this.db = db;
		c = t;
		Table table = c.getAnnotation(Table.class);
		tableName = table.name();
		locks = new HashMap<Long, ReentrantLock>(63);
		datas = new HashMap<Long, T>(63);
		rs = new HashMap<>();

		for (Field f : c.getFields()) {
			int modifiers = f.getModifiers();
			if (Modifier.isPrivate(modifiers))
				continue;
			if (Modifier.isTransient(modifiers))
				continue;
			rs.put(f.getName(), FieldReaderWriter.map.get(f.getGenericType().getTypeName()));
		}

		long maxId = db.getMaxId(t);
		if (maxId == 0) {
			maxId = ((long) DBServer.serverid) << 32 | 0;
		}
		atomicId.set(maxId);

		LogUtil.info("init " + c.getName() + " storage");
	}

	/**
	 * 初始化搜索器
	 * 
	 * @param field
	 */
	public Finder initFinder(String field) {
		if (finders == null)
			finders = new HashMap<String, Storage<T>.Finder>();
		Finder finder = new Finder(field, c);
		finders.put(field, finder);
		return finder;
	}

	/**
	 * 根据给定的字符串得到对应的对象id
	 * 
	 * @param value
	 * @return
	 */
	private List<Long> getId(String field, String value) throws Exception {
		Finder finder = finders.get(field);
		if (finder == null) {
			finder = initFinder(field); // finder延后到需要的时候再初始化
		}
		return finder.getId(value);
	}

	/**
	 * 取PlayerData
	 * 
	 * @param pid
	 * @return
	 */
	public T getObject(long id) {
		ReentrantLock lock = getLock(id);
		lock.lock();
		try {
			return getData(id);
		} finally {
			lock.unlock();
		}
	}

	public T getObjectFromMem(long id) {
		ReentrantLock lock = getLock(id);
		lock.lock();
		try {
			return getData(id, false);
		} finally {
			lock.unlock();
		}
	}

	public void delObject(long id) {
		T t = getData(id);
		if (t != null) {
			t.getLock();
			try {
				db.delete(t);
				remData(id);
			} finally {
				t.unlock();
			}
		}
	}

	public void delMemery(long[] ids) {
		for (long id : ids) {
			remData(id);
		}
	}

	public void delObject(long[] ids) {
		if (ids == null)
			return;
		lockIds(ids);
		try {
			db.deleteByIds(c, ids);
		} finally {
			unlockIds(ids);
		}
		delMemery(ids);
	}

	public void delObject(String field, String value) throws Exception {
		long[] ids = null;
		if (field.equals("id")) {
			ids = new long[] { Long.parseLong(value) };
		} else {
			if (finders != null) {
				Finder finder = finders.get(field);
				if (finder != null) {
					try {
						List<Long> idList = finder.getId(value);
						if (idList != null) {
							ids = new long[idList.size()];
							for (int i = 0; i < idList.size(); i++) {
								ids[i] = idList.get(i);
							}
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		}
		if (ids != null) {
			lockIds(ids);
			try {
				db.delete(field, value);
			} finally {
				unlockIds(ids);
			}
			delMemery(ids);
		} else {
			db.delete(field, value);
		}
	}

	public List<T> getObject(String field, String value) {
		List<Long> ids = null;
		try {
			ids = getId(field, value);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (ids == null)
			return null;
		List<T> r = new ArrayList<>();
		for (Long id : ids) {
			ReentrantLock lock = getLock(id);
			lock.lock();
			try {
				T t = getData(id);
				if (t != null)
					r.add(t);
			} finally {
				lock.unlock();
			}
		}
		return r;
	}

	public void update(List<IDBObject> list) throws Exception {
		for (IDBObject obj : list) {
			update(obj);
		}
	}

	public void update(IDBObject obj) throws Exception {
		ReentrantLock lock = getLock(obj.getId());
		lock.lock();
		try {
			@SuppressWarnings("unchecked")
			T data = (T) obj;

			datas.put(obj.getId(), data);
			obj.getLock();
			try {
				db.saver.addDirty(data);
			} finally {
				data.unlock();
			}

			Thread.sleep(db.saver.getDelay());

		} finally {
			lock.unlock();
		}
	}

	public void update(long id, String[] fields, ByteBuf buf) throws Exception {
		Object[] values = new Object[fields.length];
		for (int i = 0; i < fields.length; i++) {
			values[i] = rs.get(fields[i]).read(buf);
		}
		update(id, fields, values);
	}

	/**
	 * 更新field
	 * 
	 * @param pid
	 * @param fields
	 * @param values
	 */
	public void update(long id, String[] fields, Object[] values) throws Exception {
		ReentrantLock lock = getLock(id);
		lock.lock();
		try {
			T data = getData(id, false);
			// memery update
			if (data != null) {
				@SuppressWarnings("unchecked")
				Class<T> cc = (Class<T>) data.getClass();
				for (int i = 0; i < fields.length; i++) {
					String fieldName = fields[i];
					Field f = cc.getField(fieldName);
					f.set(data, values[i]);
				}
				// add to db
				// DB.update(cc, id, fields, values);
				// DBObject d = (DBObject)data;
				data.getLock();
				try {
					db.saver.addDirty(data);
				} finally {
					data.unlock();
				}
			} else {
				// null data
			}

			Thread.sleep(db.saver.getDelay());

		} finally {
			lock.unlock();
		}
	}

	public long getId(IDBObject d) {
		try {
			@SuppressWarnings("unchecked")
			T data = (T) d;
			Field id = c.getField("id");
			Object obj = id.get(data);
			if (obj instanceof Long) {
				return (Long) obj;
			} else if (obj instanceof Integer) {
				return ((Integer) obj).longValue();
			}
			return 0;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}

	private static ObjectCountor receiver = new ObjectCountor("DBServer.Receive ", 5000);

	public void SaveDircet(IDBObject d) throws Exception{
		d.setId(atomicId.incrementAndGet());
		@SuppressWarnings("unchecked")
		T data = (T) d;
		db.saver.addDirty(data);
	}
	
	public void save(IDBObject d) throws Exception {
		// System.out.println("save " + d);
		long t = System.nanoTime();
		receiver.inc(d);
		@SuppressWarnings("unchecked")
		T data = (T) d;
		Field id = c.getField("id");
		Object obj = id.get(data);
		boolean newObj = false;
		if (obj instanceof Long) {
			Long v = (Long) obj;
			if (v == null || v == 0)
				newObj = true;
			else {
				// 必须要替换对象，否则内存数据没有更新，下次取还是老数据
				datas.put(v, data);
			}
		} else if (obj instanceof Integer) {
			Integer v = (Integer) obj;
			if (v == null || v == 0)
				newObj = true;
			// int的key要变成long才行
			datas.put((long) v.intValue(), data);
		}
		//LogUtil.debug("Storage before save time=" + (System.nanoTime() - t));
		if (newObj) {
			id.set(data, atomicId.incrementAndGet());
			datas.put((Long) id.get(data), data);
			// System.err.println("new"+c.getSimpleName()+"ID:"+id.get(data)+":"+data);
		} else {
			// System.err.println("old"+c.getSimpleName()+"ID:"+id.get(data)+":"+data);
		}
		db.saver.addDirty(data);
		for (Finder finder : finders.values()) {
			try {
				obj = id.get(data);
				finder.addData(data, (Long) obj);
			} catch (Exception e) {
				e.printStackTrace();
				continue;
			}
		}
	}

	private synchronized ReentrantLock getLock(long pid) {
		ReentrantLock lock = locks.get(pid);
		if (lock == null) {
			lock = new ReentrantLock();
			locks.put(pid, lock);
		}
		return lock;
	}

	private List<T> getData(String field, Object key) throws Exception {
		List<T> ts = db.getList(c, Restrictions.eq(field, key));
		if (ts == null)
			return ts;

		Finder finder = finders.get(field);
		if (finder == null)
			return ts;
		for (T t : ts) {
			long id = (Long) (c.getField("id").get(t));
			finder.addData(t, id);
			datas.put(id, t);
		}
		return ts;
	}

	private T getData(long id) {
		return getData(id, true);
	}

	private T getData(long id, boolean needFromDB) {
		long t = System.nanoTime();
		T data = datas.get(id);
		if (needFromDB && data == null) {
			// data = DB.getUnique(c, "id", id);
			data = db.get(c, id);
			//LogUtil.debug("Storage " + c.getName() + " get data from db 1 " + (System.nanoTime() - t) / MathUtils.NANO);
			if (data == null)
				return null;
			datas.put(id, data);
			if (finders != null && finders.size() > 0) {
				for (Finder finder : finders.values()) {
					try {
						id = (Long) (c.getField("id").get(data));
						finder.addData(data, id);
					} catch (Exception e) {
						e.printStackTrace();
						continue;
					}
				}
			}
		}
		//LogUtil.debug("Storage " + c.getName() + " process finder 2 " + (System.nanoTime() - t) / MathUtils.NANO);
		return data;
	}

	private void remData(long id) {
		datas.remove(id);
	}

	private void lockIds(long[] ids) {
		for (long id : ids) {
			IDBObject obj = datas.get(id);
			if (obj != null)
				obj.getLock();
		}
	}

	private void unlockIds(long[] ids) {
		for (long id : ids) {
			IDBObject obj = datas.get(id);
			if (obj != null)
				obj.unlock();
		}
	}

	public void clear() {
		datas.clear();
		finders.clear();
		locks.values().forEach((lo) -> {
			if (lo.isLocked())
				lo.unlock();
		});
		locks.clear();
		db.clear(tableName);
	}

	public long getlastid() {
		return db.GetLastID(tableName);
	}

	public static void main(String[] args) {
		Integer s = 2;
		System.out.println(s.longValue());
	}

}
