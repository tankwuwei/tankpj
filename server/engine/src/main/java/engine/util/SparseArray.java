package engine.util;

/**
 * 稀疏数组，针对数据分布很有规律的数组，可以采用稀疏数组来存储数据。 结构上是：重复次数,数值，重复次数，数值......
 * @author xjf
 */
public class SparseArray {
	private static final boolean Debug = true;
	private static final int MaxSegmentLength = 32768;
	private static final int CapacityExtend = 4;
	private short[] data;
	private int end = 0;

	public SparseArray(short[] data) {
		this.data = data;
		for (int i = 0; i < data.length; i += 2) {
			if (data[i] == 0) {
				end = i;
				break;
			}
		}
		if (end == 0) {
			end = data.length - 2;
		}
	}

	/**
	 * 初始化稀疏数组，capacity的长度必须大于maxCount/256;
	 * @param capacity 实际的数组长度
	 * @param maxCount 需要存储的数据量
	 */
	public SparseArray(int maxCount) {
		int capacity = (1 + maxCount / MaxSegmentLength) * CapacityExtend; // 单个short最多表示65535长度
		data = new short[capacity * CapacityExtend];
		init(maxCount);
	}

	private void init(int max) {
		// 所有数值设为0
		int segmentLen = MaxSegmentLength / 2;
		while (max > 0) {
			data[end++] = (short) segmentLen;
			data[end++] = 0;
			max -= segmentLen;
		}
	}

	public short[] data() {
		return data;
	}

	/**
	 * 设置值
	 * @param index 真实下标
	 * @param value
	 */
	public void setData(int index, short value) {
		int internalIndex = find(index);
		if (value == data[internalIndex + 1]) {
			// 值一样，不需要改变
			return;
		}
		int start = getStart(internalIndex);
		// 生成待插入的片段，按照在不同位置进行插入，得到的片段长度分别是1，2，3块数据
		short[] insert = split(data[internalIndex], data[internalIndex + 1], value, (short) (index - start));
		replace(internalIndex, insert);
		if (Debug) {
			int ii = check();
			if (ii == -1)
				return;
			System.out.println("-----Check error at dup index=" + ii + ",  set data index=" + index + ", value="
					+ value + ", internalIndex=" + internalIndex + ", start=" + start + ", pos=" + (index - start));
			Utility.println(data);
		}
	}

	public short getData(int index) {
		index = find(index);
		return data[index + 1];
	}

	/**
	 * 找到序号对应的数组下标
	 * @param index
	 * @return
	 */
	private int find(int index) {
		// 暂时用顺序查找的方式来处理，后续优化
		int start = 0;
		int i = 0;
		for (; i < data.length; i += 2) {
			if (start + data[i] <= index) {
				start += data[i];
				continue;
			} else
				break;
		}
		return i;
	}

	/**
	 * 取得指定序号的真实起始序号
	 * @param index
	 * @return
	 */
	private int getStart(int index) {
		int sum = 0;
		for (int i = 0; i < index; i += 2) {
			sum += data[i];
		}
		return sum;
	}

	/**
	 * 生成待插入的数据
	 * @param length
	 * @param oldValue
	 * @param newValue
	 * @param pos
	 * @return 被分裂开来的数据格
	 */
	private short[] split(short length, short oldValue, short newValue, short pos) {
		short[] result = null;
		if (pos == 0 && length == 1) { // 直接替换
			result = new short[] { 1, newValue };
		} else if (pos == 0) { // 在头部
			result = new short[] { 1, newValue, (short) (length - 1), oldValue };
		} else if (pos == length - 1) { // 在尾部
			result = new short[] { (short) (length - 1), oldValue, 1, newValue };
		} else { // 在中间
			result = new short[] { pos, oldValue, 1, newValue, (short) (length - pos - 1), oldValue };
		}
		return result;
	}

	/**
	 * 使用给的片段数组替换指定位置的片段
	 * @param index 需要被替换的片段
	 * @param fragment
	 */
	private void replace(int index, short[] src) {
		if (src.length == 2) { // 长度2，直接替换即可
			data[index] = src[0];
			data[index + 1] = src[1];
			// if(!atFoot(index)){
			// checkAndMergeWithNext(index);
			// }
			// if(!atHead(index)){
			// checkAndMergeWithNext(index-2);
			// }
			// return;
		} else {
			shiftRight(index, src.length - 2);
		}
		for (int i = 0; i < src.length; i++) {
			data[index + i] = src[i];
		}
		if (!atFoot(index + src.length - 2)) {
			checkAndMergeWithNext(index + src.length - 2);
		}
		if (!atHead(index)) {
			checkAndMergeWithNext(index - 2);
		}
	}

	private boolean atHead(int index) {
		return index == 0;
	}

	private boolean atFoot(int index) {
		return index == end - 2;
	}

	/**
	 * 将指定区块和下一个区块进行合并检测，如果可以合并，则合并
	 * @param index
	 */
	private void checkAndMergeWithNext(int index) {
		if (data[index + 1] == data[index + 3] && data[index] + data[index + 2] < MaxSegmentLength) {
			System.out.println("shift left index=" + index);
			// can merge
			data[index + 2] = (short) (data[index] + data[index + 2]);
			shiftLeft(index + 2, 2);
		}
	}

	/**
	 * 将data数组的数据向右移
	 * @param index 从指定位置开始
	 * @param len 平移多长一段
	 */
	private void shiftRight(int index, int len) {
		if (end + len > data.length) {
			expand(len);
		}
		System.arraycopy(data, index, data, index + len, end - index);
		end += len;
	}

	/**
	 * 将data数组的数据向左平移
	 * @param index 从指定位置开始
	 * @param len 平移多长一段
	 */
	private void shiftLeft(int index, int len) {
		System.arraycopy(data, index, data, index - len, data.length - index);
		end -= len;
		for (int i = 0; i < len; i++) {
			data[i + end] = 0;
		}
	}

	/**
	 * 数组扩展长度
	 * @param need
	 */
	private void expand(int need) {
		short[] tmp = new short[end + need + CapacityExtend];
		System.arraycopy(data, 0, tmp, 0, data.length);
		data = tmp;
	}

	/**
	 * 检测错误的情况
	 * @return
	 */
	private int check() {
		int v = data[1];
		for (int i = 2; i < end; i += 2) {
			if (v == data[i + 1] && data[i - 2] + data[i] < MaxSegmentLength)
				return i;
			v = data[i + 1];
		}
		return -1;
	}

	private int getCapacity() {
		int sum = 0;
		for (int i = 0; i < end; i += 2) {
			sum += data[i];
		}
		return sum;
	}

	public String toString() {
		return "array length=" + data.length + ", end=" + end + ", capacity=" + getCapacity();
	}
}
