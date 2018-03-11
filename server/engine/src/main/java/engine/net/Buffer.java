package engine.net;


import io.netty.buffer.ByteBuf;
import io.netty.util.CharsetUtil;

import java.nio.ByteBuffer;

import engine.util.Pool.Poolable;

public class Buffer implements Poolable{
//	private static final int SIZE=256;
	public int code;
	public Object o;//attached data,normally Client
	
	public ByteBuf buf;
	private ByteBuffer nioBuffer;
	public Buffer(){}
	public void reset(ByteBuf buf){
		this.buf=buf;
		this.nioBuffer=null;
	}
	public void nioBuffer(ByteBuf buf){
		//TODO consider use direct buffer to reduce gc
		byte[] bytes=new byte[buf.readableBytes()];
		buf.readBytes(bytes);
		nioBuffer=ByteBuffer.wrap(bytes);
	}
	
	public void reset(){
		buf.clear();
		
	}
	public void release(){
		if(buf!=null)buf.release();
	}

	public ByteBuf beforeSend(){
		buf.setInt(0, buf.readableBytes()-4);
		return buf;
	}
	public byte readByte(){
		if(nioBuffer!=null) return nioBuffer.get();
		return buf.readByte();
	}
	public short readShort(){
		if(nioBuffer!=null) return nioBuffer.getShort();
		return buf.readShort();
	}
	
	public int readInt(){
		if(nioBuffer!=null) return nioBuffer.getInt();
		return buf.readInt();
	}
	public int[] readIntArray(){
		if(nioBuffer!=null){
			short len = nioBuffer.getShort();
			int[] result = new int[len];
			for (int i = 0; i< len; i++){
				result[i] = nioBuffer.getInt();
			}
			return result;
		}
		
		short len = buf.readShort();
		int[] result = new int[len];
		for (int i = 0; i< len; i++){
			result[i] = buf.readInt();
		}
		return result;
	}
	
	public long[] readLongArray(){
		if(nioBuffer!=null){
			short len = nioBuffer.getShort();
			long[] result = new long[len];
			for (int i = 0; i< len; i++){
				result[i] = nioBuffer.getLong();
			}
			return result;
		}
		
		short len = buf.readShort();
		long[] result = new long[len];
		for (int i = 0; i< len; i++){
			result[i] = buf.readLong();
		}
		return result;
	}

	public short[] readShortArray() {
		if(nioBuffer!=null){
			short len = nioBuffer.getShort();
			short[] result = new short[len];
			for (int i = 0; i< len; i++){
				result[i] = nioBuffer.getShort();
			}
			return result;
		}
		
		short len = buf.readShort();
		short[] result = new short[len];
		for (int i = 0; i< len; i++){
			result[i] = buf.readShort();
		}
		return result;
	}
	public long readLong(){
		if(nioBuffer!=null) return nioBuffer.getLong();
		return buf.readLong();
	}
	public float readFloat(){
		if(nioBuffer!=null) return nioBuffer.getFloat();
		return buf.readFloat();
	}
	public double readDouble(){
		if(nioBuffer!=null) return nioBuffer.getDouble();
		return buf.readDouble();
	}
	public String readUTF(){
		if(nioBuffer!=null){
			int length=nioBuffer.getShort();
			byte[] bytes=new byte[length];
			nioBuffer.get(bytes); 
			return new String(bytes,CharsetUtil.UTF_8);
		}
		
		int length=buf.readShort();
		byte[] bytes=new byte[length];
		buf.readBytes(bytes); 
		return new String(bytes,CharsetUtil.UTF_8);
	}
	public byte[] readBytes(int length){
		byte[] value=new byte[length];
		if(nioBuffer!=null){
			nioBuffer.get(value); 
		}else{
			buf.readBytes(value);
		}
		
		return value;
	}
	
	public byte[] getBytes(){
		if(nioBuffer!=null){
			int length=nioBuffer.limit()-nioBuffer.position();
			byte[] result = new byte[length];
			nioBuffer.get(result);
			return result;
		}
		byte[] result = new byte[buf.readableBytes()];
		buf.readBytes(result);
		return result;
	}
	public void writeByte(int value){
		buf.writeByte(value);
	}
	public void writeShort(int value){
		buf.writeShort(value);
	}
	
	public void writeInt(int value){
		buf.writeInt(value);
	}
	public void writeLong(long value){
		buf.writeLong(value);
	}
	public void writeFloat(float value){
		buf.writeFloat(value);
	}
	public void writeDouble(double value){
		buf.writeDouble(value);
	}
	public void writeUTF(String value){
		if(value==null) value="";
		byte[] bytes=value.getBytes(CharsetUtil.UTF_8);
		buf.writeShort(bytes.length);
		buf.writeBytes(bytes);
	}
	public void write(int[] value){
		if(value == null){
			buf.writeShort(0);
			return;
		}
		buf.writeShort(value.length);
		for (int i = 0; i< value.length; i++){
			buf.writeInt(value[i]);
		}
	}
	public void write(long[] value){
		if(value == null){
			buf.writeShort(0);
			return;
		}
		buf.writeShort(value.length);
		for (int i = 0; i< value.length; i++){
			buf.writeLong(value[i]);
		}
	}
	public void writeBytes(byte[] value){
		buf.writeBytes(value);
	}
	public void writeBytes(byte[] value, int srcIndex, int len){
		buf.writeBytes(value, srcIndex, len);
	}
	public int readableBytes(){
		return buf.readableBytes();
	}
	
}
