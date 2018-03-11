package generated.cgame.packets.server;
import engine.net.*;
import generated.cgame.packets.objects.*;

@SuppressWarnings("unused")
public class SCShopList extends CPacket
{
    public SCShopList()
    {
        code=5351;
    }
    
    public  ItemInfo[] items;
    public  int refreshtime;
    
    public void read(engine.net.NativeBuffer buf)
    {
        int itemsLength=buf.readShort();
        items=new ItemInfo[itemsLength];
        for(int i=0;i<itemsLength;i++)
        {
            ItemInfo d=new ItemInfo();
            if(buf.readByte()==1)
            {
                d.read(buf);
                items[i]=d;
            }
        }
        refreshtime=buf.readInt();
    }
    
    public void write(engine.net.NativeBuffer buf)
    {
        buf.writeArray(items);
        buf.writeInt(refreshtime);
    }
}