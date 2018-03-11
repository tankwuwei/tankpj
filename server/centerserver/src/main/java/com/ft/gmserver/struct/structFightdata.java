package com.ft.gmserver.struct;

import dbobjects.gamedb.PlayerFightStatiticTable;

public class structFightdata {

	public int playrounds;// 游戏总局数
	public int playtime;// 游戏总时长
	public int travel;// 游戏总行驶（米）
	public int avgtravel;// 平均每局游戏行驶（米）
	public int winrounds;// 获得第一名的次数
	public int top5rounds;// 获得前5名的总次数
	public float winratio;// 获得第一名的概率（吃鸡数/总局数）
	public float top5ratio;// 获得前5名的概率（前5数/总局数）
	public int destorys;// 击杀总数
	public int totaldamage;// 造成的总伤害
	public int avgdamage;// 平均伤害（总伤害/局数）
	public int kdratio;// 击杀/死亡比
	public int totalhurt;// 承受的总伤害
	public int avghurt;// 平均每局的承受伤害

	public structFightdata(PlayerFightStatiticTable data) {
		playrounds = data.playrounds;
		playtime = data.playtime / 60;// 分钟
		travel = data.travel;
		avgtravel = data.avgtravel;
		winrounds = data.winrounds;
		top5rounds = data.top5rounds;
		winratio = (float) data.winratio / 100;
		top5ratio = (float) data.top5ratio / 100;
		destorys = data.destorys;
		totaldamage = data.totaldamage;
		avgdamage = data.avgdamage;
		kdratio = data.kdratio;
		totalhurt = data.totalhurt;
		avghurt = data.avghurt;
	}

	public int getPlayrounds() {
		return playrounds;
	}

	public int getPlaytime() {
		return playtime;
	}

	public int getTravel() {
		return travel;
	}

	public int getAvgtravel() {
		return avgtravel;
	}

	public int getWinrounds() {
		return winrounds;
	}

	public int getTop5rounds() {
		return top5rounds;
	}

	public float getWinratio() {
		return winratio;
	}

	public float getTop5ratio() {
		return top5ratio;
	}

	public int getDestorys() {
		return destorys;
	}

	public int getTotaldamage() {
		return totaldamage;
	}

	public int getAvgdamage() {
		return avgdamage;
	}

	public int getKdratio() {
		return kdratio;
	}

	public int getTotalhurt() {
		return totalhurt;
	}

	public int getAvghurt() {
		return avghurt;
	}

}
