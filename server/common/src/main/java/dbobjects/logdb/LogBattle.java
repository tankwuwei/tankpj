package dbobjects.logdb;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.Table;

import engine.db.LogDBObject;

@Entity
@Table(name = "logbattle", indexes = { @Index(name = "serverid", columnList = "serverid") })
public class LogBattle extends LogDBObject {

	@Override
	public void setId(long id) {
		this.id = id;
	}

	@Override
	public long getId() {
		return id;
	}

	@Id
	public long id;

	public int zoneid;
	public long serverid;

	public int starttime;
	public int endtime;

	public String playerids;

	@Override
	public void setTime(int time) {
	}


/////////// CODE_GEN_START//////////

public void write(engine.net.NativeBuffer buf){
buf.writeLong(id);
buf.writeInt(zoneid);
buf.writeLong(serverid);
buf.writeInt(starttime);
buf.writeInt(endtime);
buf.writeUTF(playerids);
}

public void read(engine.net.NativeBuffer buf){
id=buf.readLong();
zoneid=buf.readInt();
serverid=buf.readLong();
starttime=buf.readInt();
endtime=buf.readInt();
playerids=buf.readUTF();
}


///////////// CODE_GEN_END////////////
}
