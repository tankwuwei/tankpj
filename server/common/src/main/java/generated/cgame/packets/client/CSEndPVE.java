package generated.cgame.packets.client;
import engine.net.*;
import generated.cgame.packets.objects.*;

@SuppressWarnings("unused")
public class CSEndPVE extends CPacket
{
    public CSEndPVE()
    {
        code=5402;
    }
    
    public  short ret;
    
    public void read(engine.net.NativeBuffer buf)
    {
        ret=buf.readShort();
    }
    
    public void write(engine.net.NativeBuffer buf)
    {
        buf.writeShort(ret);
    }
}