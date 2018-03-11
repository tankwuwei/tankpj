package com.ft.gmserver.pages;

import engine.log.LogUtil;

/**
 * 创建gt账号
 * @author cxz
 *
 */
public class pageaccountcreate extends pagebase {
	@Override
	public String content() {
		try {
			velocityEngine.mergeTemplate("templates/accountcreate.vm", "utf-8", context, sw);
		} catch (Exception e) {
			LogUtil.warn(e);
		}
		return sw.toString();
	}
}
