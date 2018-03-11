package engine.db.client;

import java.util.List;
import java.util.Map;
import java.util.Set;

import engine.core.IDBObject;

public interface DBDoor {
	/**
	 * 更新单个DB对象
	 * 
	 * @param obj
	 *            需要更新的DBObject
	 */
	public void update(IDBObject obj);

	/**
	 * 批量更新DB对象 List中应该只放同一类型的实例
	 * 
	 * @param objs
	 */
	public <T extends IDBObject> void update(List<T> objs);

	public <K, T extends IDBObject> void update(Map<K, T> objs);

	/**
	 * 查询某个字段的所有值
	 */
	public List<Object> getFieldValues(Class<? extends IDBObject> c, String propertyName);

	/**
	 * 查询多个字段的所有值
	 * 
	 * @return List<Object> propertyNames.length=0:List元素为字段的结果对象
	 *         >0:List元素为多个字段结果对象的数组
	 */
	public List<Object> getMultiFieldValues(Class<? extends IDBObject> c, String... propertyNames);

	/**
	 * 条件查询 此方法不使用DbServer的缓存,在返回结果有切只有一个的时候使用此方法
	 * 
	 * @return 返回单个结果
	 */
	public <T extends IDBObject> T getUniqu(Class<T> c, String propertyName, String value);

	public <T extends IDBObject> T getUniqu(Class<T> c, String propertyName, long value);

	public <T extends IDBObject> T getUniqu(Class<T> c, String propertyName, int value);

	/**
	 * 条件查询、多条件查询 此方法不使用DbServer的缓存
	 * 
	 * @return 返回查询结果集合
	 */
	public <T extends IDBObject> List<T> get(Class<T> c, Condition... conditions);

	
	public <T> List<T> getbyhql(Class<T> c, String hql);
	
	/**
	 * 通过key取对象
	 * 
	 * @param c
	 */
	public <T extends IDBObject> T get(Class<T> c, long id);

	public <T extends IDBObject> List<T> get(Class<T> c, String propertyName, int value);

	public <T extends IDBObject> List<T> get(Class<T> c, String propertyName, long value);

	public <T extends IDBObject> List<T> get(Class<T> c, String propertyName, String value);

	/**
	 * 通过EQ字段值取对象列表, 此方法使用DbServer缓存
	 * 
	 * @param List
	 */
	public <T extends IDBObject> List<T> get(Class<T> c, String field, Object fvalue);

	/**
	 * @param c
	 *            /** @param limit 列表长度 0表示全部拿取 /** @return
	 */
	public <T extends IDBObject> List<T> getAll(Class<T> c, int limit);

	/**
	 * 直接保存一个对象，不使用缓存
	 * 
	 * @param obj
	 */
	public void SaveDirect(IDBObject obj);

	/**
	 * 更新单个实例
	 */
	public void saveOrUpdate(IDBObject obj);

	/**
	 * 更新对象列表
	 */
	public void saveOrUpdate(List<? extends IDBObject> objs);

	public void saveOrUpdate(Set<? extends IDBObject> objs);

	public boolean delete(Class<?> c, String field, String fvalue);

	public void delete(Class<?> c, long id);

	public void delete(Class<?> c, long[] ids);

	public void delete(Class<?> c, List<? extends IDBObject> list);

	/**
	 * 清空表
	 */
	public void clear(Class<?> c);

	public long getlastid(Class<?> c);

}
