package dbobjects.logdb;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import engine.db.LogDBObject;

@Entity
@Table(name = "logplayer")
public class LogPlayerAct extends LogDBObject {


	public void setId(long id) {
		this.id = id;
	}

	public long getId() {
		return id;
	}
	
	@Id
	public long id;
	public long accountid;
	public String account;
	public long roleid;
	public short act;
	public String ip;
	public short result;
	public int time;
	public int timesecs;//持续时间
	@Override
	public void setTime(int time) {
		this.time = time;
	}


	public static final short act_create = 1;
	public static final short act_login = 2;
	public static final short act_logout = 3;
	

	

/////////// CODE_GEN_START//////////

public void write(engine.net.NativeBuffer buf){
buf.writeLong(id);
buf.writeLong(accountid);
buf.writeUTF(account);
buf.writeLong(roleid);
buf.writeShort(act);
buf.writeUTF(ip);
buf.writeShort(result);
buf.writeInt(time);
buf.writeInt(timesecs);
}

public void read(engine.net.NativeBuffer buf){
id=buf.readLong();
accountid=buf.readLong();
account=buf.readUTF();
roleid=buf.readLong();
act=buf.readShort();
ip=buf.readUTF();
result=buf.readShort();
time=buf.readInt();
timesecs=buf.readInt();
}


///////////// CODE_GEN_END////////////


}
