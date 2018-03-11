package generated.cgame.packets.server;
import engine.net.*;
import generated.cgame.packets.objects.*;

@SuppressWarnings("unused")
public class SCPlayerData extends CPacket
{
    public SCPlayerData()
    {
        code=5313;
    }
    
    public  long roleid;
    public  String rolename;
    public  short level;
    public  int Exp;
    public  int money;
    public  int ingot;
    public  int techPoints;
    public  int developPoints;
    public  int fightcounts;
    public  int wincount;
    public  int killcount;
    public  int deadcount;
    public  int assistcount;
    public  int[] bulletids;
    public  int[] bulletbuytype;
    public  int[] bulletcounts;
    public  int[] itemids;
    public  int[] itembuytype;
    public  int[] itemcounts;
    public  int allfightsecs;
    
    public void read(engine.net.NativeBuffer buf)
    {
        roleid=buf.readLong();
        rolename=buf.readUTF();
        level=buf.readShort();
        Exp=buf.readInt();
        money=buf.readInt();
        ingot=buf.readInt();
        techPoints=buf.readInt();
        developPoints=buf.readInt();
        fightcounts=buf.readInt();
        wincount=buf.readInt();
        killcount=buf.readInt();
        deadcount=buf.readInt();
        assistcount=buf.readInt();
        bulletids=buf.readIntArray();
        bulletbuytype=buf.readIntArray();
        bulletcounts=buf.readIntArray();
        itemids=buf.readIntArray();
        itembuytype=buf.readIntArray();
        itemcounts=buf.readIntArray();
        allfightsecs=buf.readInt();
    }
    
    public void write(engine.net.NativeBuffer buf)
    {
        buf.writeLong(roleid);
        buf.writeUTF(rolename);
        buf.writeShort(level);
        buf.writeInt(Exp);
        buf.writeInt(money);
        buf.writeInt(ingot);
        buf.writeInt(techPoints);
        buf.writeInt(developPoints);
        buf.writeInt(fightcounts);
        buf.writeInt(wincount);
        buf.writeInt(killcount);
        buf.writeInt(deadcount);
        buf.writeInt(assistcount);
        buf.writeArray(bulletids);
        buf.writeArray(bulletbuytype);
        buf.writeArray(bulletcounts);
        buf.writeArray(itemids);
        buf.writeArray(itembuytype);
        buf.writeArray(itemcounts);
        buf.writeInt(allfightsecs);
    }
}