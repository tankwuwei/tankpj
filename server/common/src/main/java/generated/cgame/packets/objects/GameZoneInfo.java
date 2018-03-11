package generated.cgame.packets.objects;
import engine.net.*;
import generated.cgame.packets.objects.*;

@SuppressWarnings("unused")
public class GameZoneInfo extends CValue
{
    
    public  int id;
    public  String zonename;
    
    public void read(engine.net.NativeBuffer buf)
    {
        id=buf.readInt();
        zonename=buf.readUTF();
    }
    
    public void write(engine.net.NativeBuffer buf)
    {
        buf.writeInt(id);
        buf.writeUTF(zonename);
    }
}