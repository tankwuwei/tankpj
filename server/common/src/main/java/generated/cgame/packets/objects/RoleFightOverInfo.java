package generated.cgame.packets.objects;
import engine.net.*;
import generated.cgame.packets.objects.*;

@SuppressWarnings("unused")
public class RoleFightOverInfo extends CValue
{
    
    public  long roleid;
    public  int usetankid;
    public  int killcount;
    public  int assistcount;
    public  int deadcount;
    public  int score;
    public  int camp;
    public  int[] bulletids;
    public  int[] bulletcounts;
    public  int[] itemids;
    public  int[] itemcounts;
    public  int fightsecs;
    
    public void read(engine.net.NativeBuffer buf)
    {
        roleid=buf.readLong();
        usetankid=buf.readInt();
        killcount=buf.readInt();
        assistcount=buf.readInt();
        deadcount=buf.readInt();
        score=buf.readInt();
        camp=buf.readInt();
        bulletids=buf.readIntArray();
        bulletcounts=buf.readIntArray();
        itemids=buf.readIntArray();
        itemcounts=buf.readIntArray();
        fightsecs=buf.readInt();
    }
    
    public void write(engine.net.NativeBuffer buf)
    {
        buf.writeLong(roleid);
        buf.writeInt(usetankid);
        buf.writeInt(killcount);
        buf.writeInt(assistcount);
        buf.writeInt(deadcount);
        buf.writeInt(score);
        buf.writeInt(camp);
        buf.writeArray(bulletids);
        buf.writeArray(bulletcounts);
        buf.writeArray(itemids);
        buf.writeArray(itemcounts);
        buf.writeInt(fightsecs);
    }
}