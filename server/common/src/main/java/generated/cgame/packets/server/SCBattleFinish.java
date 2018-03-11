package generated.cgame.packets.server;
import engine.net.*;
import generated.cgame.packets.objects.*;

@SuppressWarnings("unused")
public class SCBattleFinish extends CPacket
{
    public SCBattleFinish()
    {
        code=5505;
    }
    
    public  short win;
    public  int score;
    public  ItemInfo[] reward;
    
    public void read(engine.net.NativeBuffer buf)
    {
        win=buf.readShort();
        score=buf.readInt();
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
        buf.writeShort(win);
        buf.writeInt(score);
        buf.writeArray(reward);
    }
}