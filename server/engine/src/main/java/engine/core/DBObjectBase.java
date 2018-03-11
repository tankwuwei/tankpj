package engine.core;

import java.util.concurrent.locks.ReentrantLock;

public class DBObjectBase {
	protected transient ReentrantLock lock = new ReentrantLock();
	public boolean tryLock() {
		return lock.tryLock();
	}

	public void getLock() {
		lock.lock();
	}

	public void unlock() {
		lock.unlock();
	}

}
