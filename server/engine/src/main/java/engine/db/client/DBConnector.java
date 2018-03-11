package engine.db.client;

import java.util.Stack;

import engine.db.packets.DBClientPackets;
import engine.db.packets.DBCode;
import engine.db.packets.client.CDBSelectDB;
import engine.log.LogUtil;
import engine.net.CPacket;
import engine.net.IntegerFieldDecoder;
import engine.net.NativeBuffer;
import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;
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
 * db客户端，创建一个连接池链接dbserver，对外提供接口
 * 
 * @author xjf
 */
public class DBConnector {

	private static final int SendMaxLimit = IntegerFieldDecoder.MAX_LENGTH - 256; // 扣除一段固定头
	private static final int Sectional_Nono = 0;
	private static final int Sectional_HasNext = 1;
	private static final int Sectional_TheLast = 2;

	/**
	 * 连接器，负责将消息包传到dbserver，并将返回值送出去
	 * 
	 * @author xjf
	 */
	@Sharable
	private class Connector extends SimpleChannelInboundHandler<ByteBuf> {

		Channel channel;
		CPacket result;

		public Connector() {
			new Thread(() -> {
				try {
					connect();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}).start();
		}

		public void connect() throws Exception {
			while (!doConnect()) {
				Thread.sleep(10000);// 10秒重连
			}
		}

		private void SelectDB() {
			CDBSelectDB msg = new CDBSelectDB();
			msg.dbname = DBType.GetDBName(dbtype);
			sendCmd(msg);
		}

		private boolean doConnect() {
			try {
				realConnect();
				return true;
			} catch (Exception e) {
				LogUtil.info("can not connect to dbserver " + serverHost + " " + serverPort + ", waiting....");
				return false;
			}

		}

		private void realConnect() throws Exception {
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
						ch.pipeline().addLast(new IntegerFieldDecoder(IntegerFieldDecoder.MAX_LENGTH), Connector.this);
					}
				});
				// ChannelFuture f = b.connect("172.16.3.94",
				// Config.DBServerPort).sync();
				ChannelFuture f = b.connect(serverHost, serverPort).sync();

				f.channel().closeFuture().sync();
			} finally {
				workerGroup.shutdownGracefully();
			}
		}

		private void reconnected() {
			// new Thread(() -> {
			try {
				connect();
			} catch (Exception e) {
				e.printStackTrace();
			}
			// }).start();
		}

		private NativeBuffer buffer;

		@Override
		protected void channelRead0(ChannelHandlerContext ctx, ByteBuf msg) throws Exception {
			NativeBuffer buf = NativeBuffer.wrap(msg);
			byte sectionalType = buf.readByte();
			if (sectionalType == Sectional_Nono) {// 无分段
				fomartPacket(buf);
			} else {
				if (buffer == null) {
					buffer = NativeBuffer.allocate();
				}
				if (sectionalType == Sectional_HasNext) {// 有后续
					buffer.writeBytes(buf.readBytes());
				} else if (sectionalType == Sectional_TheLast) {// 分段的最后一段
					buffer.writeBytes(buf.readBytes());
					fomartPacket(buffer);
					buffer.free();
					buffer = null;
				}
			}
		}

		private void fomartPacket(NativeBuffer buf) {
			result = DBClientPackets.get(buf);
			if (result.code == DBCode.DBCheck) {
				SelectDB();
				// collect(this);
				return;
			}
			synchronized (this) {
				notify();
			}
		}

		@Override
		public void channelActive(ChannelHandlerContext ctx) throws Exception {
			channel = ctx.channel();
		}

		@Override
		public void channelInactive(ChannelHandlerContext ctx) throws Exception {
			System.out.println("disconnected ");
			synchronized (all) {
				if (all.contains(this)) {
					all.remove(this);
				}
			}
			reconnected();
		}

		@Override
		public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
			cause.printStackTrace();
			ctx.close();
		}

		private CPacket sendCmd(CPacket packet) {
			result = null;
			try {
				sendShard(packet);

			} catch (Exception e) {
				e.printStackTrace();
			}
			// select包不需要回包
			if (packet.code == DBCode.CDBSelectDB || packet.code == DBCode.CDBSaveDirect
					|| packet.code == DBCode.CDBUpdate) {
				collect(this);
				return result;
			}
			synchronized (this) {
				while (result == null) { // 没有返回消息包，则挂起，等待消息包回来
					try {
						wait();
					} catch (InterruptedException ie) {
						ie.printStackTrace();
					}
				}
			}
			CPacket back = this.result;
			collect(this);
			return back;
		}

		private void sendShard(CPacket packet) {
			NativeBuffer msg = NativeBuffer.unpool();
			msg.writeInt(0);
			msg.writeByte(Sectional_Nono);// 分段规则 0
			msg.writeShort(packet.code);
			packet.write(msg);
			if (msg.readableBytes() <= SendMaxLimit) {// 小于长度限制不分段 直接发送
				msg.setInt(0, msg.readableBytes() - 4);
				send(msg);
			} else {// 分段发送
				msg.readInt();
				msg.readByte();
				while (msg.readableBytes() > SendMaxLimit) {// 拆分
					NativeBuffer sendBuf = NativeBuffer.unpool();
					sendBuf.writeInt(0);
					sendBuf.writeByte(Sectional_HasNext);
					sendBuf.writeBytes(msg.getByteBuf(), SendMaxLimit);
					sendBuf.setInt(0, sendBuf.readableBytes() - 4);
					send(sendBuf);
				}

				// 分段最后一段
				NativeBuffer sendBuf = NativeBuffer.unpool();
				sendBuf.writeInt(0);
				sendBuf.writeByte(Sectional_TheLast);
				sendBuf.getByteBuf().writeBytes(msg.getByteBuf());
				sendBuf.setInt(0, sendBuf.readableBytes() - 4);
				send(sendBuf);
			}
		}

		private void send(NativeBuffer msg) {
			channel.writeAndFlush(msg.getByteBuf());
		}
	}

	private Stack<Connector> all;
	private String serverHost;
	private int serverPort;
	private int dbtype;

	public DBConnector(int threadCount, String host, int port, int dbtype) {
		this.serverHost = host;
		this.serverPort = port;
		this.dbtype = dbtype;
		all = new Stack<>();
		for (int i = 0; i < threadCount; i++) {
			new Connector();
		}
		synchronized (all) {
			while (all.size() < threadCount) {
				try {
					all.wait();
				} catch (InterruptedException ie) {
					LogUtil.error(ie);
				}
			}
		}
	}

	public CPacket command(CPacket cmd) {
		Connector c = getConnector();
		return c.sendCmd(cmd);
	}

	/**
	 * 获得链接器，如果全忙，则挂起
	 * 
	 * @return
	 */
	private Connector getConnector() {
		synchronized (all) {
			while (all.size() == 0) {
				try {
					all.wait();
				} catch (InterruptedException ie) {
					ie.printStackTrace();
				}
			}
			return all.pop();
		}
	}

	/**
	 * 回收链接器
	 * 
	 * @param c
	 */
	private void collect(Connector c) {
		synchronized (all) {
			all.push(c);
			all.notifyAll(); // 通知其他线程继续使用
		}
	}

}
