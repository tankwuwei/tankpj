package generated.cgame.packets.objects;
import engine.net.*;
import generated.cgame.packets.objects.*;

@SuppressWarnings("unused")
public class RequestLoginSvrInfo extends CValue
{
    
    public  int zoneid;
    public  String ip;
    public  int port;
    public  String svrname;
    
    public void read(engine.net.NativeBuffer buf)
    {
        zoneid=buf.readInt();
        ip=buf.readUTF();
        port=buf.readInt();
        svrname=buf.readUTF();
    }
    
    public void write(engine.net.NativeBuffer buf)
    {
        buf.writeInt(zoneid);
        buf.writeUTF(ip);
        buf.writeInt(port);
        buf.writeUTF(svrname);
    }
}