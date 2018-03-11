package generated.cgame.packets.server;
import engine.net.*;
import generated.cgame.packets.objects.*;

@SuppressWarnings("unused")
public class SCShortcut extends CPacket
{
    public SCShortcut()
    {
        code=7008;
    }
    
    public  Shortcut[] shortcuts;
    
    public void read(engine.net.NativeBuffer buf)
    {
        int shortcutsLength=buf.readShort();
        shortcuts=new Shortcut[shortcutsLength];
        for(int i=0;i<shortcutsLength;i++)
        {
            Shortcut d=new Shortcut();
            if(buf.readByte()==1)
            {
                d.read(buf);
                shortcuts[i]=d;
            }
        }
    }
    
    public void write(engine.net.NativeBuffer buf)
    {
        buf.writeArray(shortcuts);
    }
}