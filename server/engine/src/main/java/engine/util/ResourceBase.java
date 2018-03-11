package engine.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public abstract class ResourceBase {

	public int id;

	public int getId() {
		return id;
	}
	
	//1|2|3 返回 lst(1,2,3)
	public List<Integer> GetIntLst(Map<String,String> it,String name)
	{
		List<Integer> ret=new ArrayList<>();
		String namestr=it.get(name);
		if (namestr!=null) {
			
			String[] sourceStrArray = namestr.split("\\|");
			
			for(String val:sourceStrArray)
			{
				ret.add(Integer.parseInt(val));
			}
		}
		
		return ret;
	}

	abstract public void parse(Map<String,String> it);
}
