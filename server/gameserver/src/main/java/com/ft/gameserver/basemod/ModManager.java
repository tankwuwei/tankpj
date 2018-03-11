package com.ft.gameserver.basemod;

import java.util.ArrayList;
/**
 * 模块管理器，管理所有的个人模块和全局模块
 */
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;

import com.ft.gameserver.player.Player;

@Controller
public class ModManager {

	private static Map<ModType, BaseMod> mods = new HashMap<>();
	private static List<GlobalBaseMod> gmods = new ArrayList<>();

	public static void Regist(GlobalBaseMod mod) {
		gmods.add(mod);
	}

	public static void Regist(BaseMod mod) {
		mods.put(mod.GetType(), mod);
	}

	public void OnPlayerLogin(Player player) {
		for (GlobalBaseMod mod : gmods) {
			mod.onPlayerLogin(player);
		}
		for (BaseMod mod : mods.values()) {
			mod.OnPlayerLogin(player);
		}
	}

	public void OnPlayerLogout(Player player) {
		for (GlobalBaseMod mod : gmods) {
			mod.onPlayerLogout(player);
		}
		for (BaseMod mod : mods.values()) {
			mod.OnPlayerLogout(player);
		}
	}

	/*
	 * 由playermanager驅動
	 */
	public void Update(Player player) {
		for (BaseMod mod : mods.values()) {
			mod.Update(player);
		}
	}

	/*
	 * 由playermanager驅動
	 */
	public void onDayChanged(Player player){
		for (BaseMod mod : mods.values()) {
			mod.onDayChanged(player);
		}
	}
	
	
	public void onCreatePlayer(Player player) {
		for (BaseMod mod : mods.values()) {
			mod.onCreatePlayer(player);
		}
	}

	public void update() {
		for (GlobalBaseMod mod : gmods) {
			mod.Update();
		}
		// basemod 的update由playermanger驱动
	}

	public void UpdateBaseMods(Player player) {
		for (BaseMod mod : mods.values()) {
			mod.Update(player);
		}
	}

	public void onDayChanged() {
		for (GlobalBaseMod mod : gmods) {
			mod.onDayChanged();
		}
		// basemod 的onDayChanged由playermanger驱动
	}

	public void onDayChangedBaseMods(Player player) {
		for (BaseMod mod : mods.values()) {
			mod.onDayChanged(player);
		}
	}
}
