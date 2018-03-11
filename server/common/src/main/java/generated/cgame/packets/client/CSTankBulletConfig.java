package generated.cgame.packets.client;
import engine.net.*;
import generated.cgame.packets.objects.*;

@SuppressWarnings("unused")
public class CSTankBulletConfig extends CPacket
{
    public CSTankBulletConfig()
    {
        code=6010;
    }
    
    public  int tankid;
    public  int bulletid;
    public  int bulletType;
    public  int bulletCounts;
    
    public void read(engine.net.NativeBuffer buf)
    {
        tankid=buf.readInt();
        bulletid=buf.readInt();
        bulletType=buf.readInt();
        bulletCounts=buf.readInt();
    }
    
    public void write(engine.net.NativeBuffer buf)
    {
        buf.writeInt(tankid);
        buf.writeInt(bulletid);
        buf.writeInt(bulletType);
        buf.writeInt(bulletCounts);
    }
}