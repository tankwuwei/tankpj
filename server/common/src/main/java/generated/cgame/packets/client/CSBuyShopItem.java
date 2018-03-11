package generated.cgame.packets.client;
import engine.net.*;
import generated.cgame.packets.objects.*;

@SuppressWarnings("unused")
public class CSBuyShopItem extends CPacket
{
    public CSBuyShopItem()
    {
        code=7010;
    }
    
    public  int propid;
    
    public void read(engine.net.NativeBuffer buf)
    {
        propid=buf.readInt();
    }
    
    public void write(engine.net.NativeBuffer buf)
    {
        buf.writeInt(propid);
    }
}