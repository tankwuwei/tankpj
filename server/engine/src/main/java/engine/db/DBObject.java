package engine.db;

import java.io.Serializable;
import java.util.concurrent.locks.ReentrantLock;

import engine.core.IDBObject;

public abstract class DBObject implements IDBObject, Serializable {
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
