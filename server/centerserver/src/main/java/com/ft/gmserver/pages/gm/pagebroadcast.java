package com.ft.gmserver.pages.gm;

import java.util.ArrayList;
import java.util.List;

import com.ft.gmserver.pages.pagebase;

import dbobjects.gamedb.BroadcastTable;
import engine.common.TimeCreator;
import engine.db.DBManager;
import engine.db.client.Condition;
import engine.db.client.DBType;
import engine.log.LogUtil;

public class pagebroadcast extends pagebase {

	@Override
	public String content() {
		try {
			List<BroadcastTable> list = DBManager.get(BroadcastTable.class, DBType.GameDB, Condition.desc("createtime"));

			List<structBroadcast> all = new ArrayList<>();
			for (BroadcastTable o : list)
				all.add(new structBroadcast(o));

			context.put("all", all);
			velocityEngine.mergeTemplate("templates/gm/broadcast.vm", "utf-8", context, sw);
		} catch (Exception e) {
			LogUtil.warn(e);
		}
		return sw.toString();
	}

	public class structBroadcast {
		public long id;
		public String content;
		public String createtime;
		public String status;// 0:正常; 1:取消

		public structBroadcast(BroadcastTable o) {
			id = o.id;
			content = o.content;
			createtime = TimeCreator.GetStringTime(o.createtime);
			status = o.status == 0 ? "正常" : "已取消";
		}

		public long getId() {
			return id;
		}

		public String getContent() {
			return content;
		}

		public String getCreatetime() {
			return createtime;
		}

		public String getStatus() {
			return status;
		}

	}
}
