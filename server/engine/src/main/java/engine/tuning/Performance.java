package engine.tuning;

import engine.job.JobManager;

// performance monitor
// set Config.performance=false to disable.

public class Performance {
	private static final class DebugSend {
		private static int count;
		private static int prevCount;
		private static long prevTime;

		public static void init() {
			JobManager.schedule(
					() -> {
						long t = System.currentTimeMillis();
						System.out.println((count - prevCount) * 1000 / (t - prevTime)
								+ " packet send per second, All count=" + count);
						prevTime = t;
						prevCount = count;
					}, 1000, 1000);
		}
	}

	private static final class DebugReceive {
		private static int count;
		private static int prevCount;
		private static long prevTime;

		public static void init() {
			// 输出消息包数
			JobManager.schedule(
					() -> {
						long t = System.currentTimeMillis();
						System.out.println((count - prevCount) * 1000 / (t - prevTime)
								+ " packet receive per second, All count=" + count);
						prevTime = t;
						prevCount = count;
					}, 1000, 1000);
		}
	}

	// private static DebugSend send=new DebugSend();
	// private static DebugReceive receive=new DebugReceive();

	public static void init() {
		DebugSend.init();

		DebugReceive.init();
	}

	public static synchronized void addSend() {
		DebugSend.count++;
	}

	public static synchronized void addReceive() {
		DebugReceive.count++;
	}

}
