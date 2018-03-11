package engine.net;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Vector;
import java.util.concurrent.ConcurrentHashMap;

import engine.log.LogUtil;
import engine.server.Handler;
import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;

public abstract class SessionManagerBase {

	// session 对象容器
	protected Map<Channel, BaseSession> sessions = new ConcurrentHashMap<>();
	// 接受到的消息
	List<CPacket> msgs = new Vector<>();
	// 待处理的消息
	protected List<CPacket> processmsgs = new ArrayList<>();

	/**
	 * 开始监听
	 * 
	 * @param port
	 */
	public abstract void init(int port);

	public void update() {
		peekMessage();
		updateLogic();
	}

	protected abstract void updateLogic();

	/**
	 * 将网络消息转化为可识别的packet
	 * 
	 * @param buf
	 * @return
	 */
	protected abstract CPacket translatePacket(NativeBuffer buf);

	/**
	 * 将网络消息放入接收队列
	 * 
	 * @param msg
	 * @param session
	 */
	protected void processMsg(ByteBuf msg, BaseSession session) {
		NativeBuffer buf = NativeBuffer.wrap(msg);
		int readLen = buf.readableBytes();
		if (readLen < 8 || readLen > 1024 * 8) {
			return;
		}
		CPacket packet = translatePacket(buf);
		addMsg(packet, session);
	}

	protected void addMsg(CPacket packet, BaseSession session) {
		packet.o = session;
//		if (!session.processMsg(packet)) {
			msgs.add(packet);
//		}
	}

	/**
	 * 将接收队列中的消息放到处理队列
	 */
	private void peekMessage() {
		processmsgs.clear();
		synchronized (msgs) {
			for (CPacket packet : msgs) {
				processmsgs.add(packet);
			}
			msgs.clear();
		}
		for (CPacket packet : processmsgs) {
			BaseSession session = (BaseSession)packet.o;
			
			boolean bre=true;
			try {
				bre=session.processMsg(packet);
			} catch (Exception e) {
				LogUtil.error("消息处理 processMsg 捕获异常"+Handler.errInfo(e));
			}
			
			if (!bre) {
				Handler.delegate(packet);
			}
		}
	}
	
	
	public int getSessionCount(){
		return sessions.size();
	}

}
