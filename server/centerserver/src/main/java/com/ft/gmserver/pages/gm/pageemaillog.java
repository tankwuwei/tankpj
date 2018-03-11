package com.ft.gmserver.pages.gm;

import java.util.ArrayList;
import java.util.List;

import com.ft.gmserver.pages.pagebase;

import dbobjects.gamedb.MailPlayerTable;
import engine.common.TimeCreator;
import engine.db.DBManager;
import engine.db.client.DBType;
import engine.log.LogUtil;

public class pageemaillog extends pagebase {

	@Override
	public String content() {
		try {
			String hql = "from MailPlayerTable group by createtime order by createtime desc";
			List<MailPlayerTable> list = DBManager.getbyhql(MailPlayerTable.class, DBType.GameDB, hql);

			List<structMailPlayer> all = new ArrayList<>();
			for (MailPlayerTable o : list)
				all.add(new structMailPlayer(o));

			context.put("all", all);
			velocityEngine.mergeTemplate("templates/gm/emaillog.vm", "utf-8", context, sw);
		} catch (Exception e) {
			LogUtil.warn(e);
		}
		return sw.toString();
	}

	public class structMailPlayer {
		public String mailtitle;
		public String mailcontent;
		public int createtime;
		public String createtimeStr;
		public String propid;
		public String num;

		public structMailPlayer(MailPlayerTable o) {
			mailtitle = o.mailtitle;
			mailcontent = o.mailcontent;
			createtime = o.createtime;
			createtimeStr = TimeCreator.GetStringTime(o.createtime);
			propid = o.propid;
			num = o.num;
		}

		public String getMailtitle() {
			return mailtitle;
		}

		public String getMailcontent() {
			return mailcontent;
		}

		public int getCreatetime() {
			return createtime;
		}

		public String getCreatetimeStr() {
			return createtimeStr;
		}

		public String getPropid() {
			return propid;
		}

		public String getNum() {
			return num;
		}
	}
}
