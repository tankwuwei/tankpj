package generated.cgame.packets.objects;
import engine.net.*;
import generated.cgame.packets.objects.*;

@SuppressWarnings("unused")
public class Pos extends CValue
{
    
    public  int x;
    public  int y;
    public  int z;
    
    public void read(engine.net.NativeBuffer buf)
    {
        x=buf.readInt();
        y=buf.readInt();
        z=buf.readInt();
    }
    
    public void write(engine.net.NativeBuffer buf)
    {
        buf.writeInt(x);
        buf.writeInt(y);
        buf.writeInt(z);
    }
}