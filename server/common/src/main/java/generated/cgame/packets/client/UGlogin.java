package generated.cgame.packets.client;
import engine.net.*;
import generated.cgame.packets.objects.*;

@SuppressWarnings("unused")
public class UGlogin extends CPacket
{
    public UGlogin()
    {
        code=16000;
    }
    
    public  String version;
    public  String map;
    public  String battletype;
    public  short minplayer;
    public  short maxplayer;
    public  String hostname;
    public  String ip;
    public  short port;
    public  int fightnumber;
    public  int agentid;
    
    public void read(engine.net.NativeBuffer buf)
    {
        version=buf.readUTF();
        map=buf.readUTF();
        battletype=buf.readUTF();
        minplayer=buf.readShort();
        maxplayer=buf.readShort();
        hostname=buf.readUTF();
        ip=buf.readUTF();
        port=buf.readShort();
        fightnumber=buf.readInt();
        agentid=buf.readInt();
    }
    
    public void write(engine.net.NativeBuffer buf)
    {
        buf.writeUTF(version);
        buf.writeUTF(map);
        buf.writeUTF(battletype);
        buf.writeShort(minplayer);
        buf.writeShort(maxplayer);
        buf.writeUTF(hostname);
        buf.writeUTF(ip);
        buf.writeShort(port);
        buf.writeInt(fightnumber);
        buf.writeInt(agentid);
    }
}