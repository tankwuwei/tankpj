package engine.server;

import java.net.InetAddress;

import engine.bean.BeanFactory;
import engine.common.logo;
import engine.log.LogUtil;
import engine.net.HttpFileServer;
import engine.net.HttpListener;
import engine.net.HttpServer;
import engine.net.SocketListener;
import engine.net.TcpServer;
import engine.util.Configs;
import engine.util.NumberUtils;

/**
 * @ClassName: ServerBase
 * @Description: 所有服务器的基类
 * @author cxz
 * @date 2016.11.14
 */
public abstract class ServerBase {

	protected void InitAll() {
		InitConfig();
		InitBean();
		InitParams();
		// Console.initConsole();
	}

	public String getVersion() {
		return version;
	}

	public String getServerName() {
		return this.getClass().getSimpleName();
	}

	public int getServerId() {
		return serverid;
	}

	protected void InitConfig() {
		try {
			String localaddress = InetAddress.getLocalHost().getHostAddress();
			configs.AddFullPathConfigFile("config/global.properties");
			configs.AddUncertaintyConfigFile("config/" + getServerName().toLowerCase() + ".properties");
			configs.AddUncertaintyConfigFile("config/" + localaddress + ".properties");
			version = configs.GetStrConfig("version", "no config version");
			zoneid = configs.GetIntConfig("zoneid", 0);
			serverid = configs.GetIntConfig("serverid", 0);
			appid = (short) configs.GetIntConfig("appid", 0);
			appkey = configs.GetStrConfig("appkey", "null");
			usebean = configs.GetIntConfig("usebean", 0) > 0;
			samplerate = NumberUtils.str2int(configs.GetStrConfig("samplerate.time", "60*3"));
			logo.PrintLogo(getServerName(), getVersion(), localaddress);
			LogUtil.setBoConsole(configs.GetBoolConfig("logconsole", false));
			LogUtil.setLogLevel(configs.GetIntConfig("loglevel", LogUtil.LOGLEVEL_DEBUG));
		} catch (Exception exception) {
			LogUtil.warn(exception);
		}

	}

	protected static void InitBean() {
	
		BeanFactory.init();
		LogUtil.system("BeanFactory Initialize OK!");
	}

	/**
	 * 初始化程序自己需要的参数
	 */
	abstract protected void InitParams();

	abstract protected void InitNetwork();

	public static void StartTcpServer(int port, SocketListener listener) {
		StartTcpServer(port, listener, false);
	}

	public static void StartTcpServer(int port, SocketListener listener, boolean bOut) {
		if (port > 0 && listener != null) {
			new Thread(() -> {
				TcpServer server = new TcpServer();
				if (bOut) {
					server.init2("0.0.0.0", port, listener);
				} else {
					server.init("0.0.0.0", port, listener);
				}
			}).start();
		}
	}

	public static void StartHttpServer(int port, HttpListener listener) {
		if (port > 0 && listener != null) {
			new Thread(() -> {
				try {
					HttpServer server = new HttpServer();
					server.start(port, listener);
				} catch (Exception e) {
					LogUtil.error(e);
				}
			}).start();
		}

	}

	public static void StartHttpFileServer(int port, String dir) {
		if (port > 0) {
			new Thread(() -> {
				try {
					HttpFileServer server = new HttpFileServer();
					server.start(port, dir);
				} catch (Exception e) {
					LogUtil.error(e);
				}
			}).start();
		}

	}
	
	abstract public int getServerCount();

	abstract public int getClientCount();

	// 服务器版本
	static public String version;

	// 区域编号
	static public int zoneid;
	// 服务器编号
	static public int serverid;
	static public short appid;
	static public String appkey;
	// 采样时间
	static public int samplerate;
	
	static private boolean usebean;
	// 所有的配置信息
	static public Configs configs = new Configs();
}
