package com.ft.gmserver.pages.kpi;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.ft.gmserver.pages.pagebase;

import engine.db.DBManager;
import engine.db.client.DBType;
import engine.log.LogUtil;
import engine.string.StringUtil;
import engine.util.DateUtils;

public class pageregclients extends pagebase {

	@Override
	public String content() {
		// TODO Auto-generated method stub
		return null;
	}

	public String content(String p) {
		String starttime = null;
		String endtime = null;
		if (p != null) {
			Map<String, String> params = StringUtil.split(p, "&", "=");
			starttime = params.get("starttime");
			endtime = params.get("endtime");
		}

		try {
			String now = DateUtils.date2String(new Date(), DateUtils.PATTERN_DATE);
			int start = (int) ((DateUtils.string2Date(now.substring(0, 8) + "01" + " 00:00:00", DateUtils.PATTERN_DATE_TIME).getTime()) / 1000);
			int end = (int) ((DateUtils.string2Date(now + " 23:59:59", DateUtils.PATTERN_DATE_TIME).getTime()) / 1000);

			if (starttime != null && !starttime.equals("")) {
				start = (int) ((DateUtils.string2Date(starttime + " 00:00:00", DateUtils.PATTERN_DATE_TIME).getTime()) / 1000);
			}
			if (endtime != null && !endtime.equals("")) {
				end = (int) ((DateUtils.string2Date(endtime + " 23:59:59", DateUtils.PATTERN_DATE_TIME).getTime()) / 1000);
			}

			StringBuilder hql = new StringBuilder();
			hql.append("select createtime FROM AccountData WHERE 1=1");
			hql.append(" AND createtime>=").append(start);
			hql.append(" AND createtime<=").append(end);
			hql.append(" ORDER BY createtime ASC");

			LogUtil.debug("hql: " + hql);

			List<Object> all = DBManager.getbyhql(Object.class, DBType.GameDB, hql.toString());

			StringBuilder x = new StringBuilder();
			StringBuilder y = new StringBuilder();
			x.append("[");
			y.append("[");

			List<Integer> xlist = new ArrayList<Integer>();
			for (int i = start; i < end; i += 24 * 3600) {
				x.append("'").append(DateUtils.date2String(new Date(i * 1000L), DateUtils.PATTERN_DATE)).append("',");// 1000L not 1000
				xlist.add(i + 24 * 3600);
			}

			y.append("{name:").append("'注冊玩家',");
			y.append("data:[");

			int[] ylist = new int[xlist.size()];
			for (Object o : all) {
				int createtime = (int) o;
				for (int i = 0; i < xlist.size(); i++) {
					if (createtime < xlist.get(i)) {
						ylist[i]++;
						break;
					}
				}
			}

			for (int i = 0; i < ylist.length; i++) {
				if (i != ylist.length - 1)
					y.append(ylist[i]).append(",");
				else
					y.append(ylist[i]);
			}

			y.append("]}");

			x.append("]");
			y.append("]");

			context.put("datax", x.toString());
			context.put("datay", y.toString());

			context.put("starttime", starttime == null ? now.substring(0, 8) + "01" : starttime);
			context.put("endtime", endtime == null ? now : endtime);

			// 注册玩家
			context.put("regclients", DBManager.getbyhql(Object.class, DBType.GameDB, "select count(id) from AccountData").get(0));
			velocityEngine.mergeTemplate("templates/kpi/regclients.html", "utf-8", context, sw);
		} catch (Exception e) {
			LogUtil.warn(e);
		}
		return sw.toString();
	}

}
