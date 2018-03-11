package engine.util.lottery;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

class Result {
	private int index;
	private int sumTime;
	private int time;
	private double probability;

	public Result(int index, int sumTime, int time, double probability) {
		this.index = index;
		this.sumTime = sumTime;
		this.time = time;
		this.probability = probability;

	}

	public String toString() {
		return "索引值：" + index + "，抽奖总数：" + sumTime + "，抽中次数：" + time + "，概率：" + probability + "，实际概率：" + (double) time / sumTime;
	}
}

public class TestLottery {

	public static void f() {
		// 构造概率集合
		List<Double> list = new ArrayList<Double>();
		list.add(200d);
		list.add(800d);
		list.add(500d);
		list.add(300d);
		list.add(200d);

		List<Integer> r = LotteryUtil.lottery(list, TIME);
		double sumProbability = LotteryUtil.getMaxElement();
		Map<Integer, Integer> map = new HashMap<Integer, Integer>();
		for (int index : r) {
			if (map.containsKey(index)) {
				map.put(index, map.get(index) + 1);
			} else {
				map.put(index, 1);
			}
		}

		for (int i = 0; i < list.size(); i++) {
			double probability = list.get(i) / sumProbability;
			list.set(i, probability);
		}

		iteratorMap(map, list);
	}

	public static void iteratorMap(Map<Integer, Integer> map, List<Double> list) {
		for (Entry<Integer, Integer> entry : map.entrySet()) {
			int index = entry.getKey();
			int time = entry.getValue();
			Result result = new Result(index, TIME, time, list.get(index));
			System.out.println(result);
		}
	}

	public static void ff() {
		// 构造概率集合
		List<Double> list = new ArrayList<Double>();
		list.add(20d);
		list.add(80d);
		list.add(50d);
		list.add(300d);

		System.out.println(LotteryUtil.lottery(list, 1));
		System.out.println(LotteryUtil.lottery(list, 2));
		System.out.println(LotteryUtil.lottery(list, 10));

	}

	public static void main(String[] args) {
		f();
		System.out.println("---------------");
		ff();
	}

	static final int TIME = 1000000;// 100万
}