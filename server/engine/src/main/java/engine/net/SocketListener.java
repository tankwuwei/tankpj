package engine.net;

import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;
import io.netty.channel.socket.DatagramPacket;

public interface SocketListener {
	void onConnected(Channel channel);
	void onDisconnected(Channel channel);
	void onData(Channel channel,ByteBuf msg);//tcp
	void onPacket(Channel channel,DatagramPacket packet);//udp
	void setUdpChannel(Channel channel);
	
}