package com.ft.gmserver.struct;

import dbobjects.gamedb.AccountData;
import engine.common.TimeCreator;

public class structGameAccount {

	public long id;
	public String account;
	public String passwd;
	public String Birthday;
	public String blocktime;
	public String createtime;
	public String lastlogintime;
	public String lastlogouttime;
	public String totalonlinetime;
	public int lastserverid;
	
	public structGameAccount(AccountData data) {
		id = data.id;
		account = data.account;
		passwd = data.passwd;
		Birthday = TimeCreator.GetStringTime(data.Birthday);
		blocktime = TimeCreator.GetStringTime(data.blocktime);
		createtime = TimeCreator.GetStringTime(data.createtime);
		lastlogintime = TimeCreator.GetStringTime(data.lastlogintime);
		lastlogouttime = TimeCreator.GetStringTime(data.lastlogouttime);
		totalonlinetime = TimeCreator.GetStringTime(data.totalonlinetime);
		lastserverid = data.lastserverid;

	}

	public long getId() {
		return id;
	}

	public String getAccount() {
		return account;
	}

	public String getPasswd() {
		return passwd;
	}

	public String getBirthday() {
		return Birthday;
	}

	public String getBlocktime() {
		return blocktime;
	}

	public String getCreatetime() {
		return createtime;
	}

	public String getLastlogintime() {
		return lastlogintime;
	}

	public String getLastlogouttime() {
		return lastlogouttime;
	}

	public String getTotalonlinetime() {
		return totalonlinetime;
	}

	public int getLastserverid() {
		return lastserverid;
	}


}
