package generated.cgame.packets.client;
import engine.net.*;
import generated.cgame.packets.objects.*;

@SuppressWarnings("unused")
public class CSChangeSkill extends CPacket
{
    public CSChangeSkill()
    {
        code=6021;
    }
    
    public  int tankid;
    public  int skillpartid;
    
    public void read(engine.net.NativeBuffer buf)
    {
        tankid=buf.readInt();
        skillpartid=buf.readInt();
    }
    
    public void write(engine.net.NativeBuffer buf)
    {
        buf.writeInt(tankid);
        buf.writeInt(skillpartid);
    }
}