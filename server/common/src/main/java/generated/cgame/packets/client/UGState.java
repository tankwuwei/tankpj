package generated.cgame.packets.client;
import engine.net.*;
import generated.cgame.packets.objects.*;

@SuppressWarnings("unused")
public class UGState extends CPacket
{
    public UGState()
    {
        code=16002;
    }
    
    public  short state;
    
    public void read(engine.net.NativeBuffer buf)
    {
        state=buf.readShort();
    }
    
    public void write(engine.net.NativeBuffer buf)
    {
        buf.writeShort(state);
    }
}