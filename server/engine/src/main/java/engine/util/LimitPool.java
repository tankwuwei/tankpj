package engine.util;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import engine.job.JobManager;
import engine.log.LogUtil;

/**
 * 有限池，固定数量的池。开始的时候就初始化好需要的量。如果都用完了，就等待
 * @author xjf
 * @param <T>
 */
abstract public class LimitPool<T> {
	/** The highest number of free objects. Can be reset any time. */
	public int peak;

	private final List<T> freeObjects;

	private AtomicInteger count = new AtomicInteger();

	private AtomicInteger adder = new AtomicInteger();

	/** Creates a pool with an initial capacity of 16 and no maximum. */
	public LimitPool() {
		this(16);
	}

	/** @param max The maximum number of free objects to store in this pool. */
	public LimitPool(int initialCapacity) {
		freeObjects = new ArrayList<T>(initialCapacity);
		for (int i = 0; i < initialCapacity; i++) {
			freeObjects.add(newObject());
		}
		count.addAndGet(initialCapacity);
		LogUtil.info("limit pool size=" + count.get());
	}

	public void append(int count) {
		if (adder.get() > 0)
			return; // 已经有在加的了忽略
		adder.set(count);
		for (int i = 0; i < count; i++) {
			free(newObject());
			adder.decrementAndGet();
		}
		this.count.addAndGet(count);
		LogUtil.info("limit pool size=" + this.count.get());
	}

	abstract protected T newObject();

	/**
	 * Returns an object from this pool. The object may be new (from {@link #newObject()}) or reused (previously
	 * {@link #free(Object) freed}).
	 */
	public T obtain() {
		synchronized (freeObjects) {
			if (freeObjects.size() <= 8) {
				JobManager.submit(() -> {
					append(16); // 加8个
					});
			}
			while (freeObjects.size() == 0) {
				try {
					freeObjects.wait();
				} catch (Exception e) {
					LogUtil.error("wait for obtain, but break.", e);
				}
			}
			return freeObjects.remove(freeObjects.size() - 1);
		}
	}

	/**
	 * Puts the specified object in the pool, making it eligible to be returned by {@link #obtain()}. If the pool
	 * already contains {@link #max} free objects, the specified object is reset but not added to the pool.
	 */
	public void free(T object) {
		if (object == null)
			throw new IllegalArgumentException("object cannot be null.");
		synchronized (freeObjects) {
			freeObjects.add(object);
			// peak = Math.max(peak, freeObjects.size());
			if (object instanceof Poolable)
				((Poolable) object).reset();

			freeObjects.notifyAll();
		}
	}

	/**
	 * Puts the specified objects in the pool. Null objects within the array are silently ignored.
	 * @see #free(Object)
	 */
	public void freeAll(List<T> objects) {
		if (objects == null)
			throw new IllegalArgumentException("object cannot be null.");
		synchronized (freeObjects) {
			List<T> freeObjects = this.freeObjects;
			for (int i = 0; i < objects.size(); i++) {
				T object = objects.get(i);
				if (object == null)
					continue;
				freeObjects.add(object);
				if (object instanceof Poolable)
					((Poolable) object).reset();
			}
			// peak = Math.max(peak, freeObjects.size());
			freeObjects.notifyAll();
		}
	}

	/** Removes all free objects from this pool. */
	public void clear() {
		freeObjects.clear();
	}

	/** The number of objects available to be obtained. */
	public int getFree() {
		return freeObjects.size();
	}

	/** Objects implementing this interface will have {@link #reset()} called when passed to {@link #free(Object)}. */
	static public interface Poolable {
		/** Resets the object for reuse. Object references should be nulled and fields may be set to default values. */
		public void reset();
	}
}
