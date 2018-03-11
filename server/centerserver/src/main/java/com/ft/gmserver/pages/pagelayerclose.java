package com.ft.gmserver.pages;

import engine.log.LogUtil;

public class pagelayerclose extends pagebase {

	@Override
	public String content() {

		try {

			velocityEngine.mergeTemplate("templates/pagelayerclose.vm", "utf-8", context, sw);
		} catch (Exception e) {
			LogUtil.warn(e);
		}
		return sw.toString();
	}

}
