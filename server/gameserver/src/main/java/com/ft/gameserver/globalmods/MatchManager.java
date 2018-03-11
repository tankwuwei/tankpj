package com.ft.gameserver.globalmods;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.ft.csv.resources.ResourceManager;
import com.ft.gameserver.GameServer;
import com.ft.gameserver.agents.agentManager;
import com.ft.gameserver.basemod.GlobalBaseMod;
import com.ft.gameserver.clients.Client;
import com.ft.gameserver.match.Team;
import com.ft.gameserver.match.TeamMatchRule;
import com.ft.gameserver.player.Player;
import com.ft.gameserver.servers.Server;
import com.ft.gameserver.servers.ServerManager;
import com.ft.gameserver.utility.IdCreator;

import common.ErrorCode;
import engine.bean.BeanFactory;
import engine.net.CPacket;
import generated.cgame.packets.Code;
import generated.cgame.packets.client.CSRequestStartMatch;
import generated.cgame.packets.objects.TankFightInfo;
import generated.cgame.packets.server.SCRequestCancelMatchRet;
import generated.cgame.packets.server.SCRequestStartMatchRet;
import generated.cgame.packets.server.SCZoneInfo;
import generated.cgame.packets.server.SUStartFight;

/**
 * 等待进入战斗的匹配管理器
 * 
 * @author cxz
 *
 */
@Controller
public class MatchManager extends GlobalBaseMod {
	@Autowired
	ResourceManager configs;
	@Autowired
	agentManager agents;
	@Autowired
	ServerManager servers;
	@Autowired
	IdCreator ids;
	@Autowired
	TeamMod TeamMod;
	@Autowired
	TeamMatchRule TeamMatchRule;

	@Override
	public void Update() {
		matching();
		matchingSquad();
	}

	@Override
	protected void handle(CPacket packet) {
		if (packet.o.getClass() != Client.class) {
			return;
		}
		Client client = (Client) packet.o;
		switch (packet.code) {
		case Code.CSRequestStartMatch:
			CSRequestStartMatch msg = (CSRequestStartMatch) packet;
			onPlayerStartMatch(client.player, msg.matchtype, msg.zoneid);
			break;
		case Code.CSRequestCancelMatch:
			onPlayerCancelMatch(client.player);
			break;
		case Code.CSGetFighterList:
			SendFightServerList(client.player);
			break;
		default:
			break;
		}
	}

	private void onPlayerStartMatch(Player player, short matchtype, int zoneid) {
		if (!GameServer.isok) {
			sendMatchRet(player, ErrorCode.NotMatch);
			return;
		}

		if (matchtype == MATCH_SOLO)
			startMatchSolo(player, zoneid);
		else if (matchtype == MATCH_SQUAD)
			startMatchSquad(player, zoneid);
		else
			sendMatchRet(player, ErrorCode.WrongMatchType);

		player.matchtype = matchtype;// 临时记录玩家的匹配类型
	}

	private void sendMatchRet(Player player, short retcode) {
		SCRequestStartMatchRet ret = new SCRequestStartMatchRet();
		ret.ret = retcode;
		player.Send(ret);
	}

	private void startMatchSolo(Player player, int zoneid) {
		if (players.containsKey(player.getId())) {
			sendMatchRet(player, ErrorCode.InMatch);
			return;
		}

		Set<Player> zone = zonePlayers.get(zoneid);
		if (zone == null) {
			zone = new HashSet<>();
			zonePlayers.put(zoneid, zone);
		}
		zone.add(player);
		players.put(player.getId(), zoneid);

		sendMatchRet(player, ErrorCode.SUCCESS);
	}

	// 小队匹配
	private void startMatchSquad(Player player, int zoneid) {
		if (playersSquad.containsKey(player.getId())) {
			sendMatchRet(player, ErrorCode.InMatch);
			return;
		}

		Set<Team> zoneteam = zonePlayersSquad.get(zoneid);
		if (zoneteam == null) {
			zoneteam = new HashSet<>();
			zonePlayersSquad.put(zoneid, zoneteam);
		}

		Team team = TeamMatchRule.getTeam(player, zoneteam);
		if (player.getTeamId() == 0 || team == null) {
			team = BeanFactory.getBean(Team.class);
			team.teamid = player.getTeamId();
		}
		team.players.add(player);

		zoneteam.add(team);
		playersSquad.put(player.getId(), zoneid);

		sendTeamMatchRet(team);

		player.matchstatus = Player.MATCHSTATUS_ON;
		TeamMod.matchstatus2team(player);
	}

	private void sendTeamMatchRet(Team team) {
		short retcode = TeamMod.isAllMatching(team.teamid, team.players.size()) ? ErrorCode.SUCCESS : ErrorCode.WaitMatch;

		for (Player player : team.players)
			sendMatchRet(player, retcode);
	}

	private void onPlayerCancelMatch(Player player) {
		if (player.matchtype == MATCH_SOLO)
			cancelSolo(player);
		else if (player.matchtype == MATCH_SQUAD)
			cancelSquad(player);
	}

	private void cancelSolo(Player player) {
		if (!players.containsKey(player.getId())) {
			sendCancelMatchRet(player, ErrorCode.NotInMatch);
			return;
		}

		int zoneid = players.remove(player.getId());
		zonePlayers.get(zoneid).remove(player);

		sendCancelMatchRet(player, ErrorCode.SUCCESS);
	}

	// 取消小队匹配
	private void cancelSquad(Player player) {
		if (!playersSquad.containsKey(player.getId())) {
			sendCancelMatchRet(player, ErrorCode.NotInMatch);
			return;
		}

		int zoneid = playersSquad.remove(player.getId());
		Set<Team> zoneteam = zonePlayersSquad.get(zoneid);
		Team team = player.getTeamId() == 0 ? TeamMatchRule.getTeam(player.getId(), zoneteam) : TeamMatchRule.getTeam(player, zoneteam);
		team.players.remove(player);

		if (team.players.size() == 0)
			zoneteam.remove(team);
		else
			sendTeamMatchRet(team);

		sendCancelMatchRet(player, ErrorCode.SUCCESS);

		player.matchstatus = Player.MATCHSTATUS_OFF;
		TeamMod.matchstatus2team(player);
	}

	private void sendCancelMatchRet(Player player, short retcode) {
		SCRequestCancelMatchRet ret = new SCRequestCancelMatchRet();
		ret.ret = retcode;
		player.Send(ret);
	}

	/*
	 * 发送战区列表
	 */
	private void SendFightServerList(Player player) {
		SCZoneInfo msg = new SCZoneInfo();
		agents.packZoneList(msg);
		player.Send(msg);
	}

	@Override
	public void onPlayerLogin(Player player) {
		SendFightServerList(player);
	}

	/**
	 * check list on logout
	 */
	@Override
	public void onPlayerLogout(Player player) {
		if (player.matchtype == MATCH_SOLO)
			soloLogout(player);
		else if (player.matchtype == MATCH_SQUAD)
			squadLogout(player);
	}

	private void soloLogout(Player player) {
		if (players.containsKey(player.getId())) {
			int zoneid = players.remove(player.getId());
			zonePlayers.get(zoneid).remove(player);
		}
	}

	private void squadLogout(Player player) {
		if (playersSquad.containsKey(player.getId())) {
			int zoneid = playersSquad.remove(player.getId());
			Set<Team> zoneteam = zonePlayersSquad.get(zoneid);
			Team team = player.getTeamId() == 0 ? TeamMatchRule.getTeam(player.getId(), zoneteam) : TeamMatchRule.getTeam(player, zoneteam);
			team.players.remove(player);

			if (team.players.size() == 0)
				zoneteam.remove(team);
			else
				sendTeamMatchRet(team);
		}
	}

	private void matching() {
		for (Integer zoneid : zonePlayers.keySet()) {
			// 一個區排隊的人
			Set<Player> zone = zonePlayers.get(zoneid);
			while (zone.size() >= GameServer.startFightPlayer) {
				Server server = servers.getFreeSvr(zoneid);
				if (server == null) { // 没有空闲server
					break;
				}
				// 放置将要进入战斗服务器的玩家
				Set<Player> room = new HashSet<>();
				for (Player player : zone) {
					room.add(player);
					players.remove(player.getId());
					if (room.size() >= GameServer.startFightPlayer) {
						break;
					}
				}
				SUStartFight msg = new SUStartFight();
				msg.tankinfos = new TankFightInfo[room.size()];
				int i = 0;
				for (Player player : room) {
					TankFightInfo info = new TankFightInfo();
					player.packFighterInfo(info);
					msg.tankinfos[i++] = info;
				}
				server.Send(msg);
				zone.removeAll(room);
				server.addPreFightPlayers(room);
			}
		}
	}

	/**
	 * Squad
	 */
	private void matchingSquad() {
		for (Integer zoneid : zonePlayersSquad.keySet()) {
			Set<Team> zoneteam = zonePlayersSquad.get(zoneid);// 所有正在排队的小队

			Set<Team> roomteam = new HashSet<>();// 放置将要进入战斗服务器的小队
			TeamMatchRule.match(zoneteam, roomteam);

			while (roomteam.size() >= GameServer.startFightTeam) {
				Server server = servers.getFreeSvr(zoneid);
				if (server == null)// 没有空闲server
					break;

				Set<Player> roomplayer = new HashSet<>();// 放置将要进入战斗服务器的玩家
				for (int i = 1; i <= GameServer.startFightTeam; i++) {
					Team team = TeamMatchRule.popTeam(roomteam);
					roomplayer.addAll(team.players);

					zoneteam.removeAll(team.oldteams);
					for (Player player : team.players) {
						player.teamid32 = team.teamid32;// 用于进入战斗
						playersSquad.remove(player.getId());
					}
				}

				sendStartFight(server, roomplayer);
			}
		}
	}

	private void sendStartFight(Server server, Set<Player> roomplayer) {
		SUStartFight msg = new SUStartFight();
		msg.tankinfos = new TankFightInfo[roomplayer.size()];
		int i = 0;
		for (Player player : roomplayer) {
			TankFightInfo info = new TankFightInfo();
			player.packFighterInfo(info);
			msg.tankinfos[i++] = info;
		}
		server.Send(msg);
		server.addPreFightPlayers(roomplayer);
	}

	// 所有排队的人
	Map<Long, Integer> players = new HashMap<>();
	Map<Integer, Set<Player>> zonePlayers = new HashMap<>();

	@Override
	public void onDayChanged() {
		// TODO Auto-generated method stub

	}

	// 小队
	Map<Long, Integer> playersSquad = new HashMap<>();// Map<playerid, zoneid>
	Map<Integer, Set<Team>> zonePlayersSquad = new HashMap<>();// Map<zoneid, Set<Team>>

	private short MATCH_SOLO = 0;
	private short MATCH_SQUAD = 1;

}
