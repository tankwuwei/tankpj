package dbobjects.gamedb;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.Table;

import engine.db.DBObject;

@Entity
@Table(name = "shopitem", indexes = { @Index(name = "propid", columnList = "propid") })
public class ShopItemTable extends DBObject {

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

	public int propid;
	public int price;
	public short type;// 0:ShopItem_Normal; 1:ShopItem_New; 2:ShopItem_Hot
	public short onsale;// 0:On; 1:Off


/////////// CODE_GEN_START//////////

public void write(engine.net.NativeBuffer buf){
buf.writeLong(id);
buf.writeInt(propid);
buf.writeInt(price);
buf.writeShort(type);
buf.writeShort(onsale);
}

public void read(engine.net.NativeBuffer buf){
id=buf.readLong();
propid=buf.readInt();
price=buf.readInt();
type=buf.readShort();
onsale=buf.readShort();
}


///////////// CODE_GEN_END////////////
}
