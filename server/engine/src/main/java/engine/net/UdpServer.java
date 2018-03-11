package engine.net;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.DatagramPacket;
import io.netty.channel.socket.nio.NioDatagramChannel;
import engine.log.LogUtil;


public class UdpServer {
	
	@Sharable
	private static final class ServerHandler extends SimpleChannelInboundHandler<DatagramPacket>{
		private SocketListener listener;
		protected ServerHandler(SocketListener listener){
			this.listener=listener;
		}
		@Override
	    public void channelReadComplete(ChannelHandlerContext ctx) {
	        ctx.flush();
	    }

		@Override
		public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause)
				throws Exception {
			LogUtil.error(cause);
		}
		
		@Override
		protected void channelRead0(ChannelHandlerContext ctx,  DatagramPacket packet)
				throws Exception {
			listener.onPacket(ctx.channel(),packet);
			
		}
		
	}
	public void init(String host,int port,SocketListener listener){
		final ServerHandler handler=new ServerHandler(listener);
		EventLoopGroup group = new NioEventLoopGroup();
		try {
			Bootstrap b = new Bootstrap();
            b.group(group)
             .channel(NioDatagramChannel.class)
             .option(ChannelOption.SO_BROADCAST, true)
             .handler(handler);

            Channel channel=b.bind(port).sync().channel();
            listener.setUdpChannel(channel);
           // channel.closeFuture().await();
            
//			ServerBootstrap b=new ServerBootstrap();
//			b.group(bossGroup,workerGroup)
//			.channel(NioServerSocketChannel.class)
//			.childOption(ChannelOption.TCP_NODELAY, true)
//			.childOption(ChannelOption.SO_KEEPALIVE, true)
//			.childHandler(new ChannelInitializer<SocketChannel>() {
//				@Override
//				protected void initChannel(SocketChannel ch) throws Exception {
//					ch.pipeline().addLast(new IntegerFieldDecoder(),handler);
//				}
//			});
//			
//			b.bind(host,port).sync();
			
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(0);
		}
	}

}
