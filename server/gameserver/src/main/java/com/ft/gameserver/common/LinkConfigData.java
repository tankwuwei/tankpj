package com.ft.gameserver.common;

public class LinkConfigData {

	public LinkConfigData() {
	}

	public LinkConfigData(String address, int port) {
		this.address = address;
		this.port = port;
	}

	public String getAddress() {
		return address;
	}

	public int getPort() {
		return port;
	}

	public String address = "";
	public int port = 0;
}
