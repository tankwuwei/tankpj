package generated.cgame.packets.server;
import engine.net.*;
import generated.cgame.packets.objects.*;

@SuppressWarnings("unused")
public class AgentLoginGameSvrRet extends CPacket
{
    public AgentLoginGameSvrRet()
    {
        code=31013;
    }
    
    public  int ret;
    public  int index;
    
    public void read(engine.net.NativeBuffer buf)
    {
        ret=buf.readInt();
        index=buf.readInt();
    }
    
    public void write(engine.net.NativeBuffer buf)
    {
        buf.writeInt(ret);
        buf.writeInt(index);
    }
}