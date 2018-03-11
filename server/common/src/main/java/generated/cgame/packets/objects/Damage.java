package generated.cgame.packets.objects;
import engine.net.*;
import generated.cgame.packets.objects.*;

@SuppressWarnings("unused")
public class Damage extends CValue
{
    
    public  int id;
    public  int damage;
    public  int hp;
    
    public void read(engine.net.NativeBuffer buf)
    {
        id=buf.readInt();
        damage=buf.readInt();
        hp=buf.readInt();
    }
    
    public void write(engine.net.NativeBuffer buf)
    {
        buf.writeInt(id);
        buf.writeInt(damage);
        buf.writeInt(hp);
    }
}