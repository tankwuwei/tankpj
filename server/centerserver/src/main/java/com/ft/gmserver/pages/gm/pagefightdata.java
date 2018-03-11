package com.ft.gmserver.pages.gm;

import java.util.List;
import java.util.Map;

import com.ft.gmserver.pages.pagebase;
import com.ft.gmserver.struct.structFightdata;

import dbobjects.gamedb.PlayerFightStatiticTable;
import engine.db.DBManager;
import engine.db.client.DBType;
import engine.log.LogUtil;
import engine.string.StringUtil;

public class pagefightdata extends pagebase {

	public String content(String param) {

		Map<String, String> params = StringUtil.split(param, "&", "=");
		String playerid = params.get("playerid");
		String nickname = params.get("nickname");
		try {
			List<PlayerFightStatiticTable> all = DBManager.getbyhql(PlayerFightStatiticTable.class, DBType.GameDB, "from PlayerFightStatiticTable where playerid=" + playerid);

			structFightdata data = new structFightdata(new PlayerFightStatiticTable());
			if (all.size() > 0)
				data = new structFightdata(all.get(0));

			context.put("data", data);

			context.put("playerid", playerid);
			context.put("nickname", nickname);
			velocityEngine.mergeTemplate("templates/gm/fightdata.vm", "utf-8", context, sw);
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
