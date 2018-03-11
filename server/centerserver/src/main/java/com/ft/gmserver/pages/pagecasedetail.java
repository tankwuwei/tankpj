package com.ft.gmserver.pages;

import java.util.List;

import dbobjects.gmdb.CaseProcess;
import dbobjects.gmdb.GmCase;
import engine.log.LogUtil;

public class pagecasedetail extends pagebase {

	private GmCase gmCase;
	private List<CaseProcess> processes;
	
	public pagecasedetail(GmCase case1, List<CaseProcess> processes) {
		gmCase =case1;
		this.processes = processes;
	}
	
	@Override
	public String content() {
		try {
			context.put("data", gmCase);
			context.put("process", processes);
			velocityEngine.mergeTemplate("templates/casedetail.vm", "utf-8", context, sw);
		} catch (Exception e) {
			LogUtil.warn(e);
		}
		return sw.toString();
	}

}
