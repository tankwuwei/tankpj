package generated.cgame.packets.client;
import engine.net.*;
import generated.cgame.packets.objects.*;

@SuppressWarnings("unused")
public class CSUnUseItem extends CPacket
{
    public CSUnUseItem()
    {
        code=7005;
    }
    
    public  short pos;
    
    public void read(engine.net.NativeBuffer buf)
    {
        pos=buf.readShort();
    }
    
    public void write(engine.net.NativeBuffer buf)
    {
        buf.writeShort(pos);
    }
}