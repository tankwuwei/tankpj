package engine.db.packets.server;
import engine.net.*;

public class DBSaveDirect extends CPacket
{
    public DBSaveDirect()
    {
        code=10003;
    }
    
    
    public void read(engine.net.NativeBuffer buf)
    {
    }
    
    public void write(engine.net.NativeBuffer buf)
    {
    }
}