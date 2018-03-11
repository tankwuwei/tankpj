package com.ft.gameserver.common;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import com.ft.gameserver.clients.Client;

import engine.net.CPacket;

/**
 * @ClassName: Handler
 * @Description: 消息包处理类,功能对应的管理类需extends此类 并重写handle方法
 * @author jichang.yan
 * @date 2016年5月17日 下午1:43:58
 *
 */
public abstract class Handler {

	private static List<Handler> handlers = new ArrayList<>();

	/** 消息包处理类注册, 启动之后调用 */
	@PostConstruct
	private void initial() {
		handlers.add(this);
	}

	/** 消息派发入口 */
	public static void delegate(CPacket packet) {
		Client client = (Client) packet.o;
		for (Handler handler : handlers) {
			handler.handle(client, packet);
		}
	}

	/** 消息包派发方法, 功能handler实现需重写此方法 */
	abstract protected void handle(Client client, CPacket packet);
}
