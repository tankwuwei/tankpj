package dbobjects.gamedb;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.Table;

import engine.db.DBObject;

@Entity
@Table(name = "player_fight_statitic", indexes = { @Index(name = "playerid", columnList = "playerid") })
public class PlayerFightStatiticTable extends DBObject {

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
	public String nickname;

	public int playrounds;// 游戏总局数
	public int playtime;// 游戏总时长
	public int travel;// 游戏总行驶（米）
	public int avgtravel;// 平均每局游戏行驶（米）
	public int winrounds;// 获得第一名的次数
	public int top5rounds;// 获得前5名的总次数
	public int winratio;// 获得第一名的概率（吃鸡数/总局数）
	public int top5ratio;// 获得前5名的概率（前5数/总局数）
	public int destorys;// 击杀总数
	public int totaldamage;// 造成的总伤害
	public int avgdamage;// 平均伤害（总伤害/局数）
	public int kdratio;// 击杀/死亡比
	public int totalhurt;// 承受的总伤害
	public int avghurt;// 平均每局的承受伤害

	public int level;


/////////// CODE_GEN_START//////////

public void write(engine.net.NativeBuffer buf){
buf.writeLong(id);
buf.writeLong(playerid);
buf.writeUTF(nickname);
buf.writeInt(playrounds);
buf.writeInt(playtime);
buf.writeInt(travel);
buf.writeInt(avgtravel);
buf.writeInt(winrounds);
buf.writeInt(top5rounds);
buf.writeInt(winratio);
buf.writeInt(top5ratio);
buf.writeInt(destorys);
buf.writeInt(totaldamage);
buf.writeInt(avgdamage);
buf.writeInt(kdratio);
buf.writeInt(totalhurt);
buf.writeInt(avghurt);
buf.writeInt(level);
}

public void read(engine.net.NativeBuffer buf){
id=buf.readLong();
playerid=buf.readLong();
nickname=buf.readUTF();
playrounds=buf.readInt();
playtime=buf.readInt();
travel=buf.readInt();
avgtravel=buf.readInt();
winrounds=buf.readInt();
top5rounds=buf.readInt();
winratio=buf.readInt();
top5ratio=buf.readInt();
destorys=buf.readInt();
totaldamage=buf.readInt();
avgdamage=buf.readInt();
kdratio=buf.readInt();
totalhurt=buf.readInt();
avghurt=buf.readInt();
level=buf.readInt();
}


///////////// CODE_GEN_END////////////
}
