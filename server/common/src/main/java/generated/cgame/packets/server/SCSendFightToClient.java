package generated.cgame.packets.server;
import engine.net.*;
import generated.cgame.packets.objects.*;

@SuppressWarnings("unused")
public class SCSendFightToClient extends CPacket
{
    public SCSendFightToClient()
    {
        code=16008;
    }
    
    public  String ip;
    public  short port;
    
    public void read(engine.net.NativeBuffer buf)
    {
        ip=buf.readUTF();
        port=buf.readShort();
    }
    
    public void write(engine.net.NativeBuffer buf)
    {
        buf.writeUTF(ip);
        buf.writeShort(port);
    }
}