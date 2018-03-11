package engine.util;

import java.util.Random;

/**
 * 绝对概率表，确保每个物品的掉落概率绝对按照rate的定义来，也就是1%的掉落就是100个内一定掉1个，没有随机概率
 * @author xjf
 */
public class RateList {
	private static Random r = new Random(System.currentTimeMillis());

	int value;
	int[] list;
	int[] origin;
	int[] indexList;
	int total;
	int count;
	boolean force;

	/**
	 * 初始化一个概率队列
	 * @param indexList 被概率的目标列表
	 * @param rateList 给定的概率列表
	 */
	public RateList(int[] indexList, int[] rateList) {
		origin = rateList.clone();
		this.indexList = indexList;
		reset();
	}

	public RateList(int v) {
		value = v;
	}

	/**
	 * 强制最后一个物品的概率在最后出，也就是最后一个物品的概率必须是1
	 * @param indexList
	 * @param rateList
	 * @param forceLast
	 */
	public RateList(int[] indexList, int[] rateList, boolean forceLast) {
		this(indexList, rateList);
		force = true;
	}

	public int getRandomIndex() {
		if (indexList == null)
			return value; // 固定模式
		synchronized (this) {
			if (count <= 0) {
				reset();
			}
			int p = r.nextInt(count) + 1;
			if (force && p == count) {
				if (count == 1) {
					count--;
					return indexList[indexList.length - 1];
				} else {
					p--;
				}
			}
			for (int i = 0; i < list.length; i++) {
				if (p > list[i]) {
					p -= list[i];
				} else {
					list[i]--;
					count--;
					return indexList[i];
				}
			}
			// 不应该走这里
			// LogUtil.info("Ratelist invalid data ");
			System.out.println("Ratelist invalid data ");
			reset();
			return getRandomIndex();
		}
	}

	/**
	 * 返回真随机的结果
	 * @return
	 */
	public int getAbsoluteRandomIndex() {
		int p = r.nextInt(total);
		for (int i = 0; i < origin.length; i++) {
			if (p > origin[i]) {
				p -= origin[i];
			} else {
				return indexList[i];
			}
		}
		throw new IllegalArgumentException("RateList can not get a absolute random value");
	}

	private void reset() {
		list = origin.clone();
		count = 0;
		for (int i = 0; i < list.length; i++) {
			count += list[i];
		}
		total = count;
	}

	/**
	 * 解析一个RateList的定义
	 * @param define
	 * @return
	 */
	public static RateList parse(String define) {
		if (define == null || define.length() == 0)
			return null;
		if (define.indexOf(",") < 0) {
			// 一条掉落数据
			return new RateList(Utility.parseInt(define.trim()));
		} else {
			return Utility.parseRateList(define, ",", ":");
		}
	}
}
