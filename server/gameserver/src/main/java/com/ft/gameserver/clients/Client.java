package com.ft.gameserver.clients;

import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.ft.gameserver.GameServer;
import com.ft.gameserver.globalmods.PlayerManager;
import com.ft.gameserver.player.Player;

import common.ErrorCode;
import dbobjects.gamedb.AccountData;
import dbobjects.logdb.LogAccountAct;
import engine.common.TimeCreator;
import engine.db.DBManager;
import engine.db.client.DBType;
import engine.log.LogUtil;
import engine.net.BaseSession;
import engine.net.CPacket;
import engine.string.StringUtil;
import engine.util.Utility;
import generated.cgame.packets.Code;
import generated.cgame.packets.client.CSLogin;
import generated.cgame.packets.client.CSteamLogin;
import generated.cgame.packets.server.SCKickOut;
import generated.cgame.packets.server.SCLoginRet;
import generated.cgame.packets.server.SCPong;

@Controller
@Scope("prototype")
public class Client extends BaseSession {

	@Autowired
	ClientManager clientManager;
	@Autowired
	PlayerManager playerManager;

	public AccountData accountData;
	public Player player;
	public String version; // 客户端版本号

	int connecttime; // 开始连接的时间

	int lastPingTime;

	static SCPong pong = new SCPong();

	// 平台验证时候的返回
	static final short loginCheckResult_Success = 1;
	static final short loginCheckResult_NoAccount = -1;
	static final short loginCheckResult_WrongPwd = -2;

	@Override
	public boolean processMsg(CPacket packet) {
		if (packet.code != Code.CSPing) {
			// LogUtil.debug(packet.code);
		}
		if (packet.code == Code.VMsgConnect) {
			onConnect();
			return true;
		} else if (packet.code == Code.VMsgDisconnect) {
			onDisconnect();
			return true;
		} else if (accountData == null) {
			checkLogin(packet);
			return true;
		} else if (packet.code == Code.CSPing) {
			onPing();
			return true;
		}
		return false;
	}

	private void onPing() {
		lastPingTime = TimeCreator.GetTimeStamp();
		Send(pong);
	}

	protected void onConnect() {
		connecttime = TimeCreator.GetTimeStamp();
		LogUtil.debug("client connect from " + getIp());
	}

	protected void onDisconnect() {
		LogUtil.debug("client disconnected from " + getIp());
		if (accountData == null) {
			return;
		}
		saveLog(LogAccountAct.act_logout, ErrorCode.SUCCESS);
		clientManager.onPlayerLogout(accountData.id);
		if (player != null) {
			playerManager.offline(player);
		}
	}

	/**
	 * 登录验证
	 * 
	 * @param packet
	 */
	private void checkLogin(CPacket packet) {
		if (!GameServer.isok) {
			SCLoginRet ret = new SCLoginRet();
			ret.ret = ErrorCode.NoLogin;
			Send(ret);
			return;
		}

		// 收到的不是login包，踢掉
		if (packet.code != Code.CSLogin && packet.code != Code.CSteamLogin) {
			saveLog(LogAccountAct.act_login, ErrorCode.InvalidPacket);
			disConnect();
			LogUtil.debug("login， not login or steamlogin packet." + packet.code);
			return;
		}

		// 1.数据库账号登录
		if (packet.code == Code.CSLogin) {
			CSLogin msg = (CSLogin) packet;
			if (!checkValidity(msg)) {
				saveLog(LogAccountAct.act_login, ErrorCode.InvalidCode, msg.account);
				disConnect();
				LogUtil.debug("login， invalidcode");
				return;
			}

			SCLoginRet ret = new SCLoginRet();
			ret.account = msg.account;

			// if (accountData == null) {
			// accountData = new AccountData();
			// accountData.account = msg.account;
			// accountData.createtime = TimeCreator.GetTimeStamp();
			// }

			// if (accountData.blocktime > 0 && TimeCreator.GetTimeStamp() >
			// accountData.blocktime) {
			// ret.ret = ErrorCode.Blocked;
			// }
			AccountData data = DBManager.getUniqu(AccountData.class, "account", msg.account, DBType.GameDB);

			if (GameServer.selfaccount) {

				if (data == null) {
					ret.ret = ErrorCode.NoAccount;
					Send(ret);
				} else if (!StringUtils.equals(data.passwd, msg.password)) {
					ret.ret = ErrorCode.WrongPwd;
					Send(ret);
				} else if (data.blocktime > TimeCreator.GetTimeStamp()) {// 账号封停(已过封停时间,下次登录时自动解封)
					ret.ret = ErrorCode.AccountBlocked;
					Send(ret);
				} else {
					this.accountData = data;
					ret.ret = ErrorCode.SUCCESS;
					Send(ret);
					onAccountLoginSuccess();
				}

				// DBManager.saveOrUpdate(accountData, DBType.GameDB);

				// saveLog(LogAccountAct.act_login, ErrorCode.SUCCESS);

			} else {

				if (accountData == null) {
					accountData = new AccountData();
					accountData.account = msg.account;
				}

				clientManager.checkLoginToUserCenter(this, msg.account, msg.password);
			}
		}

		// 2.steam 账号登录
		if (packet.code == Code.CSteamLogin) {
			CSteamLogin steamlogin = (CSteamLogin) packet;

			accountData = DBManager.getUniqu(AccountData.class, "id", steamlogin.steamid, DBType.GameDB);
			if (accountData != null && accountData.blocktime > TimeCreator.GetTimeStamp()) {// 账号封停(已过封停时间,下次登录时自动解封)
				SCLoginRet ret = new SCLoginRet();
				ret.account = String.valueOf(steamlogin.steamid);
				ret.ret = ErrorCode.AccountBlocked;
				Send(ret);
				return;
			}

			if (!CheckSteamEncry(steamlogin)) {
				saveLog(LogAccountAct.act_login_steam, ErrorCode.InvalidCode, steamlogin.ticket);
				disConnect();
				LogUtil.debug("login steam invalidcode");
				return;
			} else {
				clientManager.CheckSteamLogOnTicket(this, steamlogin);
			}

		}
	}

	/**
	 * 平台响应返回
	 * 
	 * @param content
	 */
	public void onAccountCheckReturn(String content) {
		LogUtil.debug("onAccountCheckReturn: " + content);
		SCLoginRet msg = new SCLoginRet();
		msg.account = accountData.account;
		msg.code = ErrorCode.FAILED;
		if (content != null && !content.isEmpty()) {
			content = content.replace("{", "");
			content = content.replace("}", "");
			content = content.replaceAll("\"", "");
			Map<String, String> result = StringUtil.split(content, ",", ":");
			short state = Short.parseShort(result.get("status"));
			if (state == loginCheckResult_Success) {
				long accountid = Long.parseLong(result.get("userid"));
				if (accountData.id == 0) {
					accountData.id = accountid;
					accountData.createtime = TimeCreator.GetTimeStamp();
					saveLog(LogAccountAct.act_createfromusercenter, ErrorCode.SUCCESS);
				} else if (accountData.id != accountid) {
					// 两次id不一样
					LogUtil.warn("check login from usercenter get unmatched id [" + accountData.account + "][" + accountData.id + "][" + accountid + "]");
					accountData.id = accountid;
				}
				msg.ret = ErrorCode.SUCCESS;
			} else if (state == loginCheckResult_NoAccount) {
				msg.ret = ErrorCode.NoAccount;
			} else if (state == loginCheckResult_WrongPwd) {
				msg.ret = ErrorCode.WrongPwd;
			}
		}
		Send(msg);
		saveLog(LogAccountAct.act_login, msg.ret);

		if (msg.ret == ErrorCode.SUCCESS) {
			onAccountLoginSuccess();
		}

	}

	private boolean CheckSteamEncry(CSteamLogin msg) {
		String str = msg.ticket + GameServer.appkey;
		str = Utility.md5_32(str);
		str.toLowerCase();
		if (str.equals(msg.encrpt)) {
			version = msg.version;
			return true;
		}

		SCLoginRet ret = new SCLoginRet();
		ret.account = msg.ticket;
		ret.ret = ErrorCode.InvalidCode;
		Send(ret);

		LogAccountAct log = new LogAccountAct();
		log.account = msg.ticket;
		log.act = LogAccountAct.act_login_steam;
		log.result = ErrorCode.InvalidCode;
		log.ip = getIp();
		DBManager.saveLog(log);
		return false;
	}

	/**
	 * 验证客户端有效性
	 * 
	 * @param msg
	 * @return
	 */
	private boolean checkValidity(CSLogin msg) {
		if (msg.account.isEmpty()) {
			return false;
		}
		String str = msg.account + GameServer.appkey + msg.time;
		str = Utility.md5_32(str);
		str.toLowerCase();
		if (str.equals(msg.encrpt)) {
			version = msg.version;
			return true;
		}

		SCLoginRet ret = new SCLoginRet();
		ret.account = msg.account;
		ret.ret = ErrorCode.InvalidCode;
		Send(ret);

		LogAccountAct log = new LogAccountAct();
		log.account = msg.account;
		log.act = LogAccountAct.act_login;
		log.result = ErrorCode.InvalidCode;
		log.ip = getIp();
		DBManager.saveLog(log);
		return false;
	}

	/**
	 * 主动断开
	 */
	public void disConnect() {
		channel.disconnect();
	}

	public void setPlayer(Player p) {
		p.client = this;
		this.player = p;
		p.account = accountData.account;
	}

	public void update() {
		// 超时踢线，暂时去除
		// if (lastPingTime > 0 && ((TimeCreator.GetTimeStamp() - lastPingTime)
		// > Constant.timeOutTime)) {
		// LogUtil.debug("client ping timeout");
		// disConnect();
		// }
	}

	private void saveLog(short act, short result) {
		saveLog(act, result, null);
	}

	private void saveLog(short act, short result, String account) {
		LogAccountAct log = new LogAccountAct();
		if (accountData != null) {
			log.accountid = accountData.id;
			log.account = accountData.account;
		} else if (account != null) {
			log.account = account;
		}
		log.act = act;
		log.serverid = GameServer.serverid;
		log.ip = getIp();
		log.result = result;
		DBManager.saveLog(log);
	}

	public void onAccountLoginSuccess() {
		// 踢线
		Player onLinePlayer = playerManager.getPlayerByAccountId(accountData.id);
		if (onLinePlayer != null) {
			playerManager.offline(onLinePlayer);
			SCKickOut msg = new SCKickOut();
			onLinePlayer.Send(msg);
			onLinePlayer.client.disConnect();

		}

		accountData.lastlogintime = TimeCreator.GetTimeStamp();
		accountData.blocktime = 0;// 过了封停时间之后，再登陆时自动解封
		DBManager.saveOrUpdate(accountData, DBType.GameDB);
		playerManager.doLogin(this);
	}

}
