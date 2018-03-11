package com.ft.csv.resources.item;

import java.util.Map;

import com.ft.csv.resources.StringExt;

import engine.util.ResourceBase;

public class GameExchange extends ResourceBase {

	public int Price;
	public int GamePropID;

	@Override
	public void parse(Map<String, String> it) {
		id = StringExt.getInt(it.get("---"));
		Price = StringExt.getInt(it.get("Price"));
		GamePropID = StringExt.getInt(it.get("GamePropID"));
	}

	@Override
	public String toString() {
		return "GameExchange [id=" + id + ", Price=" + Price + ", GamePropID=" + GamePropID + "]";
	}

}
