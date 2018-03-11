package generated.cgame.packets.server;
import engine.net.*;
import generated.cgame.packets.objects.*;

@SuppressWarnings("unused")
public class SCTechRate extends CPacket
{
    public SCTechRate()
    {
        code=6027;
    }
    
    public  int techRate;
    public  int GoldMoney;
    
    public void read(engine.net.NativeBuffer buf)
    {
        techRate=buf.readInt();
        GoldMoney=buf.readInt();
    }
    
    public void write(engine.net.NativeBuffer buf)
    {
        buf.writeInt(techRate);
        buf.writeInt(GoldMoney);
    }
}