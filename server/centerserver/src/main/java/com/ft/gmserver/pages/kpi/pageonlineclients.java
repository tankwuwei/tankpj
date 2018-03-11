package com.ft.gmserver.pages.kpi;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.ft.gmserver.pages.pagebase;

import engine.common.TimeCreator;
import engine.db.DBManager;
import engine.db.client.DBType;
import engine.log.LogUtil;
import engine.string.StringUtil;
import engine.util.DateUtils;

public class pageonlineclients extends pagebase {

	@Override
	public String content() {
		// TODO Auto-generated method stub
		return null;
	}

	public String content(String param) {
		String starttime = null;
		String endtime = null;
		if (param != null) {
			Map<String, String> params = StringUtil.split(param, "&", "=");
			starttime = params.get("starttime") == null ? null : parsetime(params.get("starttime"));
			endtime = params.get("endtime") == null ? null : parsetime(params.get("endtime"));
		}

		Date date = new Date();
		String now = DateUtils.date2String(date, DateUtils.PATTERN_DATE);
		try {
			StringBuilder hql = new StringBuilder();
			hql.append("select clientcount, sampleTime FROM AgentServiceInfo WHERE processname='GameServer'");
			if (starttime != null && !starttime.equals("")) {
				hql.append(" AND sampleTime>=").append((DateUtils.string2Date(starttime + ":00", DateUtils.PATTERN_DATE_TIME).getTime()) / 1000);
			} else {
				hql.append(" AND sampleTime>=").append((DateUtils.string2Date(now + " 00:00:00", DateUtils.PATTERN_DATE_TIME).getTime()) / 1000);
			}
			if (endtime != null && !endtime.equals("")) {
				hql.append(" AND sampleTime<=").append((DateUtils.string2Date(endtime + ":00", DateUtils.PATTERN_DATE_TIME).getTime()) / 1000);
			} else {
				hql.append(" AND sampleTime<=").append((DateUtils.string2Date(now + " 23:59:59", DateUtils.PATTERN_DATE_TIME).getTime()) / 1000);
			}

			hql.append(" ORDER BY id ASC");

			LogUtil.debug("hql: " + hql);

			List<Object> all = DBManager.getbyhql(Object.class, DBType.GMDB, hql.toString());

			StringBuilder x = new StringBuilder();
			StringBuilder y = new StringBuilder();
			x.append("[");
			y.append("[");

			y.append("{name:").append("'在线玩家',");
			y.append("data:[");

			for (int i = 0; i < all.size(); i++) {
				Object[] d = (Object[]) all.get(i);
				int clientcount = 0;
				int sampleTime = 0;
				for (int k = 0; k < d.length; k++) {
					switch (k) {
					case 0:
						clientcount = (int) d[k];
						break;
					case 1:
						sampleTime = (int) d[k];
						break;
					default:
						break;
					}
				}
				if (i != all.size() - 1) {
					x.append("'").append(TimeCreator.GetStringTime(sampleTime)).append("',");
					y.append(clientcount).append(",");
				} else {
					x.append("'").append(TimeCreator.GetStringTime(sampleTime)).append("'");
					y.append(clientcount);
				}
			}
			y.append("]}");

			x.append("]");
			y.append("]");

			context.put("datax", x.toString());
			context.put("datay", y.toString());

			context.put("starttime", starttime == null ? now + "T00:00" : parsetime2(starttime));
			context.put("endtime", endtime == null ? now + "T23:59" : parsetime2(endtime));

			// 注册玩家
			context.put("regclients", DBManager.getbyhql(Object.class, DBType.GameDB, "select count(id) from AccountData").get(0));
			velocityEngine.mergeTemplate("templates/kpi/onlineclients.html", "utf-8", context, sw);
		} catch (Exception e) {
			LogUtil.warn(e);
		}
		return sw.toString();
	}

}
