package generated.cgame.packets.client;
import engine.net.*;
import generated.cgame.packets.objects.*;

@SuppressWarnings("unused")
public class RequestUnlockTech extends CPacket
{
    public RequestUnlockTech()
    {
        code=5542;
    }
    
    public  int[] techIds;
    
    public void read(engine.net.NativeBuffer buf)
    {
        techIds=buf.readIntArray();
    }
    
    public void write(engine.net.NativeBuffer buf)
    {
        buf.writeArray(techIds);
    }
}