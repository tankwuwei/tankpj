package generated.cgame.packets.server;
import engine.net.*;
import generated.cgame.packets.objects.*;

@SuppressWarnings("unused")
public class SCChangeTankMemberRet extends CPacket
{
    public SCChangeTankMemberRet()
    {
        code=5560;
    }
    
    public  int ret;
    public  int tankid;
    public  int memberid;
    public  int memberindex;
    
    public void read(engine.net.NativeBuffer buf)
    {
        ret=buf.readInt();
        tankid=buf.readInt();
        memberid=buf.readInt();
        memberindex=buf.readInt();
    }
    
    public void write(engine.net.NativeBuffer buf)
    {
        buf.writeInt(ret);
        buf.writeInt(tankid);
        buf.writeInt(memberid);
        buf.writeInt(memberindex);
    }
}