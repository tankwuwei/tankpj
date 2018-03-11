package generated.cgame.packets.server;
import engine.net.*;
import generated.cgame.packets.objects.*;

@SuppressWarnings("unused")
public class SCMail extends CPacket
{
    public SCMail()
    {
        code=7015;
    }
    
    public  Mail[] mails;
    
    public void read(engine.net.NativeBuffer buf)
    {
        int mailsLength=buf.readShort();
        mails=new Mail[mailsLength];
        for(int i=0;i<mailsLength;i++)
        {
            Mail d=new Mail();
            if(buf.readByte()==1)
            {
                d.read(buf);
                mails[i]=d;
            }
        }
    }
    
    public void write(engine.net.NativeBuffer buf)
    {
        buf.writeArray(mails);
    }
}