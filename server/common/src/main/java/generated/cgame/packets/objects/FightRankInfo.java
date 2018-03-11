package generated.cgame.packets.objects;
import engine.net.*;
import generated.cgame.packets.objects.*;

@SuppressWarnings("unused")
public class FightRankInfo extends CValue
{
    
    public  int rank;
    public  int killed;
    public  int damage;
    public  int rankreward;
    public  int killedreward;
    public  int damagereward;
    
    public void read(engine.net.NativeBuffer buf)
    {
        rank=buf.readInt();
        killed=buf.readInt();
        damage=buf.readInt();
        rankreward=buf.readInt();
        killedreward=buf.readInt();
        damagereward=buf.readInt();
    }
    
    public void write(engine.net.NativeBuffer buf)
    {
        buf.writeInt(rank);
        buf.writeInt(killed);
        buf.writeInt(damage);
        buf.writeInt(rankreward);
        buf.writeInt(killedreward);
        buf.writeInt(damagereward);
    }
}