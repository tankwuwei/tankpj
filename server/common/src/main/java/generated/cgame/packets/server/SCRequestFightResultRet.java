package generated.cgame.packets.server;
import engine.net.*;
import generated.cgame.packets.objects.*;

@SuppressWarnings("unused")
public class SCRequestFightResultRet extends CPacket
{
    public SCRequestFightResultRet()
    {
        code=6005;
    }
    
    public  long roomid;
    public  RoleFightResult[] roleResults;
    
    public void read(engine.net.NativeBuffer buf)
    {
        roomid=buf.readLong();
        int roleResultsLength=buf.readShort();
        roleResults=new RoleFightResult[roleResultsLength];
        for(int i=0;i<roleResultsLength;i++)
        {
            RoleFightResult d=new RoleFightResult();
            if(buf.readByte()==1)
            {
                d.read(buf);
                roleResults[i]=d;
            }
        }
    }
    
    public void write(engine.net.NativeBuffer buf)
    {
        buf.writeLong(roomid);
        buf.writeArray(roleResults);
    }
}