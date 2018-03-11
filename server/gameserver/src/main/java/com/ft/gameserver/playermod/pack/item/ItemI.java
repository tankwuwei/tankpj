package com.ft.gameserver.playermod.pack.item;

import com.ft.gameserver.player.Player;

public interface ItemI {

	void use(Player player, short pos);

	void unuse(Player player);

	short ColorPos = 1;
	short TexturePos = 2;
	short ParachutePos = 8;
	short KnapsackPos = 9;
	
	String EDeArchiveType_None = "EDeArchiveType_None";// 不走倒计时
	String EDeArchiveType_Get = "EDeArchiveType_Get";// 捡起开始走倒计时
	String EDeArchiveType_Use = "EDeArchiveType_Use";// 使用开始走倒计时
}
