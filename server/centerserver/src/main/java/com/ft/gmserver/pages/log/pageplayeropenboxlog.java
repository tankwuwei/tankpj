package com.ft.gmserver.pages.log;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.ft.gmserver.csv.CSVConfig;
import com.ft.gmserver.pages.pagebase;
import com.ft.gmserver.pages.paging;

import dbobjects.logdb.Logopenbox;
import engine.common.TimeCreator;
import engine.db.DBManager;
import engine.db.client.DBType;
import engine.log.LogUtil;
import engine.string.StringUtil;
import engine.util.DateUtils;

public class pageplayeropenboxlog extends pagebase {
	private String param;

	public pageplayeropenboxlog(String param) {
		this.param = param;
	}

	@Override
	public String content() {
		int pageNumber = 1;
		String nickname = null;
		String starttime = null;
		String endtime = null;

		try {
			List<structLogopenbox> alldata = new ArrayList<>();
			if (param != null) {
				Map<String, String> params = StringUtil.split(param, "&", "=");
				nickname = params.get("nickname");
				pageNumber = params.get("page") == null ? 1 : Integer.parseInt(params.get("page"));
				starttime = parsetime(params.get("starttime"));
				endtime = parsetime(params.get("endtime"));
				int start = (int) ((DateUtils.string2Date(starttime + ":00", DateUtils.PATTERN_DATE_TIME).getTime()) / 1000);
				int end = (int) ((DateUtils.string2Date(endtime + ":00", DateUtils.PATTERN_DATE_TIME).getTime()) / 1000);

				StringBuilder hql = new StringBuilder();
				StringBuilder conn = new StringBuilder();
				conn.append("FROM Logopenbox WHERE 1=1");
				if (StringUtils.isNotEmpty(nickname)) {
					conn.append(" AND nickname like '%").append(nickname).append("%'");
				}
				if (StringUtils.isNotEmpty(starttime)) {
					conn.append(" AND time>=").append(start);
				}
				if (StringUtils.isNotEmpty(endtime)) {
					conn.append(" AND time<=").append(end);
				}

				hql.append(conn).append(" ORDER BY time desc");
				hql.append(limit).append((pageNumber - 1) * pageSize).append("#").append(pageSize);// limit

				List<Logopenbox> all = DBManager.getbyhql(Logopenbox.class, DBType.LogDB, hql.toString());

				for (Logopenbox a : all) {
					alldata.add(new structLogopenbox(a));
				}

				Long count = (Long) DBManager.getbyhql(Object.class, DBType.LogDB, "select count(id) " + conn).get(0);
				paging paging = new paging(pageNumber, pageSize, count.intValue());

				StringBuilder paras = new StringBuilder();
				paras.append("nickname=");
				if (StringUtils.isNotEmpty(nickname))
					paras.append(nickname);
				paras.append("&starttime=");
				if (StringUtils.isNotEmpty(starttime))
					paras.append(starttime);
				paras.append("&endtime=");
				if (StringUtils.isNotEmpty(endtime))
					paras.append(endtime);
				context.put("pager", paging);
				context.put("paras", paras.toString());
			}

			context.put("nickname", nickname);
			String now = DateUtils.date2String(new Date(), DateUtils.PATTERN_DATE);
			context.put("starttime", starttime == null ? now + "T00:00" : parsetime2(starttime));
			context.put("endtime", endtime == null ? now + "T23:59" : parsetime2(endtime));
			context.put("alldata", alldata);
			velocityEngine.mergeTemplate("templates/log/playeropenboxlog.vm", "utf-8", context, sw);
		} catch (Exception e) {
			LogUtil.warn(e);
		}
		return sw.toString();

	}

	public class structLogopenbox {
		public long id;
		public long playerid;
		public String nickname;

		public int boxid;
		public String boxname;
		public String propidInbox;// 箱子中的道具id
		public String numInbox;// 數量

		public String result;
		public String time;

		structLogopenbox(Logopenbox o) {
			id = o.id;
			playerid = o.playerid;
			nickname = o.nickname;

			boxid = o.boxid;
			boxname = CSVConfig.propconfig.get(o.boxid).Name;
			propidInbox = o.propidInbox;
			numInbox = o.numInbox;

			result = o.result == 0 ? "成功" : "失败";
			time = TimeCreator.GetStringTime(o.time);
		}

		public long getId() {
			return id;
		}

		public long getPlayerid() {
			return playerid;
		}

		public String getNickname() {
			return nickname;
		}

		public int getBoxid() {
			return boxid;
		}

		public String getBoxname() {
			return boxname;
		}

		public String getPropidInbox() {
			return propidInbox;
		}

		public String getNumInbox() {
			return numInbox;
		}

		public String getResult() {
			return result;
		}

		public String getTime() {
			return time;
		}

	}
}
