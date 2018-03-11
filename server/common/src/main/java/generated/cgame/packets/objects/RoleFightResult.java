package generated.cgame.packets.objects;
import engine.net.*;
import generated.cgame.packets.objects.*;

@SuppressWarnings("unused")
public class RoleFightResult extends CValue
{
    
    public  long roleid;
    public  int mapid;
    public  String rolename;
    public  int gamescs;
    public  int addexp;
    public  int addmoney;
    public  int addtechpoint;
    public  int usetankid;
    public  int killcount;
    public  int assistcount;
    public  int deadcount;
    public  int addscore;
    public  int camp;
    
    public void read(engine.net.NativeBuffer buf)
    {
        roleid=buf.readLong();
        mapid=buf.readInt();
        rolename=buf.readUTF();
        gamescs=buf.readInt();
        addexp=buf.readInt();
        addmoney=buf.readInt();
        addtechpoint=buf.readInt();
        usetankid=buf.readInt();
        killcount=buf.readInt();
        assistcount=buf.readInt();
        deadcount=buf.readInt();
        addscore=buf.readInt();
        camp=buf.readInt();
    }
    
    public void write(engine.net.NativeBuffer buf)
    {
        buf.writeLong(roleid);
        buf.writeInt(mapid);
        buf.writeUTF(rolename);
        buf.writeInt(gamescs);
        buf.writeInt(addexp);
        buf.writeInt(addmoney);
        buf.writeInt(addtechpoint);
        buf.writeInt(usetankid);
        buf.writeInt(killcount);
        buf.writeInt(assistcount);
        buf.writeInt(deadcount);
        buf.writeInt(addscore);
        buf.writeInt(camp);
    }
}