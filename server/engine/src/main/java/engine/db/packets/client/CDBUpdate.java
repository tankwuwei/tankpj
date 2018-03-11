package engine.db.packets.client;
import engine.net.*;

public class CDBUpdate extends CPacket
{
    public CDBUpdate()
    {
        code=10010;
    }
    
    public String className;
    public byte[] data;
    
    public void read(engine.net.NativeBuffer buf)
    {
        className=buf.readUTF();
        if(buf.readByte()==1)
        {
            data=buf.readBytes();
        }
    }
    
    public void write(engine.net.NativeBuffer buf)
    {
        buf.writeUTF(className);
        if(data!=null)
        {
            buf.writeByte(1);
            buf.writeBytes(data);
        }
        else
        {
            buf.writeByte(0);
        }
    }
}