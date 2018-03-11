package com.ft.gmserver.struct;

import dbobjects.gmdb.AgentServiceInfo;
import engine.common.TimeCreator;

public class structAgentServiceInfo {
	
	public long id;
    public  String processname;
    public  int serverid;
    public  int zoneid;
    public  String version;
    public  long processid;
    public  String starttime;
    public  String mem;
    public  String cpu;
    public  int clientcount;
    public  int servercount;
    public  String ip;
    public  String sampleTime;


	public structAgentServiceInfo(AgentServiceInfo data) {
		id = data.id;
		processname = data.processname;
		serverid = data.serverid;
		zoneid = data.zoneid;
		version = data.version;
		processid = data.processid;
		starttime = data.starttime;
		mem = data.mem;
		cpu = data.cpu;
		clientcount = data.clientcount;
		servercount = data.servercount;
		ip = data.ip;
		sampleTime = TimeCreator.GetStringTime(data.sampleTime);
	}


	public long getId() {
		return id;
	}


	public String getProcessname() {
		return processname;
	}


	public int getServerid() {
		return serverid;
	}


	public int getZoneid() {
		return zoneid;
	}


	public String getVersion() {
		return version;
	}


	public long getProcessid() {
		return processid;
	}


	public String getStarttime() {
		return starttime;
	}


	public String getMem() {
		return mem;
	}


	public String getCpu() {
		return cpu;
	}


	public int getClientcount() {
		return clientcount;
	}


	public int getServercount() {
		return servercount;
	}


	public String getIp() {
		return ip;
	}

	public String getSampleTime() {
		return sampleTime;
	}

}
