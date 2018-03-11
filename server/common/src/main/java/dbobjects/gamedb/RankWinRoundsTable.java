package dbobjects.gamedb;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.Table;

import engine.db.DBObject;

@Entity
@Table(name = "rank_winrounds", indexes = { @Index(name = "playerid", columnList = "playerid") })
public class RankWinRoundsTable extends DBObject {

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




/////////// CODE_GEN_START//////////

public void write(engine.net.NativeBuffer buf){
buf.writeLong(id);
buf.writeLong(playerid);
}

public void read(engine.net.NativeBuffer buf){
id=buf.readLong();
playerid=buf.readLong();
}


///////////// CODE_GEN_END////////////
}
