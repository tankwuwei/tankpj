package com.ft.dbserver;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;

import engine.core.IDBObject;
import engine.core.queue.DataQueue;
import engine.db.client.Condition;
import engine.db.packets.DBClientPackets;
import engine.db.packets.DBCode;
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
import engine.db.packets.client.CDBSelectDB;
import engine.db.packets.client.CDBUpdate;
import engine.db.packets.server.DBClear;
import engine.db.packets.server.DBDelObjects;
import engine.db.packets.server.DBError;
import engine.db.packets.server.DBGetAll;
import engine.db.packets.server.DBGetFieldValues;
import engine.db.packets.server.DBGetObject;
import engine.db.packets.server.DBGetObjects;
import engine.db.packets.server.DBLastId;
import engine.db.packets.server.DBSaveObjects;
import engine.log.LogUtil;
import engine.net.CPacket;
import engine.net.IntegerFieldDecoder;
import engine.net.NativeBuffer;
import engine.net.NativeBuffer.BaseDataType;
import engine.util.MathUtils;
import engine.util.Utility;
import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;

/**
 * 这是dbserver连接gameserver的会话
 * 
 * @author cxz
 *
 */
public class Client extends DataQueue {

	private static final int SendMaxLimit = IntegerFieldDecoder.MAX_LENGTH - 256; // 扣除一段固定头
	private static final int Sectional_Nono = 0;
	private static final int Sectional_HasNext = 1;
	private static final int Sectional_TheLast = 2;
	public static AtomicInteger getCount = new AtomicInteger();
	private static String desc;

	Channel channel;
	CPacket result;
	private DB db;
	private boolean bValid = false;

	public Client(Channel channel) {
		this.channel = channel;
		if (desc == null) {
			desc = Utility.getRandomString(10);
		}
	}

	private NativeBuffer buffer;

	public void onData(ByteBuf msg) {
		NativeBuffer buf = NativeBuffer.wrap(msg);
		byte sectionalType = buf.readByte();
		if (sectionalType == Sectional_Nono) {// 无分段
			fomartPacket(buf);
		} else {
			if (buffer == null) {
				buffer = NativeBuffer.allocate();
			}
			if (sectionalType == Sectional_HasNext) {// 有后续
				buffer.writeBytes(buf.readBytes());
			} else if (sectionalType == Sectional_TheLast) {// 分段的最后一段
				buffer.writeBytes(buf.readBytes());
				fomartPacket(buffer);
				buffer.free();
				buffer = null;
			}
		}
	}

	private void fomartPacket(NativeBuffer buf) {
		CPacket packet = DBClientPackets.get(buf);
		if (packet.code < 0)
			return;
		packet.o = this;
		this.handle(packet);
	}

	private void SelectDB(String name) {
		db = DBServer.GetDB(name);
		if (db == null) {
			LogUtil.warn("GameServer selected a null db:" + name);
		} else {
			bValid = true;
		}
	}

	@Override
	protected void handle(Object obj) {
		CPacket packet = (CPacket) obj;
		if (packet.code == DBCode.CDBSelectDB) {
			SelectDB(((CDBSelectDB) packet).dbname);
			return;
		}
		if (!bValid) {
			return;
		}
		switch (packet.code) {
		case DBCode.DBError:
			sendError((short) 10001);
			break;
		case DBCode.CDBGetObject:
			doGetObject((CDBGetObject) packet);
			break;
		case DBCode.CDBSaveObjs:
			doSaveObject((CDBSaveObjs) packet);
			break;
		case DBCode.CDBGetObjects:
			doGetObjects((CDBGetObjects) packet);
			break;
		case DBCode.CDBDeleteObjs:
			doDelObjects((CDBDeleteObjs) packet);
			break;
		case DBCode.CDBGetConditions:
			doGetConditions((CDBGetConditions) packet);
			break;
		case DBCode.CDBGetbyhql:
			doGetbyhql((CDBGetbyhql) packet);
			break;
		case DBCode.CDBDeleteObject:
			doDelObject((CDBDeleteObject) packet);
			break;
		case DBCode.CDBUpdate:
			doUpdate((CDBUpdate) packet);
			break;
		case DBCode.CDBGetAll:
			doGetAll((CDBGetAll) packet);
			break;
		case DBCode.CDBGetFieldValues:
			doGetFieldValues((CDBGetFieldValues) packet);
			break;
		case DBCode.CDBClear:
			doClear((CDBClear) packet);
			break;
		case DBCode.CDBGetLastID:
			doGetLastid((CDBGetLastID) packet);
			break;
		case DBCode.CDBSaveDirect:
			doSaveDirect((CDBSaveDirect) packet);
			break;
		}
	}

	private void doSaveDirect(CDBSaveDirect packet) {
		NativeBuffer buff = NativeBuffer.wrap(packet.msg);
		try {
			Class<?> c = Class.forName(packet.className);
			IDBObject o = (IDBObject) c.newInstance();
			o.read(buff);
			// db.save(o);
			Storage<?> storage = db.getStorage(packet.className);

			if (storage != null) {
				storage.SaveDircet(o);
			} else {
				LogUtil.warn(db.GetName() + " has no storage " + packet.className);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void doGetLastid(CDBGetLastID packet) {
		DBLastId ret = new DBLastId();
		String name = packet.className;
		Storage<?> storage = db.getStorage(name);
		synchronized (storage) {
			ret.id = storage.getlastid();
		}
		send(ret);
	}

	private void doClear(CDBClear packet) {
		String className = packet.className;
		Storage<?> s = db.getStorage(className);
		synchronized (s) {
			s.clear();
		}
		DBClear result = new DBClear();
		send(result);
	}

	private void doGetFieldValues(CDBGetFieldValues packet) {
		if (!bValid) {
			return;
		}
		List<Object> r = db.getFieldValues(packet.className, packet.propertyName);
		DBGetFieldValues result = new DBGetFieldValues();
		NativeBuffer msg = NativeBuffer.allocate();
		int proLength = packet.propertyName.length;
		BaseDataType[] types = new BaseDataType[proLength];
		for (int i = 0; i < proLength; i++) {
			types[i] = BaseDataType.values()[packet.propertyType[i]];
		}
		int length = r.size();
		msg.writeInt(length);
		boolean isArray = proLength > 1;
		for (int i = 0; i < length; i++) {
			if (isArray) {
				Object[] array = (Object[]) r.get(i);
				for (int j = 0; j < array.length; j++) {
					msg.write(array[j], types[j]);
				}
			} else {
				msg.write(r.get(i), types[0]);
			}
		}
		result.msg = msg.readBytes();
		msg.free();
		send(result);
	}

	private void doGetAll(CDBGetAll packet) {
		if (!bValid) {
			return;
		}
		@SuppressWarnings("rawtypes")
		List r = null;
		try {
			Class<?> c = Class.forName(packet.className);
			r = db.getList(c, packet.start, packet.length);
			// if(c.getSimpleName().equals("MilitaryCell")){
			// DB.truncate(c);
			// }
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			r = null;
		}

		DBGetAll result = new DBGetAll();
		NativeBuffer msg = NativeBuffer.allocate();
		int length = r != null ? r.size() : 0;
		msg.writeShort(length);
		for (int i = 0; i < length; i++) {
			IDBObject obj = (IDBObject) r.get(i);
			obj.write(msg);
		}
		result.msg = msg.readBytes();
		msg.free();
		send(result);

	}

	private void doUpdate(CDBUpdate packet) {
		NativeBuffer buffer = NativeBuffer.wrap(packet.data);
		int length = buffer.readShort();
		List<IDBObject> list = new ArrayList<>();
		try {
			Class<?> c = Class.forName(packet.className);
			for (int i = 0; i < length; i++) {
				IDBObject o = (IDBObject) c.newInstance();
				o.read(buffer);
				list.add(o);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		buffer.free();
		Storage<? extends IDBObject> s = db.getStorage(packet.className);
		if (list.size() > 0) {
			try {
				s.update(list);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	private void doGetConditions(CDBGetConditions packet) {
		NativeBuffer msg = NativeBuffer.wrap(packet.conditionData);
		String className = msg.readUTF();
		int length = msg.readByte();
		List<Condition> conditions = new ArrayList<>();
		List<Criterion> cons = new ArrayList<>();
		// Order order = null;
		List<Order> orders = new ArrayList<>();
		int limit = 0;
		for (int i = 0; i < length; i++) {
			Condition condition = new Condition();
			condition.read(msg);
			conditions.add(condition);
			if (condition.isLimit()) {
				limit = (int) condition.value;
			} else {
				Object con = condition.f();
				if (con instanceof Criterion) {
					cons.add((Criterion) con);
				} else if (con instanceof Order) {
					orders.add((Order) con);
				}
			}
		}
		msg.free();
		List<?> r = null;
		if (limit == 0 && orders.size() == 0 && conditions.size() == 1 && conditions.get(0).isEq()) {// 如果有切只有一个eq
																										// 走缓存
			Condition condition = conditions.get(0);
			Storage<? extends IDBObject> s = db.getStorage(className);
			if (s == null) {
				LogUtil.debug(className);
			}

			r = s.getObject(condition.propertyName, condition.value.toString());
		} else {
			Class<?> c = null;
			try {
				c = Class.forName(className);
				r = db.getList(c, cons, orders, limit);
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
		}

		sendGetObjects(r);

	}

	
	private void doGetbyhql(CDBGetbyhql packet) {
		NativeBuffer msg = NativeBuffer.wrap(packet.msg);
		byte flag = msg.readByte();
		String hql = msg.readUTF();
		msg.free();

		List<?> r = null;
		try {
			r = db.getListbyhql(hql);
		} catch (Exception e) {
			e.printStackTrace();
		}

		DBGetObjects result = new DBGetObjects();
		NativeBuffer buf = NativeBuffer.allocate();
		buf.writeByte(flag);
		int length = r != null ? r.size() : 0;
		buf.writeInt(length);

		if (flag == 0) {
			for (Object o : r) {
				((IDBObject) o).write(buf);
			}
		} else {
			boolean b = true;
			boolean b2 = true;
			for (Object o : r) {
				if (o instanceof Object[]) {
					if (b2) {
						buf.writeByte(1);
						b2 = false;
					}
					Object[] data = (Object[]) o;
					if (b) {
						buf.writeShort(data.length);
						b = false;
					}
					for (int i = 0; i < data.length; i++) {
						buf.write(data[i]);
					}
				} else {// 查询单个字段
					if (b2) {
						buf.writeByte(0);
						b2 = false;
					}
					buf.write(o);
				}
			}
		}

		result.msg = buf.readBytes();
		buf.free();
		send(result);
	}
	
	
	private void doDelObject(CDBDeleteObject packet) {
		Storage<? extends IDBObject> s = db.getStorage(packet.className);
		int state = 1;
		try {
			s.delObject(packet.ids);
		} catch (Exception e) {
			state = 0;
			e.printStackTrace();
		}
		DBDelObjects result = new DBDelObjects();
		result.state = (byte) state;
		send(result);
	}

	private void doDelObjects(CDBDeleteObjs packet) {
		if (!bValid) {
			return;
		}
		Storage<? extends IDBObject> s = db.getStorage(packet.className);
		int state = 1;
		try {
			s.delObject(packet.field, packet.fvalue);
		} catch (Exception e) {
			state = 0;
			e.printStackTrace();
		}

		DBDelObjects result = new DBDelObjects();
		result.state = (byte) state;
		send(result);
	}

	private void doSaveObject(CDBSaveObjs packet) {
		NativeBuffer msg = NativeBuffer.wrap(packet.msg);
		int length = msg.readShort();
		List<Long> ids = new ArrayList<>();
		for (int i = 0; i < length; i++) {
			String className = msg.readUTF();
			long id = msg.readLong();
			Storage<?> s = db.getStorage(className);
			try {
				synchronized (s) {
					IDBObject data = s.getObjectFromMem(id);
					if (data == null) {
						data = (IDBObject) Class.forName(className).newInstance();
					}
					data.read(msg);
					s.save(data);
					ids.add(s.getId(data));
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		msg.free();
		DBSaveObjects result = new DBSaveObjects();
		long[] rids = new long[ids.size()];
		for (int i = 0; i < rids.length; i++) {
			rids[i] = ids.get(i);
		}
		result.ids = rids;
		send(result);
	}

	private void doGetObjects(CDBGetObjects packet) {
		long t = System.nanoTime();
		Storage<? extends IDBObject> s = db.getStorage(packet.className);
		@SuppressWarnings("rawtypes")
		List r = s.getObject(packet.field, packet.fvalue);
		LogUtil.debug("dbserver getObjects t1=" + (System.nanoTime() - t) / MathUtils.NANO);
		sendGetObjects(r);
		LogUtil.debug("dbserver getObjects t2=" + (System.nanoTime() - t) / MathUtils.NANO);
	}

	private void sendGetObjects(List<?> r) {
		DBGetObjects result = new DBGetObjects();
		NativeBuffer buf = NativeBuffer.allocate();
		int length = r != null ? r.size() : 0;
		buf.writeShort(length);
		for (int i = 0; i < length; i++) {
			IDBObject obj = (IDBObject) r.get(i);
			obj.write(buf);
		}
		result.msg = buf.readBytes();
		buf.free();
		send(result);

	}

	private void doGetObject(CDBGetObject cp) {
		long t = System.nanoTime();
		Storage<? extends IDBObject> s = db.getStorage(cp.className);
		LogUtil.debug("dbserver getObject t1=" + (System.nanoTime() - t) / MathUtils.NANO);
		IDBObject obj = s.getObject(cp.key);
		DBGetObject result = new DBGetObject();
		if (obj != null) {
			NativeBuffer msg = NativeBuffer.allocate();
			obj.write(msg);

			result.msg = msg.readBytes();
			msg.free();
		}
		send(result);
		LogUtil.debug("dbserver getObject t2=" + (System.nanoTime() - t) / MathUtils.NANO);
	}

	private void send(NativeBuffer buf) {
		channel.writeAndFlush(buf.getByteBuf());
	}

	public void test() {

	}

	public void send(CPacket packet) {
		if (channel == null)
			return;
		if (!channel.isActive())
			return;

		// if(Config.performance) Performance.addSend();

		NativeBuffer msg = NativeBuffer.unpool();
		msg.writeInt(0);// 发送的字节长度
		msg.writeByte(Sectional_Nono);// 分段规则 0
		msg.writeShort(packet.code);
		packet.write(msg);
		if (msg.readableBytes() <= SendMaxLimit) {// 小于长度限制不分段 直接发送
			msg.setInt(0, msg.readableBytes() - 4);
			send(msg);
		} else {// 分段发送
			msg.readInt();
			msg.readByte();
			while (msg.readableBytes() > SendMaxLimit) {// 拆分
				NativeBuffer sendBuf = NativeBuffer.unpool();
				sendBuf.writeInt(0);
				sendBuf.writeByte(Sectional_HasNext);
				sendBuf.writeBytes(msg.getByteBuf(), SendMaxLimit);
				sendBuf.setInt(0, sendBuf.readableBytes() - 4);
				send(sendBuf);
			}

			// 分段最后一段
			NativeBuffer sendBuf = NativeBuffer.unpool();
			sendBuf.writeInt(0);
			sendBuf.writeByte(Sectional_TheLast);
			sendBuf.getByteBuf().writeBytes(msg.getByteBuf());
			sendBuf.setInt(0, sendBuf.readableBytes() - 4);
			send(sendBuf);
		}
	}

	public void sendError(short errorCode) {
		// send(PacketUtil.error(errorCode));
		send(dbError(errorCode));
	}

	public static CPacket dbError(short errorCode) {
		DBError packet = new DBError();
		packet.errorCode = errorCode;
		packet.desc = desc;
		return packet;
	}
}
