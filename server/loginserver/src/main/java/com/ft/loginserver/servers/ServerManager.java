package com.ft.loginserver.servers;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Controller;

import engine.bean.BeanFactory;
import engine.log.LogUtil;
import engine.net.BaseSession;
import engine.net.CPacket;
import engine.net.NativeBuffer;
import engine.net.SessionManagerBase;
import engine.net.SocketListener;
import engine.server.ServerBase;
import generated.cgame.packets.ClientPackets;
import generated.cgame.packets.client.GameSvrRegistLoginSvr;
import generated.cgame.packets.server.VMsgConnect;
import generated.cgame.packets.server.VMsgDisconnect;
import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;
import io.netty.channel.socket.DatagramPacket;

@Controller
public class ServerManager extends SessionManagerBase {
	public Map<Integer, Server> servers = new ConcurrentHashMap<>();

	public void init(int port) {
		SocketListener listen = new SocketListener() {

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
				Server server = BeanFactory.getBean(Server.class);
				server.setChannel(channel);
				sessions.put(channel, server);
				VMsgConnect msg = new VMsgConnect();
				addMsg(msg, server);
			}
		};
		LogUtil.info("Start listing for gameserver @ " + port);
		ServerBase.StartTcpServer(port, listen, true);
	}

	/**
	 * 添加一个有效的server对象
	 * 
	 * @param server
	 */
	public void addServer(Server server,GameSvrRegistLoginSvr registLoginSvr) {
		LogUtil.debug(
				"gamesvr 注册 loginsvr  zoneid:" + registLoginSvr.zoneid + " ip:" + registLoginSvr.ip + " port:" + registLoginSvr.port+" name:"+registLoginSvr.svrname);
		

		
		if (registLoginSvr.zoneid > 0) {
			Server getserver = servers.get(registLoginSvr.zoneid);
			if (getserver == null) {
				server.externalIP = registLoginSvr.ip;
				server.port = registLoginSvr.port;
				server.zoneid = registLoginSvr.zoneid;
				server.servername=registLoginSvr.svrname;
				servers.put(server.zoneid, server);
			} else {
				LogUtil.error("gamesvr 注册 loginsvr 重复! zoneid:" + registLoginSvr.zoneid + " ip:" + registLoginSvr.ip + " port:"
						+ registLoginSvr.port);
			}

		}
		else
		{
			LogUtil.error("区号错误!!");
		}
		LogUtil.debug("all server counts:" + servers.size());
	}

	public void RemoveServer(Server server) {
		LogUtil.debug(
				"gamesvr 注销 loginsvr  zoneid:" + server.zoneid + " ip:" + server.externalIP + " port:" + server.port+" name:"+server.servername);
		servers.remove(server.zoneid);
		LogUtil.debug("all server counts:" + servers.size());
	}

	// public void packData(ServerListRet msg) {
	// msg.servers = new ServerInfo[servers.size()];
	// int i = 0;
	// for (Server server : servers.values()) {
	// ServerInfo info = new ServerInfo();
	// server.PackInfo(info);
	// msg.servers[i++] = info;
	// }
	// }

	public Server getServer(int serverid) {
		return servers.get(serverid);
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

}
