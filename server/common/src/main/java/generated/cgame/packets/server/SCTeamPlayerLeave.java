package generated.cgame.packets.server;
import engine.net.*;
import generated.cgame.packets.objects.*;

@SuppressWarnings("unused")
public class SCTeamPlayerLeave extends CPacket
{
    public SCTeamPlayerLeave()
    {
        code=7031;
    }
    
    public  long playerid;
    public  String nickname;
    
    public void read(engine.net.NativeBuffer buf)
    {
        playerid=buf.readLong();
        nickname=buf.readUTF();
    }
    
    public void write(engine.net.NativeBuffer buf)
    {
        buf.writeLong(playerid);
        buf.writeUTF(nickname);
    }
}