package com.ft.gameserver.basemod;

import javax.annotation.PostConstruct;

import com.ft.gameserver.player.Player;

import engine.server.Handler;

public abstract class BaseMod extends Handler {

	@PostConstruct
	private void Regist() {
		ModManager.Regist(this);
	}

	public ModType GetType() {
		return ModType;
	}

	protected SourceBaseMod GetSource(Player player) {
		return player.source.GetSource(GetType());
	}

	protected ModType ModType;

	abstract protected void onCreatePlayer(Player player);

	abstract protected void OnPlayerLogin(Player player);

	abstract protected void OnPlayerLogout(Player player);

	abstract protected void Update(Player player);

	abstract protected void onDayChanged(Player player);

}
