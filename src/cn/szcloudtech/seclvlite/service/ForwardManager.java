package cn.szcloudtech.seclvlite.service;

import cn.szcloudtech.ctlog.CTLog;
import cn.szcloudtech.seclvlite.util.Global;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

public class ForwardManager {
	private final String TAG = this.getClass().getSimpleName();
	private static final int SLEEP_TIME = 10;
	private boolean isRunning = false;

	private ForwardManager() {
	}

	private static ForwardManager instance;

	public synchronized static ForwardManager getInstance() {
		if (instance == null) {
			instance = new ForwardManager();
		}
		return instance;
	}

	public void start(String ip, int port) {
		new Thread() {
			@Override
			public void run() {
				super.run();
				ForwardConnector.getInstance().start(ip, port);
			}
		}.start();
		isRunning = true;
		new Thread() {
			public void run() {
				while (isRunning) {
					String message = Global.pollForwardMessage();
					if (message != null) {
						if (Global.forwardChannel != null) {
							ByteBuf buf = Unpooled.buffer(message.getBytes().length);
							buf.writeBytes(message.getBytes());
							Global.forwardChannel.writeAndFlush(buf);
						} else {
							isRunning = false;
						}
					} else {
						try {
							Thread.sleep(SLEEP_TIME);
						} catch (InterruptedException e) {
							CTLog.e(TAG, e);
						}
					}

				}
			};
		}.start();
	}

	public void stop() {
		isRunning = false;
		ForwardConnector.getInstance().stop();
	}
}
