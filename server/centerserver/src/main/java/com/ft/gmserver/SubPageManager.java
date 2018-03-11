package com.ft.gmserver;

import java.util.Map;

import com.ft.gmserver.httpclient.Client;
import com.ft.gmserver.httpclient.ClientManage;
import com.ft.gmserver.pages.pageaccountcreate;
import com.ft.gmserver.pages.pageaccountlist;
import com.ft.gmserver.struct.structAccount;

import dbobjects.gmdb.GMAccount;
import engine.db.DBManager;
import engine.db.client.DBType;
import engine.string.StringUtil;
import engine.util.Utility;
import io.netty.channel.ChannelHandlerContext;

public class SubPageManager extends SubPage {

	public static int AccountState_Normal = 0;
	public static int AccountState_Ban = 1;

	public SubPageManager(Client client) {
		this.client = client;
	}

	public void AnalyzeParam(String str) {
		if (client.action == GMAction.AccountCreate) {
			doCreateAccount(str);
		} else if (client.action == GMAction.AccountChangePassword) {
			doChangePassword(str);
		} else if (client.action == GMAction.AccountChangePrivilege) {
			doChangePrivilege(str);
		}
	}

	public void AnalyzeUri(String uri, String param) {
		// 权限检查
		if (!client.accountinfo.getGtmanager()) {
			client.writeErrorPage("没有足够权限");
			return;
		}
		if (uri.equals("/manager")) {
			writeList();
		} else if (uri.equals("/manager/banaccount")) {
			Map<String, String> params = StringUtil.split(param, "&", "=");
			banAccount(Long.parseLong(params.get("id")));
		} else if (uri.equals("/manager/unbanaccount")) {
			Map<String, String> params = StringUtil.split(param, "&", "=");
			unbanAccount(Long.parseLong(params.get("id")));
		} else if (uri.equals("/manager/createaccount")) {
			writeCreateAccount();
		} else if (uri.equals("/manager/submitaccountcreate")) {
			client.action = GMAction.AccountCreate;
		} else if (uri.equals("/manager/submitaccountchangepassword")) {
			client.action = GMAction.AccountChangePassword;
		} else if (uri.equals("/manager/submitaccountchangeprivilege")) {
			client.action = GMAction.AccountChangePrivilege;
		}

	}

	private void doChangePassword(String str) {
		Map<String, String> params = StringUtil.split(str, "&", "=");
		long id = Long.parseLong(params.get("id"));
		if (id > 0) {
			GMAccount account = DBManager.getUniqu(GMAccount.class, "id", id, DBType.GMDB);
			account.password = Utility.md5_32(params.get("password"));
			DBManager.saveOrUpdate(account, DBType.GMDB);
		}
		writeList();
	}

	private void doChangePrivilege(String str) {
		Map<String, String> params = StringUtil.split(str, "&", "=");
		long id = Long.parseLong(params.get("id"));
		if (id > 0) {
			GMAccount data = DBManager.getUniqu(GMAccount.class, "id", id, DBType.GMDB);
			createPrivilege(data, params);
			DBManager.saveOrUpdate(data, DBType.GMDB);
		}
		writeList();
	}

	private void createPrivilege(GMAccount data,Map<String, String> params){
		data.privilege = 0;
		if (params.get("gt") != null) {
			data.privilege |= Privilege.Manager;
		}
		if (params.get("kpi") != null) {
			data.privilege |= Privilege.KPI;
		}
		if (params.get("monitor") != null) {
			data.privilege |= Privilege.Monitor;
		}
		if (params.get("gm") != null) {
			data.privilege |= Privilege.GM;
		}
		if (params.get("log") != null) {
			data.privilege |= Privilege.Log;
		}
		if (params.get("case") != null) {
			data.privilege |= Privilege.Case;
		}
	}
	
	private void doCreateAccount(String str) {
		Map<String, String> params = StringUtil.split(str, "&", "=");
		GMAccount data = new GMAccount();
		data.account = params.get("account");
		data.password = Utility.md5_32(params.get("password"));
		createPrivilege(data, params);
		DBManager.saveOrUpdate(data, DBType.GMDB);
		writeList();
	}

	private void writeCreateAccount() {
		client.writeHeader();
		client.writeSlide();
		client.responseContent.append(new pageaccountcreate().content());
		client.writeEnd();
	}

	private void banAccount(long id) {
		GMAccount data = DBManager.getUniqu(GMAccount.class, "id", id, DBType.GMDB);
		if (data != null) {
			data.state = AccountState_Ban;
			DBManager.saveOrUpdate(data, DBType.GMDB);
			writeList();
		}
	}

	private void unbanAccount(long id) {
		GMAccount data = DBManager.getUniqu(GMAccount.class, "id", id, DBType.GMDB);
		if (data != null) {
			data.state = AccountState_Normal;
			DBManager.saveOrUpdate(data, DBType.GMDB);
			writeList();
		}

	}

	/**
	 * 账号列表
	 */
	private void writeList() {
		client.writeHeader();
		client.writeSlide();
		client.responseContent.append(new pageaccountlist().content());
		client.writeEnd();
	}

	@Override
	public void update() {
		// TODO Auto-generated method stub
		
	}
	
}
