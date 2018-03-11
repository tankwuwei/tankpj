package com.ft.loginserver.servers;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.ft.loginserver.clients.Client;

import engine.log.LogUtil;
import engine.net.BaseSession;
import engine.net.CPacket;
import generated.cgame.packets.Code;
import generated.cgame.packets.client.GameSvrRegistLoginSvr;


@Controller
@Scope("prototype")
public class Server extends BaseSession {
	@Autowired
	ServerManager manager;

	// int serverid;
	int zoneid=0;
	String servername = "unknown";
	int port;
	String externalIP; // 给客户端连接的ip
	String version;
	int maxplayer; // 可容纳的最大玩家数

	Set<Long> accounts = new HashSet<>(); // 服务器上所有在线的账号

	@Override
	public boolean processMsg(CPacket packet) {
		LogUtil.debug(packet.code);
		switch (packet.code) {
		case Code.VMsgConnect:
			onConnect();
			break;
		case Code.VMsgDisconnect:
			onDisconnect();
			break;
		case Code.GameSvrRegistLoginSvr:
			RegisterGamesvr(packet);
			break;
		default:
			break;
		}
		return true;
	}

	protected void RegisterGamesvr(CPacket packet) {
		GameSvrRegistLoginSvr registLoginSvr = (GameSvrRegistLoginSvr) packet;

		LogUtil.debug(registLoginSvr.ip + registLoginSvr.port + registLoginSvr.zoneid+registLoginSvr.svrname);
		manager.addServer(this,registLoginSvr);

	}

	@Override
	public void update() {
		// TODO Auto-generated method stub

	}

	public String getSvrName()
	{
		return servername;
	}
	public int getzoneid()
	{
		return zoneid;
	}
	
	@Override
	protected void onConnect() {
		// TODO Auto-generated method stub
		LogUtil.debug("server connect");

	}

	@Override
	protected void onDisconnect() {
		manager.RemoveServer(this);
	}

	// private void onGetGameServerInfo(GameServerInfo msg) {
	// serverid = msg.serverid;
	// zoneid = msg.zoneid;
	// port = msg.port;
	// externalIP = msg.ip;
	// version = msg.version;
	// maxplayer = msg.maxplayer;
	// ServerName name = resources.servername.get(serverid);
	// if (name != null) {
	// servername = name.servername;
	// } else {
	// servername = "unknown";
	// }
	// manager.addServer(this);
	//
	// LogServerAct log = new LogServerAct();
	// log.serverid = serverid;
	// log.zoneid = zoneid;
	// log.selfip = getIp();
	// log.externalip = externalIP;
	// log.port = port;
	// log.act = LogServerAct.act_connect;
	// log.time = TimeCreator.GetTimeStamp();
	// DBManager.saveGlobalLog(log);
	//
	// LogUtil.info("GameServer[" + zoneid + "][" + serverid + "][" + servername
	// + "] connected form " + getIp());
	// }
	//
	// public void PackInfo(ServerInfo info) {
	// info.serverid = serverid;
	// info.zoneid = zoneid;
	// info.servername = servername;
	// info.version = version;
	// }
	//
	// public boolean isFull() {
	// return accounts.size() >= maxplayer;
	// }
	//
	// public void onPlayerRequire(Client client) {
	// UserRequireLogin servermsg = new UserRequireLogin();
	// servermsg.accountid = client.data.id;
	// servermsg.account = client.data.account;
	// servermsg.token = client.token;
	// servermsg.version = client.version;
	// Send(servermsg);
	// waitclients.put(client.data.id, client);
	// }
	//
	// private void onGameServerReady(LoginRequireRet packet) {
	// Client client = waitclients.remove(packet.accountid);
	// if (client != null) {
	// SelectServerRet msg = new SelectServerRet();
	// msg.ret = ErrorCode.SUCCESS;
	// msg.ip = getExternalIP();
	// msg.port = getPort();
	// msg.token = client.token;
	// client.Send(msg);
	// // 断掉客户端连接
	// // channel.disconnect();
	// }
	// }
	//
	// private void onPlayerLogin(UserLogin msg) {
	// accountManager.onAccountLogin(msg.accountid, serverid, msg.ip);
	// }
	//
	// private void onPlayerLogout(UserLogout msg) {
	// accountManager.onAccountLogout(msg.accountid, serverid);
	// }

	public String getExternalIP() {
		return externalIP;
	}

	public int getPort() {
		return port;
	}

	// 等待进入此服务器的用户
	Map<Long, Client> waitclients = new HashMap<>();

}
