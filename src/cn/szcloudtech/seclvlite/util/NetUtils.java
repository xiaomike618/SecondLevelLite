package cn.szcloudtech.seclvlite.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class NetUtils {

	public static boolean isIpCorrect(String str) {
		String regEx = "((25[0-5]|2[0-4]\\d|((1\\d{2})|([1-9]?\\d)))\\.){3}(25[0-5]|2[0-4]\\d|((1\\d{2})|([1-9]?\\d)))";
	    Pattern pattern = Pattern.compile(regEx);
	    Matcher matcher = pattern.matcher(str);
	    return matcher.matches();
	}
	
	public static boolean isPortCorrect(String str) {
		try {
			int port = Integer.valueOf(str);
			return (port >= 0 && port <65536);
		} catch(NumberFormatException e) {
			return false;
		}
	}
}
