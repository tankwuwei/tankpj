package dbobjects.gmdb;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.Table;

import engine.common.TimeCreator;
import engine.core.IDBObject;
import engine.db.DBObject;

@Entity
@Table(name = "gmprocess", indexes = { @Index(name = "caseid", columnList = "caseid") })
public class CaseProcess extends DBObject  {

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
	public long caseid;
	public String gmname;
	public int acttype;
	public String remarks;
	public int processtime;

	public String getGmname() {
		return gmname;
	}

	public String getActtype() {
//		if (acttype == SubPageGM.CaseNew) {
//			
//		}
//		return acttype;
		return null;
	}
	

	public String getRemarks() {
		return remarks;
	}

	public String getProcesstime() {
		return TimeCreator.GetStringTime(processtime);
	}



/////////// CODE_GEN_START//////////

public void write(engine.net.NativeBuffer buf){
buf.writeLong(id);
buf.writeLong(caseid);
buf.writeUTF(gmname);
buf.writeInt(acttype);
buf.writeUTF(remarks);
buf.writeInt(processtime);
}

public void read(engine.net.NativeBuffer buf){
id=buf.readLong();
caseid=buf.readLong();
gmname=buf.readUTF();
acttype=buf.readInt();
remarks=buf.readUTF();
processtime=buf.readInt();
}


///////////// CODE_GEN_END////////////

}
