package com.ft.dbserver;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicLong;

import javax.persistence.Index;
import javax.persistence.Table;

import engine.db.DBObject;
import engine.log.LogUtil;
import engine.util.VesselExt;

public class DataTable<T extends DBObject> {

	private Class<T> c;
	private DBNew db;
	private String tableName;
	private Set<String> indexs = new HashSet<>();
	private AtomicLong atomicId = new AtomicLong();

	public DataTable(Class<T> t, DBNew db) {
		c = t;
		this.db = db;
		Table table = c.getAnnotation(Table.class);
		tableName = table.name();
		Index[] ids = table.indexes();
		for (Index index : ids) {
			String[] strings = index.columnList().split(",");
			for (String string : strings) {
				indexs.add(string);
			}
		}

		long maxId = db.getMaxId(t);
		if (maxId == 0) {
			maxId = ((long) DBServer.serverid) << 32 | 0;
		}
		atomicId.set(maxId);
		LogUtil.info("Table [" + c.getSimpleName() + "] init finish.");
	}

	public String getName() {
		return tableName;
	}

	/**
	 * 根据id获取一个对象
	 * 
	 * @param id
	 * @return
	 */
	public T getObject(long id) {
		String key = tableName + "*id" + id;
		String ret = db.getOne(key);
		return (T) db.objectDeserialization(ret);
	}

	/**
	 * 根据条件查找。 条件只能是index中的
	 * 
	 * @param field
	 * @param value
	 * @return
	 */
	public List<T> getObjects(String field, String value) {
		if (!VesselExt.HasIt(indexs, field)) {
			return null;
		}
		String key = tableName + "*" + field + value + "*";
		List<T> ret = new ArrayList<>();
		for (String str : db.getAll(key)) {
			ret.add((T) db.objectDeserialization(str));
		}
		return ret;
	}

	public List<T> getObjects() {
		String key = tableName + "*";
		List<T> ret = new ArrayList<>();
		for (String str : db.getAll(key)) {
			ret.add((T) db.objectDeserialization(str));
		}
		return ret;
	}

	public void save(List<DBObject> list) throws Exception {
		for (DBObject obj : list) {
			save(obj);
		}
	}

	public void save(DBObject o) throws Exception {
		if (o.getId() == 0) {
			o.setId(atomicId.incrementAndGet());
		}
		T data = (T) o;
		String key = tableName + "id" + o.getId();
		db.deleteAll(key + "*");
		for (String index : indexs) {
			Field field = c.getField(index);
			key += index + field.get(data).toString();
		}
		db.set(key, db.objectSerialiable(o));
		db.saver.addDirty(o);
	}

	/**
	 * 不写redis，只写mysql
	 * 
	 * @param d
	 * @throws Exception
	 */
	public void saveDircet(DBObject d) throws Exception {
		d.setId(atomicId.incrementAndGet());
		@SuppressWarnings("unchecked")
		T data = (T) d;
		db.saver.addDirty(data);
	}

	public long getLastId() throws Exception {
		T obj = c.newInstance();
		obj.setId(atomicId.getAndIncrement());
		db.saver.addDirty(obj);
		return obj.getId();
	}

	public void deleteObjects(String field, String value) {

	}

	public void deleteObjects(long[] ids) {
		String key = tableName + "id";
		for (long id : ids) {
			db.deleteAll(key + id);
		}
		db.deleteByIds(c, ids);
	}

	public void clear() {
		String key = tableName + "*";
		db.deleteAll(key);
		db.clear(tableName);

	}

}
