package cn.szcloudtech.seclvlite.util;

import java.util.concurrent.LinkedBlockingQueue;

import io.netty.channel.ChannelHandlerContext;

public class Global {
	
	public static final String ROOT_PATH = "E:\\SecondLevelLite";

	public static LinkedBlockingQueue<String> sReportBuffer = new LinkedBlockingQueue<>();
	
//	public static Map<String, Integer> stationRecvCountMap = Collections.synchronizedMap(new HashMap<>());
	
	public static boolean isForwardRunning = false;
	
	private static Object forwardBuffLock = new Object();
	
	private static LinkedBlockingQueue<String> sForwardBuffer = new LinkedBlockingQueue<>();
	
	public static void pushForwardMessage(String msg) {
		synchronized (forwardBuffLock) {
			sForwardBuffer.offer(msg);
		}
	}
	
	public static String pollForwardMessage() {
		synchronized (forwardBuffLock) {
			return sForwardBuffer.poll();
		}
	}
	
	public static ChannelHandlerContext forwardChannel;
}
