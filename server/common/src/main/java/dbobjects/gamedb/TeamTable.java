package dbobjects.gamedb;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import engine.db.DBObject;

@Entity
@Table(name = "team")
public class TeamTable extends DBObject {

	@Id
	public long id;

	public short status;// 0:正常, 1:已解散

	@Override
	public void setId(long id) {
		this.id = id;

	}

	@Override
	public long getId() {
		return this.id;
	}


/////////// CODE_GEN_START//////////

public void write(engine.net.NativeBuffer buf){
buf.writeLong(id);
buf.writeShort(status);
}

public void read(engine.net.NativeBuffer buf){
id=buf.readLong();
status=buf.readShort();
}


///////////// CODE_GEN_END////////////
}
