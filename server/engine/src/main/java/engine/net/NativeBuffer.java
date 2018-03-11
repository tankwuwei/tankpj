package engine.net;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.util.CharsetUtil;

import java.lang.reflect.Array;
import java.nio.ByteBuffer;

import engine.core.IReadable;
import engine.core.IWritable;
import engine.util.Pool;

public class NativeBuffer {

	/** buffer格式类型 */
	private static enum BufferType {
		Native,  // ByteBuffer格式
		ByteBuf  // netty.ByteBuf格式
	}

	/** buffer池的类型 */
	private static final class BufferPool extends Pool<NativeBuffer> {

		@Override
		protected NativeBuffer newObject() {
			return new NativeBuffer();
		}

		private NativeBuffer obtain(int useType) {
			NativeBuffer buf = obtain();
			buf.byteBuf.clear();
			buf.useType = useType;
			return buf;
		}
	}

	/** 基本数据类型枚举 */
	public static enum BaseDataType {
		Byte, Short, Integer, Long, String, Float, Double
	}

	/** Buffer用途 */
	/** 用于channel传输 in out */
	private final static int UseInChannel = 0;
	/** 用于内存临时处理 */
	private final static int UseInMemery = 1;

	/** buffer格式 */
	private static BufferType type = BufferType.ByteBuf;

	/** 是否是LE字节序 */
	@SuppressWarnings("unused")
	private static boolean IsLE = false;// 字节序 是否LE

	/** buffer池实例 */
	private static BufferPool pool = new BufferPool();

	private static boolean useNetty() {
		return type == BufferType.ByteBuf;
	}

	/**
	 * 池化buffer 分配一个在内存中的buffer实例 使用之后需要手动free()
	 */
	public static NativeBuffer allocate() {
		synchronized (pool) {
			return pool.obtain(UseInMemery);
		}
	}

	/**
	 * 池化buffer 将byte[]wrap在一个pool分配的buffer实例中 使用之后需要手动free()
	 * @return
	 */
	public static NativeBuffer wrap(byte[] bytes) {
		synchronized (pool) {
			NativeBuffer buf = pool.obtain(UseInMemery);
			buf.writeBytes(bytes);
			return buf;
		}
	}

	/**
	 * 非池化的buffer 用于发送和接受netty.byteBuf,由netty.release 一般用在消息底层
	 */
	public static NativeBuffer unpool() {
		return new NativeBuffer(UseInChannel);
	}

	/**
	 * 非池化的buffer 用于接收netty.inByteBuf,由netty.release 一般用在消息底层
	 */
	public static NativeBuffer wrap(ByteBuf buf) {
		NativeBuffer buffer = new NativeBuffer(buf);
		buffer.useType = UseInChannel;
		return buffer;
	}

	public static NativeBuffer copywrap(ByteBuf buf) {
		NativeBuffer buffer = allocate();
		buffer.byteBuf.writeBytes(buf);
		buffer.useType = UseInChannel;
		return buffer;
	}

	private ByteBuffer byteBuffer;
	private ByteBuf byteBuf;
	/** 使用类型 */
	private int useType;

	// 回收buffer
	public void free() {
		if (useType == UseInChannel)
			return;
		synchronized (pool) {
			if (useNetty()) {
				byteBuf.clear();
			} else {
				byteBuffer.clear();
			}
			pool.free(this);
		}
	}

	private NativeBuffer() {
		if (!useNetty()) {
			byteBuffer = ByteBuffer.allocate(10000);
		} else {
			byteBuf = Unpooled.buffer();
		}
	}

	// 默认构建方法，使用完成后需要free()
	private NativeBuffer(int useType) {
		if (!useNetty()) {
			byteBuffer = ByteBuffer.allocate(10000);
		} else {
			byteBuf = Unpooled.buffer();
		}
		this.useType = useType;
	}

	// 此构建方法生成的对象无需free()
	private NativeBuffer(ByteBuf buf) {
		if (useNetty()) {
			byteBuf = buf;
		} else {
			byte[] bytes = new byte[buf.readableBytes()];
			buf.readBytes(bytes);
			byteBuffer = ByteBuffer.wrap(bytes);
		}
	}

	// READ primitive type====================================================
	public Object read() {
		byte t = readByte();
		if (t == -1)
			return null;
		return read(BaseDataType.values()[t]);
	}

	public Object read(BaseDataType type) {
		switch (type) {
		case Integer:
			return readInt();
		case Long:
			return readLong();
		case Byte:
			return readByte();
		case Short:
			return readShort();
		case String:
			return readUTF();
		case Float:
			return readFloat();
		case Double:
			return readDouble();
		}
		return null;
	}

	public byte readByte() {
		if (useNetty())
			return byteBuf.readByte();
		return byteBuffer.get();
	}

	public short readShort() {
		if (useNetty())
			return byteBuf.readShort();
		return byteBuffer.getShort();
	}

	public int readInt() {
		if (useNetty())
			return byteBuf.readInt();
		return byteBuffer.getInt();
	}

	public long readLong() {
		if (useNetty())
			return byteBuf.readLong();
		return byteBuffer.getLong();
	}

	public float readFloat() {
		if (useNetty()) {
			float v = (float) (byteBuf.readInt());
			return v / 10000;
		}
		return byteBuffer.getFloat();
	}

	public double readDouble() {
		if (useNetty())
			return byteBuf.readDouble();
		return byteBuffer.getDouble();
	}

	public String readUTF() {
		int length = readShort();
		byte[] bytes = readBytes(length);
		return new String(bytes, CharsetUtil.UTF_8);
	}

	public byte[] readBytes(int length) {
		if (useNetty()) {
			byte[] value = new byte[length];
			byteBuf.readBytes(value);
			return value;
		}
		byte[] value = new byte[length];
		byteBuffer.get(value);
		return value;
	}

	public byte[] readBytes() {
		if (useNetty()) {
			byte[] bytes = new byte[readableBytes()];
			byteBuf.readBytes(bytes);
			return bytes;
		} else {
			byte[] bytes = new byte[readableBytes()];
			byteBuffer.get(bytes);
			return bytes;
		}
	}

	public byte[] array() {
		if (useNetty()) {
			if (byteBuf.hasArray()) {
				return byteBuf.array();
			} else {
				byte[] bytes = new byte[byteBuf.readableBytes()];
				byteBuf.getBytes(0, bytes);
				return bytes;
			}
		}
		byte[] r = new byte[byteBuffer.position()];
		System.arraycopy(byteBuffer.array(), 0, r, 0, r.length);
		return r;
	}

	// READ array
	// type=============================================================
	public byte[] readByteArray() {
		short len = readShort();
		if (len == 0)
			return null;
		return readBytes(len);
	}

	public short[] readShortArray() {
		short len = readShort();
		if (len == 0)
			return null;
		short[] result = new short[len];
		for (int i = 0; i < result.length; i++) {
			result[i] = readShort();
		}
		return result;
	}

	public int[] readIntArray() {
		short len = readShort();
		if (len == 0)
			return null;
		int[] result = new int[len];
		for (int i = 0; i < result.length; i++) {
			result[i] = readInt();
		}
		return result;
	}

	public long[] readLongArray() {
		short len = readShort();
		if (len == 0)
			return null;
		long[] result = new long[len];
		for (int i = 0; i < result.length; i++) {
			result[i] = readLong();
		}
		return result;
	}

	public String[] readUTFArray() {
		short len = readShort();
		if (len == 0)
			return null;
		String[] result = new String[len];
		for (int i = 0; i < result.length; i++) {
			result[i] = readUTF();
		}
		return result;
	}

	public float[] readFloatArray() {
		short len = readShort();
		if (len == 0)
			return null;
		float[] result = new float[len];
		for (int i = 0; i < result.length; i++) {
			result[i] = readFloat();
		}
		return result;
	}

	public double[] readDoubleArray() {
		short len = readShort();
		if (len == 0)
			return null;
		double[] result = new double[len];
		for (int i = 0; i < result.length; i++) {
			result[i] = readDouble();
		}
		return result;
	}

	@SuppressWarnings("unchecked")
	public <T extends IReadable> T[] readArray(Class<T> c) {
		short length = readShort();
		if (length == 0)
			return null;
		T[] r = (T[]) Array.newInstance(c, length);
		try {
			for (int i = 0; i < length; i++) {
				T t = null;
				if (readByte() == 1) {
					t = c.newInstance();
					t.read(this);
				}
				r[i] = t;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return r;
	}

	public <T extends IReadable> T read(Class<T> c) {
		if (readByte() == 0)
			return null;
		T t = null;
		try {
			t = c.newInstance();
		} catch (Exception e) {
			e.printStackTrace();
		}
		t.read(this);
		return t;
	}

	// WRITE primitive type====================================================
	public boolean write(Object v) {
		boolean suc = true;
		if (v instanceof Integer) {
			writeByte(BaseDataType.Integer.ordinal());
			writeInt((Integer) v);
		} else if (v instanceof Long) {
			writeByte(BaseDataType.Long.ordinal());
			writeLong((Long) v);
		} else if (v instanceof Byte) {
			writeByte(BaseDataType.Byte.ordinal());
			writeByte((Byte) v);
		} else if (v instanceof Short) {
			writeByte(BaseDataType.Short.ordinal());
			writeShort((Short) v);
		} else if (v instanceof String) {
			writeByte(BaseDataType.String.ordinal());
			writeUTF((String) v);
		} else if (v instanceof Float) {
			writeByte(BaseDataType.Float.ordinal());
			writeFloat((Float) v);
		} else if (v instanceof Double) {
			writeByte(BaseDataType.Double.ordinal());
			writeDouble((Double) v);
		} else {
			writeByte(-1);
			suc = false;
		}
		return suc;
	}

	public void write(Object v, BaseDataType t) {
		if (t == BaseDataType.Integer) {
			writeInt((Integer) v);
		} else if (t == BaseDataType.Long) {
			writeLong((Long) v);
		} else if (t == BaseDataType.Byte) {
			writeByte((Byte) v);
		} else if (t == BaseDataType.Short) {
			writeShort((Short) v);
		} else if (t == BaseDataType.String) {
			writeUTF((String) v);
		} else if (t == BaseDataType.Float) {
			writeFloat((Float) v);
		} else if (t == BaseDataType.Double) {
			writeDouble((Double) v);
		} else {
			writeByte(-1);
		}
	}

	public void setInt(int index, int v) {
		if (useNetty()) {
			byteBuf.setInt(index, v);
		} else {
			byteBuffer.putInt(index, v);
		}
	}

	public void setLong(int index, long v) {
		if (useNetty()) {
			byteBuf.setLong(index, v);
		} else {
			byteBuffer.putLong(index, v);
		}
	}

	public void setShort(int index, short v) {
		if (useNetty()) {
			byteBuf.setShort(index, v);
		} else {
			byteBuffer.putShort(index, v);
		}
	}

	public void setByte(int index, byte v) {
		if (useNetty()) {
			byteBuf.setByte(index, v);
		} else {
			byteBuffer.put(index, v);
		}
	}

	public void writeByte(int v) {
		if (useNetty()) {
			byteBuf.writeByte(v);
		} else {
			byteBuffer.put((byte) v);
		}
	}

	public void writeShort(int v) {
		if (useNetty()) {
			byteBuf.writeShort(v);
		} else {
			byteBuffer.putShort((short) v);
		}
	}

	public void writeInt(int v) {
		if (useNetty()) {
			byteBuf.writeInt(v);
		} else {
			byteBuffer.putInt(v);
		}
	}

	public void writeLong(long v) {
		if (useNetty()) {
			byteBuf.writeLong(v);
		} else {
			byteBuffer.putLong(v);
		}
	}

	public void writeFloat(float v) {
		if (useNetty()) {
			byteBuf.writeInt((int) (v * 10000));
			// byteBuf.writeFloat(v);
		} else {
			byteBuffer.putFloat(v);
		}
	}

	public void writeDouble(double v) {
		if (useNetty()) {
			byteBuf.writeDouble(v);
		} else {
			byteBuffer.putDouble(v);
		}
	}

	public void writeUTF(String v) {
		if (v == null)
			v = "";
		byte[] bytes = v.getBytes(CharsetUtil.UTF_8);
		writeShort(bytes.length);
		writeBytes(bytes);
	}

	// WRITE array type===============================
	public void writeArray(int[] v) {
		if (v == null) {
			writeShort((short) 0);
			return;
		}
		writeShort((short) v.length);
		for (int i = 0; i < v.length; i++) {
			writeInt(v[i]);
		}
	}

	public void writeArray(long[] v) {
		if (v == null) {
			writeShort((short) 0);
			return;
		}
		writeShort((short) v.length);
		for (int i = 0; i < v.length; i++) {
			writeLong(v[i]);
		}
	}

	public void writeArray(short[] v) {
		if (v == null) {
			writeShort((short) 0);
			return;
		}
		writeShort((short) v.length);
		for (int i = 0; i < v.length; i++) {
			writeShort(v[i]);
		}
	}

	public void writeArray(float[] v) {
		if (v == null) {
			writeShort((short) 0);
			return;
		}
		writeShort((short) v.length);
		for (int i = 0; i < v.length; i++) {
			writeFloat(v[i]);
		}
	}

	public void writeArray(String[] v) {
		if (v == null) {
			writeShort((short) 0);
			return;
		}
		writeShort((short) v.length);
		for (int i = 0; i < v.length; i++) {
			writeUTF(v[i]);
		}
	}

	public void writeArray(byte[] v) {
		if (v == null) {
			writeShort((short) 0);
			return;
		}
		writeShort((short) v.length);
		for (int i = 0; i < v.length; i++) {
			writeByte(v[i]);
		}
	}

	public void writeArray(double[] v) {
		if (v == null) {
			writeShort((short) 0);
			return;
		}
		writeShort((short) v.length);
		for (int i = 0; i < v.length; i++) {
			writeDouble(v[i]);
		}
	}

	public void writeArray(IWritable[] value) {
		if (value == null || value.length == 0)
			writeShort(0);
		else {
			int length = value.length;
			writeShort(length);
			for (int i = 0; i < length; i++) {
				if (value[i] == null) {
					writeByte(0);
				} else {
					writeByte(1);
					value[i].write(this);
				}
			}
		}
	}

	public void write(IWritable value) {
		if (value == null)
			writeByte(0);
		else {
			writeByte(1);
			value.write(this);
		}
	}

	public void writeBytes(byte[] v) {
		if (useNetty()) {
			byteBuf.writeBytes(v);
		} else {
			byteBuffer.put(v);
		}
	}

	public void writeBytes(ByteBuf buf) {
		if (useNetty()) {
			byteBuf.writeBytes(buf);
		}
	}

	public void writeBytes(ByteBuf buf, int length) {
		if (useNetty()) {
			byteBuf.writeBytes(buf, length);
		}
	}

	public int readableBytes() {
		if (useNetty()) {
			return byteBuf.readableBytes();
		}
		return byteBuffer.position();
	}

	public ByteBuf byteBuf() {
		if (useNetty())
			return byteBuf.copy();
		return Unpooled.wrappedBuffer(array());
	}

	public ByteBuf getByteBuf() {
		if (useNetty())
			return byteBuf;
		return null;
	}

	public int getReaderIndex() {
		if (useNetty())
			return byteBuf.readerIndex();
		return 0;
	}

	public int getWriterIndex() {
		if (useNetty())
			return byteBuf.writerIndex();
		return 0;
	}

	public void setReaderIndex(int index) {
		if (useNetty())
			byteBuf.readerIndex(index);
	}

	public void setWriteIndex(int index) {
		if (useNetty()) {
			byteBuf.writerIndex(index);
		}
	}

	/**
	 * 读一个short，但不改变readIndex
	 * @return
	 */
	public short peekShort() {
		if (useNetty()) {
			return byteBuf.getShort(byteBuf.readerIndex());
		} else {
			return byteBuffer.getShort(byteBuffer.position());
		}
	}
}
