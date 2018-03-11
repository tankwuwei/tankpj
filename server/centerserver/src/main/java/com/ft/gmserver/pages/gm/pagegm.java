package com.ft.gmserver.pages.gm;

import com.ft.gmserver.pages.pagebase;

import engine.log.LogUtil;

public class pagegm extends pagebase {
	public String content() {
		try {
			velocityEngine.mergeTemplate("templates/gm/gm.vm", "utf-8", context, sw);
		} catch (Exception e) {
			LogUtil.warn(e);
		}
		return sw.toString();
	}
}
