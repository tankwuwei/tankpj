package engine.db.packets.client;
import engine.net.*;

public class CDBSelectDB extends CPacket
{
    public CDBSelectDB()
    {
        code=10000;
    }
    
    public String dbname;
    
    public void read(engine.net.NativeBuffer buf)
    {
        dbname=buf.readUTF();
    }
    
    public void write(engine.net.NativeBuffer buf)
    {
        buf.writeUTF(dbname);
    }
}