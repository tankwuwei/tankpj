package com.ft.gmserver.struct;

import java.util.List;

import com.ft.csv.resources.item.GameProp;
import com.ft.gmserver.csv.CSVConfig;

import dbobjects.gamedb.ItemTable;
import engine.common.TimeCreator;
import generated.cgame.packets.objects.PlayerItem;

public class structItem {

	public long id;
	public int propid;
	public String deadline;
	public short pos;

	public String Type;
	public short LimitedTime;
	public String DeArchiveType;
	public int ID_GameBox;
	public int Name_PaintingDecal;

	public String Name;

	public structItem(ItemTable data, List<structItem> color, List<structItem> texture, List<structItem> decal, List<structItem> box, List<structItem> gold, List<structItem> expbox,List<structItem> parachute,List<structItem> knapsack) {
		GameProp prop = CSVConfig.propconfig.get(data.propid);

		id = data.id;
		propid = data.propid;
		deadline = TimeCreator.GetStringTime(data.deadline);
		pos = data.pos;

		Type = prop.Type;
		LimitedTime = prop.LimitedTime;
		DeArchiveType = prop.DeArchiveType;
		ID_GameBox = prop.ID_GameBox;
		Name_PaintingDecal = prop.Name_PaintingDecal;

		Name = prop.Name;

		setItemList(Type, color, texture, decal, box, gold, expbox,parachute,knapsack);
	}

	public structItem(PlayerItem data, List<structItem> color, List<structItem> texture, List<structItem> decal, List<structItem> box, List<structItem> gold, List<structItem> expbox,List<structItem> parachute,List<structItem> knapsack) {
		GameProp prop = CSVConfig.propconfig.get(data.propid);

		id = data.id;
		propid = data.propid;
		deadline = TimeCreator.GetStringTime(data.deadline);
		pos = data.pos;

		Type = prop.Type;
		LimitedTime = prop.LimitedTime;
		DeArchiveType = prop.DeArchiveType;
		ID_GameBox = prop.ID_GameBox;
		Name_PaintingDecal = prop.Name_PaintingDecal;

		Name = prop.Name;

		setItemList(Type, color, texture, decal, box, gold, expbox,parachute,knapsack);
	}

	private void setItemList(String Type, List<structItem> color, List<structItem> texture, List<structItem> decal, List<structItem> box, List<structItem> gold, List<structItem> expbox,List<structItem> parachute,List<structItem> knapsack) {
		switch (ItemType.valueOf(Type)) {
		case DIY_PaintingColor:
			color.add(this);
			break;
		case DIY_PaintingTexture:
			texture.add(this);
			break;
		case DIY_PaintingDecal:
			decal.add(this);
			break;
		case GameBox:
			box.add(this);
			break;
		case TGold:
			gold.add(this);
			break;
		case EXPBox:
			expbox.add(this);
			break;
		case DIY_PaintingParachute:
			parachute.add(this);
			break;
		case DIY_PaintingKnapsack:
			knapsack.add(this);
			break;
		default:
			break;
		}
	}

	public long getId() {
		return id;
	}

	public int getPropid() {
		return propid;
	}

	public String getDeadline() {
		return deadline;
	}

	public short getPos() {
		return pos;
	}

	public String getType() {
		return Type;
	}

	public short getLimitedTime() {
		return LimitedTime;
	}

	public String getDeArchiveType() {
		return DeArchiveType;
	}

	public int getID_GameBox() {
		return ID_GameBox;
	}

	public int getName_PaintingDecal() {
		return Name_PaintingDecal;
	}

	public String getName() {
		return Name;
	}

	enum ItemType {

		/**
		 * 底色
		 */
		DIY_PaintingColor,

		/**
		 * 迷彩
		 */
		DIY_PaintingTexture,

		/**
		 * 涂装
		 */
		DIY_PaintingDecal,

		/**
		 * Box
		 */
		GameBox,

		/**
		 * TGold
		 */
		TGold,

		/**
		 * EXPBox
		 */
		EXPBox,
		
		/**
		 * 降落伞
		 */	
		DIY_PaintingParachute,
		
		/**
		 * 负重箱
		 */
		DIY_PaintingKnapsack

	}
}
