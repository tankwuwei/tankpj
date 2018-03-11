package com.ft.gameserver.playermod.pack.item;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.ft.gameserver.player.Player;
import com.ft.gameserver.playermod.pack.PackMod;

import common.ErrorCode;
import dbobjects.logdb.Logopenbox;
import engine.db.DBManager;

@Service
@Scope("prototype")
public class EXPBox extends Item {

	@Autowired
	PackMod PackMod;

	@Override
	protected void realuse(Player player, short pos) {
		Map<Integer, Integer> data = new HashMap<>();
		data.put(propid, propconfig.Amount_TGold);
		packMod.sendUseItem(player, this.id, pos, this.propid, this.propconfig.Type, ErrorCode.SUCCESS, data);

		player.data.money += propconfig.Amount_TGold;
		PackMod.givebox(player);

		logopenbox(player);
		PackMod.delete(player, this);
	}

	@Override
	protected boolean chkpos(Player player, short pos) {
		if (pos > 0) {
			packMod.sendUseItem(player, this.id, pos, this.propid, this.propconfig.Type, ErrorCode.InvalidPos, null);
			return false;
		}
		return true;
	}

	private void logopenbox(Player player) {
		Logopenbox log = new Logopenbox();
		log.playerid = player.getId();
		log.nickname = player.getName();
		log.boxid = this.propid;
		log.propidInbox = String.valueOf(this.propid);
		log.numInbox = String.valueOf(this.propconfig.Amount_TGold);
		log.result = Logopenbox.result_0;
		DBManager.saveLog(log);
	}
}
