package engine.net;

import java.util.HashMap;
import java.util.Map;

import io.netty.buffer.ByteBuf;

public abstract class FieldReaderWriter<T> {
	
	public static Map<String, FieldReaderWriter<?>> map = new HashMap<>();
	
	static {
		map.put("int", new FieldReaderWriter<Integer>() {
			@Override
			public Integer read(ByteBuf buf) {
				return buf.readInt();
			}
			@Override
			public void write(ByteBuf buf, Object t) {
				buf.writeInt((Integer)t);
			}
		});
		map.put("long", new FieldReaderWriter<Long>() {
			@Override
			public Long read(ByteBuf buf) {
				return buf.readLong();
			}
			@Override
			public void write(ByteBuf buf, Object t) {
				buf.writeLong((Long)t);
			}
		});
		map.put("short", new FieldReaderWriter<Short>() {
			@Override
			public Short read(ByteBuf buf) {
				return buf.readShort();
			}
			@Override
			public void write(ByteBuf buf, Object t) {
				buf.writeShort((Short)t);
			}
		});
		map.put("byte", new FieldReaderWriter<Byte>() {
			@Override
			public Byte read(ByteBuf buf) {
				return buf.readByte();
			}
			@Override
			public void write(ByteBuf buf, Object t) {
				buf.writeInt((Byte)t);
			}
		});
		map.put("float", new FieldReaderWriter<Float>() {
			@Override
			public Float read(ByteBuf buf) {
				return buf.readFloat();
			}
			@Override
			public void write(ByteBuf buf, Object t) {
				buf.writeFloat((Float)t);
			}
		});
		map.put("String", new FieldReaderWriter<String>() {
			@Override
			public String read(ByteBuf buf) {
				return Buffers.readUTF(buf);
			}
			@Override
			public void write(ByteBuf buf, Object t) {
				Buffers.writeUTF(buf, (String)t);
			}
		});
		map.put("int[]", new FieldReaderWriter<int[]>() {
			@Override
			public int[] read(ByteBuf buf) {
				return Buffers.readIntArray(buf);
			}
			@Override
			public void write(ByteBuf buf, Object t) {
				Buffers.writeArray(buf, (int[])t);
			}
		});
		map.put("long[]", new FieldReaderWriter<long[]>() {
			@Override
			public long[] read(ByteBuf buf) {
				return Buffers.readLongArray(buf);
			}
			@Override
			public void write(ByteBuf buf, Object t) {
				Buffers.writeArray(buf,(long[])t);
			}
		});
		map.put("short[]", new FieldReaderWriter<short[]>() {
			@Override
			public short[] read(ByteBuf buf) {
				return Buffers.readShortArray(buf);
			}
			@Override
			public void write(ByteBuf buf, Object t) {
				Buffers.writeArray(buf, (short[])t);
			}
		});
		map.put("float[]", new FieldReaderWriter<float[]>() {
			@Override
			public float[] read(ByteBuf buf) {
				return Buffers.readFloatArray(buf);
			}
			@Override
			public void write(ByteBuf buf, Object t) {
				Buffers.writeArray(buf,(float[])t);
			}
		});
		map.put("String[]", new FieldReaderWriter<String[]>() {
			@Override
			public String[] read(ByteBuf buf) {
				return Buffers.readUTFArray(buf);
			}
			@Override
			public void write(ByteBuf buf, Object t) {
				Buffers.writeArray(buf, (String[])t);
			}
		});
	}
	
	public abstract T read(ByteBuf buf);
	public abstract void write(ByteBuf buf, Object t);
	
}
