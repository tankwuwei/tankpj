package dbobjects.gamedb;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.Table;

import engine.db.DBObject;

@Entity
@Table(name = "mailplayer", indexes = { @Index(name = "nickname", columnList = "nickname") })
public class MailPlayerTable extends DBObject {

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

	public String mailtitle;
	public String mailcontent;
	public int createtime;

	public String propid;
	public String num;
	
	public String nickname;

	public short isread;// 0:未读;1:已读
	public short isget;// 0:未领取;1:已领取
	public short isdelete;// 0:正常;1:删除


/////////// CODE_GEN_START//////////

public void write(engine.net.NativeBuffer buf){
buf.writeLong(id);
buf.writeUTF(mailtitle);
buf.writeUTF(mailcontent);
buf.writeInt(createtime);
buf.writeUTF(propid);
buf.writeUTF(num);
buf.writeUTF(nickname);
buf.writeShort(isread);
buf.writeShort(isget);
buf.writeShort(isdelete);
}

public void read(engine.net.NativeBuffer buf){
id=buf.readLong();
mailtitle=buf.readUTF();
mailcontent=buf.readUTF();
createtime=buf.readInt();
propid=buf.readUTF();
num=buf.readUTF();
nickname=buf.readUTF();
isread=buf.readShort();
isget=buf.readShort();
isdelete=buf.readShort();
}


///////////// CODE_GEN_END////////////
}
