package cn.szcloudtech.seclvlite.service;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import cn.szcloudtech.ctlog.CTLog;
import io.netty.channel.ChannelHandlerContext;

public class ChannelDetector {
	
	private final String TAG = this.getClass().getSimpleName();

	private ChannelDetector() {}
	
	private static ChannelDetector instance;
	
	public synchronized static ChannelDetector getInstance() {
		if (instance == null) {
			instance = new ChannelDetector();
		}
		return instance;
	}
	
	private Object lock = new Object();
	private Map<ChannelHandlerContext, Long> lastDataTimeMap = new HashMap<>();
	
	public void updateChannel(ChannelHandlerContext channel, long time) {
		synchronized (lock) {
			lastDataTimeMap.put(channel, time);
		}
	}
	
	public void removeChannel(ChannelHandlerContext channel) {
		synchronized (lock) {
			lastDataTimeMap.remove(channel);
		}
	}
	
	private Timer timer;
	private TimerTask task;
	private static final long TIMER_DELAY = 60 * 1000;
	private static final long TIMER_PERIOD = 60 * 1000;
	public void start() {
		stop();
		task = new TimerTask() {
			
			@Override
			public void run() {
				try {
					synchronized (lock) {
						long now = System.currentTimeMillis();
						Iterator<Map.Entry<ChannelHandlerContext, Long>> entries = lastDataTimeMap.entrySet().iterator();
						while (entries.hasNext()) {
							Map.Entry<ChannelHandlerContext, Long> entry = entries.next();
							if (entry.getValue() != null && (now - entry.getValue() > TIMER_PERIOD)) {
								entry.getKey().close();
								entries.remove();
							}
						}
					}
					
				} catch (Exception e) {
					CTLog.e(TAG, e);
				}
			}
		};
		timer = new Timer();
		timer.schedule(task, TIMER_DELAY, TIMER_PERIOD);
	}
	
	public void stop() {
		if (task != null) {
			task.cancel();
		}
		if (timer != null) {
			timer.cancel();
		}
	}
}
