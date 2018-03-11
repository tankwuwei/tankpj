package engine.doc;

import engine.util.Utility;

public class Element {

	public org.jdom2.Element e;
	
	public Element(org.jdom2.Element e){
		this.e = e;
	}
	
	public int getInt(String attname){
		return Utility.parseInt(e.getAttributeValue(attname));
	}
	public String getString(String attname){
		return e.getAttributeValue(attname);
	}
	public int[] getArray(String attname, String limit){
		return Utility.parseArray(e.getAttributeValue(attname), limit);
	}
	public int[][] get2Array(String attname, String limit1, String limit2){
		return Utility.parseArrayTwo(e.getAttributeValue(attname), limit1, limit2);
	}
}
