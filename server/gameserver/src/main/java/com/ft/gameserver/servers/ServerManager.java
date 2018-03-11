package com.ft.gameserver.servers;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.ft.gameserver.agents.agentManager;

import dbobjects.logdb.LogBattle;
import engine.bean.BeanFactory;
import engine.log.LogUtil;
import engine.net.BaseSession;
import engine.net.CPacket;
import engine.net.NativeBuffer;
import engine.net.SessionManagerBase;
import engine.net.SocketListener;
import engine.server.ServerBase;
import generated.cgame.packets.ClientPackets;
import generated.cgame.packets.server.VMsgConnect;
import generated.cgame.packets.server.VMsgDisconnect;
import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;
import io.netty.channel.socket.DatagramPacket;

/***
 * 游戏服务器管理器**
 * 
 * @author cxz
 *
 */

@Controller
public class ServerManager extends SessionManagerBase {

	@Autowired
	agentManager agentManager;

	@Override
	public void init(int port) {
		LogUtil.info("Start listing for fightserver @ " + port);
		ServerBase.StartTcpServer(port, listener, true);
	}

	@Override
	public void updateLogic() {
	}

	@Override
	protected CPacket translatePacket(NativeBuffer buf) {
		buf.readInt(); // package size
		buf.readShort(); // appid
		CPacket packet = ClientPackets.get(buf);
		return packet;
	}

	// 战斗状态改变了
	public void OnStateChange(Server server) {
		// 开启
		if (server.bOpen()) {
			onServerReady(server);
		} else {
			onServerClosed(server);
		}
	}

	private void onServerReady(Server server) {
		Set<Server> zone = freeServers.get(server.getZoneid());
		if (zone == null) {
			zone = new HashSet<>();
			freeServers.put(server.getZoneid(), zone);
		}
		zone.add(server);
		agentManager.AddFightSvr(server);
	}

	private void onServerClosed(Server server) {
		agentManager.RemoveFightSvr(server);
	}

	public void addServer(Server server) {
		server.setZoneid(agentManager.getZoneId(server.getagentindex()));
		servers.put(server.getServerid(), server);
	}

	public void removeServer(Server server) {
		Set<Server> zone = freeServers.get(server.getZoneid());
		if (zone != null) {
			zone.remove(server);
		}
		servers.remove(server.getServerid());
		if (server.bOpen()) {
			agentManager.RemoveFightSvr(server);
		}
	}

	/**
	 * 获取一个空闲的server，并且从列表中移除，防止被重复使用
	 * 
	 * @param zoneid
	 * @return
	 */
	public Server getFreeSvr(int zoneid) {
		Set<Server> getmap = freeServers.get(zoneid);
		if (getmap == null || getmap.isEmpty()) {
			return null;
		}
		Server server = getmap.iterator().next();
		getmap.remove(server);
		return server;
	}

	public Server getServer(long serverid) {
		return servers.get(serverid);
	}

	SocketListener listener = new SocketListener() {
		@Override
		public void setUdpChannel(Channel arg0) {
		}

		@Override
		public void onPacket(Channel arg0, DatagramPacket arg1) {
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
			Server server = BeanFactory.getBean(Server.class);
			server.setChannel(channel);
			sessions.put(channel, server);
			VMsgConnect msg = new VMsgConnect();
			addMsg(msg, server);
		}
	};

	// zoneid,
	Map<Integer, Set<Server>> freeServers = new HashMap<>();
	// 所有
	Map<Long, Server> servers = new HashMap<>();

}
