package generated.cgame.packets.server;
import engine.net.*;
import generated.cgame.packets.objects.*;

@SuppressWarnings("unused")
public class SCAddItemRet extends CPacket
{
    public SCAddItemRet()
    {
        code=6015;
    }
    
    public  int ret;
    public  int itemid;
    public  int allitemcounts;
    
    public void read(engine.net.NativeBuffer buf)
    {
        ret=buf.readInt();
        itemid=buf.readInt();
        allitemcounts=buf.readInt();
    }
    
    public void write(engine.net.NativeBuffer buf)
    {
        buf.writeInt(ret);
        buf.writeInt(itemid);
        buf.writeInt(allitemcounts);
    }
}