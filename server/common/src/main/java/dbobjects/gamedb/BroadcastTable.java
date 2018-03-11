package dbobjects.gamedb;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import engine.db.DBObject;

@Entity
@Table(name = "broadcast")
public class BroadcastTable extends DBObject {

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

	public String content;
	public int createtime;
	public short status;// 0:正常; 1:取消


/////////// CODE_GEN_START//////////

public void write(engine.net.NativeBuffer buf){
buf.writeLong(id);
buf.writeUTF(content);
buf.writeInt(createtime);
buf.writeShort(status);
}

public void read(engine.net.NativeBuffer buf){
id=buf.readLong();
content=buf.readUTF();
createtime=buf.readInt();
status=buf.readShort();
}


///////////// CODE_GEN_END////////////
}
