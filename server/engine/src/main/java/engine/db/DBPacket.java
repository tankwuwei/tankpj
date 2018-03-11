package engine.db;

import engine.core.IReadable;
import engine.core.IWritable;
import engine.net.CPacket;

public class DBPacket extends CPacket implements IWritable, IReadable {
	public long id;
}
