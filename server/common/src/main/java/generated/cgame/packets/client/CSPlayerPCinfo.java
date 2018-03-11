package generated.cgame.packets.client;
import engine.net.*;
import generated.cgame.packets.objects.*;

@SuppressWarnings("unused")
public class CSPlayerPCinfo extends CPacket
{
    public CSPlayerPCinfo()
    {
        code=7021;
    }
    
    public  String cpu;
    public  String gpu;
    public  int mem;
    public  int gmem;
    
    public void read(engine.net.NativeBuffer buf)
    {
        cpu=buf.readUTF();
        gpu=buf.readUTF();
        mem=buf.readInt();
        gmem=buf.readInt();
    }
    
    public void write(engine.net.NativeBuffer buf)
    {
        buf.writeUTF(cpu);
        buf.writeUTF(gpu);
        buf.writeInt(mem);
        buf.writeInt(gmem);
    }
}