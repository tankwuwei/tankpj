package engine.db.packets.client;
import engine.db.DBPacket;

public class CDBSaveObjects extends DBPacket
{
    public CDBSaveObjects()
    {
        code=11030;
    }
    
    public byte[] msg;
    
    public void read(engine.net.NativeBuffer buf)
    {
        id = buf.readLong();
        if(buf.readByte()==1)
        {
            msg=buf.readBytes();
        }
    }
    
    public void write(engine.net.NativeBuffer buf)
    {
        buf.writeLong(id);
        if(msg!=null)
        {
            buf.writeByte(1);
            buf.writeBytes(msg);
        }
        else
        {
            buf.writeByte(0);
        }
    }
}