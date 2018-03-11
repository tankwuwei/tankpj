package generated.cgame.packets.client;
import engine.net.*;
import generated.cgame.packets.objects.*;

@SuppressWarnings("unused")
public class CSTankItemAutoBak extends CPacket
{
    public CSTankItemAutoBak()
    {
        code=6018;
    }
    
    public  int tankid;
    public  int bauto;
    
    public void read(engine.net.NativeBuffer buf)
    {
        tankid=buf.readInt();
        bauto=buf.readInt();
    }
    
    public void write(engine.net.NativeBuffer buf)
    {
        buf.writeInt(tankid);
        buf.writeInt(bauto);
    }
}