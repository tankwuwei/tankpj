package com.ft.gameserver.utility;

public class Int3264 {
	public static int[] separateLong2int(Long val) {
		int[] ret = new int[2];
		ret[0] = (int) (0xFFFFFFFFl & val);
		ret[1] = (int) ((0xFFFFFFFF00000000l & val) >> 32);
		return ret;
	}

	public static long combineInt2Long(int low, int high) {
		return ((long) low & 0xFFFFFFFFl) | (((long) high << 32) & 0xFFFFFFFF00000000l);
	}
}
