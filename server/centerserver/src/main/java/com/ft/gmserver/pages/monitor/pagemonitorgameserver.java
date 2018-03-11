package com.ft.gmserver.pages.monitor;

import com.ft.gmserver.Center.CenterClient;
import com.ft.gmserver.Center.CenterManager;
import com.ft.gmserver.pages.pagebase;

import engine.bean.BeanFactory;
import engine.log.LogUtil;

public class pagemonitorgameserver extends pagebase {
	public String content() {
		boolean isopen = true;
		String ip = null;
		int countdown = 0;
		try {
			CenterClient centerClient = BeanFactory.getBean(CenterManager.class).getCenterClient_GameServer();

			if (centerClient == null) {
				isopen = false;
			} else {
				ip = centerClient.getIp();
				if (centerClient.countdown > 0)
					countdown = centerClient.countdown;
			}

			context.put("isopen", isopen);
			context.put("ip", ip);
			context.put("countdown", countdown);
			velocityEngine.mergeTemplate("templates/monitor/gameserver.vm", "utf-8", context, sw);
		} catch (Exception e) {
			LogUtil.warn(e);
		}
		return sw.toString();
	}
}
