package com.ft.gmserver.pages.gm;

import java.util.Map;

import com.ft.csv.resources.item.GameBox;
import com.ft.gmserver.csv.CSVConfig;
import com.ft.gmserver.pages.pagebase;

import engine.log.LogUtil;
import engine.string.StringUtil;

public class pagebox extends pagebase {

	public String content(String param) {

		Map<String, String> params = StringUtil.split(param, "&", "=");
		String id = params.get("id");
		String propid = params.get("propid");
		String ID_GameBox = params.get("ID_GameBox");
		try {
			GameBox box = CSVConfig.boxconfig.get(Integer.parseInt(ID_GameBox));

			StringBuilder Content = new StringBuilder();
			for (int i = 0; i < box.GamePropID.size(); i++) {
				Content.append("(").append(box.GamePropID.get(i)).append(", ").append(box.Probability.get(i).intValue()).append(")").append("<br>");
			}

			context.put("Content", Content);
			context.put("Count", box.Count);

			context.put("id", id);
			context.put("propid", propid);
			context.put("ID_GameBox", ID_GameBox);
			velocityEngine.mergeTemplate("templates/gm/box.vm", "utf-8", context, sw);
		} catch (Exception e) {
			LogUtil.warn(e);
		}
		return sw.toString();
	}

	@Override
	public String content() {
		// TODO Auto-generated method stub
		return null;
	}

}
