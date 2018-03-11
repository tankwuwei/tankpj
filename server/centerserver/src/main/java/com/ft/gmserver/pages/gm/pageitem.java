package com.ft.gmserver.pages.gm;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.ft.gmserver.Center.CenterClient;
import com.ft.gmserver.Center.CenterManager;
import com.ft.gmserver.pages.pagebase;
import com.ft.gmserver.struct.structItem;

import dbobjects.gamedb.ItemTable;
import engine.bean.BeanFactory;
import engine.db.DBManager;
import engine.db.client.DBType;
import engine.log.LogUtil;
import engine.string.StringUtil;
import generated.cgame.packets.objects.PlayerItem;

public class pageitem extends pagebase {

	public String content(String param) {

		Map<String, String> params = StringUtil.split(param, "&", "=");
		String playerid = params.get("playerid");
		String nickname = params.get("nickname");
		try {

			List<structItem> color = new ArrayList<>();
			List<structItem> texture = new ArrayList<>();
			List<structItem> decal = new ArrayList<>();
			List<structItem> box = new ArrayList<>();
			List<structItem> gold = new ArrayList<>();
			List<structItem> expbox = new ArrayList<>();
			List<structItem> parachute = new ArrayList<>();
			List<structItem> knapsack = new ArrayList<>();

			CenterClient centerClient = BeanFactory.getBean(CenterManager.class).getCenterClient_GameServer();
			if (centerClient == null) {// GameServer未启动
				List<ItemTable> all = DBManager.getbyhql(ItemTable.class, DBType.GameDB, "from ItemTable where playerid=" + playerid);
				for (ItemTable a : all) {
					new structItem(a, color, texture, decal, box, gold, expbox,parachute,knapsack);
				}
			} else {
				centerClient.getOnlinePlayerItem(Long.parseLong(playerid));
				if (centerClient.onlinePlayerItem == null || centerClient.onlinePlayerItem.size() == 0) {
					List<ItemTable> all = DBManager.getbyhql(ItemTable.class, DBType.GameDB, "from ItemTable where playerid=" + playerid);
					for (ItemTable a : all) {
						new structItem(a, color, texture, decal, box, gold, expbox,parachute,knapsack);
					}
				} else {
					for (PlayerItem a : centerClient.onlinePlayerItem) {
						new structItem(a, color, texture, decal, box, gold, expbox,parachute,knapsack);
					}
				}
			}

			context.put("color", color);
			context.put("texture", texture);
			context.put("decal", decal);
			context.put("box", box);
			context.put("gold", gold);
			context.put("expbox", expbox);
			context.put("parachute", parachute);
			context.put("knapsack", knapsack);			

			context.put("playerid", playerid);
			context.put("nickname", nickname);
			velocityEngine.mergeTemplate("templates/gm/item.vm", "utf-8", context, sw);
		} catch (Exception e) {
			LogUtil.warn(e);
		}
		return sw.toString();
	}

	@Override
	public String content() {
		// TODO Auto-generated method stub
		return null;
	}

}
