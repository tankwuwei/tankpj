package generated.cgame.packets.client;
import engine.net.*;
import generated.cgame.packets.objects.*;

@SuppressWarnings("unused")
public class CSRequestDevelopTankPart extends CPacket
{
    public CSRequestDevelopTankPart()
    {
        code=5548;
    }
    
    public  int tankid;
    public  int partid;
    
    public void read(engine.net.NativeBuffer buf)
    {
        tankid=buf.readInt();
        partid=buf.readInt();
    }
    
    public void write(engine.net.NativeBuffer buf)
    {
        buf.writeInt(tankid);
        buf.writeInt(partid);
    }
}