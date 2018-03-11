package com.ft.gameserver.playermod.pack.item;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.ft.csv.resources.ResourceManager;
import com.ft.csv.resources.item.GameProp;

import engine.bean.BeanFactory;

@Controller
public class ItemFactory {

	@Autowired
	ResourceManager configs;

	public Item getItem(int propid) {
		Item item = null;
		propconfig = configs.propconfig.get(propid);

		switch (ItemType.valueOf(propconfig.Type)) {
		case DIY_PaintingColor:
			item = BeanFactory.getBean(Color.class);
			break;
		case DIY_PaintingTexture:
			item = BeanFactory.getBean(Texture.class);
			break;
		case DIY_PaintingDecal:
			item = BeanFactory.getBean(Decal.class);
			break;
		case GameBox:
			item = BeanFactory.getBean(Box.class);
			break;
		case TGold:
			item = BeanFactory.getBean(TGold.class);
			break;
		case EXPBox:
			item = BeanFactory.getBean(EXPBox.class);
			break;
		case DIY_PaintingParachute:
			item = BeanFactory.getBean(Parachute.class);
			break;
		case DIY_PaintingKnapsack:
			item = BeanFactory.getBean(Knapsack.class);
			break;
		default:
			break;
		}

		if (item != null)
			initItem(item, propid);// 初始化道具

		return item;
	}

	/**
	 * 初始化道具
	 * 
	 * @param item
	 * @param sid
	 */
	private void initItem(Item item, int propid) {
		item.propid = propid;
		item.propconfig = propconfig;// 道具配置
		if (StringUtils.equals(ItemType.GameBox.name(), propconfig.Type))
			((Box) item).boxconfig = configs.boxconfig.get(propconfig.ID_GameBox);// 箱子配置
		if (StringUtils.equals(ItemType.DIY_PaintingDecal.name(), propconfig.Type))
			((Decal) item).decalconfig = configs.decalconfig.get(propconfig.Name_PaintingDecal);// 涂装配置
	}

	private GameProp propconfig;
}
