package generated.cgame.packets.client;
import engine.net.*;
import generated.cgame.packets.objects.*;

@SuppressWarnings("unused")
public class RequestBuySaleTankModel extends CPacket
{
    public RequestBuySaleTankModel()
    {
        code=5540;
    }
    
    public  int modelid;
    
    public void read(engine.net.NativeBuffer buf)
    {
        modelid=buf.readInt();
    }
    
    public void write(engine.net.NativeBuffer buf)
    {
        buf.writeInt(modelid);
    }
}