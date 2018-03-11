package com.ft.gameserver.playermod.pack.item;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.ft.gameserver.player.Player;
import com.ft.gameserver.playermod.pack.PackMod;

import common.ErrorCode;

@Service
@Scope("prototype")
public class Parachute extends Item {
	@Autowired
	PackMod packMod;
	
	@Override
	public void realuse(Player player, short pos) {
		unuse(player, ParachutePos);// 卸载
		putSlot(player, ParachutePos);// 放入槽位
		packMod.sendUseItem(player, this.id, ParachutePos, this.propid, this.propconfig.Type, ErrorCode.SUCCESS, null);
	}

	@Override
	protected boolean chkpos(Player player, short pos) {
		if (pos > 0 && pos != ParachutePos) {
			packMod.sendUseItem(player, this.id, pos, this.propid, this.propconfig.Type, ErrorCode.InvalidPos, null);
			return false;
		}
		return true;
	}

}
