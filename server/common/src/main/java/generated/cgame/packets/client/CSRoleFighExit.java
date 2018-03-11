package generated.cgame.packets.client;
import engine.net.*;
import generated.cgame.packets.objects.*;

@SuppressWarnings("unused")
public class CSRoleFighExit extends CPacket
{
    public CSRoleFighExit()
    {
        code=16010;
    }
    
    public  int matchid;
    public  long roomid;
    public  int matchtype;
    public  int alltimesecs;
    public  RoleFightOverInfo rolefightinfo;
    public  int bfightstart;
    public  int blockscs;
    public  int fightsecs;
    
    public void read(engine.net.NativeBuffer buf)
    {
        matchid=buf.readInt();
        roomid=buf.readLong();
        matchtype=buf.readInt();
        alltimesecs=buf.readInt();
        if(buf.readByte()==0) rolefightinfo=null;
        else
        {
            rolefightinfo=new RoleFightOverInfo();
            rolefightinfo.read(buf);
        }
        bfightstart=buf.readInt();
        blockscs=buf.readInt();
        fightsecs=buf.readInt();
    }
    
    public void write(engine.net.NativeBuffer buf)
    {
        buf.writeInt(matchid);
        buf.writeLong(roomid);
        buf.writeInt(matchtype);
        buf.writeInt(alltimesecs);
        if(rolefightinfo==null) buf.writeByte(0);
        else
        {
            buf.writeByte(1);
            rolefightinfo.write(buf);
        }
        buf.writeInt(bfightstart);
        buf.writeInt(blockscs);
        buf.writeInt(fightsecs);
    }
}