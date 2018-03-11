package engine.log;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import engine.job.JobManager;

/**
 * 计数器
 * @author xjf
 */
public class Countor {
	public Map<String, AtomicInteger> all = new HashMap<>();

	public Countor(int printDelay) {
		JobManager.schedule(() -> {
			print();
		}, 1000, printDelay * 1000);
	}

	public synchronized void inc(String key) {
		if (all.containsKey(key)) {
			all.get(key).incrementAndGet();
		} else {
			AtomicInteger v = new AtomicInteger(1);
			all.put(key, v);
		}
	}

	private synchronized void print() {
		if (all.size() == 0)
			return;
		LogUtil.info(all.toString());
	}
}
