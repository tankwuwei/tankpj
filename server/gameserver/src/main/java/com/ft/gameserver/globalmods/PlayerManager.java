package com.ft.gameserver.globalmods;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.annotation.PostConstruct;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.ft.csv.resources.ResourceManager;
import com.ft.gameserver.GameServer;
import com.ft.gameserver.basemod.GlobalBaseMod;
import com.ft.gameserver.basemod.ModManager;
import com.ft.gameserver.clients.Client;
import com.ft.gameserver.player.Player;
import com.ft.gameserver.playermod.fight.FightStatiticMod;

import common.ErrorCode;
import dbobjects.gamedb.AccountData;
import dbobjects.gamedb.NickNameTable;
import dbobjects.gamedb.PlayerData;
import dbobjects.logdb.LogPlayerAct;
import engine.bean.BeanFactory;
import engine.common.TimeCreator;
import engine.db.DBHandler;
import engine.db.DBManager;
import engine.db.client.DBType;
import engine.log.LogUtil;
import engine.net.CPacket;
import engine.string.MessageFilter;
import generated.cgame.packets.Code;
import generated.cgame.packets.client.CSPlayerClientSetting;
import generated.cgame.packets.client.CSPlayerPCinfo;
import generated.cgame.packets.client.CSShortcut;
import generated.cgame.packets.client.SetNickName;
import generated.cgame.packets.server.SCKickOut;
import generated.cgame.packets.server.SCServerShutdown;
import generated.cgame.packets.server.SetNickNameRet;
import generated.cgame.packets.server.ShouldReadyInfo;

@Controller
public class PlayerManager extends GlobalBaseMod implements DBHandler {
	@Autowired
	ModManager mods;
	@Autowired
	ResourceManager configs;
	@Autowired
	FightStatiticMod FightStatiticMod;

	// 所有在线的玩家数据
	// < roleid, player>
	Map<Long, Player> allplays = new ConcurrentHashMap<>();
	Map<Long, Player> allAccounts = new ConcurrentHashMap<>();

	protected void handle(CPacket packet) {
		Client client = (Client) packet.o;
		switch (packet.code) {
		case Code.CSlientReady:
			onClientReady(client.player);
			break;
		// case Code.CCreate:
		// doCreate(client, (CCreate) packet);
		// break;
		// case Code.CRoleLogin:
		// doRoleLogin(client, (CRoleLogin) packet);
		// break;
		// case Code.CLogout:
		// doLogout(client);
		case Code.SetNickName:
			onSetNickName(client.player, packet);
			break;
		case Code.CSShortcut:
			client.player.updShortcuts(((CSShortcut) packet).shortcuts);
			break;
		case Code.CSPlayerClientSetting:
			client.player.updPlayerClientSetting((CSPlayerClientSetting) packet);
			break;
		case Code.CSPlayerPCinfo:
			client.player.updPlayerPCinfo((CSPlayerPCinfo) packet);
			break;
		case Code.CSPlayerLobbyFromFight: // 战斗结束返回大厅
			client.player.setStatus(Player.STATUS_ONLINE);
			break;
		default:
			break;
		}
	}

	private void doLogout(Client client) {
		client.disConnect();
	}

	@PostConstruct
	private void regist() {
		DBManager.regist(this);
	}

	@Override
	public void initDB() {
		if (!GameServer.createTestAccount) {
			return;
		}
		AccountData accountData = DBManager.getUniqu(AccountData.class, "account", "test1", DBType.GameDB);
		if (accountData != null) {
			return;
		}
		long time = System.nanoTime();
		for (int i = 0; i < GameServer.testaccountcount; i++) {
			createRobotAccount("test" + i);
		}
		LogUtil.system("init test account:" + (System.nanoTime() - time));
	}

	@Override
	public void atfterInitDB() {
		// TODO Auto-generated method stub

	}

	private void createRobotAccount(String account) {
		AccountData accountData = new AccountData();
		accountData.account = account;
		accountData.passwd = "123456";
		accountData.createtime = TimeCreator.GetTimeStamp();
		DBManager.saveOrUpdate(accountData, DBType.GameDB);
		// PlayerData data = new PlayerData();
		// data.account = accountData.id;
		// data.createTime = TimeCreator.GetTimeStamp();
		// data.level = 1;
		// data.money = 999999;
		// DBManager.saveOrUpdate(data, DBType.GameDB);
	}

	/**
	 * 加载角色数据进入游戏
	 * 
	 * @param client
	 */
	public void doLogin(Client client) {

		long accountid = client.accountData.id;
		PlayerData data = DBManager.getUniqu(PlayerData.class, "account", accountid, DBType.GameDB);

		boolean bCreate = false;

		Player player = BeanFactory.getBean(Player.class);
		player.client = client;
		client.setPlayer(player);

		if (data == null) {
			player.InitPlayer(accountid);
			player.Save();
			// data = doCreate(client);
			bCreate = true;
		} else {
			player.data = data;
		}

		if (player.directEnterBattleground()) {
			online(player);
			return;
		}
		// 如果玩家没有昵称 要求设置昵称
		ShouldReadyInfo readyinfo = new ShouldReadyInfo();

		if (player.data.nickname == null || player.data.nickname.isEmpty()) {
			readyinfo.bsetnick = 1;

		} else {
			readyinfo.bsetnick = 0;
		}
		client.Send(readyinfo);

		if (bCreate) {
			mods.onCreatePlayer(player);
		}

		online(player);
	}

	public void offline(Player player) {
		if (player == null) {
			return;
		}
		mods.OnPlayerLogout(player);
		player.Offline();
		LogUtil.info("player [" + player.getAccount() + "][" + player.getAccountId() + "][" + player.getId() + "] logout.");
		allplays.remove(player.getId());
		allAccounts.remove(player.getAccountId());
		savePlayerLog(player.client, player.data, LogPlayerAct.act_logout, ErrorCode.SUCCESS);
		player.client.accountData = null;
		player = null;
	}

	private void online(Player player) {
		if (player == null) {
			return;
		}
		player.Online();
		allplays.put(player.getId(), player);
		allAccounts.put(player.getAccountId(), player);

		LogUtil.info("player [" + player.getAccount() + "][" + player.getAccountId() + "][" + player.getId() + "] login success.");
		savePlayerLog(player.client, player.data, LogPlayerAct.act_login, ErrorCode.SUCCESS);
	}

	private void onClientReady(Player player) {
		player.onClientReady();
		mods.OnPlayerLogin(player);
	}

	private void onSetNickName(Player player, CPacket packet) {
		SetNickName setpacket = (SetNickName) packet;
		SetNickNameRet setpacketret = new SetNickNameRet();

		if (MessageFilter.isValid(setpacket.nickname.toLowerCase()) == false) {
			setpacketret.ret = ErrorCode.NickNameIllegitmacy;
		} else {
			NickNameTable data = DBManager.getUniqu(NickNameTable.class, "NickName", setpacket.nickname, DBType.GameDB);

			if (data == null) {

				data = new NickNameTable();
				data.NickName = setpacket.nickname;
				DBManager.saveOrUpdate(data, DBType.GameDB);

				player.data.nickname = setpacket.nickname;
				setpacketret.ret = ErrorCode.SUCCESS;
				player.Save();
				LogUtil.debug("set nicknmae " + setpacket.nickname);

				FightStatiticMod.setNickname_PlayerFightStatitic(player);
			} else {
				setpacketret.ret = ErrorCode.NickNameRepeat;
				LogUtil.debug("set nicknmae repeat:" + setpacket.nickname);
			}
		}

		player.client.Send(setpacketret);

	}

	public void Update() {
		for (Player player : allplays.values()) {
			mods.UpdateBaseMods(player);
			autosave(player);
		}
	}

	private void autosave(Player player) {
		if (GameServer.timeautosave + player.autosavetime < TimeCreator.GetTimeStamp())
			player.autosave();
	}

	public int GetPlayerCount() {
		return allplays.size();
	}

	public Player GetPlayerById(long id) {
		return allplays.get(id);
	}

	public Player getPlayerByAccountId(long id) {
		return allAccounts.get(id);
	}

	private void savePlayerLog(Client client, PlayerData player, short act, short result) {
		LogPlayerAct log = new LogPlayerAct();
		log.accountid = client.accountData.id;
		log.account = client.accountData.account;
		log.roleid = player.id;
		log.act = act;
		log.ip = client.getIp();
		log.result = result;
		if (act == LogPlayerAct.act_logout) {
			log.timesecs = client.player.data.playtimesecs;
		}

		DBManager.saveLog(log);
	}

	@Override
	public void onPlayerLogin(Player player) {

	}

	@Override
	public void onPlayerLogout(Player player) {
	}

	@Override
	public void onDayChanged() {
		for (Player player : allplays.values()) {
			mods.onDayChangedBaseMods(player);
		}

	}

	// 账号封停、解封
	public void accountblock(String ids, int blocktime) {
		for (String id : ids.split(",")) {
			AccountData accountData = DBManager.getUniqu(AccountData.class, "id", Long.parseLong(id), DBType.GameDB);
			accountData.blocktime = blocktime;
			DBManager.saveOrUpdate(accountData, DBType.GameDB);
			if (blocktime > 0)
				kickout(Long.parseLong(id));
		}
	}

	// 在线的踢掉
	public void kickout(long id) {
		Player player = getPlayerByAccountId(id);
		if (player != null) {
			offline(player);
			SCKickOut msg = new SCKickOut();
			player.Send(msg);
			player.client.disConnect();
		}
	}

	// 在线的踢掉
	public void kickout() {
		for (Player player : allplays.values()) {
			if (player != null) {
				offline(player);
				SCKickOut msg = new SCKickOut();
				player.Send(msg);
				player.client.disConnect();
			}
		}
	}

	// 通知在线玩家服务器即将关闭
	public void noticeClientServerShutdown(int time) {
		SCServerShutdown msg = new SCServerShutdown();
		msg.time = time;
		for (Player player : allplays.values())
			if (player != null)
				player.Send(msg);
	}

	public Player getOnlinePlayer(String nickname) {
		for (Player player : allplays.values())
			if (StringUtils.equals(player.getName(), nickname))
				return player;
		return null;
	}
}
