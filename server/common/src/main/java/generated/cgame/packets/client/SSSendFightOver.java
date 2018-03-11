package generated.cgame.packets.client;
import engine.net.*;
import generated.cgame.packets.objects.*;

@SuppressWarnings("unused")
public class SSSendFightOver extends CPacket
{
    public SSSendFightOver()
    {
        code=16009;
    }
    
    public  int matchid;
    public  long roomid;
    public  int matchtype;
    public  int alltimesecs;
    public  RoleFightOverInfo[] rolesWin;
    public  RoleFightOverInfo[] rolesFailed;
    
    public void read(engine.net.NativeBuffer buf)
    {
        matchid=buf.readInt();
        roomid=buf.readLong();
        matchtype=buf.readInt();
        alltimesecs=buf.readInt();
        int rolesWinLength=buf.readShort();
        rolesWin=new RoleFightOverInfo[rolesWinLength];
        for(int i=0;i<rolesWinLength;i++)
        {
            RoleFightOverInfo d=new RoleFightOverInfo();
            if(buf.readByte()==1)
            {
                d.read(buf);
                rolesWin[i]=d;
            }
        }
        int rolesFailedLength=buf.readShort();
        rolesFailed=new RoleFightOverInfo[rolesFailedLength];
        for(int i=0;i<rolesFailedLength;i++)
        {
            RoleFightOverInfo d=new RoleFightOverInfo();
            if(buf.readByte()==1)
            {
                d.read(buf);
                rolesFailed[i]=d;
            }
        }
    }
    
    public void write(engine.net.NativeBuffer buf)
    {
        buf.writeInt(matchid);
        buf.writeLong(roomid);
        buf.writeInt(matchtype);
        buf.writeInt(alltimesecs);
        buf.writeArray(rolesWin);
        buf.writeArray(rolesFailed);
    }
}