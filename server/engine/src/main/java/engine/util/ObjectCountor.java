package engine.util;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import engine.job.JobManager;

public class ObjectCountor {
	private static final boolean Print = false;
	private String name;
	private Map<Class<?>, AtomicInteger> counts;
	public ObjectCountor(String name, int repeat){
		this.name = name;
		counts = new HashMap<>();
		JobManager.schedule(()->{
			print();
		}, repeat, repeat);
	}
	
	public void inc(Object obj){
		if(!Print)return;
		AtomicInteger v = counts.get(obj.getClass());
		if (v == null){
			counts.put(obj.getClass(), new AtomicInteger(1));
		}
		else{
			v.incrementAndGet();
		}
	}
	
	private void print(){
		if(!Print)return;
		System.out.println("println " + name + ", time="+DateUtils.date2String(new Date(), DateUtils.PATTERN_DATE_TIME));
		for (Object obj:counts.keySet().toArray()){
			System.out.println(obj + ",  count="+counts.get(obj).get());
		}
	}
}
