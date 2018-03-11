package com.ft.gameserver.globalmods;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.ft.gameserver.basemod.GlobalBaseMod;
import com.ft.gameserver.player.Player;
import com.ft.gameserver.playermod.pack.PackMod;

import dbobjects.gamedb.UnDistributeRewardTable;
import engine.db.DBHandler;
import engine.db.DBManager;
import engine.db.client.DBType;
import engine.net.CPacket;
import generated.cgame.packets.objects.FightRankInfo;

@Controller
public class DistributeRewardMod extends GlobalBaseMod implements DBHandler {

	@Autowired
	PlayerManager playerManager;
	@Autowired
	PackMod packMod;

	@Override
	protected void handle(CPacket packet) {
	}

	@PostConstruct
	private void regist() {
		DBManager.regist(this);
	}

	@Override
	public void initDB() {
		List<UnDistributeRewardTable> list = DBManager.get(UnDistributeRewardTable.class, DBType.GameDB);
		for (UnDistributeRewardTable data : list) {
			unDistributeReward.put(data.playerid, data.money);
		}
	}

	@Override
	public void onPlayerLogin(Player player) {
		if (unDistributeReward.containsKey(player.getId())) {
			player.data.money += unDistributeReward.get(player.getId());

			unDistributeReward.remove(player.getId());

			UnDistributeRewardTable del = DBManager.getUniqu(UnDistributeRewardTable.class, "playerid", player.getId(), DBType.GameDB);
			DBManager.delete(UnDistributeRewardTable.class, del.id, DBType.GameDB);

			player.sendBaseData();
		}

		// 赠送箱子
		packMod.givebox(player);
	}

	/**
	 * 发放奖励
	 * 
	 * @param rankinfo
	 */
	public void distributeReward(Map<Long, FightRankInfo> rankinfo) {
		for (Map.Entry<Long, FightRankInfo> entry : rankinfo.entrySet()) {
			int money = entry.getValue().rankreward + entry.getValue().killedreward + entry.getValue().damagereward;
			Player player = playerManager.GetPlayerById(entry.getKey());
			if (player != null) {
				player.data.money += money;

				// 赠送箱子
				packMod.givebox(player);
			} else {// 玩家下线
				unDistributeReward.put(entry.getKey(), money);

				UnDistributeRewardTable data = new UnDistributeRewardTable();
				data.playerid = entry.getKey();
				data.money = money;
				DBManager.saveOrUpdate(data, DBType.GameDB);
			}
		}
	}

	@Override
	public void atfterInitDB() {
		// TODO Auto-generated method stub

	}

	@Override
	public void Update() {
		// TODO Auto-generated method stub

	}

	@Override
	public void onPlayerLogout(Player player) {
		// TODO Auto-generated method stub

	}

	// 未分配奖励的<playerid, money>
	Map<Long, Integer> unDistributeReward = new HashMap<Long, Integer>();

	@Override
	public void onDayChanged() {
		// TODO Auto-generated method stub

	}

}
