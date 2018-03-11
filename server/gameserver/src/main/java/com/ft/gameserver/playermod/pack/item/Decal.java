package com.ft.gameserver.playermod.pack.item;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.ft.csv.resources.item.PaintingDecal;
import com.ft.gameserver.player.Player;
import com.ft.gameserver.playermod.pack.PackMod;

import common.ErrorCode;

@Service
@Scope("prototype")
public class Decal extends Item {

	@Autowired
	PackMod packMod;

	@Override
	public void realuse(Player player, short pos) {
		if (pos > 0) {
			unuse(player, pos);// 卸载
			putSlot(player, pos);// 放入槽位
		} else {
			int index = unusePos(player);
			if (index == decalconfig.pos.size())
				unuse(player, decalconfig.pos.get(0));// 卸载
			pos = index < decalconfig.pos.size() ? decalconfig.pos.get(index) : decalconfig.pos.get(0);
			putSlot(player, pos);// 放入槽位
		}
		packMod.sendUseItem(player, this.id, pos, this.propid, this.propconfig.Type, ErrorCode.SUCCESS, null);
	}

	@Override
	protected boolean chkpos(Player player, short pos) {
		if (pos > 0 && !decalconfig.pos.contains(pos)) {
			packMod.sendUseItem(player, this.id, pos, this.propid, this.propconfig.Type, ErrorCode.InvalidPos, null);
			return false;
		}
		return true;
	}

	protected int unusePos(Player player) {
		int i = 0;
		while (i < decalconfig.pos.size()) {
			boolean hasEmptyPos = true;
			for (Item item : packMod.getSource(player).items.values()) {
				if (item.pos == decalconfig.pos.get(i)) {
					hasEmptyPos = false;
					break;
				}
			}
			if (hasEmptyPos)
				break;
			i++;
		}
		return i;
	}

	protected PaintingDecal decalconfig;// 涂装配置
}
