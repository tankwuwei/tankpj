package engine.arithmetic;

import engine.util.MathUtils;

/**
 * 一个选择器，用来确定取一个数组、队列中的哪个
 * @author xjf
 */
public interface Getter {
	public int get(int size);

	/**
	 * 随机模式
	 */
	public static Getter Random = (size) -> {
		return MathUtils.getRandomInt(size);
	};
	/**
	 * 顺序模式
	 */
	public static Getter Sequence = (size) -> {
		return 0;
	};
	/**
	 * 倒序模式
	 */
	public static Getter Inverted = (size) -> {
		return size - 1;
	};
}