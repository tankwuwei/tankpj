package com.ft.gameserver.globalmods;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Controller;

import com.ft.gameserver.GameServer;
import com.ft.gameserver.basemod.GlobalBaseMod;
import com.ft.gameserver.clients.Client;
import com.ft.gameserver.player.Player;

import dbobjects.gamedb.PlayerFightStatiticTable;
import dbobjects.gamedb.RankDestorysTable;
import dbobjects.gamedb.RankLevelTable;
import dbobjects.gamedb.RankTop5RoundsTable;
import dbobjects.gamedb.RankWinRoundsTable;
import engine.db.DBHandler;
import engine.db.DBManager;
import engine.db.client.DBType;
import engine.net.CPacket;
import generated.cgame.packets.Code;
import generated.cgame.packets.client.CSRequestPlayerRankInfo;
import generated.cgame.packets.objects.PlayerRankInfo;
import generated.cgame.packets.server.SCPlayerRankInfo;

@Controller
public class PlayerRankMod extends GlobalBaseMod implements DBHandler {

	@Override
	protected void handle(CPacket packet) {
		Client client = (Client) packet.o;
		Player player = client.player;
		switch (packet.code) {
		case Code.CSRequestPlayerRankInfo:
			getPlayerRankInfo(player, ((CSRequestPlayerRankInfo) packet).flag);
			break;
		default:
			break;
		}
	}

	@PostConstruct
	private void regist() {
		DBManager.regist(this);
	}

	@Override
	public void initDB() {
		// ====
		// 初始化排行榜缓存
		// ====
		initwinList();
		inittop5List();
		initdestoryList();
		initlevelList();

		// 设置上榜阈值
		setOnRank_minvalue();
	}

	private void initwinList() {
		List<Object> list = DBManager.getbyhql(Object.class, DBType.GameDB, "SELECT a.playerid, a.nickname, a.winrounds as ord, a.winratio FROM PlayerFightStatiticTable a, RankWinRoundsTable b WHERE a.playerid=b.playerid ORDER BY ord DESC");

		long playerid = 0;
		String nickname = null;
		int wins = 0;
		int winratio = 0;

		for (Object o : list) {
			Object[] d = (Object[]) o;
			for (int i = 0; i < d.length; i++) {
				switch (i) {
				case 0:
					playerid = (long) d[i];
					break;
				case 1:
					nickname = (String) d[i];
					break;
				case 2:
					wins = (int) d[i];
					break;
				case 3:
					winratio = (int) d[i];
					break;
				default:
					break;
				}
			}
			PlayerRankInfo data = new PlayerRankInfo();
			data.playerid = playerid;
			data.nickname = nickname;
			data.wins = wins;
			data.winratio = winratio;
			winList.add(data);
		}
	}

	private void inittop5List() {
		List<Object> list = DBManager.getbyhql(Object.class, DBType.GameDB, "SELECT a.playerid, a.nickname, a.top5rounds as ord, a.top5ratio FROM PlayerFightStatiticTable a, RankTop5RoundsTable b WHERE a.playerid=b.playerid ORDER BY ord DESC");

		long playerid = 0;
		String nickname = null;
		int wins = 0;
		int winratio = 0;

		for (Object o : list) {
			Object[] d = (Object[]) o;
			for (int i = 0; i < d.length; i++) {
				switch (i) {
				case 0:
					playerid = (long) d[i];
					break;
				case 1:
					nickname = (String) d[i];
					break;
				case 2:
					wins = (int) d[i];
					break;
				case 3:
					winratio = (int) d[i];
					break;
				default:
					break;
				}
			}
			PlayerRankInfo data = new PlayerRankInfo();
			data.playerid = playerid;
			data.nickname = nickname;
			data.wins = wins;
			data.winratio = winratio;
			top5List.add(data);
		}
	}

	private void initdestoryList() {
		List<Object> list = DBManager.getbyhql(Object.class, DBType.GameDB, "SELECT a.playerid, a.nickname, a.destorys as ord, a.kdratio FROM PlayerFightStatiticTable a, RankDestorysTable b WHERE a.playerid=b.playerid ORDER BY ord DESC");

		long playerid = 0;
		String nickname = null;
		int wins = 0;
		int winratio = 0;

		for (Object o : list) {
			Object[] d = (Object[]) o;
			for (int i = 0; i < d.length; i++) {
				switch (i) {
				case 0:
					playerid = (long) d[i];
					break;
				case 1:
					nickname = (String) d[i];
					break;
				case 2:
					wins = (int) d[i];
					break;
				case 3:
					winratio = (int) d[i];
					break;
				default:
					break;
				}
			}
			PlayerRankInfo data = new PlayerRankInfo();
			data.playerid = playerid;
			data.nickname = nickname;
			data.wins = wins;
			data.winratio = winratio;
			destoryList.add(data);
		}
	}

	private void initlevelList() {
		List<Object> list = DBManager.getbyhql(Object.class, DBType.GameDB, "SELECT a.playerid, a.nickname, a.level as ord, a.playtime FROM PlayerFightStatiticTable a, RankLevelTable b WHERE a.playerid=b.playerid ORDER BY ord DESC");

		long playerid = 0;
		String nickname = null;
		int wins = 0;
		int winratio = 0;

		for (Object o : list) {
			Object[] d = (Object[]) o;
			for (int i = 0; i < d.length; i++) {
				switch (i) {
				case 0:
					playerid = (long) d[i];
					break;
				case 1:
					nickname = (String) d[i];
					break;
				case 2:
					wins = (int) d[i];
					break;
				case 3:
					winratio = (int) d[i];
					break;
				default:
					break;
				}
			}
			PlayerRankInfo data = new PlayerRankInfo();
			data.playerid = playerid;
			data.nickname = nickname;
			data.wins = wins;
			data.winratio = winratio;
			levelList.add(data);
		}

		if (levelList.size() > 0)
			min_level = levelList.get(levelList.size() - 1).wins;
	}

	/**
	 * 处理排行榜
	 * 
	 * @param data
	 *            待处理的<玩家战斗统计数据>
	 */
	public void doPlayerRank(PlayerFightStatiticTable data) {
		if (data.winrounds > min_win || winList.size() < GameServer.playerRankNum)
			updWinList(data);
		if (data.top5rounds > min_top5win || top5List.size() < GameServer.playerRankNum)
			updTop5List(data);
		if (data.destorys > min_destory || destoryList.size() < GameServer.playerRankNum)
			updDestoryList(data);

		// 设置上榜阈值
		setOnRank_minvalue();
	}

	private void updWinList(PlayerFightStatiticTable data) {
		int i = 0;
		while (i < winList.size()) {
			if (winList.get(i).playerid == data.playerid)
				break;
			i++;
		}

		if (i < winList.size()) {// 在榜
			winList.remove(i);
		} else {// 不在榜
			if (winList.size() == GameServer.playerRankNum) {// 排行榜已滿
				PlayerRankInfo last = winList.remove(winList.size() - 1);
				RankWinRoundsTable del = DBManager.getUniqu(RankWinRoundsTable.class, "playerid", last.playerid, DBType.GameDB);
				DBManager.delete(RankWinRoundsTable.class, del.id, DBType.GameDB);
			}

			RankWinRoundsTable d = new RankWinRoundsTable();
			d.playerid = data.playerid;
			DBManager.saveOrUpdate(d, DBType.GameDB);
		}

		PlayerRankInfo info = new PlayerRankInfo();
		info.playerid = data.playerid;
		info.nickname = data.nickname;
		info.wins = data.winrounds;
		info.winratio = data.winratio;
		winList.add(info);
		this.sort(winList);
	}

	private void updTop5List(PlayerFightStatiticTable data) {
		int i = 0;
		while (i < top5List.size()) {
			if (top5List.get(i).playerid == data.playerid)
				break;
			i++;
		}

		if (i < top5List.size()) {// 在榜
			top5List.remove(i);
		} else {// 不在榜
			if (top5List.size() == GameServer.playerRankNum) {// 排行榜已滿
				PlayerRankInfo last = top5List.remove(top5List.size() - 1);
				RankTop5RoundsTable del = DBManager.getUniqu(RankTop5RoundsTable.class, "playerid", last.playerid, DBType.GameDB);
				DBManager.delete(RankTop5RoundsTable.class, del.id, DBType.GameDB);
			}

			RankTop5RoundsTable d = new RankTop5RoundsTable();
			d.playerid = data.playerid;
			DBManager.saveOrUpdate(d, DBType.GameDB);
		}

		PlayerRankInfo info = new PlayerRankInfo();
		info.playerid = data.playerid;
		info.nickname = data.nickname;
		info.wins = data.top5rounds;
		info.winratio = data.top5ratio;
		top5List.add(info);
		this.sort(top5List);
	}

	private void updDestoryList(PlayerFightStatiticTable data) {
		int i = 0;
		while (i < destoryList.size()) {
			if (destoryList.get(i).playerid == data.playerid)
				break;
			i++;
		}

		if (i < destoryList.size()) {// 在榜
			destoryList.remove(i);
		} else {// 不在榜
			if (destoryList.size() == GameServer.playerRankNum) {// 排行榜已滿
				PlayerRankInfo last = destoryList.remove(destoryList.size() - 1);
				RankDestorysTable del = DBManager.getUniqu(RankDestorysTable.class, "playerid", last.playerid, DBType.GameDB);
				DBManager.delete(RankDestorysTable.class, del.id, DBType.GameDB);
			}

			RankDestorysTable d = new RankDestorysTable();
			d.playerid = data.playerid;
			DBManager.saveOrUpdate(d, DBType.GameDB);
		}

		PlayerRankInfo info = new PlayerRankInfo();
		info.playerid = data.playerid;
		info.nickname = data.nickname;
		info.wins = data.destorys;
		info.winratio = data.kdratio;
		destoryList.add(info);
		this.sort(destoryList);
	}

	private void updLevelList(PlayerFightStatiticTable data) {
		int i = 0;
		while (i < levelList.size()) {
			if (levelList.get(i).playerid == data.playerid)
				break;
			i++;
		}

		if (i < levelList.size()) {// 在榜
			levelList.remove(i);
		} else {// 不在榜
			if (levelList.size() == GameServer.playerRankNum) {// 排行榜已滿
				PlayerRankInfo last = levelList.remove(levelList.size() - 1);
				RankLevelTable del = DBManager.getUniqu(RankLevelTable.class, "playerid", last.playerid, DBType.GameDB);
				DBManager.delete(RankLevelTable.class, del.id, DBType.GameDB);
			}

			RankLevelTable d = new RankLevelTable();
			d.playerid = data.playerid;
			DBManager.saveOrUpdate(d, DBType.GameDB);
		}

		PlayerRankInfo info = new PlayerRankInfo();
		info.playerid = data.playerid;
		info.nickname = data.nickname;
		info.wins = data.level;
		info.winratio = data.playtime;
		levelList.add(info);
		this.sort(levelList);

		if (levelList.size() > 0)
			min_level = levelList.get(levelList.size() - 1).wins;
	}

	/**
	 * PlayerRankInfo.wins desc
	 * 
	 * @param list
	 */
	private void sort(List<PlayerRankInfo> list) {
		Collections.sort(list, new Comparator<PlayerRankInfo>() {
			public int compare(PlayerRankInfo o1, PlayerRankInfo o2) {
				if (o1.wins < o2.wins) {
					return 1;
				} else if (o1.wins == o2.wins) {
					return 0;
				} else {
					return -1;
				}
			}
		});
	}

	private void getPlayerRankInfo(Player player, byte flag) {
		SCPlayerRankInfo data = new SCPlayerRankInfo();
		PlayerRankInfo[] arr = null;

		// 0:吃鸡;1:K/D;2:TOP5;3:Level
		if (flag == 0) {
			int len = winList.size();
			arr = new PlayerRankInfo[len];
			for (int i = 0; i < len; i++) {
				arr[i] = winList.get(i);
			}
		} else if (flag == 1) {
			int len = destoryList.size();
			arr = new PlayerRankInfo[len];
			for (int i = 0; i < len; i++) {
				arr[i] = destoryList.get(i);
			}
		} else if (flag == 2) {
			int len = top5List.size();
			arr = new PlayerRankInfo[len];
			for (int i = 0; i < len; i++) {
				arr[i] = top5List.get(i);
			}
		} else if (flag == 3) {
			int len = levelList.size();
			arr = new PlayerRankInfo[len];
			for (int i = 0; i < len; i++) {
				arr[i] = levelList.get(i);
			}
		}

		data.flag = flag;
		data.info = arr;
		player.Send(data);
	}

	// 设置上榜阈值
	private void setOnRank_minvalue() {
		if (winList.size() > 0)
			min_win = winList.get(winList.size() - 1).wins;
		if (top5List.size() > 0)
			min_top5win = top5List.get(top5List.size() - 1).wins;
		if (destoryList.size() > 0)
			min_destory = destoryList.get(destoryList.size() - 1).wins;
	}

	@Override
	public void atfterInitDB() {
		// TODO Auto-generated method stub

	}

	@Override
	public void Update() {
		// TODO Auto-generated method stub

	}

	@Override
	public void onPlayerLogin(Player player) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onPlayerLogout(Player player) {
		// TODO Auto-generated method stub

	}

	public void updLevelRank(Player player) {
		PlayerFightStatiticTable data = DBManager.getUniqu(PlayerFightStatiticTable.class, "playerid", player.getId(), DBType.GameDB);
		data.level = player.data.level;
		DBManager.saveOrUpdate(data, DBType.GameDB);

		if (data.level > min_level || levelList.size() < GameServer.playerRankNum)
			updLevelList(data);
	}

	List<PlayerRankInfo> winList = new ArrayList<PlayerRankInfo>();// 吃鸡
	List<PlayerRankInfo> top5List = new ArrayList<PlayerRankInfo>();// TOP5
	List<PlayerRankInfo> destoryList = new ArrayList<PlayerRankInfo>();// K/D
	List<PlayerRankInfo> levelList = new ArrayList<PlayerRankInfo>();// Level
	int min_win = 0;
	int min_top5win = 0;
	int min_destory = 0;
	int min_level = 0;

	@Override
	public void onDayChanged() {
		// TODO Auto-generated method stub

	}

}
