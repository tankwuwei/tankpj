package engine.util;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class RandomRateList {
	
	private int maxPoint;
//	private int[] points;
//	private int[] indexes;
	private List<Integer> points = new ArrayList<>();
	private List<Integer> indexCopy = new ArrayList<>();
	
	public RandomRateList(){}
	
	public RandomRateList(int[] indexes, int[] rateList){
		reset(indexes, rateList);
	}
	
	public void reset(int[] indexes, int[] rateList){
		points.clear();
		indexCopy.clear();
		maxPoint = 0;
		for (int i = 0; i < rateList.length; i++) {
			addRate(indexes[i], rateList[i]);
		}
	}
	
	public void addRate(int index, int rate){
		maxPoint += rate;
		points.add(maxPoint);
		indexCopy.add(index);
	}
	
	/**
	 * 返回随机index in indexes
	 */
	public int random(){
		int random = Utility.getRandomBetween(1, maxPoint);
		for (int i = 0; i < points.size(); i++) {
			if(random<=points.get(i)) return indexCopy.get(i);
		}
		return -1;//理论上不存在
	}
	
	/**
	 * 随机出来limit个不重复的结果
	 * @param limit
	 * @return
	 */
	public int[] randomWithoutRepet(int limit){
		//TODO 写法待优化
		int[] r = new int[limit];
		Set<Integer> set = new HashSet<Integer>();
		while(true){
			set.add(random());	
			if(set.size()>=limit) break;
		}
		int index = 0;
		for (Integer i : set) {
			r[index++] = i;
		}
		return r;
	}
	
}
