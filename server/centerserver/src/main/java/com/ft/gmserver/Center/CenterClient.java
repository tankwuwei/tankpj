package com.ft.gmserver.Center;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import dbobjects.gmdb.AgentServiceInfo;
import dbobjects.gmdb.AgentSystemInfo;
import engine.db.DBManager;
import engine.db.client.DBType;
import engine.log.LogUtil;
import engine.net.BaseSession;
import engine.net.CPacket;
import generated.cgame.packets.Code;
import generated.cgame.packets.objects.PlayerItem;
import generated.cgame.packets.objects.ServiceInfo;
import generated.cgame.packets.objects.ShopItemList;
import generated.cgame.packets.objects.SystemInfo;
import generated.cgame.packets.server.CGAccountBlock;
import generated.cgame.packets.server.CGBroadcast;
import generated.cgame.packets.server.CGBroadcastCancel;
import generated.cgame.packets.server.CGGetIPBlacklist;
import generated.cgame.packets.server.CGGetOnlinePlayerItem;
import generated.cgame.packets.server.CGGetShopItem;
import generated.cgame.packets.server.CGMail;
import generated.cgame.packets.server.CGUpdIPBlacklist;
import generated.cgame.packets.server.CGUpdShopItem;
import generated.cgame.packets.server.GCIPBlacklist;
import generated.cgame.packets.server.GCOnlinePlayerItem;
import generated.cgame.packets.server.GCShopItemList;
import generated.cgame.packets.server.ReportServiceInfo;
import generated.cgame.packets.server.ReportSystemInfo;
import generated.cgame.packets.server.ServerType;
import generated.cgame.packets.server.Shutdown;

@Controller
@Scope("prototype")
public class CenterClient extends BaseSession {

	@Autowired
	CenterManager CenterManager;

	private static final int recordSize = 100;
	List<SystemInfo> infos = new ArrayList<>();
	List<ServiceInfo> serviceInfos = new ArrayList<>();

	@Override
	public void update() {
	}

	@Override
	public boolean processMsg(CPacket packet) {
		switch (packet.code) {
		case Code.VMsgConnect:
			onConnect();
			break;
		case Code.VMsgDisconnect:
			onDisconnect();
			CenterManager.clients.values().remove(this);
			break;
		case Code.ReportServiceInfo:
			onReportServiceInfo((ReportServiceInfo) packet);
			break;
		case Code.ReportSystemInfo:
			onReportSystemInfo((ReportSystemInfo) packet);
			break;
		case Code.ServerType:
			CenterManager.clients.put(((ServerType) packet).type, this);
			break;
		case Code.GCShopItemList:
			setShopitemList(((GCShopItemList) packet).shopitem);
			break;
		case Code.GCOnlinePlayerItem:
			setOnlinePlayerItem(((GCOnlinePlayerItem) packet).items);
			break;
		case Code.GCIPBlacklist:
			ipblacklist = ((GCIPBlacklist) packet).ips;
			break;
		default:
			break;
		}
		return true;
	}

	public void getShopItem() {
		CGGetShopItem msg = new CGGetShopItem();
		Send(msg);
	}

	public void updShopItem() {
		CGUpdShopItem msg = new CGUpdShopItem();
		msg.shopitem = new ShopItemList[shopitemList.size()];
		shopitemList.toArray(msg.shopitem);
		Send(msg);
	}

	private void setShopitemList(ShopItemList[] arr) {
		shopitemList = new ArrayList<ShopItemList>(arr.length);
		// shopitemList = Arrays.asList(arr);
		shopitemList.addAll(Arrays.asList(arr));
	}

	public void accountblock(String ids, int blocktime) {
		CGAccountBlock msg = new CGAccountBlock();
		msg.ids = ids;
		msg.blocktime = blocktime;
		Send(msg);
	}

	public void getOnlinePlayerItem(long playerid) {
		CGGetOnlinePlayerItem msg = new CGGetOnlinePlayerItem();
		msg.playerid = playerid;
		Send(msg);
	}

	private void setOnlinePlayerItem(PlayerItem[] arr) {
		onlinePlayerItem = new ArrayList<PlayerItem>(arr.length);
		onlinePlayerItem.addAll(Arrays.asList(arr));
	}

	public void sendShutdown(int time) {
		Shutdown msg = new Shutdown();
		msg.time = time;
		Send(msg);

		countdown(time);
	}

	private void countdown(int time) {
		countdown = 60 * time;
		while (countdown > 0) {
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			countdown--;
		}
	}

	public void getIPBlacklist() {
		CGGetIPBlacklist msg = new CGGetIPBlacklist();
		Send(msg);
	}

	public void updIPBlacklist(short oper, int[] ips) {
		CGUpdIPBlacklist msg = new CGUpdIPBlacklist();
		msg.oper = oper;
		msg.ips = ips;
		Send(msg);
	}

	private void onReportSystemInfo(ReportSystemInfo msg) {
		AgentSystemInfo info = new AgentSystemInfo();
		info.network = msg.info.network;
		info.os = msg.info.os;
		info.cpucount = msg.info.cpucount;
		info.syscpuidle = msg.info.syscpuidle;
		info.sysmem = msg.info.sysmem;
		info.sysmemfree = msg.info.sysmemfree;
		info.ip = msg.info.ip;
		info.sampleTime = msg.info.sampleTime;
		DBManager.saveOrUpdate(info, DBType.GMDB);
	}

	private void onReportServiceInfo(ReportServiceInfo msg) {
		AgentServiceInfo info = new AgentServiceInfo();
		info.processname = msg.info.processname;
		info.serverid = msg.info.serverid;
		info.zoneid = msg.info.zoneid;
		info.version = msg.info.version;
		info.processid = msg.info.processid;
		info.starttime = msg.info.starttime;
		info.mem = msg.info.mem;
		info.cpu = msg.info.cpu;
		info.clientcount = msg.info.clientcount;
		info.servercount = msg.info.servercount;
		info.ip = msg.info.ip;
		info.sampleTime = msg.info.sampleTime;
		DBManager.saveOrUpdate(info, DBType.GMDB);
	}

	public void broadcastdeploy(String content) {
		CGBroadcast msg = new CGBroadcast();
		msg.content = content;
		Send(msg);
	}

	public void broadcastcancel() {
		CGBroadcastCancel msg = new CGBroadcastCancel();
		Send(msg);
	}

	public void mail(String[] nickname, String mailtitle, String mailcontent, int[] propid, int[] num) {
		CGMail msg = new CGMail();
		msg.nickname = nickname;
		msg.mailtitle = mailtitle;
		msg.mailcontent = mailcontent;
		msg.propid = propid;
		msg.num = num;
		Send(msg);
	}

	@Override
	protected void onConnect() {
		LogUtil.debug("Server connect from " + getIp());
	}

	@Override
	protected void onDisconnect() {
		LogUtil.debug("Server [" + getIp() + "] disconnected");
	}

	public List<ShopItemList> shopitemList = new ArrayList<ShopItemList>();
	public List<PlayerItem> onlinePlayerItem = new ArrayList<PlayerItem>();
	public int countdown = 0;

	public int[] ipblacklist;// ip黑名單
}
