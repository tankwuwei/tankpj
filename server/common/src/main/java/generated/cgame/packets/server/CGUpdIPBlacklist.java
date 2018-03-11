package generated.cgame.packets.server;
import engine.net.*;
import generated.cgame.packets.objects.*;

@SuppressWarnings("unused")
public class CGUpdIPBlacklist extends CPacket
{
    public CGUpdIPBlacklist()
    {
        code=31023;
    }
    
    public  short oper;
    public  int[] ips;
    
    public void read(engine.net.NativeBuffer buf)
    {
        oper=buf.readShort();
        ips=buf.readIntArray();
    }
    
    public void write(engine.net.NativeBuffer buf)
    {
        buf.writeShort(oper);
        buf.writeArray(ips);
    }
}