package engine.string;

import java.lang.reflect.Array;
import java.util.ArrayList;

import org.jdom2.Document;
import org.jdom2.Element;

import engine.util.Utility;
/**
 * 随机字符串
 * @author xjf
 *
 */
public class RandomString {
	public static ArrayList<String>[] header;
	public static ArrayList<String>[] footer;
	/**
	 * 疯狂西游的随机名库
	 * @param path
	 */
	public static void initPattern0(String path){
		initList();
		Document doc = Utility.readDocument(path);
		Element root = doc.getRootElement();
		root = root.getChild("first");
		Element r = root.getChild("id_1");
		String hs = r.getAttributeValue("name");
		addStr(hs, header[0]);
		r = root.getChild("id_2");
		addStr(r.getAttributeValue("boytwin"), footer[0]);
		r = root.getChild("id_3");
		addStr(r.getAttributeValue("girltwin"), footer[1]);
	}
	
	@SuppressWarnings("unchecked")
	private static void initList(){
		header = (ArrayList<String>[])Array.newInstance(ArrayList.class, 2);
		footer = (ArrayList<String>[])Array.newInstance(ArrayList.class, 2);
		for (int i = 0; i< header.length; i++){
			header[i]  = new ArrayList<>();
		}
		for (int i = 0; i< footer.length; i++){
			footer[i] = new ArrayList<>();
		}
	}
	
	private static void addStr(String src, ArrayList<String> list){
		String[] ss = src.split(",");
		for (String s:ss){
			list.add(s);
		}
	}
	/**
	 * 得到一个随机字符串
	 * @return
	 */
	public static String getRandomString(){
		return getRandomString(0, 0);
	}
	
	public static String getRandomString(int head, int foot){
		String h = header[head].get(Utility.getRandomBetween(0, header[head].size()-1));
		String f = footer[foot].get(Utility.getRandomBetween(0, footer[foot].size()-1));
		return h+f;
	}
	
	public static void main(String[] args){
		initPattern0("data/loginname.xml");
		for (int i =0; i< 10; i++){
			System.out.println(getRandomString());
		}
	}
}
