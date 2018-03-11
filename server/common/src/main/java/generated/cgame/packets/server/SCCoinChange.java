package generated.cgame.packets.server;
import engine.net.*;
import generated.cgame.packets.objects.*;

@SuppressWarnings("unused")
public class SCCoinChange extends CPacket
{
    public SCCoinChange()
    {
        code=6023;
    }
    
    public  int cointype;
    public  int value;
    
    public void read(engine.net.NativeBuffer buf)
    {
        cointype=buf.readInt();
        value=buf.readInt();
    }
    
    public void write(engine.net.NativeBuffer buf)
    {
        buf.writeInt(cointype);
        buf.writeInt(value);
    }
}