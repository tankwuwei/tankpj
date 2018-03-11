package com.ft.gmserver.pages.gm;

import java.util.ArrayList;
import java.util.List;

import com.ft.gmserver.Center.CenterClient;
import com.ft.gmserver.Center.CenterManager;
import com.ft.gmserver.pages.pagebase;

import dbobjects.gamedb.BlacklistTable;
import engine.bean.BeanFactory;
import engine.db.DBManager;
import engine.db.client.DBType;
import engine.log.LogUtil;
import engine.util.IPv4Util;

public class pageipblacklist extends pagebase {

	@Override
	public String content() {
		try {
			List<structIpblack> all = new ArrayList<>();

			CenterClient centerClient = BeanFactory.getBean(CenterManager.class).getCenterClient_GameServer();
			if (centerClient == null) {// GameServer未启动
				List<BlacklistTable> list = DBManager.get(BlacklistTable.class, DBType.GameDB);
				for (BlacklistTable o : list)
					all.add(new structIpblack(o.ip));
			} else {
				centerClient.getIPBlacklist();
				if (centerClient.ipblacklist == null || centerClient.ipblacklist.length == 0) {
					List<BlacklistTable> list = DBManager.get(BlacklistTable.class, DBType.GameDB);
					for (BlacklistTable o : list)
						all.add(new structIpblack(o.ip));
				} else {
					if (centerClient.ipblacklist != null) {
						for (int ip : centerClient.ipblacklist)
							all.add(new structIpblack(ip));
					}
				}
			}

			context.put("all", all);
			velocityEngine.mergeTemplate("templates/gm/ipblacklist.vm", "utf-8", context, sw);
		} catch (Exception e) {
			LogUtil.warn(e);
		}
		return sw.toString();
	}

	public class structIpblack {
		public int ip;
		public String ipstr;

		public structIpblack(int ip) {
			this.ip = ip;
			this.ipstr = IPv4Util.ipInt2Str(ip);
		}

		public int getIp() {
			return ip;
		}

		public String getIpstr() {
			return ipstr;
		}
	}
}
