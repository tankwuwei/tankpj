package generated.cgame.packets.server;
import engine.net.*;
import generated.cgame.packets.objects.*;

@SuppressWarnings("unused")
public class SCTankFightLockInfo extends CPacket
{
    public SCTankFightLockInfo()
    {
        code=6033;
    }
    
    public  int tankid;
    public  int secs;
    
    public void read(engine.net.NativeBuffer buf)
    {
        tankid=buf.readInt();
        secs=buf.readInt();
    }
    
    public void write(engine.net.NativeBuffer buf)
    {
        buf.writeInt(tankid);
        buf.writeInt(secs);
    }
}