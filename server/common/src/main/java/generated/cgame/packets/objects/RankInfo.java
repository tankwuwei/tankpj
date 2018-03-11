package generated.cgame.packets.objects;
import engine.net.*;
import generated.cgame.packets.objects.*;

@SuppressWarnings("unused")
public class RankInfo extends CValue
{
    
    public  int index;
    public  String name;
    public  int value;
    
    public void read(engine.net.NativeBuffer buf)
    {
        index=buf.readInt();
        name=buf.readUTF();
        value=buf.readInt();
    }
    
    public void write(engine.net.NativeBuffer buf)
    {
        buf.writeInt(index);
        buf.writeUTF(name);
        buf.writeInt(value);
    }
}