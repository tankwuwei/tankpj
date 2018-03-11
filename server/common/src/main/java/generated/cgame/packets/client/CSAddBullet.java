package generated.cgame.packets.client;
import engine.net.*;
import generated.cgame.packets.objects.*;

@SuppressWarnings("unused")
public class CSAddBullet extends CPacket
{
    public CSAddBullet()
    {
        code=6008;
    }
    
    public  int bulletid;
    public  int addbulletCounts;
    public  int buytype;
    
    public void read(engine.net.NativeBuffer buf)
    {
        bulletid=buf.readInt();
        addbulletCounts=buf.readInt();
        buytype=buf.readInt();
    }
    
    public void write(engine.net.NativeBuffer buf)
    {
        buf.writeInt(bulletid);
        buf.writeInt(addbulletCounts);
        buf.writeInt(buytype);
    }
}