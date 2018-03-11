package engine.util.lottery;

/**
 * 定义一个连续集合 集合中元素x满足:(minElement,maxElement] 
 * 数学表达式为：minElement < x <= maxElement
 * 
 */
public class ContinuousList {

	public ContinuousList(double minElement, double maxElement) {
		if (minElement > maxElement) {
			throw new IllegalArgumentException("区间不合理，minElement不能大于maxElement！");
		}
		this.minElement = minElement;
		this.maxElement = maxElement;
	}

	/**
	 * 判断当前集合是否包含特定元素
	 * 
	 * @param element
	 * @return
	 */
	public boolean isContainKey(double element) {
		boolean flag = false;
		if (element > minElement && element <= maxElement) {
			flag = true;
		}
		return flag;
	}

	private double minElement;
	private double maxElement;
}
