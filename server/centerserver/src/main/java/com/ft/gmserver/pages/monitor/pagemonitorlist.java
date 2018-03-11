package com.ft.gmserver.pages.monitor;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.ft.gmserver.pages.pagebase;
import com.ft.gmserver.pages.paging;
import com.ft.gmserver.struct.structAgentSystemInfo;

import dbobjects.gmdb.AgentSystemInfo;
import engine.db.DBManager;
import engine.db.client.Condition;
import engine.db.client.DBType;
import engine.log.LogUtil;
import engine.string.StringUtil;

public class pagemonitorlist extends pagebase {
	public String content() {
		try {
			List<AgentSystemInfo> all = DBManager.get(AgentSystemInfo.class, DBType.LogDB, Condition.desc("id"));
			List<structAgentSystemInfo> alldata = new ArrayList<>();
			for (AgentSystemInfo account : all) {
				alldata.add(new structAgentSystemInfo(account));
			}
			context.put("all", alldata);
			velocityEngine.mergeTemplate("templates/monitor/monitorlist.vm", "utf-8", context, sw);
		} catch (Exception e) {
			LogUtil.warn(e);
		}
		return sw.toString();
	}
	
	
	
	
	
	public String content(String param) {
		int pageNumber = 1;
		int pageSize = 20;// 以后可以扩展，每页显示多少由用户自己选择
		if (param != null) {
			Map<String, String> params = StringUtil.split(param, "&", "=");
			pageNumber=Integer.parseInt(params.get("page"));
			//pageSize=Integer.parseInt(params.get("pageSize"));
		}
		
		try {
			List<AgentSystemInfo> all = DBManager.get(AgentSystemInfo.class, DBType.LogDB, Condition.desc("id"));
			List<structAgentSystemInfo> alldata = new ArrayList<>();
			for (AgentSystemInfo account : all) {
				alldata.add(new structAgentSystemInfo(account));
			}
			
			paging paging = new paging(pageNumber, pageSize, all.size());
			List<structAgentSystemInfo> alldata2 = new ArrayList<>();
			
			alldata2.addAll(alldata.subList((pageNumber-1)*pageSize, (pageNumber-1)*pageSize+pageSize<all.size()?(pageNumber-1)*pageSize+pageSize:all.size()));

			context.put("all", alldata2);
			context.put("url", "/monitor/monitorlist");
			context.put("pager", paging);
			velocityEngine.mergeTemplate("templates/monitor/monitorlist.vm", "utf-8", context, sw);
		} catch (Exception e) {
			LogUtil.warn(e);
		}
		return sw.toString();
	}
}
