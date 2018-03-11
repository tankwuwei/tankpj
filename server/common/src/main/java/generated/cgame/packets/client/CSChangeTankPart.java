package generated.cgame.packets.client;
import engine.net.*;
import generated.cgame.packets.objects.*;

@SuppressWarnings("unused")
public class CSChangeTankPart extends CPacket
{
    public CSChangeTankPart()
    {
        code=6006;
    }
    
    public  int tankid;
    public  int partid;
    public  int index;
    
    public void read(engine.net.NativeBuffer buf)
    {
        tankid=buf.readInt();
        partid=buf.readInt();
        index=buf.readInt();
    }
    
    public void write(engine.net.NativeBuffer buf)
    {
        buf.writeInt(tankid);
        buf.writeInt(partid);
        buf.writeInt(index);
    }
}