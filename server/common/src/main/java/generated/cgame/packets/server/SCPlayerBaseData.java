package generated.cgame.packets.server;
import engine.net.*;
import generated.cgame.packets.objects.*;

@SuppressWarnings("unused")
public class SCPlayerBaseData extends CPacket
{
    public SCPlayerBaseData()
    {
        code=5312;
    }
    
    public  int level;
    public  int Exp;
    public  int money;
    public  int ingot;
    public  long teamid;
    public  int givetimesPrice;
    public  int boxid;
    
    public void read(engine.net.NativeBuffer buf)
    {
        level=buf.readInt();
        Exp=buf.readInt();
        money=buf.readInt();
        ingot=buf.readInt();
        teamid=buf.readLong();
        givetimesPrice=buf.readInt();
        boxid=buf.readInt();
    }
    
    public void write(engine.net.NativeBuffer buf)
    {
        buf.writeInt(level);
        buf.writeInt(Exp);
        buf.writeInt(money);
        buf.writeInt(ingot);
        buf.writeLong(teamid);
        buf.writeInt(givetimesPrice);
        buf.writeInt(boxid);
    }
}