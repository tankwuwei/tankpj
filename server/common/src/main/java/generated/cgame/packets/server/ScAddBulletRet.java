package generated.cgame.packets.server;
import engine.net.*;
import generated.cgame.packets.objects.*;

@SuppressWarnings("unused")
public class ScAddBulletRet extends CPacket
{
    public ScAddBulletRet()
    {
        code=6009;
    }
    
    public  int ret;
    public  int bulletid;
    public  int allbulletCounts;
    
    public void read(engine.net.NativeBuffer buf)
    {
        ret=buf.readInt();
        bulletid=buf.readInt();
        allbulletCounts=buf.readInt();
    }
    
    public void write(engine.net.NativeBuffer buf)
    {
        buf.writeInt(ret);
        buf.writeInt(bulletid);
        buf.writeInt(allbulletCounts);
    }
}