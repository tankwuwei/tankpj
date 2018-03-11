package com.ft.gmserver.httpclient;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Controller;

import com.ft.gmserver.struct.structAccount;

import engine.log.LogUtil;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.HttpRequest;
import io.netty.handler.codec.http.websocketx.WebSocketFrame;

@Controller
public class ClientManage {
	public static final Map<String, structAccount> session = new HashMap<String, structAccount>();

	public void OnRequest(ChannelHandlerContext ctx, Object msg) {
		try {
			if (msg instanceof HttpRequest && new URI(((HttpRequest) msg).uri()).getPath().equals("/favicon.ico"))
				return;

			if (msg instanceof HttpRequest)
				new Client(ctx.channel(), (HttpRequest) msg).handleHttpRequest();// 传统HTTP接入
			else if (msg instanceof WebSocketFrame)
				WebsocketManage.websockets.get(ctx.channel()).handleWebSocketFrame((WebSocketFrame) msg);// WebSocket接入

		} catch (Exception e) {
			LogUtil.warn(e);
		}
	}

	public void update() {
	}

}
