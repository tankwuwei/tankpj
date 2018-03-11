package engine.http;

import java.util.ArrayList;
import static io.netty.handler.codec.http.HttpHeaderNames.CONTENT_TYPE;
import java.util.List;
import java.util.Vector;

import org.asynchttpclient.AsyncCompletionHandler;
import org.asynchttpclient.AsyncHttpClient;
import org.asynchttpclient.DefaultAsyncHttpClient;
import org.asynchttpclient.Param;
import org.asynchttpclient.Response;

import engine.log.LogUtil;
/*
 * 注意 asynchttpclient版本号 要和netty对应 否则启动时候直接抛出异常
 * 当前版本号 如下
 * 		<dependency>
			<groupId>io.netty</groupId>
			<artifactId>netty-all</artifactId>
			<version>4.1.17.Final</version>
		</dependency>
		
		<dependency>
			<groupId>org.asynchttpclient</groupId>
			<artifactId>async-http-client</artifactId>
			<version>2.1.0-alpha26</version>
		</dependency>

 * */

public class HttpSimpleManager {

	public static final class MyHttpClientResult {
		public Response response;
		public boolean bsuccess;
		private MyHandler<MyHttpClientResult> handler;
	}

	@FunctionalInterface
	public interface MyHandler<E> {

		void handle(E event);
	}

	public void UpdateResult() {

		synchronized (lstresult) {
			if (lstresult.size() > 0) {
				for (MyHttpClientResult val : lstresult) {
					try {

						val.handler.handle(val);
					} catch (Exception e) {
						LogUtil.error("http 处理错误 "+e);
					}
				}
				lstresult.clear();
			}
		}

	}

	private void addResult(MyHttpClientResult result) {
		lstresult.add(result);
	}
	
	

	public void AddHttpGet(String geturl,List<Param> params, MyHandler<MyHttpClientResult> handler) {
		MyHttpClientResult result = new MyHttpClientResult();
		result.handler = handler;

	
		asyncHttpClient.prepareGet(geturl).setQueryParams(params).execute(new AsyncCompletionHandler<Response>() {

			@Override
			public Response onCompleted(Response response) throws Exception {
				result.response = response;
				result.bsuccess = true;
				addResult(result);
				return response;
			}

			@Override
			public void onThrowable(Throwable t) {
				result.response = null;
				result.bsuccess = false;
				addResult(result);
			}
		});
	}
	
	//List<Param> params=new ArrayList<>();
	//params.add(new Param("ss", "11\r"));
	//params.add(new Param("ss2", "112"));
	public void AddHttpPost(String url,List<Param> params,MyHandler<MyHttpClientResult> handler){
		MyHttpClientResult result = new MyHttpClientResult();
		result.handler = handler;
	
		asyncHttpClient.preparePost(url).setFormParams(params).execute(new AsyncCompletionHandler<Response>() {

			@Override
			public Response onCompleted(Response response) throws Exception {
			
				result.response = response;
				result.bsuccess = true;
				addResult(result);
				return response;
			}

			@Override
			public void onThrowable(Throwable t) {
				result.response = null;
				result.bsuccess = false;
				addResult(result);
			}
		});
	}

	private List<MyHttpClientResult> lstresult = new Vector<>();
	AsyncHttpClient asyncHttpClient = new DefaultAsyncHttpClient();
}
