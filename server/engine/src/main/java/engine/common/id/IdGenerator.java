package engine.common.id;

import java.util.concurrent.atomic.AtomicLong;

public class IdGenerator {
	/** 运营商标识 */
	private final short operator;
	/** 服务器标识 */
	private final short server;
	/** 当前自增值 */
	private final AtomicLong current;
	/** 溢出边界 */
	private final long limit;

	/**
	 * 运营商标识
	 * @param operator
	 * @param server //服务器编号
	 * @param current //当前起始id
	 */
	public IdGenerator(short operator, short server, long current) {
		if (!validValue(operator, 12)) {
			throw new IllegalArgumentException("运营商标识[" + operator + "]超过了12位二进制数的表示范围");
		}
		if (!validValue(server, 12)) {
			throw new IllegalArgumentException("服务器标识[" + server + "]超过了12位二进制数的表示范围");
		}
		this.operator = operator;
		this.server = server;

		final long[] limits = getLimits(operator, server);
		if (current > 0) {
			if (current < limits[0] || current > limits[1]) {
				throw new IllegalArgumentException("当前主键值[" + current + "],不符合运营商标识[" + operator
						+ "]服务器标识[" + server
						+ "]的要求");
			}
			this.current = new AtomicLong(current);
		} else {
			this.current = new AtomicLong(limits[0]);
		}
		this.limit = limits[1];
	}

	public long getCurrent() {
		return current.get();
	}

	public long getNext() {
		long result = current.incrementAndGet();
		if (result > limit) {
			throw new IllegalStateException("主键值[" + result + "]已经超出了边界[" + limit + "]");
		}
		return result;
	}

	public short getServer() {
		return server;
	}

	public short getOperator() {
		return operator;
	}

	public long getLimit() {
		return limit;
	}

	/**
	 * 获取主键中的服标识
	 * @param id 主键值
	 * @return
	 */
	public static short getServer(long id) {
		if ((0xF000000000000000L & id) != 0) {
			throw new IllegalArgumentException("无效的ID标识值:" + id);
		}
		// 将高位置0(保留位+运营商位+服务器位)
		return (short) ((id >> 36) & 0x0000000000000FFFL);
	}

	/**
	 * 获取主键中的运营商标识
	 * @param id 主键值
	 * @return
	 */
	public static short toOperator(long id) {
		if ((0xF000000000000000L & id) != 0) {
			throw new IllegalArgumentException("无效的ID标识值:" + id);
		}
		// 将高位置0(保留位+运营商位+服务器位)
		return (short) ((id >> 48) & 0x0000000000000FFFL);
	}

	private static boolean validValue(short value, int digit) {
		if (digit <= 0 || digit > 64) {
			throw new IllegalArgumentException("位数必须在1-64之间");
		}
		int max = (1 << digit) - 1;
		return value >= 0 && value <= max;
	}

	/**
	 * 获取ID值边界
	 * @param operator 运营商值
	 * @param server 服务器值
	 * @return [0]:最小值,[1]:最大值
	 */
	private static long[] getLimits(short operator, short server) {
		if (!validValue(operator, 12)) {
			throw new IllegalArgumentException("运营商标识[" + operator + "]超过了12位二进制数的表示范围");
		}
		if (!validValue(server, 12)) {
			throw new IllegalArgumentException("服务器标识[" + server + "]超过了12位二进制数的表示范围");
		}

		long min = (((long) operator) << 48) + (((long) server) << 36);
		long max = min | 0x0000000FFFFFFFFFL;
		return new long[] { min, max };
	}
}
