package com.ft.gameserver;

import org.springframework.stereotype.Controller;

import engine.log.LogUtil;
import engine.net.CPacket;
import engine.net.ConnectBaseSession;
import engine.net.NativeBuffer;
import engine.net.SocketListener;
import engine.net.TcpConnector;
import generated.cgame.packets.ClientPackets;
import generated.cgame.packets.Code;
import generated.cgame.packets.client.GameSvrRegistLoginSvr;
import generated.cgame.packets.server.VMsgConnect;
import generated.cgame.packets.server.VMsgDisconnect;
import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;
import io.netty.channel.socket.DatagramPacket;

/**
 * 与LoginServer的连接器
 * 
 * @author cxz
 *
 */
@Controller
public class LoginSession extends ConnectBaseSession {

	private static final int overtime = 60;

	public void SetConfigAndStart(String ip, int port) {
		m_ip = ip;
		m_port = port;
		doLink();
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

		default:
			break;
		}
	}

	@Override
	protected CPacket translatePacket(NativeBuffer buf) {
		return ClientPackets.get(buf);
	}

	@Override
	protected void onConnect() {
		LogUtil.debug("Connect to LoginServer[" + m_ip + " @ " + m_port + "] Successfully.");

		GameSvrRegistLoginSvr regsitLoginSvr = new GameSvrRegistLoginSvr();
		regsitLoginSvr.ip = GameServer.externalip;
		regsitLoginSvr.port = GameServer.externalport;
		regsitLoginSvr.zoneid = GameServer.zoneid;
		regsitLoginSvr.svrname=GameServer.svrname;
		Send(regsitLoginSvr);
	}

	@Override
	protected void onDisconnect() {
		LogUtil.debug("Disconnect to LoginServer[" + m_ip + " @ " + m_port + "] .");
		doLink();
	}

	protected void doLink() {
		new Thread(() -> {
			while (!new TcpConnector().connect(m_ip, m_port, listener)) {
				try {
					Thread.sleep(2000);
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

	@Override
	protected void updateLogic() {
		// checkOvertime();
	}

	/**
	 * 建超超时的登录token
	 */
	private void checkOvertime() {

	}

	private String m_ip;
	private int m_port;
}
