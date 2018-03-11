package com.ft.gameserver.agents;

import java.util.HashSet;
import java.util.Set;

import org.apache.commons.collections.functors.IfClosure;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.ft.gameserver.GameServer;
import com.ft.gameserver.servers.Server;

import common.ErrorCode;
import engine.log.LogUtil;
import engine.net.BaseSession;
import engine.net.CPacket;
import engine.util.Utility;
import generated.cgame.packets.Code;
import generated.cgame.packets.server.AgentLoginGameSvr;
import generated.cgame.packets.server.AgentLoginGameSvrRet;
import generated.cgame.packets.server.StartService;

@Controller
@Scope("prototype")
public class agentSession extends BaseSession {

	@Autowired
	agentManager agentManager;
	public int zoneid;
	public String zonename;
	public int freeCount; // 空闲的fightserver的数量
	private static int g_agentindex = 1;
	public int agentindex = 0;// gamesvr 当前分配的id号

	private Set<Server> freefightserver = new HashSet<>();

	public agentSession() {
		// TODO Auto-generated constructor stub

	}

	@Override
	public boolean processMsg(CPacket packet) {
		// TODO Auto-generated method stub
		if (packet.code != Code.CSPing) {
			// LogUtil.debug(packet.code);
		}

		if (packet.code == Code.VMsgConnect) {
			onConnect();
			return true;
		} else if (packet.code == Code.VMsgDisconnect) {
			onDisconnect();
			return true;
		} else if (packet.code == Code.AgentLoginGameSvr) {
			AgentLoginCheck((AgentLoginGameSvr) (packet));
		} else if (packet.code == Code.AgentLoginGameSvrRet) {// agent原路返回
																// 收到agentindex后才添加然后可以工作了
			agentManager.AddAgent(this);
		}
		return true;
	}

	/*
	 * 校验agent的有效性
	 */
	private void AgentLoginCheck(AgentLoginGameSvr packet) {
		String str = packet.time + "agent" + "wxft#$!$md5";
		String md5check = Utility.md5_32(str);
		AgentLoginGameSvrRet ret = new AgentLoginGameSvrRet();
		ret.ret = ErrorCode.FAILED;
		if (md5check.equals(packet.md5check)) {
			zoneid = packet.zoneid;
			zonename = packet.zonename;
			freeCount = packet.freecount;
			LogUtil.debug("agent[" + zoneid + "][" + zonename + "] 登录验证成功 返回index " + agentindex);
			ret.ret = ErrorCode.SUCCESS;
			ret.index = agentindex;

		} else {
			LogUtil.debug("agent 登录验证失败");

		}
		Send(ret);
	}

	// gamesvr 要求agent启动fightsvr
	public void StartFightServer(int numbid) {
		StartService start = new StartService();
		start.fightid = numbid;
		Send(start);
	}

	@Override
	protected void onConnect() {
		// TODO Auto-generated method stub
		agentindex = g_agentindex;
		g_agentindex++;
		LogUtil.debug("agent session connect " + agentindex);
	}

	@Override
	protected void onDisconnect() {
		// TODO Auto-generated method stub
		LogUtil.debug("agent session disconnect " + agentindex);
		agentManager.RemoveSession(this);
	}

	@Override
	public void update() {
		// TODO Auto-generated method stub

	}

	
	public int getFreeServerCount(){
		return freefightserver.size();
	}
	
	
	/**
	 * 检查空余服务器数量
	 */
	public void checkServerNum() {
		if (freefightserver.size() < freeCount) {
			StartFightServer(GameServer.fightServerLevel);
		}
	}

	public void RemoveFightServer(Server server) {
		freefightserver.remove(server);
	}

	public void AddFightServer(Server server) {
		freefightserver.add(server);
	}

}
