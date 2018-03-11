package com.ft.gameserver;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.ft.gameserver.globalmods.BlacklistMod;
import com.ft.gameserver.globalmods.BroadcastMod;
import com.ft.gameserver.globalmods.MailMod;
import com.ft.gameserver.globalmods.PlayerManager;
import com.ft.gameserver.globalmods.ShopItemMod;
import com.ft.gameserver.player.Player;
import com.ft.gameserver.playermod.pack.PackMod;

import common.Constant;
import common.MonitorConntor;
import engine.common.TimeCreator;
import engine.log.LogUtil;
import engine.net.CPacket;
import engine.util.CMDUtil;
import generated.cgame.packets.Code;
import generated.cgame.packets.server.CGAccountBlock;
import generated.cgame.packets.server.CGBroadcast;
import generated.cgame.packets.server.CGGetOnlinePlayerItem;
import generated.cgame.packets.server.CGMail;
import generated.cgame.packets.server.CGUpdIPBlacklist;
import generated.cgame.packets.server.CGUpdShopItem;
import generated.cgame.packets.server.GCIPBlacklist;
import generated.cgame.packets.server.GCOnlinePlayerItem;
import generated.cgame.packets.server.GCShopItemList;
import generated.cgame.packets.server.Shutdown;

@Controller
public class GameServerMonitorConntor extends MonitorConntor {

	@Autowired
	ShopItemMod ShopItemMod;
	@Autowired
	PlayerManager PlayerManager;
	@Autowired
	PackMod PackMod;
	@Autowired
	BlacklistMod BlacklistMod;
	@Autowired
	BroadcastMod BroadcastMod;
	@Autowired
	MailMod MailMod;

	@Override
	protected void processMsg(CPacket packet) {
		switch (packet.code) {
		case Code.VMsgConnect:
			onConnect();
			sendServerType(Constant.SERVERTYPE_GAMESERVER);
			break;
		case Code.VMsgDisconnect:
			onDisconnect();
			break;
		case Code.Shutdown:
			onShutdown(((Shutdown) packet).time);
			break;
		case Code.CGGetShopItem:
			getShopItem();
			break;
		case Code.CGUpdShopItem:
			ShopItemMod.updShopItem(((CGUpdShopItem) packet).shopitem);
			break;
		case Code.CGAccountBlock:
			accountblock((CGAccountBlock) packet);
			break;
		case Code.CGGetOnlinePlayerItem:
			getOnlinePlayerItem(((CGGetOnlinePlayerItem) packet).playerid);
			break;
		case Code.CGGetIPBlacklist:
			getIPBlacklist();
			break;
		case Code.CGUpdIPBlacklist:
			BlacklistMod.updIPBlacklist(((CGUpdIPBlacklist) packet).oper, ((CGUpdIPBlacklist) packet).ips);
			break;
		case Code.CGBroadcast:// 广播
			BroadcastMod.broadcast(((CGBroadcast) packet).content);
			break;
		case Code.CGBroadcastCancel:// 取消广播
			BroadcastMod.broadcastcancel();
			break;
		case Code.CGMail:// Mail
			MailMod.mail((CGMail) packet);
			break;
		default:
			break;
		}
	}

	private void getShopItem() {
		GCShopItemList msg = new GCShopItemList();
		msg.shopitem = ShopItemMod.getShopItemList();
		Send(msg);
	}

	/**
	 * 账号封停、解封
	 */
	private void accountblock(CGAccountBlock msg) {
		PlayerManager.accountblock(msg.ids, msg.blocktime);
	}

	/**
	 * 请求在线玩家道具
	 */
	private void getOnlinePlayerItem(long playerid) {
		GCOnlinePlayerItem msg = new GCOnlinePlayerItem();
		Player player = PlayerManager.GetPlayerById(playerid);
		if (player != null)
			msg.items = PackMod.getOnlinePlayerItem(player);
		Send(msg);
	}

	protected void onShutdown(int time) {
		// 通知在线玩家GameServer几分钟后重启
		PlayerManager.noticeClientServerShutdown(time);
		// game server即将重启 不允许再匹配
		GameServer.isok = false;

		closetime = TimeCreator.GetTimeStamp() + 60 * time;
	}

	public void Shutdown() {
		if (!GameServer.isok && TimeCreator.GetTimeStamp() > closetime) {
			PlayerManager.kickout();

			int pid = CMDUtil.getPIDbywindowtitle(GameServer.gameserver_windowtitle);
			LogUtil.system("close gameserver pid=" + pid);
			if (pid != -1)
				CMDUtil.killPID(pid);
		}
	}

	private void getIPBlacklist() {
		GCIPBlacklist msg = new GCIPBlacklist();
		msg.ips = BlacklistMod.getIPBlacklist();
		Send(msg);
	}

	int closetime;
}
