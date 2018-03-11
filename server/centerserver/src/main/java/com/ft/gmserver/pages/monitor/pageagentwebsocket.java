package com.ft.gmserver.pages.monitor;

import com.ft.gmserver.CenterServer;
import com.ft.gmserver.pages.pagebase;

import engine.log.LogUtil;

public class pageagentwebsocket extends pagebase {

	@Override
	public String content() {
		try {
			context.put("websocketuri", CenterServer.websocketserver + "/monitor");
			velocityEngine.mergeTemplate("templates/websocket.vm", "utf-8", context, sw);
		} catch (Exception e) {
			LogUtil.warn(e);
		}
		return sw.toString();
	}

}
