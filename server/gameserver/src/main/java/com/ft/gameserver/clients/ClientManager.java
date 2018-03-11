package com.ft.gameserver.clients;

import java.net.InetSocketAddress;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.Future;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Controller;

import com.ft.gameserver.GameServer;
import com.ft.gameserver.SteamHttp;
import com.ft.gameserver.globalmods.BlacklistMod;

import common.ErrorCode;
import dbobjects.gamedb.AccountData;
import engine.bean.BeanFactory;
import engine.log.LogUtil;
import engine.net.BaseSession;
import engine.net.CPacket;
import engine.net.HttpConnector;
import engine.net.NativeBuffer;
import engine.net.SessionManagerBase;
import engine.net.SocketListener;
import engine.server.ServerBase;
import engine.util.IPv4Util;
import generated.cgame.packets.ClientPackets;
import generated.cgame.packets.client.CSteamLogin;
import generated.cgame.packets.server.SCLoginRet;
import generated.cgame.packets.server.VMsgConnect;
import generated.cgame.packets.server.VMsgDisconnect;
import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;
import io.netty.channel.socket.DatagramPacket;

@Controller
//@Configuration
//@EnableAsync
public class ClientManager extends SessionManagerBase {

	@Autowired
	SteamHttp steamhttp;
	@Autowired
	BlacklistMod BlacklistMod;
	
	public ClientManager() {

	
	}
	
	//检查steam登录
	/*
	 * */
	public void CheckSteamLogOnTicket(Client client,CSteamLogin login){
	
	
		/*
		  返回值
		{
		"appownership": {
			"ownsapp": true,
			"permanent": false,
			"timestamp": "2017-12-18T10:04:09Z",
			"ownersteamid": "76561198356316072",
			"result": "OK"
		}
		}
		*/
		
		
		steamhttp.CheckSteamAuth(Long.toString(login.steamid), handler->{
			
			SCLoginRet ret = new SCLoginRet();
			ret.account="";
			ret.ret = ErrorCode.InvalidCode;
			
			if (handler.bsuccess) {
				String response=handler.response.getResponseBody();
				//返回json格式 但是不使用数据所以这里直接 判断是否包含ownsapp即可
				if (response.contains("\"ownsapp\": true")) {
					ret.ret=ErrorCode.SUCCESS;
					client.Send(ret);
					if (client.accountData==null) {
						client.accountData = new AccountData();
					}
					client.accountData.id=login.steamid;
					client.onAccountLoginSuccess();
					
					LogUtil.debug("steam 登录验证成功 "+login.steamid);
				}else{
					client.Send(ret);
					LogUtil.error("steam 登录验证失败 "+login.steamid);
				}
				
			}else{
				client.Send(ret);
				LogUtil.error("steam 登录验证失败  网络验证异常 "+login.steamid);
			}
			
		
		});
		
		
	}
	
	Map<Long, Client> mpsteam=new HashMap<>();//放steam 检测的steamid 对应map
	
	@Override
	public void init(int port) {
		LogUtil.info("Start listening for client @ " + port);
		ServerBase.StartTcpServer(port, listener, true);
	}

	@Override
	protected void updateLogic() {
		// 检查账号check的返回值
		Iterator<Map.Entry<Future<String>, Client>> iterator = allrequest.entrySet().iterator();
		while (iterator.hasNext()) {
			Map.Entry<Future<String>, Client> entry = iterator.next();
			if (entry.getKey().isDone()) {
				try {
					entry.getValue().onAccountCheckReturn(entry.getKey().get());
				} catch (Exception e) {
					LogUtil.warn("ClientManager.updateLogic" + e);
				}
				iterator.remove();
			}
		}

		for (BaseSession session : sessions.values()) {
			session.update();
		}
	}

	@Override
	protected CPacket translatePacket(NativeBuffer buf) {
		buf.readInt(); // package size
		buf.readShort(); // appid
		CPacket packet = ClientPackets.get(buf);
		return packet;
	}

	/**
	 * 到平台验证账号有效性 这是个异步过程
	 * 
	 * @param account
	 * @param password
	 * @return
	 */
	public void checkLoginToUserCenter(Client client, String account, String password) {
		Future<String> request = getContentFromRemote(account, password);
		allrequest.put(request, client);
		LogUtil.debug("get content return");
	}

	@Async
	public Future<String> getContentFromRemote(String account, String password) {
		LogUtil.debug("start to get");
		String url = GameServer.usercenterurl + account + "&password=" + password;
		String content = HttpConnector.Content(url);
		return new AsyncResult<String>(content);
	}

	
	
	
	/**
	 * 处理玩家登录逻辑
	 * 
	 * @param client
	 */
	public void onPlayerLogin(Client client) {
		// 检查重复登录，把之前的账号踢掉
		Client old = accountclients.get(client.accountData.id);
		if (old != null) {
			old.disConnect();
		}
		accountclients.put(client.accountData.id, client);
	}

	public void onPlayerLogout(long id) {
		accountclients.remove(id);
	}

	// <accountid, client> // 重复登录判断
	Map<Long, Client> accountclients = new HashMap<>();

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
			String ip = ((InetSocketAddress) channel.remoteAddress()).getHostString();
			if (BlacklistMod.isblackip(ip)) {// check IP
				LogUtil.warn("黑名单尝试连接！IP=" + ip);
				channel.disconnect();
				return;
			}

			Client client = BeanFactory.getBean(Client.class);
			client.setChannel(channel);
			sessions.put(channel, client);
			VMsgConnect msg = new VMsgConnect();
			addMsg(msg, client);
		}
	};

	public void kickout(int ip) {
		for (Map.Entry<Channel, BaseSession> o : sessions.entrySet())
			if (ip == IPv4Util.ipStr2Int(o.getValue().getIp())) {
				LogUtil.warn("加入黑名单！断开连接！IP=" + o.getValue().getIp());
				listener.onDisconnected(o.getKey());
			}
	}

	Map<Future<String>, Client> allrequest = new HashMap<>();

}
