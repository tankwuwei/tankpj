package dbobjects.gamedb;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.Table;

import engine.db.DBObject;

@Entity
@Table(name = "item", indexes = { @Index(name = "playerid", columnList = "playerid") })
public class ItemTable extends DBObject {

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

	public int propid;// 道具id
	public int deadline;// 过期时间

	public short pos;// 槽位位置(从左到右1\2\3\4\5\6\7); 0是背包中的道具

	/////////// CODE_GEN_START//////////

public void write(engine.net.NativeBuffer buf){
buf.writeLong(id);
buf.writeLong(playerid);
buf.writeInt(propid);
buf.writeInt(deadline);
buf.writeShort(pos);
}

public void read(engine.net.NativeBuffer buf){
id=buf.readLong();
playerid=buf.readLong();
propid=buf.readInt();
deadline=buf.readInt();
pos=buf.readShort();
}


///////////// CODE_GEN_END////////////
}
