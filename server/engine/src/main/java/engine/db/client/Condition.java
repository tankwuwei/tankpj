package engine.db.client;

import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import engine.net.NativeBuffer;

public class Condition {

	private static final byte eq = 1;
	private static final byte gt = 2;
	// private static final byte ge = 3;
	private static final byte lt = 4;
	// private static final byte le = 5;
	private static final byte in = 6;
	private static final byte asc = 7;
	private static final byte desc = 8;
	private static final byte limit = 9;
	// private static final byte or = 9;

	private byte type = 0;// 条件模式
	public String propertyName;
	public Object value;

	public boolean isEq() {
		return type == eq;
	}

	public boolean isLimit() {
		return type == limit;
	}

	public void write(NativeBuffer buf) {
		buf.writeByte(type);
		buf.writeUTF(propertyName);

		if (value instanceof Byte) {
			buf.writeUTF("byte");
			buf.writeByte((Byte) value);
		} else if (value instanceof Short) {
			buf.writeUTF("short");
			buf.writeShort((Short) value);
		} else if (value instanceof Integer) {
			buf.writeUTF("int");
			buf.writeInt((Integer) value);
		} else if (value instanceof Long) {
			buf.writeUTF("long");
			buf.writeLong((Long) value);
		} else if (value instanceof Float) {
			buf.writeUTF("float");
			buf.writeFloat((Float) value);
		} else if (value instanceof String) {
			buf.writeUTF("String");
			buf.writeUTF((String) value);
		} else if (value instanceof int[]) {
			buf.writeUTF("int[]");
			buf.writeArray((int[]) value);
		} else if (value instanceof long[]) {
			buf.writeUTF("long[]");
			buf.writeArray((long[]) value);
		} else {
			buf.writeUTF("NULL");
		}
	}

	public void read(NativeBuffer buf) {
		type = buf.readByte();
		propertyName = buf.readUTF();
		String typeName = buf.readUTF();
		if ("byte".equals(typeName)) {
			value = buf.readByte();
		} else if ("short".equals(typeName)) {
			value = buf.readShort();
		} else if ("int".equals(typeName)) {
			value = buf.readInt();
		} else if ("long".equals(typeName)) {
			value = buf.readLong();
		} else if ("String".equals(typeName)) {
			value = buf.readUTF();
		} else if ("float".equals(typeName)) {
			value = buf.readFloat();
		} else if ("int[]".equals(typeName)) {
			int[] v = buf.readIntArray();
			Object[] v2 = new Object[v.length];
			for (int i = 0; i < v2.length; i++) {
				v2[i] = v[i];
			}
			value = v2;
		} else if ("long[]".equals(typeName)) {
			long[] v = buf.readLongArray();
			Object[] v2 = new Object[v.length];
			for (int i = 0; i < v2.length; i++) {
				v2[i] = v[i];
			}
			value = v2;
		}

	}

	public Object f() {
		switch (type) {
		case eq:
			return Restrictions.eq(propertyName, value);
		case gt:
			return Restrictions.gt(propertyName, value);
		case lt:
			return Restrictions.lt(propertyName, value);
		case in:
			return Restrictions.in(propertyName, (Object[]) value);
		case asc:
			return Order.asc(propertyName);
		case desc:
			return Order.desc(propertyName);
		case limit:
			return value;
		}

		return null;
	}

	private static Condition create(byte type, String propertyName, Object o) {
		Condition c = new Condition();
		c.type = type;
		c.propertyName = propertyName;
		c.value = o;
		return c;
	}

	// 等于.
	public static Condition eq(String propertyName, long value) {
		return create(eq, propertyName, value);
	}

	public static Condition eq(String propertyName, int value) {
		return create(eq, propertyName, value);
	}

	public static Condition eq(String propertyName, byte value) {
		return create(eq, propertyName, value);
	}

	public static Condition eq(String propertyName, String value) {
		return create(eq, propertyName, value);
	}

	public static Condition eq(String propertyName, short value) {
		return create(eq, propertyName, value);
	}

	// great-than > 大于
	public static Condition gt(String propertyName, int value) {
		return create(gt, propertyName, value);
	}

	public static Condition gt(String propertyName, long value) {
		return create(gt, propertyName, value);
	}

	public static Condition gt(String propertyName, float value) {
		return create(gt, propertyName, value);
	}

	public static Condition gt(String propertyName, short value) {
		return create(gt, propertyName, value);
	}

	// less-than, < 小于
	public static Condition lt(String propertyName, int value) {
		return create(lt, propertyName, value);
	}

	public static Condition lt(String propertyName, long value) {
		return create(lt, propertyName, value);
	}

	public static Condition lt(String propertyName, float value) {
		return create(lt, propertyName, value);
	}

	public static Condition lt(String propertyName, short value) {
		return create(lt, propertyName, value);
	}

	// 对应SQL的in子句
	public static Condition in(String propertyName, int[] value) {
		return create(in, propertyName, value);
	}

	// 对应SQL的in子句
	public static Condition in(String propertyName, long[] value) {
		return create(in, propertyName, value);
	}

	// 升序
	public static Condition asc(String propertyName) {
		return create(asc, propertyName, null);
	}

	// 降序
	public static Condition desc(String propertyName) {
		return create(desc, propertyName, null);
	}

	public static Condition limit(int limitValue) {
		return create(limit, null, limitValue);
	}

}
