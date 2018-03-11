package generated.cgame.packets.server;
import engine.net.*;
import generated.cgame.packets.objects.*;

@SuppressWarnings("unused")
public class SCRequestAddTankSpaceRet extends CPacket
{
    public SCRequestAddTankSpaceRet()
    {
        code=5547;
    }
    
    public  int ret;
    
    public void read(engine.net.NativeBuffer buf)
    {
        ret=buf.readInt();
    }
    
    public void write(engine.net.NativeBuffer buf)
    {
        buf.writeInt(ret);
    }
}