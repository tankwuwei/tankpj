package generated.cgame.packets.server;
import engine.net.*;
import generated.cgame.packets.objects.*;

@SuppressWarnings("unused")
public class SCTankParts extends CPacket
{
    public SCTankParts()
    {
        code=5333;
    }
    
    public  TankPartInfo[] tankparts;
    
    public void read(engine.net.NativeBuffer buf)
    {
        int tankpartsLength=buf.readShort();
        tankparts=new TankPartInfo[tankpartsLength];
        for(int i=0;i<tankpartsLength;i++)
        {
            TankPartInfo d=new TankPartInfo();
            if(buf.readByte()==1)
            {
                d.read(buf);
                tankparts[i]=d;
            }
        }
    }
    
    public void write(engine.net.NativeBuffer buf)
    {
        buf.writeArray(tankparts);
    }
}