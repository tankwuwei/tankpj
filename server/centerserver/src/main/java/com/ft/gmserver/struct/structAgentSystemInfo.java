package com.ft.gmserver.struct;

import dbobjects.gmdb.AgentSystemInfo;
import engine.common.TimeCreator;

public class structAgentSystemInfo {
	
	public long id;
    public  String network;
    public  String os;
    public  int cpucount;
    public  int syscpuidle;
    public  String sysmem;
    public  String sysmemfree;
    public  String ip;
    public  String sampleTime;

	public structAgentSystemInfo(AgentSystemInfo data) {
		id = data.id;
		network = data.network;
		os = data.os;
		cpucount = data.cpucount;
		syscpuidle = data.syscpuidle;
		sysmem = Long.toString(data.sysmem/1024L/1024);
		sysmemfree = Long.toString(data.sysmemfree/1024L/1024);
		ip = data.ip;
		sampleTime = TimeCreator.GetStringTime(data.sampleTime);
	}

	public long getId() {
		return id;
	}

	public String getNetwork() {
		return network;
	}

	public String getOs() {
		return os;
	}

	public int getCpucount() {
		return cpucount;
	}

	public int getSyscpuidle() {
		return syscpuidle;
	}

	public String getSysmem() {
		return sysmem;
	}

	public String getSysmemfree() {
		return sysmemfree;
	}

	public String getIp() {
		return ip;
	}

	public String getSampleTime() {
		return sampleTime;
	}


}
