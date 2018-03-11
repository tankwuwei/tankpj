package generated.cgame.packets.server;
import engine.net.*;
import generated.cgame.packets.objects.*;

@SuppressWarnings("unused")
public class SCMaxUsedTank extends CPacket
{
    public SCMaxUsedTank()
    {
        code=6030;
    }
    
    public  int[] tankid;
    public  int[] count;
    
    public void read(engine.net.NativeBuffer buf)
    {
        tankid=buf.readIntArray();
        count=buf.readIntArray();
    }
    
    public void write(engine.net.NativeBuffer buf)
    {
        buf.writeArray(tankid);
        buf.writeArray(count);
    }
}