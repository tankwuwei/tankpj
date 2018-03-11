package com.ft.gmserver.pages.monitor;

import com.ft.gmserver.pages.pagebase;

import engine.log.LogUtil;

public class pagemonitor extends pagebase {
	public String content() {
		try {
			velocityEngine.mergeTemplate("templates/monitor/monitor.vm", "utf-8", context, sw);
		} catch (Exception e) {
			LogUtil.warn(e);
		}
		return sw.toString();
	}
}
