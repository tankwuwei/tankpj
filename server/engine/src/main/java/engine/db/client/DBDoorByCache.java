package engine.db.client;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import engine.core.IDBObject;
import engine.db.packets.client.CDBClear;
import engine.db.packets.client.CDBDeleteObject;
import engine.db.packets.client.CDBDeleteObjs;
import engine.db.packets.client.CDBGetAll;
import engine.db.packets.client.CDBGetConditions;
import engine.db.packets.client.CDBGetFieldValues;
import engine.db.packets.client.CDBGetLastID;
import engine.db.packets.client.CDBGetObject;
import engine.db.packets.client.CDBGetObjects;
import engine.db.packets.client.CDBGetbyhql;
import engine.db.packets.client.CDBSaveDirect;
import engine.db.packets.client.CDBSaveObjs;
import engine.db.packets.client.CDBUpdate;
import engine.db.packets.server.DBDelObjects;
import engine.db.packets.server.DBGetAll;
import engine.db.packets.server.DBGetFieldValues;
import engine.db.packets.server.DBGetObject;
import engine.db.packets.server.DBGetObjects;
import engine.db.packets.server.DBLastId;
import engine.db.packets.server.DBSaveObjects;
import engine.log.LogUtil;
import engine.net.NativeBuffer;
import engine.net.NativeBuffer.BaseDataType;

public class DBDoorByCache implements DBDoor {

	private DBConnector client;

	public DBDoorByCache(int threadCount, String host, int serverPort, int dbtype) {
		client = new DBConnector(threadCount, host, serverPort, dbtype);
		LogUtil.system("Success to link to DBServer," + host + ":" + serverPort + "@" + DBType.GetDBName(dbtype));
	}

	/**
	 * 更新单个DB对象
	 * 
	 * @param obj
	 *            需要更新的DBObject
	 */
	@Override
	public void update(IDBObject obj) {
		List<IDBObject> list = new ArrayList<>();
		list.add(obj);
		update(list);
	}

	@Override
	public <K, T extends IDBObject> void update(Map<K, T> objs) {
		List<IDBObject> list = new ArrayList<>(objs.values());
		update(list);
	}
	/**
	 * 批量更新DB对象 List中应该只放同一类型的实例
	 * 
	 * @param objs
	 */
	public <T extends IDBObject> void update(List<T> objs) {
		if (objs == null || objs.size() == 0)
			return;
		CDBUpdate packet = new CDBUpdate();
		packet.className = objs.get(0).getClass().getName();

		NativeBuffer buffer = NativeBuffer.allocate();
		buffer.writeShort(objs.size());
		for (int i = 0; i < objs.size(); i++) {
			objs.get(i).write(buffer);
		}
		packet.data = buffer.readBytes();
		client.command(packet);
	}

	@Override
	public List<Object> getFieldValues(Class<? extends IDBObject> c, String propertyName) {
		return getMultiFieldValues(c, propertyName);
	}

	@Override
	public List<Object> getMultiFieldValues(Class<? extends IDBObject> c, String... propertyNames) {
		int proLength = propertyNames.length;
		CDBGetFieldValues packet = new CDBGetFieldValues();
		packet.className = c.getName();
		packet.propertyName = propertyNames;
		packet.propertyType = new byte[proLength];

		BaseDataType[] types = new BaseDataType[proLength];
		for (int i = 0; i < proLength; i++) {
			Class<?> type = null;
			try {
				type = c.getField(propertyNames[i]).getType();
			} catch (Exception e) {
				LogUtil.error(e);
			}
			BaseDataType dataType = BaseDataType.String;
			if (type == int.class) {
				dataType = BaseDataType.Integer;
			} else if (type == short.class) {
				dataType = BaseDataType.Short;
			} else if (type == long.class) {
				dataType = BaseDataType.Long;
			} else if (type == byte.class) {
				dataType = BaseDataType.Byte;
			}
			packet.propertyType[i] = (byte) dataType.ordinal();
			types[i] = dataType;
		}

		DBGetFieldValues result = (DBGetFieldValues) client.command(packet);
		NativeBuffer msg = NativeBuffer.wrap(result.msg);
		int length = msg.readInt();
		List<Object> r = new ArrayList<>();
		for (int i = 0; i < length; i++) {
			if (proLength == 1) {
				r.add(msg.read(types[0]));
			} else {
				Object[] o = new Object[proLength];
				for (int j = 0; j < proLength; j++) {
					o[j] = msg.read(types[j]);
				}
				r.add(o);
			}
		}
		return r;
	}

	/**
	 * 条件查询 此方法不使用DbServer的缓存,在返回结果有切只有一个的时候使用此方法
	 * 
	 * @return 返回单个结果
	 */
	public <T extends IDBObject> T getUniqu(Class<T> c, String propertyName, String value) {
		return getUniqu(c, Condition.eq(propertyName, value));
	}

	public <T extends IDBObject> T getUniqu(Class<T> c, String propertyName, long value) {
		return getUniqu(c, Condition.eq(propertyName, value));
	}

	public <T extends IDBObject> T getUniqu(Class<T> c, String propertyName, int value) {
		return getUniqu(c, Condition.eq(propertyName, value));
	}

	private <T extends IDBObject> T getUniqu(Class<T> c, Condition con) {
		List<T> list = get(c, con);
		if (list != null && list.size() > 0) {
			return list.get(0);
		}
		return null;
	}

	/**
	 * 条件查询、多条件查询 此方法不使用DbServer的缓存
	 * 
	 * @return 返回查询结果集合
	 */
	public <T extends IDBObject> List<T> get(Class<T> c, Condition... conditions) {
		CDBGetConditions packet = new CDBGetConditions();
		NativeBuffer buf = NativeBuffer.allocate();
		buf.writeUTF(c.getName());
		buf.writeByte(conditions.length);
		for (Condition condition : conditions) {
			condition.write(buf);
		}
		packet.conditionData = buf.readBytes();
		buf.free();
		DBGetObjects result = (DBGetObjects) client.command(packet);
		if (result.msg == null || result.msg.length == 0)
			return null;
		NativeBuffer msg = NativeBuffer.wrap(result.msg);
		List<T> list = new ArrayList<>();
		int length = msg.readShort();
		T obj = null;
		for (int i = 0; i < length; i++) {
			try {
				obj = (T) c.newInstance();
			} catch (Exception e) {
				e.printStackTrace();
			}
			obj.read(msg);
			list.add(obj);
		}
		msg.free();

		return list;

	}

	
	public <T> List<T> getbyhql(Class<T> c, String hql) {
		CDBGetbyhql packet = new CDBGetbyhql();
		NativeBuffer buf = NativeBuffer.allocate();

		if (IDBObject.class.isAssignableFrom(c)) {
			buf.writeByte(0);
		} else {
			buf.writeByte(1);
		}
		buf.writeUTF(hql);

		packet.msg = buf.readBytes();
		buf.free();

		DBGetObjects result = (DBGetObjects) client.command(packet);
		if (result.msg == null || result.msg.length == 0)
			return null;
		NativeBuffer msg = NativeBuffer.wrap(result.msg);

		List<T> list = new ArrayList<>();

		byte flag = msg.readByte();
		int length = msg.readInt();

		if (flag == 0) {
			for (int i = 0; i < length; i++) {
				T o = null;
				try {
					o = (T) c.newInstance();
				} catch (Exception e) {
					e.printStackTrace();
				}
				((IDBObject) o).read(msg);
				list.add(o);
			}
		} else {
			boolean b = true;
			boolean b2 = true;
			int len = 0;
			byte oy = 0;
			for (int i = 0; i < length; i++) {
				if (b2) {
					oy = msg.readByte();
					b2 = false;
				}
				if (oy == 1) {
					if (b) {
						len = msg.readShort();
						b = false;
					}
					Object[] o = new Object[len];
					for (int j = 0; j < len; j++) {
						o[j] = msg.read();
					}
					list.add((T) o);
				} else {// 单个字段
					list.add((T) msg.read());
				}
			}
		}

		msg.free();
		return list;
	}
	
	/**
	 * 通过key取对象
	 * 
	 * @param c
	 */
	public <T extends IDBObject> T get(Class<T> c, long id) {
		CDBGetObject packet = new CDBGetObject();
		packet.className = c.getName();
		packet.key = id;
		DBGetObject result = (DBGetObject) client.command(packet);
		if (result.msg == null || result.msg.length == 0)
			return null;
		T t = null;
		try {
			t = (T) c.newInstance();
		} catch (Exception e) {
			e.printStackTrace();
		}

		NativeBuffer msg = NativeBuffer.wrap(result.msg);
		t.read(msg);
		return t;
	}

	public <T extends IDBObject> List<T> get(Class<T> c, String propertyName, int value) {
		return get(c, Condition.eq(propertyName, value));
	}

	public <T extends IDBObject> List<T> get(Class<T> c, String propertyName, long value) {
		return get(c, Condition.eq(propertyName, value));
	}

	public <T extends IDBObject> List<T> get(Class<T> c, String propertyName, String value) {
		return get(c, Condition.eq(propertyName, value));
	}

	/**
	 * 通过EQ字段值取对象列表, 此方法使用DbServer缓存
	 * 
	 * @param List
	 */
	public <T extends IDBObject> List<T> get(Class<T> c, String field, Object fvalue) {
		CDBGetObjects packet = new CDBGetObjects();
		packet.className = c.getName();
		packet.field = field;
		packet.fvalue = fvalue.toString();
		DBGetObjects result = (DBGetObjects) client.command(packet);
		if (result.msg == null || result.msg.length == 0)
			return null;
		NativeBuffer msg = NativeBuffer.wrap(result.msg);
		List<T> list = new ArrayList<>();
		try {
			int length = msg.readShort();
			T obj = null;
			for (int i = 0; i < length; i++) {
				try {
					obj = (T) c.newInstance();
				} catch (Exception e) {
					e.printStackTrace();
				}
				obj.read(msg);
				list.add(obj);
			}
		} finally {
			msg.free();
		}
		return list;
	}

	/**
	 * @param c
	 *            /** @param limit 列表长度 0表示全部拿取 /** @return
	 */
	public <T extends IDBObject> List<T> getAll(Class<T> c, int limit) {
		List<T> list = new ArrayList<>();
		int start = 0;
		short length = 100;

		while (true) {

			if (limit != 0 && start + length > limit) {
				length = (short) (limit - start);
			}

			CDBGetAll packet = new CDBGetAll();
			packet.className = c.getName();
			packet.start = start;
			packet.length = length;
			DBGetAll result = (DBGetAll) client.command(packet);
			if (result.msg == null || result.msg.length == 0)
				break;
			NativeBuffer msg = NativeBuffer.wrap(result.msg);
			int len = msg.readShort();
			T obj = null;
			for (int i = 0; i < len; i++) {
				try {
					obj = (T) c.newInstance();
					obj.read(msg);
					list.add(obj);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			if (len < length) {
				break;
			}
			start += 100;
		}
		return list;
	}

	public void SaveDirect(IDBObject obj) {
		CDBSaveDirect packet = new CDBSaveDirect();
		packet.className = obj.getClass().getName();
		NativeBuffer buf = NativeBuffer.allocate();
		obj.write(buf);
		packet.msg = buf.readBytes();
		buf.free();
		client.command(packet);
	}

	/**
	 * 更新单个实例
	 */
	public void saveOrUpdate(IDBObject obj) {
		List<IDBObject> list = new ArrayList<>();
		list.add(obj);
		saveOrUpdate(list);
	}

	/**
	 * 更新对象列表
	 */
	public void saveOrUpdate(List<? extends IDBObject> objs) {
		CDBSaveObjs packet = new CDBSaveObjs();
		NativeBuffer buf = NativeBuffer.allocate();
		int length = objs.size();
		buf.writeShort(length);
		for (int i = 0; i < length; i++) {
			IDBObject obj = objs.get(i);
			buf.writeUTF(obj.getClass().getName());
			buf.writeLong(obj.getId());
			obj.write(buf);
		}
		packet.msg = buf.readBytes();
		buf.free();
		DBSaveObjects result = (DBSaveObjects) client.command(packet);
		try {
			for (int i = 0; i < length; i++) {
				if (i >= result.ids.length)
					continue;
				IDBObject obj = objs.get(i);
				obj.setId(result.ids[i]);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void saveOrUpdate(Set<? extends IDBObject> objs) {
		CDBSaveObjs packet = new CDBSaveObjs();
		NativeBuffer buf = NativeBuffer.allocate();
		int length = objs.size();
		buf.writeShort(length);
		for (IDBObject obj : objs) {
			buf.writeUTF(obj.getClass().getName());
			buf.writeLong(obj.getId());
			obj.write(buf);
		}
		packet.msg = buf.readBytes();
		buf.free();
		DBSaveObjects result = (DBSaveObjects) client.command(packet);
		try {
			int index = 0;
			for (IDBObject obj:objs) {
				if (index >= result.ids.length)
					break;
				obj.setId(result.ids[index++]);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public boolean delete(Class<?> c, String field, String fvalue) {
		CDBDeleteObjs packet = new CDBDeleteObjs();
		packet.className = c.getName();
		packet.field = field;
		packet.fvalue = fvalue;
		DBDelObjects result = (DBDelObjects) client.command(packet);
		return result.state == 1 ? true : false;
	}

	public void delete(Class<?> c, long id) {
		delete(c, new long[] { id });
	}

	public void delete(Class<?> c, long[] ids) {
		CDBDeleteObject packet = new CDBDeleteObject();
		packet.className = c.getName();
		packet.ids = ids;
		client.command(packet);
	}

	public void delete(Class<?> c, List<? extends IDBObject> list) {
		long[] ids = new long[list.size()];
		for (int i = 0; i < list.size(); i++) {
			ids[i] = list.get(i).getId();
		}
		delete(c, ids);
	}

	@Override
	public void clear(Class<?> c) {
		CDBClear packet = new CDBClear();
		packet.className = c.getName();
		client.command(packet);
	}

	public long getlastid(Class<?> c) {
		CDBGetLastID packet = new CDBGetLastID();
		packet.className = c.getName();
		DBLastId ret = (DBLastId) client.command(packet);
		return ret.id;
	}

}
