package engine.net;

import engine.core.IReadable;
import engine.core.IWritable;


public class CValue implements Cloneable, IWritable, IReadable{
	
	public CValue clone() throws CloneNotSupportedException{return (CValue)super.clone();}
	
	public void read(NativeBuffer buf){}
	
	public void write(NativeBuffer buf){}
}
