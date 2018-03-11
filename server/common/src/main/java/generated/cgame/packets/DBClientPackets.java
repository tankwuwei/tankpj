package generated.cgame.packets;
import java.util.*;
import engine.net.*;

public class DBClientPackets
{
    private static final Map<Integer,CPacket> packets=new HashMap<Integer,CPacket>();
    static
    {
    }
    
    public static CPacket get(engine.net.NativeBuffer buf)
    {
        int code=buf.readShort();
        CPacket packet=packets.get(code);
        if(packet==null) packet=new CPacket();
        else
        {
            try
            {
                packet=(CPacket)packet.clone();
            }
            catch(Exception ex)
            {
                ex.printStackTrace();
            }
        }
        packet.code=code;
        packet.read(buf);
        return packet;
    }
}