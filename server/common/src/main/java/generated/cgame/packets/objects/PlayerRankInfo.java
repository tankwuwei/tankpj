package generated.cgame.packets.objects;
import engine.net.*;
import generated.cgame.packets.objects.*;

@SuppressWarnings("unused")
public class PlayerRankInfo extends CValue
{
    
    public  long playerid;
    public  String nickname;
    public  int wins;
    public  int winratio;
    
    public void read(engine.net.NativeBuffer buf)
    {
        playerid=buf.readLong();
        nickname=buf.readUTF();
        wins=buf.readInt();
        winratio=buf.readInt();
    }
    
    public void write(engine.net.NativeBuffer buf)
    {
        buf.writeLong(playerid);
        buf.writeUTF(nickname);
        buf.writeInt(wins);
        buf.writeInt(winratio);
    }
}