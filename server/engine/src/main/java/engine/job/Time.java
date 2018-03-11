package engine.job;

import java.util.Calendar;
import java.util.Locale;

/**
 * 定义一个时间，精度是秒日期可以是按年的，按周的，按月的，时间是从0点开始
 * day从1开始，表示第几天。0表示当天
 * second表示第几秒的时候
 * @author xjf
 *
 */
public class Time {
	public static final byte YEAR = 0;
	public static final byte MONTH = 1;
	public static final byte WEEK = 2;
	public static final byte DAY =3;
	
	public static final int SecondPerWeek = 7*3600*24;
	public static final int SecondPerDay = 3600*24;
	
	public static final Time MONDAY = new Time(WEEK, 1);
	public static final Time TUESDAY = new Time(WEEK, 2);
	public static final Time WEDNESDAY = new Time(WEEK, 3);
	public static final Time THURSDAY = new Time(WEEK, 4);
	public static final Time FRIDAY = new Time(WEEK, 5);
	public static final Time SATURDAY = new Time(WEEK, 6);
	public static final Time SUNDAY = new Time(WEEK, 7);
	
	
	public byte type;
	public int day;
	public int second; //首次启动的时长
	public int repeat; //重新触发的时长
	public boolean first=true; //是否首次触发
	
	private Calendar c = Calendar.getInstance(Locale.CHINA);
	
	public Time(){
		c.setFirstDayOfWeek(Calendar.MONDAY);
	}
	
	public Time(byte type, int day){
		this();
		this.type = type;
		this.day = day;
	}
	
	public Time(byte type, int day, int hour, int minute, int second){
		this();
		this.type = type;
		this.day = day;
		second = getSecond(hour, minute, second);
	}
	
	public Time(int hour, int minute, int second){
		this();
		type = DAY;
		day = 0;
		second = getSecond(hour, minute, second);
	}
	
	public Time(int second){
		this();
		type = DAY;
		day = 0;
		this.second = second;
	}
	
	public void setRepeat(int repeat){
		this.repeat = repeat;
	}
	
	/**
	 * 计算下一次触发的时间差
	 * @return
	 */
	public int getNextFireTime(){
		long t = System.currentTimeMillis();
		c.setTimeInMillis(t);
		switch(type){
		case YEAR:
			//以年为单位的情况暂不处理
			break;
		case MONTH:
			//以月为单位的情况暂不处理
			break;
		case WEEK:
			c.set(Calendar.DAY_OF_WEEK, day);
			c.set(Calendar.HOUR_OF_DAY, 0);
			c.set(Calendar.MINUTE, 0);
			c.set(Calendar.SECOND, 0);
			long want = c.getTimeInMillis()+second;
			if(want < t){
				return (int)((want-t)/1000+SecondPerWeek);
			}else{
				return (int)((want-t)/1000);
			}
		}
		return 0; //
	}
	/**
	 * 得到距离今天0点的时间差，单位是秒
	 * @param hour
	 * @param minute
	 * @param second
	 * @return
	 */
	public int getSecond(int hour, int minute, int second){
		return hour*3600+minute*60+second;
	}
}
