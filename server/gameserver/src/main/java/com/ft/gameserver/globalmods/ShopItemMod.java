package com.ft.gameserver.globalmods;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.ft.csv.resources.ResourceManager;
import com.ft.csv.resources.item.ShopItemCSV;
import com.ft.gameserver.basemod.GlobalBaseMod;
import com.ft.gameserver.clients.Client;
import com.ft.gameserver.player.Player;
import com.ft.gameserver.playermod.pack.PackMod;
import com.ft.gameserver.playermod.pack.item.ItemFactory;

import common.ErrorCode;
import dbobjects.gamedb.ShopItemTable;
import dbobjects.logdb.Logbuyitem;
import engine.db.DBHandler;
import engine.db.DBManager;
import engine.db.client.DBType;
import engine.net.CPacket;
import generated.cgame.packets.Code;
import generated.cgame.packets.client.CSBuyShopItem;
import generated.cgame.packets.objects.ShopItem;
import generated.cgame.packets.objects.ShopItemList;
import generated.cgame.packets.server.SCBuyShopItem;
import generated.cgame.packets.server.SCShopItem;

@Controller
public class ShopItemMod extends GlobalBaseMod implements DBHandler {

	@Autowired
	ResourceManager config;
	@Autowired
	PlayerManager PlayerManager;
	@Autowired
	PackMod PackMod;
	@Autowired
	ItemFactory ItemFactory;

	@Override
	protected void handle(CPacket packet) {
		Client client = (Client) packet.o;
		Player player = client.player;
		switch (packet.code) {
		case Code.CSBuyShopItem:
			buyShopItem(player, ((CSBuyShopItem) packet).propid);
			break;
		default:
			break;
		}
	}

	@PostConstruct
	private void regist() {
		DBManager.regist(this);
	}

	@Override
	public void initDB() {
		List<ShopItemTable> list = DBManager.get(ShopItemTable.class, DBType.GameDB);
		if (list == null || list.size() == 0) {
			readCSV();
			return;
		}

		for (ShopItemTable o : list) {
			allshopitems.put(o.propid, o);
			if (o.onsale == ONSALE)
				onshopitems.put(o.propid, o);
		}
	}

	private void readCSV() {
		for (ShopItemCSV o : config.shopitemconfig.values()) {
			ShopItemTable d = new ShopItemTable();
			d.propid = o.id;
			d.price = o.price;
			d.type = o.type;
			d.onsale = ONSALE;
			onshopitems.put(d.propid, d);
			allshopitems.put(d.propid, d);
		}
		saveShopItem();
	}

	private void saveShopItem() {
		DBManager.clear(ShopItemTable.class, DBType.GameDB);
		DBManager.saveOrUpdate(new HashSet<>(allshopitems.values()), DBType.GameDB);
	}

	@Override
	public void onPlayerLogin(Player player) {
		sendShopItem(player);
	}

	private void sendShopItem(Player player) {
		SCShopItem msg = new SCShopItem();
		msg.shopitem = new ShopItem[onshopitems.size()];
		int i = 0;
		for (ShopItemTable o : onshopitems.values()) {
			ShopItem d = new ShopItem();
			d.propid = o.propid;
			d.price = o.price;
			d.type = o.type;
			msg.shopitem[i++] = d;
		}
		player.Send(msg);
	}

	private void buyShopItem(Player player, int propid) {
		SCBuyShopItem msg = new SCBuyShopItem();
		msg.propid = propid;
		if (player.data.gold < onshopitems.get(propid).price) {
			msg.errcode = ErrorCode.NotEnoughGold;
		} else {
			msg.errcode = ErrorCode.SUCCESS;
			player.data.gold -= onshopitems.get(propid).price;
			player.sendBaseData();
			PackMod.putPack(player, propid);// 放入背包
		}
		player.Send(msg);
		logbuyitem(player, propid, msg.errcode == ErrorCode.SUCCESS ? Logbuyitem.result_0 : Logbuyitem.result_1);
	}

	private void logbuyitem(Player player, int propid, short result) {
		Logbuyitem log = new Logbuyitem();
		log.playerid = player.getId();
		log.nickname = player.getName();
		log.propid = propid;
		log.price = onshopitems.get(propid).price;
		log.result = result;
		DBManager.saveLog(log);
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

	@Override
	public void onDayChanged() {
		// TODO Auto-generated method stub

	}

	public ShopItemList[] getShopItemList() {
		ShopItemList[] shopitemList = new ShopItemList[allshopitems.size()];
		int i = 0;
		for (ShopItemTable o : allshopitems.values()) {
			ShopItemList d = new ShopItemList();
			d.propid = o.propid;
			d.price = o.price;
			d.type = o.type;
			d.onsale = o.onsale;
			shopitemList[i++] = d;
		}
		return shopitemList;
	}

	public void updShopItem(ShopItemList[] list) {
		Map<Integer, ShopItemTable> onshopitems = new HashMap<Integer, ShopItemTable>();
		Map<Integer, ShopItemTable> allshopitems = new HashMap<Integer, ShopItemTable>();
		for (ShopItemList o : list) {
			ShopItemTable d = new ShopItemTable();
			d.propid = o.propid;
			d.price = o.price;
			d.type = o.type;
			d.onsale = o.onsale;
			allshopitems.put(d.propid, d);
			if (d.onsale == ONSALE)
				onshopitems.put(d.propid, d);
		}
		this.allshopitems = allshopitems;
		this.onshopitems = onshopitems;
		saveShopItem();
		sendOnlinePlayer();
	}

	// 推送给在线玩家
	private void sendOnlinePlayer() {
		for (Player player : PlayerManager.allplays.values())
			sendShopItem(player);
	}

	public Map<Integer, ShopItemTable> onshopitems = new HashMap<Integer, ShopItemTable>();// on sale
	public Map<Integer, ShopItemTable> allshopitems = new HashMap<Integer, ShopItemTable>();
	private short ONSALE = 0;
}
