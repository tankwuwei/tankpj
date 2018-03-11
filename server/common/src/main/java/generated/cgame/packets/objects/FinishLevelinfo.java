package generated.cgame.packets.objects;
import engine.net.*;
import generated.cgame.packets.objects.*;

@SuppressWarnings("unused")
public class FinishLevelinfo extends CValue
{
    
    public  int id;
    public  int star;
    
    public void read(engine.net.NativeBuffer buf)
    {
        id=buf.readInt();
        star=buf.readInt();
    }
    
    public void write(engine.net.NativeBuffer buf)
    {
        buf.writeInt(id);
        buf.writeInt(star);
    }
}