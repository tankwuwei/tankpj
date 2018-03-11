package generated.cgame.packets.server;
import engine.net.*;
import generated.cgame.packets.objects.*;

@SuppressWarnings("unused")
public class RequestLoginSvrRet extends CPacket
{
    public RequestLoginSvrRet()
    {
        code=503;
    }
    
    public  int retcode;
    public  RequestLoginSvrInfo[] gamesvrs;
    
    public void read(engine.net.NativeBuffer buf)
    {
        retcode=buf.readInt();
        int gamesvrsLength=buf.readShort();
        gamesvrs=new RequestLoginSvrInfo[gamesvrsLength];
        for(int i=0;i<gamesvrsLength;i++)
        {
            RequestLoginSvrInfo d=new RequestLoginSvrInfo();
            if(buf.readByte()==1)
            {
                d.read(buf);
                gamesvrs[i]=d;
            }
        }
    }
    
    public void write(engine.net.NativeBuffer buf)
    {
        buf.writeInt(retcode);
        buf.writeArray(gamesvrs);
    }
}