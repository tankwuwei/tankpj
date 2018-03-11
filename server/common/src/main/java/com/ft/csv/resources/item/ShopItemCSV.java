package com.ft.csv.resources.item;

import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.ft.csv.resources.StringExt;

import engine.util.ResourceBase;

public class ShopItemCSV extends ResourceBase {

	public int price;
	public short type;

	@Override
	public void parse(Map<String, String> it) {
		id = StringExt.getInt(it.get("GamePropID"));
		price = StringExt.getInt(it.get("Price"));
		type = getType(it.get("type"));

	}

	private short getType(String type) {
		if (StringUtils.equals(type, "ShopItem_Normal"))
			return 0;
		else if (StringUtils.equals(type, "ShopItem_New"))
			return 1;
		else if (StringUtils.equals(type, "ShopItem_Hot"))
			return 2;

		return 0;
	}

}
