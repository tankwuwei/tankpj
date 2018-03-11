package generated.cgame.packets.server;
import engine.net.*;
import generated.cgame.packets.objects.*;

@SuppressWarnings("unused")
public class SCRequestUpMemberstarRet extends CPacket
{
    public SCRequestUpMemberstarRet()
    {
        code=6035;
    }
    
    public  int ret;
    public  int memberid;
    public  int chips;
    public  int star;
    public  int lvl;
    
    public void read(engine.net.NativeBuffer buf)
    {
        ret=buf.readInt();
        memberid=buf.readInt();
        chips=buf.readInt();
        star=buf.readInt();
        lvl=buf.readInt();
    }
    
    public void write(engine.net.NativeBuffer buf)
    {
        buf.writeInt(ret);
        buf.writeInt(memberid);
        buf.writeInt(chips);
        buf.writeInt(star);
        buf.writeInt(lvl);
    }
}