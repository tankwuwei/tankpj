package com.ft.csv.resources.init;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.ft.csv.resources.ResourceManager;
import com.ft.csv.resources.StringExt;

import engine.bean.BeanFactory;
import engine.util.ResourceBase;

public class StartFightConfig extends ResourceBase {

	public int startfightplayer;
	public int startfightteam;
	public int onlinenum;

	@Override
	public void parse(Map<String, String> it) {
		id = StringExt.getInt(it.get("---"));
		startfightplayer = StringExt.getInt(it.get("startfightplayer"));
		startfightteam = StringExt.getInt(it.get("startfightteam"));
		onlinenum = StringUtils.isEmpty(it.get("onlinenum")) ? Integer.MAX_VALUE : StringExt.getInt(it.get("onlinenum"));
	}

	public static int getStartFightPlayer(int onlinenum) {
		List<StartFightConfig> list = new ArrayList<>(BeanFactory.getBean(ResourceManager.class).startfightconfig.values());
		sort(list);
		for (StartFightConfig o : list)
			if (onlinenum <= o.onlinenum)
				return o.startfightplayer;

		return 0;
	}

	public static int getStartFightTeam(int onlinenum) {
		List<StartFightConfig> list = new ArrayList<>(BeanFactory.getBean(ResourceManager.class).startfightconfig.values());
		sort(list);
		for (StartFightConfig o : list)
			if (onlinenum <= o.onlinenum)
				return o.startfightteam;

		return 0;
	}

	// ASC
	private static void sort(List<StartFightConfig> list) {
		Collections.sort(list, new Comparator<StartFightConfig>() {
			public int compare(StartFightConfig o1, StartFightConfig o2) {
				if (o1.onlinenum < o2.onlinenum) {
					return -1;
				} else if (o1.onlinenum == o2.onlinenum) {
					return 0;
				} else {
					return 1;
				}
			}
		});
	}
}
