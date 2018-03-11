package generated.cgame.packets.objects;
import engine.net.*;
import generated.cgame.packets.objects.*;

@SuppressWarnings("unused")
public class TankPartInfo extends CValue
{
    
    public  int tankid;
    public  int partid;
    public  int state;
    
    public void read(engine.net.NativeBuffer buf)
    {
        tankid=buf.readInt();
        partid=buf.readInt();
        state=buf.readInt();
    }
    
    public void write(engine.net.NativeBuffer buf)
    {
        buf.writeInt(tankid);
        buf.writeInt(partid);
        buf.writeInt(state);
    }
}