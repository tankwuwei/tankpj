package com.ft.gmserver.pages;

import com.ft.gmserver.struct.structAccount;

import engine.log.LogUtil;

public class pageslide extends pagebase {
	private structAccount info;
	
	public pageslide(structAccount data) {
		info = data;
	}

	@Override
	public String content() {
		try {
			context.put("accountinfo", info);
			velocityEngine.mergeTemplate("templates/slide.vm", "utf-8", context, sw);
		} catch (Exception e) {
			LogUtil.warn(e);
		}
		return sw.toString();
	}

}
