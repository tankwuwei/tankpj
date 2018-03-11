package generated.cgame.packets.objects;
import engine.net.*;
import generated.cgame.packets.objects.*;

@SuppressWarnings("unused")
public class Buff extends CValue
{
    
    public  int id;
    public  int buffid;
    public  int time;
    
    public void read(engine.net.NativeBuffer buf)
    {
        id=buf.readInt();
        buffid=buf.readInt();
        time=buf.readInt();
    }
    
    public void write(engine.net.NativeBuffer buf)
    {
        buf.writeInt(id);
        buf.writeInt(buffid);
        buf.writeInt(time);
    }
}