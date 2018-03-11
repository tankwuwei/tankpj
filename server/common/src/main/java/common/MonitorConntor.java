package common;

import java.io.File;
import java.net.InetAddress;
import java.util.List;
import java.util.Properties;

import org.hyperic.sigar.CpuPerc;
import org.hyperic.sigar.Mem;
import org.hyperic.sigar.NetInterfaceConfig;
import org.hyperic.sigar.Sigar;
import org.hyperic.sigar.cmd.Ps;
import org.springframework.stereotype.Controller;

import engine.common.TimeCreator;
import engine.log.LogUtil;
import engine.net.CPacket;
import engine.net.ConnectBaseSession;
import engine.net.NativeBuffer;
import engine.net.SocketListener;
import engine.net.TcpConnector;
import engine.server.ServerBase;
import generated.cgame.packets.ClientPackets;
import generated.cgame.packets.Code;
import generated.cgame.packets.objects.ServiceInfo;
import generated.cgame.packets.objects.SystemInfo;
import generated.cgame.packets.server.ReportServiceInfo;
import generated.cgame.packets.server.ReportSystemInfo;
import generated.cgame.packets.server.ServerType;
import generated.cgame.packets.server.VMsgConnect;
import generated.cgame.packets.server.VMsgDisconnect;
import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;
import io.netty.channel.socket.DatagramPacket;

/**
 * 连接监控的会话，主要是汇报自己的状况
 * 
 * @author cxz
 *
 */
@Controller
public class MonitorConntor extends ConnectBaseSession {
	// 采样频率
	public final static Sigar sigar = initSigar();
	private String processName;
	private String version;
	private Long processid;
	private int serverid;
	private ServerBase server;

	public void init(ServerBase server, String ip, int port) {
		processName = server.getServerName();
		version = server.getVersion();
		String processName = java.lang.management.ManagementFactory.getRuntimeMXBean().getName();
		processid = Long.parseLong(processName.substring(0, processName.indexOf('@')));
		serverid = server.getServerId();
		this.server = server;
		init(ip, port);
	}

	@Override
	protected CPacket translatePacket(NativeBuffer buf) {
		return ClientPackets.get(buf);
	}

	@Override
	protected void onDisconnect() {
		LogUtil.debug("disconnect from centerserver!");
		doLink();
	}

	@Override
	protected void onConnect() {
		LogUtil.debug("connect to centerserver!");
		sampleTime = 0;
	}

	int sampleTime;

	@Override
	protected void updateLogic() {
		report();
		Shutdown();
	}

	public void report() {
		if (TimeCreator.GetTimeStamp() > sampleTime) {
			sampleTime = TimeCreator.GetTimeStamp() + server.samplerate;

			sampleServiceInfo();
			if (processName.equals("Agent")) {
				sampleSystemInfo();
			}
		}
	}

	public void Shutdown() {
	}

	private void sampleSystemInfo() {

		ReportSystemInfo msg = new ReportSystemInfo();
		msg.info = new SystemInfo();
		Properties p = System.getProperties();
		msg.info.os = p.getProperty("os.name") + "(" + p.getProperty("os.arch") + " " + p.getProperty("os.version") + ")";
		msg.info.ip = getIP();
		msg.info.sampleTime = sampleTime;

		try {
			Mem mem = sigar.getMem();
			msg.info.sysmem = mem.getTotal();
			msg.info.sysmemfree = mem.getFree();
			CpuPerc cpuList[] = sigar.getCpuPercList();
			msg.info.cpucount = cpuList.length;
			double idle = 0;
			for (CpuPerc info : cpuList) {
				idle += info.getIdle();
			}
			msg.info.syscpuidle = (int) (idle * 100 / cpuList.length);
			msg.info.network = "";
			String ifNames[] = sigar.getNetInterfaceList();
			for (String string : ifNames) {
				NetInterfaceConfig ifconfig = sigar.getNetInterfaceConfig(string);
				if (!ifconfig.getAddress().equals("0.0.0.0")) {
					msg.info.network += ifconfig.getName() + ":";
					msg.info.network += ifconfig.getAddress() + " / ";
				}
			}
		} catch (Exception e) {
			LogUtil.warn("sampleSystemInfo: " + e);
		}
		Send(msg);

	}

	private void sampleServiceInfo() {
		ReportServiceInfo msg = new ReportServiceInfo();
		msg.info = new ServiceInfo();
		msg.info.processname = processName;
		msg.info.serverid = serverid;
		msg.info.zoneid = server.zoneid;
		// LogUtil.debug("1");
		msg.info.processid = processid;
		// LogUtil.debug("12");
		msg.info.version = version;
		msg.info.servercount = server.getServerCount();
		msg.info.clientcount = server.getClientCount();
		msg.info.ip = getIP();
		msg.info.sampleTime = sampleTime;
		try {
			Ps ps = new Ps();
			List<String> infos = ps.getInfo(sigar, processid);
			msg.info.starttime = infos.get(2);
			msg.info.mem = infos.get(4);
			msg.info.cpu = infos.get(7);
		} catch (Exception e) {
			LogUtil.warn("sampleServiceInfo: " + e);
		}
		Send(msg);
	}

	@Override
	protected void processMsg(CPacket packet) {
		switch (packet.code) {
		case Code.VMsgConnect:
			onConnect();
			break;
		case Code.VMsgDisconnect:
			onDisconnect();
			break;
		case Code.Shutdown:
			onShutdown();
			break;

		default:
			break;
		}
	}

	protected void onShutdown() {

	}

	SocketListener listener = new SocketListener() {

		@Override
		public void setUdpChannel(Channel channel) {
		}

		@Override
		public void onPacket(Channel channel, DatagramPacket packet) {
		}

		@Override
		public void onDisconnected(Channel channel) {
			VMsgDisconnect msg = new VMsgDisconnect();
			addPacket(msg);
		}

		@Override
		public void onData(Channel channel, ByteBuf msg) {
			processMsg(msg);
		}

		@Override
		public void onConnected(Channel channel) {
			setChannel(channel);
			VMsgConnect msg = new VMsgConnect();
			addPacket(msg);
		}
	};

	protected void doLink() {

		new Thread(() -> {
			while (!new TcpConnector().connect(ip, port, listener)) {
				try {
					LogUtil.debug(getClass().getName() + " Can not connect to " + ip + " @ " + port + ". wait... ");
					Thread.sleep(10000);
				} catch (Exception e) {
					LogUtil.warn(e);
				}
			}
		}).start();

	}

	private static Sigar initSigar() {
		try {
			String file = System.getProperty("user.dir") + File.separator + "lib";
			String path = System.getProperty("java.library.path");
			if (System.getProperty("os.name").toLowerCase().indexOf("windows") >= 0) {
				path += ";" + file;
			} else {
				path += ":" + file;
			}
			System.setProperty("java.library.path", path);
			return new Sigar();
		} catch (Exception e) {
			LogUtil.warn("initSigar " + e);
			return null;
		}
	}

	private String getIP() {
		try {
			return InetAddress.getLocalHost().getHostAddress();
		} catch (Exception e) {
			LogUtil.warn("getIP " + e);
			return null;
		}
	}

	protected void sendServerType(short type) {
		ServerType msg = new ServerType();
		msg.type = type;
		Send(msg);
	}
}
