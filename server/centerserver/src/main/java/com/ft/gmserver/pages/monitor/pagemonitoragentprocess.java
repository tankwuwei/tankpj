package com.ft.gmserver.pages.monitor;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.ft.gmserver.pages.pagebase;

import dbobjects.gmdb.AgentServiceInfo;
import engine.common.TimeCreator;
import engine.db.DBManager;
import engine.db.client.DBType;
import engine.log.LogUtil;
import engine.string.StringUtil;
import engine.util.DateUtils;

public class pagemonitoragentprocess extends pagebase {

	@Override
	public String content() {
		// TODO Auto-generated method stub
		return null;
	}

	public String content(String param) {
		Map<String, String> params = StringUtil.split(param, "&", "=");
		String ip = params.get("ip");
		String processname = params.get("processname");
		String starttime = params.get("starttime") == null ? null : parsetime(params.get("starttime"));
		String endtime = params.get("endtime") == null ? null : parsetime(params.get("endtime"));

		Date date = new Date();
		String now = DateUtils.date2String(date, DateUtils.PATTERN_DATE);
		try {
			StringBuilder hql = new StringBuilder();
			hql.append("FROM AgentServiceInfo WHERE ip='").append(ip).append("' AND processname='").append(processname).append("'");
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

			hql.append(" ORDER BY sampleTime ASC");

			LogUtil.debug("hql: " + hql);

			List<AgentServiceInfo> all = DBManager.getbyhql(AgentServiceInfo.class, DBType.GMDB, hql.toString());

			StringBuilder x = new StringBuilder();
			StringBuilder y = new StringBuilder();
			x.append("[");
			y.append("[");

			StringBuilder y1 = new StringBuilder();
			StringBuilder y2 = new StringBuilder();
			y1.append("{name:").append("'cpu时间',");
			y1.append("data:[");
			y2.append("{name:").append("'占用内存',");
			y2.append("data:[");
			for (int i = 0; i < all.size(); i++) {
				AgentServiceInfo info = all.get(i);
				if (i != all.size() - 1) {
					x.append("'").append(TimeCreator.GetStringTime(info.sampleTime)).append("',");
					y1.append(info.cpu.replaceAll(":", "")).append(",");// 暂不处理
					y2.append(info.mem.substring(0, info.mem.length() - 1)).append(",");
				} else {
					x.append("'").append(TimeCreator.GetStringTime(info.sampleTime)).append("'");
					y1.append(info.cpu.replaceAll(":", ""));
					y2.append(info.mem.substring(0, info.mem.length() - 1));
				}
			}
			y1.append("]}");
			y2.append("]}");

			y.append(y1).append(",").append(y2);

			x.append("]");
			y.append("]");

			context.put("datax", x.toString());
			context.put("datay", y.toString());

			context.put("starttime", starttime == null ? now + "T00:00" : parsetime2(starttime));
			context.put("endtime", endtime == null ? now + "T23:59" : parsetime2(endtime));

			context.put("ip", ip);
			context.put("processname", processname);
			velocityEngine.mergeTemplate("templates/monitor/monitoragentprocess.html", "utf-8", context, sw);
		} catch (Exception e) {
			LogUtil.warn(e);
		}
		return sw.toString();
	}

}
