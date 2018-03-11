package com.ft.csv.resources;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Controller;

import com.ft.csv.resources.init.RoleInitConfig;
import com.ft.csv.resources.init.StartFightConfig;
import com.ft.csv.resources.init.testItemConfig;
import com.ft.csv.resources.item.GameBox;
import com.ft.csv.resources.item.GameExchange;
import com.ft.csv.resources.item.GameProp;
import com.ft.csv.resources.item.PaintingDecal;
import com.ft.csv.resources.item.ShopItemCSV;

import engine.log.LogUtil;
import engine.util.CSVReader;
import engine.util.ResourceBase;

/**
 * 资源配置管理器。 将预先读取所有的配置文件
 * 
 * @author cxz
 *
 */
@Controller
public class ResourceManager {
	public RoleInitConfig roleinitconfig = new RoleInitConfig();
	public testItemConfig testitemconfig = new testItemConfig();
	public Map<Integer, StartFightConfig> startfightconfig = new HashMap<>();

	public Map<Integer, GameProp> propconfig = new HashMap<>();
	public Map<Integer, GameBox> boxconfig = new HashMap<>();
	public Map<Integer, GameExchange> exchangeconfig = new HashMap<>();
	public Map<Integer, PaintingDecal> decalconfig = new HashMap<>();
	public Map<Integer, ShopItemCSV> shopitemconfig = new HashMap<>();

	@PostConstruct
	private void init() {
		parse("config/csv/init/RoleInit.csv", roleinitconfig);
		parse("config/csv/init/testItemInit.csv", testitemconfig);
		parse("config/csv/init/startfightplayer.csv", startfightconfig, StartFightConfig.class);

		// ====item start========
		parse("config/csv/item/GameProp.csv", propconfig, GameProp.class);
		parse("config/csv/item/GameBox.csv", boxconfig, GameBox.class);
		parse("config/csv/item/GameExchange.csv", exchangeconfig, GameExchange.class);
		parse("config/csv/item/PaintingDecal.csv", decalconfig, PaintingDecal.class);
		parse("config/csv/item/ShopItem.csv", shopitemconfig, ShopItemCSV.class);
		// ====item end========

	}

	<T extends ResourceBase> void parse(String path, Map<Integer, T> map, Class<T> c) {
		LogUtil.debug("parse " + path);
		List<Map<String, String>> list = CSVReader.read(path);
		try {
			for (Map<String, String> it : list) {
				T t = c.newInstance();
				t.parse(it);
				map.put(t.getId(), t);
			}
		} catch (Exception e) {
			LogUtil.warn(path + " parse error. " + e);
		}
	}

	<T extends ResourceBase> void parse(String path, List<T> l, Class<T> c) {
		List<Map<String, String>> list = CSVReader.read(path);
		try {
			for (Map<String, String> it : list) {
				T t = c.newInstance();
				t.parse(it);
				l.add(t);
			}
		} catch (Exception e) {
			LogUtil.warn(path + " parse error. " + e);
		}
	}

	<T extends ResourceBase> void parse(String path, T t) {
		List<Map<String, String>> list = CSVReader.read(path);
		if (list.isEmpty()) {
			return;
		}
		try {
			Map<String, String> it = list.get(0);
			t.parse(it);
		} catch (Exception e) {
			LogUtil.warn(path + " parse error. " + e);
		}
	}

}
