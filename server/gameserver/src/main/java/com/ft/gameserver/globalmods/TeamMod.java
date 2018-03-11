package com.ft.gameserver.globalmods;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.ft.gameserver.GameServer;
import com.ft.gameserver.basemod.GlobalBaseMod;
import com.ft.gameserver.clients.Client;
import com.ft.gameserver.player.Player;
import com.ft.gameserver.playermod.pack.PackMod;
import com.ft.gameserver.utility.IdCreator;

import common.ErrorCode;
import dbobjects.gamedb.PlayerData;
import dbobjects.gamedb.TeamTable;
import engine.common.TimeCreator;
import engine.db.DBHandler;
import engine.db.DBManager;
import engine.db.client.DBType;
import engine.net.CPacket;
import generated.cgame.packets.Code;
import generated.cgame.packets.client.CSSearchPlayer;
import generated.cgame.packets.client.CSSetIsInvite;
import generated.cgame.packets.client.CSTeamInvite;
import generated.cgame.packets.client.CSTeamNotifyRet;
import generated.cgame.packets.objects.TeamPlayer;
import generated.cgame.packets.server.SCSearchPlayer;
import generated.cgame.packets.server.SCTeamLeaveRet;
import generated.cgame.packets.server.SCTeamNotify;
import generated.cgame.packets.server.SCTeamPlayer;
import generated.cgame.packets.server.SCTeamPlayerLeave;

@Controller
public class TeamMod extends GlobalBaseMod implements DBHandler {

	@Autowired
	PlayerManager PlayerManager;
	@Autowired
	IdCreator IdCreator;
	@Autowired
	PackMod PackMod;

	@Override
	protected void handle(CPacket packet) {
		Client client = (Client) packet.o;
		Player player = client.player;
		switch (packet.code) {
		case Code.CSSearchPlayer:
			searchPlayer(client.player, ((CSSearchPlayer) packet).nickname);
			break;
		case Code.CSTeamInvite:
			invite(player, ((CSTeamInvite) packet).playerid);
			break;
		case Code.CSTeamNotifyRet:
			CSTeamNotifyRet teamNotifyRet = (CSTeamNotifyRet) packet;
			notifyRet(player, teamNotifyRet.playerid, teamNotifyRet.retcode);
			break;
		case Code.CSTeamLeave:
			leave(player);
			break;
		case Code.CSSetIsInvite:
			player.isinvite = ((CSSetIsInvite) packet).isinvite;
			break;
		default:
			break;
		}
	}

	private void searchPlayer(Player player, String nickname) {
		SCSearchPlayer msg = new SCSearchPlayer();
		msg.nickname = nickname;

		Player friend = PlayerManager.getOnlinePlayer(nickname);
		if (friend != null) {
			msg.errorcode = ErrorCode.SUCCESS;
			msg.playerid = friend.getId();
			msg.status = friend.data.status;
			msg.isinvite = friend.isinvite;
		} else {// offline
			PlayerData frienddata = DBManager.getUniqu(PlayerData.class, "nickname", nickname, DBType.GameDB);
			if (frienddata != null) {
				msg.errorcode = ErrorCode.SUCCESS;
				msg.playerid = frienddata.id;
				msg.status = frienddata.status;
			} else {
				msg.errorcode = ErrorCode.NoFriend;
			}
		}

		player.Send(msg);
	}

	private void leave(Player player) {
		Set<Long> teamplayer = team.get(player.data.teamid);
		if (teamplayer == null || teamplayer.size() < 2) {
			sendLeaveMsg(player, ErrorCode.TeamLeaved);
			return;
		}

		teamplayer.remove(player.getId());
		playerLeaveTeam(player);// 通知队伍其他玩家，有成员离开
		if (teamplayer.size() == 1)// 衹剩一個人 解散隊伍
			dissolve(player, Long.parseLong(teamplayer.toArray()[0].toString()));

		sendLeaveMsg(player, ErrorCode.SUCCESS);

		player.data.teamid = 0;
		if (PlayerManager.allplays.containsValue(player)) {// online
			player.setStatus(Player.STATUS_ONLINE);
			player.sendBaseData();
		} else {
			DBManager.saveOrUpdate(player.data, DBType.GameDB);
		}
	}

	private void dissolve(Player player, long playerid) {
		Player onlyone = PlayerManager.GetPlayerById(playerid);
		if (onlyone != null) {
			onlyone.data.teamid = 0;
			onlyone.setStatus(Player.STATUS_ONLINE);
			onlyone.sendBaseData();
		} else {
			PlayerData onlyonedata = DBManager.getUniqu(PlayerData.class, "id", playerid, DBType.GameDB);
			onlyonedata.teamid = 0;
			DBManager.saveOrUpdate(onlyonedata, DBType.GameDB);
		}

		team.remove(player.data.teamid);
		TeamTable teamTable = DBManager.getUniqu(TeamTable.class, "id", player.data.teamid, DBType.GameDB);
		teamTable.status = 1;
		DBManager.saveOrUpdate(teamTable, DBType.GameDB);
	}

	private void sendLeaveMsg(Player player, short errorcode) {
		SCTeamLeaveRet msg = new SCTeamLeaveRet();
		msg.retcode = errorcode;
		player.Send(msg);
	}

	private void playerLeaveTeam(Player o) {
		Set<Long> teamplayer = team.get(o.data.teamid);
		if (teamplayer == null)
			return;

		SCTeamPlayerLeave msg = new SCTeamPlayerLeave();
		msg.playerid = o.getId();
		msg.nickname = o.getName();

		Iterator<Long> it = teamplayer.iterator();
		while (it.hasNext()) {
			Player player = PlayerManager.GetPlayerById(it.next());
			if (player != null)
				player.Send(msg);
		}
	}

	/**
	 * 邀请玩家
	 * 
	 * @param player
	 *            邀请玩家
	 * @param playerid
	 *            被邀请玩家id
	 */
	private void invite(Player player, long playerid) {
		SCTeamNotify msg = new SCTeamNotify();
		msg.retcode = ErrorCode.SUCCESS;
		msg.playerid = player.getId();
		msg.nickname = player.getName();
		Player friend = PlayerManager.GetPlayerById(playerid);
		if (friend != null)
			friend.Send(msg);
	}

	/**
	 * 被邀请玩家的响应
	 * 
	 * @param player
	 *            被邀请玩家
	 * @param playerid
	 *            邀请玩家id
	 * @param retcode
	 *            0:同意, 1:拒绝
	 */
	private void notifyRet(Player player, long playerid, short retcode) {
		if (retcode == 0) {// 同意邀请

			long teamid = 0;
			Player friend = PlayerManager.GetPlayerById(playerid);// 邀请玩家
			if (friend != null)
				teamid = friend.data.teamid;
			else
				teamid = DBManager.getUniqu(PlayerData.class, "id", playerid, DBType.GameDB).teamid;

			if (teamid == 0) {
				if (friend == null)// 邀请玩家不在线，不创建新房间
					return;

				teamid = IdCreator.GetId64();

				friend.data.teamid = teamid;
				friend.setStatus(Player.STATUS_INTEAM);
				friend.sendBaseData();

				Set<Long> teamplayer = new HashSet<>();
				teamplayer.add(playerid);
				teamplayer.add(player.getId());
				team.put(teamid, teamplayer);

				TeamTable teamTable = new TeamTable();
				teamTable.id = teamid;
				teamTable.status = 0;
				DBManager.saveOrUpdate(teamTable, DBType.GameDB);
			} else {
				Set<Long> teamplayer = team.get(teamid);
				if (teamplayer.size() >= TEAMMAX) {// 房間已滿
					SCTeamNotify msg = new SCTeamNotify();
					msg.retcode = ErrorCode.TeamMax;
					player.Send(msg);
					return;
				}
				teamplayer.add(player.getId());
			}

			player.data.teamid = teamid;
			player.setStatus(Player.STATUS_INTEAM);
			player.sendBaseData();

			sendTeamPlayerAll(player);
		}
	}

	/**
	 * 周知队员（此玩家的信息）
	 * 
	 * @param player
	 */
	private void sendTeamPlayer(Player player) {
		SCTeamPlayer msg = new SCTeamPlayer();
		msg.teamplayer = new TeamPlayer[1];
		TeamPlayer teamplayer = new TeamPlayer();
		teamplayer.status = player.data.status;
		teamplayer.matchstatus = player.matchstatus;
		teamplayer.playerid = player.getId();
		teamplayer.nickname = player.getName();
		teamplayer.curdecalid = PackMod.getUseItems(player);
		msg.teamplayer[0] = teamplayer;

		scTeamPlayer(player.data.teamid, msg);
	}

	private void scTeamPlayer(long teamid, SCTeamPlayer msg) {
		Set<Long> teamplayer = team.get(teamid);
		if (teamplayer == null)
			return;

		Iterator<Long> it = teamplayer.iterator();
		while (it.hasNext()) {
			Player player = PlayerManager.GetPlayerById(it.next());
			if (player != null)
				player.Send(msg);
		}
	}

	@Override
	public void onPlayerLogin(Player player) {
		if (player.data.teamid != 0) {
			Iterator<Player> it = offlinePlayer.iterator();
			while (it.hasNext()) {
				if (it.next().getId() == player.getId()) {
					it.remove();
					break;
				}
			}

			sendTeamPlayerAll(player);
		}
	}

	/**
	 * 周知队员（此队伍中所有玩家的信息）
	 * 
	 * @param player
	 */
	private void sendTeamPlayerAll(Player player) {
		Set<Long> set = team.get(player.data.teamid);
		if (set == null)
			return;

		SCTeamPlayer msg = new SCTeamPlayer();
		msg.teamplayer = new TeamPlayer[set.size()];

		Iterator<Long> it = set.iterator();
		int i = 0;
		while (it.hasNext()) {
			long playerid = it.next();
			TeamPlayer teamplayer = new TeamPlayer();
			msg.teamplayer[i++] = teamplayer;
			Player o = PlayerManager.GetPlayerById(playerid);
			if (o != null) {
				teamplayer.status = o.data.status;
				teamplayer.matchstatus = o.matchstatus;
				teamplayer.playerid = o.getId();
				teamplayer.nickname = o.getName();
				teamplayer.curdecalid = PackMod.getUseItems(o);
			} else {
				PlayerData d = DBManager.getUniqu(PlayerData.class, "id", playerid, DBType.GameDB);
				teamplayer.status = d.status;
				teamplayer.playerid = d.id;
				teamplayer.nickname = d.nickname;
				teamplayer.curdecalid = PackMod.getUseItems(d.id);
			}
		}

		scTeamPlayer(player.data.teamid, msg);
	}

	public void decal2team(Player player) {
		if (player.data.teamid != 0)
			sendTeamPlayer(player);
	}

	public void matchstatus2team(Player player) {
		if (player.data.teamid != 0)
			sendTeamPlayer(player);
	}

	@Override
	public void atfterInitDB() {
		// TODO Auto-generated method stub

	}

	@Override
	public void Update() {
		Iterator<Player> it = offlinePlayer.iterator();
		while (it.hasNext()) {
			Player player = it.next();
			if (TimeCreator.GetTimeStamp() - player.data.lastLogoutTime > GameServer.teamTimeMax) {
				leave(player);
				it.remove();
			}
		}
	}

	@Override
	public void onPlayerLogout(Player player) {
		if (player.data.teamid != 0) {
			player.setStatus(Player.STATUS_OFFLINE);
			offlinePlayer.add(player);
			sendTeamPlayer(player);
		}
	}

	@Override
	public void onDayChanged() {
		// TODO Auto-generated method stub

	}

	public boolean isAllMatching(long teamid, int matchingNum) {
		return teamid == 0 || team.get(teamid).size() == matchingNum;// 没有组队 或者 队伍中所有成员都点击了匹配
	}

	// <teamid, <playerid>>
	private Map<Long, Set<Long>> team = new HashMap<Long, Set<Long>>();
	private Set<Player> offlinePlayer = new HashSet<Player>();

	private int TEAMMAX = 5;// 隊伍最大人數

	@PostConstruct
	private void regist() {
		DBManager.regist(this);
	}

	@Override
	public void initDB() {
		init_team();
	}

	private void init_team() {
		String hql = "select b.teamid, b.id from TeamTable a, PlayerData b where a.id=b.teamid and a.status=0";
		List<Object> list = DBManager.getbyhql(Object.class, DBType.GameDB, hql);
		long teamid = 0;
		long playerid = 0;
		for (Object o : list) {
			Object[] d = (Object[]) o;
			for (int i = 0; i < d.length; i++) {
				switch (i) {
				case 0:
					teamid = (long) d[i];
					break;
				case 1:
					playerid = (long) d[i];
					break;
				default:
					break;
				}

				if (team.containsKey(teamid))
					team.get(teamid).add(playerid);
				else {
					Set<Long> set = new HashSet<Long>();
					set.add(playerid);
					team.put(teamid, set);
				}
			}
		}
	}
}
