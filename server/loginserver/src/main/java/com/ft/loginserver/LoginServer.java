package com.ft.loginserver;

import java.util.Timer;
import java.util.TimerTask;

import com.ft.loginserver.clients.ClientManager;
import com.ft.loginserver.servers.ServerManager;

import common.MonitorConntor;
import engine.bean.BeanFactory;
import engine.common.TimeCreator;
import engine.db.DBManager;
import engine.db.client.DBType;
import engine.log.LogUtil;
import engine.server.ServerBase;
import engine.util.NumberUtils;

public class LoginServer extends ServerBase {

	public static boolean SelfAccount = false;
	public static int BanIPCount; // ban ip时候连续错误的次数
	public static int BanIPTime; // ban ip时间
	public static String usercenterurl = "";

	public LoginServer() {
		InitAll();
		// InitDB();
		InitNetwork();
		LogUtil.system("LoginServer Initialize Finished.");
		startUpdate();
	}

	public int getServerCount() {
		return BeanFactory.getBean(ServerManager.class).getSessionCount();
	}

	public int getClientCount() {
		return BeanFactory.getBean(ClientManager.class).getSessionCount();
	}

	@Override
	protected void InitParams() {
		SelfAccount = configs.GetBoolConfig("selfaccount", false);
		BanIPCount = configs.GetIntConfig("banipcount", 0);
		BanIPTime = configs.GetIntConfig("baniptime", 0);
		usercenterurl = configs.GetStrConfig("usercenterurl", "");
	}

	@Override
	protected void InitNetwork() {
		BeanFactory.getBean(ServerManager.class).init(configs.GetIntConfig("loginserver.internalport", 7000));
		BeanFactory.getBean(ClientManager.class).init(configs.GetIntConfig("loginserver.externalport", 7100));
		// BeanFactory.getBean(MonitorConntor.class).init(this,
		// configs.GetStrConfig("centerserver.address", "127.0.0.1"),
		// configs.GetIntConfig("centerserver.monitor.port", 7761));
		BeanFactory.getBean(MonitorConntor.class).init(this, configs.GetStrConfig("centerserver.address", "127.0.0.1"),
				configs.GetIntConfig("centerserver.monitor.port", 7761));
	}

	private void InitDB() {
		DBManager.init(DBType.GlobalDB, configs.GetStrConfig("globaldb.dbserver.address", "0.0.0.0"),
				configs.GetIntConfig("dbserver.port", 7757), configs.GetIntConfig("gameserver.dbthread", 10));
	}

	private void startUpdate() {
		ServerManager servers = BeanFactory.getBean(ServerManager.class);
		ClientManager clients = BeanFactory.getBean(ClientManager.class);
		MonitorConntor monitorConntor = BeanFactory.getBean(MonitorConntor.class);
		Timer timer = new Timer(true);
		timer.schedule(new TimerTask() {
			@Override
			public void run() {
				TimeCreator.tick();
				servers.update();
				clients.update();
				monitorConntor.update();
			}
		}, 0, 25);
	}

	
	public static void main(String[] args) {
		new LoginServer();
	}

}
