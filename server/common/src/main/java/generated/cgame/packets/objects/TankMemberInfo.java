package generated.cgame.packets.objects;
import engine.net.*;
import generated.cgame.packets.objects.*;

@SuppressWarnings("unused")
public class TankMemberInfo extends CValue
{
    
    public  int id;
    public  int lvl;
    public  int exp;
    public  int star;
    public  int state;
    public  int chips;
    
    public void read(engine.net.NativeBuffer buf)
    {
        id=buf.readInt();
        lvl=buf.readInt();
        exp=buf.readInt();
        star=buf.readInt();
        state=buf.readInt();
        chips=buf.readInt();
    }
    
    public void write(engine.net.NativeBuffer buf)
    {
        buf.writeInt(id);
        buf.writeInt(lvl);
        buf.writeInt(exp);
        buf.writeInt(star);
        buf.writeInt(state);
        buf.writeInt(chips);
    }
}