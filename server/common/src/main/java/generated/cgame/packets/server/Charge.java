package generated.cgame.packets.server;
import engine.net.*;
import generated.cgame.packets.objects.*;

@SuppressWarnings("unused")
public class Charge extends CPacket
{
    public Charge()
    {
        code=404;
    }
    
    public  String sn;
    public  int itme;
    public  String rmb;
    
    public void read(engine.net.NativeBuffer buf)
    {
        sn=buf.readUTF();
        itme=buf.readInt();
        rmb=buf.readUTF();
    }
    
    public void write(engine.net.NativeBuffer buf)
    {
        buf.writeUTF(sn);
        buf.writeInt(itme);
        buf.writeUTF(rmb);
    }
}