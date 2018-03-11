package generated.cgame.packets.objects;
import engine.net.*;
import generated.cgame.packets.objects.*;

@SuppressWarnings("unused")
public class ShopItemInfo extends CValue
{
    
    public  int itemid;
    public  int count;
    public  int type;
    public  int money;
    public  int price;
    
    public void read(engine.net.NativeBuffer buf)
    {
        itemid=buf.readInt();
        count=buf.readInt();
        type=buf.readInt();
        money=buf.readInt();
        price=buf.readInt();
    }
    
    public void write(engine.net.NativeBuffer buf)
    {
        buf.writeInt(itemid);
        buf.writeInt(count);
        buf.writeInt(type);
        buf.writeInt(money);
        buf.writeInt(price);
    }
}