package gameclient;

public class gameclient {
	public static String appkey = "6969D4282BBA854AED9FB20731F4DAB1";
	
	public gameclient() {
		Session session = new Session();
		session.login();
	}
	
	public static void main(String[] args) {
		new gameclient();
	}

}
