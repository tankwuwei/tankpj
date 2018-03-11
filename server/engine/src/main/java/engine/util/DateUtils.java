package engine.util;

import static java.util.Calendar.DATE;
import static java.util.Calendar.DAY_OF_WEEK;
import static java.util.Calendar.HOUR_OF_DAY;
import static java.util.Calendar.MILLISECOND;
import static java.util.Calendar.MINUTE;
import static java.util.Calendar.SECOND;
import static java.util.Calendar.WEEK_OF_YEAR;
import static java.util.Calendar.YEAR;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

//import org.springframework.scheduling.support.CronSequenceGenerator;

/**
 * 日期工具类，用于简化程序中的日期处理
 * @author qu.yy
 *
 */
public class DateUtils extends org.apache.commons.lang.time.DateUtils {

	/** 日期时间格式:年-月-日 时:分:秒[2011-5-5 20:00:00] */
	public static final String PATTERN_DATE_TIME = "yyyy-MM-dd HH:mm:ss";
	/** 日期格式:年-月-日[2011-05-05] */
	public static final String PATTERN_DATE = "yyyy-MM-dd";
	/** 时间格式:时:分:秒[20:00:00] */
	public static final String PATTERN_TIME = "HH:mm:ss";
	/** 短时间格式:时:分[20:00] */
	public static final String PATTERN_SHORT_TIME = "HH:mm";
	
	/** 一个很久很久以前的时间(格林威治的起始时间. 1970-01-01 00:00:00) */
	public static final Date LONG_BEFORE_TIME = string2Date("1970-01-01 00:00:00", PATTERN_DATE_TIME);
	/** 一个很久很久以后的时间(该框架可能被遗弃的时间. 2048-01-01 00:00:00) */
	public static final Date LONG_AFTER_TIME = string2Date("2048-01-01 00:00:00", PATTERN_DATE_TIME);
		
	/**
	 * 检查当前时间和指定时间是否同一周
	 * 
	 * @param year 				年
	 * @param week 				周
	 * @param firstDayOfWeek 	周的第一天设置值，{@link Calendar#DAY_OF_WEEK}
	 * @return
	 */
	public static boolean isSameWeek(int year, int week, int firstDayOfWeek) {
		Calendar cal = Calendar.getInstance();
		cal.setFirstDayOfWeek(firstDayOfWeek);
		return year == cal.get(YEAR) && week == cal.get(WEEK_OF_YEAR);
	}
	
	/**
	 * 检查当前时间和指定时间是否同一周
	 * 
	 * @param  time 				被检查的时间
	 * @param  firstDayOfWeek 		周的第一天设置值，{@link Calendar#DAY_OF_WEEK}
	 * @return {@link Boolean}		是否同一周. true-是, false-不是
	 */
	public static boolean isSameWeek(Date time, int firstDayOfWeek) {
		if(time == null) {
			return false;
		}
		
		Calendar cal = Calendar.getInstance();
		cal.setTime(time);
		cal.setFirstDayOfWeek(firstDayOfWeek);
		return isSameWeek(cal.get(YEAR), cal.get(WEEK_OF_YEAR), firstDayOfWeek);
	}
	
	/**
	 * 获取周的第一天
	 * @param 	firstDayOfWeek 	周的第一天设置值，{@link Calendar#DAY_OF_WEEK}
	 * @param 	time 			指定时间，为 null 代表当前时间
	 * @return  {@link Date}	周的第一天
	 */
	public static Date firstTimeOfWeek(int firstDayOfWeek, Date time) {
		Calendar cal = Calendar.getInstance();
		if (time != null) {
			cal.setTime(time);
		}
		
		cal.setFirstDayOfWeek(firstDayOfWeek);
		int day = cal.get(DAY_OF_WEEK);
		if (day == firstDayOfWeek) {
			day = 0;
		} else if (day < firstDayOfWeek) {
			day = day + (7 - firstDayOfWeek);
		} else if (day > firstDayOfWeek) {
			day = day - firstDayOfWeek;
		}
		
		cal.set(HOUR_OF_DAY, 0);
		cal.set(MINUTE, 0);
		cal.set(SECOND, 0);
		cal.set(MILLISECOND, 0);
		
		cal.add(DATE, -day);
		return cal.getTime();
	}
	
	/**
	 * 检查指定日期是否今天(使用系统时间)
	 * 
	 * @param  date 			被检查的日期
	 * @return {@link Boolean}	是否今天, true-今天, false-不是今天
	 */
	public static boolean isToday(Date date) {
		if (date == null) {
			return false;
		}
		return isSameDay(date, new Date());
	}

	/**
	 * 日期转换成字符串格式
	 * @param  date 			待转换的日期
	 * @param  pattern 		日期格式
	 * @return {@link String}	日期字符串
	 */
	public static String date2String(Date date, String pattern) {
		return new SimpleDateFormat(pattern).format(date);
	}

	/**
	 * 字符串转换成日期格式
	 * @param  string 		待转换的日期字符串
	 * @param  pattern 		日期格式
	 * @return {@link Date}		转换后的日期
	 */
	public static Date string2Date(String string, String pattern) {
		try {
			return new SimpleDateFormat(pattern).parse(string);
		} catch (ParseException e) {
			throw new IllegalArgumentException("无法将字符串[" + string + "]按格式[" + pattern + "]转换为日期", e);
		}
	}
	
	/**
	 * 对一个具体的时间增加时间
	 * 
	 * @param  source		需要修改的时间	
	 * @param  hours		需要增加或者减少的小时
	 * @param  minutes		需要增加或者减少的分
	 * @param  second		需要增加或者减少的秒
	 * @return {@link Date}	返回修改过的时间
	 */
	public static Date addTime(Date source, int hours, int minutes, int second) {
		if (source == null) {
			return null;
		}

		Calendar cal = Calendar.getInstance();
		cal.setTime(source);
		cal.add(Calendar.HOUR_OF_DAY, hours);
		cal.add(Calendar.MINUTE, minutes);
		cal.add(Calendar.SECOND, second);
		return cal.getTime();
	}

	/**
	 * 获取某日的开始时间，即获得某一时间的0点
	 * @param date 需要计算的时间
	 */
	public static Date getFirstTime(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		return calendar.getTime();
	}

	/**
	 * 获得指定时间的下一个0点
	 * 
	 * @param date 需要计算的时间
	 */
	public static Date getNextDayFirstTime(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(date.getTime() + MILLIS_PER_DAY);
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		return calendar.getTime();
	}
	
	/**
	 * 计算2个时间相差的天数,这个方法算的是2个零点时间的绝对时间(天数)
	 * 
	 * @param  startDate 		起始时间
	 * @param  endDate 			结束时间
	 * @return 相差的天数
	 */
	public static int calcIntervalDays(Date startDate, Date endDate) {
		int value = 0;
		if (startDate != null && endDate != null) {
			Date startDate0AM = getFirstTime(startDate);
			Date endDate0AM = getFirstTime(endDate);
			long subValue = startDate0AM.getTime() - endDate0AM.getTime();
			value = Math.abs((int) MathUtils.divideAndRoundUp(subValue, MILLIS_PER_DAY, 0));
		}
		return value;
	}
	
	/**
	 * 获取指定CRON表达式的下一个时间点
	 * @param cron	CRON表达式
	 * @param now	基准时间点
	 * @return 下一个时间点
	 */
//	public static Date getNextTime(String cron, Date now) {
//		CronSequenceGenerator gen = new CronSequenceGenerator(cron, TimeZone.getDefault());
//		Date time = gen.next(now);
//		return time;
//	}
	
	public static String now(){
		return date2String(new Date(), PATTERN_DATE_TIME) + '.'+(System.currentTimeMillis()%1000);
	}
	/**
	 * 将定义的时间区段转成int数组，数组的值表示当天从0点开始计算的秒数。
	 * @param define
	 * @return
	 */
	public static int[] parseTimeRange(String define) {
		if(define==null || define.length()==0)return null;
		String[] ss = define.split("-");
		int[] from = Utility.parseArray(ss[0], ":");
		int[] end = Utility.parseArray(ss[1], ":");
		int[] result = new int[2];
		result[0] = time2second(from[0], from[1], 0);
		result[1] = time2second(end[0], end[1], 0);
		return result;
	}
	/**
	 * 得到定义时间的秒数
	 * @param define
	 * @return
	 */
	public static int parseTime(String define) {
		if(define == null || define.length()==0)return 0;
		int[] time = Utility.parseArray(define, ":");
		return time2second(time[0], time[1], 0);
	}
	
	private static int time2second(int hour, int minute, int second){
		return hour*3600+minute*60+second;
	}

}
