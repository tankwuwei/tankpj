package generated.cgame.packets.server;
import engine.net.*;
import generated.cgame.packets.objects.*;

@SuppressWarnings("unused")
public class SCFinishLevel extends CPacket
{
    public SCFinishLevel()
    {
        code=5322;
    }
    
    public  FinishLevelinfo[] finishs;
    
    public void read(engine.net.NativeBuffer buf)
    {
        int finishsLength=buf.readShort();
        finishs=new FinishLevelinfo[finishsLength];
        for(int i=0;i<finishsLength;i++)
        {
            FinishLevelinfo d=new FinishLevelinfo();
            if(buf.readByte()==1)
            {
                d.read(buf);
                finishs[i]=d;
            }
        }
    }
    
    public void write(engine.net.NativeBuffer buf)
    {
        buf.writeArray(finishs);
    }
}