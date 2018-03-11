package com.ft.gmserver.pages;

import engine.log.LogUtil;

public class pageheader extends pagebase {
	public String content() {
		try {
			velocityEngine.mergeTemplate("templates/header.vm", "utf-8", context, sw);
		} catch (Exception e) {
			LogUtil.warn(e);
		}
		return sw.toString();
	}
}
