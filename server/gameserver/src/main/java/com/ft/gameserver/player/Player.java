package com.ft.gameserver.player;

import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.ft.csv.resources.ResourceManager;
import com.ft.csv.resources.item.GameExchange;
import com.ft.gameserver.basemod.ModManager;
import com.ft.gameserver.basemod.SourceManager;
import com.ft.gameserver.clients.Client;
import com.ft.gameserver.playermod.pack.PackMod;
import com.ft.gameserver.servers.Server;
import com.ft.gameserver.servers.ServerManager;
import com.ft.gameserver.utility.IdCreator;

import dbobjects.gamedb.PlayerClientSetting;
import dbobjects.gamedb.PlayerData;
import dbobjects.gamedb.ShortcutTable;
import dbobjects.gamedb.TankModelTable;
import dbobjects.logdb.LogMoney;
import engine.common.TimeCreator;
import engine.db.DBManager;
import engine.db.client.Condition;
import engine.db.client.DBType;
import engine.net.CPacket;
import engine.util.DateUtils;
import generated.cgame.packets.client.CSPlayerClientSetting;
import generated.cgame.packets.client.CSPlayerPCinfo;
import generated.cgame.packets.objects.FightLoadingRef;
import generated.cgame.packets.objects.Shortcut;
import generated.cgame.packets.objects.TankFightInfo;
import generated.cgame.packets.server.SCPlayerBaseData;
import generated.cgame.packets.server.SCPlayerLoginData;
import generated.cgame.packets.server.SCShortcut;
import generated.cgame.packets.server.SCTankFightLockInfo;

@Component
@Scope("prototype")
public class Player {

	@Autowired
	public SourceManager source;
	@Autowired
	IdCreator idCreator;
	@Autowired
	ResourceManager configs;
	@Autowired
	ServerManager servers;
	@Autowired
	PackMod packages;
	@Autowired
	ModManager mods;

	public static final short STATUS_ONLINE = 0;
	public static final short STATUS_OFFLINE = 1;
	public static final short STATUS_GAMING = 2;
	public static final short STATUS_INTEAM = 3;

	public void SendFightLock(TankModelTable tank) {
		SCTankFightLockInfo ret = new SCTankFightLockInfo();
		ret.tankid = tank.modelid;
		ret.secs = tank.FightLockSecs();
		Send(ret);
	}

	public void InitPlayer(long id) {
		data.id = idCreator.GetId64();
		data.money = configs.roleinitconfig.Silver;
		data.gold = configs.roleinitconfig.Gold;
		data.level = 1;
		data.account = id;
		data.createTime = TimeCreator.GetTimeStamp();
	}

	public void onClientReady() {
		sendLoginData();
		sendBaseData();
	}

	/*
	 * 如果有未结束的战场，再次进入
	 */
	public boolean directEnterBattleground() {
		if (data.fightserver > 0) {
			Server server = servers.getServer(data.fightserver);
			if (server != null && !server.playerisdead(getId())) {
				server.callPlayerReEnter(this);
				return true;
			} else {
				data.fightserver = 0;
			}
		}
		return false;

	}

	public class CTankSort {
		int tankid;
		int usecount;
	}

	public long getId() {
		return data.id;
	}

	public long getAccountId() {
		return data.account;
	}

	public String getAccount() {
		return account;
		// return client.accountData.account;
	}

	public String getName() {
		return data.nickname;
	}

	public int getLevel() {
		return data.level;
	}

	public long getTeamId() {
		return data.teamid;
	}

	public void Send(CPacket packet) {
		if (client != null) {
			client.Send(packet);
		}
		packet = null;
	}

	public void autosave() {
		source.Save(getId());
		Save();
		saveShortcuts();

		autosavetime = TimeCreator.GetTimeStamp();
	}

	public void Offline() {
		source.Save(getId());
		data.lastLogoutTime = TimeCreator.GetTimeStamp();
		data.playtimesecs += data.lastLogoutTime - data.lastLoginTime;// 玩家游戏时间
		data.status = STATUS_OFFLINE;
		Save();
		saveShortcuts();// 保存快捷鍵
	}

	/**
	 * 加载玩家数据
	 */
	public void Online() {
		source.Load(getId());
		data.lastLoginTime = TimeCreator.GetTimeStamp();
		data.status = data.teamid != 0 ? STATUS_INTEAM : STATUS_ONLINE;
		Save();
		long tm = 1000L * data.lastLogoutTime;
		Date date = new Date(tm);
		if (!DateUtils.isToday(date)) {
			mods.onDayChanged(this);
		}
		sendShortcuts();// 推送快捷鍵

		autosavetime = TimeCreator.GetTimeStamp();
	}

	public void setStatus(short status) {
		data.status = status;
	}

	private void sendShortcuts() {
		List<ShortcutTable> list = DBManager.get(ShortcutTable.class, DBType.GameDB, Condition.eq("playerid", getId()));
		SCShortcut msg = new SCShortcut();
		msg.shortcuts = new Shortcut[list.size()];
		shortcutsids = new long[list.size()];
		int i = 0;
		for (ShortcutTable o : list) {
			Shortcut d = new Shortcut();
			d.keytype = o.keytype;
			d.value = o.value;
			int index = i++;
			msg.shortcuts[index] = d;
			shortcutsids[index] = o.id;
			shortcuts.put(o.id, o);
		}
		Send(msg);
	}

	private void saveShortcuts() {
		DBManager.delete(ShortcutTable.class, shortcutsids, DBType.GameDB);
		DBManager.saveOrUpdate(new HashSet<>(shortcuts.values()), DBType.GameDB);
	}

	public void updShortcuts(Shortcut[] shortcutsArr) {
		shortcuts.clear();
		for (Shortcut o : shortcutsArr) {
			ShortcutTable data = new ShortcutTable();
			long id = idCreator.GetId64();
			data.id = id;
			data.playerid = getId();
			data.keytype = o.keytype;
			data.value = o.value;
			shortcuts.put(id, data);
		}
	}

	/*
	 * 发送登录时候需要的数据
	 */
	private void sendLoginData() {
		SCPlayerLoginData msg = new SCPlayerLoginData();
		msg.rolename = data.nickname;
		msg.roleid = data.id;
		msg.Exp = data.exp;
		msg.level = data.level;
		msg.money = data.money;
		msg.ingot = data.gold;
		msg.teamid = data.teamid;
		Send(msg);
	}

	/*
	 * 发送玩家基本数据，用于后期更新时候使用
	 */
	public void sendBaseData() {
		SCPlayerBaseData msg = new SCPlayerBaseData();
		msg.Exp = data.exp;
		msg.level = data.level;
		msg.money = data.money;
		msg.ingot = data.gold;
		msg.teamid = data.teamid;
		msg.givetimesPrice = getGameExchange().Price;
		msg.boxid = configs.propconfig.get(getGameExchange().GamePropID).ID_GameBox;
		Send(msg);
	}

	private GameExchange getGameExchange() {
		int n = 1 + data.givetimes;// 第幾次贈送
		GameExchange exchangeconfig = configs.exchangeconfig.get(n) == null ? configs.exchangeconfig.get(configs.exchangeconfig.size()) : configs.exchangeconfig.get(n);// n超过了规则个数，取最后一个
		return exchangeconfig;
	}

	public void setData(PlayerData data) {
		this.data = data;
	}

	public void Save() {
		DBManager.saveOrUpdate(data, DBType.GameDB);

	}

	public void SaveMoneyLog(int changetype, int changevalue, int reason) {

		LogMoney log = new LogMoney();
		log.roleid = getId();
		log.changetype = changetype;
		log.changevalue = changevalue;
		log.reason = reason;

		DBManager.saveLog(log);

	}

	public int money() {
		return data.money;
	}

	public void addExp(int value) {

	}

	public void disConnect() {
		if (client != null) {
			client.disConnect();
		}
	}

	/**
	 * 发送缓存消息
	 */
	public void sendCacheMsgs() {
		for (CPacket msg : cacheMsgs) {
			Send(msg);
		}
		cacheMsgs.clear();
	}

	/*
	 * 发送给客户段的数据，用于预加载的
	 */
	public void packPerFightInfo(FightLoadingRef info) {
		info.roleid = getId();
		info.rolelvl = getLevel();
		info.rolename = getName();
		info.tankid = getCurTank();
	}

	/*
	 * 发送给fightserver的玩家数据
	 */
	public void packFighterInfo(TankFightInfo info) {
		info.roleid = getId();
		info.rolename = getName();
		info.tankid = getCurTank();
		info.decalid = packages.getUseItems(this);
		info.teamid = teamid32;
	}

	/*
	 * 获取当前使用的tank 现在写死，以后会使用tank模块
	 */
	public int getCurTank() {
		return 1012;
	}

	/*
	 * 玩家开始战斗
	 */
	public void onPlayerStartFight(long id) {
		data.fightserver = id;
	}

	/*
	 * s 玩家结束战斗
	 */
	public void onPlayerEndFight() {
		data.fightserver = 0;
	}

	/**
	 * 玩家客户端设置
	 * 
	 * @param msg
	 */
	public void updPlayerClientSetting(CSPlayerClientSetting msg) {
		PlayerClientSetting data = DBManager.getUniqu(PlayerClientSetting.class, "playerid", getId(), DBType.GameDB);
		if (data == null) {
			data = new PlayerClientSetting();
			data.playerid = getId();
		}
		data.WindowMode = msg.setting[0];
		data.Resolution = msg.setting[1];
		data.VSync = msg.setting[2];
		data.Language = msg.setting[3];
		data.OverallQuality = msg.setting[4];
		data.ViewDistance = msg.setting[5];
		data.AntiAliasing = msg.setting[6];
		data.PostProcessing = msg.setting[7];
		data.Shadow = msg.setting[8];
		data.Texture = msg.setting[9];
		data.VisualEffect = msg.setting[10];
		data.Foliage = msg.setting[11];
		data.ResolutionScalability = msg.setting[12];
		DBManager.saveOrUpdate(data, DBType.GameDB);
	}

	/**
	 * 玩家PC信息
	 * 
	 * @param msg
	 */
	public void updPlayerPCinfo(CSPlayerPCinfo msg) {
		PlayerClientSetting data = DBManager.getUniqu(PlayerClientSetting.class, "playerid", getId(), DBType.GameDB);
		if (data == null) {
			data = new PlayerClientSetting();
			data.playerid = getId();
		}
		data.cpu = msg.cpu;
		data.gpu = msg.gpu;
		data.mem = msg.mem;
		data.gmem = msg.gmem;
		DBManager.saveOrUpdate(data, DBType.GameDB);
	}

	public Client client;
	public PlayerData data = new PlayerData();

	public String account;

	// 快捷键
	private Map<Long, ShortcutTable> shortcuts = new HashMap<Long, ShortcutTable>();
	private long[] shortcutsids;

	/************************** 下面都是临时数据 *******************************/

	public List<CPacket> cacheMsgs = new ArrayList<>();

	CTankSort[] sortdata = new CTankSort[5];

	// 是否允許被邀請的開關（在綫用戶使用）
	public short isinvite;

	// 记录玩家自动保存的时间
	public int autosavetime;

	// 玩家匹配类型
	public short matchtype;

	// 用于进入战斗
	public int teamid32;

	public short matchstatus;// 組隊成員使用
	public static final short MATCHSTATUS_OFF = 0;// 沒有點擊匹配
	public static final short MATCHSTATUS_ON = 1;// 點擊了匹配

}
