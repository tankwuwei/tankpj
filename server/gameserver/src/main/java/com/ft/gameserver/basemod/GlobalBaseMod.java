package com.ft.gameserver.basemod;

import javax.annotation.PostConstruct;

import com.ft.gameserver.player.Player;

import engine.server.Handler;

public abstract class GlobalBaseMod extends Handler{

	@PostConstruct
	public void Regist() {
		ModManager.Regist(this);
	}

	public ModType GetType() {
		return ModType;
	}

	protected ModType ModType;

	
	
	public abstract void Update();

	public abstract void onPlayerLogin(Player player);
	
	public abstract void onPlayerLogout(Player player);
	
	public abstract void onDayChanged();
}
