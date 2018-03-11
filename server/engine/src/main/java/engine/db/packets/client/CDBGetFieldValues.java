package engine.db.packets.client;
import engine.net.*;

public class CDBGetFieldValues extends CPacket
{
    public CDBGetFieldValues()
    {
        code=10012;
    }
    
    public String className;
    public String[] propertyName;
    public byte[] propertyType;
    
    public void read(engine.net.NativeBuffer buf)
    {
        className=buf.readUTF();
        propertyName=buf.readUTFArray();
        propertyType=buf.readByteArray();
    }
    
    public void write(engine.net.NativeBuffer buf)
    {
        buf.writeUTF(className);
        buf.writeArray(propertyName);
        buf.writeArray(propertyType);
    }
}