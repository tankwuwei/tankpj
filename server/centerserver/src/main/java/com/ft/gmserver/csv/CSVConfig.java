package com.ft.gmserver.csv;

import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.ft.csv.resources.ResourceManager;
import com.ft.csv.resources.item.GameBox;
import com.ft.csv.resources.item.GameProp;
import com.ft.csv.resources.item.PaintingDecal;

@Controller
public class CSVConfig {

	@Autowired
	ResourceManager configs;

	@PostConstruct
	private void init() {
		propconfig = configs.propconfig;
		boxconfig = configs.boxconfig;
		decalconfig = configs.decalconfig;
	}

	public static Map<Integer, GameProp> propconfig;// 道具配置
	public static Map<Integer, GameBox> boxconfig;// 箱子配置
	public static Map<Integer, PaintingDecal> decalconfig;// decal
}
