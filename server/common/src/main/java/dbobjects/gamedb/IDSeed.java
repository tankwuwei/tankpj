package dbobjects.gamedb;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import engine.core.IDBObject;
import engine.db.DBObject;

@Entity
@Table(name = "seed")
public class IDSeed extends DBObject implements IDBObject {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public long id;

	@Override
	public void setId(long id) {
		this.id = id;
	}

	@Override
	public long getId() {
		return id;
	}

	/////////// CODE_GEN_START//////////

public void write(engine.net.NativeBuffer buf){
buf.writeLong(id);
}

public void read(engine.net.NativeBuffer buf){
id=buf.readLong();
}


///////////// CODE_GEN_END////////////

}
