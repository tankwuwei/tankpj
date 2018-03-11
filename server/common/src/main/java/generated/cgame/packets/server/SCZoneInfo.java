package generated.cgame.packets.server;
import engine.net.*;
import generated.cgame.packets.objects.*;

@SuppressWarnings("unused")
public class SCZoneInfo extends CPacket
{
    public SCZoneInfo()
    {
        code=7001;
    }
    
    public  GameZoneInfo[] zones;
    
    public void read(engine.net.NativeBuffer buf)
    {
        int zonesLength=buf.readShort();
        zones=new GameZoneInfo[zonesLength];
        for(int i=0;i<zonesLength;i++)
        {
            GameZoneInfo d=new GameZoneInfo();
            if(buf.readByte()==1)
            {
                d.read(buf);
                zones[i]=d;
            }
        }
    }
    
    public void write(engine.net.NativeBuffer buf)
    {
        buf.writeArray(zones);
    }
}