package dbobjects.gamedb;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import engine.db.DBObject;

@Entity
@Table(name = "nickname_table")
public class NickNameTable extends DBObject {

	@Id
	public long id;
	
	@Override
	public void setId(long id) {
		this.id = id;
	}

	@Override
	public long getId() {
		return this.id;
	}
	
	public String NickName;

/////////// CODE_GEN_START//////////

public void write(engine.net.NativeBuffer buf){
buf.writeLong(id);
buf.writeUTF(NickName);
}

public void read(engine.net.NativeBuffer buf){
id=buf.readLong();
NickName=buf.readUTF();
}


///////////// CODE_GEN_END////////////
}
