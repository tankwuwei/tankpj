package engine.db.packets.client;
import engine.net.*;

public class CDBDeleteObject extends CPacket
{
    public CDBDeleteObject()
    {
        code=10009;
    }
    
    public String className;
    public long[] ids;
    
    public void read(engine.net.NativeBuffer buf)
    {
        className=buf.readUTF();
        ids=buf.readLongArray();
    }
    
    public void write(engine.net.NativeBuffer buf)
    {
        buf.writeUTF(className);
        buf.writeArray(ids);
    }
}