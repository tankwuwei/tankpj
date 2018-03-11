package com.ft.gameserver.playermod.pack;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.ft.gameserver.basemod.SourceBaseMod;
import com.ft.gameserver.playermod.pack.item.Item;
import com.ft.gameserver.playermod.pack.item.ItemFactory;

import dbobjects.gamedb.ItemTable;
import engine.db.DBManager;
import engine.db.client.Condition;
import engine.db.client.DBType;
import engine.log.LogUtil;

@Service
@Scope("prototype")
public class PackSource extends SourceBaseMod {

	@Autowired
	ItemFactory itemFactory;

	public PackSource() {
		ModType = ModType.Mod_Pack;
	}

	@Override
	protected void LoadData(long id) {
		List<ItemTable> list = DBManager.get(ItemTable.class, DBType.GameDB, Condition.eq("playerid", id));
		for (ItemTable data : list) {
			Item item = itemFactory.getItem(data.propid);
			if (item == null) {
				LogUtil.warn("Item type is not correct. propid=" + data.propid);
				continue;
			}
			item.setData(data);
			items.put(data.id, item);
		}
	}

	@Override
	protected void SaveData(long id) {
		for (Item item : items.values()) {
			DBManager.saveOrUpdate(item.getData(), DBType.GameDB);
		}
	}

	// 玩家拥有的物品
	public Map<Long, Item> items = new HashMap<>();

}