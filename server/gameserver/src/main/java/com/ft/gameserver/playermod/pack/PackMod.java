package com.ft.gameserver.playermod.pack;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.ft.csv.resources.ResourceManager;
import com.ft.csv.resources.item.GameExchange;
import com.ft.csv.resources.item.GameProp;
import com.ft.gameserver.basemod.BaseMod;
import com.ft.gameserver.clients.Client;
import com.ft.gameserver.globalmods.PlayerRankMod;
import com.ft.gameserver.globalmods.TeamMod;
import com.ft.gameserver.player.Player;
import com.ft.gameserver.playermod.pack.item.Item;
import com.ft.gameserver.playermod.pack.item.ItemFactory;
import com.ft.gameserver.playermod.pack.item.ItemI;
import com.ft.gameserver.playermod.pack.item.TGold;
import com.ft.gameserver.utility.IdCreator;

import common.ErrorCode;
import dbobjects.gamedb.ItemTable;
import dbobjects.logdb.LogItem;
import engine.common.TimeCreator;
import engine.db.DBManager;
import engine.db.client.Condition;
import engine.db.client.DBType;
import engine.net.CPacket;
import generated.cgame.packets.Code;
import generated.cgame.packets.client.CSUnUseItem;
import generated.cgame.packets.client.CSUseItem;
import generated.cgame.packets.objects.BoxData;
import generated.cgame.packets.objects.PlayerItem;
import generated.cgame.packets.server.SCPlayerItems;
import generated.cgame.packets.server.SCUnUseItem;
import generated.cgame.packets.server.SCUseItem;

@Controller
public class PackMod extends BaseMod {

	@Autowired
	ResourceManager configs;
	@Autowired
	ItemFactory itemFactory;
	@Autowired
	IdCreator idCreator;
	@Autowired
	PlayerRankMod playerRankMod;
	@Autowired
	TeamMod TeamMod;

	public PackMod() {
		ModType = ModType.Mod_Pack;
	}

	@Override
	protected void handle(CPacket packet) {
		Client client = (Client) packet.o;
		Player player = client.player;
		switch (packet.code) {
		case Code.CSUseItem:
			CSUseItem msg = (CSUseItem) packet;
			use(player, msg.id, msg.pos);
			break;
		case Code.CSUnUseItem:
			unuse(player, ((CSUnUseItem) packet).pos);
			break;
		default:
			break;
		}
	}

	/**
	 * 使用道具
	 * 
	 * @param player
	 * @param id
	 * @param pos
	 */
	private void use(Player player, long id, short pos) {
		if (!getSource(player).items.containsKey(id)) {// chk 是否存在
			sendUseItem(player, id, pos, 0, null, ErrorCode.NotItem, null);
			return;
		}

		Item item = getSource(player).items.get(id);

		if (item.deadline > 0 && item.deadline < TimeCreator.GetTimeStamp()) {// chk
																				// 有效期
			sendUseItem(player, id, pos, item.propid, item.propconfig.Type, ErrorCode.OverDeadLine, null);
			delete(player, item);// 删除道具
			return;
		}

		item.use(player, pos);

		TeamMod.decal2team(player);
	}

	public void sendUseItem(Player player, long id, short pos, int propid, String itemtype, short errcode, Map<Integer, Integer> boxdata) {
		SCUseItem msg = new SCUseItem();
		msg.id = id;
		msg.pos = pos;
		msg.errcode = errcode;
		if (boxdata != null) {
			msg.boxdata = new BoxData[boxdata.size()];
			int i = 0;
			for (Map.Entry<Integer, Integer> entry : boxdata.entrySet()) {
				BoxData data = new BoxData();
				data.propid = entry.getKey();
				data.count = entry.getValue();
				msg.boxdata[i++] = data;
			}
		}
		player.Send(msg);
		log(player, propid, itemtype, LogItem.FLAG_USE, errcode);
	}

	private void log(Player player, int propid, String itemtype, short flag, short result) {
		LogItem log = new LogItem();
		log.playerid = player.data.id;
		log.nickname = player.data.nickname;
		log.propid = propid;
		log.itemtype = itemtype;
		log.flag = flag;
		log.result = result;
		DBManager.saveLog(log);
	}

	/**
	 * 卸载道具
	 * 
	 * @param player
	 * @param id
	 */
	private void unuse(Player player, short pos) {
		Item item = null;
		for (Item o : getSource(player).items.values()) {
			if (o.pos == pos) {
				item = o;
				break;
			}
		}

		if (item == null) {// chk 是否已經被卸載
			sendUnUseItem(player, pos, pos, null, ErrorCode.UnUse);// 已经被卸载时，LogItem中的propid=请求卸载pos
			return;
		}

		item.unuse(player);

		sendUnUseItem(player, pos, item.propid, item.propconfig.Type, ErrorCode.SUCCESS);
	}

	private void sendUnUseItem(Player player, short pos, int propid, String itemtype, short errcode) {
		SCUnUseItem msg = new SCUnUseItem();
		msg.pos = pos;
		msg.errcode = errcode;
		player.Send(msg);
		log(player, propid, itemtype, LogItem.FLAG_UNUSE, errcode);
	}

	/**
	 * 刪除道具
	 */
	public void delete(Player player, Item item) {
		getSource(player).items.remove(item.id);
		DBManager.delete(ItemTable.class, item.id, DBType.GameDB);
		log(player, item.propid, item.propconfig.Type, LogItem.FLAG_DEL, ErrorCode.SUCCESS);
	}

	/**
	 * 增加道具
	 */
	public void add(Player player, Item item) {
		long id = idCreator.GetId64();
		item.id = id;
		item.playerid = player.getId();
		if (StringUtils.equals(item.propconfig.DeArchiveType, ItemI.EDeArchiveType_Get))// 捡起倒计时
			item.deadline = TimeCreator.GetTimeStamp() + item.propconfig.LimitedTime * 24 * 3600;
		item.pos = 0;
		getSource(player).items.put(id, item);
		log(player, item.propid, item.propconfig.Type, LogItem.FLAG_GET, ErrorCode.SUCCESS);
	}

	@Override
	protected void OnPlayerLogin(Player player) {
		sendPlayerItems(player, getSource(player).items);// 推送玩家道具
	}

	/**
	 * 道具放入背包
	 */
	public void putPack(Player player, int propid) {
		Item item = itemFactory.getItem(propid);
		add(player, item);
		Map<Long, Item> items = new HashMap<Long, Item>();
		items.put(item.id, item);
		sendPlayerItems(player, items);
	}

	/**
	 * 道具放入背包（批量處理）
	 */
	public void putPack(Player player, int[] propidArr, int[] numArr) {
		Map<Long, Item> items = new HashMap<Long, Item>();
		for (int i = 0; i < propidArr.length; i++) {
			int num = numArr[i];// 数量
			Item gold = itemFactory.getItem(propidArr[i]);
			if (gold instanceof TGold) {
				player.data.gold += gold.propconfig.Amount_TGold * num;
				continue;
			}
			for (int k = 0; k < num; k++) {
				Item item = itemFactory.getItem(propidArr[i]);
				add(player, item);
				items.put(item.id, item);
			}
		}

		player.sendBaseData();
		sendPlayerItems(player, items);
	}

	/**
	 * 推送玩家道具
	 */
	public void sendPlayerItems(Player player, Map<Long, Item> items) {
		isdeadline(player, items);// 检查是否过期

		SCPlayerItems msg = new SCPlayerItems();
		msg.items = new PlayerItem[items.size()];
		int i = 0;
		for (Item o : items.values()) {
			PlayerItem item = new PlayerItem();
			item.id = o.id;
			item.propid = o.propid;
			item.deadline = o.deadline == 0 ? 0 : o.deadline - TimeCreator.GetTimeStamp();
			item.pos = o.pos;
			msg.items[i++] = item;
		}
		player.Send(msg);
	}

	/**
	 * 是否过期
	 */
	private void isdeadline(Player player, Map<Long, Item> items) {
		Iterator<Item> it = items.values().iterator();
		while (it.hasNext()) {
			Item o = it.next();
			if (o.deadline == 0)
				continue;
			if (o.deadline - TimeCreator.GetTimeStamp() > 0)
				continue;
			else {
				DBManager.delete(ItemTable.class, o.id, DBType.GameDB);
				log(player, o.propid, o.propconfig.Type, LogItem.FLAG_DEL, ErrorCode.SUCCESS);
				it.remove();// 此迭代遍历, 可以避免ConcurrentModificationException
			}
		}
	}

	// 当前使用的<道具配置id>
	public int[] getUseItems(Player player) {
		int[] useitems = new int[Slot_Num];
		for (Item o : getSource(player).items.values()) {
			if (o.pos != 0) {
				switch (o.pos) {
				case 1:
					useitems[o.pos] = o.propid;
					break;
				case 2:
					useitems[o.pos] = o.propid;
					break;
				case 3:
					useitems[o.pos] = o.propid;
					break;
				case 4:
					useitems[o.pos] = o.propid;
					break;
				case 5:
					useitems[o.pos] = o.propid;
					break;
				case 6:
					useitems[o.pos] = o.propid;
					break;
				case 7:
					useitems[o.pos] = o.propid;
					break;
				case 8:
					useitems[o.pos] = o.propid;
					break;
				case 9:
					useitems[o.pos] = o.propid;
					break;
				}
			}
		}
		return useitems;
	}

	public int[] getUseItems(long playerid) {
		int[] useitems = new int[Slot_Num];
		List<ItemTable> list = DBManager.get(ItemTable.class, DBType.GameDB, Condition.eq("playerid", playerid));
		for (ItemTable o : list) {
			if (o.pos != 0) {
				switch (o.pos) {
				case 1:
					useitems[o.pos] = o.propid;
					break;
				case 2:
					useitems[o.pos] = o.propid;
					break;
				case 3:
					useitems[o.pos] = o.propid;
					break;
				case 4:
					useitems[o.pos] = o.propid;
					break;
				case 5:
					useitems[o.pos] = o.propid;
					break;
				case 6:
					useitems[o.pos] = o.propid;
					break;
				case 7:
					useitems[o.pos] = o.propid;
					break;
				case 8:
					useitems[o.pos] = o.propid;
					break;
				case 9:
					useitems[o.pos] = o.propid;
					break;
				}
			}
		}
		return useitems;
	}

	public PackSource getSource(Player player) {
		return (PackSource) GetSource(player);
	}

	@Override
	protected void onCreatePlayer(Player player) {
		testItemInit0(player);// 测试用////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	}

	private void testItemInit0(Player player) {
		int n = 0;
		try {
			n = Integer.parseInt(player.account.substring(4));
		} catch (Exception e) {
			return;
		}
		if (n >= configs.testitemconfig.account_start && n <= configs.testitemconfig.account_end)
			testItemInit(player);
	}

	private void testItemInit(Player player) {
		for (GameProp o : configs.propconfig.values()) {
			ItemTable item = new ItemTable();
			item.playerid = player.getId();
			item.propid = o.id;
			if (StringUtils.equals(o.DeArchiveType, ItemI.EDeArchiveType_Get))// 捡起倒计时
				item.deadline = TimeCreator.GetTimeStamp() + o.LimitedTime * 24 * 3600;
			item.pos = 0;
			DBManager.saveOrUpdate(item, DBType.GameDB);
		}
	}

	@Override
	protected void OnPlayerLogout(Player player) {
		// TODO Auto-generated method stub

	}

	@Override
	protected void Update(Player player) {
		// TODO Auto-generated method stub

	}

	@Override
	protected void onDayChanged(Player player) {
		player.data.givetimes = 0;// 重置贈送次数
		givebox(player);// 贈送箱子
	}

	/**
	 * 贈送箱子
	 * 
	 * @param player
	 */
	public void givebox(Player player) {
		// 第幾次贈送
		int n = 1 + player.data.givetimes;
		// n超过了规则个数，取最后一个
		GameExchange exchangeconfig = configs.exchangeconfig.get(n) == null ? configs.exchangeconfig.get(configs.exchangeconfig.size()) : configs.exchangeconfig.get(n);

		Map<Long, Item> items = new HashMap<>();
		while (player.data.money >= exchangeconfig.Price) {
			player.data.money -= exchangeconfig.Price;// 消耗多少錢

			Item item = itemFactory.getItem(exchangeconfig.GamePropID);
			add(player, item);// 增加道具

			items.put(item.id, item);
			player.data.givetimes++;
			player.data.level++;

			// 第幾次贈送
			n = 1 + player.data.givetimes;
			// n超过了规则个数，取最后一个
			exchangeconfig = configs.exchangeconfig.get(n) == null ? configs.exchangeconfig.get(configs.exchangeconfig.size()) : configs.exchangeconfig.get(n);
		}

		if (items.size() > 0) {
			sendPlayerItems(player, items);// 推送奖励的箱子
			playerRankMod.updLevelRank(player);// 更新等级排行
		}

		player.sendBaseData();// 刷新玩家基本数据
	}

	/**
	 * 请求在线玩家道具
	 */
	public PlayerItem[] getOnlinePlayerItem(Player player) {
		PlayerItem[] items = new PlayerItem[getSource(player).items.size()];
		int i = 0;
		for (Item o : getSource(player).items.values()) {
			PlayerItem d = new PlayerItem();
			d.id = o.id;
			d.propid = o.propid;
			d.deadline = o.deadline;
			d.pos = o.pos;
			items[i++] = d;
		}
		return items;
	}

	private static int Slot_Num = 10; // 从1开始

}
