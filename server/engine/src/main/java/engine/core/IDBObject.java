package engine.core;

import engine.net.NativeBuffer;

public interface IDBObject extends IWritable {
	
	/**
	 * 设置DB对象的id，此方法仅在创建时，由IDGenetor赋值
	 * @param id
	 */
	public void setId(long id);
	
	public long getId();
	
	/**
	 * 获取对象锁，只有DBSaver使用，其他模块不可以用
	 */
	public boolean tryLock();
	
	public void getLock();
	/**
	 * 释放对象锁
	 */
	public void unlock();
	
	public default void write(NativeBuffer buf){}
	
	public default void read(NativeBuffer buf){}
	
	public default void afterLoad(){}
	
	public default void beforeSave(){}
}
