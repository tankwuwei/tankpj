package engine.common.id;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * 一个简单的id创建器，用来产生一个int的id
 * @author xjf
 */
public class SimpleIdGenerator {
	public AtomicInteger idStart;

	public SimpleIdGenerator(int start) {
		idStart = new AtomicInteger(start);
	}

	public int getId() {
		return idStart.incrementAndGet();
	}
}
