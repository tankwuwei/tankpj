package com.ft.gmserver;

import java.util.Timer;
import java.util.TimerTask;

import com.ft.gmserver.Center.CenterManager;
import com.ft.gmserver.httpclient.ClientManage;

import dbobjects.gmdb.GMAccount;
import engine.bean.BeanFactory;
import engine.common.TimeCreator;
import engine.db.DBManager;
import engine.db.client.DBType;
import engine.log.LogUtil;
import engine.net.HttpListener;
import engine.server.ServerBase;
import io.netty.channel.ChannelHandlerContext;

public class CenterServer extends ServerBase {

	public CenterServer() {
		InitAll();
		InitDB();
		InitNetwork();
		startUpdate();
	}

	private void InitDB() {
		DBManager.init(DBType.GlobalDB, configs.GetStrConfig("globaldb.dbserver.address", "0.0.0.0"), configs.GetIntConfig("dbserver.port", 7757), configs.GetIntConfig("gmserver.dbthread", 10));
		DBManager.init(DBType.GMDB, configs.GetStrConfig("gmdb.dbserver.address", "0.0.0.0"), configs.GetIntConfig("dbserver.port", 7757), configs.GetIntConfig("gmserver.dbthread", 10));
		DBManager.init(DBType.LogDB, configs.GetStrConfig("logdb.dbserver.address", "0.0.0.0"), configs.GetIntConfig("dbserver.port", 7757), configs.GetIntConfig("gmserver.dbthread", 10));
		DBManager.init(DBType.GameDB, configs.GetStrConfig("gamedb.dbserver.address", "0.0.0.0"), configs.GetIntConfig("dbserver.port", 7757), configs.GetIntConfig("gameserver.dbthread", 10));

		CheckAdminAccount();
	}

	private void CheckAdminAccount() {
		GMAccount account = DBManager.getUniqu(GMAccount.class, "account", "admin", DBType.GMDB);
		if (account == null) {
			account = new GMAccount();
			account.account = "admin";
			account.password = "e10adc3949ba59abbe56e057f20f883e";
			account.privilege = Privilege.GetFullPrivilege();
			DBManager.saveOrUpdate(account, DBType.GMDB);
		}
	}

	@Override
	protected void InitParams() {
		websocketserver = configs.GetStrConfig("websocketserver", "ws://127.0.0.1:8080");
		fileserver = configs.GetStrConfig("fileserver", "http://127.0.0.1:9090");
		gameserver_script = configs.GetStrConfig("gameserver.script", "start_gameserver.bat");
	}

	@Override
	protected void InitNetwork() {
		/**
		 * 开启http，用于工具前台连接
		 */
		HttpListener listener2 = new HttpListener() {
			public void request(ChannelHandlerContext ctx, Object msg) {
				BeanFactory.getBean(ClientManage.class).OnRequest(ctx, msg);
			}
		};
		int HttpPort = configs.GetIntConfig("http.port", 8080);
		LogUtil.system("HttpServer listener Listening at " + HttpPort);
		StartHttpServer(HttpPort, listener2);

		/**
		 * HttpFileServer web静态资源
		 */
		int port = configs.GetIntConfig("fileserver.port", 9090);
		String dir = configs.GetStrConfig("fileserver.dir", "D:/fileserver/web/staticsource");
		LogUtil.system("HttpFileServer port: " + port);
		StartHttpFileServer(port, dir);

		BeanFactory.getBean(CenterManager.class).init(configs.GetIntConfig("centerserver.monitor.port", 7761));
	}

	private void startUpdate() {
		CenterManager CenterManager = BeanFactory.getBean(CenterManager.class);
		Timer timer = new Timer(true);
		timer.schedule(new TimerTask() {
			@Override
			public void run() {
				TimeCreator.tick();
				CenterManager.update();
			}
		}, 0, 25);
	}


	@Override
	public int getServerCount() {
		return 0;
	}

	@Override
	public int getClientCount() {
		return 0;
	}

	public static void main(String[] args) {
		new CenterServer();
	}

	// ====http server paras====
	public static String websocketserver;
	public static String fileserver;

	public static String gameserver_script;
}
