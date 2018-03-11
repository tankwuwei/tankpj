package generated.cgame.packets.server;
import engine.net.*;
import generated.cgame.packets.objects.*;

@SuppressWarnings("unused")
public class LoginRet extends CPacket
{
    public LoginRet()
    {
        code=403;
    }
    
    public  short ret;
    
    public void read(engine.net.NativeBuffer buf)
    {
        ret=buf.readShort();
    }
    
    public void write(engine.net.NativeBuffer buf)
    {
        buf.writeShort(ret);
    }
}