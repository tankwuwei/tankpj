package com.ft.loginserver.clients;

import java.net.InetSocketAddress;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Controller;

import com.ft.loginserver.LoginServer;

import engine.bean.BeanFactory;
import engine.common.TimeCreator;
import engine.job.JobManager;
import engine.log.LogUtil;
import engine.net.BaseSession;
import engine.net.CPacket;
import engine.net.HttpConnector;
import engine.net.NativeBuffer;
import engine.net.SessionManagerBase;
import engine.net.SocketListener;
import engine.server.ServerBase;
import engine.string.StringUtil;
import generated.cgame.packets.ClientPackets;
import generated.cgame.packets.server.VMsgDisconnect;
import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;
import io.netty.channel.socket.DatagramPacket;

/**
 * 客户端管理器
 * 
 * @author cxz
 *
 */
@Controller
public class ClientManager extends SessionManagerBase {

	static final short loginCheckResult_Success = 1;
	static final short loginCheckResult_NoAccount = -1;
	static final short loginCheckResult_WrongPwd = -2;

	// ban列表
	Map<String, Integer> banlist = new HashMap<>();

//	// 等待验证的用户信息
//	List<AccountCheckInfo> uncheckList = new Vector<>();
//	// 验证完成的用户信息
//	List<AccountCheckInfo> checkedList = new Vector<>();

	@Override
	public void init(int port) {
		SocketListener listener = new SocketListener() {

			@Override
			public void setUdpChannel(Channel channel) {
			}

			@Override
			public void onPacket(Channel channel, DatagramPacket packet) {
			}

			@Override
			public void onDisconnected(Channel channel) {
				BaseSession session = sessions.get(channel);
				if (session != null) {
					VMsgDisconnect msg = new VMsgDisconnect();
					addMsg(msg, session);
					sessions.remove(channel);
				}
			}

			@Override
			public void onData(Channel channel, ByteBuf msg) {
				BaseSession session = sessions.get(channel);
				if (session != null) {
					processMsg(msg, session);
				}
			}

			@Override
			public void onConnected(Channel channel) {
				// ban ip check
				String ip = ((InetSocketAddress) channel.remoteAddress()).getHostString();
				Integer time = banlist.get(ip);
				if (time != null) {
					if (time > TimeCreator.GetTimeStamp()) {
						channel.disconnect();
						return;
					} else {
						banlist.remove(ip);
					}
				}

				Client client = BeanFactory.getBean(Client.class);
				client.setChannel(channel);
				sessions.put(channel, client);
				// VMsgDisconnect msg = new VMsgDisconnect();
				// addMsg(msg, client);
			}
		};
		LogUtil.info("Start listening for client @ " + port);
		ServerBase.StartTcpServer(port, listener, true);
	}

	/**
	 * 向ip黑名单里面添加一个ip
	 * 
	 * @param ip
	 */
	public void AddBanIp(String ip) {
		// if (LoginServer.BanIPTime > 0) {
		// int time = TimeCreator.GetTimeStamp() + LoginServer.BanIPTime;
		// banlist.put(ip, time);
		// LogBan log = new LogBan();
		// log.act = LogBan.act_add;
		// log.ip = ip;
		// log.closetime = time;
		// log.time = TimeCreator.GetTimeStamp();
		// DBManager.saveGlobalLog(log);
		// }
	}

	/**
	 * 添加一个验证请求
	 * 
	 * @param info
	 */
//	public void AddCheckRequest(AccountCheckInfo info) {
//		uncheckList.add(info);
//	}

	/**
	 * 处理所有的验证请求
	 */
//	private void doLoginCheck() {
//		if (uncheckList.size() > 0) {
//			AccountCheckInfo data = uncheckList.get(0);
//			uncheckList.remove(0);
//			String url = LoginServer.usercenterurl + data.client.getAccount() + "&password=" + data.password;
//			String content = HttpConnector.Content(url);
//			if (content != null && !content.isEmpty()) {
//				content = content.replace("{", "");
//				content = content.replace("}", "");
//				content = content.replaceAll("\"", "");
//				Map<String, String> result = StringUtil.split(content, ",", ":");
//				data.State = Short.parseShort(result.get("status"));
//				if (data.State == loginCheckResult_Success) {
//					long accountid = Long.parseLong(result.get("userid"));
//					if (data.client.data.id == 0) {
//						data.client.data.id = accountid;
//						data.client.data.createtime = TimeCreator.GetTimeStamp();
//					} else if (data.client.data.id != accountid) {
//						// 两次id不一样
//						LogUtil.warn("check login from usercenter get unmatched id [" + data.client.getAccount() + "]["
//								+ data.client.data.id + "][" + accountid + "]");
//					}
//				}
//				checkedList.add(data);
//			}
//		}
//
//	}

	/**
	 * 检查请求队列和返回队列
	 */
//	private void checkList() {
//		if (checkedList.size() > 50) {
//			LogUtil.warn("login check result overflow:" + checkedList.size());
//		}
//		if (uncheckList.size() > 50) {
//			LogUtil.warn("login uncheck request overflow:" + uncheckList.size());
//		}
//	}

	/**
	 * 处理登录验证结果
	 */
//	private void processLoginQueryReturn() {
//		while (!checkedList.isEmpty()) {
//			AccountCheckInfo data = checkedList.get(0);
//			checkedList.remove(0);
//			// data.client.onCheckReturn(data);
//		}
//	}

	@Override
	public void updateLogic() {
//		checkList();
//		processLoginQueryReturn();
	}

	@Override
	protected CPacket translatePacket(NativeBuffer buf) {
		buf.readInt(); // package size
		buf.readShort(); // appid
		CPacket packet = ClientPackets.get(buf);
		return packet;
	}

	/**
	 * 另开一个线程处理验证请求
	 */
	@PostConstruct
	private void StartToCheckLogin() {
		JobManager.execute(() -> {
			while (true) {
				try {
//					doLoginCheck();
					Thread.sleep(10);
				} catch (Exception e) {
					LogUtil.warn(e);
				}
			}
		});
	}

}
