package generated.cgame.packets.server;
import engine.net.*;
import generated.cgame.packets.objects.*;

@SuppressWarnings("unused")
public class StartService extends CPacket
{
    public StartService()
    {
        code=31010;
    }
    
    public  int fightid;
    
    public void read(engine.net.NativeBuffer buf)
    {
        fightid=buf.readInt();
    }
    
    public void write(engine.net.NativeBuffer buf)
    {
        buf.writeInt(fightid);
    }
}