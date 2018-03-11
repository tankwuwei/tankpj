package com.ft.gmserver.pages.gm;

import com.ft.gmserver.pages.pagebase;

import engine.log.LogUtil;

public class pageshopitemadd extends pagebase {

	@Override
	public String content() {
		try {
			velocityEngine.mergeTemplate("templates/gm/shopitemadd.vm", "utf-8", context, sw);
		} catch (Exception e) {
			LogUtil.warn(e);
		}
		return sw.toString();
	}

}
