package engine.job;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import engine.log.LogUtil;

public class JobManager {
	private static final int cpu = Runtime.getRuntime().availableProcessors();

	private static final ExecutorService worker = Executors.newCachedThreadPool();
	private static final ScheduledExecutorService timer = Executors.newScheduledThreadPool(cpu * 2);

	public static Future<?> submit(Runnable r) {
		return worker.submit(r);
	}

	public static void execute(Runnable r) {
		worker.execute(r);
	}

	/**
	 * @param runnable
	 * @param delay in milliseconds
	 */
	public static ScheduledFuture<?> schedule(Runnable runnable, int delay) {
		return timer.schedule(() -> {
			try {
				runnable.run();
			} catch (Exception e) {
				LogUtil.error("jobmanager catch exception", e);
			}
		}, delay, TimeUnit.MILLISECONDS);
	}

	/**
	 * @param runnable
	 * @param initialDelay in milliseconds
	 * @param period in milliseconds
	 */
	public static ScheduledFuture<?> schedule(Runnable runnable, int initialDelay, int period) {
		return timer.scheduleAtFixedRate(() -> {
			try {
				runnable.run();
			} catch (Exception e) {
				LogUtil.error("jobmanager catch exception", e);
			}
		}, initialDelay, period, TimeUnit.MILLISECONDS);
	}
}
