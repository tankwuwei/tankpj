package generated.cgame.packets.server;
import engine.net.*;
import generated.cgame.packets.objects.*;

@SuppressWarnings("unused")
public class SCTeamNotify extends CPacket
{
    public SCTeamNotify()
    {
        code=7026;
    }
    
    public  short retcode;
    public  long playerid;
    public  String nickname;
    
    public void read(engine.net.NativeBuffer buf)
    {
        retcode=buf.readShort();
        playerid=buf.readLong();
        nickname=buf.readUTF();
    }
    
    public void write(engine.net.NativeBuffer buf)
    {
        buf.writeShort(retcode);
        buf.writeLong(playerid);
        buf.writeUTF(nickname);
    }
}