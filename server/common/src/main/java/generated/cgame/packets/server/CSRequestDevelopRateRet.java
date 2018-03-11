package generated.cgame.packets.server;
import engine.net.*;
import generated.cgame.packets.objects.*;

@SuppressWarnings("unused")
public class CSRequestDevelopRateRet extends CPacket
{
    public CSRequestDevelopRateRet()
    {
        code=6029;
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