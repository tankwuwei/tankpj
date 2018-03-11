package dbobjects.gmdb;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;

import engine.common.TimeCreator;
import engine.core.IDBObject;
import engine.db.DBObject;

@Entity
@Table(name = "gmcase")
public class GmCase extends DBObject {

	@Id
	public long id;
	public String account;
	public long roleid;
	public String rolename;
	public String title;
	@Lob
	public String detail;
	public String tel;
	public long qq;
	public String processgm;
	public int createtime;
	public int closetime;

	@Override
	public void setId(long id) {
		this.id = id;
	}
	
	@Override
	public long getId() {
		return id;
	}
	
	public String getAccount() {
		return account;
	}

	public String getRolename() {
		return rolename;
	}

	public String getTitle() {
		return title;
	}

	public String getCreatetime() {
		return TimeCreator.GetStringTime(createtime);
	}

	public String getProcessgm() {
		return processgm;
	}
	public long getQq() {
		return qq;
	}
	public String getTel() {
		return tel;
	}
	public String getDetail() {
		return detail;
	}



/////////// CODE_GEN_START//////////

public void write(engine.net.NativeBuffer buf){
buf.writeLong(id);
buf.writeUTF(account);
buf.writeLong(roleid);
buf.writeUTF(rolename);
buf.writeUTF(title);
buf.writeUTF(detail);
buf.writeUTF(tel);
buf.writeLong(qq);
buf.writeUTF(processgm);
buf.writeInt(createtime);
buf.writeInt(closetime);
}

public void read(engine.net.NativeBuffer buf){
id=buf.readLong();
account=buf.readUTF();
roleid=buf.readLong();
rolename=buf.readUTF();
title=buf.readUTF();
detail=buf.readUTF();
tel=buf.readUTF();
qq=buf.readLong();
processgm=buf.readUTF();
createtime=buf.readInt();
closetime=buf.readInt();
}


///////////// CODE_GEN_END////////////


}
