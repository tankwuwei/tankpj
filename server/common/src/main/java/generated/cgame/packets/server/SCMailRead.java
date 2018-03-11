package generated.cgame.packets.server;
import engine.net.*;
import generated.cgame.packets.objects.*;

@SuppressWarnings("unused")
public class SCMailRead extends CPacket
{
    public SCMailRead()
    {
        code=7017;
    }
    
    public  short errorcode;
    public  long[] mailids;
    
    public void read(engine.net.NativeBuffer buf)
    {
        errorcode=buf.readShort();
        mailids=buf.readLongArray();
    }
    
    public void write(engine.net.NativeBuffer buf)
    {
        buf.writeShort(errorcode);
        buf.writeArray(mailids);
    }
}