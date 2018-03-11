package engine.net;

import java.io.IOException;
import java.io.InputStream;
import java.net.InetSocketAddress;
import java.util.concurrent.Executors;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import com.sun.net.httpserver.spi.HttpServerProvider;

/**
 * 
* @ClassName: HttpNativeServer 
* @Description: JDK自带的HttpServer玩具
* @author jichang.yan 
* @date 2016年3月31日 上午10:39:12 
*
 */

@SuppressWarnings("restriction")
public class HttpNativeServer {
	//启动服务，监听来自客户端的请求  
    public static void start(int port, HttpNativeHandler handler) throws IOException {
		HttpServerProvider provider = HttpServerProvider.provider();  
        HttpServer httpserver =provider.createHttpServer(new InetSocketAddress(port), 100);//监听端口6666,能同时接 受100个请求  
        httpserver.createContext("/", new HttpHandler(){
			@Override
			public void handle(HttpExchange arg0) throws IOException {
				InputStream in = arg0.getRequestBody();
	            byte[] msg = new byte[readInt(in)];
	            in.read(msg);
	            handler.onData(arg0, msg);
			}
			private int readInt(InputStream in) throws IOException{
	        	int length = 0;
				for (int i = 0; i < 4; i++) {
					length = length << 8 | in.read();
				}
				return length;
	        }
        });   
        
        httpserver.setExecutor(Executors.newCachedThreadPool());  
        httpserver.start();  
        System.err.println("HttpNativeServer start at "+port);  
    }  
}
