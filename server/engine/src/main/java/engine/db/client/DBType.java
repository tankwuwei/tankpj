package engine.db.client;


public class DBType {
	public final static int GlobalDB = 1;
	public final static int GameDB = 2;
	public final static int LogDB = 3;
	public final static int GMDB = 4;

	public static String GetDBName(int type) {
		switch (type) {
		case GlobalDB:
			return "globaldb";
		case GameDB:
			return "gamedb";
		case LogDB:
			return "logdb";
		case GMDB:
			return "gmdb";
		default:
			return null;
		}
	}

}
