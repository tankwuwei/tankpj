package generated.cgame.packets.server;
import engine.net.*;
import generated.cgame.packets.objects.*;

@SuppressWarnings("unused")
public class ReportServiceInfo extends CPacket
{
    public ReportServiceInfo()
    {
        code=31000;
    }
    
    public  ServiceInfo info;
    
    public void read(engine.net.NativeBuffer buf)
    {
        if(buf.readByte()==0) info=null;
        else
        {
            info=new ServiceInfo();
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