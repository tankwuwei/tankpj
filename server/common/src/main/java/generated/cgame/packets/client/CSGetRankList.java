package generated.cgame.packets.client;
import engine.net.*;
import generated.cgame.packets.objects.*;

@SuppressWarnings("unused")
public class CSGetRankList extends CPacket
{
    public CSGetRankList()
    {
        code=5342;
    }
    
    public  short type;
    
    public void read(engine.net.NativeBuffer buf)
    {
        type=buf.readShort();
    }
    
    public void write(engine.net.NativeBuffer buf)
    {
        buf.writeShort(type);
    }
}