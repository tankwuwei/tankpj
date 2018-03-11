package generated.cgame.packets.server;
import engine.net.*;
import generated.cgame.packets.objects.*;

@SuppressWarnings("unused")
public class ServerType extends CPacket
{
    public ServerType()
    {
        code=31017;
    }
    
    public  short type;
    
    public void read(engine.net.NativeBuffer buf)
    {
        type=buf.readShort();
    }
    
    public void write(engine.net.NativeBuffer buf)
    {
        buf.writeShort(type);
    }
}