package generated.cgame.packets.server;
import engine.net.*;
import generated.cgame.packets.objects.*;

@SuppressWarnings("unused")
public class ScUseTankRet extends CPacket
{
    public ScUseTankRet()
    {
        code=5326;
    }
    
    public  int ret;
    public  int tankid;
    
    public void read(engine.net.NativeBuffer buf)
    {
        ret=buf.readInt();
        tankid=buf.readInt();
    }
    
    public void write(engine.net.NativeBuffer buf)
    {
        buf.writeInt(ret);
        buf.writeInt(tankid);
    }
}