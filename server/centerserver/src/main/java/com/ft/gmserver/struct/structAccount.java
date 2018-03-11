package com.ft.gmserver.struct;

import com.ft.gmserver.Privilege;

import dbobjects.gmdb.GMAccount;
import engine.common.TimeCreator;

public class structAccount {
	public long id;
	public String account;
	public String logintime;
	public int state;	// ban
	public boolean gtmanager;
	public boolean kpi;
	public boolean monitor;
	public boolean gm;
	public boolean log;
	public boolean gmcase;

	public structAccount(GMAccount data) {
		id = data.id;
		account = data.account;
		logintime = TimeCreator.GetStringTime(data.lastlogintime);
		state = data.state;
		gtmanager = Privilege.IsManager(data.privilege);
		kpi = Privilege.CanKPI(data.privilege);
		monitor = Privilege.CanMonitor(data.privilege);
		gm = Privilege.CanGM(data.privilege);
		log = Privilege.CanLog(data.privilege);
		gmcase = Privilege.CanCase(data.privilege);
	}

	public long getId() {
		return id;
	}

	public String getAccount() {
		return account;
	}

	public String getLogintime() {
		return logintime;
	}

	public int getState() {
		return state;
	}

	public boolean getGtmanager() {
		return gtmanager;
	}

	public boolean getKpi() {
		return kpi;
	}

	public boolean getMonitor() {
		return monitor;
	}

	public boolean getGm() {
		return gm;
	}

	public boolean getLog() {
		return log;
	}

	public boolean getGmcase() {
		return gmcase;
	}

}
