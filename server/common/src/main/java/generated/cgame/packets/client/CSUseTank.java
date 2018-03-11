package generated.cgame.packets.client;
import engine.net.*;
import generated.cgame.packets.objects.*;

@SuppressWarnings("unused")
public class CSUseTank extends CPacket
{
    public CSUseTank()
    {
        code=5325;
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