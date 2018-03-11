package engine.db.packets.client;
import engine.net.*;

public class CDBGetbyhql extends CPacket
{
    public CDBGetbyhql()
    {
        code=10015;
    }
    
    public byte[] msg;
    
    public void read(engine.net.NativeBuffer buf)
    {
    	msg=buf.readByteArray();
    }
    
    public void write(engine.net.NativeBuffer buf)
    {
        buf.writeArray(msg);
    }
}