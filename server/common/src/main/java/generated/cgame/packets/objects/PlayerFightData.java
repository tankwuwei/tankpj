package generated.cgame.packets.objects;
import engine.net.*;
import generated.cgame.packets.objects.*;

@SuppressWarnings("unused")
public class PlayerFightData extends CValue
{
    
    public  int playrounds;
    public  int playtime;
    public  int travel;
    public  int avgtravel;
    public  int winrounds;
    public  int top5rounds;
    public  int winratio;
    public  int top5ratio;
    public  int destorys;
    public  int totaldamage;
    public  int avgdamage;
    public  int kdratio;
    public  int totalhurt;
    public  int avghurt;
    
    public void read(engine.net.NativeBuffer buf)
    {
        playrounds=buf.readInt();
        playtime=buf.readInt();
        travel=buf.readInt();
        avgtravel=buf.readInt();
        winrounds=buf.readInt();
        top5rounds=buf.readInt();
        winratio=buf.readInt();
        top5ratio=buf.readInt();
        destorys=buf.readInt();
        totaldamage=buf.readInt();
        avgdamage=buf.readInt();
        kdratio=buf.readInt();
        totalhurt=buf.readInt();
        avghurt=buf.readInt();
    }
    
    public void write(engine.net.NativeBuffer buf)
    {
        buf.writeInt(playrounds);
        buf.writeInt(playtime);
        buf.writeInt(travel);
        buf.writeInt(avgtravel);
        buf.writeInt(winrounds);
        buf.writeInt(top5rounds);
        buf.writeInt(winratio);
        buf.writeInt(top5ratio);
        buf.writeInt(destorys);
        buf.writeInt(totaldamage);
        buf.writeInt(avgdamage);
        buf.writeInt(kdratio);
        buf.writeInt(totalhurt);
        buf.writeInt(avghurt);
    }
}