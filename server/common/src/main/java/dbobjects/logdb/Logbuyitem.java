package dbobjects.logdb;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.Table;

import engine.db.LogDBObject;

@Entity
@Table(name = "logbuyitem", indexes = { @Index(name = "nickname", columnList = "nickname") })
public class Logbuyitem extends LogDBObject {

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
	public int price;

	public short result;

	public int time;

	@Override
	public void setTime(int time) {
		this.time = time;
	}

	public static short result_0 = 0;// 成功
	public static short result_1 = 1;// 金币不足

	/////////// CODE_GEN_START//////////

public void write(engine.net.NativeBuffer buf){
buf.writeLong(id);
buf.writeLong(playerid);
buf.writeUTF(nickname);
buf.writeInt(propid);
buf.writeInt(price);
buf.writeShort(result);
buf.writeInt(time);
}

public void read(engine.net.NativeBuffer buf){
id=buf.readLong();
playerid=buf.readLong();
nickname=buf.readUTF();
propid=buf.readInt();
price=buf.readInt();
result=buf.readShort();
time=buf.readInt();
}


///////////// CODE_GEN_END////////////
}
