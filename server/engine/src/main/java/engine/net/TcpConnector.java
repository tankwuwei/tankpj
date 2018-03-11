package engine.net;

import engine.log.LogUtil;
import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

/**
 * tcp 连接器
 * 
 * @author cxz
 *
 */
@Sharable
public class TcpConnector extends SimpleChannelInboundHandler<ByteBuf> {

	public boolean connect(String host, int port, SocketListener lis) {
		listener = lis;
		EventLoopGroup workerGroup = new NioEventLoopGroup();
		try {
			Bootstrap b = new Bootstrap();
			b.group(workerGroup);
			b.channel(NioSocketChannel.class);
			b.option(ChannelOption.TCP_NODELAY, true);
			b.option(ChannelOption.SO_KEEPALIVE, true);
			b.handler(new ChannelInitializer<SocketChannel>() {

				@Override
				public void initChannel(SocketChannel ch) throws Exception {
					ch.pipeline().addLast(new IntegerFieldDecoder(IntegerFieldDecoder.MAX_LENGTH), TcpConnector.this);
				}
			});
			ChannelFuture f = b.connect(host, port).sync();

			f.channel().closeFuture().sync();
			return true;
		} catch (Exception e) {
			LogUtil.debug(e);
			workerGroup.shutdownGracefully();
			return false;
		}
	}

	@Override
	protected void channelRead0(ChannelHandlerContext ctx, ByteBuf msg) throws Exception {
		listener.onData(ctx.channel(), msg);
	}

	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		listener.onConnected(ctx.channel());
	}

	@Override
	public void channelInactive(ChannelHandlerContext ctx) throws Exception {
		listener.onDisconnected(ctx.channel());
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		cause.printStackTrace();
		ctx.close();
	}

	private SocketListener listener;

}
