package com.ft.gmserver;

/**
 * gm账号权限
 * 
 * @author cxz
 *
 */
public class Privilege {
	public static final int Manager = 0x1000000; // 账号管理
	public static final int KPI = 0x100000; // KPI查看
	public static final int Monitor = 0x10000; // monitor
	public static final int GM = 0x100; // 玩家数据查看修改
	public static final int Log = 0x10; // 玩家日志查看
	public static final int Case = 0x1; // Case查看修改

	public static boolean IsManager(int priv) {
		return (priv & Manager) > 0;
	}

	public static boolean CanKPI(int priv) {
		return (priv & KPI) > 0;
	}

	public static boolean CanMonitor(int priv) {
		return (priv & Monitor) > 0;
	}

	public static boolean CanLog(int priv) {
		return (priv & Log) > 0;
	}

	public static boolean CanGM(int priv) {
		return (priv & GM) > 0;
	}

	public static boolean CanCase(int priv) {
		return (priv & Case) > 0;
	}

	public static boolean IsGM(int priv) {
		return CanLog(priv) | CanGM(priv) | CanCase(priv);
	}

	public static int GetFullPrivilege() {
		return Manager | KPI | Monitor | GM | Log | Case;
	}
}
