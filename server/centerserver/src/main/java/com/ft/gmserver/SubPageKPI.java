package com.ft.gmserver;

import com.ft.gmserver.httpclient.Client;
import com.ft.gmserver.pages.kpi.pagekpi;
import com.ft.gmserver.pages.kpi.pageonlineclients;
import com.ft.gmserver.pages.kpi.pageregclients;

public class SubPageKPI extends SubPage {

	public SubPageKPI(Client client) {
		this.client = client;
	}

	public void AnalyzeParam(String str) {

	}

	public void AnalyzeUri(String uri, String param) {
		// 权限检查
		if (!client.accountinfo.getKpi()) {
			client.writeErrorPage("没有足够权限");
			return;
		}
		if (uri.equals("/kpi")) {
			kpi();
		} else if (uri.equals("/kpi/onlineclients")) {
			onlineclients(param);
		} else if (uri.equals("/kpi/regclients")) {
			regclients(param);
		}

	}

	private void kpi() {
		client.writeHeader();
		client.writeSlide();
		client.responseContent.append(new pagekpi().content());
		client.writeEnd();
	}

	private void onlineclients(String param) {
		client.writeHeader();
		client.writeSlide();
		client.responseContent.append(new pageonlineclients().content(param));
		client.writeEnd();
	}

	private void regclients(String param) {
		client.writeHeader();
		client.writeSlide();
		client.responseContent.append(new pageregclients().content(param));
		client.writeEnd();
	}

	@Override
	public void update() {
		// TODO Auto-generated method stub

	}

}
