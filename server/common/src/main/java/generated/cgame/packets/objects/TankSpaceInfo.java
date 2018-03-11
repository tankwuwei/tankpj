package generated.cgame.packets.objects;
import engine.net.*;
import generated.cgame.packets.objects.*;

@SuppressWarnings("unused")
public class TankSpaceInfo extends CValue
{
    
    public  int spaceid;
    public  int tankmodelid;
    
    public void read(engine.net.NativeBuffer buf)
    {
        spaceid=buf.readInt();
        tankmodelid=buf.readInt();
    }
    
    public void write(engine.net.NativeBuffer buf)
    {
        buf.writeInt(spaceid);
        buf.writeInt(tankmodelid);
    }
}