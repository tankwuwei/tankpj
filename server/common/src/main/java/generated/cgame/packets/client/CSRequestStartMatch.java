package generated.cgame.packets.client;
import engine.net.*;
import generated.cgame.packets.objects.*;

@SuppressWarnings("unused")
public class CSRequestStartMatch extends CPacket
{
    public CSRequestStartMatch()
    {
        code=5554;
    }
    
    public  short matchtype;
    public  int zoneid;
    
    public void read(engine.net.NativeBuffer buf)
    {
        matchtype=buf.readShort();
        zoneid=buf.readInt();
    }
    
    public void write(engine.net.NativeBuffer buf)
    {
        buf.writeShort(matchtype);
        buf.writeInt(zoneid);
    }
}