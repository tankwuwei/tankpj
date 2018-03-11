package generated.cgame.packets.server;
import engine.net.*;
import generated.cgame.packets.objects.*;

@SuppressWarnings("unused")
public class SCPVPData extends CPacket
{
    public SCPVPData()
    {
        code=5341;
    }
    
    public  int allcount;
    public  int wincount;
    public  int fullwincount;
    public  int score;
    public  int topscore;
    public  ItemInfo[] cardusecount;
    
    public void read(engine.net.NativeBuffer buf)
    {
        allcount=buf.readInt();
        wincount=buf.readInt();
        fullwincount=buf.readInt();
        score=buf.readInt();
        topscore=buf.readInt();
        int cardusecountLength=buf.readShort();
        cardusecount=new ItemInfo[cardusecountLength];
        for(int i=0;i<cardusecountLength;i++)
        {
            ItemInfo d=new ItemInfo();
            if(buf.readByte()==1)
            {
                d.read(buf);
                cardusecount[i]=d;
            }
        }
    }
    
    public void write(engine.net.NativeBuffer buf)
    {
        buf.writeInt(allcount);
        buf.writeInt(wincount);
        buf.writeInt(fullwincount);
        buf.writeInt(score);
        buf.writeInt(topscore);
        buf.writeArray(cardusecount);
    }
}