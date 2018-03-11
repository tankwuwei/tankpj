package generated.cgame.packets.server;
import engine.net.*;
import generated.cgame.packets.objects.*;

@SuppressWarnings("unused")
public class SCRequestUpMemberLvlRet extends CPacket
{
    public SCRequestUpMemberLvlRet()
    {
        code=6036;
    }
    
    public  int ret;
    public  int memberid;
    public  int exp;
    public  int lvl;
    
    public void read(engine.net.NativeBuffer buf)
    {
        ret=buf.readInt();
        memberid=buf.readInt();
        exp=buf.readInt();
        lvl=buf.readInt();
    }
    
    public void write(engine.net.NativeBuffer buf)
    {
        buf.writeInt(ret);
        buf.writeInt(memberid);
        buf.writeInt(exp);
        buf.writeInt(lvl);
    }
}