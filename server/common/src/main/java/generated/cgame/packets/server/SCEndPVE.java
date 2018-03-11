package generated.cgame.packets.server;
import engine.net.*;
import generated.cgame.packets.objects.*;

@SuppressWarnings("unused")
public class SCEndPVE extends CPacket
{
    public SCEndPVE()
    {
        code=5403;
    }
    
    public  ItemInfo[] reward;
    
    public void read(engine.net.NativeBuffer buf)
    {
        int rewardLength=buf.readShort();
        reward=new ItemInfo[rewardLength];
        for(int i=0;i<rewardLength;i++)
        {
            ItemInfo d=new ItemInfo();
            if(buf.readByte()==1)
            {
                d.read(buf);
                reward[i]=d;
            }
        }
    }
    
    public void write(engine.net.NativeBuffer buf)
    {
        buf.writeArray(reward);
    }
}