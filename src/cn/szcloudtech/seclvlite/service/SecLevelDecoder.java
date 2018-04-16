package cn.szcloudtech.seclvlite.service;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import cn.szcloudtech.ctlog.CTLog;
import cn.szcloudtech.seclvlite.util.Global;

public class SecLevelDecoder implements Runnable {
	private final String TAG = this.getClass().getSimpleName();
	private String report;
	
	public SecLevelDecoder(String report) {
		this.report = report;
	}
	
	@Override
	public void run() {
		String[] messages = report.split(";");
		String stationName = messages[0].substring(messages[0].indexOf(":") + 1);
		String date = messages[1].substring(messages[1].indexOf(":") + 1);
		String time = messages[2].substring(messages[2].indexOf(":") + 1);
		String year = "20" + date.substring(0, 2);
		String month = date.substring(2, 4);
		String day = date.substring(4, 6);
		String hour = time.substring(0, 2);
		
		File path = new File(Global.ROOT_PATH + "\\" + year + "\\" + month + "\\" + day);
		if (!path.exists()) {
			path.mkdirs();
		}
		File file = new File(path + "\\" + hour + "." + stationName);
		if (!file.exists()) {
			try {
				file.createNewFile();
			} catch (IOException e) {
				CTLog.e(TAG, e);
				return;
			}
		}
		FileWriter writer = null;
		try {
			writer = new FileWriter(file, true);
			writer.write(report + "\r\n");
//			int count = 0;
//			if (Global.stationRecvCountMap.containsKey(stationName)) {
//				count = Global.stationRecvCountMap.get(stationName);
//			}
//			System.out.println("服务器：接收到" + stationName + "的数据，并第" + (count + 1) 
//					+ "次写入文件成功，当前时间：" + System.currentTimeMillis());
//			Global.stationRecvCountMap.put(stationName, count + 1);
		} catch (IOException e) {
			CTLog.e(TAG, e);
		} finally {
			if (writer != null) {
				try {
					writer.close();
				} catch (IOException e) {
					CTLog.e(TAG, e);
				}
			}
		}
	}

}
