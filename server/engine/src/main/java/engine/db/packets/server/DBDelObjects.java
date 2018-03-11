package engine.db.packets.server;
import engine.net.*;

public class DBDelObjects extends CPacket
{
    public DBDelObjects()
    {
        code=15006;
    }
    
    public byte state;
    
    public void read(engine.net.NativeBuffer buf)
    {
        state=buf.readByte();
    }
    
    public void write(engine.net.NativeBuffer buf)
    {
        buf.writeByte(state);
    }
}