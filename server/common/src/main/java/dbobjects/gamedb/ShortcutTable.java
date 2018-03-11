package dbobjects.gamedb;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.Table;

import engine.db.DBObject;

@Entity
@Table(name = "shortcut", indexes = { @Index(name = "playerid", columnList = "playerid") })
public class ShortcutTable extends DBObject {

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

	public long playerid;

	public String keytype;
	public String value;



/////////// CODE_GEN_START//////////

public void write(engine.net.NativeBuffer buf){
buf.writeLong(id);
buf.writeLong(playerid);
buf.writeUTF(keytype);
buf.writeUTF(value);
}

public void read(engine.net.NativeBuffer buf){
id=buf.readLong();
playerid=buf.readLong();
keytype=buf.readUTF();
value=buf.readUTF();
}


///////////// CODE_GEN_END////////////
}
