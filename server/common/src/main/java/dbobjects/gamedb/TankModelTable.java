package dbobjects.gamedb;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import engine.common.TimeCreator;
import engine.db.DBObject;

@Entity
@Table(name = "tankmodel_table")
public class TankModelTable extends DBObject {
	@Id
	public long id;

	@Override
	public void setId(long id) {
		this.id = id;
	}

	@Override
	public long getId() {
		return this.id;
	}

	public long accountid;
	public int modelid;
	public int Quality;//等级
	public int barrelid;//火炮id
	public int turretid;//炮塔id
	public int engineid;//引擎id
	public int transid;//传�?�id
	public int bodyid;//车体id
	public int bsale;//是否被卖掉了 
	public int state;//0解锁 1已购�?
	public int maxbullet;//�?大弹药数
	public int barrelautobak;//弹药是否自动补给 0不是自动补给 1是自动补�?
	
	public int[] barrelBulletids={0,0,0};//坦克私有的弹�? 0->AP 1->APCR 2->HE  			保存id
	public int[] barrelBulletcounts={0,0,0};//坦克私有的弹�? 0->AP 1->APCR 2->HE		保存数量
	
	public int[] barrelBulletids_bak={0,0,0};//坦克私有的弹�? 只是作为备份数量使用 0->AP 1->APCR 2->HE  保存id
	public int[] barrelBulletcounts_bak={0,0,0};//坦克私有的弹�? 只是作为备份数量使用 0->AP 1->APCR       保存数量
	
	public int[] itemids={0,0,0};//道具id
	public int[] itemcounts={0,0,0};//道具数量
	//道具备份
	public int[] itemids_bak={0,0,0};
	public int[] itemcounts_bak={0,0,0};
	
	
	public int  developPoints;//tank 私有研发�?
	public int  itemautobak;//道具是否自动补给  0不是自动补给 1是自动补�?
	public int  skillpartid;//当前使用�? �?能部件id 对应 diy部件信息
	public int  usecount;//使用次数 用于判断�?常用tank
	
	public int  diypartids[]={0,0,0};//diy部件信息
	public int  fightlock;//战斗中�?��??出锁�?
	public int  tankmembers[]={0,0,0,0,0};//�?�?5个成员id
	public int  fightlocktime;//锁住tank起始时间
	public int  fightlocksecs;//锁住多少�?

	
	//tank是否战斗锁住 >0锁住 ==0解锁
	public int  FightLockSecs()
	{

		int finalscs=0;
		
		if (fightlock==1) {
			int currtime=TimeCreator.GetTimeStamp();
			int subsces=currtime-fightlocktime;
			finalscs=fightlocksecs-subsces;//还有多少秒解�?
			if (finalscs<=0) {//已经解锁了重置时�? �?
				fightlock=0;
				fightlocktime=0;
				fightlocksecs=0;
				finalscs=0;
			}
		}
		
		return finalscs;
	}

	/////////// CODE_GEN_START//////////

public void write(engine.net.NativeBuffer buf){
buf.writeLong(id);
buf.writeLong(accountid);
buf.writeInt(modelid);
buf.writeInt(Quality);
buf.writeInt(barrelid);
buf.writeInt(turretid);
buf.writeInt(engineid);
buf.writeInt(transid);
buf.writeInt(bodyid);
buf.writeInt(bsale);
buf.writeInt(state);
buf.writeInt(maxbullet);
buf.writeInt(barrelautobak);
buf.writeArray(barrelBulletids);
buf.writeArray(barrelBulletcounts);
buf.writeArray(barrelBulletids_bak);
buf.writeArray(barrelBulletcounts_bak);
buf.writeArray(itemids);
buf.writeArray(itemcounts);
buf.writeArray(itemids_bak);
buf.writeArray(itemcounts_bak);
buf.writeInt(developPoints);
buf.writeInt(itemautobak);
buf.writeInt(skillpartid);
buf.writeInt(usecount);
buf.writeArray(diypartids);
buf.writeInt(fightlock);
buf.writeArray(tankmembers);
buf.writeInt(fightlocktime);
buf.writeInt(fightlocksecs);
}

public void read(engine.net.NativeBuffer buf){
id=buf.readLong();
accountid=buf.readLong();
modelid=buf.readInt();
Quality=buf.readInt();
barrelid=buf.readInt();
turretid=buf.readInt();
engineid=buf.readInt();
transid=buf.readInt();
bodyid=buf.readInt();
bsale=buf.readInt();
state=buf.readInt();
maxbullet=buf.readInt();
barrelautobak=buf.readInt();
barrelBulletids=buf.readIntArray();
barrelBulletcounts=buf.readIntArray();
barrelBulletids_bak=buf.readIntArray();
barrelBulletcounts_bak=buf.readIntArray();
itemids=buf.readIntArray();
itemcounts=buf.readIntArray();
itemids_bak=buf.readIntArray();
itemcounts_bak=buf.readIntArray();
developPoints=buf.readInt();
itemautobak=buf.readInt();
skillpartid=buf.readInt();
usecount=buf.readInt();
diypartids=buf.readIntArray();
fightlock=buf.readInt();
tankmembers=buf.readIntArray();
fightlocktime=buf.readInt();
fightlocksecs=buf.readInt();
}


///////////// CODE_GEN_END////////////
}
