package engine.db.packets.server;
import engine.net.*;

public class DBLastId extends CPacket
{
    public DBLastId()
    {
        code=15010;
    }
    
    public long id;
    
    public void read(engine.net.NativeBuffer buf)
    {
        id=buf.readLong();
    }
    
    public void write(engine.net.NativeBuffer buf)
    {
        buf.writeLong(id);
    }
}