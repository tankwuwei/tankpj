package generated.cgame.packets.objects;
import engine.net.*;
import generated.cgame.packets.objects.*;

@SuppressWarnings("unused")
public class FighterInfo extends CValue
{
    
    public  int id;
    public  int battlelevelid;
    public  String mapname;
    public  String hostname;
    
    public void read(engine.net.NativeBuffer buf)
    {
        id=buf.readInt();
        battlelevelid=buf.readInt();
        mapname=buf.readUTF();
        hostname=buf.readUTF();
    }
    
    public void write(engine.net.NativeBuffer buf)
    {
        buf.writeInt(id);
        buf.writeInt(battlelevelid);
        buf.writeUTF(mapname);
        buf.writeUTF(hostname);
    }
}