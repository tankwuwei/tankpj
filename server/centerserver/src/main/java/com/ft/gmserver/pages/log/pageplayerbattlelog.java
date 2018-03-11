package com.ft.gmserver.pages.log;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.ft.gmserver.pages.pagebase;
import com.ft.gmserver.pages.paging;

import dbobjects.logdb.PlayerFightTable;
import engine.common.TimeCreator;
import engine.db.DBManager;
import engine.db.client.DBType;
import engine.log.LogUtil;
import engine.string.StringUtil;
import engine.util.DateUtils;

public class pageplayerbattlelog extends pagebase {
	private String param;

	public pageplayerbattlelog(String param) {
		this.param = param;
	}

	@Override
	public String content() {
		int pageNumber = 1;
		String nickname = null;
		String starttime = null;
		String endtime = null;

		try {
			List<structLogBattleDetail> alldata = new ArrayList<>();
			if (param != null) {
				Map<String, String> params = StringUtil.split(param, "&", "=");
				nickname = params.get("nickname");
				pageNumber = params.get("page") == null ? 1 : Integer.parseInt(params.get("page"));
				starttime = parsetime(params.get("starttime"));
				endtime = parsetime(params.get("endtime"));
				int start = (int) ((DateUtils.string2Date(starttime + ":00", DateUtils.PATTERN_DATE_TIME).getTime()) / 1000);
				int end = (int) ((DateUtils.string2Date(endtime + ":00", DateUtils.PATTERN_DATE_TIME).getTime()) / 1000);

				StringBuilder hql = new StringBuilder();
				StringBuilder conn = new StringBuilder();
				conn.append("FROM PlayerFightTable WHERE 1=1");
				if (StringUtils.isNotEmpty(nickname)) {
					conn.append(" AND nickname like '%").append(nickname).append("%'");
				}
				if (StringUtils.isNotEmpty(starttime)) {
					conn.append(" AND deadtime>=").append(start);
				}
				if (StringUtils.isNotEmpty(endtime)) {
					conn.append(" AND deadtime<=").append(end);
				}

				hql.append(conn).append(" ORDER BY deadtime desc");
				hql.append(limit).append((pageNumber - 1) * pageSize).append("#").append(pageSize);// limit

				List<PlayerFightTable> all = DBManager.getbyhql(PlayerFightTable.class, DBType.LogDB, hql.toString());

				for (PlayerFightTable a : all) {
					alldata.add(new structLogBattleDetail(a));
				}

				Long count = (Long) DBManager.getbyhql(Object.class, DBType.LogDB, "select count(id) " + conn).get(0);
				paging paging = new paging(pageNumber, pageSize, count.intValue());

				StringBuilder paras = new StringBuilder();
				paras.append("nickname=");
				if (StringUtils.isNotEmpty(nickname))
					paras.append(nickname);
				paras.append("&starttime=");
				if (StringUtils.isNotEmpty(starttime))
					paras.append(starttime);
				paras.append("&endtime=");
				if (StringUtils.isNotEmpty(endtime))
					paras.append(endtime);
				context.put("pager", paging);
				context.put("paras", paras.toString());
			}

			context.put("nickname", nickname);
			String now = DateUtils.date2String(new Date(), DateUtils.PATTERN_DATE);
			context.put("starttime", starttime == null ? now + "T00:00" : parsetime2(starttime));
			context.put("endtime", endtime == null ? now + "T23:59" : parsetime2(endtime));
			context.put("alldata", alldata);
			velocityEngine.mergeTemplate("templates/log/playerbattlelog.vm", "utf-8", context, sw);
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
