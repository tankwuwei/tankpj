package generated.cgame.packets.client;
import engine.net.*;
import generated.cgame.packets.objects.*;

@SuppressWarnings("unused")
public class CSTankChangeItem extends CPacket
{
    public CSTankChangeItem()
    {
        code=6016;
    }
    
    public  int tankid;
    public  int itemindex;
    public  int itemid;
    public  int itemcount;
    
    public void read(engine.net.NativeBuffer buf)
    {
        tankid=buf.readInt();
        itemindex=buf.readInt();
        itemid=buf.readInt();
        itemcount=buf.readInt();
    }
    
    public void write(engine.net.NativeBuffer buf)
    {
        buf.writeInt(tankid);
        buf.writeInt(itemindex);
        buf.writeInt(itemid);
        buf.writeInt(itemcount);
    }
}