package com.pinting.business.util;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 系统时间类，用来设置系统时间
 * 注意！！！！此类只用于测试，除了测试代码，其他一切代码不能使用此类
 * @Project: business
 * @Title: Systime.java
 * @author Zhou Changzai
 * @date 2019-3-26 下午9:35:05
 * @Copyright: 2019 BiGangWan.com Inc. All rights reserved.
 */
public class Systime {
	protected static boolean set(Date date) {
		if(date == null){
			return false;
		}
		
		String osName = System.getProperty("os.name");
		String cmd = "";
		
		try {
			SimpleDateFormat sdf = new SimpleDateFormat();
			if (osName.matches("^(?i)Windows.*$")) {// Window 系统
				sdf.applyPattern("yyyy-MM-dd");
				String dateStr = sdf.format(date);
				sdf.applyPattern("HH:mm:ss");
				String timeStr = sdf.format(date);
				
				// 格式：yyyy-MM-dd
				cmd = " cmd /c date " + dateStr;
				Runtime.getRuntime().exec(cmd);
				// 格式 HH:mm:ss
				cmd = "  cmd /c time " + timeStr;
				Runtime.getRuntime().exec(cmd);
			} else {// Linux 系统
				sdf.applyPattern("yyyyMMdd");
				String dateStr = sdf.format(date);
				sdf.applyPattern("HH:mm:ss");
				String timeStr = sdf.format(date);
				
				// 格式：yyyyMMdd
				cmd = "  date -s " + dateStr;
				Runtime.getRuntime().exec(cmd);
				// 格式 HH:mm:ss
				cmd = "  date -s " + timeStr;
				Runtime.getRuntime().exec(cmd);
			}
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
		
		return true;
	}
	
	public static void main(String[] args) {
		Calendar c = Calendar.getInstance();
		c.set(Calendar.YEAR, 2000);
		Systime.set(c.getTime());
	}
}
 