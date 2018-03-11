package generated.cgame.packets.server;
import engine.net.*;
import generated.cgame.packets.objects.*;

@SuppressWarnings("unused")
public class SCBuyItem extends CPacket
{
    public SCBuyItem()
    {
        code=5353;
    }
    
    public  int id;
    public  short ret;
    public  ItemInfo[] items;
    
    public void read(engine.net.NativeBuffer buf)
    {
        id=buf.readInt();
        ret=buf.readShort();
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
    }
    
    public void write(engine.net.NativeBuffer buf)
    {
        buf.writeInt(id);
        buf.writeShort(ret);
        buf.writeArray(items);
    }
}