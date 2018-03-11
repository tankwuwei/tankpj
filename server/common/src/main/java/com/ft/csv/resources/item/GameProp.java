package com.ft.csv.resources.item;

import java.util.Map;

import com.ft.csv.resources.StringExt;

import engine.util.ResourceBase;

public class GameProp extends ResourceBase {

	public String Type;
	public short LimitedTime;
	public int ID_GameBox;
	public int Name_PaintingDecal;// int not String
	public String DeArchiveType;

	public String Name;
	public int Amount_TGold;// 金币
	
	@Override
	public void parse(Map<String, String> it) {
		id = StringExt.getInt(it.get("---"));
		Type = it.get("Type");
		LimitedTime = StringExt.getShort(it.get("LimitedTime"));
		ID_GameBox = StringExt.getInt(it.get("ID_GameBox"));
		Name_PaintingDecal = StringExt.getInt(it.get("Name_PaintingDecal"));
		DeArchiveType = it.get("DeArchiveType");

		Name = it.get("Name").replaceAll("NSLOCTEXT\\(", "").replaceAll("\\)", "").split("\\,")[2].replaceAll("\"", "");
		Amount_TGold = StringExt.getInt(it.get("Amount_TGold"));
	}

	@Override
	public String toString() {
		return "GameProp [id=" + id + ", Type=" + Type + ", LimitedTime=" + LimitedTime + ", ID_GameBox=" + ID_GameBox + ", Name_PaintingDecal=" + Name_PaintingDecal + ", DeArchiveType=" + DeArchiveType + "]";
	}

}
