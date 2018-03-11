package generated.cgame.packets.server;
import engine.net.*;
import generated.cgame.packets.objects.*;

@SuppressWarnings("unused")
public class AgentLoginGameSvr extends CPacket
{
    public AgentLoginGameSvr()
    {
        code=31012;
    }
    
    public  int time;
    public  String md5check;
    public  int zoneid;
    public  String zonename;
    public  int freecount;
    
    public void read(engine.net.NativeBuffer buf)
    {
        time=buf.readInt();
        md5check=buf.readUTF();
        zoneid=buf.readInt();
        zonename=buf.readUTF();
        freecount=buf.readInt();
    }
    
    public void write(engine.net.NativeBuffer buf)
    {
        buf.writeInt(time);
        buf.writeUTF(md5check);
        buf.writeInt(zoneid);
        buf.writeUTF(zonename);
        buf.writeInt(freecount);
    }
}