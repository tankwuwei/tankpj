package com.ft.csv.resources;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringExt {

	public static boolean isNumeric(String str) {
		Pattern pattern = Pattern.compile("[0-9]*");
		Matcher isNum = pattern.matcher(str);
		if (!isNum.matches()) {
			return false;
		}
		return true;
	}

	public static int getInt(String s) {
		return s == null || s.isEmpty() ? 0 : Integer.parseInt(s);
	}

	public static float getFloat(String s) {
		return s == null || s.isEmpty() ? 0 : Float.parseFloat(s);
	}

	public static short getShort(String s) {
		return s == null || s.isEmpty() ? 0 : Short.parseShort(s);
	}

	public static void getIntArray(List<Integer> a, String str) {
		if (a == null || str.isEmpty()) {
			return;
		}
		String[] strings = str.split("\\|");
		for (String s : strings) {
			a.add(Integer.parseInt(s));
		}
	}

	public static void getLongArray(List<Long> a, String str) {
		if (a == null || str.isEmpty()) {
			return;
		}
		String[] strings = str.split("\\|");
		for (String s : strings) {
			a.add(Long.parseLong(s));
		}
	}

	public static <T> String makeString(List<T> a) {
		String str = "";
		if (a == null || a.isEmpty()) {
			return str;
		}
		for (T i : a) {
			str += i + "|";
		}
		return str;
	}

	public static void getIntArray(Map<Integer, Integer> a, String str) {
		if (a == null || str.isEmpty()) {
			return;
		}
		String[] strings = str.split("\\|");
		String key = "";
		for (String s : strings) {
			if (key.isEmpty()) {
				key = s;
			} else {
				a.put(Integer.parseInt(key), Integer.parseInt(s));
				key = "";
			}
		}
	}

	public static String makeString(Map<Integer, Integer> a) {
		String str = "";
		Iterator<Entry<Integer, Integer>> iterator = a.entrySet().iterator();
		while (iterator.hasNext()) {
			Entry entry = iterator.next();
			str += entry.getKey() + "|";
			str += entry.getValue() + "|";
		}
		return str;
	}

	public static void getIntArray(Set<Integer> a, String str) {
		if (a == null || str.isEmpty()) {
			return;
		}
		String[] strings = str.split("\\|");
		for (String s : strings) {
			a.add(Integer.parseInt(s));
		}
	}

	public static void getFloatArray(List<Float> a, String str) {
		if (str.isEmpty()) {
			return;
		}
		String[] strings = str.split("\\|");
		for (String s : strings) {
			a.add(Float.parseFloat(s));
		}

	}

}
