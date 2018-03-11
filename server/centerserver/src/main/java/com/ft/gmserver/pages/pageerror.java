package com.ft.gmserver.pages;

import engine.log.LogUtil;

public class pageerror extends pagebase {

	public pageerror(String errorstr) {
		context.put("error", errorstr);
	}
	public String content() {
		try{
			velocityEngine.mergeTemplate("templates/error.vm", "utf-8", context, sw);
		}
		catch (Exception e) {
			LogUtil.warn(e);
		}
		return sw.toString();
	}
}
