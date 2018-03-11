package com.ft.dbserver;

public class StringConvertor {
	
	/**
	 * 将String的值转成需要的目标对象
	 * @param c
	 * @param value
	 * @return
	 */
	public static Object convert(Class<?> c, String value){
		if(c == int.class){
			return Integer.valueOf(value);
		}else if(c == long.class){
			return Long.valueOf(value);
		}else if(c == short.class){
			return Short.valueOf(value);
		}else if(c == byte.class){
			return Byte.valueOf(value);
		}
		return value;
	}
}	
