package generated.cgame.packets.server;
import engine.net.*;
import generated.cgame.packets.objects.*;

@SuppressWarnings("unused")
public class CGBroadcast extends CPacket
{
    public CGBroadcast()
    {
        code=31024;
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