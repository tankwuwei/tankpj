package generated.cgame.packets.client;
import engine.net.*;
import generated.cgame.packets.objects.*;

@SuppressWarnings("unused")
public class CSTeamInvite extends CPacket
{
    public CSTeamInvite()
    {
        code=7025;
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