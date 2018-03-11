package engine.db.packets.client;
import engine.net.*;

public class CDBGetConditions extends CPacket
{
    public CDBGetConditions()
    {
        code=10008;
    }
    
    public byte[] conditionData;
    
    public void read(engine.net.NativeBuffer buf)
    {
        conditionData=buf.readByteArray();
    }
    
    public void write(engine.net.NativeBuffer buf)
    {
        buf.writeArray(conditionData);
    }
}