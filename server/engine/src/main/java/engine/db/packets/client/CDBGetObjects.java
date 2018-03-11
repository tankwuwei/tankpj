package engine.db.packets.client;
import engine.net.*;

public class CDBGetObjects extends CPacket
{
    public CDBGetObjects()
    {
        code=10005;
    }
    
    public String className;
    public String field;
    public String fvalue;
    
    public void read(engine.net.NativeBuffer buf)
    {
        className=buf.readUTF();
        field=buf.readUTF();
        fvalue=buf.readUTF();
    }
    
    public void write(engine.net.NativeBuffer buf)
    {
        buf.writeUTF(className);
        buf.writeUTF(field);
        buf.writeUTF(fvalue);
    }
}