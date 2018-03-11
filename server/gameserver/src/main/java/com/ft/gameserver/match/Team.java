package com.ft.gameserver.match;

import java.util.HashSet;
import java.util.Set;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.ft.gameserver.player.Player;

@Component
@Scope("prototype")
public class Team {

	public long teamid = 0L;
	public Set<Player> players = new HashSet<>();

	public Set<Team> oldteams = new HashSet<>();
	public int teamid32 = 0;// 用于进入战斗

}
