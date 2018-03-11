package generated.cgame.packets.server;
import engine.net.*;
import generated.cgame.packets.objects.*;

@SuppressWarnings("unused")
public class SCPlayerRankInfo extends CPacket
{
    public SCPlayerRankInfo()
    {
        code=5564;
    }
    
    public  byte flag;
    public  PlayerRankInfo[] info;
    
    public void read(engine.net.NativeBuffer buf)
    {
        flag=buf.readByte();
        int infoLength=buf.readShort();
        info=new PlayerRankInfo[infoLength];
        for(int i=0;i<infoLength;i++)
        {
            PlayerRankInfo d=new PlayerRankInfo();
            if(buf.readByte()==1)
            {
                d.read(buf);
                info[i]=d;
            }
        }
    }
    
    public void write(engine.net.NativeBuffer buf)
    {
        buf.writeByte(flag);
        buf.writeArray(info);
    }
}