package generated.cgame.packets.server;
import engine.net.*;
import generated.cgame.packets.objects.*;

@SuppressWarnings("unused")
public class SCBattleStart extends CPacket
{
    public SCBattleStart()
    {
        code=5504;
    }
    
    public  int seconds;
    
    public void read(engine.net.NativeBuffer buf)
    {
        seconds=buf.readInt();
    }
    
    public void write(engine.net.NativeBuffer buf)
    {
        buf.writeInt(seconds);
    }
}