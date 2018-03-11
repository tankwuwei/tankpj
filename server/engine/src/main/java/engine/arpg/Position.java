package engine.arpg;

import engine.core.IWritable;
import engine.net.NativeBuffer;

public class Position implements IWritable{
	//TODO change to short
	public float x,y,z;
	public float angle;
	
	public void write(NativeBuffer buffer){
		buffer.writeFloat(x);
		buffer.writeFloat(y);
		buffer.writeFloat(z);
		buffer.writeFloat(angle);
	}
	public void read(NativeBuffer buffer){
		
		x=buffer.readFloat();
		y=buffer.readFloat();
		z=buffer.readFloat();
		angle=buffer.readFloat();
	}
	
	public String toString(){
		return x+","+y+","+z;
	}
	
	public Object clone(){
		Position p = new Position();
		p.x = x;
		p.y = y;
		p.z = z;
		p.angle = angle;
		return p;
	}
}
