package generated.cgame.packets.objects;
import engine.net.*;
import generated.cgame.packets.objects.*;

@SuppressWarnings("unused")
public class CardInfo extends CValue
{
    
    public  long cardid;
    public  int cardtype;
    public  int cardlevel;
    public  int exp;
    
    public void read(engine.net.NativeBuffer buf)
    {
        cardid=buf.readLong();
        cardtype=buf.readInt();
        cardlevel=buf.readInt();
        exp=buf.readInt();
    }
    
    public void write(engine.net.NativeBuffer buf)
    {
        buf.writeLong(cardid);
        buf.writeInt(cardtype);
        buf.writeInt(cardlevel);
        buf.writeInt(exp);
    }
}