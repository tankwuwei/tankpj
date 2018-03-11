package com.ft.gameserver.playermod.pack.item;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.ft.csv.resources.item.GameBox;
import com.ft.gameserver.player.Player;
import com.ft.gameserver.playermod.pack.PackMod;

import common.ErrorCode;
import dbobjects.logdb.Logopenbox;
import engine.db.DBManager;
import engine.util.lottery.LotteryUtil;

@Service
@Scope("prototype")
public class Box extends Item {

	@Autowired
	ItemFactory itemFactory;
	@Autowired
	PackMod packMod;

	@Override
	public void realuse(Player player, short pos) {
		List<Integer> list = LotteryUtil.lottery(boxconfig.Probability, boxconfig.Count);// 抽奖
		for (int i : list) {
			int propid = boxconfig.GamePropID.get(i);
			Item item = itemFactory.getItem(propid);
			if (!isTGold(player, item))// 开出来的是金币?
				packMod.add(player, item);
			itemsInBox.put(item.id, item);
		}
		packMod.sendUseItem(player, this.id, pos, this.propid, this.propconfig.Type, ErrorCode.SUCCESS, getBoxData());
		packMod.sendPlayerItems(player, itemsInBox);// 推送箱子内容
		logopenbox(player);
		packMod.delete(player, this);
		player.sendBaseData();// 刷新金币
	}

	private boolean isTGold(Player player, Item item) {
		if (!(item instanceof TGold))
			return false;
		player.data.gold += item.propconfig.Amount_TGold;
		return true;
	}

	@Override
	protected boolean chkpos(Player player, short pos) {
		if (pos > 0) {
			packMod.sendUseItem(player, this.id, pos, this.propid, this.propconfig.Type, ErrorCode.InvalidPos, null);
			return false;
		}
		return true;
	}

	private Map<Integer, Integer> getBoxData() {
		Map<Integer, Integer> boxdata = new HashMap<>();// <propid, number>
		for (Item o : itemsInBox.values()) {
			if (boxdata.containsKey(o.propid))
				boxdata.put(o.propid, boxdata.get(o.propid) + 1);
			else
				boxdata.put(o.propid, 1);
		}
		return boxdata;
	}

	private void logopenbox(Player player) {
		Logopenbox log = new Logopenbox();
		log.playerid = player.getId();
		log.nickname = player.getName();
		log.boxid = this.propid;
		Map<Integer, Integer> itemInbox = getBoxData();
		log.propidInbox = itemInbox.keySet().toString();
		log.numInbox = itemInbox.values().toString();
		log.result = Logopenbox.result_0;
		DBManager.saveLog(log);
	}

	private Map<Long, Item> itemsInBox = new HashMap<>();

	protected GameBox boxconfig;

}
