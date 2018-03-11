package generated.cgame.packets.client;
import engine.net.*;
import generated.cgame.packets.objects.*;

@SuppressWarnings("unused")
public class UGUserLoginRet extends CPacket
{
    public UGUserLoginRet()
    {
        code=16004;
    }
    
    public  long roleid;
    public  short ret;
    
    public void read(engine.net.NativeBuffer buf)
    {
        roleid=buf.readLong();
        ret=buf.readShort();
    }
    
    public void write(engine.net.NativeBuffer buf)
    {
        buf.writeLong(roleid);
        buf.writeShort(ret);
    }
}