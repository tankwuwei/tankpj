package engine.db.packets.server;
import engine.net.*;

public class DBCheck extends CPacket
{
    public DBCheck()
    {
        code=14999;
    }
    
    
    public void read(engine.net.NativeBuffer buf)
    {
    }
    
    public void write(engine.net.NativeBuffer buf)
    {
    }
}