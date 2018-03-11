package generated.cgame.packets.client;
import engine.net.*;
import generated.cgame.packets.objects.*;

@SuppressWarnings("unused")
public class CSGameSvrStartFightRet extends CPacket
{
    public CSGameSvrStartFightRet()
    {
        code=16007;
    }
    
    public  int ret;
    public  long roomid;
    public  int matchtype;
    
    public void read(engine.net.NativeBuffer buf)
    {
        ret=buf.readInt();
        roomid=buf.readLong();
        matchtype=buf.readInt();
    }
    
    public void write(engine.net.NativeBuffer buf)
    {
        buf.writeInt(ret);
        buf.writeLong(roomid);
        buf.writeInt(matchtype);
    }
}