package generated.cgame.packets.server;
import engine.net.*;
import generated.cgame.packets.objects.*;

@SuppressWarnings("unused")
public class GCIPBlacklist extends CPacket
{
    public GCIPBlacklist()
    {
        code=31022;
    }
    
    public  int[] ips;
    
    public void read(engine.net.NativeBuffer buf)
    {
        ips=buf.readIntArray();
    }
    
    public void write(engine.net.NativeBuffer buf)
    {
        buf.writeArray(ips);
    }
}