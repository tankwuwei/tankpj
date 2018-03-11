package com.ft.gameserver.playermod.fight;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.ft.gameserver.GameServer;
import com.ft.gameserver.basemod.BaseMod;
import com.ft.gameserver.globalmods.PlayerManager;
import com.ft.gameserver.player.Player;
import com.ft.gameserver.servers.Server;
import com.ft.gameserver.servers.ServerManager;

import dbobjects.gamedb.PlayerFightStatiticTable;
import dbobjects.logdb.PlayerFightTable;
import engine.common.TimeCreator;
import engine.db.DBManager;
import engine.db.client.DBType;
import engine.log.LogUtil;
import engine.net.CPacket;
import generated.cgame.packets.client.UGPlayerDead;
import generated.cgame.packets.objects.FightRankInfo;
import generated.cgame.packets.objects.PlayerFightData;
import generated.cgame.packets.server.SCPlayerFightData;

@Controller
public class FightStatiticMod extends BaseMod {

	@Autowired
	PlayerManager PlayerManager;
	@Autowired
	ServerManager servers;

	@Override
	protected void handle(CPacket packet) {
	}

	@Override
	protected void OnPlayerLogin(Player player) {
		if (!playerFightStatitic.containsKey(player.getId())) {
			PlayerFightStatiticTable data = DBManager.getUniqu(PlayerFightStatiticTable.class, "playerid", player.getId(), DBType.GameDB);
			playerFightStatitic.put(player.getId(), data);
		}

		sendPlayerFightData(player, playerFightStatitic.get(player.getId()));
	}

	/**
	 * 计算奖励
	 * 
	 */
	public void calcReward(FightRankInfo playerrank) {
		int rankreward = (int) (Math.pow((double) GameServer.p1 / playerrank.rank, 1 / 3.5) * 89.1 - playerrank.rank * 4.4);
		playerrank.rankreward = rankreward > 0 ? rankreward : 0;
		playerrank.killedreward = playerrank.killed * GameServer.p2;
		playerrank.damagereward = playerrank.damage / GameServer.p3;
	}

	/**
	 * 处理战斗统计数据
	 * 
	 */
	public PlayerFightStatiticTable doFightStatitic(UGPlayerDead msg, FightRankInfo playerrank, int zoneid, long serverid) {
		// 保存玩家战斗记录
		savePlayerFightData(msg, playerrank, zoneid, serverid);
		// 更新玩家战斗统计数据
		return updFightDataStatitic(msg, playerrank);
	}

	private void savePlayerFightData(UGPlayerDead msg, FightRankInfo playerrank, int zoneid, long serverid) {
		PlayerFightTable data = new PlayerFightTable();
		data.zoneid = zoneid;
		data.serverid = serverid;

		data.playerid = msg.playerid;
		data.nickname = getplayer_nickname(serverid, msg.playerid);

		data.killed = msg.killed;
		data.damage = msg.damage;
		data.hurt = msg.hurt;
		data.time = msg.time;
		data.travel = msg.travel;

		data.rank = playerrank.rank;

		data.rankreward = playerrank.rankreward;
		data.killedreward = playerrank.killedreward;
		data.damagereward = playerrank.damagereward;

		data.deadtime = TimeCreator.GetTimeStamp();

		DBManager.saveOrUpdate(data, DBType.LogDB);
	}

	private String getplayer_nickname(long serverid, long playerid) {
		Server server = servers.getServer(serverid);
		for (Player player : server.preFight)
			if (player.getId() == playerid)
				return player.getName();

		LogUtil.warn("死亡玩家非法！playerid=" + playerid);
		return null;
	}

	private PlayerFightStatiticTable updFightDataStatitic(UGPlayerDead msg, FightRankInfo playerrank) {
		PlayerFightStatiticTable data = DBManager.getUniqu(PlayerFightStatiticTable.class, "playerid", msg.playerid, DBType.GameDB);
		data.playrounds += 1;// 游戏总局数
		data.playtime += msg.time;// 游戏总时长
		data.travel += msg.travel;// 游戏总行驶公里数
		data.avgtravel = data.travel / data.playrounds;// 平均每局游戏行驶公里数

		if (playerrank.rank == 1)
			data.winrounds += 1;// 获得第一名的次数
		data.winratio = (int) ((float) data.winrounds / data.playrounds * 100);// 获得第一名的概率（吃鸡数/总局数）

		if (playerrank.rank <= 5)
			data.top5rounds += 1;// 获得前5名的总次数
		data.top5ratio = (int) ((float) data.top5rounds / data.playrounds * 100);// 获得前5名的概率（前5数/总局数）

		data.destorys += msg.killed;// 击杀总数
		data.totaldamage += msg.damage;// 造成的总伤害
		data.avgdamage = data.totaldamage / data.playrounds;// 平均伤害（总伤害/局数）
		if (data.playrounds - data.winrounds > 0) {
			data.kdratio = (int) (((float) data.destorys / (data.playrounds - data.winrounds)) * 100);// 击杀/死亡比
		} else {
			data.kdratio = (int) ((float) data.destorys / 1 * 100);
		}
		data.totalhurt += msg.hurt;// 承受的总伤害
		data.avghurt = data.totalhurt / data.playrounds;// 平均每局的承受伤害

		DBManager.saveOrUpdate(data, DBType.GameDB);

		// ========更新缓存========
		playerFightStatitic.put(msg.playerid, data);

		Player player = PlayerManager.GetPlayerById(msg.playerid);
		if (player != null)// 在线
			sendPlayerFightData(player, data);

		return data;
	}

	private void sendPlayerFightData(Player player, PlayerFightStatiticTable d) {
		SCPlayerFightData data = new SCPlayerFightData();
		PlayerFightData info = new PlayerFightData();

		info.playrounds = d.playrounds;
		info.playtime = d.playtime;
		info.travel = d.travel;
		info.avgtravel = d.avgtravel;
		info.winrounds = d.winrounds;
		info.top5rounds = d.top5rounds;
		info.winratio = d.winratio;
		info.top5ratio = d.top5ratio;
		info.destorys = d.destorys;
		info.totaldamage = d.totaldamage;
		info.avgdamage = d.avgdamage;
		info.kdratio = d.kdratio;
		info.totalhurt = d.totalhurt;
		info.avghurt = d.avghurt;

		data.info = info;
		player.Send(data);
	}

	@Override
	protected void onCreatePlayer(Player player) {
		init_PlayerFightStatitic(player);
	}

	@Override
	protected void OnPlayerLogout(Player player) {
		playerFightStatitic.remove(player.getId());
	}

	@Override
	protected void Update(Player player) {
		// TODO Auto-generated method stub

	}

	@Override
	protected void onDayChanged(Player player) {
		// TODO Auto-generated method stub

	}

	private void init_PlayerFightStatitic(Player player) {
		PlayerFightStatiticTable data = new PlayerFightStatiticTable();
		data.playerid = player.getId();
		DBManager.saveOrUpdate(data, DBType.GameDB);
	}

	public void setNickname_PlayerFightStatitic(Player player) {
		PlayerFightStatiticTable data = DBManager.getUniqu(PlayerFightStatiticTable.class, "playerid", player.getId(), DBType.GameDB);
		data.nickname = player.getName();
		DBManager.saveOrUpdate(data, DBType.GameDB);
	}

	// <playerid, 玩家战斗数据统计>
	Map<Long, PlayerFightStatiticTable> playerFightStatitic = new HashMap<>();
}
