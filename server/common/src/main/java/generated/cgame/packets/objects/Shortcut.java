package generated.cgame.packets.objects;
import engine.net.*;
import generated.cgame.packets.objects.*;

@SuppressWarnings("unused")
public class Shortcut extends CValue
{
    
    public  String keytype;
    public  String value;
    
    public void read(engine.net.NativeBuffer buf)
    {
        keytype=buf.readUTF();
        value=buf.readUTF();
    }
    
    public void write(engine.net.NativeBuffer buf)
    {
        buf.writeUTF(keytype);
        buf.writeUTF(value);
    }
}