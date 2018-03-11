package com.ft.gmserver.Center;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Controller;

import com.ft.gmserver.httpclient.WebsocketManage;

import common.Constant;
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
import generated.cgame.packets.server.VMsgConnect;
import generated.cgame.packets.server.VMsgDisconnect;
import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;
import io.netty.channel.socket.DatagramPacket;

@Controller
public class CenterManager extends SessionManagerBase {

	@Override
	public void init(int port) {
		LogUtil.info("Start listening for client @ " + port);
		ServerBase.StartTcpServer(port, listener, true);

	}

	int sampleTime;

	@Override
	protected void updateLogic() {
		if (TimeCreator.GetTimeStamp() > sampleTime) {
			sampleTime = TimeCreator.GetTimeStamp() + ServerBase.samplerate;
			WebsocketManage.msg2websocket();
		}
	}

	@Override
	protected CPacket translatePacket(NativeBuffer buf) {
		buf.readInt(); // package size
		buf.readShort(); // appid
		return ClientPackets.get(buf);
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
			CenterClient client = BeanFactory.getBean(CenterClient.class);
			client.setChannel(channel);
			sessions.put(channel, client);
			VMsgConnect msg = new VMsgConnect();
			addMsg(msg, client);
		}
	};

	public CenterClient getCenterClient_GameServer() {
		return clients.get(Constant.SERVERTYPE_GAMESERVER);
	}

	public Map<Short, CenterClient> clients = new HashMap<Short, CenterClient>();

}
