package engine.db.packets.client;
import engine.net.*;

public class CDBGetAll extends CPacket
{
    public CDBGetAll()
    {
        code=10011;
    }
    
    public String className;
    public int start;
    public short length;
    
    public void read(engine.net.NativeBuffer buf)
    {
        className=buf.readUTF();
        start=buf.readInt();
        length=buf.readShort();
    }
    
    public void write(engine.net.NativeBuffer buf)
    {
        buf.writeUTF(className);
        buf.writeInt(start);
        buf.writeShort(length);
    }
}