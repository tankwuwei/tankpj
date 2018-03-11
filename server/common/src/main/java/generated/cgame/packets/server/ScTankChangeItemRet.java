package generated.cgame.packets.server;
import engine.net.*;
import generated.cgame.packets.objects.*;

@SuppressWarnings("unused")
public class ScTankChangeItemRet extends CPacket
{
    public ScTankChangeItemRet()
    {
        code=6017;
    }
    
    public  int ret;
    public  int tankid;
    public  int itemindex;
    public  int itemid;
    public  int itemcount;
    
    public void read(engine.net.NativeBuffer buf)
    {
        ret=buf.readInt();
        tankid=buf.readInt();
        itemindex=buf.readInt();
        itemid=buf.readInt();
        itemcount=buf.readInt();
    }
    
    public void write(engine.net.NativeBuffer buf)
    {
        buf.writeInt(ret);
        buf.writeInt(tankid);
        buf.writeInt(itemindex);
        buf.writeInt(itemid);
        buf.writeInt(itemcount);
    }
}