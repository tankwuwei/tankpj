package dbobjects.gamedb;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.Table;

import engine.db.DBObject;

@Entity
@Table(name = "player", indexes = { @Index(name = "account", columnList = "account") })
public class PlayerData extends DBObject {

	@Id
	public long id;

	public long account;

	public int level;

	public int exp;

	public int money;// 银币

	public int gold;// 金币

	public int createTime;

	public int lastLoginTime;

	public int lastLogoutTime;

	public int blocktime;

	public int playtimesecs; // 已经玩了多长时间秒数 用于防沉�?

	public int allfightsecs; // 玩家总共的游戏事件

	public String nickname;

	public int givetimes;

	public long fightserver; // 玩家所在的战服id

	public short status;// 0:online, 1:offline, 2:gaming, 3:in team

	public long teamid;

	@Override
	public void setId(long id) {
		this.id = id;

	}

	@Override
	public long getId() {
		return this.id;
	}


/////////// CODE_GEN_START//////////

public void write(engine.net.NativeBuffer buf){
buf.writeLong(id);
buf.writeLong(account);
buf.writeInt(level);
buf.writeInt(exp);
buf.writeInt(money);
buf.writeInt(gold);
buf.writeInt(createTime);
buf.writeInt(lastLoginTime);
buf.writeInt(lastLogoutTime);
buf.writeInt(blocktime);
buf.writeInt(playtimesecs);
buf.writeInt(allfightsecs);
buf.writeUTF(nickname);
buf.writeInt(givetimes);
buf.writeLong(fightserver);
buf.writeShort(status);
buf.writeLong(teamid);
}

public void read(engine.net.NativeBuffer buf){
id=buf.readLong();
account=buf.readLong();
level=buf.readInt();
exp=buf.readInt();
money=buf.readInt();
gold=buf.readInt();
createTime=buf.readInt();
lastLoginTime=buf.readInt();
lastLogoutTime=buf.readInt();
blocktime=buf.readInt();
playtimesecs=buf.readInt();
allfightsecs=buf.readInt();
nickname=buf.readUTF();
givetimes=buf.readInt();
fightserver=buf.readLong();
status=buf.readShort();
teamid=buf.readLong();
}


///////////// CODE_GEN_END////////////
}
