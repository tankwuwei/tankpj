package com.ft.gmserver.pages.gm;

import java.util.Map;

import com.ft.gmserver.pages.pagebase;
import com.ft.gmserver.struct.structAccount;

import engine.log.LogUtil;
import engine.string.StringUtil;

public class pageaccountblock extends pagebase {
	private structAccount info;

	public pageaccountblock(structAccount info) {
		this.info = info;
	}

	public String content(String param) {

		Map<String, String> params = StringUtil.split(param, "&", "=");
		String ids = params.get("ids");
		try {

			context.put("ids", ids);
			velocityEngine.mergeTemplate("templates/gm/accountblock.vm", "utf-8", context, sw);
		} catch (Exception e) {
			LogUtil.warn(e);
		}
		return sw.toString();
	}

	@Override
	public String content() {
		// TODO Auto-generated method stub
		return null;
	}

}
