package generated.cgame.packets.server;
import engine.net.*;
import generated.cgame.packets.objects.*;

@SuppressWarnings("unused")
public class SCRankList extends CPacket
{
    public SCRankList()
    {
        code=5343;
    }
    
    public  short type;
    public  int rank;
    public  int value;
    public  RankInfo[] list;
    
    public void read(engine.net.NativeBuffer buf)
    {
        type=buf.readShort();
        rank=buf.readInt();
        value=buf.readInt();
        int listLength=buf.readShort();
        list=new RankInfo[listLength];
        for(int i=0;i<listLength;i++)
        {
            RankInfo d=new RankInfo();
            if(buf.readByte()==1)
            {
                d.read(buf);
                list[i]=d;
            }
        }
    }
    
    public void write(engine.net.NativeBuffer buf)
    {
        buf.writeShort(type);
        buf.writeInt(rank);
        buf.writeInt(value);
        buf.writeArray(list);
    }
}