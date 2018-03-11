package com.ft.gameserver.playermod.pack.item;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.ft.csv.resources.ResourceManager;
import com.ft.csv.resources.item.GameProp;
import com.ft.gameserver.player.Player;
import com.ft.gameserver.playermod.pack.PackMod;

import dbobjects.gamedb.ItemTable;
import engine.common.TimeCreator;

@Service
@Scope("prototype")
public abstract class Item implements ItemI {

	@Autowired
	ItemFactory itemFactory;
	@Autowired
	ResourceManager configs;
	@Autowired
	PackMod packMod;

	/**
	 * 使用道具
	 */
	@Override
	public void use(Player player, short pos) {
		if (!chkpos(player, pos))
			return;

		if (this.deadline == 0 && StringUtils.equals(propconfig.DeArchiveType, EDeArchiveType_Use))// 第一次使用，开始倒计时
			this.deadline = TimeCreator.GetTimeStamp() + propconfig.LimitedTime * 24 * 3600;

		realuse(player, pos);
	}

	protected abstract boolean chkpos(Player player, short pos);

	protected abstract void realuse(Player player, short pos);

	/**
	 * 卸载道具
	 */
	@Override
	public void unuse(Player player) {
		putPack(player);// 放入背包
	}

	/**
	 * 放入背包
	 */
	protected void putPack(Player player) {
		this.pos = 0;
	}

	/**
	 * 放入槽位
	 */
	protected void putSlot(Player player, short pos) {
		this.pos = pos;
	}

	/**
	 * 需要卸載時、執行卸載
	 */
	protected void unuse(Player player, short pos) {
		for (Item item : packMod.getSource(player).items.values()) {
			if (item.pos == pos) {
				item.unuse(player); // 卸载
				break;
			}
		}
	}

	public void setData(ItemTable data) {
		this.id = data.id;
		this.playerid = data.playerid;
		this.propid = data.propid;
		this.deadline = data.deadline;
		this.pos = data.pos;
	}

	public ItemTable getData() {
		ItemTable data = new ItemTable();
		data.id = this.id;
		data.playerid = this.playerid;
		data.propid = this.propid;
		data.deadline = this.deadline;
		data.pos = this.pos;
		return data;
	}

	public long id;
	public long playerid;
	public int propid;// 道具id
	public int deadline;// 过期时间
	public short pos;// 槽位位置(从左到右1\2\3\4\5\6\7); 0是背包中的道具

	public GameProp propconfig;// 道具配置

}
