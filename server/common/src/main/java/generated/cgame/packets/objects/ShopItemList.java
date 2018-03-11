package generated.cgame.packets.objects;
import engine.net.*;
import generated.cgame.packets.objects.*;

@SuppressWarnings("unused")
public class ShopItemList extends CValue
{
    
    public  int propid;
    public  int price;
    public  short type;
    public  short onsale;
    
    public void read(engine.net.NativeBuffer buf)
    {
        propid=buf.readInt();
        price=buf.readInt();
        type=buf.readShort();
        onsale=buf.readShort();
    }
    
    public void write(engine.net.NativeBuffer buf)
    {
        buf.writeInt(propid);
        buf.writeInt(price);
        buf.writeShort(type);
        buf.writeShort(onsale);
    }
}