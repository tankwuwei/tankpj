package generated.cgame.packets.server;
import engine.net.*;
import generated.cgame.packets.objects.*;

@SuppressWarnings("unused")
public class SCFightRankInfo extends CPacket
{
    public SCFightRankInfo()
    {
        code=5562;
    }
    
    public  FightRankInfo info;
    
    public void read(engine.net.NativeBuffer buf)
    {
        if(buf.readByte()==0) info=null;
        else
        {
            info=new FightRankInfo();
            info.read(buf);
        }
    }
    
    public void write(engine.net.NativeBuffer buf)
    {
        if(info==null) buf.writeByte(0);
        else
        {
            buf.writeByte(1);
            info.write(buf);
        }
    }
}