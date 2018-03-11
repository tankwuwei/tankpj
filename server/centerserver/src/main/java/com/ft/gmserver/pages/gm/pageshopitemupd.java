package com.ft.gmserver.pages.gm;

import java.util.Map;

import com.ft.gmserver.pages.pagebase;

import engine.log.LogUtil;
import engine.string.StringUtil;

public class pageshopitemupd extends pagebase {

	public String content(String param) {
		try {
			Map<String, String> params = StringUtil.split(param, "&", "=");
			String propid = params.get("propid");
			String price = params.get("price");
			String type = params.get("type");
			String onsale = params.get("onsale");

			context.put("propid", propid);
			context.put("price", price);
			context.put("type", type);
			context.put("onsale", onsale);
			velocityEngine.mergeTemplate("templates/gm/shopitemupd.vm", "utf-8", context, sw);
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
