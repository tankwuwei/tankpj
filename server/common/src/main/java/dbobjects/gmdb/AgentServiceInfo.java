package dbobjects.gmdb;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.Table;

import engine.db.DBObject;

@Entity
@Table(name = "agentserviceinfo", indexes = { @Index(name = "ip", columnList = "ip, processname") })
public class AgentServiceInfo extends DBObject {
	public void setId(long id) {
		this.id = id;
	}

	public long getId() {
		return id;
	}

	@Id
	public long id;


	
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


    

/////////// CODE_GEN_START//////////

public void write(engine.net.NativeBuffer buf){
buf.writeLong(id);
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

public void read(engine.net.NativeBuffer buf){
id=buf.readLong();
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


///////////// CODE_GEN_END////////////
}
