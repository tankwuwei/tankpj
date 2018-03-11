package generated.cgame.packets.server;
import engine.net.*;
import generated.cgame.packets.objects.*;

@SuppressWarnings("unused")
public class ScTankSpaceCount extends CPacket
{
    public ScTankSpaceCount()
    {
        code=16011;
    }
    
    public  int tankspacecount;
    
    public void read(engine.net.NativeBuffer buf)
    {
        tankspacecount=buf.readInt();
    }
    
    public void write(engine.net.NativeBuffer buf)
    {
        buf.writeInt(tankspacecount);
    }
}