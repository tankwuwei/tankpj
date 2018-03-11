package com.ft.gmserver.pages.log;

import com.ft.gmserver.pages.pagebase;

import engine.log.LogUtil;

public class pagelog extends pagebase {
	public String content() {
		try {
			velocityEngine.mergeTemplate("templates/log/log.vm", "utf-8", context, sw);
		} catch (Exception e) {
			LogUtil.warn(e);
		}
		return sw.toString();
	}
}
