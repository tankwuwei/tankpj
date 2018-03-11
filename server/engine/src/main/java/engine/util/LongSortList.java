package engine.util;
/**
 * 排序的int数组。
 * 使用时用add方法来加值，如果这个值可以加入到队列，就加入，不能就忽略
 * 
 * @author xjf
 *
 */
public class LongSortList {
	long[] data; //排序数组，总是从大到小
	int capacity;
	
	public LongSortList(int capacity){
		setCapacity(capacity);
	}
	
	public void setCapacity(int c){
		this.capacity = c;
		data = new long[capacity];
	}
	/**
	 * 冒泡法，数组长度大则效率不高效率不高
	 * @param value
	 */
	public void add(long value){
		for (int i = capacity-1; i>=0; i--){
			if(value > data[i]){
				if(i< capacity-1){
					data[i+1] = data[i];
				}
				data[i] = value;
			}
		}
	}
	
	public void clear(){
		data = new long[capacity];
	}
	
	public long[] getData(){
		return data;
	}
}
