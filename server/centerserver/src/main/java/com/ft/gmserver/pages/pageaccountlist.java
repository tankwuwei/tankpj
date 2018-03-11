package com.ft.gmserver.pages;

import java.util.ArrayList;
import java.util.List;

import com.ft.gmserver.struct.structAccount;

import dbobjects.gmdb.GMAccount;
import engine.db.DBManager;
import engine.db.client.DBType;
import engine.log.LogUtil;

public class pageaccountlist extends pagebase {
	public String content() {
		try {
			List<GMAccount> all = DBManager.get(GMAccount.class, DBType.GMDB);
			List<structAccount> alldata = new ArrayList<>();
			for (GMAccount account : all) {
				alldata.add(new structAccount(account));
			}
			context.put("all", alldata);
			velocityEngine.mergeTemplate("templates/accountlist.vm", "utf-8", context, sw);
		} catch (Exception e) {
			LogUtil.warn(e);
		}
		return sw.toString();
	}
}
