package com.ft.gameserver.agents;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.stereotype.Controller;

import com.ft.gameserver.servers.Server;

import engine.bean.BeanFactory;
import engine.common.TimeCreator;
import engine.log.LogUtil;
import engine.net.BaseSession;
import engine.net.CPacket;
import engine.net.NativeBuffer;
import engine.net.SessionManagerBase;
import engine.net.SocketListener;
import engine.server.ServerBase;
import generated.cgame.packets.ClientPackets;
import generated.cgame.packets.objects.GameZoneInfo;
import generated.cgame.packets.server.SCZoneInfo;
import generated.cgame.packets.server.VMsgConnect;
import generated.cgame.packets.server.VMsgDisconnect;
import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;
import io.netty.channel.socket.DatagramPacket;

@Controller
public class agentManager extends SessionManagerBase {

	private static int checkRate = 10;

	public void UpdateFightOpen() {
		int currtime = TimeCreator.GetTimeStamp();
		if ((currtime - timesave) < checkRate) {
			return;
		}

		timesave = currtime;
		ForceUpdate();
	}

	private void ForceUpdate() {
		for (agentSession agent : agents.values()) {
			agent.checkServerNum();
		}
	}

	@Override
	public void init(int port) {
		// TODO Auto-generated method stub
		LogUtil.info("Start listening for agent @ " + port);
		ServerBase.StartTcpServer(port, listener, true);
	}

	@Override
	protected void updateLogic() {
		// TODO Auto-generated method stub

	}

	/*
	 * 打包游戏区信息
	 */
	public void packZoneList(SCZoneInfo msg) {
		List<GameZoneInfo> list = new ArrayList<>();
		for (Map.Entry<Integer, Set<agentSession>> entry : zones.entrySet()) {
			if (getServerCount(entry.getValue()) == 0) {
				continue;
			}
			GameZoneInfo info = new GameZoneInfo();
			info.id = entry.getKey();
			info.zonename = zoneName.get(entry.getKey());
			list.add(info);
		}
		msg.zones = new GameZoneInfo[list.size()];
		int i = 0;
		for (GameZoneInfo info : list) {
			msg.zones[i++] = info;
		}
	}

	/*
	 * 获取指定agent下面的fightserver数量
	 */
	private int getServerCount(Set<agentSession> sets) {
		int count = 0;
		for (agentSession agent : sets) {
			count += agent.getFreeServerCount();
		}
		return count;
	}

	public void AddFightSvr(Server server) {
		agentSession agentSession = getAgentSession(server.getagentindex());
		if (agentSession != null) {
			agentSession.AddFightServer(server);
		}

	}

	/*
	 * 移除一个空闲的server
	 */
	public void RemoveFightSvr(Server server) {
		agentSession agentSession = getAgentSession(server.getagentindex());
		if (agentSession != null) {
			agentSession.RemoveFightServer(server);
		}
	}

	public void AddAgent(agentSession session) {
		zoneName.remove(session.zoneid);
		zoneName.put(session.zoneid, session.zonename);
		agents.put(session.agentindex, session);
		Set<agentSession> agentSessions = zones.get(session.zoneid);
		if (agentSessions == null) {
			agentSessions = new HashSet<>();
			zones.put(session.zoneid, agentSessions);
		}
		agentSessions.add(session);
	}

	public void RemoveSession(agentSession session) {
		agents.remove(session.agentindex);
		Set<agentSession> agentSessions = zones.get(session.zoneid);
		if (agentSessions != null) {
			agentSessions.remove(session);
		}
	}

	private agentSession getAgentSession(int agentid) {
		return agents.get(agentid);
	}

	@Override
	protected CPacket translatePacket(NativeBuffer buf) {
		buf.readInt(); // package size
		buf.readShort(); // appid
		CPacket packet = ClientPackets.get(buf);
		return packet;
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
			BaseSession session = sessions.get(channel);
			if (session != null) {
				VMsgDisconnect msg = new VMsgDisconnect();
				addMsg(msg, session);
				sessions.remove(channel);
			}
		}

		@Override
		public void onData(Channel channel, ByteBuf msg) {
			BaseSession session = sessions.get(channel);
			if (session != null) {
				processMsg(msg, session);
			}
		}

		@Override
		public void onConnected(Channel channel) {
			agentSession client = BeanFactory.getBean(agentSession.class);
			client.setChannel(channel);
			sessions.put(channel, client);
			VMsgConnect msg = new VMsgConnect();
			addMsg(msg, client);
		}
	};

	public int getZoneId(int agentid) {
		if (!agents.containsKey(agentid)) {
			return 0;
		}
		return agents.get(agentid).zoneid;
	}

	// 自增// id->管理所有agents
	private Map<Integer, agentSession> agents = new HashMap();

	// zoneid
	private Map<Integer, Set<agentSession>> zones = new HashMap<>();

	// zoneid
	private Map<Integer, String> zoneName = new HashMap<>();

	private int timesave;
}
