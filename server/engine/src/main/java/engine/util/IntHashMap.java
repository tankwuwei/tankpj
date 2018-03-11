package engine.util;

/**
 * 自定义的IntHashMap, 目前默认值是-1
 * @author xjf
 *
 */
public class IntHashMap {
	private static int DEFAULT = 0;
	
    private final static class Entry {
        int key;
        int value;
        Entry next;
         
        Entry(int key, int value, Entry next) {
            this.key= key;
            this.value= value;
            this.next= next;
        }
    }
 
    private final static int LOAD_PERCENT_FACTOR= 75;
 
    private Entry[] _table;
    private int _count;
    private int _threshold;
    
    private int defaultValue;
 
    public IntHashMap() {
        this(20);
    }
 
    public IntHashMap(int initialCapacity) {
        this(initialCapacity, DEFAULT);
    }
    
    public IntHashMap(int initialCapacity, int defaultValue){
    	if (initialCapacity < 0) {
            throw new IllegalArgumentException("Illegal Capacity: " + initialCapacity);
        }
 
        if (initialCapacity == 0) {
            initialCapacity= 1;
        }
         
        _table= new Entry[initialCapacity];
        _threshold= (initialCapacity * LOAD_PERCENT_FACTOR) / 100;
        this.defaultValue = defaultValue;
    }
 
    public void clear() {
        Entry[] tab= _table;
        for(int i= 0; i < tab.length; ++i) {
            tab[i]= null;
        }
         
        _count= 0;
    }
 
    public boolean containsKey(int key) {
        return getEntry(key) != null;
    }
 
    public boolean containsValue(int value) {
        Entry[] tab= _table;
        for (int i= 0; i < tab.length; i++) {
            for (Entry e= tab[i]; e != null; e= e.next) {
                if (value == e.value) {
                    return true;
                }
            }
        }
        return false;
    }
     
    public int[] elements() {
        int[] result= new int[_count];
        Entry entry= null;
        for(int i= 0, e= 0; i < _count; ++i) {
            while(entry == null) {
                entry= _table[e++];
            }
             
            result[i]= entry.value;
            entry= entry.next;
        }
        return result;
    }
 
    public int get(int key) {
        Entry e= getEntry(key);
        return e != null ? e.value : defaultValue;
    }
 
    public boolean isEmpty() {
        return _count == 0;
    }
     
    public int[] keys() {
        int[] result= new int[_count];
        Entry entry= null;
        for(int i= 0, e= 0; i < _count; ++i) {
            while(entry == null) {
                entry= _table[e++];
            }
             
            result[i]= entry.key;
            entry= entry.next;
        }
        return result;
    }
 
    public int put(int key, int value) {
        Entry tab[] = _table;
        int index = (key & 0x7FFFFFFF) % tab.length;
        for (Entry e = tab[index] ; e != null ; e = e.next) {
            if (e.key == key) {
                int old = e.value;
                e.value = value;
                return old;
            }
        }
 
        Entry e= _table[index];
        _table[index]= new Entry(key, value, e);
        if(_count++ >= _threshold) {
            rehash();
        }
        return defaultValue;
    }
    /**
     * 增加
     * @param key
     * @param value
     */
    public void inc(int key, int value){
    	if(containsKey(key)){
    		put(key, value+get(key));
    	}else{
    		put(key, value);
    	}
    }
    
    public void putAll(IntHashMap src){
    	for (int key:src.keys()){
    		put(key, src.get(key));
    	}
    }
 
    public int remove(int key) {
        Entry tab[] = _table;
        int hash = key;
        int index = (hash & 0x7FFFFFFF) % tab.length;
        for (Entry e = tab[index], prev = null ; e != null ; prev = e, e = e.next) {
            if (e.key == key) {
                if (prev != null) {
                    prev.next = e.next;
                } else {
                    tab[index] = e.next;
                }
                _count--;
                return e.value;
            }
        }
        return defaultValue;
    }
 
    public int size() {
        return _count;
    }
     
    private Entry getEntry(int key) {
        Entry[] tab= _table;
        int index= (key & 0x7FFFFFFF) % tab.length;
        for(Entry e= tab[index]; e != null; e= e.next) {
            if(e.key == key) {
                return e;
            }
        }
         
        return null;
    }
     
    private void rehash() {
        int oldCapacity = _table.length;
        Entry oldTable[] = _table;
 
        int newCapacity = oldCapacity * 2 + 1;
        Entry newTable[] = new Entry[newCapacity];
 
        _threshold = ((newCapacity * LOAD_PERCENT_FACTOR) / 100);
        _table = newTable;
 
        for (int i = oldCapacity ; i-- > 0 ;) {
            for (Entry old = oldTable[i] ; old != null ; ) {
                Entry e = old;
                old = old.next;
 
                int index = (e.key & 0x7FFFFFFF) % newCapacity;
                e.next = newTable[index];
                newTable[index] = e;
            }
        }
    }
}
