package generated.cgame.packets.client;
import engine.net.*;
import generated.cgame.packets.objects.*;

@SuppressWarnings("unused")
public class ChargeRet extends CPacket
{
    public ChargeRet()
    {
        code=405;
    }
    
    public  String sn;
    public  short errorcode;
    
    public void read(engine.net.NativeBuffer buf)
    {
        sn=buf.readUTF();
        errorcode=buf.readShort();
    }
    
    public void write(engine.net.NativeBuffer buf)
    {
        buf.writeUTF(sn);
        buf.writeShort(errorcode);
    }
}