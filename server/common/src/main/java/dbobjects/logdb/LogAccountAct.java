package dbobjects.logdb;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import engine.db.LogDBObject;

@Entity
@Table(name="logaccount")
public class LogAccountAct extends LogDBObject{

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
	
	public long accountid;
	public String account;
	public short act;
	public int serverid;
	public String ip;
	public short result;
	public int time;
	@Override
	public void setTime(int time) {
		this.time = time;
	}

	
	public static final short act_create = 3;
	public static final short act_createfromusercenter = 4;
	public static final short act_login = 1;
	public static final short act_logout = 2;
	public static final short act_login_steam = 5;



/////////// CODE_GEN_START//////////

public void write(engine.net.NativeBuffer buf){
buf.writeLong(id);
buf.writeLong(accountid);
buf.writeUTF(account);
buf.writeShort(act);
buf.writeInt(serverid);
buf.writeUTF(ip);
buf.writeShort(result);
buf.writeInt(time);
}

public void read(engine.net.NativeBuffer buf){
id=buf.readLong();
accountid=buf.readLong();
account=buf.readUTF();
act=buf.readShort();
serverid=buf.readInt();
ip=buf.readUTF();
result=buf.readShort();
time=buf.readInt();
}


///////////// CODE_GEN_END////////////


}
