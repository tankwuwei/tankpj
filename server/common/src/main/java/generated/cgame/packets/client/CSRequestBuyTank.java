package generated.cgame.packets.client;
import engine.net.*;
import generated.cgame.packets.objects.*;

@SuppressWarnings("unused")
public class CSRequestBuyTank extends CPacket
{
    public CSRequestBuyTank()
    {
        code=5552;
    }
    
    public  int tankid;
    
    public void read(engine.net.NativeBuffer buf)
    {
        tankid=buf.readInt();
    }
    
    public void write(engine.net.NativeBuffer buf)
    {
        buf.writeInt(tankid);
    }
}