package engine.net;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.HttpContent;
import io.netty.handler.codec.http.HttpObject;

import java.net.InetSocketAddress;

import engine.log.LogUtil;

@Sharable
public class HttpJsonHandler extends SimpleChannelInboundHandler<HttpObject> {
	private static final boolean Log = true;
	private static final double NonaTime = 1000000000.0;
	
	@Override
	protected void channelRead0(ChannelHandlerContext ctx, HttpObject httpObj)
			throws Exception {
		if(httpObj instanceof HttpContent){
			if(Log)System.out.println("1------ receive data " + System.nanoTime()/NonaTime);
			ByteBuf byteBuf = ((HttpContent)httpObj).content();
			NativeBuffer msg = NativeBuffer.wrap(byteBuf);
			if(Log)System.out.println("2------ after receive data " + System.nanoTime()/NonaTime);
			Request.onData(ctx, msg);
			if(Log)System.out.println("3------ after process data " + System.nanoTime()/NonaTime);
		}
	}
	
	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
        super.channelActive(ctx);
        System.out.println(ctx.name() + "  connected " + ", ip="+((InetSocketAddress)ctx.channel().remoteAddress()).getHostString());
    } 
	
	/**
	 * 
	* @ClassName: Request 
	* @Description: 封装了一整条包含组合消息包的request, 其中包括了>=1个的packet指令
	* @author jichang.yan 
	* @date 2016年4月14日 上午10:09:49 
	*
	 */
	public final static class Request {
		protected ChannelHandlerContext ctx;
//		private List<CPacket> requestPackets = new ArrayList<>();//client->server的请求packet>=1
//		private List<CPacket> responsePackets = new ArrayList<>();//server->client的返回消息 packet>=0
//		private long uniquMark;
		
		private static void onData(ChannelHandlerContext ctx, NativeBuffer msg){
			Request r = new Request();
			r.ctx = ctx;
			try {
				r.excute();//执行各个指令
				if(Log)System.out.println("---23  ------ after excute " + System.nanoTime()/NonaTime);
			} catch (Exception e) {
				LogUtil.error(e);
			} finally{
				r.send();//返回消息
				if(Log)System.out.println("---25  ------ after send " + System.nanoTime()/NonaTime);
			}
		}
		
		//添加返回消息包
//		private void addResponse(CPacket packet){
//			if(responsePackets==null) responsePackets = new ArrayList<CPacket>();
//			responsePackets.add(packet);
//		}
		
		//执行各个packet
		private void excute(){
			
		}
		
		private void send(){
			
		}
	}
	
}
