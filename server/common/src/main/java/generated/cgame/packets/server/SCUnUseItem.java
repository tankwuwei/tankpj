package generated.cgame.packets.server;
import engine.net.*;
import generated.cgame.packets.objects.*;

@SuppressWarnings("unused")
public class SCUnUseItem extends CPacket
{
    public SCUnUseItem()
    {
        code=7006;
    }
    
    public  short pos;
    public  short errcode;
    
    public void read(engine.net.NativeBuffer buf)
    {
        pos=buf.readShort();
        errcode=buf.readShort();
    }
    
    public void write(engine.net.NativeBuffer buf)
    {
        buf.writeShort(pos);
        buf.writeShort(errcode);
    }
}