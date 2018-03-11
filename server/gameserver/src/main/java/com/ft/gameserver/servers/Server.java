package com.ft.gameserver.servers;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.ft.csv.resources.ResourceManager;
import com.ft.gameserver.GameServer;
import com.ft.gameserver.globalmods.DistributeRewardMod;
import com.ft.gameserver.globalmods.MatchManager;
import com.ft.gameserver.globalmods.PlayerManager;
import com.ft.gameserver.globalmods.PlayerRankMod;
import com.ft.gameserver.player.Player;
import com.ft.gameserver.playermod.fight.FightStatiticMod;
import com.ft.gameserver.utility.IdCreator;

import common.ErrorCode;
import dbobjects.gamedb.PlayerFightStatiticTable;
import dbobjects.logdb.LogBattle;
import engine.common.TimeCreator;
import engine.db.DBManager;
import engine.db.client.DBType;
import engine.log.LogUtil;
import engine.net.BaseSession;
import engine.net.CPacket;
import engine.util.Utility;
import generated.cgame.packets.Code;
import generated.cgame.packets.client.UGPlayerDead;
import generated.cgame.packets.client.UGPlayerJoinFight;
import generated.cgame.packets.client.UGPlayerLeave;
import generated.cgame.packets.client.UGState;
import generated.cgame.packets.client.UGUserLoginRet;
import generated.cgame.packets.client.UGlogin;
import generated.cgame.packets.client.USStartFightRet;
import generated.cgame.packets.objects.FightLoadingRef;
import generated.cgame.packets.objects.FightRankInfo;
import generated.cgame.packets.server.GULoginRet;
import generated.cgame.packets.server.SCEnterFighter;
import generated.cgame.packets.server.SCFightRankInfo;
import generated.cgame.packets.server.SCSendFightLoadingInfo;
import generated.cgame.packets.server.SCSendFightToClient;

/****
 * 地图服务器实例**
 * 
 * @author cxz
 **/
@Controller
@Scope("prototype")
public class Server extends BaseSession {

	@Autowired
	PlayerManager playerManager;
	@Autowired
	IdCreator idCreator;
	@Autowired
	ServerManager serverManager;
	@Autowired
	MatchManager matchManager;

	@Autowired
	ResourceManager config;

	@Autowired
	FightStatiticMod fightStatiticMod;
	@Autowired
	PlayerRankMod playerRankMod;
	@Autowired
	DistributeRewardMod distributeRewardMod;

	public static final String strBattleMode_Unknow = "UNKNOWN";
	public static final String strBattleMode_PVE = "PVE";
	public static final String strBattleMode_DeathMath = "ProjectBlueGameMode_FreeForAll";
	public static final String strBattleMode_TeamBattle = "Team";

	public static final short StateReady = 1;
	public static final short StateFighting = 2;

	private String battleMode = ""; // 此服务器开启的战斗模块
	private String mapname;
	private String version;
	private int StartTime; // 此服务器开启时间
	private String hostname; // fightserver所在机器名

	private String externalip; // 对外的ip
	private short port; // 对外的端口
	private short minplayer;// 最少的开战人数
	private short maxplayer; // 可进入的最大玩家数

	private long serverid; // 给每一个server的编号
	private int zoneid;

	private int fightnumber = 0;// 关卡id
	private int agentindex = 0;// 属于哪个agent管理

	private Set<Long> notIn = new HashSet<>(); // 请求进入的玩家
	private Set<Long> players = new HashSet<>(); // 已经进入的玩家

	public short fightstate;// 1 open 2 close

	public boolean bOpen() {
		return fightstate == StateReady;
	}

	public int getagentindex() {
		return agentindex;
	}

	public boolean bClose() {
		return fightstate == 2;
	}

	public int getfightnumber() {
		return fightnumber;
	}

	public int getZoneid() {
		return zoneid;
	}

	public void setZoneid(int zoneid) {
		this.zoneid = zoneid;
	}

	public boolean processMsg(CPacket packet) {
		switch (packet.code) {
		case Code.VMsgConnect:
			onConnect();
			break;
		case Code.VMsgDisconnect:
			onDisconnect();
			break;
		case Code.UGlogin:
			onServerLogin((UGlogin) packet);
			break;
		case Code.UGUserLoginRet:
			onPlayerRequireEnterReturn((UGUserLoginRet) packet);
			break;
		case Code.UGState:// 战斗服务器状态
			UGState state = (UGState) packet;
			fightstate = state.state;
			serverManager.OnStateChange(this);
			break;
		case Code.USStartFightRet:
			USStartFightRet fightret = (USStartFightRet) packet;
			if (fightret.ret == ErrorCode.SUCCESS) {
				callPlayerIntoFightServer();
				joinfightplayers += fightret.aicount;// 加上机器人数量
			} else {
				LogUtil.error("fight svr start fight ret  not success  id: " + serverid);
			}
			break;
		case Code.UGPlayerJoinFight:// 有玩家加入战斗
			playerjoinfight(((UGPlayerJoinFight) packet).playerid);
			break;
		case Code.UGPlayerDead:// 玩家死亡
			playerdead((UGPlayerDead) packet);
			break;
		case Code.UGPlayerLeave: // 玩家退出
			onPlayerLeave(((UGPlayerLeave) packet).playerid);
			break;
		default:
			break;
		}
		return true;
	}

	/*
	 * 玩家退出
	 */
	private void onPlayerLeave(long roleid) {
		if (rankinfo.containsKey(roleid)) {
			playerLeaveSet.add(roleid);
			Player player = playerManager.GetPlayerById(roleid);
			player.onPlayerEndFight();
		}
	}

	/**
	 * 这场战斗，玩家是否死亡
	 * 
	 * @param playerid
	 * @return true:已死亡
	 */
	public boolean playerisdead(long playerid) {
		return rankinfo.get(playerid).rank > 0;
	}

	/*
	 * 拉玩家重新进入战场
	 */
	public void callPlayerReEnter(Player player) {
		SCSendFightLoadingInfo infoMsg = new SCSendFightLoadingInfo();
		infoMsg.fightnumber = GameServer.fightServerLevel;
		// 加入玩家自己的id
		infoMsg.rolesinfo = new FightLoadingRef[1];
		FightLoadingRef info = new FightLoadingRef();
		info.roleid = player.getId();
		info.rolename = player.getName();
		info.tankid = player.getCurTank();
		infoMsg.rolesinfo[0] = info;

		SCSendFightToClient msg = new SCSendFightToClient();
		msg.ip = getExternalIp();
		msg.port = getPort();
		player.Send(infoMsg);
		player.Send(msg);
	}

	/*
	 * 发送战斗服务器信息给客户端，通知客户端进入。
	 */
	private void callPlayerIntoFightServer() {

		SCSendFightLoadingInfo infoMsg = new SCSendFightLoadingInfo();
		infoMsg.fightnumber = GameServer.fightServerLevel;
		SCSendFightToClient msg = new SCSendFightToClient();
		msg.ip = getExternalIp();
		msg.port = getPort();
		StringBuilder playerids = new StringBuilder();
		for (Player player : preFight) {
			player.Send(infoMsg);
			player.Send(msg);
			this.players.remove(player.getId());
			LogUtil.debug("call player into fightserver[" + player.getId());
			playerids.append(player.getId()).append("|");
		}
		logbattleStart(playerids.toString());// 戰鬥開始
	}

	public void addPreFightPlayers(Set<Player> s) {
		preFight = s;
	}

	/**
	 * fighter对于玩家请求进入的回报处理
	 * 
	 * @param msg
	 */
	private void onPlayerRequireEnterReturn(UGUserLoginRet msg) {
		if (msg.ret != ErrorCode.SUCCESS) {
			notIn.remove(msg.roleid);
		}
		Player player = playerManager.GetPlayerById(msg.roleid);
		if (player == null) {
			return;
		}
		SCEnterFighter clientmsg = new SCEnterFighter();
		clientmsg.ip = externalip;
		clientmsg.port = port;
		clientmsg.token = token;
		player.Send(clientmsg);
	}

	/**
	 * 有玩家加入战斗
	 * 
	 * @param playerid
	 */
	private void playerjoinfight(long playerid) {
		if (!rankinfo.containsKey(playerid)) {
			Player player = playerManager.GetPlayerById(playerid);
			if (player == null)
				return;
			rankinfo.put(playerid, new FightRankInfo());
			joinfightplayers++;
			player.onPlayerStartFight(getServerid());
			player.setStatus(Player.STATUS_GAMING);
		}
	}

	/**
	 * 玩家死亡
	 * 
	 * @param msg
	 */
	private void playerdead(UGPlayerDead msg) {
		LogUtil.debug("player dead. playerid=" + msg.playerid);

		if (msg.playerid == 0) {// 機器人死亡
			rank++;
			return;
		}

		if (!rankinfo.containsKey(msg.playerid))
			return;

		FightRankInfo playerrank = rankinfo.get(msg.playerid);

		int r = joinfightplayers - rank++;
		if (r == 0)
			return;
		playerrank.rank = r;
		playerrank.killed = msg.killed;
		playerrank.damage = msg.damage;
		if (!playerLeaveSet.contains(msg.playerid)) {// 玩家主动离开，要求不给奖励
			// 计算奖励
			fightStatiticMod.calcReward(playerrank);
		}

		// 推送玩家排名
		sendFightRankInfo(msg.playerid);

		// 处理战斗统计数据
		PlayerFightStatiticTable data = fightStatiticMod.doFightStatitic(msg, playerrank, zoneid, serverid);
		// 处理排行榜
		playerRankMod.doPlayerRank(data);

		// 吃鸡时、发放奖励
		if (playerrank.rank == 1) {
			distributeRewardMod.distributeReward(rankinfo);
			logbattleEnd();// 戰鬥結束
			preFight.clear();
		}
	}

	private void sendFightRankInfo(long playerid) {
		SCFightRankInfo ret = new SCFightRankInfo();
		ret.info = rankinfo.get(playerid);
		Player player = playerManager.GetPlayerById(playerid);
		if (player != null)
			player.Send(ret);
	}

	public String getExternalIp() {
		return externalip;
	}

	public short getPort() {
		return port;
	}

	public String getBattleMode() {
		return battleMode;
	}

	public String getMapname() {
		return mapname;
	}

	public int getStartTime() {
		return StartTime;
	}

	public String getVersion() {
		return version;
	}

	public short getMinPlayer() {
		return minplayer;
	}

	/**
	 * 相应玩家请求进入fightserver
	 * 
	 * @param roleid
	 * @return
	 */
	public void onPlayerRequireIn(Player player) {
		/*
		 * notIn.add(player.getId()); String string = Utility.md5_32(TimeCreator.GetCurStringTime() + player.getId()); // 发送给fightserver的 GUUserLogin msg = new GUUserLogin(); RoleFightInfo roleFightInfo = new RoleFightInfo();
		 * 
		 * TechnologySource sources = (TechnologySource) player.source.GetSource(ModType.Mod_Technology); roleFightInfo.roleid = player.getId(); roleFightInfo.token = string; roleFightInfo.rolename = player.getName(); roleFightInfo.tankid = player.getCurrTank(); List<TechnologyInfo> techs = sources.GetAllTech(); roleFightInfo.techs = new TechnologyInfo[techs.size()]; int n = 0; for (TechnologyInfo tech : techs) { roleFightInfo.techs[n++] = tech; }
		 * 
		 * // 发送弹药信息 TankModelSource tanksource = (TankModelSource) player.source.GetSource(ModType.Mod_TankModel); TankModelTable tanktable = tanksource.tankmodels.get(player.getCurrTank()); if (tanktable != null) { roleFightInfo.bulletids = tanktable.barrelBulletids; roleFightInfo.bulletcounts = tanktable.barrelBulletcounts; roleFightInfo.itemids = tanktable.itemids; roleFightInfo.itemcounts = tanktable.itemcounts; roleFightInfo.skillpartid = tanktable.skillpartid; roleFightInfo.diys =
		 * tanktable.diypartids; List<DriverInfo> driverInfos = new ArrayList(); for (int j = 0; j < tanktable.tankmembers.length; j++) { int memberid = tanktable.tankmembers[j]; if (memberid > 0) { TankMemberTable membertable = tanksource.tankmembersmp.get(memberid); if (membertable != null) { DriverInfo newdriver = new DriverInfo(); newdriver.id = memberid; newdriver.level = membertable.lvl; newdriver.star = membertable.star; newdriver.state = membertable.state; newdriver.chips =
		 * membertable.chips; driverInfos.add(newdriver); } } } roleFightInfo.drivers = new DriverInfo[driverInfos.size()];
		 * 
		 * driverInfos.toArray(roleFightInfo.drivers);
		 * 
		 * }
		 * 
		 * msg.roleinfo = roleFightInfo; Send(msg);
		 * 
		 * player.token = string;
		 */
	}

	/**
	 * 处理fighter登入
	 * 
	 * @param msg
	 */
	private void onServerLogin(UGlogin msg) {
		hostname = msg.hostname;
		mapname = msg.map;
		version = msg.version;
		battleMode = msg.battletype;
		minplayer = msg.minplayer;
		maxplayer = msg.maxplayer;
		externalip = msg.ip;
		port = msg.port;
		fightnumber = msg.fightnumber;
		StartTime = TimeCreator.GetTimeStamp();
		agentindex = msg.agentid;
		serverid = idCreator.GetId64();
		serverManager.addServer(this);
		makeToken();
		LogUtil.debug("fight server [" + serverid + "][" + getMapname() + "][" + getBattleMode() + "] login from " + getIp() + "[" + hostname + "]" + " successfully!" + "agentid " + agentindex);

		GULoginRet ret = new GULoginRet();
		ret.serverid = serverid;
		Send(ret);

	}

	private void makeToken() {
		String str = "SERVER" + serverid + TimeCreator.GetTimeStamp();
		token = Utility.md5_32(str);
	}

	/**
	 * 重置连接这个服务器的所有玩家的状态
	 */
	private void resetPlayerState() {
		for (Long id : notIn) {
			Player player = playerManager.GetPlayerById(id);
			if (player != null) {
				// player.setMap(null);
				// player.setSelfSever(null);
			}
		}
		for (Long id : players) {
			Player player = playerManager.GetPlayerById(id);
			if (player != null) {
				// player.setMap(null);
				// player.setSelfSever(null);
			}
		}
	}

	@Override
	protected void onConnect() {
		serverid = idCreator.GetTmpId32();
		LogUtil.debug("fight server [" + serverid + "] connected from " + getIp());
		// LogFightServerInfo log = new LogFightServerInfo();
		// log.ip = getIp();
		// log.serverid = serverid;
		// log.state = LogFightServerInfo.connect;
		// DBManager.saveLog(log);
	}

	@Override
	protected void onDisconnect() {
		LogUtil.debug("fight server [" + serverid + "] disconnected");
		resetPlayerState();
		serverManager.removeServer(this);
		// agents.onFightServerDisconnect(this);

		// LogFightServerInfo log = new LogFightServerInfo();
		// log.ip = getIp();
		// log.serverid = serverid;
		// log.state = LogFightServerInfo.disconnect;
		// DBManager.saveLog(log);

	}

	public void sendMsgForMap(CPacket packet) {
		for (Long id : players) {
			Player player = playerManager.GetPlayerById(id);
			if (player != null) {
				player.Send(packet);
			}
		}
	}

	@Override
	public void update() {
		// TODO Auto-generated method stub

	}

	public long getServerid() {
		return serverid;
	}

	private void logbattleStart(String playerids) {
		LogBattle log = new LogBattle();
		log.zoneid = this.zoneid;
		log.serverid = this.serverid;
		log.playerids = playerids.substring(0, playerids.toString().length() - 1);
		log.starttime = TimeCreator.GetTimeStamp();
		DBManager.saveLog(log);
	}

	private void logbattleEnd() {
		LogBattle log = DBManager.getUniqu(LogBattle.class, "serverid", this.serverid, DBType.LogDB);
		log.endtime = TimeCreator.GetTimeStamp();
		DBManager.saveOrUpdate(log, DBType.LogDB);
	}

	// 准备进去的人
	public Set<Player> preFight = new HashSet<>();

	int rank = 0;
	int joinfightplayers = 0;// 实际加入战斗的玩家数
	Map<Long, FightRankInfo> rankinfo = new HashMap<>();// <playerid, 战斗排名信息>
	Set<Long> playerLeaveSet = new HashSet<Long>();// 标记主动退出的玩家，for 玩家主动离开，要求不给奖励
	// 玩家进入时候使用的token
	String token;

}
