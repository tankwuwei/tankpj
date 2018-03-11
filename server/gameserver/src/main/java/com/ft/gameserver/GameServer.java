package com.ft.gameserver;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import com.ft.csv.resources.init.StartFightConfig;
import com.ft.gameserver.agents.agentManager;
import com.ft.gameserver.basemod.ModManager;
import com.ft.gameserver.clients.ClientManager;
import com.ft.gameserver.servers.ServerManager;

import engine.bean.BeanFactory;
import engine.common.TimeCreator;
import engine.db.DBManager;
import engine.db.client.DBType;
import engine.log.LogUtil;
import engine.server.ServerBase;
import engine.string.MessageFilter;
import engine.util.DateUtils;
import engine.util.InputManager;
import engine.util.NumberUtils;

/**
 * game server Instance
 * 
 * @author cxz
 *
 */
public class GameServer extends ServerBase {

	public GameServer() {

		InitAll();
		InitDB();
		InitResource();
		InitNetwork();
		LogUtil.system("GameServer[" + zoneid + "][" + serverid + "] Initialize Finished.");
		startUpdate();
	}

	/**
	 * 杩炴帴鎵�鏈夌殑dbserver
	 */
	private void InitDB() {
		DBManager.init(DBType.GameDB, configs.GetStrConfig("gamedb.dbserver.address", "0.0.0.0"), configs.GetIntConfig("dbserver.port", 7757), configs.GetIntConfig("gameserver.dbthread", 10));
		DBManager.init(DBType.GlobalDB, configs.GetStrConfig("gamedb.dbserver.address", "0.0.0.0"), configs.GetIntConfig("dbserver.port", 7757), configs.GetIntConfig("gameserver.dbthread", 10));
		DBManager.init(DBType.LogDB, configs.GetStrConfig("logdb.dbserver.address", "0.0.0.0"), configs.GetIntConfig("dbserver.port", 7757), configs.GetIntConfig("gameserver.dbthread", 10));
	}

	@Override
	protected void InitParams() {
		externalip = configs.GetStrConfig("gameserver.externalip", "127.0.0.1");
		externalport = configs.GetIntConfig("gameserver.externalport", 7755);
		maxplayer = configs.GetIntConfig("maxplayer", 1024);
		agentlistenport = configs.GetIntConfig("gameserver.agentlistenport", 8057);

		selfaccount = configs.GetIntConfig("selfaccount", 0) > 0 ? true : false;
		usercenterurl = configs.GetStrConfig("usercenterurl", null);
		createTestAccount = configs.GetIntConfig("createstaccount", 0) > 0;
		testaccountcount = configs.GetIntConfig("testaccountcount", 0);

		loginsvrip = configs.GetStrConfig("loginserver.address", "127.0.0.1");
		loginsvrport = configs.GetIntConfig("loginserver.internalport", 7000);
		zoneid = configs.GetIntConfig("zoneid", 1);
		svrname = configs.GetStrConfig("svrname", "unknown");

		fightServerLevel = configs.GetIntConfig("fightServerLevel", 0);
		startFightPlayer = configs.GetIntConfig("startFightPlayer", 30);

		// ====奖励参数====
		p1 = ServerBase.configs.GetIntConfig("reward.p1", 200);
		p2 = ServerBase.configs.GetIntConfig("reward.p2", 40);
		p3 = ServerBase.configs.GetIntConfig("reward.p3", 100);

		playerRankNum = ServerBase.configs.GetIntConfig("player.rank.num", 10);// 排行榜显示多少玩家

		steamwebkey = configs.GetStrConfig("steamwebkey", "steamkey");
		steamappid = configs.GetStrConfig("steamappid", "123");

		gameserver_windowtitle = configs.GetStrConfig("gameserver.windowtitle", "gameserver");

		islink_loginserver = ServerBase.configs.GetBoolConfig("islink.loginserver", false);
		islink_centerserver = ServerBase.configs.GetBoolConfig("islink.centerserver", false);

		teamTimeMax = NumberUtils.str2int(configs.GetStrConfig("team.time.max", "10*60"));// 10分鐘

		timeautosave = NumberUtils.str2int(configs.GetStrConfig("time.auto.save", "10*60"));// 10分鐘
	}

	private void InitResource() {
		MessageFilter.initFilter("config/filter/Filter.txt");
	}

	@Override
	protected void InitNetwork() {
		BeanFactory.getBean(ServerManager.class).init(configs.GetIntConfig("gameserver.internalport", 7756));
		BeanFactory.getBean(ClientManager.class).init(externalport);
		BeanFactory.getBean(agentManager.class).init(agentlistenport);
		if (islink_loginserver)
			BeanFactory.getBean(LoginSession.class).SetConfigAndStart(loginsvrip, loginsvrport);
		if (islink_centerserver)
			BeanFactory.getBean(GameServerMonitorConntor.class).init(this, configs.GetStrConfig("centerserver.address", "127.0.0.1"), configs.GetIntConfig("centerserver.monitor.port", 7761));

		BeanFactory.getBean(SteamHttp.class).SetConfig(steamwebkey, steamappid);

	}

	@Override
	public int getClientCount() {
		return BeanFactory.getBean(ClientManager.class).getSessionCount();
	}

	@Override
	public int getServerCount() {
		return BeanFactory.getBean(ServerManager.class).getSessionCount();
	}

	private void DoCmd(String strcmd) {
		// LogUtil.debug("输入命令 "+strcmd);
		//
		// if (strcmd.equals("reload")) {
		// ResourceManager resourceManager = BeanFactory.getBean(ResourceManager.class);
		// resourceManager.Reload();
		// }else if(strcmd.equals("matchall")){
		// GlobalMatchRoom match = BeanFactory.getBean(GlobalMatchRoom.class);
		// match.PrintAll();
		// }else if(strcmd.equals("match")){
		// GlobalMatchRoom match = BeanFactory.getBean(GlobalMatchRoom.class);
		// match.PrintSimple();
		// }

	}

	private void UpdateCmds() {
		if (InputManager.getInstance().GetCmd(cmds)) {
			for (String val : cmds) {
				DoCmd(val);
			}
		}

	}

	private void startUpdate() {
		// LoginSession login = BeanFactory.getBean(LoginSession.class);
		ServerManager servers = BeanFactory.getBean(ServerManager.class);
		ClientManager clients = BeanFactory.getBean(ClientManager.class);
		agentManager agents = BeanFactory.getBean(agentManager.class);
		ModManager mod = BeanFactory.getBean(ModManager.class);
		LoginSession loginsession = BeanFactory.getBean(LoginSession.class);
		InputManager.getInstance().Start();
		// AgentManager agents = BeanFactory.getBean(AgentManager.class);
		agentManager agentManager = BeanFactory.getBean(agentManager.class);

		SteamHttp steamHttp = BeanFactory.getBean(SteamHttp.class);
		GameServerMonitorConntor monitorConntor = BeanFactory.getBean(GameServerMonitorConntor.class);

		Date[] date = new Date[] { new Date() };
		Timer timer = new Timer(true);
		timer.schedule(new TimerTask() {
			@Override
			public void run() {
				TimeCreator.tick();
				servers.update();
				clients.update();
				agents.update();

				// login.update();
				// agents.update();
				mod.update();

				if (!DateUtils.isToday(date[0])) {
					mod.onDayChanged();
					dayChange(date);
				}

				if (islink_loginserver)
					loginsession.update();
				// LogUtil.debug("fuck" +Thread.currentThread().getName());
				agentManager.UpdateFightOpen();
				UpdateCmds();
				steamHttp.Update();
				// LogUtil.debug("run timer");
				// monitor.update();
				if (islink_centerserver)
					monitorConntor.update();

				DynamicStartFightPlayer();// 动态调整开始战斗阈值
			}
		}, 0, 25);

	}

	private void dayChange(Date[] date) {
		date[0] = new Date();
	}

	private int time_setStartFightPlayer;

	private void DynamicStartFightPlayer() {
		if (TimeCreator.GetTimeStamp() > time_setStartFightPlayer) {
			time_setStartFightPlayer = TimeCreator.GetTimeStamp() + 5 * 60;// 每隔5分钟调整一次

			int onlinecount = getClientCount();
			startFightPlayer = StartFightConfig.getStartFightPlayer(onlinecount);
			startFightTeam = StartFightConfig.getStartFightTeam(onlinecount);
			LogUtil.info("在线人数=" + onlinecount + ", 开始战斗【人数阈值=" + startFightPlayer + ", 小队阈值=" + startFightTeam + "】");
		}
	}

	//
	static public boolean createTestAccount;
	static public int testaccountcount;

	public static boolean selfaccount;
	public static String usercenterurl;
	public static int externalport;
	public static String externalip;
	public static int maxplayer;
	public static int agentlistenport;

	public static String loginsvrip;
	public static int loginsvrport;
	public static String svrname;
	List<String> cmds = new ArrayList<>();

	// 开启的fightserver的地图id
	public static int fightServerLevel;
	// 开战人数
	public static int startFightPlayer;
	// 开战小队数
	public static int startFightTeam;

	// ====奖励参数====
	public static int p1;
	public static int p2;
	public static int p3;

	public static int playerRankNum;// 排行榜显示多少玩家

	public static String steamwebkey;
	public static String steamappid;

	public static String gameserver_windowtitle;

	private boolean islink_loginserver;
	private boolean islink_centerserver;

	public static int teamTimeMax;

	public static int timeautosave;

	public static boolean isok = true;

	public static void main(String[] args) {
		new GameServer();
	}

}
