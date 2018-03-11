package generated.cgame.packets.client;
import engine.net.*;
import generated.cgame.packets.objects.*;

@SuppressWarnings("unused")
public class CSBuyItem extends CPacket
{
    public CSBuyItem()
    {
        code=5352;
    }
    
    public  int id;
    
    public void read(engine.net.NativeBuffer buf)
    {
        id=buf.readInt();
    }
    
    public void write(engine.net.NativeBuffer buf)
    {
        buf.writeInt(id);
    }
}