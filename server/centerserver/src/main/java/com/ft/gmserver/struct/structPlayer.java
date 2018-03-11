package com.ft.gmserver.struct;

import dbobjects.gamedb.PlayerData;
import engine.common.TimeCreator;

public class structPlayer {

	public long playerid;
	public long accountid;
	public String nickname;
	public int level;
	public int exp;
	public int money;
	public int gold;
	public String createTime;
	public String lastLoginTime;
	public String lastLogoutTime;
	public int allfightsecs;// 游戲時長（Min）
	public int givetimes;// 贈送次數
	public long fightserver;// 战服id

	public structPlayer(PlayerData data) {
		playerid = data.id;
		accountid = data.account;
		nickname = data.nickname;

		level = data.level;
		exp = data.exp;
		money = data.money;
		gold = data.gold;

		createTime = TimeCreator.GetStringTime(data.createTime);
		lastLoginTime = TimeCreator.GetStringTime(data.lastLoginTime);
		lastLogoutTime = TimeCreator.GetStringTime(data.lastLogoutTime);

		allfightsecs = data.allfightsecs / 60;
		givetimes = data.givetimes;
		fightserver = data.fightserver;
	}

	public long getPlayerid() {
		return playerid;
	}

	public long getAccountid() {
		return accountid;
	}

	public String getNickname() {
		return nickname;
	}

	public int getLevel() {
		return level;
	}

	public int getExp() {
		return exp;
	}

	public int getMoney() {
		return money;
	}

	public int getGold() {
		return gold;
	}

	public String getCreateTime() {
		return createTime;
	}

	public String getLastLoginTime() {
		return lastLoginTime;
	}

	public String getLastLogoutTime() {
		return lastLogoutTime;
	}

	public int getAllfightsecs() {
		return allfightsecs;
	}

	public int getGivetimes() {
		return givetimes;
	}

	public long getFightserver() {
		return fightserver;
	}

}
