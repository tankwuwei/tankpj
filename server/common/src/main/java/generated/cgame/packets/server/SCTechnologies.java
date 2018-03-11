package generated.cgame.packets.server;
import engine.net.*;
import generated.cgame.packets.objects.*;

@SuppressWarnings("unused")
public class SCTechnologies extends CPacket
{
    public SCTechnologies()
    {
        code=5324;
    }
    
    public  TechnologyInfo[] technologies;
    
    public void read(engine.net.NativeBuffer buf)
    {
        int technologiesLength=buf.readShort();
        technologies=new TechnologyInfo[technologiesLength];
        for(int i=0;i<technologiesLength;i++)
        {
            TechnologyInfo d=new TechnologyInfo();
            if(buf.readByte()==1)
            {
                d.read(buf);
                technologies[i]=d;
            }
        }
    }
    
    public void write(engine.net.NativeBuffer buf)
    {
        buf.writeArray(technologies);
    }
}