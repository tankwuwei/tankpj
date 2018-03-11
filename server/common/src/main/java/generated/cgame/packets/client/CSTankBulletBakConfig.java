package generated.cgame.packets.client;
import engine.net.*;
import generated.cgame.packets.objects.*;

@SuppressWarnings("unused")
public class CSTankBulletBakConfig extends CPacket
{
    public CSTankBulletBakConfig()
    {
        code=6012;
    }
    
    public  int tankid;
    public  int bauto;
    
    public void read(engine.net.NativeBuffer buf)
    {
        tankid=buf.readInt();
        bauto=buf.readInt();
    }
    
    public void write(engine.net.NativeBuffer buf)
    {
        buf.writeInt(tankid);
        buf.writeInt(bauto);
    }
}