package generated.cgame.packets.client;
import engine.net.*;
import generated.cgame.packets.objects.*;

@SuppressWarnings("unused")
public class CSSearchPlayer extends CPacket
{
    public CSSearchPlayer()
    {
        code=7022;
    }
    
    public  String nickname;
    
    public void read(engine.net.NativeBuffer buf)
    {
        nickname=buf.readUTF();
    }
    
    public void write(engine.net.NativeBuffer buf)
    {
        buf.writeUTF(nickname);
    }
}