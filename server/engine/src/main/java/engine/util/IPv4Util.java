package engine.util;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class IPv4Util {
	public static String ipInt2Str(int ip) {
		StringBuilder sb = new StringBuilder();
		sb.append(String.valueOf(ip >>> 24)).append(".");
		sb.append(String.valueOf((ip & 0xFFFFFF) >>> 16)).append(".");
		sb.append(String.valueOf((ip & 0xFFFF) >>> 8)).append(".");
		sb.append(String.valueOf(ip & 0xFF));
		return sb.toString();
	}

	public static int ipStr2Int(String ipstr) {
		byte[] bytes = null;
		try {
			bytes = InetAddress.getByName(ipstr).getAddress();
		} catch (UnknownHostException e) {
			e.printStackTrace();
			return 0;
		}
		int ip = bytes[3] & 0xFF;
		ip |= (bytes[2] << 8) & 0xFF00;
		ip |= (bytes[1] << 16) & 0xFF0000;
		ip |= (bytes[0] << 24) & 0xFF000000;
		return ip;
	}

	public static void main(String... a) {
		String ip = "192.168.10.100";
		System.err.println(ip);
		System.err.println(ipStr2Int(ip));
		System.err.println(ipInt2Str(ipStr2Int(ip)));
	}
}