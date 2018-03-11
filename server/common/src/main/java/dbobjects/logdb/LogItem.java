package dbobjects.logdb;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.Table;

import engine.db.LogDBObject;

@Entity
@Table(name = "logitem", indexes = { @Index(name = "playerid", columnList = "playerid") })
public class LogItem extends LogDBObject {

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

	public long playerid;
	public String nickname;
	public int propid;
	public String itemtype;
	public short flag;
	public short result;

	public int time;

	@Override
	public void setTime(int time) {
		this.time = time;
	}

	public static final short FLAG_USE = 1;// 使用道具
	public static final short FLAG_UNUSE = 2;// 卸载道具
	public static final short FLAG_GET = 3;// 获得
	public static final short FLAG_DEL = 4;// 删除


/////////// CODE_GEN_START//////////

public void write(engine.net.NativeBuffer buf){
buf.writeLong(id);
buf.writeLong(playerid);
buf.writeUTF(nickname);
buf.writeInt(propid);
buf.writeUTF(itemtype);
buf.writeShort(flag);
buf.writeShort(result);
buf.writeInt(time);
}

public void read(engine.net.NativeBuffer buf){
id=buf.readLong();
playerid=buf.readLong();
nickname=buf.readUTF();
propid=buf.readInt();
itemtype=buf.readUTF();
flag=buf.readShort();
result=buf.readShort();
time=buf.readInt();
}


///////////// CODE_GEN_END////////////
}
