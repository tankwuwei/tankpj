package dbobjects.gmdb;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.Table;

import engine.core.IDBObject;
import engine.db.DBObject;

@Entity
@Table(name = "agentsysteminfo", indexes = { @Index(name = "ip", columnList = "ip") })
public class AgentSystemInfo extends DBObject {
	public void setId(long id) {
		this.id = id;
	}

	public long getId() {
		return id;
	}

	@Id
	public long id;

	
    public  String network;
    public  String os;
    public  int cpucount;
    public  int syscpuidle;
    public  long sysmem;
    public  long sysmemfree;
    public  String ip;
    public  int sampleTime;

	


/////////// CODE_GEN_START//////////

public void write(engine.net.NativeBuffer buf){
buf.writeLong(id);
buf.writeUTF(network);
buf.writeUTF(os);
buf.writeInt(cpucount);
buf.writeInt(syscpuidle);
buf.writeLong(sysmem);
buf.writeLong(sysmemfree);
buf.writeUTF(ip);
buf.writeInt(sampleTime);
}

public void read(engine.net.NativeBuffer buf){
id=buf.readLong();
network=buf.readUTF();
os=buf.readUTF();
cpucount=buf.readInt();
syscpuidle=buf.readInt();
sysmem=buf.readLong();
sysmemfree=buf.readLong();
ip=buf.readUTF();
sampleTime=buf.readInt();
}


///////////// CODE_GEN_END////////////


}
