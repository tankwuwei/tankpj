package generated.cgame.packets.client;
import engine.net.*;
import generated.cgame.packets.objects.*;

@SuppressWarnings("unused")
public class USStartFightRet extends CPacket
{
    public USStartFightRet()
    {
        code=17002;
    }
    
    public  short ret;
    public  int aicount;
    
    public void read(engine.net.NativeBuffer buf)
    {
        ret=buf.readShort();
        aicount=buf.readInt();
    }
    
    public void write(engine.net.NativeBuffer buf)
    {
        buf.writeShort(ret);
        buf.writeInt(aicount);
    }
}