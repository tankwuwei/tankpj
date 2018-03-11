package generated.cgame.packets.objects;
import engine.net.*;
import generated.cgame.packets.objects.*;

@SuppressWarnings("unused")
public class TankModelinfo extends CValue
{
    
    public  int modelid;
    public  int masterlvl;
    public  int barrelid;
    public  int turretid;
    public  int engineid;
    public  int transmissionid;
    public  int[] diypartids;
    public  int bodyid;
    public  int bsale;
    public  int state;
    public  int[] bulletids;
    public  int[] bulletcounts;
    public  int[] bulletidsbak;
    public  int[] bulletcountsbak;
    public  int[] itemids;
    public  int[] itemcounts;
    public  int[] itemidsbak;
    public  int[] itemcountsbak;
    public  int autobak;
    public  int itemautobak;
    public  int skillpartid;
    public  int[] tankmembers;
    public  int fightlock;
    public  int startlocktime;
    public  int locksecs;
    
    public void read(engine.net.NativeBuffer buf)
    {
        modelid=buf.readInt();
        masterlvl=buf.readInt();
        barrelid=buf.readInt();
        turretid=buf.readInt();
        engineid=buf.readInt();
        transmissionid=buf.readInt();
        diypartids=buf.readIntArray();
        bodyid=buf.readInt();
        bsale=buf.readInt();
        state=buf.readInt();
        bulletids=buf.readIntArray();
        bulletcounts=buf.readIntArray();
        bulletidsbak=buf.readIntArray();
        bulletcountsbak=buf.readIntArray();
        itemids=buf.readIntArray();
        itemcounts=buf.readIntArray();
        itemidsbak=buf.readIntArray();
        itemcountsbak=buf.readIntArray();
        autobak=buf.readInt();
        itemautobak=buf.readInt();
        skillpartid=buf.readInt();
        tankmembers=buf.readIntArray();
        fightlock=buf.readInt();
        startlocktime=buf.readInt();
        locksecs=buf.readInt();
    }
    
    public void write(engine.net.NativeBuffer buf)
    {
        buf.writeInt(modelid);
        buf.writeInt(masterlvl);
        buf.writeInt(barrelid);
        buf.writeInt(turretid);
        buf.writeInt(engineid);
        buf.writeInt(transmissionid);
        buf.writeArray(diypartids);
        buf.writeInt(bodyid);
        buf.writeInt(bsale);
        buf.writeInt(state);
        buf.writeArray(bulletids);
        buf.writeArray(bulletcounts);
        buf.writeArray(bulletidsbak);
        buf.writeArray(bulletcountsbak);
        buf.writeArray(itemids);
        buf.writeArray(itemcounts);
        buf.writeArray(itemidsbak);
        buf.writeArray(itemcountsbak);
        buf.writeInt(autobak);
        buf.writeInt(itemautobak);
        buf.writeInt(skillpartid);
        buf.writeArray(tankmembers);
        buf.writeInt(fightlock);
        buf.writeInt(startlocktime);
        buf.writeInt(locksecs);
    }
}