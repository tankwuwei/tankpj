package com.ft.agent;

import org.hyperic.sigar.CpuPerc;
import org.hyperic.sigar.Mem;
import org.springframework.stereotype.Controller;

import common.ErrorCode;
import common.MonitorConntor;
import engine.common.TimeCreator;
import engine.log.LogUtil;
import engine.net.CPacket;
import engine.net.ConnectBaseSession;
import engine.net.NativeBuffer;
import engine.net.SocketListener;
import engine.net.TcpConnector;
import engine.server.ServerBase;
import engine.util.Utility;
import generated.cgame.packets.ClientPackets;
import generated.cgame.packets.Code;
import generated.cgame.packets.server.AgentLoginGameSvr;
import generated.cgame.packets.server.AgentLoginGameSvrRet;
import generated.cgame.packets.server.StartService;
import generated.cgame.packets.server.VMsgConnect;
import generated.cgame.packets.server.VMsgDisconnect;
import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;
import io.netty.channel.socket.DatagramPacket;

@Controller
public class AgentToGameSvrConnector extends ConnectBaseSession {

	private int agentindex;

	public void init(ServerBase server, String ip, int port) {

		init(ip, port);
	}

	@Override
	protected CPacket translatePacket(NativeBuffer buf) {
		return ClientPackets.get(buf);
	}

	@Override
	protected void processMsg(CPacket packet) {
		switch (packet.code) {
		case Code.VMsgConnect:
			onConnect();
			break;
		case Code.VMsgDisconnect:
			onDisconnect();
			break;
		case Code.Shutdown:

			break;
		case Code.AgentLoginGameSvrRet:
			LoginGamesvrRet((AgentLoginGameSvrRet) packet);
			break;
		case Code.StartService:
			if (memcpu_isok())
				StartFightSvr((StartService) (packet));
			else
				LogUtil.warn("Agent空闲内存不足或CPU空闲率不足！");
			break;
		default:
			break;
		}
	}

	private void StartFightSvr(StartService packet) {
		LogUtil.debug("开启战斗服务器 " + packet.fightid);
		if (agentindex > 0) {
			try {
				String runFile = Agent.binFile + " -battlelevel=" + packet.fightid + " -AGENTID=" + agentindex + " -log"
						+ " -GAMEIP=" + Agent.gamesvrip + " -GAMEPORT=" + Agent.gamesvrfightport + " -OUTIP="
						+ Agent.agentip;
				LogUtil.debug("ready to start server: " + runFile);
				// if
				// (System.getProperty("os.name").toLowerCase().indexOf("windows")
				// >= 0) {
				// Runtime.getRuntime().exec("rundll32 url.dll
				// FileProtocolHandler " + runFile);
				// } else {
				Runtime.getRuntime().exec(runFile);
				// }
			} catch (Exception e) {
				LogUtil.warn(e);
			}

		} else {
			LogUtil.debug("等待分配agentid后开启战斗服务器");
		}

	}

	public boolean memcpu_isok() {
		Mem mem = null;
		CpuPerc perc = null;
		try {
			mem = MonitorConntor.sigar.getMem();
			perc = MonitorConntor.sigar.getCpuPerc();
		} catch (Exception e) {
			LogUtil.warn("memcpu_isok: " + e);
			return false;
		}
		double memfree = (double) mem.getFree() / mem.getTotal();
		double cpuidle = perc.getIdle();
		LogUtil.debug("Agent memfree=" + memfree + ", cpuidle=" + cpuidle);

		return memfree > Agent.memfree && cpuidle > Agent.cpuidle;
	}

	private void LoginGamesvrRet(AgentLoginGameSvrRet packet) {
		// TODO Auto-generated method stub
		if (packet.ret == ErrorCode.SUCCESS) {
			agentindex = packet.index;
			LogUtil.debug("agent 登录gamesvr 返回成功 index " + agentindex);
		} else {
			LogUtil.error("agent 登录gamesvr 返回失败");
		}
		Send(packet);
	}

	@Override
	protected void updateLogic() {
		// TODO Auto-generated method stub

	}

	// md5效验 time+agent+wxft#$!$md5 的md5值"
	@Override
	protected void onConnect() {
		// TODO Auto-generated method stub
		AgentLoginGameSvr login = new AgentLoginGameSvr();
		login.time = TimeCreator.GetTimeStamp();
		String str = login.time + "agent" + "wxft#$!$md5";
		login.md5check = Utility.md5_32(str);
		login.zoneid = Agent.zoneid;
		login.zonename = Agent.zoneName;
		login.freecount = Agent.freeFightNum;
		Send(login);
		LogUtil.debug("connect to gameserver send md5" + login.md5check);
	}

	@Override
	protected void onDisconnect() {
		// TODO Auto-generated method stub
		LogUtil.debug("disconnect from gameserver!");
		doLink();
	}

	@Override
	protected void doLink() {
		// TODO Auto-generated method stub
		new Thread(() -> {
			while (!new TcpConnector().connect(ip, port, listener)) {
				try {
					LogUtil.debug(getClass().getName() + " Can not connect to " + ip + " @ " + port + ". wait... ");
					Thread.sleep(10000);
				} catch (Exception e) {
					LogUtil.warn(e);
				}
			}
		}).start();
	}

	SocketListener listener = new SocketListener() {

		@Override
		public void setUdpChannel(Channel channel) {
		}

		@Override
		public void onPacket(Channel channel, DatagramPacket packet) {
		}

		@Override
		public void onDisconnected(Channel channel) {
			VMsgDisconnect msg = new VMsgDisconnect();
			addPacket(msg);
		}

		@Override
		public void onData(Channel channel, ByteBuf msg) {
			processMsg(msg);
		}

		@Override
		public void onConnected(Channel channel) {
			setChannel(channel);
			VMsgConnect msg = new VMsgConnect();
			addPacket(msg);
		}
	};

}
