package generated.cgame.packets.objects;
import engine.net.*;
import generated.cgame.packets.objects.*;

@SuppressWarnings("unused")
public class TechnologyInfo extends CValue
{
    
    public  int techId;
    public  int techLvl;
    
    public void read(engine.net.NativeBuffer buf)
    {
        techId=buf.readInt();
        techLvl=buf.readInt();
    }
    
    public void write(engine.net.NativeBuffer buf)
    {
        buf.writeInt(techId);
        buf.writeInt(techLvl);
    }
}