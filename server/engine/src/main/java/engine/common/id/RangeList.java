package engine.common.id;

import engine.util.Utility;

/**
 * 区段处理
 * @author xjf
 *
 */
public class RangeList {
	int[] ranges;//low,big对
	/**
	 * 创建一个区段集合，区段定义最好是顺序的且不相交的
	 * @param define
	 */
	public RangeList(String define){
		String[] list = define.split(",");
		ranges = new int[list.length*2];
		int index = 0;
		for (int i = 0; i< list.length; i++){
			String s = list[i].trim();
			if(s.indexOf('-')>0){
				String[] ss = s.split("-");
				ranges[index++] = Utility.parseInt(ss[0]);
				ranges[index++] = Utility.parseInt(ss[1]);
			}else{
				//单个值，没有区间
				ranges[index++] = Utility.parseInt(s);
				ranges[index++] = Utility.parseInt(s);
			}
		}
	}
	/**
	 * 取指定值所在区段，如果找不到则返回-1
	 * @param value
	 * @return
	 */
	public int getIndex(int value){
		for (int i = 0; i< ranges.length; i+=2){
			if(value>=ranges[i] && value <= ranges[i+1])return i/2;
		}
		return -1;
	}
}
