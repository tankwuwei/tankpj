package com.ft.gmserver.pages.gm;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.ft.gmserver.pages.pagebase;
import com.ft.gmserver.pages.paging;
import com.ft.gmserver.struct.structAccount;
import com.ft.gmserver.struct.structGameAccount;

import dbobjects.gamedb.AccountData;
import engine.db.DBManager;
import engine.db.client.DBType;
import engine.log.LogUtil;
import engine.string.StringUtil;

public class pageaccount extends pagebase {
	private structAccount info;

	public pageaccount(structAccount info) {
		this.info = info;
	}

	public String content(String param) {
		int pageNumber = 1;
		String accountid = null;
		String account = null;
		if (param != null) {
			Map<String, String> params = StringUtil.split(param, "&", "=");
			pageNumber = params.get("page") == null ? 1 : Integer.parseInt(params.get("page"));
			accountid = params.get("accountid");
			account = params.get("account");
		}
		try {
			StringBuilder hql = new StringBuilder();
			StringBuilder conn = new StringBuilder();
			conn.append("FROM AccountData WHERE 1=1");
			if (StringUtils.isNotEmpty(accountid)) {
				conn.append(" AND id=").append(accountid);
			}
			if (StringUtils.isNotEmpty(account)) {
				conn.append(" AND account like ").append("'%").append(account).append("%'");
			}
			hql.append(conn).append(" ORDER BY id ASC");
			hql.append(limit).append((pageNumber - 1) * pageSize).append("#").append(pageSize);// limit

			LogUtil.debug("hql: " + hql);

			List<AccountData> all = DBManager.getbyhql(AccountData.class, DBType.GameDB, hql.toString());

			List<structGameAccount> alldata = new ArrayList<>();
			for (AccountData a : all) {
				alldata.add(new structGameAccount(a));
			}

			Long count = (Long) DBManager.getbyhql(Object.class, DBType.GameDB, "select count(id) " + conn).get(0);
			paging paging = new paging(pageNumber, pageSize, count.intValue());

			context.put("all", alldata);
			context.put("accountinfo", info);
			context.put("pager", paging);
			context.put("accountid", accountid);
			context.put("account", account);
			StringBuilder paras = new StringBuilder();
			paras.append("accountid=");
			if (accountid != null)
				paras.append(accountid);
			paras.append("&account=");
			if (account != null)
				paras.append(account);
			context.put("paras", paras.toString());
			velocityEngine.mergeTemplate("templates/gm/account.vm", "utf-8", context, sw);
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
