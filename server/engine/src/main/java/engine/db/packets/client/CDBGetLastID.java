package engine.db.packets.client;
import engine.net.*;

public class CDBGetLastID extends CPacket
{
    public CDBGetLastID()
    {
        code=10014;
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