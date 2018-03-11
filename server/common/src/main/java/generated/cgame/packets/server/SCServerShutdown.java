package generated.cgame.packets.server;
import engine.net.*;
import generated.cgame.packets.objects.*;

@SuppressWarnings("unused")
public class SCServerShutdown extends CPacket
{
    public SCServerShutdown()
    {
        code=7012;
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