package generated.cgame.packets.server;
import engine.net.*;
import generated.cgame.packets.objects.*;

@SuppressWarnings("unused")
public class SCTankTechChange extends CPacket
{
    public SCTankTechChange()
    {
        code=6024;
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