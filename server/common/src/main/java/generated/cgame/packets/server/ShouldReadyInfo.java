package generated.cgame.packets.server;
import engine.net.*;
import generated.cgame.packets.objects.*;

@SuppressWarnings("unused")
public class ShouldReadyInfo extends CPacket
{
    public ShouldReadyInfo()
    {
        code=406;
    }
    
    public  int bsetnick;
    
    public void read(engine.net.NativeBuffer buf)
    {
        bsetnick=buf.readInt();
    }
    
    public void write(engine.net.NativeBuffer buf)
    {
        buf.writeInt(bsetnick);
    }
}