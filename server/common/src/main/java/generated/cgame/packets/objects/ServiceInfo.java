package generated.cgame.packets.objects;
import engine.net.*;
import generated.cgame.packets.objects.*;

@SuppressWarnings("unused")
public class ServiceInfo extends CValue
{
    
    public  String processname;
    public  int serverid;
    public  int zoneid;
    public  String version;
    public  long processid;
    public  String starttime;
    public  String mem;
    public  String cpu;
    public  int clientcount;
    public  int servercount;
    public  String ip;
    public  int sampleTime;
    
    public void read(engine.net.NativeBuffer buf)
    {
        processname=buf.readUTF();
        serverid=buf.readInt();
        zoneid=buf.readInt();
        version=buf.readUTF();
        processid=buf.readLong();
        starttime=buf.readUTF();
        mem=buf.readUTF();
        cpu=buf.readUTF();
        clientcount=buf.readInt();
        servercount=buf.readInt();
        ip=buf.readUTF();
        sampleTime=buf.readInt();
    }
    
    public void write(engine.net.NativeBuffer buf)
    {
        buf.writeUTF(processname);
        buf.writeInt(serverid);
        buf.writeInt(zoneid);
        buf.writeUTF(version);
        buf.writeLong(processid);
        buf.writeUTF(starttime);
        buf.writeUTF(mem);
        buf.writeUTF(cpu);
        buf.writeInt(clientcount);
        buf.writeInt(servercount);
        buf.writeUTF(ip);
        buf.writeInt(sampleTime);
    }
}