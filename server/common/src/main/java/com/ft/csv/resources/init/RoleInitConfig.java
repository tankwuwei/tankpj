package com.ft.csv.resources.init;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.persistence.Convert;

import com.ft.csv.resources.StringExt;

import engine.util.ResourceBase;

public class RoleInitConfig extends ResourceBase {

	public String modelids;
	public int 	Silver;
	public int 	Gold;
	public int PublicPoint;
	public int  DevelopPoint;
	public List<Integer>lst=new ArrayList<>();
	public List<Integer>memberlst=new ArrayList<>();
	@Override
	public void parse(Map<String, String> it) {
	
		modelids=it.get("TankID");
		Silver = StringExt.getInt(it.get("Silver"));
		Gold = StringExt.getInt(it.get("Gold"));
		
		PublicPoint=StringExt.getInt(it.get("PublicPoint"));
		DevelopPoint=StringExt.getInt(it.get("DevelopPoint"));

		lst=GetIntLst(it,"TankID");
		memberlst=GetIntLst(it,"tankmembers");
	}


}
