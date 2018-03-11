package engine.db.packets.server;
import engine.net.*;

public class DBSaveObjects extends CPacket
{
    public DBSaveObjects()
    {
        code=15005;
    }
    
    public long[] ids;
    
    public void read(engine.net.NativeBuffer buf)
    {
        ids=buf.readLongArray();
    }
    
    public void write(engine.net.NativeBuffer buf)
    {
        buf.writeArray(ids);
    }
}