package engine.net;

import java.io.IOException;

import engine.log.LogUtil;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.epoll.EpollEventLoopGroup;
import io.netty.channel.epoll.EpollServerSocketChannel;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.TooLongFrameException;
import io.netty.util.internal.PlatformDependent;

// netty4 socket server
public class TcpServer {
	@Sharable
	private static final class ServerHandler extends SimpleChannelInboundHandler<ByteBuf> {
		private SocketListener listener;

		protected ServerHandler(SocketListener listener) {
			this.listener = listener;
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
		public void channelReadComplete(ChannelHandlerContext ctx) {
			ctx.flush();
		}

		@Override
		public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
			if (cause instanceof TooLongFrameException || cause instanceof IOException) {
				ctx.channel().close();
				// LogUtil.error(cause);
				return;
			}
			LogUtil.error(cause);
			ctx.close();
		}

		@Override
		protected void channelRead0(ChannelHandlerContext ctx, ByteBuf msg) throws Exception {
			if (msg.readableBytes() > 0) {
				listener.onData(ctx.channel(), msg);
			}

		}

	}

	public void init(String host, int port, SocketListener listener) {
		init(host, port, listener, 327670);
	}

	public void init2(String host, int port, SocketListener listener) {
		final ServerHandler handler = new ServerHandler(listener);
		EventLoopGroup bossGroup = PlatformDependent.isWindows() ? new NioEventLoopGroup(1)
				: new EpollEventLoopGroup(1);
		EventLoopGroup workerGroup = PlatformDependent.isWindows() ? new NioEventLoopGroup()
				: new EpollEventLoopGroup();
		try {
			ServerBootstrap b = new ServerBootstrap();
			b.group(bossGroup, workerGroup)
					.channel(PlatformDependent.isWindows() ? NioServerSocketChannel.class
							: EpollServerSocketChannel.class)
					.childOption(ChannelOption.TCP_NODELAY, true).childOption(ChannelOption.SO_KEEPALIVE, true)
					.childHandler(new ChannelInitializer<SocketChannel>() {
						@Override
						protected void initChannel(SocketChannel ch) throws Exception {
							ch.pipeline().addLast(new LengthFieldBasedFrameDecoder(32767, 0, 4), handler);
						}
					});

			ChannelFuture f = b.bind("0.0.0.0", port).sync();
			LogUtil.debug("tcpserver started at " + port);
			f.channel().closeFuture().sync();
		} catch (Exception e) {
			workerGroup.shutdownGracefully();
			bossGroup.shutdownGracefully();
			e.printStackTrace();
			System.exit(0);
		}
	}

	public void init(String host, int port, SocketListener listener, int packageLength) {
		final ServerHandler handler = new ServerHandler(listener);
		EventLoopGroup bossGroup = new NioEventLoopGroup(1);
		EventLoopGroup workerGroup = new NioEventLoopGroup();
		try {
			ServerBootstrap b = new ServerBootstrap();
			b.group(bossGroup, workerGroup).channel(NioServerSocketChannel.class)
					.childOption(ChannelOption.TCP_NODELAY, true).childOption(ChannelOption.SO_KEEPALIVE, true)
					.childHandler(new ChannelInitializer<SocketChannel>() {
						@Override
						protected void initChannel(SocketChannel ch) throws Exception {
							ch.pipeline().addLast(new IntegerFieldDecoder(packageLength), handler);
						}
					});

			ChannelFuture f = b.bind("0.0.0.0", port).sync();
			LogUtil.debug("tcpserver started at " + port);
			f.channel().closeFuture().sync();
		} catch (Exception e) {
			workerGroup.shutdownGracefully();
			bossGroup.shutdownGracefully();
			e.printStackTrace();
			System.exit(0);
		}
	}
}
