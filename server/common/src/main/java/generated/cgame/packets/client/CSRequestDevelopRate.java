package generated.cgame.packets.client;
import engine.net.*;
import generated.cgame.packets.objects.*;

@SuppressWarnings("unused")
public class CSRequestDevelopRate extends CPacket
{
    public CSRequestDevelopRate()
    {
        code=6028;
    }
    
    public  int tankid;
    public  int value;
    
    public void read(engine.net.NativeBuffer buf)
    {
        tankid=buf.readInt();
        value=buf.readInt();
    }
    
    public void write(engine.net.NativeBuffer buf)
    {
        buf.writeInt(tankid);
        buf.writeInt(value);
    }
}