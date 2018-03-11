package com.ft.gmserver.pages.gm;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.ft.gmserver.pages.pagebase;

import dbobjects.gamedb.MailPlayerTable;
import engine.db.DBManager;
import engine.db.client.Condition;
import engine.db.client.DBType;
import engine.log.LogUtil;
import engine.string.StringUtil;

public class pageemaillogdetail extends pagebase {
	private String paras;

	public pageemaillogdetail(String paras) {
		super();
		this.paras = paras;
	}

	@Override
	public String content() {
		try {
			Map<String, String> params = StringUtil.split(paras, "&", "=");
			int createtime = Integer.parseInt(params.get("createtime"));

			List<MailPlayerTable> list = DBManager.get(MailPlayerTable.class, DBType.GameDB, Condition.eq("createtime", createtime));

			List<structMailPlayer> all = new ArrayList<>();
			for (MailPlayerTable o : list)
				all.add(new structMailPlayer(o));

			context.put("all", all);
			velocityEngine.mergeTemplate("templates/gm/emaillogdetail.vm", "utf-8", context, sw);
		} catch (Exception e) {
			LogUtil.warn(e);
		}
		return sw.toString();
	}

	public class structMailPlayer {
		public String nickname;

		public short isread;// 0:未读;1:已读
		public short isget;// 0:未领取;1:已领取
		public short isdelete;// 0:正常;1:删除

		public structMailPlayer(MailPlayerTable o) {
			nickname = o.nickname;
			isread = o.isread;
			isget = o.isget;
			isdelete = o.isdelete;
		}

		public String getNickname() {
			return nickname;
		}

		public short getIsread() {
			return isread;
		}

		public short getIsget() {
			return isget;
		}

		public short getIsdelete() {
			return isdelete;
		}

	}
}
