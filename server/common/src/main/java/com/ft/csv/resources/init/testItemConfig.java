package com.ft.csv.resources.init;

import java.util.Map;

import com.ft.csv.resources.StringExt;

import engine.util.ResourceBase;

public class testItemConfig extends ResourceBase {

	public int account_start;
	public int account_end;

	@Override
	public void parse(Map<String, String> it) {
		account_start = StringExt.getInt(it.get("account_start"));
		account_end = StringExt.getInt(it.get("account_end"));
	}

}
