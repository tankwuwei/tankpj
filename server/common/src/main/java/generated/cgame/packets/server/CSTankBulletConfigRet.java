package generated.cgame.packets.server;
import engine.net.*;
import generated.cgame.packets.objects.*;

@SuppressWarnings("unused")
public class CSTankBulletConfigRet extends CPacket
{
    public CSTankBulletConfigRet()
    {
        code=6011;
    }
    
    public  int ret;
    public  int tankid;
    public  int bulletid;
    public  int bulletType;
    public  int allbulletCounts;
    
    public void read(engine.net.NativeBuffer buf)
    {
        ret=buf.readInt();
        tankid=buf.readInt();
        bulletid=buf.readInt();
        bulletType=buf.readInt();
        allbulletCounts=buf.readInt();
    }
    
    public void write(engine.net.NativeBuffer buf)
    {
        buf.writeInt(ret);
        buf.writeInt(tankid);
        buf.writeInt(bulletid);
        buf.writeInt(bulletType);
        buf.writeInt(allbulletCounts);
    }
}