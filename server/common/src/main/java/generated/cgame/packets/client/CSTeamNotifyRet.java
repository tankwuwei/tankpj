package generated.cgame.packets.client;
import engine.net.*;
import generated.cgame.packets.objects.*;

@SuppressWarnings("unused")
public class CSTeamNotifyRet extends CPacket
{
    public CSTeamNotifyRet()
    {
        code=7027;
    }
    
    public  long playerid;
    public  short retcode;
    
    public void read(engine.net.NativeBuffer buf)
    {
        playerid=buf.readLong();
        retcode=buf.readShort();
    }
    
    public void write(engine.net.NativeBuffer buf)
    {
        buf.writeLong(playerid);
        buf.writeShort(retcode);
    }
}