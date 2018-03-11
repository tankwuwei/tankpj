package generated.cgame.packets.server;
import engine.net.*;
import generated.cgame.packets.objects.*;

@SuppressWarnings("unused")
public class CGUpdShopItem extends CPacket
{
    public CGUpdShopItem()
    {
        code=31015;
    }
    
    public  ShopItemList[] shopitem;
    
    public void read(engine.net.NativeBuffer buf)
    {
        int shopitemLength=buf.readShort();
        shopitem=new ShopItemList[shopitemLength];
        for(int i=0;i<shopitemLength;i++)
        {
            ShopItemList d=new ShopItemList();
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