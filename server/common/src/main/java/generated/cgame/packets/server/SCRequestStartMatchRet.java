package generated.cgame.packets.server;
import engine.net.*;
import generated.cgame.packets.objects.*;

@SuppressWarnings("unused")
public class SCRequestStartMatchRet extends CPacket
{
    public SCRequestStartMatchRet()
    {
        code=5555;
    }
    
    public  int ret;
    public  int locksecs;
    
    public void read(engine.net.NativeBuffer buf)
    {
        ret=buf.readInt();
        locksecs=buf.readInt();
    }
    
    public void write(engine.net.NativeBuffer buf)
    {
        buf.writeInt(ret);
        buf.writeInt(locksecs);
    }
}