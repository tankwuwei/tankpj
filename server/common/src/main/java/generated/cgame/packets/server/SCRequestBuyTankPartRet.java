package generated.cgame.packets.server;
import engine.net.*;
import generated.cgame.packets.objects.*;

@SuppressWarnings("unused")
public class SCRequestBuyTankPartRet extends CPacket
{
    public SCRequestBuyTankPartRet()
    {
        code=5551;
    }
    
    public  int tankid;
    public  int partid;
    public  int ret;
    
    public void read(engine.net.NativeBuffer buf)
    {
        tankid=buf.readInt();
        partid=buf.readInt();
        ret=buf.readInt();
    }
    
    public void write(engine.net.NativeBuffer buf)
    {
        buf.writeInt(tankid);
        buf.writeInt(partid);
        buf.writeInt(ret);
    }
}