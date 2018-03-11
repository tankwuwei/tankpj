package engine.net;

import com.sun.net.httpserver.HttpExchange;

@SuppressWarnings("restriction")
public interface HttpNativeHandler {
	public void onData(HttpExchange exchange,  byte[] data);
}
