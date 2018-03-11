package generated.cgame.packets.server;
import engine.net.*;
import generated.cgame.packets.objects.*;

@SuppressWarnings("unused")
public class SCGameSvrStartFight extends CPacket
{
    public SCGameSvrStartFight()
    {
        code=16006;
    }
    
    public  RoleFightInfo[] roleinfos;
    public  long roomid;
    public  int matchtype;
    public  int matchid;
    
    public void read(engine.net.NativeBuffer buf)
    {
        int roleinfosLength=buf.readShort();
        roleinfos=new RoleFightInfo[roleinfosLength];
        for(int i=0;i<roleinfosLength;i++)
        {
            RoleFightInfo d=new RoleFightInfo();
            if(buf.readByte()==1)
            {
                d.read(buf);
                roleinfos[i]=d;
            }
        }
        roomid=buf.readLong();
        matchtype=buf.readInt();
        matchid=buf.readInt();
    }
    
    public void write(engine.net.NativeBuffer buf)
    {
        buf.writeArray(roleinfos);
        buf.writeLong(roomid);
        buf.writeInt(matchtype);
        buf.writeInt(matchid);
    }
}