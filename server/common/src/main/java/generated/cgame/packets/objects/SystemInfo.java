package generated.cgame.packets.objects;
import engine.net.*;
import generated.cgame.packets.objects.*;

@SuppressWarnings("unused")
public class SystemInfo extends CValue
{
    
    public  String network;
    public  String os;
    public  int cpucount;
    public  int syscpuidle;
    public  long sysmem;
    public  long sysmemfree;
    public  String ip;
    public  int sampleTime;
    
    public void read(engine.net.NativeBuffer buf)
    {
        network=buf.readUTF();
        os=buf.readUTF();
        cpucount=buf.readInt();
        syscpuidle=buf.readInt();
        sysmem=buf.readLong();
        sysmemfree=buf.readLong();
        ip=buf.readUTF();
        sampleTime=buf.readInt();
    }
    
    public void write(engine.net.NativeBuffer buf)
    {
        buf.writeUTF(network);
        buf.writeUTF(os);
        buf.writeInt(cpucount);
        buf.writeInt(syscpuidle);
        buf.writeLong(sysmem);
        buf.writeLong(sysmemfree);
        buf.writeUTF(ip);
        buf.writeInt(sampleTime);
    }
}