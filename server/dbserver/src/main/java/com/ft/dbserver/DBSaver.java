package com.ft.dbserver;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.concurrent.atomic.AtomicInteger;

import engine.core.IDBObject;
import engine.job.JobManager;
import engine.log.LogUtil;
import engine.util.ObjectCountor;
import engine.util.Pool;

/**
 * 脏数据加入到队列中，如果有空线程，则直接存。 如果没有空线程，则保存到队列中，等待空线程读取。
 * 
 * @author xjf
 */
public class DBSaver {
	private DB db;

	public AtomicInteger busyThreadCounter = new AtomicInteger(0);
	public AtomicInteger saveThreadCounter = new AtomicInteger();
	public AtomicInteger saveCount = new AtomicInteger();

	private HashSet<IDBObject> set;
	private LinkedList<IDBObject> dirtys;

	private ObjectCountor dirtyCountor, saveCountor;

	private Pool<ArrayList<IDBObject>> pool = new Pool<ArrayList<IDBObject>>() {

		@Override
		protected ArrayList<IDBObject> newObject() {
			return new ArrayList<IDBObject>();
		}

	};

	public void init(DB db) {
		// window = new DBSaverWindow();
		this.db = db;
		dirtyCountor = new ObjectCountor("DBSaver.dirtyCounter", 5100);
		saveCountor = new ObjectCountor("DBSaver.saveCounter", 5000);
	}

	public DBSaver() {
		dirtys = new LinkedList<IDBObject>();
		set = new HashSet<>();
		JobManager.schedule(new Runnable() {

			@Override
			public void run() {
				// LogUtil.info("getcount=" + Client.getCount.get() + " busy
				// thread count=" + busyThreadCounter.get()
				// + ", saveTime=" + saveThreadCounter.get() + "dirty size=" +
				// dirtys.size() + ", saveCount="
				// + saveCount.get() + ", delay=" + getDelay());
				saveCount.set(0);
				Client.getCount.set(0);

			}
		}, 1000, 20000);

		for (int i = 0; i < 1; i++) {
			new Thread(() -> {
				while (true) {
					synchronized (dirtys) {
						if (busyThreadCounter.get() >= DBServer.MaxThread || dirtys.size() == 0) {
							try {
								dirtys.wait();
							} catch (Exception e) {
								LogUtil.error(e);
							}
						} else if (dirtys.size() > 0) {
							saveDirty();
						}
					}
				}
			}).start();
		}
	}

	public int getDelay() {
		int result = dirtys.size() < 10 ? 0 : dirtys.size() * 10 / DBServer.MaxDirtyCount;
		return result > 500 ? 500 : result;
	}

	public void addDirty(IDBObject obj) {
		// LogUtil.info("add dirty " + obj + ", id="+obj.getId());
		long t = System.nanoTime();
		synchronized (dirtys) {
			dirtyCountor.inc(obj);
			if (set.contains(obj)) {
				// //有数据要先存
				// if(busyThreadCounter.get() < DBConfig.MaxThread){
				// saveDirty();
				// }
				return; // 已经在列表里了
			}
			dirtys.add(obj);
			set.add(obj);
			dirtys.notify();
			// if(busyThreadCounter.get() < DBConfig.MaxThread){
			// saveDirty();
			// }
		}

//		LogUtil.debug("*******  add dirty " + (System.nanoTime() - t));
	}

	private void saveDirty() {
		// 如果线程不忙，则一个也存，如果线程忙，则10个一存
		// final DBObject[] list = new
		// DBObject[dirtys.size()>DBConfig.MaxCountPerTime?DBConfig.MaxCountPerTime:dirtys.size()];
		int count = dirtys.size() > DBServer.MaxCountPerTime ? DBServer.MaxCountPerTime : dirtys.size();
		if (count == 0)
			return;
		final ArrayList<IDBObject> list = pool.obtain();
		// 从dirty中去掉
		for (int i = 0; i < count; i++) {
			list.add(dirtys.removeFirst()); // 从dirtys中取出，但不从set中去掉，在正式存前才从set中去掉
			// LogUtil.info(list[i]);
		}
		busyThreadCounter.incrementAndGet();
		JobManager.execute(() -> {
			final long t = System.nanoTime();
			saveCount.addAndGet(list.size());
			saveThreadCounter.incrementAndGet();
			for (int i = list.size() - 1; i >= 0; i--) {
				IDBObject obj = list.get(i);
				if (obj.tryLock()) {
					set.remove(obj);
					saveCountor.inc(obj);
				} else {
					set.remove(obj);
					list.remove(i);
				}
			}
			try {
				long now = System.currentTimeMillis();
				LogUtil.debug("start===========================================================" + now);
				for (IDBObject dbObject : list) {
					LogUtil.debug(dbObject.getClass().getSimpleName() + ":" + dbObject.getId());
				}
				db.saveOrUpdate(list);
				LogUtil.debug("end===========================================================" + now);
				LogUtil.debug("save db count=" + list.size() + ", time=" + (System.nanoTime() - t));
			} finally {
				for (int i = list.size() - 1; i >= 0; i--) {
					list.get(i).unlock();
				}

				list.clear();
				pool.free(list);

				busyThreadCounter.decrementAndGet();
				synchronized (dirtys) {
					dirtys.notify();
				}
			}
		});
	}
}
