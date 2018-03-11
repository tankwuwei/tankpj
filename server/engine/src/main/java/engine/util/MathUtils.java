package engine.util;

import java.math.BigDecimal;

/**
 * 数学工具类
 * @author qu.yy
 */
public class MathUtils {
	public static java.util.Random r = new java.util.Random(System.currentTimeMillis());

	public static float NANO = 1000000.0f;
	static double[] SQRT_TABLE = new double[65536];

	private static double[] SIN = new double[360];
	private static double[] COS = new double[360];

	static {
		init();
	}

	public static void init() {
		initMath();
	}

	private static void initMath() {

		for (int i = 1; i < SQRT_TABLE.length; i++) {
			SQRT_TABLE[i] = Math.sqrt(i);
		}
		// ROOT = Utility.class.getResource("/").getPath();
		// ROOT = ROOT.substring("/".length());

		for (int i = 0; i < 360; i++) {
			SIN[i] = Math.sin(i * Math.PI / 180);
			COS[i] = Math.cos(i * Math.PI / 180);
		}

	}

	public static final double sin(int arc) {
		return SIN[arc % 360];
	}

	public static final double cos(int arc) {
		return COS[arc % 360];
	}

	public static double sqrt(int value) {
		return value < 0xFFFF ? SQRT_TABLE[value] : Math.sqrt(value);
	}

	/**
	 * 提供精确的小数位四舍五入处理。
	 * @param value 需要四舍五入的数字
	 * @param scale 小数点后保留几位
	 * @return {@link Double} 四舍五入后的结果
	 */
	public static double round(double value, int scale) {
		BigDecimal b = new BigDecimal(Double.toString(value));
		BigDecimal one = new BigDecimal("1");
		return b.divide(one, scale, BigDecimal.ROUND_HALF_UP).doubleValue();
	}

	/**
	 * 提供精确的小数位向下取整处理。
	 * @param value 需要向下取证的数字
	 * @param scale 精度(小数点后保留几位)
	 * @return {@link Double} 向下取证后的数值
	 */
	public static double roundDown(double v, int scale) {
		BigDecimal b = new BigDecimal(Double.toString(v));
		BigDecimal one = new BigDecimal("1");
		return b.divide(one, scale, BigDecimal.ROUND_DOWN).doubleValue();
	}

	/**
	 * 提供精确的小数位向上取整处理。
	 * @param value 需要向上取整的数字
	 * @param scale 精度(小数点后保留几位)
	 * @return {@link Double} 向上取证后的数值
	 */
	public static double roundUp(double value, int scale) {
		BigDecimal b = new BigDecimal(Double.toString(value));
		BigDecimal one = new BigDecimal("1");
		return b.divide(one, scale, BigDecimal.ROUND_UP).doubleValue();
	}

	/**
	 * 提供精确相除向上取整方法
	 * @param value1 被除数
	 * @param value2 除数(不能为0)
	 * @param scale 精度(小数点后保留几位)
	 * @return {@link Double} 向上取证后的数值
	 */
	public static double divideAndRoundUp(double value1, double value2, int scale) {
		BigDecimal bd1 = new BigDecimal(value1);
		BigDecimal bd2 = new BigDecimal(value2);
		return bd1.divide(bd2, scale, BigDecimal.ROUND_UP).doubleValue();
	}

	/**
	 * 提供精确相除向下取整方法
	 * @param value1 被除数
	 * @param value2 除数(不能为0)
	 * @param scale 精度(小数点后保留几位)
	 * @return {@link Double} 向上取证后的数值
	 */
	public static double divideAndRoundDown(double value1, double value2, int scale) {
		BigDecimal bd1 = new BigDecimal(value1);
		BigDecimal bd2 = new BigDecimal(value2);
		return bd1.divide(bd2, scale, BigDecimal.ROUND_DOWN).doubleValue();
	}

	/**
	 * 汇总全部数值
	 * @param ns
	 * @return
	 */
	public static int sum(int... ns) {
		if (ns == null) {
			return 0;
		}
		int total = 0;
		for (int n : ns) {
			total += n;
		}
		return total;
	}

	/**
	 * 求log值
	 * @param base
	 * @param value
	 * @return
	 */
	public static double log(double base, double value) {
		return Math.log(value) / Math.log(base);
	}

	/**
	 * @param low
	 * @param up
	 * @param includeLow
	 * @param includeUp
	 * @return
	 */
	public static int random(int low, int up, boolean includeLow, boolean includeUp) {
		if (!includeUp) {
			up = up - 1;
			if (up < low) {
				up = low;
			}
		} else if (!includeLow) {
			low += 1;
			if (low > up) {
				low = up;
			}
		}
		int value = r.nextInt(up - low + 1) + low;
		return value;
	}

	public static int getRandomBetween(int min, int max) {
		if (min == max)
			return min;
		return r.nextInt(max - min + 1) + min;
	}

	public static int getRandomInt(int round) {
		if (round == 0) {
			return 0;
		}
		return r.nextInt(round);
	}

	public static int[] getRandomNoRepeat(int max, int min, int num) {
		if (max < min || num > (max - min + 1))
			return null;
		int[] result = createArray(num, min - 1);// 用非区间内的数值生成数组

		int index = 0;
		while (true) {
			int r = getRandomBetween(min, max);
			if (Utility.indexOf(r, result) == -1) {
				result[index++] = r;
			}
			if (index >= num)
				break;
		}
		return result;
	}

	public static long getRandomLong(long round) {
		if (round == 0)
			return 0;
		return r.nextLong() % round;
	}

	public static float getRandomFloat(float radius) {
		return r.nextFloat() * radius;
	}

	public static int[] createArray(int len, int defaultValue) {
		int[] result = new int[len];
		for (int i = 0; i < len; i++) {
			result[i] = defaultValue;
		}
		return result;
	}
}
