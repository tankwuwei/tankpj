package generated.cgame.packets.server;
import engine.net.*;
import generated.cgame.packets.objects.*;

@SuppressWarnings("unused")
public class SCStartPVE extends CPacket
{
    public SCStartPVE()
    {
        code=5401;
    }
    
    public  short ret;
    public  int commander;
    
    public void read(engine.net.NativeBuffer buf)
    {
        ret=buf.readShort();
        commander=buf.readInt();
    }
    
    public void write(engine.net.NativeBuffer buf)
    {
        buf.writeShort(ret);
        buf.writeInt(commander);
    }
}