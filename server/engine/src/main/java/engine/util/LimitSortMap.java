package engine.util;

import java.lang.reflect.Array;
/**
 * 有限个数量的排序列表。
 * 使用时先创建一个指定容量的列表，然后向列表中加入数据，数据会被排序并保留下最多容量大小的量，超出的抛弃。
 * @author xjf
 *
 * @param <K>
 * @param <V>
 */
public class LimitSortMap<K extends Comparable<K>,V> {
	private static class Entry<K extends Comparable<K>,V>{
		V value;
		K key;
	}
	
	private Entry<K,V>[] data;
	
	private int capacity;
	
	@SuppressWarnings("unchecked")
	public LimitSortMap(int capacity){
		this.capacity = capacity;
		data = (Entry<K,V>[])new Entry[capacity];
	}
	
	@SuppressWarnings("unchecked")
	public void setCapacity(int capacity){
		this.capacity = capacity;
		data = (Entry<K,V>[])new Entry[capacity];
	}
	
	public void add(K key, V value){
		Entry<K,V> entry = null;
		for (int i = capacity-1; i>=0; i--){
			if(data[i]==null || data[i].key.compareTo(key)<0){
				if(entry == null){
					entry = new Entry<K,V>();
					entry.key = key;
					entry.value = value;
				}
				if(i< capacity-1){
					data[i+1] = data[i];
				}
				data[i] = entry;
			}
		}
	}
	/**
	 * 取排序后的列表，不包括null
	 * @param list
	 * @return
	 */
	public V[] getData(V[] list){
		int len = data.length;
		for (int i = 0; i< data.length; i++){
			if(data[i] == null){
				len = i+1;
				break;
			}
		}
		@SuppressWarnings("unchecked")
		V[] result = (V[])Array.newInstance(list.getClass().getComponentType(), len);
		for (int i = 0; i< len; i++){
			if(data[i] != null)result[i] = data[i].value;
		}
		return result;
	}
}
