package generated.cgame.packets.server;
import engine.net.*;
import generated.cgame.packets.objects.*;

@SuppressWarnings("unused")
public class SUStartFight extends CPacket
{
    public SUStartFight()
    {
        code=17001;
    }
    
    public  TankFightInfo[] tankinfos;
    
    public void read(engine.net.NativeBuffer buf)
    {
        int tankinfosLength=buf.readShort();
        tankinfos=new TankFightInfo[tankinfosLength];
        for(int i=0;i<tankinfosLength;i++)
        {
            TankFightInfo d=new TankFightInfo();
            if(buf.readByte()==1)
            {
                d.read(buf);
                tankinfos[i]=d;
            }
        }
    }
    
    public void write(engine.net.NativeBuffer buf)
    {
        buf.writeArray(tankinfos);
    }
}