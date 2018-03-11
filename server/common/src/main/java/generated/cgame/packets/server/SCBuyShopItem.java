package generated.cgame.packets.server;
import engine.net.*;
import generated.cgame.packets.objects.*;

@SuppressWarnings("unused")
public class SCBuyShopItem extends CPacket
{
    public SCBuyShopItem()
    {
        code=7011;
    }
    
    public  short errcode;
    public  int propid;
    
    public void read(engine.net.NativeBuffer buf)
    {
        errcode=buf.readShort();
        propid=buf.readInt();
    }
    
    public void write(engine.net.NativeBuffer buf)
    {
        buf.writeShort(errcode);
        buf.writeInt(propid);
    }
}