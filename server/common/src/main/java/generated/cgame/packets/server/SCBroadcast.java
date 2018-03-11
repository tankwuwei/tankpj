package generated.cgame.packets.server;
import engine.net.*;
import generated.cgame.packets.objects.*;

@SuppressWarnings("unused")
public class SCBroadcast extends CPacket
{
    public SCBroadcast()
    {
        code=7013;
    }
    
    public  String content;
    
    public void read(engine.net.NativeBuffer buf)
    {
        content=buf.readUTF();
    }
    
    public void write(engine.net.NativeBuffer buf)
    {
        buf.writeUTF(content);
    }
}