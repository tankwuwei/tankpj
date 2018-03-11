package engine.common;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.springframework.stereotype.Component;

@Component
public class TimeCreator {
	private static long tick = 0;
	private static long starttick = 0;
	private static int TimeStamp = 0;

	public static void tick() {
		tick = System.currentTimeMillis();
		TimeStamp = (int) (tick / 1000);
		if (starttick == 0) {
			starttick = tick;
			tick = 0;
		} else {
			tick -= starttick;
		}
	}

	public static long getTick() {
		return tick;
	}

	/** 获取当前时间戳 */
	public static int GetTimeStamp() {
		return TimeStamp > 0 ? TimeStamp : (int) (System.currentTimeMillis() / 1000);
	}

	/**
	 * 获取当前时间字符串
	 * 
	 * @return
	 */
	public static String GetCurStringTime() {
		return GetStringTime(GetTimeStamp());
	}

	public static String GetStringTime(int time) {
		if (time == 0) {
			return "";
		}
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = new Date();
		date.setTime((long) time * 1000);
		return df.format(date);
	}

	public int GetTimeErase() {
		return GetTimeStamp() - GetMorningTimeStamp();
	}

	/** 获得0点时间戳 */
	public int GetMorningTimeStamp() {
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.MILLISECOND, 0);
		return (int) (cal.getTimeInMillis() / 1000);
	}

	/** 获得24点时间戳 */
	public int GetNightTimeStamp() {
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.HOUR_OF_DAY, 24);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.MILLISECOND, 0);
		return (int) (cal.getTimeInMillis() / 1000);
	}

	/**
	 * 获取下一个整点的时间戳
	 * 
	 * @return
	 */
	static public int getNextClock() {
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.HOUR_OF_DAY, +1);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.MILLISECOND, 0);
		return (int) (cal.getTimeInMillis() / 1000);
	}

	/** 获得周几 */
	public static int GetDayForWeek() {
		Calendar rightNow = Calendar.getInstance();
		return rightNow.get(rightNow.DAY_OF_WEEK) - 1;
	}

	public static int GetMONDAYStamp() {
		Calendar date = Calendar.getInstance();
		date.setFirstDayOfWeek(Calendar.MONDAY);// 将每周第�?天设为星期一，默认是星期�?
		date.add(Calendar.WEEK_OF_MONTH, +1);// 周数减一，即上周
		date.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);// 日子设为星期�?
		date.set(Calendar.HOUR_OF_DAY, 0);
		date.set(Calendar.SECOND, 0);
		date.set(Calendar.MINUTE, 0);
		date.set(Calendar.MILLISECOND, 0);
		return (int) (date.getTimeInMillis() / 1000);
	}

}
