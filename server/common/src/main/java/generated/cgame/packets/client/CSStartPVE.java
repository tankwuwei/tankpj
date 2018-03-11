package generated.cgame.packets.client;
import engine.net.*;
import generated.cgame.packets.objects.*;

@SuppressWarnings("unused")
public class CSStartPVE extends CPacket
{
    public CSStartPVE()
    {
        code=5400;
    }
    
    public  int mapid;
    public  int commander;
    
    public void read(engine.net.NativeBuffer buf)
    {
        mapid=buf.readInt();
        commander=buf.readInt();
    }
    
    public void write(engine.net.NativeBuffer buf)
    {
        buf.writeInt(mapid);
        buf.writeInt(commander);
    }
}