package generated.cgame.packets.server;
import engine.net.*;
import generated.cgame.packets.objects.*;

@SuppressWarnings("unused")
public class SCMaps extends CPacket
{
    public SCMaps()
    {
        code=5404;
    }
    
    public  int[] mapid;
    
    public void read(engine.net.NativeBuffer buf)
    {
        mapid=buf.readIntArray();
    }
    
    public void write(engine.net.NativeBuffer buf)
    {
        buf.writeArray(mapid);
    }
}