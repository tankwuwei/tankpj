package generated.cgame.packets.client;
import engine.net.*;
import generated.cgame.packets.objects.*;

@SuppressWarnings("unused")
public class CSLogin extends CPacket
{
    public CSLogin()
    {
        code=5300;
    }
    
    public  String account;
    public  String password;
    public  String version;
    public  int time;
    public  String encrpt;
    
    public void read(engine.net.NativeBuffer buf)
    {
        account=buf.readUTF();
        password=buf.readUTF();
        version=buf.readUTF();
        time=buf.readInt();
        encrpt=buf.readUTF();
    }
    
    public void write(engine.net.NativeBuffer buf)
    {
        buf.writeUTF(account);
        buf.writeUTF(password);
        buf.writeUTF(version);
        buf.writeInt(time);
        buf.writeUTF(encrpt);
    }
}