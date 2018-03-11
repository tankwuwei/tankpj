package generated.cgame.packets.server;
import engine.net.*;
import generated.cgame.packets.objects.*;

@SuppressWarnings("unused")
public class SCPlayerLoginData extends CPacket
{
    public SCPlayerLoginData()
    {
        code=5311;
    }
    
    public  long roleid;
    public  String rolename;
    public  int level;
    public  int Exp;
    public  int money;
    public  int ingot;
    public  long teamid;
    
    public void read(engine.net.NativeBuffer buf)
    {
        roleid=buf.readLong();
        rolename=buf.readUTF();
        level=buf.readInt();
        Exp=buf.readInt();
        money=buf.readInt();
        ingot=buf.readInt();
        teamid=buf.readLong();
    }
    
    public void write(engine.net.NativeBuffer buf)
    {
        buf.writeLong(roleid);
        buf.writeUTF(rolename);
        buf.writeInt(level);
        buf.writeInt(Exp);
        buf.writeInt(money);
        buf.writeInt(ingot);
        buf.writeLong(teamid);
    }
}