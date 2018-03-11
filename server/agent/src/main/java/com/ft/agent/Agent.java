package com.ft.agent;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.Vector;

import common.MonitorConntor;
import engine.bean.BeanFactory;
import engine.common.TimeCreator;
import engine.log.LogUtil;
import engine.net.BaseSession;
import engine.net.CPacket;
import engine.net.NativeBuffer;
import engine.server.ServerBase;
import engine.util.NumberUtils;
import generated.cgame.packets.ClientPackets;
import io.netty.buffer.ByteBuf;

public class Agent extends ServerBase {

	// 接受到的消息
	List<CPacket> msgs = new Vector<>();
	// 待处理的消息
	List<CPacket> processmsgs = new ArrayList<>();
	// fightserver运行路径
	public static String binFile;
	public static String strParam;

	public static String fightsvrip;
	public static String gamesvrip;
	public static int gamesvragentport;
	public static int gamesvrfightport;
	public static String agentip;

	public Agent() {
		InitAll();
		InitNetwork();
		startUpdate();
	}

	@Override
	protected void InitParams() {
		LogUtil.debug("agent: InitParams");
		binFile = configs.GetStrConfig("FightServerPath", "");
		strParam = configs.GetStrConfig("strParam", "");
		fightsvrip = configs.GetStrConfig("agentip", "localhost");
		zoneName = configs.GetStrConfig("zonename", "unknow");
		freeFightNum = configs.GetIntConfig("freecount", 0);
		LogUtil.debug(binFile);
		LogUtil.debug("zonename:"+zoneName);
		
		memfree = (double) configs.GetIntConfig("memfree", 20) / 100;
		cpuidle = (double) configs.GetIntConfig("cpuidle", 20) / 100;
	}

	@Override
	protected void InitNetwork() {
		// 初始化给gameserver连接的监听端口

		agentip = configs.GetStrConfig("agentip", "127.0.0.1");
		gamesvrip = configs.GetStrConfig("gameserver.agentlistenip", "127.0.0.1");
		gamesvragentport = configs.GetIntConfig("gameserver.agentlistenport", 8157);
		gamesvrfightport = configs.GetIntConfig("gameserver.internalport", 8056);

		// StartTcpServer(configs.GetIntConfig("agent.port", 7759), listener,
		// true);

		BeanFactory.getBean(MonitorConntor.class).init(this, configs.GetStrConfig("centerserver.address", "127.0.0.1"),
				configs.GetIntConfig("centerserver.monitor.port", 7761));

		BeanFactory.getBean(AgentToGameSvrConnector.class).init(this, gamesvrip, gamesvragentport);
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
		AgentToGameSvrConnector gameServerConnector = BeanFactory.getBean(AgentToGameSvrConnector.class);
		MonitorConntor monitorConntor = BeanFactory.getBean(MonitorConntor.class);
		timer.schedule(new TimerTask() {
			@Override
			public void run() {
				TimeCreator.tick();
				gameServerConnector.update();
				monitorConntor.update();
				update();
			}
		}, 0, 25);
	}


	@Override
	public int getClientCount() {
		return 0;
	}

	public static void main(String[] args) {
		new Agent();
	}

	@Override
	public int getServerCount() {
		// TODO Auto-generated method stub
		return 0;
	}

	public static int freeFightNum;
	public static String zoneName;
	
	public static double memfree;
	public static double cpuidle;

}
