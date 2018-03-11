package com.ft.loginserver.resources;

import java.util.Map;

import engine.util.ResourceBase;

public class ServerName extends ResourceBase {

	public String servername;

	@Override
	public void parse(Map<String, String> it) {
		id = Integer.parseInt(it.get("id"));
		servername = it.get("servername");
	}

}
