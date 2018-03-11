package com.ft.gameserver.match;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.ft.gameserver.globalmods.TeamMod;
import com.ft.gameserver.player.Player;
import com.ft.gameserver.utility.IdCreator;

import engine.bean.BeanFactory;

@Controller
public class TeamMatchRule {
	@Autowired
	IdCreator IdCreator;
	@Autowired
	TeamMod TeamMod;

	public Team getTeam(Player player, Set<Team> zoneteam) {
		for (Team team : zoneteam)
			if (team.teamid == player.getTeamId())
				return team;

		return null;
	}

	public Team getTeam(long playerid, Set<Team> zoneteam) {
		for (Team team : zoneteam)
			if (team.teamid == 0)
				for (Player player : team.players)
					if (player.getId() == playerid)
						return team;

		return null;
	}

	public void match(Set<Team> zoneteam, Set<Team> roomteam) {
		Set<Team> team1 = new HashSet<>();
		Set<Team> team2 = new HashSet<>();
		Set<Team> team3 = new HashSet<>();
		Set<Team> team4 = new HashSet<>();
		Set<Team> team5 = new HashSet<>();
		sortTeam(zoneteam, team1, team2, team3, team4, team5);// 按人数分组

		matchTeam(roomteam, team1, team2, team3, team4, team5);// 自动匹配
	}

	private void sortTeam(Set<Team> zoneteam, Set<Team> team1, Set<Team> team2, Set<Team> team3, Set<Team> team4, Set<Team> team5) {
		for (Team team : zoneteam) {
			if (!TeamMod.isAllMatching(team.teamid, team.players.size()))// 队员没有全部匹配
				continue;

			if (team.players.size() == 1)
				team1.add(team);
			else if (team.players.size() == 2)
				team2.add(team);
			else if (team.players.size() == 3)
				team3.add(team);
			else if (team.players.size() == 4)
				team4.add(team);
			else if (team.players.size() == 5)
				team5.add(team);
		}
	}

	private void matchTeam(Set<Team> roomteam, Set<Team> team1, Set<Team> team2, Set<Team> team3, Set<Team> team4, Set<Team> team5) {
		{// 5
			while (true) {
				if (team5.isEmpty())
					break;

				Team o5 = popTeam(team5);

				Team team = BeanFactory.getBean(Team.class);
				team.teamid32 = IdCreator.GetTmpId32();
				team.players.addAll(o5.players);
				team.oldteams.add(o5);
				roomteam.add(team);
			}
		}

		{// 4 + 1
			while (true) {
				if (team4.isEmpty() || team1.isEmpty())
					break;

				Team o4 = popTeam(team4);
				Team o1 = popTeam(team1);

				Team team = BeanFactory.getBean(Team.class);
				team.teamid32 = IdCreator.GetTmpId32();
				team.players.addAll(o4.players);
				team.players.addAll(o1.players);
				team.oldteams.add(o4);
				team.oldteams.add(o1);
				roomteam.add(team);
			}
		}

		{// 3 + 2
			while (true) {
				if (team3.isEmpty() || team2.isEmpty())
					break;

				Team o3 = popTeam(team3);
				Team o2 = popTeam(team2);

				Team team = BeanFactory.getBean(Team.class);
				team.teamid32 = IdCreator.GetTmpId32();
				team.players.addAll(o3.players);
				team.players.addAll(o2.players);
				team.oldteams.add(o3);
				team.oldteams.add(o2);
				roomteam.add(team);
			}
		}

		{// 3 + 1 + 1
			while (true) {
				if (team3.isEmpty() || team1.size() < 2)
					break;

				Team o3 = popTeam(team3);
				Team o11 = popTeam(team1);
				Team o12 = popTeam(team1);

				Team team = BeanFactory.getBean(Team.class);
				team.teamid32 = IdCreator.GetTmpId32();
				team.players.addAll(o3.players);
				team.players.addAll(o11.players);
				team.players.addAll(o12.players);
				team.oldteams.add(o3);
				team.oldteams.add(o11);
				team.oldteams.add(o12);
				roomteam.add(team);
			}
		}

		{// 2 + 2 + 1
			while (true) {
				if (team2.size() < 2 || team1.isEmpty())
					break;

				Team o21 = popTeam(team2);
				Team o22 = popTeam(team2);
				Team o1 = popTeam(team1);

				Team team = BeanFactory.getBean(Team.class);
				team.teamid32 = IdCreator.GetTmpId32();
				team.players.addAll(o21.players);
				team.players.addAll(o22.players);
				team.players.addAll(o1.players);
				team.oldteams.add(o21);
				team.oldteams.add(o22);
				team.oldteams.add(o1);
				roomteam.add(team);
			}
		}

		{// 2 + 1 + 1 + 1
			while (true) {
				if (team2.isEmpty() || team1.size() < 3)
					break;

				Team o2 = popTeam(team2);
				Team o11 = popTeam(team1);
				Team o12 = popTeam(team1);
				Team o13 = popTeam(team1);

				Team team = BeanFactory.getBean(Team.class);
				team.teamid32 = IdCreator.GetTmpId32();
				team.players.addAll(o2.players);
				team.players.addAll(o11.players);
				team.players.addAll(o12.players);
				team.players.addAll(o13.players);
				team.oldteams.add(o2);
				team.oldteams.add(o11);
				team.oldteams.add(o12);
				team.oldteams.add(o13);
				roomteam.add(team);
			}
		}

		{// 1 + 1 + 1 + 1 + 1
			while (true) {
				if (team1.size() < 5)
					break;

				Team o11 = popTeam(team1);
				Team o12 = popTeam(team1);
				Team o13 = popTeam(team1);
				Team o14 = popTeam(team1);
				Team o15 = popTeam(team1);

				Team team = BeanFactory.getBean(Team.class);
				team.teamid32 = IdCreator.GetTmpId32();
				team.players.addAll(o11.players);
				team.players.addAll(o12.players);
				team.players.addAll(o13.players);
				team.players.addAll(o14.players);
				team.players.addAll(o15.players);
				team.oldteams.add(o11);
				team.oldteams.add(o12);
				team.oldteams.add(o13);
				team.oldteams.add(o14);
				team.oldteams.add(o15);
				roomteam.add(team);
			}
		}
	}

	public Team popTeam(Set<Team> team) {
		Iterator<Team> it = team.iterator();
		while (it.hasNext()) {
			Team o = it.next();
			it.remove();
			return o;
		}
		return null;
	}
}
