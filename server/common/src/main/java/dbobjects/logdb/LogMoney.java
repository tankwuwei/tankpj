package dbobjects.logdb;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import engine.db.LogDBObject;

@Entity
@Table(name = "LogMoney")
public class LogMoney extends LogDBObject {

	@Override
	public void setId(long id) {
		this.id = id;
	}

	@Override
	public long getId() {
		return id;
	}

	@Override
	public void setTime(int time) {
		this.time = time;
	}

	@Id
	public long id;
	public long roleid;
	public int changevalue;//用了多少
	public int changetype;//哪种货币
	public int reason;//哪种消�?�行�?
	public int time;

	/////////// CODE_GEN_START//////////

public void write(engine.net.NativeBuffer buf){
buf.writeLong(id);
buf.writeLong(roleid);
buf.writeInt(changevalue);
buf.writeInt(changetype);
buf.writeInt(reason);
buf.writeInt(time);
}

public void read(engine.net.NativeBuffer buf){
id=buf.readLong();
roleid=buf.readLong();
changevalue=buf.readInt();
changetype=buf.readInt();
reason=buf.readInt();
time=buf.readInt();
}


///////////// CODE_GEN_END////////////
}
