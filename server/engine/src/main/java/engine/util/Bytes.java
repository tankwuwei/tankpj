package engine.util;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import engine.net.Buffer;
import engine.net.Buffers;


/**
 * primitive/primitive array and byte array conversion.
 * 
 * @author SunnyAwake
 *
 */
public class Bytes {
	
	public static String[] stringArray(byte[] value) {
		if(value==null||value.length==0) return null;
		Buffer buffer=Buffers.getLocal();
		buffer.writeBytes(value);
		List<String> result=new ArrayList<String>();
		while(buffer.readableBytes()>1){
			result.add(buffer.readUTF());
		}
		buffer.release();
		return result.toArray(new String[0]);
	}
	public static byte[] get(String[] value){
		if(value==null) return null;
		int length=value.length;
		Buffer buffer=Buffers.getLocal();
		for(int i=0;i<length;i++){
			buffer.writeUTF(value[i]);
		}
		byte[] result= buffer.getBytes();
		buffer.release();
		return result;
	}
	public static boolean[] booleanArray(byte[] value){
		if(value==null) return null;
		int length=value.length;
		boolean[] result=new boolean[length];
		for(int i=0;i<length;i++){
			result[i]=value[i]==1?true:false;
		}
		return result;
	}
	public static byte[] get(double[] value){
		if(value==null) return null;
		int length=value.length;
		Buffer buffer=Buffers.getLocal();
		for(int i=0;i<length;i++){
			buffer.writeDouble(value[i]);
		}
		byte[] result= buffer.getBytes();
		buffer.release();
		return result;
		
	}
	public static double[] doubleArray(byte[] value){
		if(value==null) return null;
		int length=value.length/8;
		double[] result=new double[length];
		Buffer buffer=Buffers.getLocal();
		buffer.writeBytes(value);
		for(int i=0;i<length;i++){
			result[i]=buffer.readDouble();
		}
		buffer.release();
		return result;
	}
	public static byte[] get(float[] value){
		if(value==null) return null;
		int length=value.length;
		Buffer buffer=Buffers.getLocal();
		for(int i=0;i<length;i++){
			buffer.writeFloat(value[i]);
		}
		byte[] result= buffer.getBytes();
		buffer.release();
		return result;
		
	}
	public static float[] floatArray(byte[] value){
		if(value==null) return null;
		int length=value.length/2;
		float[] result=new float[length];
		Buffer buffer=Buffers.getLocal();
		buffer.writeBytes(value);
		for(int i=0;i<length;i++){
			result[i]=buffer.readFloat();
		}
		buffer.release();
		return result;
	}
	public static byte[] get(short[] value){
		if(value==null)return null;
		int length=value.length;
		Buffer buffer=Buffers.getLocal();
		for(int i=0;i<length;i++){
			buffer.writeShort(value[i]);
		}
		byte[] result= buffer.getBytes();
		buffer.release();
		return result;
	}
	public static short[] shortArray(byte[] value){
		if(value==null) return null;
		int length=value.length/2;
		short[] result=new short[length];
		Buffer buffer=Buffers.getLocal();
		buffer.writeBytes(value);
		for(int i=0;i<length;i++){
			result[i]=buffer.readShort();
		}
		return result;
	}
	public static byte[] get(long[] value){
		if(value==null) return null;
		int length=value.length;
		Buffer buffer=Buffers.getLocal();
		for(int i=0;i<length;i++){
			buffer.writeLong(value[i]);
		}
		byte[] result= buffer.getBytes();
		buffer.release();
		return result;
	}
	public static long[] longArray(byte[] value){
		if(value==null) return null;
		int length=value.length/8;
		long[] result=new long[length];
		Buffer buffer=Buffers.getLocal();
		buffer.writeBytes(value);
		for(int i=0;i<length;i++){
			result[i]=buffer.readLong();
		}
		buffer.release();
		return result;
	}
	public static byte[] get(int[] value){
		if(value==null) return null;
		int length=value.length;
		Buffer buffer=Buffers.getLocal();
		for(int i=0;i<length;i++){
			buffer.writeInt(value[i]);
		}
		byte[] result= buffer.getBytes();
		buffer.release();
		return result;			
		
	}
	
	public static int[] intArray(byte[] value){
		if(value==null) return null;
		int length=value.length/4;
		int[] result=new int[length];
		Buffer buffer=Buffers.getLocal();
		buffer.writeBytes(value);
		for(int i=0;i<length;i++){
			result[i]=buffer.readInt();
		}
		buffer.release();
		return result;
	}
	public static Set<Long> longSet(byte[] value){
		Set<Long> result=new HashSet<Long>();
		if(value!=null){
			int length=value.length/8;
			Buffer buffer=Buffers.getLocal();
			buffer.writeBytes(value);
			for(int i=0;i<length;i++){
				result.add(buffer.readLong());
			}
			buffer.release();	
		}
		
		return result;
	}
	public static byte[] get(Set<Long> values){
		if(values==null) return null;
		Buffer buffer=Buffers.getLocal();
		for(Long value:values){
			buffer.writeLong(value);
		}
		byte[] result= buffer.getBytes();
		buffer.release();
		return result;
	}
	public static byte[] booleanBytes(boolean value) {
		return value?new byte[]{1}:new byte[]{0};
	}
	public static byte[] byteBytes(byte value){
		return new byte[]{value};
	}
	public static int intValue(byte[] value){
		if(value==null) return 0;
		Buffer buffer=Buffers.getLocal();
		buffer.writeBytes(value);
		int result =buffer.readInt();
		buffer.release();
		return result;
	}
	public static byte[] intBytes(int value){
		Buffer buffer=Buffers.getLocal();
		buffer.writeInt(value);
		byte[] result= buffer.getBytes();
		buffer.release();
		return result;
	}
	
	 
	 //////////////////////////////////////////////////////////////
	 ////////////////////////////////////////////////////////////////
	 ///////////////////////Cache Helper Methods//////////////////////
	 //////////////////////////////////////////////////////////////
	 ////////////////////////////////////////////////////////////////
	 //////////////////////////////////////////////////////////////////
//	public static Object getValue(Class<?> type, byte[] value,Buffer buffer) {
//		buffer.writeBytes(value);
//		if(type==boolean.class) return value[0]==1?Boolean.TRUE:Boolean.FALSE;
//		else if(type==byte.class) return value[0];
//		else if(type==short.class) return buffer.readShort();
//		else if(type==int.class) return buffer.readInt();
//		else if(type==long.class) return buffer.readLong();
//		else if(type==float.class) return buffer.readFloat();
//		else if(type==double.class) return buffer.readDouble();
//		else if(type==String.class) return buffer.readUTF();
//		else if(IBinary.class.isAssignableFrom(type)){
//			return getBinaryValue(type,value,buffer);
//		}
//		else if(type==boolean[].class) return booleanArray(value);
//		else if(type==byte[].class) return value;
//		else if(type==short[].class) return shortArray(value);
//		else if(type==int[].class) return intArray(value);
//		else if(type==long[].class) return longArray(value);
//		else if(type==float[].class) return floatArray(value);
//		else if(type==double[].class) return doubleArray(value);
//		else if(type==String[].class) return stringArray(value);
//		
//		return null;
//	}
//	private static Object getBinaryValue(Class<?> type, byte[] value,Buffer buffer) {
//		IBinary result=null;
//		try {
//			if(value==null||value.length==0) return null;
//			result=(IBinary)type.newInstance();
//			buffer.writeBytes(value);
//			result.fromBuffer(buffer);
//		} catch (Exception e) {
//			LogUtil.error(e);
//		}
//		return result;
//	
//	}
//	public static byte[] getBytes(Class<?> type, Object value,Buffer buffer) {
//		if(type==boolean.class) return new byte[]{((Boolean)value).booleanValue()?(byte)1:(byte)0};
//		else if(type==byte.class) return new byte[]{(Byte)value};
//		else if(type==short.class){
//			buffer.writeShort((Integer)value);
//			return buffer.getBytes();
//		}
//		else if(type==int.class){
//			buffer.writeInt((Integer)value);
//			return buffer.getBytes();
//		}
//		else if(type==long.class){
//			buffer.writeLong((Long)value);
//			return buffer.getBytes();
//		}
//		else if(type==float.class){
//			buffer.writeFloat((Float)value);
//			return buffer.getBytes();
//		}
//		else if(type==double.class){
//			buffer.writeDouble((Double)value);
//			return buffer.getBytes();
//		}
//		else if(type==String.class){
//			buffer.writeUTF((String)value);
//			return buffer.getBytes(); 
//		}
//		else if(IBinary.class.isAssignableFrom(type)){
//			((IBinary)value).toBuffer(buffer);
//			return buffer.getBytes();
//		}
//		else if(type==boolean[].class) return booleanBytes((Boolean)value);
//		else if(type==byte[].class) return (byte[])value;
//		else if(type==short[].class) return get((short[])value);
//		else if(type==int[].class) return get((int[])value);
//		else if(type==long[].class) return get((long[])value);
//		else if(type==float[].class) return get((float[])value);
//		else if(type==double[].class) return get((double[])value);
//		else if(type==String[].class) return get((String[])value);
//		
//		
//		return null;
//	}
//	public static byte[] getMapValueBytes(Class<?> type, Object value,Buffer buffer) {
//		if(type==Boolean.class) return new byte[]{((Boolean)value).booleanValue()?(byte)1:(byte)0};
//		else if(type==Byte.class) return new byte[]{(Byte)value};
//		else if(type==Short.class) {
//			buffer.writeShort((Integer)value);
//			return buffer.getBytes();
//		}
//		else if(type==Integer.class) {
//			buffer.writeInt((Integer)value);
//			return buffer.getBytes();
//		}
//		else if(type==Long.class) {
//			buffer.writeLong((Long)value);
//			return buffer.getBytes();
//		}
//		else if(type==Float.class) {
//			buffer.writeFloat((Float)value);
//			return buffer.getBytes();
//		}
//		else if(type==Double.class){
//			buffer.writeDouble((Double)value);
//			return buffer.getBytes();
//		}
//		else if(type==String.class){
//			buffer.writeUTF((String)value);
//			return buffer.getBytes(); 
//		}
//		else if(IBinary.class.isAssignableFrom(type)) {
//			((IBinary)value).toBuffer(buffer);
//			return buffer.getBytes();
//		}
//		
//		else if(type==boolean[].class) return booleanBytes((Boolean)value);
//		else if(type==byte[].class) return (byte[])value;
//		else if(type==short[].class) return get((short[])value);
//		else if(type==int[].class) return get((int[])value);
//		else if(type==long[].class) return get((long[])value);
//		else if(type==float[].class) return get((float[])value);
//		else if(type==double[].class) return get((double[])value);
//		else if(type==String[].class) return get((String[])value);
//		
//		return null;
//	}
//	public static Object getMapValue(Class<?> type, byte[] value,Buffer buffer) {
//		if(type==Boolean.class) return value[0]==1?Boolean.TRUE:Boolean.FALSE;
//		else if(type==Byte.class) return value[0];
//		else if(type==short.class) return buffer.readShort();
//		else if(type==int.class) return buffer.readInt();
//		else if(type==long.class) return buffer.readLong();
//		else if(type==float.class) return buffer.readFloat();
//		else if(type==double.class) return buffer.readDouble();
//		else if(type==String.class) return buffer.readUTF();
//		else if(IBinary.class.isAssignableFrom(type)){
//			return getBinaryValue(type,value,buffer);
//		}
//		
//		else if(type==boolean[].class) return booleanArray(value);
//		else if(type==byte[].class) return value;
//		else if(type==short[].class) return shortArray(value);
//		else if(type==int[].class) return intArray(value);
//		else if(type==long[].class) return longArray(value);
//		else if(type==float[].class) return floatArray(value);
//		else if(type==double[].class) return doubleArray(value);
//		else if(type==String[].class) return stringArray(value);
//		
//		return value;
//	}
//	public static Object getMapKey(Class<?> keyType, String key) {
//		if(keyType==Boolean.class) return Boolean.valueOf(key);
//		else if(keyType==Byte.class) return Byte.valueOf(key);
//		else if(keyType==Short.class) return Short.valueOf(key);
//		else if(keyType==Integer.class) return Integer.valueOf(key);
//		else if(keyType==Long.class) return Long.valueOf(key);
//		else if(keyType==Float.class) return Float.valueOf(key);
//		else if(keyType==Double.class) return Double.valueOf(key);
//		else if(keyType==String.class) return key;
//		return key;
//	}
}
