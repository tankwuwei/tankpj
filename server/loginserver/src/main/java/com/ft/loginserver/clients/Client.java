package com.ft.loginserver.clients;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.ft.loginserver.LoginServer;
import com.ft.loginserver.servers.Server;
import com.ft.loginserver.servers.ServerManager;

import common.ErrorCode;
import dbobjects.gamedb.AccountData;
import engine.log.LogUtil;
import engine.net.BaseSession;
import engine.net.CPacket;
import generated.cgame.packets.Code;
import generated.cgame.packets.client.RequestLoginSvr;
import generated.cgame.packets.objects.RequestLoginSvrInfo;
import generated.cgame.packets.server.RequestLoginSvrRet;
import io.netty.handler.codec.rtsp.RtspRequestDecoder;

@Controller
@Scope("prototype")
public class Client extends BaseSession {
	@Autowired
	ClientManager manager;
	@Autowired
	ServerManager serverManager;

	private boolean valid = false;
	private int wrongNum; // 验证错误次数
	public AccountData data;
	public String version; // 客户端版本号
	public String token; // 进入gameserver的token

	public String getAccount() {
		return data.account;
	}

	@Override
	public void update() {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean processMsg(CPacket packet) {
		//LogUtil.debug(packet.code);
		if (packet.code == Code.VMsgConnect) {
			onConnect();
		} else if (packet.code == Code.VMsgDisconnect) {
			onDisconnect();
		} else if (!valid) {
			onLogin(packet);
		} else {
			 switch (packet.code) {
			 case Code.RequestLoginSvr:
				 onRequesLoginSvr(packet);
			 break;
			 default:
			 break;
			 }
		}
		return true;
	}

	private void sendServerList() {
		// ServerListRet msg = new ServerListRet();
		// serverManager.packData(msg);
		// Send(msg);
	}

	private void onRequesLoginSvr(CPacket packet) {
		RequestLoginSvr registLoginSvr = (RequestLoginSvr) packet;
		RequestLoginSvrRet requestLoginSvrRet = new RequestLoginSvrRet();
		requestLoginSvrRet.retcode = ErrorCode.SUCCESS;
		requestLoginSvrRet.gamesvrs=new RequestLoginSvrInfo[serverManager.servers.size()];
		int i=0;
		for(Server value:serverManager.servers.values())
		{
			RequestLoginSvrInfo request=new RequestLoginSvrInfo();
			request.ip=value.getExternalIP();
			request.port=value.getPort();
			request.zoneid=value.getzoneid();
			request.svrname=value.getSvrName();
			requestLoginSvrRet.gamesvrs[i]=request;
			i++;
		}

		Send(requestLoginSvrRet);
	}
	
	private void onLogin(CPacket packet) {

		if (packet.code != Code.ClientRegistLoginSvr) {
			LogUtil.error("第一个包非法");
			channel.disconnect();
		} else {
			valid=true;
		}
	}

	// 验证客户端有效性
	// private boolean checkValidity(CLLogin msg) {
	// if (msg.account.isEmpty()) {
	// return false;
	// }
	// String str = msg.account + LoginServer.appkey + msg.time;
	// str = Utility.md5_32(str);
	// if (str.equals(msg.encrpt)) {
	// version = msg.version;
	// valid = true;
	// return true;
	// }
	//
	// LCLoginRet ret = new LCLoginRet();
	// ret.account = msg.account;
	// ret.ret = ErrorCode.InvalidCode;
	// Send(ret);
	// LogAccountAct log = new LogAccountAct();
	// log.account = msg.account;
	// log.act = LogAccountAct.act_login;
	// log.result = ErrorCode.InvalidCode;
	// log.ip = getIp();
	// DBManager.saveGlobalLog(log);
	// return false;
	// }

	/**
	 * 处理用户登录，使用游戏自己的数据库
	 * 
	 * @param msg
	 */
	// private CompletableFuture<Object> checkAccountSelf(CLLogin msg) {
	// data = DBManager.getUniqu(AccountData.class, "account", msg.account,
	// DBType.GlobalDB);
	// // 没有账号自动创建一个。
	// if (data == null) {
	// data = new AccountData();
	// data.account = msg.account;
	// data.createtime = TimeCreator.GetTimeStamp();
	// DBManager.saveOrUpdate(data, DBType.GlobalDB);
	// LogUtil.debug("Create account:" + msg.account);
	// LogAccountAct log = new LogAccountAct();
	// log.account = msg.account;
	// log.ip = getIp();
	// log.act = LogAccountAct.act_create;
	// log.time = TimeCreator.GetTimeStamp();
	// DBManager.saveGlobalLog(log);
	// }
	//
	// LCLoginRet ret = new LCLoginRet();
	// ret.account = msg.account;
	// // 判断是否被ban
	// if (data.blocktime > TimeCreator.GetTimeStamp()) {
	// ret.ret = ErrorCode.Blocked;
	// Send(msg);
	// channel.disconnect();
	//
	// LogAccountAct log = new LogAccountAct();
	// log.account = msg.account;
	// log.ip = getIp();
	// log.act = LogAccountAct.act_login;
	// log.time = TimeCreator.GetTimeStamp();
	// log.result = ErrorCode.Blocked;
	// DBManager.saveGlobalLog(log);
	// return null;
	// }
	//
	// ret.ret = ErrorCode.SUCCESS;
	// Send(ret);
	// // 0表示在loginserver
	// accountManager.onAccountLogin(data, 0, getIp());
	// return null;
	// }
	//
	// /**
	// * 通过用户中心验证玩家账号密码登录
	// *
	// * @param msg
	// */
	// private void checkAccountWithUserCenter(CLLogin msg) {
	// data = DBManager.getUniqu(AccountData.class, "account", msg.account,
	// DBType.GlobalDB);
	// // 没有账号直接去平台验证
	// if (data == null) {
	// data = new AccountData();
	// data.account = msg.account;
	// AccountCheckInfo checkdata = new AccountCheckInfo(this, msg.password);
	// manager.AddCheckRequest(checkdata);
	// return;
	// }
	//
	// // 判断是否被ban
	// if (data.blocktime > TimeCreator.GetTimeStamp()) {
	// LCLoginRet ret = new LCLoginRet();
	// ret.account = msg.account;
	// ret.ret = ErrorCode.Blocked;
	// Send(msg);
	// channel.disconnect();
	//
	// LogAccountAct log = new LogAccountAct();
	// log.account = msg.account;
	// log.ip = getIp();
	// log.act = LogAccountAct.act_login;
	// log.time = TimeCreator.GetTimeStamp();
	// log.result = ErrorCode.Blocked;
	// DBManager.saveGlobalLog(log);
	// return;
	// }
	//
	// // 送去平台验证
	// AccountCheckInfo checkdata = new AccountCheckInfo(this, msg.password);
	// manager.AddCheckRequest(checkdata);
	// }
	//
	// public void onCheckReturn(AccountCheckInfo checkdata) {
	// LCLoginRet msg = new LCLoginRet();
	// msg.account = data.account;
	//
	// LogAccountAct log = new LogAccountAct();
	// log.account = data.account;
	// log.act = LogAccountAct.act_login;
	// log.ip = getIp();
	// log.time = TimeCreator.GetTimeStamp();
	//
	// if (checkdata.State == ClientManager.loginCheckResult_Success) {
	// /////////////// 验证成功放进去///////////////
	// msg.ret = ErrorCode.SUCCESS;
	// Send(msg);
	// accountManager.onAccountLogin(data, 0, getIp());
	// } else if (checkdata.State == ClientManager.loginCheckResult_NoAccount) {
	// msg.ret = ErrorCode.NoAccount;
	// Send(msg);
	// log.result = msg.ret;
	// DBManager.saveGlobalLog(log);
	// LogUtil.info("account [" + data.account + "] login falied for
	// noaccount");
	// checkWrongNum();
	// } else if (checkdata.State == ClientManager.loginCheckResult_WrongPwd) {
	// msg.ret = ErrorCode.WrongPwd;
	// Send(msg);
	// log.result = msg.ret;
	// DBManager.saveGlobalLog(log);
	// LogUtil.info("account [" + data.account + "] login falied for wrong
	// password");
	// checkWrongNum();
	// } else {
	// msg.ret = ErrorCode.FAILED;
	// Send(msg);
	// log.result = msg.ret;
	// DBManager.saveLog(log);
	// LogUtil.info("account [" + data.account + "] login falied for unknow
	// error[" + checkdata.State + "]");
	// checkWrongNum();
	// }
	//
	// }

	private void checkWrongNum() {
		wrongNum++;
		if (LoginServer.BanIPCount > 0 && wrongNum >= LoginServer.BanIPCount) {
			manager.AddBanIp(getIp());
			channel.disconnect();
		}
	}

	@Override
	protected void onConnect() {
		LogUtil.debug("client connect from " + getIp());
	}

	@Override
	protected void onDisconnect() {
		// if (data != null && data.id > 0) {
		// accountManager.onAccountLogout(data.id, 0);
		// }
	}

}
