package generated.cgame.packets.objects;
import engine.net.*;
import generated.cgame.packets.objects.*;

@SuppressWarnings("unused")
public class DriverInfo extends CValue
{
    
    public  int id;
    public  int level;
    public  int star;
    public  int state;
    public  int chips;
    
    public void read(engine.net.NativeBuffer buf)
    {
        id=buf.readInt();
        level=buf.readInt();
        star=buf.readInt();
        state=buf.readInt();
        chips=buf.readInt();
    }
    
    public void write(engine.net.NativeBuffer buf)
    {
        buf.writeInt(id);
        buf.writeInt(level);
        buf.writeInt(star);
        buf.writeInt(state);
        buf.writeInt(chips);
    }
}