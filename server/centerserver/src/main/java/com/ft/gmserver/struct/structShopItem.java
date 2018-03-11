package com.ft.gmserver.struct;

import com.ft.gmserver.csv.CSVConfig;

import generated.cgame.packets.objects.ShopItemList;

public class structShopItem {

	public int propid;
	public int price;
	public short type;// 0:ShopItem_Normal; 1:ShopItem_New; 2:ShopItem_Hot
	public short onsale;// 0:On; 1:Off
	public String name;

	public structShopItem(ShopItemList o) {
		propid = o.propid;
		price = o.price;
		type = o.type;
		onsale = o.onsale;
		name = CSVConfig.propconfig.get(propid) == null ? "该道具配置不存在" : CSVConfig.propconfig.get(propid).Name;
	}

	public int getPropid() {
		return propid;
	}

	public int getPrice() {
		return price;
	}

	public short getType() {
		return type;
	}

	public short getOnsale() {
		return onsale;
	}

	public String getName() {
		return name;
	}
}
