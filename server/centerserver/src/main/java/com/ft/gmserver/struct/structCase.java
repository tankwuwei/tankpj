package com.ft.gmserver.struct;

import dbobjects.gmdb.GmCase;

/**
 * case显示用的数据结构
 * @author cxz
 *
 */
public class structCase {
	private long id;
	private String account;
	private long roleid;
	private String rolename;
	private String title;
	private String detail;
	private String tel;
	private long qq;
	private String gmaccount;	// 正在处理的gm

	
	public structCase(GmCase sor) {
		id= sor.id;
		account = sor.account;
		roleid = sor.roleid;
		rolename = sor.rolename;
		title = sor.title;
		detail = sor.detail;
		tel = sor.tel;
		qq = sor.qq;
	}
	
	public String getAccount() {
		return account;
	}

	public String getDetail() {
		return detail;
	}
	public long getId() {
		return id;
	}
	public long getQq() {
		return qq;
	}
	public long getRoleid() {
		return roleid;
	}
	public String getRolename() {
		return rolename;
	}
	public String getTel() {
		return tel;
	}
	public String getTitle() {
		return title;
	}
	
}
