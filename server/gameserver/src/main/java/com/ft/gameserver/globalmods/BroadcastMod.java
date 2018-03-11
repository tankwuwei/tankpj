package com.ft.gameserver.globalmods;

import java.util.List;

import javax.annotation.PostConstruct;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.ft.gameserver.basemod.GlobalBaseMod;
import com.ft.gameserver.player.Player;

import dbobjects.gamedb.BroadcastTable;
import engine.common.TimeCreator;
import engine.db.DBHandler;
import engine.db.DBManager;
import engine.db.client.Condition;
import engine.db.client.DBType;
import engine.net.CPacket;
import generated.cgame.packets.server.SCBroadcast;
import generated.cgame.packets.server.SCBroadcastCancel;

@Controller
public class BroadcastMod extends GlobalBaseMod implements DBHandler {

	@Autowired
	PlayerManager PlayerManager;

	@Override
	protected void handle(CPacket packet) {
	}

	@PostConstruct
	private void regist() {
		DBManager.regist(this);
	}

	@Override
	public void initDB() {
		List<BroadcastTable> list = DBManager.get(BroadcastTable.class, DBType.GameDB, Condition.desc("createtime"));
		if (list.size() > 0 && list.get(0).status == 0)
			this.content = list.get(0).content;
	}

	@Override
	public void onPlayerLogin(Player player) {
		if (StringUtils.isNotEmpty(this.content))
			sendBroadcast(player);
	}

	// 广播
	private void sendBroadcast(Player player) {
		SCBroadcast msg = new SCBroadcast();
		msg.content = this.content;
		player.Send(msg);
	}

	// 取消广播
	private void sendBroadcastCancel(Player player) {
		SCBroadcastCancel msg = new SCBroadcastCancel();
		player.Send(msg);
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

	public void broadcast(String content) {
		this.content = content;

		BroadcastTable data = new BroadcastTable();
		data.content = content;
		data.createtime = TimeCreator.GetTimeStamp();
		data.status = 0;
		DBManager.saveOrUpdate(data, DBType.GameDB);

		for (Player player : PlayerManager.allplays.values())
			if (player != null)
				sendBroadcast(player);
	}

	public void broadcastcancel() {
		this.content = null;

		List<BroadcastTable> list = DBManager.get(BroadcastTable.class, DBType.GameDB, Condition.desc("createtime"));
		if (list.size() > 0 && list.get(0).status == 0) {
			list.get(0).status = 1;
			DBManager.saveOrUpdate(list.get(0), DBType.GameDB);
		}

		for (Player player : PlayerManager.allplays.values())
			if (player != null)
				sendBroadcastCancel(player);
	}

	private String content;// 广播内容
}
