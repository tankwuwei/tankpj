package engine.util;
/**
 * 一个id池，用于生成id，回收不用的id，如果不回收，那么就有爆的可能
 * @author cxz
 *
 */
public class IdPool {
	/**无效的id，只有溢出的时候会产生*/
	public static final int INVALID_ID = -1; 
	
	int max;
	int currentMax;
	IntArray collectList;
	
	public IdPool(int max){
		this.max = max;
		currentMax = 1;
		collectList = new IntArray();
	}
	
	public synchronized int obtain(){
		if(collectList.size() > 0)return collectList.removeIndex(collectList.size()-1);
		if(currentMax >= max){
			//爆了 
			throw new IllegalStateException("id创建爆仓 max=" + max);
			//return INVALID_ID;
		}
		int result = currentMax;
		currentMax++;
		return result;
	}
	
	public synchronized void free(int v){
		collectList.add(v);
	}
}
