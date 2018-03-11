package generated.cgame.packets.objects;
import engine.net.*;
import generated.cgame.packets.objects.*;

@SuppressWarnings("unused")
public class FightLoadingRef extends CValue
{
    
    public  long roleid;
    public  int camp;
    public  int rolelvl;
    public  String rolename;
    public  int tankid;
    public  int tanklvl;
    
    public void read(engine.net.NativeBuffer buf)
    {
        roleid=buf.readLong();
        camp=buf.readInt();
        rolelvl=buf.readInt();
        rolename=buf.readUTF();
        tankid=buf.readInt();
        tanklvl=buf.readInt();
    }
    
    public void write(engine.net.NativeBuffer buf)
    {
        buf.writeLong(roleid);
        buf.writeInt(camp);
        buf.writeInt(rolelvl);
        buf.writeUTF(rolename);
        buf.writeInt(tankid);
        buf.writeInt(tanklvl);
    }
}