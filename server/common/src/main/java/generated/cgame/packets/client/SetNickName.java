package generated.cgame.packets.client;
import engine.net.*;
import generated.cgame.packets.objects.*;

@SuppressWarnings("unused")
public class SetNickName extends CPacket
{
    public SetNickName()
    {
        code=407;
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