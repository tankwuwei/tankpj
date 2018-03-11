package generated.cgame.packets.client;
import engine.net.*;
import generated.cgame.packets.objects.*;

@SuppressWarnings("unused")
public class CSAddItem extends CPacket
{
    public CSAddItem()
    {
        code=6014;
    }
    
    public  int itemid;
    public  int additemcounts;
    public  int buytype;
    
    public void read(engine.net.NativeBuffer buf)
    {
        itemid=buf.readInt();
        additemcounts=buf.readInt();
        buytype=buf.readInt();
    }
    
    public void write(engine.net.NativeBuffer buf)
    {
        buf.writeInt(itemid);
        buf.writeInt(additemcounts);
        buf.writeInt(buytype);
    }
}