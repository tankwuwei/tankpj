package com.ft.gmserver.pages.kpi;

import com.ft.gmserver.pages.pagebase;

import engine.log.LogUtil;

public class pagekpi extends pagebase {
	public String content() {
		try {
			velocityEngine.mergeTemplate("templates/kpi/kpi.vm", "utf-8", context, sw);
		} catch (Exception e) {
			LogUtil.warn(e);
		}
		return sw.toString();
	}
}
