package com.ft.gmserver;

import com.ft.gmserver.httpclient.Client;

public abstract class SubPage {
	protected Client client;
	
	public abstract void AnalyzeParam(String str);
	public abstract void AnalyzeUri(String uri, String param);
	public abstract void update();
}
