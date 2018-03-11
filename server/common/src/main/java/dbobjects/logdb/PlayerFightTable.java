package dbobjects.logdb;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import engine.db.DBObject;

@Entity
@Table(name = "player_fight", uniqueConstraints ={@UniqueConstraint(columnNames={"zoneid", "serverid", "playerid"})})
public class PlayerFightTable extends DBObject {

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

	public int zoneid;// 区id
	public long serverid;// 战斗服务id

	public long playerid;
	public String nickname;

	public int rank;// 排名
	public int killed;// 击杀数
	public int damage;// 伤害数
	public int hurt;// 承受伤害数
	public int time;// 时长
	public int travel;// 米

	public int deadtime;// 死亡时间
	
	public int rankreward;// 排名奖励
	public int killedreward;// 击杀奖励
	public int damagereward;// 伤害奖励





/////////// CODE_GEN_START//////////

public void write(engine.net.NativeBuffer buf){
buf.writeLong(id);
buf.writeInt(zoneid);
buf.writeLong(serverid);
buf.writeLong(playerid);
buf.writeUTF(nickname);
buf.writeInt(rank);
buf.writeInt(killed);
buf.writeInt(damage);
buf.writeInt(hurt);
buf.writeInt(time);
buf.writeInt(travel);
buf.writeInt(deadtime);
buf.writeInt(rankreward);
buf.writeInt(killedreward);
buf.writeInt(damagereward);
}

public void read(engine.net.NativeBuffer buf){
id=buf.readLong();
zoneid=buf.readInt();
serverid=buf.readLong();
playerid=buf.readLong();
nickname=buf.readUTF();
rank=buf.readInt();
killed=buf.readInt();
damage=buf.readInt();
hurt=buf.readInt();
time=buf.readInt();
travel=buf.readInt();
deadtime=buf.readInt();
rankreward=buf.readInt();
killedreward=buf.readInt();
damagereward=buf.readInt();
}


///////////// CODE_GEN_END////////////
}
