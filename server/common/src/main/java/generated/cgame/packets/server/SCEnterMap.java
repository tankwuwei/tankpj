package generated.cgame.packets.server;
import engine.net.*;
import generated.cgame.packets.objects.*;

@SuppressWarnings("unused")
public class SCEnterMap extends CPacket
{
    public SCEnterMap()
    {
        code=5502;
    }
    
    public  int mapid;
    public  int index;
    public  int commander;
    
    public void read(engine.net.NativeBuffer buf)
    {
        mapid=buf.readInt();
        index=buf.readInt();
        commander=buf.readInt();
    }
    
    public void write(engine.net.NativeBuffer buf)
    {
        buf.writeInt(mapid);
        buf.writeInt(index);
        buf.writeInt(commander);
    }
}