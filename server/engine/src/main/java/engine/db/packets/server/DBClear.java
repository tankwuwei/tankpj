package engine.db.packets.server;
import engine.net.*;

public class DBClear extends CPacket
{
    public DBClear()
    {
        code=15009;
    }
    
    
    public void read(engine.net.NativeBuffer buf)
    {
    }
    
    public void write(engine.net.NativeBuffer buf)
    {
    }
}