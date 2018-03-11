package generated.cgame.packets.server;
import engine.net.*;
import generated.cgame.packets.objects.*;

@SuppressWarnings("unused")
public class SCFighterList extends CPacket
{
    public SCFighterList()
    {
        code=6001;
    }
    
    public  FighterInfo[] fighters;
    
    public void read(engine.net.NativeBuffer buf)
    {
        int fightersLength=buf.readShort();
        fighters=new FighterInfo[fightersLength];
        for(int i=0;i<fightersLength;i++)
        {
            FighterInfo d=new FighterInfo();
            if(buf.readByte()==1)
            {
                d.read(buf);
                fighters[i]=d;
            }
        }
    }
    
    public void write(engine.net.NativeBuffer buf)
    {
        buf.writeArray(fighters);
    }
}