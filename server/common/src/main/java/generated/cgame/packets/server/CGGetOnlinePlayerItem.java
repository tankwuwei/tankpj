package generated.cgame.packets.server;
import engine.net.*;
import generated.cgame.packets.objects.*;

@SuppressWarnings("unused")
public class CGGetOnlinePlayerItem extends CPacket
{
    public CGGetOnlinePlayerItem()
    {
        code=31019;
    }
    
    public  long playerid;
    
    public void read(engine.net.NativeBuffer buf)
    {
        playerid=buf.readLong();
    }
    
    public void write(engine.net.NativeBuffer buf)
    {
        buf.writeLong(playerid);
    }
}