package generated.cgame.packets.client;
import engine.net.*;
import generated.cgame.packets.objects.*;

@SuppressWarnings("unused")
public class UGPlayerDead extends CPacket
{
    public UGPlayerDead()
    {
        code=16013;
    }
    
    public  long playerid;
    public  int killed;
    public  int damage;
    public  int hurt;
    public  int time;
    public  int travel;
    
    public void read(engine.net.NativeBuffer buf)
    {
        playerid=buf.readLong();
        killed=buf.readInt();
        damage=buf.readInt();
        hurt=buf.readInt();
        time=buf.readInt();
        travel=buf.readInt();
    }
    
    public void write(engine.net.NativeBuffer buf)
    {
        buf.writeLong(playerid);
        buf.writeInt(killed);
        buf.writeInt(damage);
        buf.writeInt(hurt);
        buf.writeInt(time);
        buf.writeInt(travel);
    }
}