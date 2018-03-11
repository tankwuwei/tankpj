package engine.db.packets.client;
import engine.net.*;

public class CDBSaveDirect extends CPacket
{
    public CDBSaveDirect()
    {
        code=10002;
    }
    
    public String className;
    public byte[] msg;
    
    public void read(engine.net.NativeBuffer buf)
    {
        className=buf.readUTF();
        if(buf.readByte()==1)
        {
            msg=buf.readBytes();
        }
    }
    
    public void write(engine.net.NativeBuffer buf)
    {
        buf.writeUTF(className);
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