package com.pinting.business.util;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Enumeration;
//获取mac地址和IP地址
public class MacAddrUtil {
	//获取mac地址
	public static String getMacAddr() {
	    NetworkInterface netInterface;
		// 获得Mac地址的byte数组
	    byte[] macAddr = null;
		try {
			netInterface = NetworkInterface.getByInetAddress(InetAddress.getLocalHost());
			macAddr = netInterface.getHardwareAddress();
		} catch (SocketException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

        // 循环输出
        String macAdr = "";
        int i = 0;
		for (byte b : macAddr) {
		//这里的toHexString()是自己写的格式化输出的方法。
			if (macAddr.length == i-1) {
				macAdr = macAdr + toHexString(b) ;
			}else {
				macAdr = macAdr + toHexString(b) + "-";
			}
			i++;
		}
		return macAdr;
	}
	//获取IP地址
	public static String getIPAddr() {
	    // 获得ＩＰ
	    NetworkInterface netInterface = null ;
	    InetAddress address = null;
		try {
			netInterface = NetworkInterface.getByInetAddress(InetAddress.getLocalHost());
			 Enumeration<InetAddress> enumeration = netInterface.getInetAddresses();
			 address = enumeration.nextElement();
		} catch (SocketException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        //获得ＩＰ地址
		return address.getHostName();
		
	}
	
	
	private static String toHexString(int integer) {
		//将得来的int类型数字转化为十六进制数
		String str = Integer.toHexString((int) (integer & 0xff));
		// 如果遇到单字符，前置0占位补满两格
		if (str.length() == 1) {
		str = "0" + str;
		}
		return str;
	}


}



