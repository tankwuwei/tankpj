package generated.cgame.packets.objects;
import engine.net.*;
import generated.cgame.packets.objects.*;

@SuppressWarnings("unused")
public class KeyboardData extends CValue
{
    
    public  int propid;
    public  int count;
    
    public void read(engine.net.NativeBuffer buf)
    {
        propid=buf.readInt();
        count=buf.readInt();
    }
    
    public void write(engine.net.NativeBuffer buf)
    {
        buf.writeInt(propid);
        buf.writeInt(count);
    }
}