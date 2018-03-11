package com.ft.gmserver.pages.gm;

import java.util.Map;

import com.ft.csv.resources.item.PaintingDecal;
import com.ft.gmserver.csv.CSVConfig;
import com.ft.gmserver.pages.pagebase;

import engine.log.LogUtil;
import engine.string.StringUtil;

public class pagedecal extends pagebase {

	public String content(String param) {

		Map<String, String> params = StringUtil.split(param, "&", "=");
		String id = params.get("id");
		String propid = params.get("propid");
		String Name_PaintingDecal = params.get("Name_PaintingDecal");
		try {
			PaintingDecal decal = CSVConfig.decalconfig.get(Integer.parseInt(Name_PaintingDecal));
			StringBuilder Sockets = new StringBuilder();

			for (short s : decal.pos) {
				switch (s) {
				case 3:
					Sockets.append("BodyFront ");
					break;
				case 4:
					Sockets.append("TurretFront ");
					break;
				case 5:
					Sockets.append("TurretBack ");
					break;
				case 6:
					Sockets.append("TurretLeft ");
					break;
				case 7:
					Sockets.append("TurretRight ");
					break;
				}
			}

			context.put("Sockets", Sockets);

			context.put("id", id);
			context.put("propid", propid);
			context.put("Name_PaintingDecal", Name_PaintingDecal);
			velocityEngine.mergeTemplate("templates/gm/decal.vm", "utf-8", context, sw);
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
