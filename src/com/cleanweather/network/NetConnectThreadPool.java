package com.cleanweather.network;

import java.util.Hashtable;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class NetConnectThreadPool extends ThreadPoolExecutor {
	private static Map<String, NetConnectThreadPool> pools = new Hashtable<String, NetConnectThreadPool>();
	public static final int NET_POOL=0;
	public static final int FILE_POOL=1;
	private NetConnectThreadPool(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit, final BlockingQueue<Runnable> workQueue, final String name) {
		super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue, new DefaultThreadFactory(name), new RejectedExecutionHandler() {
			@Override
			public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
				workQueue.offer(r);
			}
		});
	}

	public static NetConnectThreadPool getInstance(String name, int type) {
		NetConnectThreadPool pool = pools.get(name);
		if (pool == null) {
			int coreThreadNum = Runtime.getRuntime().availableProcessors() * 2;
			// 最大线程数
			int maxThreadNum = Runtime.getRuntime().availableProcessors() * 10;
			int maxLiveTime = 60;
			switch (type) {
			case NET_POOL: 
				coreThreadNum = Math.min(Runtime.getRuntime().availableProcessors() * 1, 3);
				maxThreadNum = Math.min(Runtime.getRuntime().availableProcessors() * 2, 4);
				break;
			case FILE_POOL:
				coreThreadNum = 0;
				maxThreadNum = 2;
				break;
			}

			BlockingQueue<Runnable> sq = new LinkedBlockingQueue<Runnable>();
			pool = new NetConnectThreadPool(coreThreadNum, maxThreadNum, maxLiveTime, TimeUnit.SECONDS, sq, name);
			pools.put(name, pool);
		}
		return pool;
	}

	public void excuteThread(Thread thread) {
		super.execute(thread);
	}

	static class DefaultThreadFactory implements ThreadFactory {
		private final ThreadGroup group;
		private final AtomicInteger threadNumber = new AtomicInteger(1);
		private final String namePrefix;

		DefaultThreadFactory(String name) {
			SecurityManager s = System.getSecurityManager();
			group = (s != null) ? s.getThreadGroup() : Thread.currentThread().getThreadGroup();
			namePrefix = "pool-" + name + "-thread-";
		}

		@Override
		public Thread newThread(Runnable r) {
			Thread t = new Thread(group, r, namePrefix + threadNumber.getAndIncrement(), 0);
			t.setPriority(Thread.NORM_PRIORITY - 1);
			if (t.isDaemon())
				t.setDaemon(false);
			if (t.getPriority() != Thread.NORM_PRIORITY)
				t.setPriority(Thread.NORM_PRIORITY);
			return t;
		}
	}
}
