package generated.cgame.packets.server;
import engine.net.*;
import generated.cgame.packets.objects.*;

@SuppressWarnings("unused")
public class CGMail extends CPacket
{
    public CGMail()
    {
        code=31026;
    }
    
    public  String[] nickname;
    public  String mailtitle;
    public  String mailcontent;
    public  int[] propid;
    public  int[] num;
    
    public void read(engine.net.NativeBuffer buf)
    {
        nickname=buf.readUTFArray();
        mailtitle=buf.readUTF();
        mailcontent=buf.readUTF();
        propid=buf.readIntArray();
        num=buf.readIntArray();
    }
    
    public void write(engine.net.NativeBuffer buf)
    {
        buf.writeArray(nickname);
        buf.writeUTF(mailtitle);
        buf.writeUTF(mailcontent);
        buf.writeArray(propid);
        buf.writeArray(num);
    }
}