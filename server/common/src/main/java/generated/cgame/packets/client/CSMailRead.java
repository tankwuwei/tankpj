package generated.cgame.packets.client;
import engine.net.*;
import generated.cgame.packets.objects.*;

@SuppressWarnings("unused")
public class CSMailRead extends CPacket
{
    public CSMailRead()
    {
        code=7016;
    }
    
    public  long[] mailids;
    
    public void read(engine.net.NativeBuffer buf)
    {
        mailids=buf.readLongArray();
    }
    
    public void write(engine.net.NativeBuffer buf)
    {
        buf.writeArray(mailids);
    }
}