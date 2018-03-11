package engine.db.packets.client;
import engine.net.*;

public class CDBClear extends CPacket
{
    public CDBClear()
    {
        code=10013;
    }
    
    public String className;
    
    public void read(engine.net.NativeBuffer buf)
    {
        className=buf.readUTF();
    }
    
    public void write(engine.net.NativeBuffer buf)
    {
        buf.writeUTF(className);
    }
}