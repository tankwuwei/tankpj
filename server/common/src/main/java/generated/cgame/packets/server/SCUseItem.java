package generated.cgame.packets.server;
import engine.net.*;
import generated.cgame.packets.objects.*;

@SuppressWarnings("unused")
public class SCUseItem extends CPacket
{
    public SCUseItem()
    {
        code=7004;
    }
    
    public  long id;
    public  short pos;
    public  short errcode;
    public  BoxData[] boxdata;
    
    public void read(engine.net.NativeBuffer buf)
    {
        id=buf.readLong();
        pos=buf.readShort();
        errcode=buf.readShort();
        int boxdataLength=buf.readShort();
        boxdata=new BoxData[boxdataLength];
        for(int i=0;i<boxdataLength;i++)
        {
            BoxData d=new BoxData();
            if(buf.readByte()==1)
            {
                d.read(buf);
                boxdata[i]=d;
            }
        }
    }
    
    public void write(engine.net.NativeBuffer buf)
    {
        buf.writeLong(id);
        buf.writeShort(pos);
        buf.writeShort(errcode);
        buf.writeArray(boxdata);
    }
}