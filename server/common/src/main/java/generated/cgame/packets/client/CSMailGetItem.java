package generated.cgame.packets.client;
import engine.net.*;
import generated.cgame.packets.objects.*;

@SuppressWarnings("unused")
public class CSMailGetItem extends CPacket
{
    public CSMailGetItem()
    {
        code=7018;
    }
    
    public  long mailid;
    
    public void read(engine.net.NativeBuffer buf)
    {
        mailid=buf.readLong();
    }
    
    public void write(engine.net.NativeBuffer buf)
    {
        buf.writeLong(mailid);
    }
}