package com.ft.gameserver.globalmods;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Controller;

import com.ft.gameserver.basemod.GlobalBaseMod;
import com.ft.gameserver.clients.ClientManager;
import com.ft.gameserver.player.Player;

import dbobjects.gamedb.BlacklistTable;
import engine.bean.BeanFactory;
import engine.db.DBHandler;
import engine.db.DBManager;
import engine.db.client.DBType;
import engine.log.LogUtil;
import engine.net.CPacket;
import engine.util.IPv4Util;

@Controller
public class BlacklistMod extends GlobalBaseMod implements DBHandler {

	@Override
	protected void handle(CPacket packet) {
	}

	@PostConstruct
	private void regist() {
		DBManager.regist(this);
	}

	@Override
	public void initDB() {
		List<BlacklistTable> list = DBManager.get(BlacklistTable.class, DBType.GameDB);
		for (BlacklistTable o : list)
			ipblacklist.add(o.ip);
	}

	@Override
	public void onPlayerLogin(Player player) {

	}

	@Override
	public void atfterInitDB() {
		// TODO Auto-generated method stub

	}

	@Override
	public void Update() {
		// TODO Auto-generated method stub

	}

	@Override
	public void onPlayerLogout(Player player) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onDayChanged() {
		// TODO Auto-generated method stub

	}

	public int[] getIPBlacklist() {
		Integer[] a = (Integer[]) ipblacklist.toArray(new Integer[ipblacklist.size()]);
		int[] b = new int[a.length];
		b = Arrays.stream(a).mapToInt(Integer::valueOf).toArray();
		return b;
	}

	public void updIPBlacklist(short oper, int[] ips) {
		if (oper == OPER_ADD) {
			ipblackadd(ips[0]);
			BeanFactory.getBean(ClientManager.class).kickout(ips[0]);
		} else if (oper == OPER_DEL)
			ipblackdel(ips);
	}

	private void ipblackadd(int ip) {
		LogUtil.warn("加入黑名单！IP=" + IPv4Util.ipInt2Str(ip));
		ipblacklist.add(ip);
		BlacklistTable o = new BlacklistTable();
		o.ip = ip;
		DBManager.saveOrUpdate(o, DBType.GameDB);
	}

	private void ipblackdel(int[] ips) {
		for (int ip : ips) {
			ipblacklist.remove((Integer) ip);
			BlacklistTable o = DBManager.getUniqu(BlacklistTable.class, "ip", ip, DBType.GameDB);
			DBManager.delete(BlacklistTable.class, o.id, DBType.GameDB);
		}
	}

	public boolean isblackip(int ip) {
		return ipblacklist.contains(ip);
	}

	public boolean isblackip(String ip) {
		return ipblacklist.contains(IPv4Util.ipStr2Int(ip));
	}

	public List<Integer> ipblacklist = new ArrayList<Integer>();// ip黑名單
	private short OPER_ADD = 0;
	private short OPER_DEL = 1;

}
