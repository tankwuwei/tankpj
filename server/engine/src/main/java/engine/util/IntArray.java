package engine.util;

import java.util.Arrays;


//copy and changed from ArrayList

public class IntArray {
	
	private transient int[] elementData;
	private transient int size;
	protected transient int modCount = 0;

	public IntArray(int initialCapacity) {
		super();
		if (initialCapacity < 0)
			throw new IllegalArgumentException("Illegal Capacity: " + initialCapacity);
		this.elementData = new int[initialCapacity];
	}

	public IntArray() {
		this(10);
	}

	public IntArray(int[] c) {
		elementData = c;
		size = elementData.length;
	}

	
	public void trimToSize() {
		modCount++;
		int oldCapacity = elementData.length;
		if (size < oldCapacity) {
			elementData = Arrays.copyOf(elementData, size);
		}
	}

	public void ensureCapacity(int minCapacity) {
		modCount++;
		int oldCapacity = elementData.length;
		if (minCapacity > oldCapacity) {
			int newCapacity = (oldCapacity * 3) / 2 + 1;
			if (newCapacity < minCapacity)
				newCapacity = minCapacity;
			// minCapacity is usually close to size, so this is a win:
			elementData = Arrays.copyOf(elementData, newCapacity);
		}
	}

	public int size() {
		return size;
	}

	public boolean isEmpty() {
		return size == 0;
	}

	public boolean contains(int o) {
		return indexOf(o) >= 0;
	}

	public int indexOf(int o) {
		for (int i = 0; i < size; i++)
			if (o == elementData[i])
				return i;
		return -1;
	}

	public int lastIndexOf(int o) {
		for (int i = size - 1; i >= 0; i--)
			if (o == elementData[i])
				return i;
		return -1;
	}

	public Object clone() {
		try {
			IntArray v = (IntArray) super.clone();
			v.elementData = Arrays.copyOf(elementData, size);
			v.modCount = 0;
			return v;
		} catch (CloneNotSupportedException e) {
			// this shouldn't happen, since we are Cloneable
			throw new InternalError();
		}
	}

	public int[] toArray() {
		return Arrays.copyOf(elementData, size);
	}

	public int get(int index) {
		RangeCheck(index);

		return elementData[index];
	}

	public int set(int index, int element) {
		RangeCheck(index);

		int oldValue = elementData[index];
		elementData[index] = element;
		return oldValue;
	}

	public boolean add(int e) {
		ensureCapacity(size + 1); 
		elementData[size++] = e;
		return true;
	}

	public void add(int index, int element) {
		if (index > size || index < 0)
			throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);

		ensureCapacity(size + 1);
		System.arraycopy(elementData, index, elementData, index + 1, size - index);
		elementData[index] = element;
		size++;
	}

	public int removeIndex(int index) {
		RangeCheck(index);

		modCount++;
		int oldValue = elementData[index];

		int numMoved = size - index - 1;
		if (numMoved > 0)
			System.arraycopy(elementData, index + 1, elementData, index, numMoved);
		elementData[--size] = 0; 

		return oldValue;
	}

	public boolean remove(int o) {
		for (int index = 0; index < size; index++)
			if (o == elementData[index]) {
				fastRemove(index);
				return true;
			}
		return false;
	}

	private void fastRemove(int index) {
		modCount++;
		int numMoved = size - index - 1;
		if (numMoved > 0)
			System.arraycopy(elementData, index + 1, elementData, index, numMoved);
		elementData[--size] = 0;
	}

	public void clear() {
		modCount++;

		for (int i = 0; i < size; i++)
			elementData[i] = 0;

		size = 0;
	}

	public boolean addAll(int[] c) {
		int[] a = c;
		int numNew = a.length;
		ensureCapacity(size + numNew);
		System.arraycopy(a, 0, elementData, size, numNew);
		size += numNew;
		return numNew != 0;
	}

	public boolean addAll(int index, int[] c) {
		if (index > size || index < 0)
			throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);

		int[] a = c;
		int numNew = a.length;
		ensureCapacity(size + numNew);

		int numMoved = size - index;
		if (numMoved > 0)
			System.arraycopy(elementData, index, elementData, index + numNew, numMoved);

		System.arraycopy(a, 0, elementData, index, numNew);
		size += numNew;
		return numNew != 0;
	}

	protected void removeRange(int fromIndex, int toIndex) {
		modCount++;
		int numMoved = size - toIndex;
		System.arraycopy(elementData, toIndex, elementData, fromIndex, numMoved);

		int newSize = size - (toIndex - fromIndex);
		while (size != newSize)
			elementData[--size] = 0;
	}

	private void RangeCheck(int index) {
		if (index >= size)
			throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
	}
	
}
