package com.ft.gmserver.pages.log;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.ft.gmserver.pages.pagebase;

import dbobjects.logdb.PlayerFightTable;
import engine.common.TimeCreator;
import engine.db.DBManager;
import engine.db.client.Condition;
import engine.db.client.DBType;
import engine.log.LogUtil;
import engine.string.StringUtil;

public class pagebattlelogplayerdata extends pagebase {

	@Override
	public String content() {
		// TODO Auto-generated method stub
		return null;
	}

	public String content(String param) {
		try {
			Map<String, String> params = StringUtil.split(param, "&", "=");
			int zoneid = Integer.parseInt(params.get("zoneid"));
			long serverid = Long.parseLong(params.get("serverid"));
			List<PlayerFightTable> all = DBManager.get(PlayerFightTable.class, DBType.LogDB, Condition.eq("zoneid", zoneid), Condition.eq("serverid", serverid), Condition.asc("rank"));

			List<structLogBattleDetail> alldata = new ArrayList<>();
			for (PlayerFightTable a : all) {
				alldata.add(new structLogBattleDetail(a));
			}

			context.put("zoneid", zoneid);
			context.put("serverid", serverid);
			context.put("alldata", alldata);
			velocityEngine.mergeTemplate("templates/log/battlelogplayerdata.vm", "utf-8", context, sw);
		} catch (Exception e) {
			LogUtil.warn(e);
		}
		return sw.toString();

	}

	public class structLogBattleDetail {
		public long id;

		public int zoneid;// 区id
		public long serverid;// 战斗服务id

		public long playerid;
		public String nickname;

		public int rank;// 排名
		public int killed;// 击杀数
		public int damage;// 伤害数
		public int hurt;// 承受伤害数
		public int time;// 时长
		public int travel;// 米

		public String deadtime;// 死亡时间

		public int reward;// 总奖励
		public int rankreward;// 排名奖励
		public int killedreward;// 击杀奖励
		public int damagereward;// 伤害奖励

		structLogBattleDetail(PlayerFightTable o) {
			id = o.id;

			zoneid = o.zoneid;
			serverid = o.serverid;

			playerid = o.playerid;
			nickname = o.nickname;

			rank = o.rank;
			killed = o.killed;
			damage = o.damage;
			hurt = o.hurt;
			time = o.time / 60;// 分鐘
			travel = o.travel;

			deadtime = TimeCreator.GetStringTime(o.deadtime);

			reward = o.rankreward + o.killedreward + o.damagereward;
			rankreward = o.rankreward;
			killedreward = o.killedreward;
			damagereward = o.damagereward;
		}

		public long getId() {
			return id;
		}

		public int getZoneid() {
			return zoneid;
		}

		public long getServerid() {
			return serverid;
		}

		public long getPlayerid() {
			return playerid;
		}

		public String getNickname() {
			return nickname;
		}

		public int getRank() {
			return rank;
		}

		public int getKilled() {
			return killed;
		}

		public int getDamage() {
			return damage;
		}

		public int getHurt() {
			return hurt;
		}

		public int getTime() {
			return time;
		}

		public int getTravel() {
			return travel;
		}

		public String getDeadtime() {
			return deadtime;
		}

		public int getReward() {
			return reward;
		}

		public int getRankreward() {
			return rankreward;
		}

		public int getKilledreward() {
			return killedreward;
		}

		public int getDamagereward() {
			return damagereward;
		}

	}
}
