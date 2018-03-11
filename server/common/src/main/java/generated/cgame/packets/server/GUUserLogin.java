package generated.cgame.packets.server;
import engine.net.*;
import generated.cgame.packets.objects.*;

@SuppressWarnings("unused")
public class GUUserLogin extends CPacket
{
    public GUUserLogin()
    {
        code=16003;
    }
    
    public  RoleFightInfo roleinfo;
    
    public void read(engine.net.NativeBuffer buf)
    {
        if(buf.readByte()==0) roleinfo=null;
        else
        {
            roleinfo=new RoleFightInfo();
            roleinfo.read(buf);
        }
    }
    
    public void write(engine.net.NativeBuffer buf)
    {
        if(roleinfo==null) buf.writeByte(0);
        else
        {
            buf.writeByte(1);
            roleinfo.write(buf);
        }
    }
}