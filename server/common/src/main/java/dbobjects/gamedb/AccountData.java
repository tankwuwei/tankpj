package dbobjects.gamedb;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.Table;

import engine.db.DBObject;

@Entity
@Table(name = "account", indexes = { @Index(name = "account", columnList = "account",unique = true) })
public class AccountData extends DBObject {

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

	// 账号
	public String account;
	//
	public String passwd;

	public int Birthday;

	// 封停时间
	public int blocktime;

	public int createtime;

	public int lastlogintime;

	public int lastlogouttime;

	// 当天在线总时�?
	public int totalonlinetime;

	// �?近登录的服务�?
	public int lastserverid;

	/////////// CODE_GEN_START//////////

public void write(engine.net.NativeBuffer buf){
buf.writeLong(id);
buf.writeUTF(account);
buf.writeUTF(passwd);
buf.writeInt(Birthday);
buf.writeInt(blocktime);
buf.writeInt(createtime);
buf.writeInt(lastlogintime);
buf.writeInt(lastlogouttime);
buf.writeInt(totalonlinetime);
buf.writeInt(lastserverid);
}

public void read(engine.net.NativeBuffer buf){
id=buf.readLong();
account=buf.readUTF();
passwd=buf.readUTF();
Birthday=buf.readInt();
blocktime=buf.readInt();
createtime=buf.readInt();
lastlogintime=buf.readInt();
lastlogouttime=buf.readInt();
totalonlinetime=buf.readInt();
lastserverid=buf.readInt();
}


///////////// CODE_GEN_END////////////
}
