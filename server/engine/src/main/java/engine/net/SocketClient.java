package engine.net;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.DatagramPacket;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;

public class SocketClient {
	private static final class IntegerFieldDecoder extends LengthFieldBasedFrameDecoder{
		public IntegerFieldDecoder() {
			super(1014,0,4,0,4);
		}
	}
	private static final class ServerHandler extends SimpleChannelInboundHandler<ByteBuf>{
		private SocketListener listener;
		protected ServerHandler(SocketListener listener){
			this.listener=listener;
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
		protected void channelRead0(ChannelHandlerContext ctx, ByteBuf msg)
				throws Exception {
			listener.onData(ctx.channel(), msg);
		}
		
	}
	private static final class ClientHandler implements SocketListener{

		@Override
		public void onConnected(Channel channel) {
			System.out.println("connected");
			
//			Buffer buffer=Buffers.create(Code.C_Connect);
//			byte[] data=new byte[256];
//			buffer.writeInt(data.length);
//			buffer.writeBytes(data);
//			channel.writeAndFlush(buffer.beforeSend());
		}

		@Override
		public void onDisconnected(Channel channel) {
			System.out.println("disconnected");
		}

		@Override
		public void onData(Channel channel, ByteBuf msg) {
			System.out.println(msg.hashCode()+"  "+msg.readableBytes());
		}

		@Override
		public void onPacket(Channel channel, DatagramPacket packet) {
			System.out.println("udpreceive:"+packet);
		}

		@Override
		public void setUdpChannel(Channel channel) {
			// TODO Auto-generated method stub
			
		}
		
	}
		public static void main(String[] args) throws Exception {
			final ServerHandler handler=new ServerHandler(new ClientHandler());
	      
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
	                    ch.pipeline().addLast(new IntegerFieldDecoder(),handler);
	                }
	            });

	            // Start the client.
	            ChannelFuture f = b.connect("localhost", 1234).sync();

	            // Wait until the connection is closed.
	            f.channel().closeFuture().sync();
	            
	            
	        } finally {
	            workerGroup.shutdownGracefully();
	        }
	        
	       
	    }
	}
