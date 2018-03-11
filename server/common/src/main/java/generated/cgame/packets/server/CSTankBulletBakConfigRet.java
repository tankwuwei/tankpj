package generated.cgame.packets.server;
import engine.net.*;
import generated.cgame.packets.objects.*;

@SuppressWarnings("unused")
public class CSTankBulletBakConfigRet extends CPacket
{
    public CSTankBulletBakConfigRet()
    {
        code=6013;
    }
    
    public  int ret;
    public  int tankid;
    public  int bauto;
    
    public void read(engine.net.NativeBuffer buf)
    {
        ret=buf.readInt();
        tankid=buf.readInt();
        bauto=buf.readInt();
    }
    
    public void write(engine.net.NativeBuffer buf)
    {
        buf.writeInt(ret);
        buf.writeInt(tankid);
        buf.writeInt(bauto);
    }
}