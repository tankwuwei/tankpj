package generated.cgame.packets.server;
import engine.net.*;
import generated.cgame.packets.objects.*;

@SuppressWarnings("unused")
public class SCRequireMapRet extends CPacket
{
    public SCRequireMapRet()
    {
        code=5501;
    }
    
    public  short ret;
    
    public void read(engine.net.NativeBuffer buf)
    {
        ret=buf.readShort();
    }
    
    public void write(engine.net.NativeBuffer buf)
    {
        buf.writeShort(ret);
    }
}