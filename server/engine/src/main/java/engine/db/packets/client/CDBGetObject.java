package engine.db.packets.client;
import engine.net.*;

public class CDBGetObject extends CPacket
{
    public CDBGetObject()
    {
        code=10004;
    }
    
    public String className;
    public long key;
    
    public void read(engine.net.NativeBuffer buf)
    {
        className=buf.readUTF();
        key=buf.readLong();
    }
    
    public void write(engine.net.NativeBuffer buf)
    {
        buf.writeUTF(className);
        buf.writeLong(key);
    }
}