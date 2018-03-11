package generated.cgame.packets.server;
import engine.net.*;
import generated.cgame.packets.objects.*;

@SuppressWarnings("unused")
public class SCRequestBuyTankRet extends CPacket
{
    public SCRequestBuyTankRet()
    {
        code=5553;
    }
    
    public  int tankid;
    public  int ret;
    
    public void read(engine.net.NativeBuffer buf)
    {
        tankid=buf.readInt();
        ret=buf.readInt();
    }
    
    public void write(engine.net.NativeBuffer buf)
    {
        buf.writeInt(tankid);
        buf.writeInt(ret);
    }
}