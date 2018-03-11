package generated.cgame.packets.objects;
import engine.net.*;
import generated.cgame.packets.objects.*;

@SuppressWarnings("unused")
public class ItemInfo extends CValue
{
    
    public  int itemid;
    public  int count;
    
    public void read(engine.net.NativeBuffer buf)
    {
        itemid=buf.readInt();
        count=buf.readInt();
    }
    
    public void write(engine.net.NativeBuffer buf)
    {
        buf.writeInt(itemid);
        buf.writeInt(count);
    }
}