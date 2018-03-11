package generated.cgame.packets.client;
import engine.net.*;
import generated.cgame.packets.objects.*;

@SuppressWarnings("unused")
public class CSRequestUpMemberstar extends CPacket
{
    public CSRequestUpMemberstar()
    {
        code=6034;
    }
    
    public  int memberid;
    
    public void read(engine.net.NativeBuffer buf)
    {
        memberid=buf.readInt();
    }
    
    public void write(engine.net.NativeBuffer buf)
    {
        buf.writeInt(memberid);
    }
}