package com.ft.gmserver.httpclient;

import java.net.URI;
import java.util.Collections;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.ft.gmserver.GMAction;
import com.ft.gmserver.SubPage;
import com.ft.gmserver.SubPageGM;
import com.ft.gmserver.SubPageKPI;
import com.ft.gmserver.SubPageLog;
import com.ft.gmserver.SubPageManager;
import com.ft.gmserver.SubPageMonitor;
import com.ft.gmserver.pages.pageerror;
import com.ft.gmserver.pages.pageheader;
import com.ft.gmserver.pages.pagelogin;
import com.ft.gmserver.pages.pagemain;
import com.ft.gmserver.pages.pageslide;
import com.ft.gmserver.struct.structAccount;

import dbobjects.gmdb.GMAccount;
import engine.common.TimeCreator;
import engine.db.DBManager;
import engine.db.client.DBType;
import engine.server.ServerBase;
import engine.string.StringUtil;
import engine.util.Utility;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.buffer.UnpooledByteBufAllocator;
import io.netty.channel.Channel;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpContent;
import io.netty.handler.codec.http.HttpHeaderNames;
import io.netty.handler.codec.http.HttpMethod;
import io.netty.handler.codec.http.HttpRequest;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.netty.handler.codec.http.HttpVersion;
import io.netty.handler.codec.http.cookie.Cookie;
import io.netty.handler.codec.http.cookie.ServerCookieDecoder;
import io.netty.handler.codec.http.websocketx.WebSocketServerHandshaker;
import io.netty.handler.codec.http.websocketx.WebSocketServerHandshakerFactory;
import io.netty.util.CharsetUtil;

/**
 * http连接的client
 * 
 * @author cxz
 *
 */
@Controller
@Scope("prototype")
public class Client {

	public Client(Channel channel, HttpRequest request) {
		this.channel = channel;
		this.request = request;
		this.token = getToken();
		if (token != null)
			this.accountinfo = ClientManage.session.get(token);
	}

	private Channel channel;
	private HttpRequest request;
	private String token;
	public structAccount accountinfo;

	private ByteBuf buffer_body = UnpooledByteBufAllocator.DEFAULT.buffer();
	private pageheader header = new pageheader();
	private SubPage page;
	public final StringBuilder responseContent = new StringBuilder();
	public GMAction action = GMAction.Null;

	public void handleHttpRequest() throws Exception {
		if (request.method() == HttpMethod.GET) // 处理GET请求
			if ("websocket".equals(request.headers().get("Upgrade"))) // WebSocket第一次请求
				doWebsocket();
			else
				doGet();
		else if (request.method() == HttpMethod.POST) // 处理POST请求
			doPost();
	}

	private void doWebsocket() throws Exception {
		// 构造握手响应返回，本机测试
		WebSocketServerHandshakerFactory wsFactory = new WebSocketServerHandshakerFactory(ServerBase.configs.GetStrConfig("websocketserver", "ws://127.0.0.1:8080"), null, false);
		WebSocketServerHandshaker handshaker = wsFactory.newHandshaker(request);
		if (handshaker == null)
			WebSocketServerHandshakerFactory.sendUnsupportedVersionResponse(channel);
		else
			handshaker.handshake(channel, request);

		Websocket websocket = WebsocketManage.websockets.get(channel);
		if (websocket == null) {
			websocket = new Websocket(request.uri(), channel, handshaker);
			WebsocketManage.websockets.put(channel, websocket);
		}
		WebsocketManage.msg2websocket();
	}

	private void doGet() throws Exception {
		if (accountinfo == null) {
			writeLogin();
			return;
		}

		URI uri = new URI(request.uri());

		if (uri.getPath().equals("/logout")) {
			writeLogin();
			ClientManage.session.remove(token);
			return;
		}

		// 免登陆，重定向到首页
		if (uri.getPath().equals("/") && ClientManage.session.containsKey(token)) {
			writeMainPage();
			return;
		}

		setpage(uri);
		page.AnalyzeUri(uri.getPath(), uri.getQuery());
	}

	private void doPost() throws Exception {
		buffer_body.writeBytes(((HttpContent) request).content());
		String str = buffer_body.toString(CharsetUtil.UTF_8);

		URI uri = new URI(request.uri());

		if (uri.getPath().equals("/login")) {
			checkLogin(str);
			return;
		}

		setpage(uri);
		page.AnalyzeUri(uri.getPath(), str);
		page.AnalyzeParam(str);
	}

	private void setpage(URI uri) {
		if (uri.getPath().indexOf("/manager") > -1) {
			page = new SubPageManager(this);
		} else if (uri.getPath().indexOf("/kpi") > -1) {
			page = new SubPageKPI(this);
		} else if (uri.getPath().indexOf("/gmtool") > -1) {
			page = new SubPageGM(this);
		} else if (uri.getPath().indexOf("/log") > -1) {
			page = new SubPageLog(this);
		} else if (uri.getPath().indexOf("/monitor") > -1) {
			page = new SubPageMonitor(this);
		}
	}

	/**
	 * 检测gm的账号
	 * 
	 * @param request
	 * @throws Exception
	 */
	private void checkLogin(String str) throws Exception {
		Map<String, String> params = StringUtil.split(str, "&", "=");
		String account = params.get("account");
		String pass = params.get("password");

		if (account == null || account.isEmpty() || pass == null || pass.isEmpty()) {
			writeErrorPage("Account or password can't be Empty! ");
			return;
		}

		String password = Utility.md5_32(pass);
		GMAccount accountdata = DBManager.getUniqu(GMAccount.class, "account", account, DBType.GMDB);
		if (accountdata == null) {
			writeErrorPage("No This Account info：" + account);
			return;
		}
		if (!accountdata.password.equals(password)) {
			writeErrorPage("Account or Password is Error!");
			return;
		}
		accountdata.lastlogintime = TimeCreator.GetTimeStamp();
		accountinfo = new structAccount(accountdata);

		String token = Utility.md5_32("wxft" + account + pass + System.currentTimeMillis());
		ClientManage.session.put(token, accountinfo);

		DBManager.saveOrUpdate(accountdata, DBType.GMDB);

		writeMainPage_setCookie(token);

	}

	private void writeMainPage_setCookie(String token) {
		writeHeader();
		writeSlide();
		responseContent.append(new pagemain().content());

		responseContent.append("</body>");
		responseContent.append("</html>");
		ByteBuf buf = Unpooled.copiedBuffer(responseContent.toString(), CharsetUtil.UTF_8);
		FullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK, buf);
		response.headers().set(HttpHeaderNames.CONTENT_TYPE, "text/html; charset=UTF-8");
		response.headers().set(HttpHeaderNames.CONTENT_LENGTH, buf.readableBytes());

		response.headers().add(HttpHeaderNames.SET_COOKIE, "wxftgmtoken=" + token);

		channel.writeAndFlush(response);
	}

	private String getToken() {
		Set<Cookie> cookies;
		String cookie = request.headers().get(HttpHeaderNames.COOKIE);
		if (cookie == null)
			cookies = Collections.emptySet();
		else
			cookies = ServerCookieDecoder.STRICT.decode(cookie);

		for (Cookie o : cookies)
			if (StringUtils.equals("wxftgmtoken", o.name()))
				return o.value();

		return null;
	}

	private void writeMainPage() {
		writeHeader();
		writeSlide();
		responseContent.append(new pagemain().content());
		writeEnd();
	}

	private void writeLogin() {
		writeHeader();
		responseContent.append(new pagelogin().content());
		writeEnd();
	}

	public void writeHeader() {
		responseContent.setLength(0);
		responseContent.append(header.content());
	}

	public void writeEnd() {
		responseContent.append("</body>");
		responseContent.append("</html>");

		ByteBuf buf = Unpooled.copiedBuffer(responseContent.toString(), CharsetUtil.UTF_8);
		FullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK, buf);
		response.headers().set(HttpHeaderNames.CONTENT_TYPE, "text/html; charset=UTF-8");
		response.headers().set(HttpHeaderNames.CONTENT_LENGTH, buf.readableBytes());
		channel.writeAndFlush(response);
	}

	public void writeSlide() {
		responseContent.append(new pageslide(accountinfo).content());
	}

	/**
	 * 输出错误页面
	 * 
	 * @param str
	 */
	public void writeErrorPage(String str) {
		writeHeader();
		responseContent.append(new pageerror(str).content());
		writeEnd();
	}

	/**
	 * 修改了自己的权限。
	 * 
	 * @param account
	 */
	public void onChangePrivilege(GMAccount account) {
		accountinfo = new structAccount(account);
	}

	public void update() {
		if (page != null) {
			page.update();
		}
	}

}
