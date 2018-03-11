package dbobjects.logdb;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.Table;

import engine.db.LogDBObject;

@Entity
@Table(name = "logopenbox", indexes = { @Index(name = "nickname", columnList = "nickname") })
public class Logopenbox extends LogDBObject {

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

	public int boxid;
	public String propidInbox;// 箱子中的道具id
	public String numInbox;// 數量

	public short result;

	public int time;

	@Override
	public void setTime(int time) {
		this.time = time;
	}

	public static short result_0 = 0;// 成功
	public static short result_1 = 1;// 失敗


/////////// CODE_GEN_START//////////

public void write(engine.net.NativeBuffer buf){
buf.writeLong(id);
buf.writeLong(playerid);
buf.writeUTF(nickname);
buf.writeInt(boxid);
buf.writeUTF(propidInbox);
buf.writeUTF(numInbox);
buf.writeShort(result);
buf.writeInt(time);
}

public void read(engine.net.NativeBuffer buf){
id=buf.readLong();
playerid=buf.readLong();
nickname=buf.readUTF();
boxid=buf.readInt();
propidInbox=buf.readUTF();
numInbox=buf.readUTF();
result=buf.readShort();
time=buf.readInt();
}


///////////// CODE_GEN_END////////////
}
