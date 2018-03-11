package generated.cgame.packets.server;
import engine.net.*;
import generated.cgame.packets.objects.*;

@SuppressWarnings("unused")
public class RequestBuySaleTankModelRet extends CPacket
{
    public RequestBuySaleTankModelRet()
    {
        code=5541;
    }
    
    public  int modelid;
    public  int retcode;
    
    public void read(engine.net.NativeBuffer buf)
    {
        modelid=buf.readInt();
        retcode=buf.readInt();
    }
    
    public void write(engine.net.NativeBuffer buf)
    {
        buf.writeInt(modelid);
        buf.writeInt(retcode);
    }
}