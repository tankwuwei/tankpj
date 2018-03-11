package engine.db.packets.server;
import engine.net.*;

public class DBError extends CPacket
{
    public DBError()
    {
        code=15000;
    }
    
    public short errorCode;
    public String desc;
    
    public void read(engine.net.NativeBuffer buf)
    {
        errorCode=buf.readShort();
        desc=buf.readUTF();
    }
    
    public void write(engine.net.NativeBuffer buf)
    {
        buf.writeShort(errorCode);
        buf.writeUTF(desc);
    }
}