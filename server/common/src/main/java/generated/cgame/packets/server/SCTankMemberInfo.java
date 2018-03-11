package generated.cgame.packets.server;
import engine.net.*;
import generated.cgame.packets.objects.*;

@SuppressWarnings("unused")
public class SCTankMemberInfo extends CPacket
{
    public SCTankMemberInfo()
    {
        code=5558;
    }
    
    public  TankMemberInfo[] tankmembers;
    
    public void read(engine.net.NativeBuffer buf)
    {
        int tankmembersLength=buf.readShort();
        tankmembers=new TankMemberInfo[tankmembersLength];
        for(int i=0;i<tankmembersLength;i++)
        {
            TankMemberInfo d=new TankMemberInfo();
            if(buf.readByte()==1)
            {
                d.read(buf);
                tankmembers[i]=d;
            }
        }
    }
    
    public void write(engine.net.NativeBuffer buf)
    {
        buf.writeArray(tankmembers);
    }
}