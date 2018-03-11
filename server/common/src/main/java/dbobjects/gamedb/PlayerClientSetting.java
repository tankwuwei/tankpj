package dbobjects.gamedb;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.Table;

import engine.db.DBObject;

@Entity
@Table(name = "playerclientsetting", indexes = { @Index(name = "playerid", columnList = "playerid") })
public class PlayerClientSetting extends DBObject {

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

	/**
	 * @全屏模式
	 */
	public short WindowMode;
	/**
	 * @分辨率
	 */
	public short Resolution;
	/**
	 * @垂直同步
	 */
	public short VSync;
	/**
	 * @语言
	 */
	public short Language;
	/**
	 * @质量
	 */
	public short OverallQuality;
	/**
	 * @视图距离
	 */
	public short ViewDistance;
	/**
	 * @抗锯齿
	 */
	public short AntiAliasing;
	/**
	 * 后期处理
	 */
	public short PostProcessing;
	/**
	 * 阴影
	 */
	public short Shadow;
	/**
	 * 贴图
	 */
	public short Texture;
	/**
	 * 特效
	 */
	public short VisualEffect;
	/**
	 * Foliage
	 */
	public short Foliage;
	/**
	 * 分辨率缩放
	 */
	public short ResolutionScalability;

	public String cpu;
	public String gpu;
	public int mem;
	public int gmem;

	/////////// CODE_GEN_START//////////

public void write(engine.net.NativeBuffer buf){
buf.writeLong(id);
buf.writeLong(playerid);
buf.writeShort(WindowMode);
buf.writeShort(Resolution);
buf.writeShort(VSync);
buf.writeShort(Language);
buf.writeShort(OverallQuality);
buf.writeShort(ViewDistance);
buf.writeShort(AntiAliasing);
buf.writeShort(PostProcessing);
buf.writeShort(Shadow);
buf.writeShort(Texture);
buf.writeShort(VisualEffect);
buf.writeShort(Foliage);
buf.writeShort(ResolutionScalability);
buf.writeUTF(cpu);
buf.writeUTF(gpu);
buf.writeInt(mem);
buf.writeInt(gmem);
}

public void read(engine.net.NativeBuffer buf){
id=buf.readLong();
playerid=buf.readLong();
WindowMode=buf.readShort();
Resolution=buf.readShort();
VSync=buf.readShort();
Language=buf.readShort();
OverallQuality=buf.readShort();
ViewDistance=buf.readShort();
AntiAliasing=buf.readShort();
PostProcessing=buf.readShort();
Shadow=buf.readShort();
Texture=buf.readShort();
VisualEffect=buf.readShort();
Foliage=buf.readShort();
ResolutionScalability=buf.readShort();
cpu=buf.readUTF();
gpu=buf.readUTF();
mem=buf.readInt();
gmem=buf.readInt();
}


///////////// CODE_GEN_END////////////
}
