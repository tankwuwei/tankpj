package engine.db.packets.server;
import engine.net.*;

public class DBGetObject extends CPacket
{
    public DBGetObject()
    {
        code=15003;
    }
    
    public byte[] msg;
    
    public void read(engine.net.NativeBuffer buf)
    {
        if(buf.readByte()==1)
        {
            msg=buf.readBytes();
        }
    }
    
    public void write(engine.net.NativeBuffer buf)
    {
        if(msg!=null)
        {
            buf.writeByte(1);
            buf.writeBytes(msg);
        }
        else
        {
            buf.writeByte(0);
        }
    }
}