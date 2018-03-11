package generated.cgame.packets.client;
import engine.net.*;
import generated.cgame.packets.objects.*;

@SuppressWarnings("unused")
public class UGUserAction extends CPacket
{
    public UGUserAction()
    {
        code=16005;
    }
    
    public  long roleid;
    public  short action;
    
    public void read(engine.net.NativeBuffer buf)
    {
        roleid=buf.readLong();
        action=buf.readShort();
    }
    
    public void write(engine.net.NativeBuffer buf)
    {
        buf.writeLong(roleid);
        buf.writeShort(action);
    }
}