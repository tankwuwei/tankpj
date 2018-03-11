package engine.string;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import engine.log.LogUtil;
import engine.util.IOUtil;

/**
 * 消息过滤器，用来过滤屏蔽字
 * @author cxz
 */
public class MessageFilter {
	public static final String MASK = "**";
	static Set<String> filters = new HashSet<>();
	static String[] all;
	static Pattern[] patterns;

	public static void initFilter(String path) {
		try(BufferedReader reader=IOUtil.readTextBuffer(path, "utf-8")){
			String s = reader.readLine();
			while (s != null) {
				s = s.trim();
				if (s.length() > 0) {
					filters.add(s.toLowerCase());
				}
				s = reader.readLine();
			}
		} catch (IOException e) {
			LogUtil.error("读取文件异常" + path, e);
		}
	
	}

	private static void initList() {
		all = filters.toArray(new String[0]);
		Arrays.sort(all, (a, b) -> {
			if (a.length() < b.length())
				return 1;
			if (a.length() > b.length())
				return -1;
			int v = a.compareTo(b);
			return -v;
		});

		patterns = new Pattern[all.length];
		for (int i = 0; i < all.length; i++) {
			patterns[i] = Pattern.compile(Pattern.quote(all[i]));
		}

	}

	/**
	 * 是否是合法的字符串
	 * @param msg
	 * @return
	 */
	public static boolean isValid(String msg) {
		if (all == null) {
			initList();
		}
		for (String s : all) {
			if (msg.indexOf(s) >= 0)
				return false;
		}
		return true;
	}

	/**
	 * 将屏蔽字替换
	 * @param msg
	 * @return
	 */
	public static String filter(String msg) {
		if (patterns == null) {
			initList();
		}
		String origin = msg;
		boolean match = false;
		msg = msg.toLowerCase();
		for (Pattern p : patterns) {
			Matcher matcher = p.matcher(msg);
			if (matcher.find()) {
				match = true;
				msg = matcher.replaceAll(MASK);
			}
		}
		return match ? msg : origin;
	}
}
