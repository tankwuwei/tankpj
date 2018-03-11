package generated.cgame.packets.server;
import engine.net.*;
import generated.cgame.packets.objects.*;

@SuppressWarnings("unused")
public class ReportSystemInfo extends CPacket
{
    public ReportSystemInfo()
    {
        code=31001;
    }
    
    public  SystemInfo info;
    
    public void read(engine.net.NativeBuffer buf)
    {
        if(buf.readByte()==0) info=null;
        else
        {
            info=new SystemInfo();
            info.read(buf);
        }
    }
    
    public void write(engine.net.NativeBuffer buf)
    {
        if(info==null) buf.writeByte(0);
        else
        {
            buf.writeByte(1);
            info.write(buf);
        }
    }
}