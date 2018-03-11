package com.ft.gmserver.pages.gm;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.ft.gmserver.pages.pagebase;
import com.ft.gmserver.pages.paging;
import com.ft.gmserver.struct.structAccount;
import com.ft.gmserver.struct.structPlayer;

import dbobjects.gamedb.PlayerData;
import engine.db.DBManager;
import engine.db.client.DBType;
import engine.log.LogUtil;
import engine.string.StringUtil;

public class pageplayer extends pagebase {
	private structAccount info;

	public pageplayer(structAccount info) {
		this.info = info;
	}

	public String content(String param) {
		int pageNumber = 1;
		String playerid = null;
		String accountid = null;
		String nickname = null;
		if (param != null) {
			Map<String, String> params = StringUtil.split(param, "&", "=");
			pageNumber = params.get("page") == null ? 1 : Integer.parseInt(params.get("page"));
			playerid = params.get("playerid");
			accountid = params.get("accountid");
			nickname = params.get("nickname");
		}
		try {
			StringBuilder hql = new StringBuilder();
			StringBuilder conn = new StringBuilder();
			conn.append("FROM PlayerData WHERE 1=1");
			if (StringUtils.isNotEmpty(playerid)) {
				conn.append(" AND id=").append(playerid);
			}
			if (StringUtils.isNotEmpty(accountid)) {
				conn.append(" AND account=").append(accountid);
			}
			if (StringUtils.isNotEmpty(nickname)) {
				conn.append(" AND nickname like ").append("'%").append(nickname).append("%'");
			}
			hql.append(conn).append(" ORDER BY id ASC");
			hql.append(limit).append((pageNumber - 1) * pageSize).append("#").append(pageSize);// limit

			LogUtil.debug("hql: " + hql);

			List<PlayerData> all = DBManager.getbyhql(PlayerData.class, DBType.GameDB, hql.toString());

			List<structPlayer> alldata = new ArrayList<>();
			for (PlayerData a : all) {
				alldata.add(new structPlayer(a));
			}

			Long count = (Long) DBManager.getbyhql(Object.class, DBType.GameDB, "select count(id) " + conn).get(0);
			paging paging = new paging(pageNumber, pageSize, count.intValue());

			context.put("all", alldata);
			context.put("accountinfo", info);
			context.put("pager", paging);
			context.put("playerid", playerid);
			context.put("accountid", accountid);
			context.put("nickname", nickname);
			StringBuilder paras = new StringBuilder();
			paras.append("playerid=");
			if (playerid != null)
				paras.append(playerid);
			paras.append("&accountid=");
			if (accountid != null)
				paras.append(accountid);
			paras.append("&nickname=");
			if (nickname != null)
				paras.append(nickname);
			context.put("paras", paras.toString());
			velocityEngine.mergeTemplate("templates/gm/player.vm", "utf-8", context, sw);
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
