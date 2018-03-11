package generated.cgame.packets.server;
import engine.net.*;
import generated.cgame.packets.objects.*;

@SuppressWarnings("unused")
public class SCSearchPlayer extends CPacket
{
    public SCSearchPlayer()
    {
        code=7023;
    }
    
    public  short errorcode;
    public  String nickname;
    public  long playerid;
    public  short isinvite;
    public  short status;
    
    public void read(engine.net.NativeBuffer buf)
    {
        errorcode=buf.readShort();
        nickname=buf.readUTF();
        playerid=buf.readLong();
        isinvite=buf.readShort();
        status=buf.readShort();
    }
    
    public void write(engine.net.NativeBuffer buf)
    {
        buf.writeShort(errorcode);
        buf.writeUTF(nickname);
        buf.writeLong(playerid);
        buf.writeShort(isinvite);
        buf.writeShort(status);
    }
}