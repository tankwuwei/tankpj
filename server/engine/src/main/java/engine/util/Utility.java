package engine.util;

import java.awt.Image;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Scanner;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import javax.imageio.ImageIO;

import org.apache.commons.lang.ArrayUtils;
import org.jdom2.Document;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;

import engine.log.LogUtil;

public class Utility {

	public static final Charset ascii = Charset.forName("ascii");

	public static final int OneMinute_Millis = 60 * 1000;
	public static final int ONEHOUR_MILLIS = 60 * OneMinute_Millis;

	public static java.util.Random r = new java.util.Random(System.currentTimeMillis());
	static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	static SimpleDateFormat dateFormat1 = new SimpleDateFormat("yyyyMMdd");
	static SimpleDateFormat dateFormat2 = new SimpleDateFormat("yyyy年MM月dd日");
	public static String configRoot;

	public static long TimeBegin;
	static {
		try {
			TimeBegin = dateFormat1.parse("20140601").getTime();
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}
	static String Chars = "abcdefghijklmnopqrstuvwsyzABCDEFGHIJKLMNOPQRSTUVWXYZ";

	private static long t20120101;
	private static long dayTime = 24 * 3600 * 1000l;

	static {
		init();
	}

	public static void init() {
		Calendar c = Calendar.getInstance();
		c.set(2012, 0, 1, 0, 0, 0);
		t20120101 = c.getTimeInMillis();
		t20120101 = (t20120101 / 1000l) * 1000l;
	}

	public static void setConfigRoot(String root) {
		configRoot = root;
	}

	/**
	 * 此类方法已经移植到MathUtils中
	 */
	@Deprecated
	public static final double sin(int arc) {
		return MathUtils.sin(arc);
	}

	/**
	 * 此类方法已经移植到MathUtils中
	 */
	@Deprecated
	public static final double cos(int arc) {
		return MathUtils.cos(arc);
	}

	public static boolean isEmpty(String value) {
		if (value == null || value.length() < 1)
			return true;
		int length = value.length();
		for (int i = 0; i < length; i++) {
			char c = value.charAt(i);
			if (!Character.isWhitespace(c))
				return false;
		}
		return true;
	}

	/**
	 * 此类方法已经移植到MathUtils中
	 */
	@Deprecated
	public static int random(int low, int up, boolean includeLow, boolean includeUp) {
		return MathUtils.random(low, up, includeLow, includeUp);
	}

	/**
	 * 此类方法已经移植到MathUtils中
	 */
	@Deprecated
	public static int getRandomBetween(int min, int max) {
		return MathUtils.getRandomBetween(min, max);
	}

	/**
	 * 此类方法已经移植到MathUtils中
	 */
	@Deprecated
	public static int getRandomInt(int round) {
		return MathUtils.getRandomInt(round);
	}

	/**
	 * 此类方法已经移植到MathUtils中
	 */
	@Deprecated
	public static int[] getRandomNoRepeat(int max, int min, int num) {
		return MathUtils.getRandomNoRepeat(max, min, num);
	}

	/**
	 * 此类方法已经移植到MathUtils中
	 */
	@Deprecated
	public static long getRandomLong(long round) {
		return MathUtils.getRandomLong(round);
	}

	/**
	 * 此类方法已经移植到MathUtils中
	 */
	@Deprecated
	public static float getRandomFloat(float radius) {
		return MathUtils.getRandomFloat(radius);
	}

	public static String getRandomString(int len) {
		StringBuffer sb = new StringBuffer();
		int l = Chars.length();
		for (int i = 0; i < len; i++) {
			sb.append(Chars.charAt(getRandomInt(l)));
		}
		return sb.toString();
	}

	public static List<String> readTextFile(String path) {
		BufferedReader reader = null;
		URL url = ClassLoader.getSystemResource(path);
		InputStream is = null;
		try {
			is = url.openStream();
			reader = new BufferedReader(new InputStreamReader(is, "UTF8"));
			String line = null;
			List<String> result = new ArrayList<String>();
			while ((line = reader.readLine()) != null) {
				if (line.length() > 0)
					result.add(line);
			}
			return result;
		} catch (Exception e) {
			// System.exit(0);
			return null;
		} finally {
			if (reader != null)
				IOUtil.saveClose(reader);
		}
	}

	public static String readText(String path) {
		BufferedReader reader = null;
		// URL url=ClassLoader.getSystemResource(path);
		InputStream is = null;
		try {
			// is=url.openStream();
			is = IOUtil.getResourceAsStream(path);
			reader = new BufferedReader(new InputStreamReader(is, "UTF8"));
			String line = null;
			StringBuilder result = new StringBuilder();
			while ((line = reader.readLine()) != null) {
				if (line.length() > 0)
					result.append(line).append("\n");
			}
			return result.toString();
		} catch (Exception e) {
			// System.exit(0);
			return null;
		} finally {
			if (reader != null)
				IOUtil.saveClose(reader);
		}
	}

	public static Document readDocument(String path) {
		// return readDocument(new File(configRoot,path));
		return readDocument(IOUtil.getResourceAsStream(path));
	}

	public static Document readDocument(InputStream is) {
		try {
			Document doc = new SAXBuilder().build(is);
			is.close();
			return doc;
		} catch (JDOMException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static Document readDocument(File file) {
		try {
			Document doc = new SAXBuilder().build(file);
			return doc;
		} catch (JDOMException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static Document readXML(File path) {
		try {
			Document doc = new SAXBuilder().build(path);
			return doc;
		} catch (Exception ie) {
			ie.printStackTrace();
		}
		return null;
	}

	public static String getDate(Date date) {
		return dateFormat.format(date);
	}

	public static String getDate1(Date date) {
		return dateFormat1.format(date);
	}

	public static String getDate2(Date date) {
		return dateFormat2.format(date);
	}

	public static Date getDate(String date) {
		try {
			return dateFormat.parse(date);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static long timeFormat(String date) {
		try {
			return dateFormat.parse(date).getTime();
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return 0;
	}

	public static String timeFormat(long time) {
		return dateFormat.format(new Date(time));
	}

	public static boolean isDifferentDay(long t1, long t2) {
		return leaveDay(t1, t2) != 0;
		// if(t1==0) return false;
		// GregorianCalendar c1=calendarPool.checkOut();
		// c1.setTimeInMillis(t1);
		// GregorianCalendar c2=calendarPool.checkOut();
		// c2.setTimeInMillis(t2);
		// int year1=c1.get(Calendar.YEAR);
		// int year2=c2.get(Calendar.YEAR);
		// boolean differentDay=year1!=year2;
		// if(!differentDay){
		// int day1=c1.get(Calendar.DAY_OF_YEAR);
		// int day2=c2.get(Calendar.DAY_OF_YEAR);
		// differentDay=day1!=day2;
		// }
		//
		// calendarPool.checkIn(c1);
		// calendarPool.checkIn(c2);
		//
		// return differentDay;
	}

	public static int leaveDay(long t1, long t2) {

		// GregorianCalendar c1=calendarPool.checkOut();
		// c1.setTimeInMillis(t1);
		// GregorianCalendar c2=calendarPool.checkOut();
		// c2.setTimeInMillis(t2);
		// int year1=c1.get(Calendar.YEAR);
		// int year2=c2.get(Calendar.YEAR);
		// int day1=c1.get(Calendar.DAY_OF_YEAR);
		// int day2=c2.get(Calendar.DAY_OF_YEAR);
		//
		// int differentDay=(year1-year2)*365+day1-day2;
		//
		// calendarPool.checkIn(c1);
		// calendarPool.checkIn(c2);
		//
		// return differentDay;
		return leaveDay2(t1, t2);
	}

	/**
	 * �õ��������ǰ�����������㣬���ǰ�������������
	 * 
	 * @param t1
	 * @param t2
	 * @return
	 */
	public static int leaveDay2(long t1, long t2) {
		return (int) ((t1 - t20120101) / dayTime - (t2 - t20120101) / dayTime);
	}

	public static boolean isDifferentDay(GregorianCalendar c1, long t2) {
		long t = c1.getTimeInMillis();
		return leaveDay2(t, t2) != 0;
		// GregorianCalendar c2=calendarPool.checkOut();
		// c2.setTimeInMillis(t2);
		// int year1=c1.get(Calendar.YEAR);
		// int year2=c2.get(Calendar.YEAR);
		// boolean differentDay=year1!=year2;
		// if(!differentDay){
		// int day1=c1.get(Calendar.DAY_OF_YEAR);
		// int day2=c2.get(Calendar.DAY_OF_YEAR);
		// differentDay=day1!=day2;
		// }
		// calendarPool.checkIn(c2);
		// return differentDay;

	}

	/**
	 * 此类方法已经移植到MathUtils中
	 */
	@Deprecated
	public static double sqrt(int value) {
		return MathUtils.sqrt(value);
	}

	public static int[] parseArray(String s) {
		if (s == null || s.length() == 0)
			return new int[0];
		s = s.trim();
		if (s.length() == 0)
			return new int[0];
		String ss[] = s.split(",");
		int[] result = new int[ss.length];
		for (int i = 0; i < ss.length; i++) {
			result[i] = Integer.parseInt(ss[i]);
		}
		return result;
	}

	public static long[] parseLongArray(String s) {
		if (s == null)
			return new long[0];
		s = s.trim();
		if (s.length() == 0)
			return new long[0];
		String ss[] = s.split(",");
		long[] result = new long[ss.length];
		for (int i = 0; i < ss.length; i++) {
			result[i] = Long.parseLong(ss[i]);
		}
		return result;
	}

	public static int[][] parseArrayTwo(String s) {
		return parseArrayTwo(s, ",", ":");
	}

	public static int[][] parseArrayTwo(String s, String limit1, String limit2) {
		if (s == null)
			return new int[0][0];
		s = s.trim();
		if (s.length() == 0)
			return new int[0][0];
		String ss[] = s.split(limit1);
		int[][] result = new int[ss.length][];
		for (int i = 0; i < ss.length; i++) {
			String s2[] = ss[i].split(limit2);
			int[] r2 = new int[s2.length];
			for (int j = 0; j < s2.length; j++) {
				r2[j] = Integer.parseInt(s2[j]);
			}
			result[i] = r2;
		}
		return result;
	}

	// public static int parseEffectInt(String s){
	// if (Utility.isEmpty(s))return 0;
	// String[] array = s.split(":");
	// System.out.println(s);
	// if (isEmpty(s))return 0;
	// return
	// parseEffectType(array[0],EffectCons.EFFECT_NAME)<<16|parseInt(array[1]);
	// }
	/**
	 * @param s
	 * @param minLength
	 *            �������С���ȣ���û�����Ƿ��ص����鳤��Ϊ0
	 * @return
	 */
	public static int[] parseArray(String s, int minLength) {
		if (s == null)
			return new int[0];
		s = s.trim();
		if (s.length() == 0)
			return new int[0];
		String ss[] = s.split(",");
		int[] result = new int[ss.length > minLength ? ss.length : minLength];
		for (int i = 0; i < ss.length; i++) {
			result[i] = Integer.parseInt(ss[i]);
		}
		return result;
	}

	public static int[] parseArray2(String s) {
		if (s == null)
			return new int[0];
		s = s.trim();
		if (s.length() == 0)
			return new int[0];
		String ss[] = s.split("#");
		int[] result = new int[ss.length];
		for (int i = 0; i < ss.length; i++) {
			result[i] = Integer.parseInt(ss[i]);
		}
		return result;
	}

	public static int[] parseArray(String s, String limit) {
		s = s.trim();
		if (s.length() <= 0)
			return new int[0];
		String ss[] = s.split(limit);
		int result[] = new int[ss.length];
		for (int i = 0; i < ss.length; i++) {
			result[i] = Integer.parseInt(ss[i]);
		}
		return result;
	}

	public static int[] parseArray(String s, String split1, String split2) {
		s = s.trim();
		if (s.length() <= 0)
			return new int[0];
		String ss[] = s.split(split1);
		int result[] = new int[ss.length];
		for (int i = 0; i < ss.length; i++) {
			String[] tt = ss[i].split(":");
			result[i] = (Integer.parseInt(tt[1]) << 16) | Integer.parseInt(tt[0]);
		}
		return result;
	}

	public static long parseLong(String s) {
		if (s == null || s.length() == 0) {
			return 0;
		}
		try {
			return Long.parseLong(s);
		} catch (NumberFormatException nfe) {

			return 0;
		}
	}

	public static int parseInt(String s) {
		if (s == null || s.length() == 0) {
			return 0;
		}
		try {
			return Integer.parseInt(s);
		} catch (NumberFormatException nfe) {
			LogUtil.error(nfe);
			return 0;
		}
	}

	public static float parseFloat(String s) {
		if (s == null || s.length() == 0) {
			return 0;
		}
		try {
			return Float.parseFloat(s);
		} catch (NumberFormatException nfe) {
			Thread.dumpStack();
			return 0;
		}
	}

	public static double parseDouble(String s) {
		if (s == null || s.length() == 0) {
			return 0;
		}
		try {
			return Double.parseDouble(s);
		} catch (NumberFormatException nfe) {
			Thread.dumpStack();
			return 0;
		}
	}

	public static boolean parseBoolean(String value) {
		return value != null && value.equals("true");
	}

	public static float[] parseFloatArray(String s) {
		if (s == null)
			return new float[0];
		s = s.trim();
		if (s.length() == 0)
			return new float[0];
		String ss[] = s.split(",");
		float[] result = new float[ss.length];
		for (int i = 0; i < ss.length; i++) {
			result[i] = parseFloat(ss[i]);
		}
		return result;
	}

	/**
	 * ��byte����д��intֵ���Զ��ֽ�intΪ4��byte
	 * 
	 * @param src
	 * @param index
	 * @param value
	 */
	public static void setInt2ByteArray(byte[] src, int index, int value) {
		for (int i = 0; i < 4; i++, index++) {
			src[index] = (byte) ((value >> ((3 - i) * 8)) & 0xFF);
		}
	}

	public static int[] toIntArray(byte[] src) {
		if (src == null)
			return null;
		if (src.length == 0)
			return new int[0];
		ByteBuffer buffer = ByteBuffer.wrap(src);
		int length = src.length / 4;
		int[] result = new int[length];
		for (int i = 0; i < length; i++) {
			result[i] = buffer.getInt();
		}
		return result;
	}

	/**
	 * ȡint�����е����ֵ
	 */
	public static int max(int[] array) {
		int result = 0;
		for (int i : array) {
			if (i > result)
				result = i;
		}
		return result;
	}

	/**
	 * @param src
	 * @param index
	 * @return
	 */
	public static int getIntFromByteArray(byte[] src, int index) {
		return (src[index] << 24) | (src[index + 1] << 16) | (src[index + 2] << 8) | (src[index + 3]);
	}

	/**
	 * @param ele
	 * @return
	 */
	public static boolean arrayDel(int[] array, int ele) {
		if (array == null)
			return false;
		int[] new_Array = new int[array.length - 1];
		int index = 0;
		for (int i = 0; i < array.length; i++) {
			if (array[i] != ele) {
				if (i != new_Array.length) {
					new_Array[index++] = array[i];
				} else {
					new_Array = null;
				}
			}
		}
		if (new_Array == null)
			return false;
		else {
			array = new_Array;
			return true;
		}
	}

	/**
	 * @param ele
	 * @return
	 */
	public static void arrayAdd(int[] array, int ele) {
		int[] new_Array = new int[array.length + 1];
		for (int i = 0; i < new_Array.length; i++) {
			if (i == new_Array.length - 1) {
				new_Array[i] = ele;
			} else {
				new_Array[i] = array[i];
			}
		}
		array = new_Array;
	}

	public static boolean in_Array(int value, int[] values) {
		if (values == null)
			return false;
		for (int i = 0; i < values.length; i++) {
			if (values[i] == value)
				return true;
		}
		return false;
	}

	public static boolean in_Array(short value, short[] values) {
		if (values == null)
			return false;
		for (int i = 0; i < values.length; i++) {
			if (values[i] == value)
				return true;
		}
		return false;
	}

	public static boolean in_Array(byte value, byte[] values) {
		if (values == null)
			return false;
		for (int i = 0; i < values.length; i++) {
			if (values[i] == value)
				return true;
		}
		return false;
	}

	public static boolean in_Array(int value, int[][] values, int index) {
		if (values == null)
			return false;
		int length = values.length;
		for (int i = 0; i < length; i++) {
			int l = index != 0 ? index : values[i].length;
			for (int j = 0; j < l; j++) {
				if (values[i][j] == value)
					return true;
			}
		}
		return false;
	}

	public static boolean in_Array(long value, long[] values) {
		if (values == null)
			return false;
		for (int i = 0; i < values.length; i++) {
			if (values[i] == value)
				return true;
		}
		return false;
	}

	public static boolean in_Array(long value, List<Long> values) {
		if (values == null)
			return false;
		for (int i = 0; i < values.size(); i++) {
			if (values.get(i) == value)
				return true;
		}
		return false;
	}

	public static boolean in_Array(Object value, Object[] values) {
		if (values == null)
			return false;
		for (int i = 0; i < values.length; i++) {
			if (values[i] == value)
				return true;
		}
		return false;
	}

	public static boolean in_Array(String value, String[] values) {
		if (values == null)
			return false;
		for (int i = 0; i < values.length; i++) {
			if (values[i].equals(value))
				return true;
		}
		return false;
	}

	/**
	 * 随机乱序
	 * 
	 * @param objs
	 */
	public static <T> void mix(T[] objs) {
		if (objs == null || objs.length == 1) {
			return;
		}
		for (int i = 0; i < objs.length; i++) {
			swap(objs, 0, MathUtils.getRandomInt(objs.length));
		}
	}

	/**
	 * 交换
	 * 
	 * @param roles
	 * @param index1
	 * @param index2
	 */
	public static <T> void swap(T[] objs, int index1, int index2) {
		if (index1 == index2)
			return;
		T r = objs[index1];
		objs[index1] = objs[index2];
		objs[index2] = r;
	}

	public static int getStringLengthUTF(String str) {
		int strlen = str.length();
		int utflen = 0;
		int c = 0;

		for (int i = 0; i < strlen; i++) {
			c = str.charAt(i);
			if ((c >= 0x0001) && (c <= 0x007F)) {
				utflen++;
			} else if (c > 0x07FF) {
				utflen += 2;
			} else {
				utflen += 3;
			}
		}
		return utflen;
	}

	public static byte[] readXMLAsBytes(String path) {
		BufferedInputStream reader = null;
		try {
			InputStream is = new FileInputStream(new File(configRoot, path));
			reader = new BufferedInputStream(is);
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			byte[] buffer = new byte[8192];
			int length = -1;
			while ((length = reader.read(buffer)) != -1) {
				baos.write(buffer, 0, length);
			}
			return baos.toByteArray();
		} catch (Exception e) {
			e.printStackTrace();

		} finally {
			IOUtil.saveClose(reader);
		}

		return null;
	}

	public static String toString(List<String> memberNames, String delimiter) {
		if (memberNames == null)
			return null;
		StringBuilder builder = new StringBuilder();
		int length = memberNames.size();
		for (int i = 0; i < length; i++) {
			builder.append(memberNames.get(i));
			if (i != length - 1)
				builder.append(delimiter);
		}
		return builder.toString();
	}

	public static List<String> toList(String names, String delimiter) {
		List<String> result = new ArrayList<String>();
		String[] parts = names.split(delimiter);
		for (String name : parts) {
			result.add(name);
		}
		return result;
	}

	public static void println(Object obj) {
		print(obj);
		System.out.println();
	}

	private static void print(Object obj) {
		if (obj instanceof int[]) {
			int[] is = (int[]) obj;
			for (int i = 0; i < is.length; i++) {
				System.out.print("" + is[i] + ",");
			}
		} else if (obj instanceof long[]) {
			long[] is = (long[]) obj;
			for (int i = 0; i < is.length; i++) {
				System.out.print("" + is[i] + ",");
			}
		} else if (obj instanceof Object[]) {
			Object[] os = (Object[]) obj;
			for (int i = 0; i < os.length; i++) {
				print(os[i]);
				print(",");
			}
		} else if (obj instanceof short[]) {
			short[] is = (short[]) obj;
			for (int i = 0; i < is.length; i++) {
				System.out.print("" + is[i] + ",");
			}
		} else if (obj instanceof Integer) {
			System.out.print(((Integer) obj).toString());
		} else if (obj instanceof byte[]) {
			byte[] is = (byte[]) obj;
			for (int i = 0; i < is.length; i++) {
				System.out.print("" + Integer.toBinaryString(is[i] & 0xFF) + ",");
			}
		} else {
			System.out.print(obj);
		}
	}

	/**
	 * .
	 * 
	 * @return
	 */
	public static long get24HourMill() {
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		calendar.add(Calendar.DAY_OF_MONTH, 1);

		return System.currentTimeMillis() - calendar.getTimeInMillis();
	}

	public static boolean isLegalPlayerName(String name) {
		for (int i = name.length() - 1; i > -1; i--) {
			char c = name.charAt(i);
			if (Character.isWhitespace(c))
				return false;
			if (Character.isISOControl(c))
				return false;
		}
		return true;
	}

	public static int indexOf(String value, String[] values) {
		int length = values.length;
		for (int i = 0; i < length; i++) {
			if (values[i].equals(value))
				return i;
		}
		return -1;
	}

	public static int indexOf(int value, int[] values) {
		int length = values.length;
		for (int i = 0; i < length; i++) {
			if (values[i] == value)
				return i;
		}
		return -1;
	}

	public static int indexOf(byte value, byte[] values) {
		int length = values.length;
		for (int i = 0; i < length; i++) {
			if (values[i] == value)
				return i;
		}
		return -1;
	}

	public static int indexOf(long value, long[] values) {
		if (values == null)
			return -1;
		int length = values.length;
		for (int i = 0; i < length; i++) {
			if (values[i] == value)
				return i;
		}
		return -1;
	}

	public static int getRandomIndex(int[] probabilities) {
		int up = sumIntArray(probabilities);
		int random = MathUtils.random(1, up, true, true);
		int[] realPros = new int[probabilities.length + 1];
		for (int k = 0; k < realPros.length; k++) {
			int ks = 0;
			for (int j = 0; j < k; j++) {
				ks += probabilities[j];
			}
			realPros[k] = ks;
		}
		for (int i = 0; i < probabilities.length; i++) {
			if (random >= realPros[i] && random <= realPros[i + 1])
				return i;
		}
		return -1;
	}

	public static int sumIntArray(int[] values) {
		int value = 0;
		for (int i = 0; i < values.length; i++) {
			value += values[i];
		}
		return value;
	}

	public static int indexOf(int value, List<Integer> values) {
		int length = values.size();
		for (int i = 0; i < length; i++) {
			if (values.get(i) == value)
				return i;
		}
		return -1;
	}

	// shuffle an array

	public static void shuffle(int[] a) {
		shuffle(a, a.length);
	}

	public static void shuffle(int[] a, int count) {
		for (int i = 0; i < count; i++) {
			int change = i + getRandomInt(a.length - i);
			int temp = a[i];
			a[i] = a[change];
			a[change] = temp;
		}
	}

	public static void shuffle(List<Integer> a) {
		int count = a.size();
		for (int i = 0; i < count; i++) {
			int change = i + getRandomInt(count - i);
			int temp = a.get(i);
			a.set(i, a.get(change));
			a.set(change, temp);
		}
	}

	public static final int compare(int a, int b) {
		return a > b ? 1 : (a == b ? 0 : -1);
	}

	public static final void confuseArray(int[] list) {
		int end = list.length - 1;
		for (int i = 0; i < end; i++) {
			int index = random(i, end, false, true);
			int tmp = list[index];
			list[index] = list[i];
			list[i] = tmp;
		}
	}

	public static final void formatLeft(int[] arr) {
		int result[] = new int[arr.length];
		int index = 0;
		for (int i = 0; i < arr.length; i++) {
			if (arr[i] > 0)
				result[index++] = arr[i];
		}
		System.arraycopy(result, 0, arr, 0, arr.length);
	}

	public static final void arrayClear(int[] arr) {
		for (int i = 0; i < arr.length; i++) {
			arr[i] = 0;
		}
	}

	public static final void arrayClear(long[] arr) {
		for (int i = 0; i < arr.length; i++) {
			arr[i] = 0;
		}
	}

	/**
	 * �ݶ�ֻ�� yyyy-MM-dd ��ʽ
	 * 
	 * @param timeStr
	 */
	public static long parseTime(String timeStr) {
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
		Date date = null;
		long time = 0;
		try {
			date = sf.parse(timeStr);
			time = date.getTime();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			LogUtil.error(e);
		}
		return time;
	}

	public static String parseStr(String s, int fontSize, String color) {
		return "<font family='SimSun' color='" + color + "' size='" + fontSize + "'>" + s + "</font>";
	}

	/**
	 * time对应的当天凌晨的时间戳
	 * 
	 * @param time
	 * @return
	 */
	public static long getMorning(long time) {
		Calendar c = Calendar.getInstance();
		c.setTime(new Date(time));
		c.set(Calendar.HOUR_OF_DAY, 0);
		c.set(Calendar.MINUTE, 0);
		c.set(Calendar.SECOND, 0);
		c.set(Calendar.MILLISECOND, 0);
		return c.getTimeInMillis();
		// return (time / dayTime) * dayTime;
	}

	/**
	 * time对应的当月第一天凌晨的时间戳
	 * 
	 * @param time
	 * @return
	 */
	public static long getFirstDayMorningOfMonth(long time) {
		Calendar c = Calendar.getInstance();
		c.setTime(new Date(time));
		c.set(Calendar.DAY_OF_MONTH, 1);
		c.set(Calendar.HOUR_OF_DAY, 0);
		c.set(Calendar.MINUTE, 0);
		c.set(Calendar.SECOND, 0);
		c.set(Calendar.MILLISECOND, 0);
		return c.getTimeInMillis();
	}

	public static void sleep(int second) {
		try {
			Thread.sleep(second * 1000);
		} catch (Exception e) {
		}
	}

	public static void sleepMillis(int millis) {
		try {
			Thread.sleep(millis);
		} catch (Exception e) {
		}
	}

	public static void sleepNano(int nanos) {
		try {
			Thread.sleep(0, nanos);
		} catch (Exception e) {
		}
	}

	public static int setBit(int origin, int index) {
		return origin | (1 << index);
	}

	public static int clearBit(int origin, int index) {
		return origin & (~(1 << index));
	}

	public static int getBit(int origin, int index) {
		return (origin >> index) & 1;
	}

	// public static Image getImage(String path){
	// Toolkit kit = Toolkit.getDefaultToolkit();
	// return kit.getImage(new File(Config.configRoot,path).getAbsolutePath());
	// }

	public static Image getImage(File f) {
		try {
			return ImageIO.read(f);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static int parseEffectType(String attribute, String[] passiveEffectName) {
		for (int i = passiveEffectName.length - 1; i >= 0; i--) {
			if (passiveEffectName[i].equalsIgnoreCase(attribute)) {
				return i;
			}
		}
		return -1;
	}

	public static int[] parseEffects(String attr, String[] EffectNames) {
		String[] ats = attr.split("#");
		int[] result = new int[ats.length];
		for (int i = 0; i < ats.length; i++) {
			String[] def = ats[i].split(":");
			result[i] = parseEffectType(def[0], EffectNames) | (Utility.parseInt(def[1]) << 16);
		}
		return result;
	}

	public static int parseEffect(String str) {
		if (str == null || str.length() == 0)
			return 0;
		String[] vs = str.split(":");
		int eff = Utility.parseInt(vs[0]);
		int value = Utility.parseInt(vs[1]);
		value = value < 0 ? ((-value) & 0x8000) : value;
		return eff | value << 16;
	}

	public static boolean isSame(byte[] b1, byte[] b2) {
		if (b1.length != b2.length)
			return false;
		for (int i = b1.length - 1; i > -1; i--)
			if (b1[i] != b2[i])
				return false;
		return true;
	}

	public static int sumArray(int[] ratios) {
		int sum = 0;
		for (int i : ratios) {
			sum += i;
		}
		return sum;
	}

	public static long sumArray(long[] ratios) {
		long sum = 0;
		for (long i : ratios) {
			sum += i;
		}
		return sum;
	}

	/** 32位md5加密 */
	public static String md5_32(String str) {
		MessageDigest messageDigest = null;

		try {
			messageDigest = MessageDigest.getInstance("MD5");
			messageDigest.reset();
			messageDigest.update(str.getBytes("UTF-8"));
		} catch (NoSuchAlgorithmException e) {
			System.out.println("NoSuchAlgorithmException caught!");
			System.exit(-1);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}

		byte[] byteArray = messageDigest.digest();

		StringBuffer md5StrBuff = new StringBuffer();
		for (int i = 0; i < byteArray.length; i++) {
			if (Integer.toHexString(0xFF & byteArray[i]).length() == 1)
				md5StrBuff.append("0").append(Integer.toHexString(0xFF & byteArray[i]));
			else
				md5StrBuff.append(Integer.toHexString(0xFF & byteArray[i]));
		}
		return md5StrBuff.toString();

	}

	/** 16位md5加密 */
	public static String md5_16(String str) {
		return md5_32(str).substring(8, 24);
	}

	public static String base64Encoder(String str) {
		try {
			if (str == null)
				return null;
			byte[] src = str.getBytes("utf-8");
			return Base64.getEncoder().encodeToString(src);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static String base64Decoder(String str) {
		if (str == null)
			return null;
		byte[] b = Base64.getDecoder().decode(str);
		try {
			return new String(b, "utf-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 判断字符串长度 中英文混合长度 中文=2 英文=1
	 * 
	 * @param value
	 * @return
	 */
	public static int stringLength(String value) {
		String regular = "[\u4e00-\u9fa5]";// 中文正则式
		int length = 0;
		for (int i = 0; i < value.length(); i++) {
			String temp = value.substring(i, i + 1);
			if (temp.matches(regular)) {
				length += 2;
			} else {
				length += 1;
			}
		}
		return length;
	}

	public static int parseRange(String def) {
		if (def == null)
			return 0;
		if (def.indexOf('-') > 0) {
			String[] vs = def.split("-");
			int low = Utility.parseInt(vs[0]);
			int big = Utility.parseInt(vs[1]);
			return (big << 16) | low;
		} else {
			return Utility.parseInt(def);
		}
	}

	public static RateList parseRateList(String s, String split1, String split2) {
		if (s == null || s.length() < 1)
			return null;
		String[] ss = s.split(split1);
		int[] ids = new int[ss.length];
		int[] rates = new int[ss.length];
		for (int i = 0; i < ss.length; i++) {
			String[] v = ss[i].split(split2);
			ids[i] = parseInt(v[0]);
			rates[i] = parseInt(v[1]);
		}
		return new RateList(ids, rates);
	}

	public static boolean equals(byte[] a1, byte[] a2) {
		if (a1 == null || a2 == null || a1.length != a2.length)
			return false;
		for (int i = 0; i < a1.length; i++)
			if (a1[i] != a2[i])
				return false;
		return true;
	}

	private static MessageDigest md5;

	public static byte[] md5(byte[] source) {
		if (md5 == null) {

			try {
				md5 = MessageDigest.getInstance("md5");
			} catch (NoSuchAlgorithmException e) {
				e.printStackTrace();
			}
		}
		return md5.digest(source);

	}

	/**
	 * 得到指定长度的随机byte数组
	 * 
	 * @param len
	 * @return
	 */
	public static byte[] randomBytes(int len) {
		byte[] result = new byte[len];
		r.nextBytes(result);
		return result;
	}

	public static byte[] randomBattleBytes(int minLen, int maxLen) {
		int length = r.nextInt(maxLen - minLen) + minLen;
		byte[] result = new byte[length];
		r.nextBytes(result);
		for (int i = 0; i < result.length; i++) {
			if (result[i] < 0)
				result[i] = (byte) (-result[i]);
		}
		return result;
	}

	public static void clearIntArray(int[] a) {
		for (int i = 0; i < a.length; i++) {
			a[i] = 0;
		}
	}

	public static void clearFloatArray(float[] a) {
		for (int i = 0; i < a.length; i++) {
			a[i] = 0;
		}
	}

	public static void clearArray(long[] arr) {
		for (int i = 0; i < arr.length; i++) {
			arr[i] = 0;
		}
	}

	public static int getDayOfWeek() {
		Calendar c = Calendar.getInstance();
		return c.get(Calendar.DAY_OF_WEEK);
	}

	public static int[] expandArray(int[] old, int delta) {
		int[] tmp = new int[old.length + delta];
		System.arraycopy(old, 0, tmp, 0, old.length);
		return tmp;
	}

	public static float[] expandArray(float[] old, int delta) {
		float[] tmp = new float[old.length + delta];
		System.arraycopy(old, 0, tmp, 0, old.length);
		return tmp;
	}

	public static <T> ArrayList<T> initList(Class<?> T, int count) {
		ArrayList<T> list = new ArrayList<>();
		for (int i = 0; i < count; i++) {
			list.add(null);
		}
		return list;
	}

	public static void swap(int[] vs, int index1, int index2) {
		int v = vs[index1];
		vs[index1] = vs[index2];
		vs[index2] = v;
	}

	public static long[] longListToArray(List<Long> list) {
		if (list == null || list.size() == 0)
			return new long[0];
		long[] value = new long[list.size()];
		for (int i = 0; i < value.length; i++) {
			value[i] = list.get(i);
		}
		return value;
	}

	public static byte[] createByteArray(int len, byte defaultValue) {
		byte[] result = new byte[len];
		for (int i = 0; i < len; i++) {
			result[i] = defaultValue;
		}
		return result;
	}

	public static void fillArray(long[] source, long value) {
		for (int i = 0; i < source.length; i++) {
			source[i] = value;
		}
	}

	public static void fillArray(byte[] source, byte value) {
		for (int i = 0; i < source.length; i++) {
			source[i] = value;
		}
	}

	public static long[] createArray(int len, long defaultValue) {
		long[] result = new long[len];
		for (int i = 0; i < len; i++) {
			result[i] = defaultValue;
		}
		return result;
	}

	public static int[] createArray(int len, int defaultValue) {
		int[] result = new int[len];
		for (int i = 0; i < len; i++) {
			result[i] = defaultValue;
		}
		return result;
	}

	public static int[] remIdCountArray(int[] source, int id, int count) {
		if (source == null)
			return null;
		int len = source.length;
		for (int i = 0; i < len; i += 2) {
			if (source[i] == id) {
				source[i + 1] -= count;
				if (source[i + 1] <= 0) {// 没有数值了
					return ArrayUtils.remove(ArrayUtils.remove(source, i), i);
				} else {
					return source;
				}
			}
		}
		return source;
	}

	public static int[] addIdCountArray(int[] source, int id, int count) {
		if (source == null) {
			return new int[] { id, count };
		}
		int len = source.length;
		for (int i = 0; i < len; i += 2) {
			if (source[i] == id) {
				source[i + 1] += count;
				return source;
			}
		}
		return ArrayUtils.addAll(source, new int[] { id, count });
	}

	public static int getIdCountArray(int[] source, int id) {
		if (source == null)
			return 0;
		int len = source.length;
		for (int i = 0; i < len; i += 2) {
			if (source[i] == id) {
				return source[i + 1];
			}
		}
		return 0;
	}

	public static boolean hasSameValue(long[] values) {
		for (int i = 0; i < values.length; i++) {
			for (int j = i + 1; j < values.length; j++) {
				if (values[i] == values[j])
					return true;
			}
		}
		return false;
	}

	public static boolean hasSameValue(int[] values) {
		for (int i = 0; i < values.length; i++) {
			for (int j = i + 1; j < values.length; j++) {
				if (values[i] == values[j])
					return true;
			}
		}
		return false;
	}

	public static void clearByteArray(byte[] arr) {
		for (int i = 0; i < arr.length; i++) {
			arr[i] = 0;
		}
	}

	/**
	 * 取数组中的指定值，如果是无效位置，则返回默认值0。 如果位置超出数组长度，返回数组的最后一个值
	 * 
	 * @param list
	 * @param index
	 * @return
	 */
	public static int getValueFromArray(int[] list, int index) {
		return getValueFromArray(list, index, 0);
	}

	/**
	 * 取数组中的指定值，如果是无效位置，则返回默认值。 如果位置超出数组长度，返回数组的最后一个值
	 * 
	 * @param list
	 * @param index
	 * @param defaultValue
	 * @return
	 */
	public static int getValueFromArray(int[] list, int index, int defaultValue) {
		if (index < 0 || list == null || list.length == 0)
			return defaultValue;
		if (list.length <= index)
			return list[list.length - 1];
		return list[index];
	}

	/**
	 * 取数组中的指定值，如果是无效位置，则返回默认值0。 如果位置超出数组长度，返回数组的最后一个值
	 * 
	 * @param list
	 * @param index
	 * @return
	 */
	public static float getValueFromArray(float[] list, int index) {
		return getValueFromArray(list, index, 0);
	}

	/**
	 * 取数组中的指定值，如果是无效位置，则返回默认值。 如果位置超出数组长度，返回数组的最后一个值
	 * 
	 * @param list
	 * @param index
	 * @param defaultValue
	 * @return
	 */
	public static float getValueFromArray(float[] list, int index, float defaultValue) {
		if (index < 0 || list == null || list.length == 0)
			return defaultValue;
		if (list.length <= index)
			return list[list.length - 1];
		return list[index];
	}

	/**
	 * 加密
	 * 
	 * @param 要加密的内容
	 * @param 加密的密码
	 * @return
	 */

	public static byte[] encrypt(String content, String password) {
		try {
			KeyGenerator kgen = KeyGenerator.getInstance("AES");
			kgen.init(128, new SecureRandom(password.getBytes()));
			SecretKey secretKey = kgen.generateKey();
			byte[] enCodeFormat = secretKey.getEncoded();
			SecretKeySpec key = new SecretKeySpec(enCodeFormat, "AES");
			Cipher cipher = Cipher.getInstance("AES");// 创建密码器
			byte[] byteContent = content.getBytes("utf-8");
			cipher.init(Cipher.ENCRYPT_MODE, key);// 初始化
			byte[] result = cipher.doFinal(byteContent);
			return result; // 加密
		} catch (Exception e) {
			LogUtil.debug(e);
		}
		return null;
	}

	/**
	 * 解密
	 * 
	 * @param content
	 * @param password
	 * @return
	 */
	public static byte[] decrypt(byte[] content, String password) {
		try {
			KeyGenerator kgen = KeyGenerator.getInstance("AES");
			kgen.init(128, new SecureRandom(password.getBytes()));
			SecretKey secretKey = kgen.generateKey();
			byte[] enCodeFormat = secretKey.getEncoded();
			SecretKeySpec key = new SecretKeySpec(enCodeFormat, "AES");
			Cipher cipher = Cipher.getInstance("AES");// 创建密码器
			cipher.init(Cipher.DECRYPT_MODE, key);// 初始化
			byte[] result = cipher.doFinal(content);
			return result; // 加密
		} catch (Exception e) {
			LogUtil.debug(e);
		}
		return null;
	}

	/**
	 * 将二进制转换成16进制
	 * 
	 * @param buf
	 * @return
	 */
	public static String parseByte2HexStr(byte buf[]) {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < buf.length; i++) {
			String hex = Integer.toHexString(buf[i] & 0xFF);
			if (hex.length() == 1) {
				hex = '0' + hex;
			}
			sb.append(hex.toUpperCase());
		}
		return sb.toString();
	}

	/**
	 * 将16进制转换为二进制
	 * 
	 * @param hexStr
	 * @return
	 */
	public static byte[] parseHexStr2Byte(String hexStr) {
		if (hexStr.length() < 1)
			return null;
		byte[] result = new byte[hexStr.length() / 2];
		for (int i = 0; i < hexStr.length() / 2; i++) {
			int high = Integer.parseInt(hexStr.substring(i * 2, i * 2 + 1), 16);
			int low = Integer.parseInt(hexStr.substring(i * 2 + 1, i * 2 + 2), 16);
			result[i] = (byte) (high * 16 + low);
		}
		return result;
	}

	public static String encryptstr(String content, String password) {
		return parseByte2HexStr(encrypt(content, password));
	}

	public static String decryptstr(String content, String password) {
		return new String(decrypt(parseHexStr2Byte(content), password));
	}

	public static void main(String[] aa) {
		String password = "cxz7qa791";
		System.out.println("请输入要加密的字符串,输入exit退出：");
		Scanner sc = new Scanner(System.in);
		String inputstring = sc.nextLine();
		while (!inputstring.equals("exit")) {
			String encryptstring = encryptstr(inputstring, password);
			System.out.println("加密后：" + encryptstring);
			String decryptstring = decryptstr(encryptstring, password);
			System.out.println("解密后:" + decryptstring );
			if (decryptstring.equals(inputstring)) {
				System.err.println("相符！");
			}
			else{
				System.err.println("不相符！");
			}
			System.out.println();
			System.out.println("请输入要加密的字符串,输入exit退出：");
			inputstring = sc.nextLine();
		}
		sc.close();

	}
}
