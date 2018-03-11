package generated.cgame.packets.server;
import engine.net.*;
import generated.cgame.packets.objects.*;

@SuppressWarnings("unused")
public class SCEnterFighter extends CPacket
{
    public SCEnterFighter()
    {
        code=6003;
    }
    
    public  String ip;
    public  short port;
    public  String token;
    
    public void read(engine.net.NativeBuffer buf)
    {
        ip=buf.readUTF();
        port=buf.readShort();
        token=buf.readUTF();
    }
    
    public void write(engine.net.NativeBuffer buf)
    {
        buf.writeUTF(ip);
        buf.writeShort(port);
        buf.writeUTF(token);
    }
}