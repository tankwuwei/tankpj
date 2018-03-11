package generated.cgame.packets.objects;
import engine.net.*;
import generated.cgame.packets.objects.*;

@SuppressWarnings("unused")
public class TeamPlayer extends CValue
{
    
    public  short status;
    public  short matchstatus;
    public  long playerid;
    public  String nickname;
    public  int[] curdecalid;
    
    public void read(engine.net.NativeBuffer buf)
    {
        status=buf.readShort();
        matchstatus=buf.readShort();
        playerid=buf.readLong();
        nickname=buf.readUTF();
        curdecalid=buf.readIntArray();
    }
    
    public void write(engine.net.NativeBuffer buf)
    {
        buf.writeShort(status);
        buf.writeShort(matchstatus);
        buf.writeLong(playerid);
        buf.writeUTF(nickname);
        buf.writeArray(curdecalid);
    }
}