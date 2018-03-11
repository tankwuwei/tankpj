package generated.cgame.packets.objects;
import engine.net.*;
import generated.cgame.packets.objects.*;

@SuppressWarnings("unused")
public class Mail extends CValue
{
    
    public  long mailid;
    public  String mailtitle;
    public  String mailcontent;
    public  int createtime;
    public  short isread;
    public  short isget;
    public  int[] propid;
    public  int[] num;
    
    public void read(engine.net.NativeBuffer buf)
    {
        mailid=buf.readLong();
        mailtitle=buf.readUTF();
        mailcontent=buf.readUTF();
        createtime=buf.readInt();
        isread=buf.readShort();
        isget=buf.readShort();
        propid=buf.readIntArray();
        num=buf.readIntArray();
    }
    
    public void write(engine.net.NativeBuffer buf)
    {
        buf.writeLong(mailid);
        buf.writeUTF(mailtitle);
        buf.writeUTF(mailcontent);
        buf.writeInt(createtime);
        buf.writeShort(isread);
        buf.writeShort(isget);
        buf.writeArray(propid);
        buf.writeArray(num);
    }
}