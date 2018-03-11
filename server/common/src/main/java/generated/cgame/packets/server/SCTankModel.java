package generated.cgame.packets.server;
import engine.net.*;
import generated.cgame.packets.objects.*;

@SuppressWarnings("unused")
public class SCTankModel extends CPacket
{
    public SCTankModel()
    {
        code=5323;
    }
    
    public  TankModelinfo[] tankmodels;
    
    public void read(engine.net.NativeBuffer buf)
    {
        int tankmodelsLength=buf.readShort();
        tankmodels=new TankModelinfo[tankmodelsLength];
        for(int i=0;i<tankmodelsLength;i++)
        {
            TankModelinfo d=new TankModelinfo();
            if(buf.readByte()==1)
            {
                d.read(buf);
                tankmodels[i]=d;
            }
        }
    }
    
    public void write(engine.net.NativeBuffer buf)
    {
        buf.writeArray(tankmodels);
    }
}