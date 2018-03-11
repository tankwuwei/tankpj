package dbobjects.gamedb;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.Table;

import engine.db.DBObject;

@Entity
@Table(name = "blacklist", indexes = { @Index(name = "ip", columnList = "ip") })
public class BlacklistTable extends DBObject {

	@Id
	public long id;

	@Override
	public void setId(long id) {
		this.id = id;
	}

	@Override
	public long getId() {
		return id;
	}

	public int ip;


/////////// CODE_GEN_START//////////

public void write(engine.net.NativeBuffer buf){
buf.writeLong(id);
buf.writeInt(ip);
}

public void read(engine.net.NativeBuffer buf){
id=buf.readLong();
ip=buf.readInt();
}


///////////// CODE_GEN_END////////////
}
