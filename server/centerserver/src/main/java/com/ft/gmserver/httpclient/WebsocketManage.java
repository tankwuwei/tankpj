package com.ft.gmserver.httpclient;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;

import com.ft.gmserver.struct.structAgentServiceInfo;
import com.ft.gmserver.struct.structAgentSystemInfo;

import dbobjects.gmdb.AgentServiceInfo;
import dbobjects.gmdb.AgentSystemInfo;
import engine.db.DBManager;
import engine.db.client.Condition;
import engine.db.client.DBType;
import engine.log.LogUtil;
import engine.util.DateUtils;
import io.netty.channel.Channel;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;

@Controller
public class WebsocketManage {
	public static final Map<Channel, Websocket> websockets = new HashMap<Channel, Websocket>();

	public static void msg2websocket() {
		try {
			for (Map.Entry<Channel, Websocket> entry : websockets.entrySet()) {
				if (entry.getValue().uri.equals("/monitor")) {
					monitor(entry.getKey());
				} else if (entry.getValue().uri.equals("/test")) {
					entry.getKey().writeAndFlush(new TextWebSocketFrame("来自服务器的时间: " + DateUtils.date2String(new Date(), DateUtils.PATTERN_DATE_TIME)));
				}
			}
		} catch (Exception e) {
			LogUtil.warn("msg2websocket", e);
		}
	}

	private static void monitor(Channel chanel) {
		List<AgentSystemInfo> all = DBManager.getbyhql(AgentSystemInfo.class, DBType.GMDB, "FROM AgentSystemInfo GROUP BY ip");

		StringBuilder s = new StringBuilder();
		for (AgentSystemInfo info : all) {
			s.append("<table border='0'><tbody>");
			s.append("<tr bgcolor='#66FF66'>");
			s.append("<th align='left' width='150px'>IP</th>");
			s.append("<th align='left' width='200px'>采样时间</th>");
			s.append("<th align='left' width='250px'>操作系统</th>");
			s.append("<th align='left' width='300px'>网络</th>");
			s.append("<th align='left' width='50px'>cpu数</th>");
			s.append("<th align='left' width='150px'>cpu空闲率(%)</th>");
			s.append("<th align='left' width='100px'>内存(M)</th>");
			s.append("<th align='left' width='100px'>空闲内存(M)</th>");
			s.append("</tr>");

			structAgentSystemInfo data = new structAgentSystemInfo(DBManager.get(AgentSystemInfo.class, DBType.GMDB, Condition.eq("ip", info.ip), Condition.desc("id"), Condition.limit(1)).get(0));
			s.append("<tr>");
			s.append("<td><a href=/monitor/monitoragent?ip=").append(data.ip).append(">").append(data.ip).append("</a></td>");
			s.append("<td>").append(data.sampleTime).append("</td>");
			s.append("<td>").append(data.os).append("</td>");
			s.append("<td>").append(data.network).append("</td>");
			s.append("<td>").append(data.cpucount).append("</td>");
			s.append("<td>").append(data.syscpuidle).append("</td>");
			s.append("<td>").append(data.sysmem).append("</td>");
			s.append("<td>").append(data.sysmemfree).append("</td>");
			s.append("</tr>");

			s.append("<tr><table border='0'><tbody>");

			s.append("<tr bgcolor='#66CCFF'>");
			s.append("<th bgcolor='#FFFFFF' align='left' width='150px'></th>");
			s.append("<th bgcolor='#FFFFFF' align='left' width='200px'></th>");
			s.append("<th align='left' width='150px'>进程名</th>");
			s.append("<th align='left' width='150px'>进程号</th>");
			s.append("<th align='left' width='150px'>区域编号</th>");
			s.append("<th align='left' width='150px'>服务器编号</th>");
			s.append("<th align='left' width='150px'>服务器版本</th>");
			s.append("<th align='left' width='200px'>开始时间</th>");
			s.append("<th align='left' width='100px'>内存</th>");
			s.append("<th align='left' width='100px'>cpu时间</th>");
			s.append("<th align='left' width='100px'>客户数</th>");
			s.append("<th align='left' width='100px'>服务数</th>");
			s.append("</tr>");

			List<AgentServiceInfo> all2 = DBManager.getbyhql(AgentServiceInfo.class, DBType.GMDB, "FROM AgentServiceInfo WHERE ip='" + data.ip + "' GROUP BY processname");
			for (AgentServiceInfo info2 : all2) {
				structAgentServiceInfo data2 = new structAgentServiceInfo(DBManager.get(AgentServiceInfo.class, DBType.GMDB, Condition.eq("ip", info2.ip), Condition.eq("processname", info2.processname), Condition.desc("id"), Condition.limit(1)).get(0));
				s.append("<tr>");
				s.append("<td>").append("</td>");
				s.append("<td>").append(data2.sampleTime).append("</td>");
				s.append("<td><a href=/monitor/monitoragentprocess?ip=").append(data2.ip).append("&processname=").append(data2.processname).append(">").append(data2.processname).append("</a></td>");
				s.append("<td>").append(data2.processid).append("</td>");
				s.append("<td>").append(data2.zoneid).append("</td>");
				s.append("<td>").append(data2.serverid).append("</td>");
				s.append("<td>").append(data2.version).append("</td>");
				s.append("<td>").append(data2.starttime).append("</td>");
				s.append("<td>").append(data2.mem).append("</td>");
				s.append("<td>").append(data2.cpu).append("</td>");
				if (StringUtils.equals(data2.processname, "GameServer"))
					s.append("<td><font color=red>").append(data2.clientcount).append("</font></td>");
				else
					s.append("<td>").append(data2.clientcount).append("</td>");
				s.append("<td>").append(data2.servercount).append("</td>");
				s.append("</tr>");
			}
			s.append("</tbody></table></tr>");
			s.append("</tbody></table>");
			s.append("<hr>");
		}

		chanel.writeAndFlush(new TextWebSocketFrame(s.toString()));
	}

}
