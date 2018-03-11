package generated.cgame.packets.server;
import engine.net.*;
import generated.cgame.packets.objects.*;

@SuppressWarnings("unused")
public class SCShopItem extends CPacket
{
    public SCShopItem()
    {
        code=7009;
    }
    
    public  ShopItem[] shopitem;
    
    public void read(engine.net.NativeBuffer buf)
    {
        int shopitemLength=buf.readShort();
        shopitem=new ShopItem[shopitemLength];
        for(int i=0;i<shopitemLength;i++)
        {
            ShopItem d=new ShopItem();
            if(buf.readByte()==1)
            {
                d.read(buf);
                shopitem[i]=d;
            }
        }
    }
    
    public void write(engine.net.NativeBuffer buf)
    {
        buf.writeArray(shopitem);
    }
}