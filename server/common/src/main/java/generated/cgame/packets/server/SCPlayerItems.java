package generated.cgame.packets.server;
import engine.net.*;
import generated.cgame.packets.objects.*;

@SuppressWarnings("unused")
public class SCPlayerItems extends CPacket
{
    public SCPlayerItems()
    {
        code=7002;
    }
    
    public  PlayerItem[] items;
    
    public void read(engine.net.NativeBuffer buf)
    {
        int itemsLength=buf.readShort();
        items=new PlayerItem[itemsLength];
        for(int i=0;i<itemsLength;i++)
        {
            PlayerItem d=new PlayerItem();
            if(buf.readByte()==1)
            {
                d.read(buf);
                items[i]=d;
            }
        }
    }
    
    public void write(engine.net.NativeBuffer buf)
    {
        buf.writeArray(items);
    }
}