package engine.db.packets.server;
import engine.net.*;

public class DBSelectDB extends CPacket
{
    public DBSelectDB()
    {
        code=10001;
    }
    
    
    public void read(engine.net.NativeBuffer buf)
    {
    }
    
    public void write(engine.net.NativeBuffer buf)
    {
    }
}