package generated.cgame.packets.objects;
import engine.net.*;
import generated.cgame.packets.objects.*;

@SuppressWarnings("unused")
public class TankFightInfo extends CValue
{
    
    public  long roleid;
    public  String rolename;
    public  String token;
    public  int tankid;
    public  int[] decalid;
    public  int teamid;
    
    public void read(engine.net.NativeBuffer buf)
    {
        roleid=buf.readLong();
        rolename=buf.readUTF();
        token=buf.readUTF();
        tankid=buf.readInt();
        decalid=buf.readIntArray();
        teamid=buf.readInt();
    }
    
    public void write(engine.net.NativeBuffer buf)
    {
        buf.writeLong(roleid);
        buf.writeUTF(rolename);
        buf.writeUTF(token);
        buf.writeInt(tankid);
        buf.writeArray(decalid);
        buf.writeInt(teamid);
    }
}