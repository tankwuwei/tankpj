package generated.cgame.packets.client;
import engine.net.*;
import generated.cgame.packets.objects.*;

@SuppressWarnings("unused")
public class CSteamLogin extends CPacket
{
    public CSteamLogin()
    {
        code=504;
    }
    
    public  String ticket;
    public  long steamid;
    public  String version;
    public  String encrpt;
    
    public void read(engine.net.NativeBuffer buf)
    {
        ticket=buf.readUTF();
        steamid=buf.readLong();
        version=buf.readUTF();
        encrpt=buf.readUTF();
    }
    
    public void write(engine.net.NativeBuffer buf)
    {
        buf.writeUTF(ticket);
        buf.writeLong(steamid);
        buf.writeUTF(version);
        buf.writeUTF(encrpt);
    }
}