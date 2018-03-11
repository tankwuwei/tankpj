package com.ft.gmserver;

import com.ft.gmserver.httpclient.Client;
import com.ft.gmserver.pages.log.pagebattlelog;
import com.ft.gmserver.pages.log.pagebattlelogplayerdata;
import com.ft.gmserver.pages.log.pagebattletimes;
import com.ft.gmserver.pages.log.pagelog;
import com.ft.gmserver.pages.log.pageplayerbattlelog;
import com.ft.gmserver.pages.log.pageplayerbuyitemlog;
import com.ft.gmserver.pages.log.pageplayeropenboxlog;

public class SubPageLog extends SubPage {

	public SubPageLog(Client client) {
		this.client = client;
	}

	public void AnalyzeParam(String str) {

	}

	public void AnalyzeUri(String uri, String param) {
		// 权限检查
		if (!client.accountinfo.getLog()) {
			client.writeErrorPage("没有足够权限");
			return;
		}

		if (uri.equals("/log")) {
			writeLog();
		} else if (uri.equals("/log/battletimes")) {
			battletimes(param);
		} else if (uri.equals("/log/battlelog")) {
			battlelog(param);
		} else if (uri.equals("/log/battlelog/playerdata")) {
			battlelogplayerdata(param);
		} else if (uri.equals("/log/playerbattlelog")) {// 玩家战斗数据
			playerbattlelog(param);
		} else if (uri.equals("/log/playeropenboxlog")) {// 玩家开箱记录
			playeropenboxlog(param);
		} else if (uri.equals("/log/playerbuyitemlog")) {// 玩家购买道具记录
			playerbuyitemlog(param);
		}

	}

	private void writeLog() {
		client.writeHeader();
		client.writeSlide();
		client.responseContent.append(new pagelog().content());
		client.writeEnd();
	}

	private void battletimes(String param) {
		client.writeHeader();
		client.writeSlide();
		client.responseContent.append(new pagebattletimes().content(param));
		client.writeEnd();
	}

	private void battlelog(String param) {
		client.writeHeader();
		client.writeSlide();
		client.responseContent.append(new pagebattlelog().content(param));
		client.writeEnd();
	}

	private void battlelogplayerdata(String param) {
		client.writeHeader();
		client.responseContent.append(new pagebattlelogplayerdata().content(param));
		client.writeEnd();
	}

	private void playerbattlelog(String param) {
		client.writeHeader();
		client.writeSlide();
		client.responseContent.append(new pageplayerbattlelog(param).content());
		client.writeEnd();
	}

	private void playeropenboxlog(String param) {
		client.writeHeader();
		client.writeSlide();
		client.responseContent.append(new pageplayeropenboxlog(param).content());
		client.writeEnd();
	}
	private void playerbuyitemlog(String param) {
		client.writeHeader();
		client.writeSlide();
		client.responseContent.append(new pageplayerbuyitemlog(param).content());
		client.writeEnd();
	}
	@Override
	public void update() {
		// TODO Auto-generated method stub

	}
}
