package generated.cgame.packets.client;
import engine.net.*;
import generated.cgame.packets.objects.*;

@SuppressWarnings("unused")
public class CLogin extends CPacket
{
    public CLogin()
    {
        code=402;
    }
    
    public  String appid;
    public  String uid;
    public  String version;
    public  int time;
    public  String encrpt;
    
    public void read(engine.net.NativeBuffer buf)
    {
        appid=buf.readUTF();
        uid=buf.readUTF();
        version=buf.readUTF();
        time=buf.readInt();
        encrpt=buf.readUTF();
    }
    
    public void write(engine.net.NativeBuffer buf)
    {
        buf.writeUTF(appid);
        buf.writeUTF(uid);
        buf.writeUTF(version);
        buf.writeInt(time);
        buf.writeUTF(encrpt);
    }
}