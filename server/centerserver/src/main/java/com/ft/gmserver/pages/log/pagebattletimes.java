package com.ft.gmserver.pages.log;

import java.util.ArrayList;
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

public class pagebattletimes extends pagebase {

	@Override
	public String content() {
		// TODO Auto-generated method stub
		return null;
	}

	public String content(String param) {
		String starttime = null;
		String endtime = null;
		String zone = null;// 战区
		String time = null;// 统计间隔
		int flag = 0;
		try {
			if (param != null) {
				Map<String, String> params = StringUtil.split(param, "&", "=");
				starttime = parsetime(params.get("starttime"));
				endtime = parsetime(params.get("endtime"));
				zone = params.get("zone");
				time = params.get("time");
				flag = 1;
				int start = (int) ((DateUtils.string2Date(starttime + ":00", DateUtils.PATTERN_DATE_TIME).getTime()) / 1000);
				int end = (int) ((DateUtils.string2Date(endtime + ":00", DateUtils.PATTERN_DATE_TIME).getTime()) / 1000);

				StringBuilder hql = new StringBuilder();
				hql.append("select starttime FROM LogBattle WHERE zoneid=").append(zone);// 戰鬥開始時間
				hql.append(" AND starttime>=").append(start);
				hql.append(" AND endtime<=").append(end);
				hql.append(" ORDER BY id ASC");
				LogUtil.debug("hql: " + hql);
				List<Object> list = DBManager.getbyhql(Object.class, DBType.LogDB, hql.toString());

				StringBuilder x = new StringBuilder();
				StringBuilder y = new StringBuilder();
				x.append("[");
				y.append("[");

				List<Integer> xlist = new ArrayList<Integer>();
				for (int i = start; i < end; i += Integer.parseInt(time) * 60) {
					x.append("'").append(TimeCreator.GetStringTime(i)).append("',");
					xlist.add(i);
				}
				x.append("'").append(TimeCreator.GetStringTime(end)).append("'");
				xlist.add(end);

				y.append("{name:").append("'战区:").append(zone).append("',");
				y.append("data:[");

				int[] ylist = new int[xlist.size()];
				for (Object o : list) {
					int startTime = (int) o;
					for (int i = 0; i < xlist.size(); i++) {
						if (startTime < xlist.get(i)) {
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
			}

			String now = DateUtils.date2String(new Date(), DateUtils.PATTERN_DATE);
			context.put("starttime", starttime == null ? now + "T00:00" : parsetime2(starttime));
			context.put("endtime", endtime == null ? now + "T23:59" : parsetime2(endtime));
			context.put("selectzone", getZone(zone));
			context.put("zone", zone);
			context.put("time", time);
			context.put("flag", flag);
			velocityEngine.mergeTemplate("templates/log/battletimes.vm", "utf-8", context, sw);
		} catch (Exception e) {
			LogUtil.warn(e);
		}
		return sw.toString();
	}

	private String getZone(String zone) {
		StringBuilder sb = new StringBuilder();
		sb.append("<select name='zone' style='width:100px;'>");

		List<Object> list = DBManager.getbyhql(Object.class, DBType.LogDB, "select zoneid from LogBattle group by zoneid order by zoneid asc");
		for (Object o : list) {
			if (zone != null && zone.equals(o.toString()))
				sb.append("<option value='").append(o).append("' selected='selected'>").append(o).append("</option>");
			else
				sb.append("<option value='").append(o).append("'>").append(o).append("</option>");
		}

		sb.append("</select>");
		return sb.toString();
	}

}
