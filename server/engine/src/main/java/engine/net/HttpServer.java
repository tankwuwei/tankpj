package engine.net;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpObject;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpRequestDecoder;
import io.netty.handler.codec.http.HttpResponseEncoder;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.stream.ChunkedWriteHandler;

import java.util.List;
import java.util.concurrent.Executors;

import engine.log.LogUtil;

public class HttpServer {
	public void start(int port, SimpleChannelInboundHandler<HttpObject> handler) throws Exception {
		EventLoopGroup bossGroup = new NioEventLoopGroup(2);
		EventLoopGroup workerGroup = new NioEventLoopGroup(4, Executors.newCachedThreadPool());
		try {
			ServerBootstrap b = new ServerBootstrap();
			b.group(bossGroup, workerGroup).channel(NioServerSocketChannel.class)
					.childHandler(new ChannelInitializer<SocketChannel>() {
						@Override
						public void initChannel(SocketChannel ch) throws Exception {
							ch.pipeline().addLast(new HttpRequestDecoder(8192, 8192 + 8192, 32767));
							ch.pipeline().addLast(new HttpResponseEncoder());
							// ch.pipeline().addLast("aggregator", new HttpObjectAggregator(65536));
							ch.pipeline().addLast(handler);
						}
					}).option(ChannelOption.SO_BACKLOG, 1024).childOption(ChannelOption.SO_REUSEADDR, true)
					.childOption(ChannelOption.TCP_NODELAY, true) // 无延迟
					.childOption(ChannelOption.SO_KEEPALIVE, false); // 是否保持

			ChannelFuture f = b.bind("0.0.0.0", port).sync();
			System.err.println("httpserver started at " + port);
			f.channel().closeFuture().sync();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			workerGroup.shutdownGracefully();
			bossGroup.shutdownGracefully();
		}

	}

	public <T> void start(int port, HttpListener lis) throws Exception {
		EventLoopGroup bossGroup = new NioEventLoopGroup();
		EventLoopGroup workerGroup = new NioEventLoopGroup();
		try {
			ServerBootstrap b = new ServerBootstrap();
			b.group(bossGroup, workerGroup).channel(NioServerSocketChannel.class)
					.childHandler(new ChannelInitializer<SocketChannel>() {
						@Override
						public void initChannel(SocketChannel ch) throws Exception {
							ch.pipeline().addLast(new HttpRequestDecoder());
							// ch.pipeline().addLast(new MyHttpRequestDecoder());
							ch.pipeline().addLast(new HttpResponseEncoder());

							
							// httprequest, httpcontent等等, 合并成Fullhttprequest
							ch.pipeline().addLast("aggregator", new HttpObjectAggregator(1024*1024*64));
							
							
                            ch.pipeline().addLast(new SimpleChannelInboundHandler<Object>() {
								@Override
								protected void channelRead0(ChannelHandlerContext ctx, Object msg) throws Exception {
									lis.request(ctx, msg);
								}
							});
						}
					}).option(ChannelOption.SO_BACKLOG, 12800).childOption(ChannelOption.SO_KEEPALIVE, false);

			ChannelFuture f = b.bind("0.0.0.0", port).sync();
			LogUtil.debug("httpserver started at " + port);
			f.channel().closeFuture().sync();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			workerGroup.shutdownGracefully();
			bossGroup.shutdownGracefully();
		}

	}

	static class MyHttpRequestDecoder extends HttpRequestDecoder {

		@Override
		protected void decode(ChannelHandlerContext ctx, ByteBuf buffer, List<Object> out) throws Exception {
			super.decode(ctx, buffer, out);
			System.out.print("decode =================  ");
			// Utility.println(buffer.array());
		}

		@Override
		protected void decodeLast(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
			// TODO Auto-generated method stub
			super.decodeLast(ctx, in, out);
			System.out.print("decode last-------------  ");
			// Utility.println(in.array());
		}

	}

}