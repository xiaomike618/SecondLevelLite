package cn.szcloudtech.seclvlite.service;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import cn.szcloudtech.ctlog.CTLog;
import cn.szcloudtech.seclvlite.util.Global;

public class SecLevelDecodeManager {
	private final String TAG = this.getClass().getSimpleName();
	private static SecLevelDecodeManager instance;
	private SecLevelDecodeManager() {}
	
	public static SecLevelDecodeManager getInstance() {
		if (instance == null) {
			instance = new SecLevelDecodeManager();
		}
		return instance;
	}
	
	private ExecutorService threadPool;
	private boolean isRunning = false;
	private static final int POOL_SIZE = 10;
	private static final int SLEEP_TIME = 10;
	
	public void start() {
		threadPool = Executors.newFixedThreadPool(POOL_SIZE);
		isRunning = true;
		new Thread() {
			public void run() {
				while (isRunning) {
					try {
						String report = Global.sReportBuffer.poll();
						if (report != null) {
							threadPool.execute(new SecLevelDecoder(report));
						} else {
							Thread.sleep(SLEEP_TIME);
						}
					} catch (Exception e) {
						CTLog.e(TAG, e);
					}
				}
			};
		}.start();
	}
	
	public void stop() {
		isRunning = false;
		if (threadPool != null) {
			threadPool.shutdown();
			threadPool = null;
		}
	}
}
