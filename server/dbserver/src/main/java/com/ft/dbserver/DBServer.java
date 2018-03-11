package com.ft.dbserver;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.Vector;
import java.util.concurrent.ConcurrentHashMap;


import common.MonitorConntor;
import engine.bean.BeanFactory;
import engine.common.TimeCreator;
import engine.db.client.DBType;
import engine.db.packets.server.DBCheck;
import engine.log.LogUtil;
import engine.net.BaseSession;
import engine.net.CPacket;
import engine.net.NativeBuffer;
import engine.net.SocketListener;
import engine.server.ServerBase;
import engine.util.NumberUtils;
import generated.cgame.packets.ClientPackets;
import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;
import io.netty.channel.socket.DatagramPacket;

public class DBServer extends ServerBase {
	
	// 接受到的消息
	List<CPacket> msgs = new Vector<>();
	// 待处理的消息
	List<CPacket> processmsgs = new ArrayList<>();
	
	public static Map<String, DB> alldb = new HashMap<>();

	public DBServer() {
		InitAll();
		InitDB();
		LogUtil.info("dbserver tcp initing");
		InitNetwork();
		
		
		startUpdate();
		
	}

	private void InitDB() {
		if (configs.GetIntConfig("globaldb", 0) > 0) {
			DB db = new DB(DBType.GlobalDB);
			alldb.put(db.GetName(), db);
			LogUtil.system(db.GetName() + " Init finished.");
		}
		if (configs.GetIntConfig("gamedb", 0) > 0) {
			DB db = new DB(DBType.GameDB);
			alldb.put(db.GetName(), db);
			LogUtil.system(db.GetName() + "gamedb Init finished.");
//			DBNew db1 = new DBNew(DBType.GameDB);
 		}
		if (configs.GetIntConfig("logdb", 0) > 0) {
			DB db = new DB(DBType.LogDB);
			alldb.put(db.GetName(), db);
			LogUtil.system(db.GetName() + "logdb Init finished.");
		}
		if (configs.GetIntConfig("gmdb", 0) > 0) {
			DB db = new DB(DBType.GMDB);
			alldb.put(db.GetName(), db);
			LogUtil.system(db.GetName() + "gmdb Init finished.");
		}
	}

	public static DB GetDB(String name) {
		return alldb.get(name);
	}

	@Override
	protected void InitParams() {
		MaxDirtyCount = configs.GetIntConfig("maxDirtyCount", 500);
		MaxCountPerTime = configs.GetIntConfig("maxCountPerTime", 50); // 一次存10个obj
		MaxThread = configs.GetIntConfig("maxThread", 10);
	}

	@Override
	protected void InitNetwork() {
		SocketListener listener = new SocketListener() {

			@Override
			public void setUdpChannel(Channel channel) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onPacket(Channel channel, DatagramPacket packet) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onDisconnected(Channel channel) {
				Client client = clients.remove(channel);
				if (client == null)
					return;
				LogUtil.info(channel.toString() + " disconnected.");
			}

			@Override
			public void onData(Channel channel, ByteBuf msg) {
				Client client = clients.get(channel);
				if (client == null)
					return;
				client.onData(msg);
			}

			@Override
			public void onConnected(Channel channel) {
				Client client = new Client(channel);
				clients.put(channel, client);
				LogUtil.info(channel.toString() + " connect .");
				// 发送验证包
				client.send(new DBCheck());
			}
		};
		int port = configs.GetIntConfig("dbserver.port", 7757);
		LogUtil.debug("start to listening @ " + port);
		StartTcpServer(port, listener);
		
		
		
		BeanFactory.getBean(MonitorConntor.class).init(this, configs.GetStrConfig("centerserver.address", "127.0.0.1"),
				configs.GetIntConfig("centerserver.monitor.port", 7761));
	}
	
	
	
	
	/**
	 * 将网络消息放入接收队列
	 * 
	 * @param msg
	 * @param session
	 */
	private void processMsg(ByteBuf msg, BaseSession session) {
		NativeBuffer buf = NativeBuffer.wrap(msg);
		int readLen = buf.readableBytes();
		if (readLen < 8 || readLen > 1024 * 8) {
			return;
		}
		CPacket packet = translatePacket(buf);
		packet.o = session;
		msgs.add(packet);
	}

	private CPacket translatePacket(NativeBuffer buf) {
		buf.readInt(); // package size
		buf.readShort(); // appid
		CPacket packet = ClientPackets.get(buf);
		return packet;
	}

	protected void addMsg(CPacket packet, BaseSession session) {
		packet.o = session;
		if (!session.processMsg(packet)) {
			msgs.add(packet);
		}
	}

	private void peekMessage() {
		if (msgs.size() <= 0) {
			return;
		}
		processmsgs.clear();
		synchronized (msgs) {
			for (CPacket packet : msgs) {
				processmsgs.add(packet);
			}
			msgs.clear();
		}
		for (CPacket packet : processmsgs) {
			((BaseSession) packet.o).processMsg(packet);
		}
	}

	private void update() {
		peekMessage();
	}

	private void startUpdate() {
		Timer timer = new Timer(true);
		MonitorConntor monitorConntor = BeanFactory.getBean(MonitorConntor.class);
		timer.schedule(new TimerTask() {
			@Override
			public void run() {
				TimeCreator.tick();
				update();
				monitorConntor.update();
			}
		}, 0, 25);
	}
	
	
	
	@Override
	public int getClientCount() {
		return clients.size();
	}
	
	@Override
	public int getServerCount() {
		return 0;
	}

	private ConcurrentHashMap<Channel, Client> clients = new ConcurrentHashMap<>();

	// 相关配置
	public static int MaxDirtyCount;
	public static int MaxCountPerTime; // 一次存10个obj
	public static int MaxThread;

	public static void main(String[] args) {
		new DBServer();
	}

}
