package com.ft.gmserver;

import java.util.Map;

import com.ft.gmserver.Center.CenterClient;
import com.ft.gmserver.Center.CenterManager;
import com.ft.gmserver.httpclient.Client;
import com.ft.gmserver.pages.monitor.pageagentwebsocket;
import com.ft.gmserver.pages.monitor.pagemain;
import com.ft.gmserver.pages.monitor.pagemonitor;
import com.ft.gmserver.pages.monitor.pagemonitoragent;
import com.ft.gmserver.pages.monitor.pagemonitoragentprocess;
import com.ft.gmserver.pages.monitor.pagemonitorgameserver;
import com.ft.gmserver.pages.monitor.pagemonitorlist;

import engine.bean.BeanFactory;
import engine.string.StringUtil;
import engine.util.CMDUtil;

public class SubPageMonitor extends SubPage {

	public SubPageMonitor(Client client) {
		this.client = client;
	}

	@Override
	public void AnalyzeParam(String str) {
	}

	@Override
	public void AnalyzeUri(String uri, String param) {
		// 权限检查
		if (!client.accountinfo.getMonitor()) {
			client.writeErrorPage("没有足够权限");
			return;
		}
		if (uri.equals("/monitor")) {
			main();
		} else if (uri.equals("/monitor/agent")) {
			agent();
		} else if (uri.equals("/monitor/monitorlist")) {
			monitorlist(param);
		} else if (uri.equals("/monitor/monitoragent")) {
			monitoragent(param);
		} else if (uri.equals("/monitor/monitoragentprocess")) {
			monitoragentprocess(param);
		} else if (uri.equals("/monitor/gameserver")) {
			gameserver();
		} else if (uri.equals("/monitor/gameserver/start")) {
			gameserverstart();
		} else if (uri.equals("/monitor/gameserver/close")) {
			gameserverclose(param);
		}
	}

	private void main() {
		client.writeHeader();
		client.writeSlide();
		client.responseContent.append(new pagemain().content());
		client.writeEnd();
	}

	private void agent() {
		client.writeHeader();
		client.writeSlide();
		client.responseContent.append(new pageagentwebsocket().content());
		client.responseContent.append(new pagemonitor().content());
		client.writeEnd();
	}

	private void monitorlist(String param) {
		client.writeHeader();
		client.writeSlide();
		client.responseContent.append(new pagemonitorlist().content(param));
		client.writeEnd();
	}

	private void monitoragent(String param) {
		client.writeHeader();
		client.writeSlide();
		client.responseContent.append(new pagemonitoragent().content(param));
		client.writeEnd();
	}

	private void monitoragentprocess(String param) {
		client.writeHeader();
		client.writeSlide();
		client.responseContent.append(new pagemonitoragentprocess().content(param));
		client.writeEnd();
	}

	private void gameserver() {
		client.writeHeader();
		client.writeSlide();
		client.responseContent.append(new pagemonitorgameserver().content());
		client.writeEnd();
	}

	private void gameserverstart() {
		CMDUtil.runBAT(CenterServer.gameserver_script);
		gameserver();
	}

	private void gameserverclose(String param) {
		Map<String, String> params = StringUtil.split(param, "&", "=");
		int time = Integer.parseInt(params.get("time"));
		CenterClient centerClient = BeanFactory.getBean(CenterManager.class).getCenterClient_GameServer();
		if (centerClient == null) {
			client.writeErrorPage("GameServer 未启动！");
			return;
		}
		centerClient.sendShutdown(time);
		gameserver();
	}

	@Override
	public void update() {
	}

}
