package generated.cgame.packets.client;
import engine.net.*;
import generated.cgame.packets.objects.*;

@SuppressWarnings("unused")
public class CSRequestPlayerRankInfo extends CPacket
{
    public CSRequestPlayerRankInfo()
    {
        code=5563;
    }
    
    public  byte flag;
    
    public void read(engine.net.NativeBuffer buf)
    {
        flag=buf.readByte();
    }
    
    public void write(engine.net.NativeBuffer buf)
    {
        buf.writeByte(flag);
    }
}