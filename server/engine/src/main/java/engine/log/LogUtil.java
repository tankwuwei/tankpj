package engine.log;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import engine.util.DateUtils;

public class LogUtil {
	public static final int LOGLEVEL_SYSTEM = 1;
	public static final int LOGLEVEL_ERROR = 2;
	public static final int LOGLEVEL_WARN = 3;
	public static final int LOGLEVEL_INFO = 4;
	public static final int LOGLEVEL_DEBUG = 5;

	private static final Logger logger = LoggerFactory.getLogger("");
	// 日志等级
	private static int logLevel = LOGLEVEL_DEBUG;
	// 控制台标记
	private static boolean boConsole = true;

	/**
	 * 是否控制台输出
	 * 
	 * @return
	 */
	public static void setBoConsole(boolean boConsole) {
		LogUtil.boConsole = boConsole;
	}

	/**
	 * 是否控制台输出
	 * 
	 * @return true输出 false不输出
	 */
	public static boolean isBoConsole() {
		return boConsole;
	}

	/**
	 * 获得日志等级
	 * 
	 * @return
	 */
	public static int getLogLevel() {
		return logLevel;
	}

	/**
	 * 设置日志等级
	 * 
	 * @param logLevel
	 */
	public static void setLogLevel(int logLevel) {
		LogUtil.logLevel = logLevel;
	}

	/**
	 * 获得当前时间格式字符串
	 * 
	 * @return
	 */
	private static String getNowDate() {
		return DateUtils.date2String(new Date(), DateUtils.PATTERN_DATE_TIME);
	}

	/**
	 * 记录登记为为debug的信息
	 * 
	 * @param msg
	 */
	public static void error(String msg) {
		String content = String.valueOf(msg);
		if (isBoConsole() == true) {
			System.err.println("[" + getNowDate() + "][error]: " + content);
		}

		if (getLogLevel() >= LOGLEVEL_ERROR) {
			logger.error(content);
		}
	}

	/**
	 * 记录登记为为debug的信息
	 * 
	 * @param msg
	 */
	public static void debug(Object msg) {
		String content = String.valueOf(msg);
		if (isBoConsole() == true && getLogLevel() >= LOGLEVEL_DEBUG) {
			System.out.println("[" + getNowDate() + "][debug]: " + content);
		}

		if (getLogLevel() >= LOGLEVEL_DEBUG) {
			logger.debug(content);
		}
	}

	/**
	 * info等级日志
	 * 
	 * @param msg
	 * @param t
	 */
	public static void info(Object msg) {
		String content = String.valueOf(msg);
		if (isBoConsole() == true) {
			System.out.println("[" + getNowDate() + "][info]: " + content);
		}
		if (getLogLevel() >= LOGLEVEL_INFO) {
			logger.info(content);
		}
	}

	/**
	 * warn等级日志
	 * 
	 * @param msg
	 * @param t
	 */
	public static void warn(Object msg) {
		String content = String.valueOf(msg);
		if (isBoConsole() == true) {
			System.out.println("[" + getNowDate() + "][warn]: " + content);
		}
		if (getLogLevel() >= LOGLEVEL_WARN) {
			logger.warn(content);
		}
	}

	/**
	 * 警告接口
	 * 
	 * @param msg
	 * @param e
	 */
	public static void warn(Object msg, Exception e) {
		String content = String.valueOf(msg);
		if (isBoConsole() == true) {
			System.out.println("[warn]: " + content + " | " + getNowDate());
		}

		if (getLogLevel() >= LOGLEVEL_WARN) {
			logger.warn(content, e);
		}

		if (e != null)
			e.printStackTrace();
	}

	/**
	 * 错误日志
	 * 
	 * @param msg
	 * @param t
	 */
	public static void error(Object msg, Throwable t) {
		String content = String.valueOf(msg);
		if (isBoConsole() == true) {
			System.err.println("[error]: " + content + " | " + getNowDate());
		}

		if (getLogLevel() >= LOGLEVEL_ERROR) {
			logger.error(content, t);
		}

		if (t != null)
			t.printStackTrace();
	}

	public static void error(Throwable t) {
		if (isBoConsole() == true) {
			t.printStackTrace();
		}
		if (getLogLevel() >= LOGLEVEL_ERROR) {
			logger.error("", t);
		}
	}

	public static void error(Exception t) {
		if (isBoConsole() == true) {
			t.printStackTrace();
		}
		if (getLogLevel() >= LOGLEVEL_ERROR) {
			logger.error("", t);
		}
	}

	/**
	 * 系统信息
	 * 
	 * @param msg
	 */
	public static void system(String msg) {
		String content = String.valueOf(msg);
		if (isBoConsole() == true) {
			System.err.println("[" + getNowDate() + "][system]: " + content);
		}

		if (getLogLevel() >= LOGLEVEL_SYSTEM) {
			logger.trace(content);
		}
	}

}
