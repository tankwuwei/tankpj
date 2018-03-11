package com.ft.gameserver;
import java.util.ArrayList;
import java.util.List;

import org.asynchttpclient.Param;
import org.springframework.stereotype.Controller;

import engine.http.HttpSimpleManager;
import engine.http.HttpSimpleManager.MyHandler;
import engine.http.HttpSimpleManager.MyHttpClientResult;

@Controller
public class SteamHttp {
	
	
	void SetConfig(String webkey,String appid){
		steamwebkey=webkey;
		SteamAppId=appid;
	}
	
	void Update(){
		httpmgr.UpdateResult();
	}
	
	/*
	 * 返回值
	 * {
	"appownership": {
		"ownsapp": true,
		"permanent": false,
		"timestamp": "2017-12-18T10:04:09Z",
		"ownersteamid": "76561198356316070",
		"result": "OK"
	}
	}
	 * */
	public void CheckSteamAuth(String steamid,MyHandler<MyHttpClientResult> handler){
		
		List<Param> params=new ArrayList<>();
		params.add(new Param("key", steamwebkey));
		params.add(new Param("steamid", steamid));
		params.add(new Param("appid", SteamAppId));
		httpmgr.AddHttpGet(SteamAuth, params, handler);
	}
	
	private HttpSimpleManager httpmgr=new HttpSimpleManager();
	private String steamwebkey="268A5529D97625922270A8D29B94853E";
	private String SteamAppId="1001";
	private String SteamAuth="https://partner.steam-api.com/ISteamUser/CheckAppOwnership/v1/";//登录验证
}
