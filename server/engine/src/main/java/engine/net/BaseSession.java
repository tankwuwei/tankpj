package engine.net;

import java.net.InetSocketAddress;

import engine.server.ServerBase;
import io.netty.channel.Channel;

public abstract class BaseSession {
	protected Channel channel;

	private String ip;
	public void setChannel(Channel channel) {
		this.channel = channel;
		ip = ((InetSocketAddress)channel.remoteAddress()).getHostString();
	}

	public Channel getChannel() {
		return channel;
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
	
	public String getIp(){
		return ip;
//		if (channel == null || !channel.isActive())
//			return "null";
//		return ((InetSocketAddress)channel.remoteAddress()).getHostString();
	}
	
	// session自己处理掉的消息
	public abstract boolean processMsg(CPacket packet);
	
	abstract protected void onConnect();

	abstract protected void onDisconnect();
	
	abstract public void update();
	
}
