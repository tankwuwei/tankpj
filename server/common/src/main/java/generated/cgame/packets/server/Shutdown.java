package generated.cgame.packets.server;
import engine.net.*;
import generated.cgame.packets.objects.*;

@SuppressWarnings("unused")
public class Shutdown extends CPacket
{
    public Shutdown()
    {
        code=31011;
    }
    
    public  int time;
    
    public void read(engine.net.NativeBuffer buf)
    {
        time=buf.readInt();
    }
    
    public void write(engine.net.NativeBuffer buf)
    {
        buf.writeInt(time);
    }
}