package generated.cgame.packets.client;
import engine.net.*;
import generated.cgame.packets.objects.*;

@SuppressWarnings("unused")
public class CSUseItem extends CPacket
{
    public CSUseItem()
    {
        code=7003;
    }
    
    public  long id;
    public  short pos;
    
    public void read(engine.net.NativeBuffer buf)
    {
        id=buf.readLong();
        pos=buf.readShort();
    }
    
    public void write(engine.net.NativeBuffer buf)
    {
        buf.writeLong(id);
        buf.writeShort(pos);
    }
}