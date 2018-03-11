package generated.cgame.packets.client;
import engine.net.*;
import generated.cgame.packets.objects.*;

@SuppressWarnings("unused")
public class UGPlayerJoinFight extends CPacket
{
    public UGPlayerJoinFight()
    {
        code=16012;
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