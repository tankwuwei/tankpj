package engine.core.queue;

import java.util.concurrent.atomic.AtomicInteger;

import engine.job.JobManager;
import engine.log.LogUtil;

public class DefaultQueue<T> implements IQueue<T>, Runnable {
	private static final byte NONE = 0;
	private static final byte QUEUED = 1;

	private Object[] queue = new Object[4];
	private Object[] temp = new Object[4];
	private int queueIndex, tempIndex;
	private Object lock = new Object();

	private AtomicInteger state = new AtomicInteger(NONE);

	public DefaultQueue() {
	}

	public void queue(T entry) {
		synchronized (lock) {
			if (queueIndex + 1 >= queue.length)
				expand(4);
			queue[queueIndex++] = entry;
		}
		if (!state.compareAndSet(NONE, QUEUED))
			return;
		JobManager.execute(this);
	}

	private void expand(int size) {
		Object[] newQueue = new Object[queue.length + size];
		System.arraycopy(queue, 0, newQueue, 0, queue.length);
		queue = newQueue;
	}

	public void dispose() {
		for (int i = queue.length - 1; i > -1; i--)
			queue[i] = null;
		for (int i = temp.length - 1; i > -1; i--)
			temp[i] = null;
	}

	protected void handleEntry(T o) {

	}

	@SuppressWarnings("unchecked")
	@Override
	public void run() {
		try {
			synchronized (lock) {
				if (temp.length < queue.length)
					temp = new Object[queue.length];
				for (int i = 0; i < queueIndex; i++) {
					temp[i] = queue[i];
					queue[i] = null;
				}
				tempIndex = queueIndex;
				queueIndex = 0;
			}
			for (int i = 0; i < tempIndex; i++) {
				try {
					handleEntry((T) temp[i]);
					temp[i] = null;
				} catch (Exception e) {
					LogUtil.error(e);
				}
			}
		} finally {
			synchronized (lock) {
				if (queueIndex > 0) {
					JobManager.submit(this);
				} else {
					state.set(NONE);
				}
			}
		}
	}
}
