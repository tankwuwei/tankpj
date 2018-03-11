package generated.cgame.packets.server;
import engine.net.*;
import generated.cgame.packets.objects.*;

@SuppressWarnings("unused")
public class SCTeamLeaveRet extends CPacket
{
    public SCTeamLeaveRet()
    {
        code=7030;
    }
    
    public  short retcode;
    
    public void read(engine.net.NativeBuffer buf)
    {
        retcode=buf.readShort();
    }
    
    public void write(engine.net.NativeBuffer buf)
    {
        buf.writeShort(retcode);
    }
}