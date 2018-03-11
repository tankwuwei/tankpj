package generated.cgame.packets.client;
import engine.net.*;
import generated.cgame.packets.objects.*;

@SuppressWarnings("unused")
public class CSSetIsInvite extends CPacket
{
    public CSSetIsInvite()
    {
        code=7032;
    }
    
    public  short isinvite;
    
    public void read(engine.net.NativeBuffer buf)
    {
        isinvite=buf.readShort();
    }
    
    public void write(engine.net.NativeBuffer buf)
    {
        buf.writeShort(isinvite);
    }
}