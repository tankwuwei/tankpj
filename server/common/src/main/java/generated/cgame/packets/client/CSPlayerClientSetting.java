package generated.cgame.packets.client;
import engine.net.*;
import generated.cgame.packets.objects.*;

@SuppressWarnings("unused")
public class CSPlayerClientSetting extends CPacket
{
    public CSPlayerClientSetting()
    {
        code=7020;
    }
    
    public  short[] setting;
    
    public void read(engine.net.NativeBuffer buf)
    {
        setting=buf.readShortArray();
    }
    
    public void write(engine.net.NativeBuffer buf)
    {
        buf.writeArray(setting);
    }
}