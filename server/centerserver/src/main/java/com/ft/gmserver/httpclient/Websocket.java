package com.ft.gmserver.httpclient;

import java.util.Map;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import io.netty.channel.Channel;
import io.netty.handler.codec.http.websocketx.CloseWebSocketFrame;
import io.netty.handler.codec.http.websocketx.PingWebSocketFrame;
import io.netty.handler.codec.http.websocketx.PongWebSocketFrame;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketServerHandshaker;

@Controller
@Scope("prototype")
public class Websocket {

	public Websocket() {
	}

	public Websocket(String uri, Channel channel, WebSocketServerHandshaker handshaker) {
		this.uri = uri;
		this.channel = channel;
		this.handshaker = handshaker;
	}

	public String uri;
	private Channel channel;
	private WebSocketServerHandshaker handshaker;

	public void handleWebSocketFrame(WebSocketFrame frame) {
		// 判断是否是关闭链路的指令
		if (frame instanceof CloseWebSocketFrame) {
			handshaker.close(channel, (CloseWebSocketFrame) frame.retain());
			WebsocketManage.websockets.remove(channel);
			return;
		}

		// 判断是否是Ping消息
		if (frame instanceof PingWebSocketFrame) {
			channel.writeAndFlush(new PongWebSocketFrame(frame.content().retain()));
			return;
		}

		// 本例程仅支持文本消息，不支持二进制消息
		if (!(frame instanceof TextWebSocketFrame)) {
			throw new UnsupportedOperationException(String.format("%s frame types not supported", frame.getClass().getName()));
		}

		// 返回应答消息
		String msg = ((TextWebSocketFrame) frame).text();

		for (Map.Entry<Channel, Websocket> client : WebsocketManage.websockets.entrySet()) {
			if (client.getValue().uri.equals("/test")) {
				client.getKey().writeAndFlush(new TextWebSocketFrame("来自服务端的消息：" + msg));
			}
		}
	}

}
