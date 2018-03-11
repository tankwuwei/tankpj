package engine.util;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Vector;

public class VesselExt {

	public static final <T> boolean HasIt(List<T> obj, T it) {
		for (T t : obj) {
			if (t.equals(it)) {
				return true;
			}
		}
		return false;
	}

	public static final <T> boolean HasIt(Set<T> obj, T it) {
		for (T t : obj) {
			if (t.equals(it)) {
				return true;
			}
		}
		return false;
	}

	public static final <T> void Remove(List<T> obj, T it) {
		for (T t : obj) {
			if (t.equals(it)) {
				obj.remove(t);
				return;
			}
		}
	}

	public static final <K, V> void Remove(Map<K, V> obj, V value) {
		for (Map.Entry<K, V> entry : obj.entrySet()) {
			if (value == entry.getValue()) {
				obj.remove(entry.getKey(), entry.getValue());
				return;
			}
		}
	}

	public static final <K, V> void MultimapPut(Map<K, Vector<V>> obj, K key, V value) {
		Vector<V> l = obj.get(key);
		if (l == null) {
			l = new Vector<V>();
			obj.put(key, l);
		}
		l.add(value);
	}

	public static final <K, V> List<V> MultimapGet(Map<K, Vector<V>> obj, K key) {
		List<V> l = obj.get(key);
		return l;
	}

	public static final int getMax(Set<Integer> s) {
		int t = 0;
		for (int it : s) {
			if (it > t) {
				t = it;
			}
		}
		return t;
	}

}
