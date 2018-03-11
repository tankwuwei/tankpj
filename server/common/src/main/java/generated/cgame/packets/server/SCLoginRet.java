package generated.cgame.packets.server;
import engine.net.*;
import generated.cgame.packets.objects.*;

@SuppressWarnings("unused")
public class SCLoginRet extends CPacket
{
    public SCLoginRet()
    {
        code=5301;
    }
    
    public  String account;
    public  short ret;
    
    public void read(engine.net.NativeBuffer buf)
    {
        account=buf.readUTF();
        ret=buf.readShort();
    }
    
    public void write(engine.net.NativeBuffer buf)
    {
        buf.writeUTF(account);
        buf.writeShort(ret);
    }
}