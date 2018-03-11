package generated.cgame.packets.server;
import engine.net.*;
import generated.cgame.packets.objects.*;

@SuppressWarnings("unused")
public class RequestDevelopTankModelRet extends CPacket
{
    public RequestDevelopTankModelRet()
    {
        code=5544;
    }
    
    public  int modelid;
    public  int ret;
    
    public void read(engine.net.NativeBuffer buf)
    {
        modelid=buf.readInt();
        ret=buf.readInt();
    }
    
    public void write(engine.net.NativeBuffer buf)
    {
        buf.writeInt(modelid);
        buf.writeInt(ret);
    }
}