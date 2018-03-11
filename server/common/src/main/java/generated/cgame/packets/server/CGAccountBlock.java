package generated.cgame.packets.server;
import engine.net.*;
import generated.cgame.packets.objects.*;

@SuppressWarnings("unused")
public class CGAccountBlock extends CPacket
{
    public CGAccountBlock()
    {
        code=31018;
    }
    
    public  String ids;
    public  int blocktime;
    
    public void read(engine.net.NativeBuffer buf)
    {
        ids=buf.readUTF();
        blocktime=buf.readInt();
    }
    
    public void write(engine.net.NativeBuffer buf)
    {
        buf.writeUTF(ids);
        buf.writeInt(blocktime);
    }
}