package generated.cgame.packets.client;
import engine.net.*;
import generated.cgame.packets.objects.*;

@SuppressWarnings("unused")
public class CSRequestFightResult extends CPacket
{
    public CSRequestFightResult()
    {
        code=6004;
    }
    
    public  long roomid;
    
    public void read(engine.net.NativeBuffer buf)
    {
        roomid=buf.readLong();
    }
    
    public void write(engine.net.NativeBuffer buf)
    {
        buf.writeLong(roomid);
    }
}