package generated.cgame.packets.server;
import engine.net.*;
import generated.cgame.packets.objects.*;

@SuppressWarnings("unused")
public class SCTankItemAutoBakRet extends CPacket
{
    public SCTankItemAutoBakRet()
    {
        code=6019;
    }
    
    public  int tankid;
    public  int bauto;
    public  int ret;
    
    public void read(engine.net.NativeBuffer buf)
    {
        tankid=buf.readInt();
        bauto=buf.readInt();
        ret=buf.readInt();
    }
    
    public void write(engine.net.NativeBuffer buf)
    {
        buf.writeInt(tankid);
        buf.writeInt(bauto);
        buf.writeInt(ret);
    }
}