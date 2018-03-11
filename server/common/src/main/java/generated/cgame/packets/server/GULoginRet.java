package generated.cgame.packets.server;
import engine.net.*;
import generated.cgame.packets.objects.*;

@SuppressWarnings("unused")
public class GULoginRet extends CPacket
{
    public GULoginRet()
    {
        code=16001;
    }
    
    public  short ret;
    public  long serverid;
    
    public void read(engine.net.NativeBuffer buf)
    {
        ret=buf.readShort();
        serverid=buf.readLong();
    }
    
    public void write(engine.net.NativeBuffer buf)
    {
        buf.writeShort(ret);
        buf.writeLong(serverid);
    }
}