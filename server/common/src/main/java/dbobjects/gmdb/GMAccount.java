package dbobjects.gmdb;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import engine.core.IDBObject;
import engine.db.DBObject;

@Entity
@Table(name = "account")
public class GMAccount extends DBObject {
	public void setId(long id) {
		this.id = id;
	}

	public long getId() {
		return id;
	}

	@Id
	public long id;

	public String account;
	public String password;
	public int 	privilege;
	public int lastlogintime;
	public int state;
	


/////////// CODE_GEN_START//////////

public void write(engine.net.NativeBuffer buf){
buf.writeLong(id);
buf.writeUTF(account);
buf.writeUTF(password);
buf.writeInt(privilege);
buf.writeInt(lastlogintime);
buf.writeInt(state);
}

public void read(engine.net.NativeBuffer buf){
id=buf.readLong();
account=buf.readUTF();
password=buf.readUTF();
privilege=buf.readInt();
lastlogintime=buf.readInt();
state=buf.readInt();
}


///////////// CODE_GEN_END////////////


}
