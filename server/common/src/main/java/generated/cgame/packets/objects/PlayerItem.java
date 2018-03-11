package generated.cgame.packets.objects;
import engine.net.*;
import generated.cgame.packets.objects.*;

@SuppressWarnings("unused")
public class PlayerItem extends CValue
{
    
    public  long id;
    public  int propid;
    public  int deadline;
    public  short pos;
    
    public void read(engine.net.NativeBuffer buf)
    {
        id=buf.readLong();
        propid=buf.readInt();
        deadline=buf.readInt();
        pos=buf.readShort();
    }
    
    public void write(engine.net.NativeBuffer buf)
    {
        buf.writeLong(id);
        buf.writeInt(propid);
        buf.writeInt(deadline);
        buf.writeShort(pos);
    }
}