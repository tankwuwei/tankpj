package com.ft.gmserver.pages.log;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.ft.gmserver.pages.pagebase;
import com.ft.gmserver.pages.paging;

import dbobjects.logdb.LogBattle;
import engine.common.TimeCreator;
import engine.db.DBManager;
import engine.db.client.DBType;
import engine.log.LogUtil;
import engine.string.StringUtil;
import engine.util.DateUtils;

public class pagebattlelog extends pagebase {

	@Override
	public String content() {
		// TODO Auto-generated method stub
		return null;
	}

	public String content(String param) {
		int pageNumber = 1;
		String starttime = null;
		String endtime = null;
		int flag = 0;

		try {
			if (param != null) {
				Map<String, String> params = StringUtil.split(param, "&", "=");
				pageNumber = params.get("page") == null ? 1 : Integer.parseInt(params.get("page"));
				starttime = parsetime(params.get("starttime"));
				endtime = parsetime(params.get("endtime"));
				int start = (int) ((DateUtils.string2Date(starttime + ":00", DateUtils.PATTERN_DATE_TIME).getTime()) / 1000);
				int end = (int) ((DateUtils.string2Date(endtime + ":00", DateUtils.PATTERN_DATE_TIME).getTime()) / 1000);
				flag = 1;

				StringBuilder hql = new StringBuilder();
				StringBuilder conn = new StringBuilder();
				conn.append("FROM LogBattle WHERE 1=1");
				if (StringUtils.isNotEmpty(starttime)) {
					conn.append(" AND starttime>=").append(start);
				}
				if (StringUtils.isNotEmpty(endtime)) {
					conn.append(" AND endtime<=").append(end);
				}
				hql.append(conn).append(" ORDER BY id ASC");
				hql.append(limit).append((pageNumber - 1) * pageSize).append("#").append(pageSize);// limit

				LogUtil.debug("hql: " + hql);

				List<LogBattle> all = DBManager.getbyhql(LogBattle.class, DBType.LogDB, hql.toString());

				List<structLogBattle> alldata = new ArrayList<>();
				for (LogBattle a : all) {
					alldata.add(new structLogBattle(a));
				}

				Long count = (Long) DBManager.getbyhql(Object.class, DBType.LogDB, "select count(id) " + conn).get(0);
				paging paging = new paging(pageNumber, pageSize, count.intValue());

				context.put("all", alldata);
				context.put("pager", paging);
				StringBuilder paras = new StringBuilder();
				paras.append("starttime=");
				if (starttime != null)
					paras.append(starttime);
				paras.append("&endtime=");
				if (endtime != null)
					paras.append(endtime);
				context.put("paras", paras.toString());
			}

			String now = DateUtils.date2String(new Date(), DateUtils.PATTERN_DATE);
			context.put("starttime", starttime == null ? now + "T00:00" : parsetime2(starttime));
			context.put("endtime", endtime == null ? now + "T23:59" : parsetime2(endtime));
			context.put("flag", flag);
			velocityEngine.mergeTemplate("templates/log/battlelog.vm", "utf-8", context, sw);
		} catch (Exception e) {
			LogUtil.warn(e);
		}
		return sw.toString();

	}

	public class structLogBattle {
		public long id;
		public int zoneid;
		public long serverid;
		public String starttime;
		public String endtime;
		public int time;// 分钟
		public String playerids;
		public int playernum;

		structLogBattle(LogBattle o) {
			id = o.id;
			zoneid = o.zoneid;
			serverid = o.serverid;
			starttime = TimeCreator.GetStringTime(o.starttime);
			endtime = TimeCreator.GetStringTime(o.endtime);
			time = (o.endtime - o.starttime) / 60;
			playerids = o.playerids;
			playernum = o.playerids.split("\\|").length;
		}

		public long getId() {
			return id;
		}

		public int getZoneid() {
			return zoneid;
		}

		public long getServerid() {
			return serverid;
		}

		public String getStarttime() {
			return starttime;
		}

		public String getEndtime() {
			return endtime;
		}

		public String getPlayerids() {
			return playerids;
		}

		public int getTime() {
			return time;
		}

		public int getPlayernum() {
			return playernum;
		}

	}
}
