package generated.cgame.packets.client;
import engine.net.*;
import generated.cgame.packets.objects.*;

@SuppressWarnings("unused")
public class CSChangeTankMember extends CPacket
{
    public CSChangeTankMember()
    {
        code=5559;
    }
    
    public  int tankid;
    public  int memberid;
    public  int memberindex;
    
    public void read(engine.net.NativeBuffer buf)
    {
        tankid=buf.readInt();
        memberid=buf.readInt();
        memberindex=buf.readInt();
    }
    
    public void write(engine.net.NativeBuffer buf)
    {
        buf.writeInt(tankid);
        buf.writeInt(memberid);
        buf.writeInt(memberindex);
    }
}