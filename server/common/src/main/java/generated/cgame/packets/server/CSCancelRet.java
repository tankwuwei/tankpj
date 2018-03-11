package generated.cgame.packets.server;
import engine.net.*;
import generated.cgame.packets.objects.*;

@SuppressWarnings("unused")
public class CSCancelRet extends CPacket
{
    public CSCancelRet()
    {
        code=5509;
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