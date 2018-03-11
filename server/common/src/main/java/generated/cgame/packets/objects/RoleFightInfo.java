package generated.cgame.packets.objects;
import engine.net.*;
import generated.cgame.packets.objects.*;

@SuppressWarnings("unused")
public class RoleFightInfo extends CValue
{
    
    public  long roleid;
    public  int camp;
    public  String rolename;
    public  String token;
    public  int tankid;
    public  int[] decalid;
    public  int tankfireid;
    public  int tankturedid;
    public  int tankarmouredid;
    public  int tankmoveid;
    public  int tankcarid;
    public  TechnologyInfo[] techs;
    public  int[] bulletids;
    public  int[] bulletcounts;
    public  int[] itemids;
    public  int[] itemcounts;
    public  int skillpartid;
    public  int[] diys;
    public  DriverInfo[] drivers;
    
    public void read(engine.net.NativeBuffer buf)
    {
        roleid=buf.readLong();
        camp=buf.readInt();
        rolename=buf.readUTF();
        token=buf.readUTF();
        tankid=buf.readInt();
        decalid=buf.readIntArray();
        tankfireid=buf.readInt();
        tankturedid=buf.readInt();
        tankarmouredid=buf.readInt();
        tankmoveid=buf.readInt();
        tankcarid=buf.readInt();
        int techsLength=buf.readShort();
        techs=new TechnologyInfo[techsLength];
        for(int i=0;i<techsLength;i++)
        {
            TechnologyInfo d=new TechnologyInfo();
            if(buf.readByte()==1)
            {
                d.read(buf);
                techs[i]=d;
            }
        }
        bulletids=buf.readIntArray();
        bulletcounts=buf.readIntArray();
        itemids=buf.readIntArray();
        itemcounts=buf.readIntArray();
        skillpartid=buf.readInt();
        diys=buf.readIntArray();
        int driversLength=buf.readShort();
        drivers=new DriverInfo[driversLength];
        for(int i=0;i<driversLength;i++)
        {
            DriverInfo d=new DriverInfo();
            if(buf.readByte()==1)
            {
                d.read(buf);
                drivers[i]=d;
            }
        }
    }
    
    public void write(engine.net.NativeBuffer buf)
    {
        buf.writeLong(roleid);
        buf.writeInt(camp);
        buf.writeUTF(rolename);
        buf.writeUTF(token);
        buf.writeInt(tankid);
        buf.writeArray(decalid);
        buf.writeInt(tankfireid);
        buf.writeInt(tankturedid);
        buf.writeInt(tankarmouredid);
        buf.writeInt(tankmoveid);
        buf.writeInt(tankcarid);
        buf.writeArray(techs);
        buf.writeArray(bulletids);
        buf.writeArray(bulletcounts);
        buf.writeArray(itemids);
        buf.writeArray(itemcounts);
        buf.writeInt(skillpartid);
        buf.writeArray(diys);
        buf.writeArray(drivers);
    }
}