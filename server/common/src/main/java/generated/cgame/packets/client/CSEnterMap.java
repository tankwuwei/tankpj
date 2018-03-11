package generated.cgame.packets.client;
import engine.net.*;
import generated.cgame.packets.objects.*;

@SuppressWarnings("unused")
public class CSEnterMap extends CPacket
{
    public CSEnterMap()
    {
        code=5503;
    }
    
    public  int mapid;
    
    public void read(engine.net.NativeBuffer buf)
    {
        mapid=buf.readInt();
    }
    
    public void write(engine.net.NativeBuffer buf)
    {
        buf.writeInt(mapid);
    }
}