package engine.net;

import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import engine.log.LogUtil;
import engine.server.Handler;
import engine.server.ServerBase;
import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;

public abstract class ConnectBaseSession {
	protected String ip;
	protected int port;
	
	
	// 接受到的消息
	List<CPacket> msgs = new Vector<>();
	// 待处理的消息
	List<CPacket> processmsgs = new ArrayList<>();

	protected Channel channel;

	public void setChannel(Channel channel) {
		this.channel = channel;
	}

	public Channel getChannel() {
		return channel;
	}

	protected void processMsg(ByteBuf msg) {
		NativeBuffer buf = NativeBuffer.wrap(msg);
		int readLen = buf.readableBytes();
		if (readLen < 4 || readLen > 1024 * 8) {
			return;
		}
		buf.readShort(); // appid
		addPacket(translatePacket(buf));
	}

	
	public void init(String ip, int port) {
		this.ip = ip;
		this.port = port;
		doLink();
	}
	
	abstract protected CPacket translatePacket(NativeBuffer buf);

	abstract protected void processMsg(CPacket packet);

	abstract protected void updateLogic();

	abstract protected void onConnect();

	abstract protected void onDisconnect();

	abstract protected void doLink();
	
	public void update() {
		peekMessage();
		updateLogic();
	}

	/**
	 * 将消息投递到接收队列
	 * 
	 * @param packet
	 */
	protected void addPacket(CPacket packet) {
		msgs.add(packet);
	}

	/**
	 * 将接收队列中的消息放到处理队列
	 */
	private void peekMessage() {
		processmsgs.clear();
		synchronized (msgs) {
			for (CPacket packet : msgs) {
				processmsgs.add(packet);
			}
			msgs.clear();
		}
		for (CPacket packet : processmsgs) {
			
			try {
				processMsg(packet);
			} catch (Exception e) {
				LogUtil.error("connectbase peekmessage 异常"+Handler.errInfo(e));
			}

		}
	}

	public void Send(CPacket packet) {
		if (channel == null || !channel.isActive())
			return;
		NativeBuffer buffer = NativeBuffer.allocate();
		buffer.writeInt(0);
		buffer.writeShort(ServerBase.appid);
		buffer.writeShort(packet.code);
		packet.write(buffer);
		buffer.setInt(0, buffer.readableBytes() - 4);
		channel.writeAndFlush(buffer.byteBuf());
		buffer.free();
	}

	public String getIp() {
		if (channel == null || !channel.isActive())
			return "null";
		return ((InetSocketAddress) channel.remoteAddress()).getHostString();
	}
}
