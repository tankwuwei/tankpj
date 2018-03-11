package com.ft.gameserver.common;

import java.math.BigDecimal;

/**
 * 资源配置的基类
 * 
 * @author cxz
 *
 */
public class Base {
	public static final int maxPower = 100;
	public int id;

	public int getId() {
		return id;
	}

	public void parse(engine.doc.Element e) {
	}

	/**
	 * 得到转换后的值
	 * 
	 * @param value
	 * @return
	 */
//	public static double toRealValue(double v) {
//		double value = v;
//		int power = ((int) value) / CommonCons.PowerBase;
//		value = value % CommonCons.PowerBase;
//		if (power > maxPower) {
//			System.out.println("Power Out of Range! value = " + v);
//			return 0.0;
//		}
//		value *= Math.pow(1000, power) / 100d;
//		return myround(value);
//	}

	/**
	 * 取DOUBLE值的四舍五入取整
	 * 
	 * @param num
	 * @return
	 */
	public static double myround(double num) {
		BigDecimal b = new BigDecimal(num);
		num = b.setScale(0, BigDecimal.ROUND_HALF_UP).doubleValue();
		return num;
	}

	/**
	 * 取DOUBLE值的向上取整
	 * 
	 * @param num
	 * @return
	 */
	public static double myroundup(double num) {
		BigDecimal b = new BigDecimal(num);
		num = b.setScale(0, BigDecimal.ROUND_UP).doubleValue();
		return num;
	}

	/**
	 * 转换为DOUBLE双下标
	 * 
	 * @param s
	 * @param limit1
	 * @param limit2
	 * @return
	 */
	public static double[][] parseDoubleArrayTwo(String s, String limit1, String limit2) {
		if (s == null)
			return new double[0][0];
		s = s.trim();
		if (s.length() == 0)
			return new double[0][0];
		String ss[] = s.split(limit1);
		double[][] result = new double[ss.length][];
		for (int i = 0; i < ss.length; i++) {
			String s2[] = ss[i].split(limit2);
			double[] r2 = new double[s2.length];
			for (int j = 0; j < s2.length; j++) {
				r2[j] = Double.parseDouble(s2[j]);
			}
			result[i] = r2;
		}
		return result;
	}
}
