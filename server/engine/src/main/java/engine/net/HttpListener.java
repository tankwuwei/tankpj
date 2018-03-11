package engine.net;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.HttpObject;

public interface HttpListener {
	/**
	 * 收到包
	 * @param msg
	 */
	public void request(ChannelHandlerContext ctx, Object msg);
}
