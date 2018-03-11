package engine.net;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.util.CharsetUtil;

import java.nio.ByteBuffer;


public class Buffers {
	///////////read buffer pool////////////////////
//	private static final Pool<Buffer> pool = new Pool<Buffer>(100) {
//		@Override
//		protected Buffer newObject() {
//			return new Buffer();
//		}
//	};
//	private static final Pool<ByteBuf> bbPool=new Pool<ByteBuf>(){
//		@Override
//		protected ByteBuf newObject() {
//			return Unpooled.buffer();
//		}
//	};
//	
//	public static Buffer getRead(ByteBuf buf){
//		Buffer buffer=bufferPool.obtain();
//		buffer.reset(buf);
//		return buffer;
//	}
//	public static void freeRead(Buffer buffer){
//		bufferPool.free(buffer);
//	}
	
	public static byte[] readBytes(ByteBuf buf){
		byte[] dst=new byte[buf.readShort()];
		buf.readBytes(dst);
		return dst;
	}
	public static byte[] readByteArray(ByteBuf buf){
		int l = buf.readShort();
		byte[] v = new byte[l];
		for (int i = 0; i < l; i++) {
			v[i] = buf.readByte();
		}
		return v;
	}
	public static int[] readIntArray(ByteBuf buf){
		int l = buf.readShort();
		int[] v = new int[l];
		for(int i=0;i<l;i++){
			v[i] = buf.readInt();
		}
		return v;
	}
	public static float[] readFloatArray(ByteBuf buf){
		int l = buf.readShort();
		float[] v = new float[l];
		for(int i=0;i<l;i++){
			v[i] = buf.readFloat();
		}
		return v;
	}
	public static short[] readShortArray(ByteBuf buf){
		int l = buf.readShort();
		short[] v = new short[l];
		for(int i=0;i<l;i++){
			v[i] = buf.readShort();
		}
		return v;
	}
	public static long[] readLongArray(ByteBuf buf){
		int l = buf.readShort();
		long[] v = new long[l];
		for(int i=0;i<l;i++){
			v[i] = buf.readLong();
		}
		return v; 
	}
	public static String[] readUTFArray(ByteBuf buf){
		int l = buf.readShort();
		String[] v = new String[l];
		for(int i=0;i<l;i++){
			v[i] = readUTF(buf);
		}
		return v;
	}
	public static int[][] readIntTwoArray(ByteBuf buf){
		int l = buf.readShort();
		int[][] v = new int[l][];
		for (int i = 0; i < l; i++) {
			v[i] = readIntArray(buf);
		}
		return v;
	}
	
	public static String readUTF(ByteBuf buf){
		int length=buf.readShort();
		byte[] bytes=new byte[length];
		buf.readBytes(bytes);
		return new String(bytes,CharsetUtil.UTF_8);
	}
	
//	public static void writeArray(ByteBuf buf,IWritable[] value){
//		if(value==null||value.length==0) buf.writeShort(0);
//		else{
//			int length=value.length;
//			buf.writeShort(length);
//			for(int i=0;i<length;i++) value[i].write(buf);
//		}
//	}
	public static void writeArray(ByteBuf buf,int[] value){
		if(value==null||value.length==0) buf.writeShort(0);
		else{
			int length=value.length;
			buf.writeShort(length);
			for(int i=0;i<length;i++) buf.writeInt(value[i]);
		}
	}
	public static void writeTowArray(ByteBuf buf,int[][] value){
		if(value==null||value.length==0) buf.writeShort(0);
		else{
			int length=value.length;
			buf.writeShort(length);
			for(int i=0;i<length;i++) writeArray(buf,value[i]);
		}
	}
	public static void writeArray(ByteBuf buf,long[] value){
		if(value==null||value.length==0) buf.writeShort(0);
		else{
			int length=value.length;
			buf.writeShort(length);
			for(int i=0;i<length;i++) buf.writeLong(value[i]);
		}
	}
	public static void writeArray(ByteBuf buf,short[] value){
		if(value==null||value.length==0) buf.writeShort(0);
		else{
			int length=value.length;
			buf.writeShort(length);
			for(int i=0;i<length;i++) buf.writeShort(value[i]);
		}
	}
	public static void writeArray(ByteBuf buf,float[] value){
		if(value==null||value.length==0) buf.writeShort(0);
		else{
			int length = value.length;
			buf.writeShort(length);
			for (int i = 0; i < length; i++) buf.writeFloat(value[i]);
		}
	}
	public static void writeArray(ByteBuf buf,String[] value){
		if(value==null||value.length==0) buf.writeShort(0);
		else{
			int length=value.length;
			buf.writeShort(length);
			for(int i=0;i<length;i++) writeUTF(buf,value[i]);
		}
	}
	public static void writeArray(ByteBuf buf,byte[] value){
		if(value==null||value.length==0) buf.writeShort(0);
		else{
			int length=value.length;
			buf.writeShort(length);
			buf.writeBytes(value);
		}
	}
	public static void writeUTF(ByteBuf buf,String value){
		if(value==null||value.length()==0) buf.writeShort(0);
		else{
			byte[] bytes=value.getBytes(CharsetUtil.UTF_8);
			buf.writeShort(bytes.length);
			buf.writeBytes(bytes);
		}
	}
	///////////////////////////////////////////////////
	
	public static Buffer create(int code){
		ByteBuf buf=Unpooled.directBuffer();
		Buffer buffer=new Buffer();
		buffer.reset(buf);
		buffer.writeInt(0);
		buffer.writeShort(code);
		return buffer;
	}
	public static Buffer createUdp(int code){
		ByteBuf buf=Unpooled.directBuffer();
		Buffer buffer=new Buffer();
		buffer.reset(buf);		
		buffer.writeShort(code);
		return buffer;
	}

	public static Buffer getLocal() {
		ByteBuf buf = Unpooled.directBuffer();
		Buffer buffer = new Buffer();
		buffer.reset(buf);
		return buffer;
	}
	public static void putUTF(ByteBuffer buf, String value) {
		if(value==null||value.length()==0) buf.putShort((short)0);
		else{
			byte[] bytes=value.getBytes(CharsetUtil.UTF_8);
			buf.putShort((short)bytes.length);
			buf.put(bytes);
		}
	}
	public static void putArray(ByteBuffer buf, long[] value) {
		if(value==null||value.length==0) buf.putShort((short)0);
		else{
			int length=value.length;
			buf.putShort((short)length);
			for(int i=0;i<length;i++) buf.putLong(value[i]);
		}
	}
	
}
