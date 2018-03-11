package engine.net;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;

public class WebSocketServer {
    
    public <T> void start(int port, WebSocketServerHandler handler) throws Exception {  
        EventLoopGroup bossGroup = new NioEventLoopGroup(); 
        EventLoopGroup workerGroup = new NioEventLoopGroup();  
        try {  
            ServerBootstrap b = new ServerBootstrap(); 
            b.group(bossGroup, workerGroup)
            .channel(NioServerSocketChannel.class) 
            .childHandler(new ChannelInitializer<SocketChannel>() { 
                                @Override  
                                public void initChannel(SocketChannel ch) throws Exception {
                                	ChannelPipeline pipeline = ch.pipeline();
                                	pipeline.addLast(new HttpServerCodec());
                                    pipeline.addLast(new HttpObjectAggregator(65536));
                                    pipeline.addLast(handler==null?new WebSocketServerHandler():handler);
                                }  
                            })
            		.option(ChannelOption.SO_BACKLOG, 12800) 
                    .childOption(ChannelOption.SO_KEEPALIVE, false); 
  
            ChannelFuture f = b.bind("0.0.0.0",port).sync(); 
            System.err.println("websocket server started at "+port);
            f.channel().closeFuture().sync();  
        }
        catch(Exception e){
        	 e.printStackTrace();
        } finally {  
            workerGroup.shutdownGracefully();  
            bossGroup.shutdownGracefully();  
        }  
    
    } 
}
