package generated.cgame.packets.server;
import engine.net.*;
import generated.cgame.packets.objects.*;

@SuppressWarnings("unused")
public class SCSendFightLoadingInfo extends CPacket
{
    public SCSendFightLoadingInfo()
    {
        code=5561;
    }
    
    public  int fightnumber;
    public  FightLoadingRef[] rolesinfo;
    
    public void read(engine.net.NativeBuffer buf)
    {
        fightnumber=buf.readInt();
        int rolesinfoLength=buf.readShort();
        rolesinfo=new FightLoadingRef[rolesinfoLength];
        for(int i=0;i<rolesinfoLength;i++)
        {
            FightLoadingRef d=new FightLoadingRef();
            if(buf.readByte()==1)
            {
                d.read(buf);
                rolesinfo[i]=d;
            }
        }
    }
    
    public void write(engine.net.NativeBuffer buf)
    {
        buf.writeInt(fightnumber);
        buf.writeArray(rolesinfo);
    }
}