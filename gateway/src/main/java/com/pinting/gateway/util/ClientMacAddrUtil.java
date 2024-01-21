package com.pinting.gateway.util;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.LineNumberReader;

import javax.servlet.http.HttpServletRequest;

public class ClientMacAddrUtil {
	
	
    
    public static String getIpAddr(HttpServletRequest request) {  
         String ip = request.getHeader("x-forwarded-for");  
            if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
               ip = request.getHeader("Proxy-Client-IP");  
           }  
            if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
                ip = request.getHeader("WL-Proxy-Client-IP");  
           }  
          if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
                ip = request.getRemoteAddr();  
            }  
           return ip;  
    } 
	
    public static String getMAC(String ip) {  
        String str = null;  
        String macAddress = null;  
        try {  
            Process p = Runtime.getRuntime().exec("nbtstat -A " + ip);  
            InputStreamReader ir = new InputStreamReader(p.getInputStream(), "GBK");
            System.out.println(ir.getEncoding());
            LineNumberReader input = new LineNumberReader(ir); 
            

            
            while ((str = input.readLine()) != null){
                if (str != null) {  
                    if (str.indexOf("MAC 地址") > 1) {  
                        macAddress = str.substring(str.indexOf("MAC 地址") + 8);  
                        break;  
                    }else if (str.indexOf("MAC Address") > 1) {
                    	macAddress = str.substring(str.indexOf("MAC Address") + 13);
                    	break;  
					}  
                }  
            }  
        } catch (IOException e) {  
            e.printStackTrace(System.out);  
            return null;  
        }  
        return macAddress;  
    }  
}
