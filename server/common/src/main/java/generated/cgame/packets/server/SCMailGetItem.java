package generated.cgame.packets.server;
import engine.net.*;
import generated.cgame.packets.objects.*;

@SuppressWarnings("unused")
public class SCMailGetItem extends CPacket
{
    public SCMailGetItem()
    {
        code=7019;
    }
    
    public  short errorcode;
    public  long mailid;
    public  int[] propid;
    public  int[] num;
    
    public void read(engine.net.NativeBuffer buf)
    {
        errorcode=buf.readShort();
        mailid=buf.readLong();
        propid=buf.readIntArray();
        num=buf.readIntArray();
    }
    
    public void write(engine.net.NativeBuffer buf)
    {
        buf.writeShort(errorcode);
        buf.writeLong(mailid);
        buf.writeArray(propid);
        buf.writeArray(num);
    }
}