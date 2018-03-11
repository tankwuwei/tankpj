package com.ft.gmserver.pages;

import java.util.List;

import com.ft.gmserver.struct.structAccount;

import dbobjects.gmdb.GmCase;
import engine.db.DBManager;
import engine.db.client.DBType;
import engine.log.LogUtil;

public class pagegmmain extends pagebase {
	private structAccount info;

	public pagegmmain(structAccount info) {
		this.info = info;
	}

	@Override
	public String content() {
		try {
			List<GmCase> all = DBManager.get(GmCase.class, "closetime", 0, DBType.GMDB);
			context.put("all", all);
			context.put("accountinfo", info);
			velocityEngine.mergeTemplate("templates/gmmain.vm", "utf-8", context, sw);
		} catch (Exception e) {
			LogUtil.warn(e);
		}
		return sw.toString();
	}

}
