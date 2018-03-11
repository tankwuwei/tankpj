package generated.cgame.packets.server;
import engine.net.*;
import generated.cgame.packets.objects.*;

@SuppressWarnings("unused")
public class SCChangeSkillRet extends CPacket
{
    public SCChangeSkillRet()
    {
        code=6022;
    }
    
    public  int tankid;
    public  int skillpartid;
    public  int ret;
    
    public void read(engine.net.NativeBuffer buf)
    {
        tankid=buf.readInt();
        skillpartid=buf.readInt();
        ret=buf.readInt();
    }
    
    public void write(engine.net.NativeBuffer buf)
    {
        buf.writeInt(tankid);
        buf.writeInt(skillpartid);
        buf.writeInt(ret);
    }
}