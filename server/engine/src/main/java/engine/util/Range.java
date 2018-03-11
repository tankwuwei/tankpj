package engine.util;

/**
 * 定义一个区间
 * @author xjf
 * @param <T>
 */
public abstract class Range<T> {
	public abstract T getValue();

	public abstract boolean contain(T v);

	public static Range<Integer> valueOf(int a, int b) {
		return new IntRange(a, b);
	}

	public static Range<Float> valueOf(float a, float b) {
		return new FloatRange(a, b);
	}

	public static Range<Long> valueOf(long a, long b) {
		return new LongRange(a, b);
	}

	private static class IntRange extends Range<Integer> {
		int start, end;

		public IntRange(int a, int b) {
			start = Math.min(a, b);
			end = Math.max(a, b);
		}

		@Override
		public Integer getValue() {
			return MathUtils.getRandomBetween(start, end);
		}

		@Override
		public boolean contain(Integer v) {
			return start <= v && end >= v;
		}

	}

	private static class LongRange extends Range<Long> {
		long start, end;

		public LongRange(long a, long b) {
			start = Math.min(a, b);
			end = Math.max(a, b);
		}

		@Override
		public Long getValue() {
			return MathUtils.getRandomLong(end - start) + start;
		}

		@Override
		public boolean contain(Long v) {
			return start <= v && end >= v;
		}
	}

	private static class FloatRange extends Range<Float> {
		float start, end;

		public FloatRange(float a, float b) {
			start = Math.min(a, b);
			end = Math.max(a, b);
		}

		@Override
		public Float getValue() {
			return MathUtils.getRandomFloat(end - start) + start;
		}

		@Override
		public boolean contain(Float v) {
			return start <= v && end >= v;
		}
	}
}
