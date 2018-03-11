package com.ft.gmserver.pages.gm;

import com.ft.gmserver.pages.pagebase;

import engine.log.LogUtil;

public class pageemail extends pagebase {

	@Override
	public String content() {
		try {
			velocityEngine.mergeTemplate("templates/gm/email.vm", "utf-8", context, sw);
		} catch (Exception e) {
			LogUtil.warn(e);
		}
		return sw.toString();
	}

}
