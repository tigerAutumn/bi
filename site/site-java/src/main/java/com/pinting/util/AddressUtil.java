package com.pinting.util;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AddressUtil {

	private static final Logger log = LoggerFactory.getLogger(AddressUtil.class);
	
	public static String getIp(HttpServletRequest request) {
		String ip = request.getHeader("X-Forwarded-For");  
        if (log.isInfoEnabled()) {  
            log.info("getIpAddress(HttpServletRequest) - X-Forwarded-For - String ip=" + ip);  
        }  
  
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
            if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
                ip = request.getHeader("Proxy-Client-IP");  
                if (log.isInfoEnabled()) {  
                    log.info("getIpAddress(HttpServletRequest) - Proxy-Client-IP - String ip=" + ip);  
                }  
            }  
            if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
                ip = request.getHeader("WL-Proxy-Client-IP");  
                if (log.isInfoEnabled()) {  
                    log.info("getIpAddress(HttpServletRequest) - WL-Proxy-Client-IP - String ip=" + ip);  
                }  
            }  
            if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
                ip = request.getHeader("HTTP_CLIENT_IP");  
                if (log.isInfoEnabled()) {  
                    log.info("getIpAddress(HttpServletRequest) - HTTP_CLIENT_IP - String ip=" + ip);  
                }  
            }  
            if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
                ip = request.getHeader("HTTP_X_FORWARDED_FOR");  
                if (log.isInfoEnabled()) {  
                    log.info("getIpAddress(HttpServletRequest) - HTTP_X_FORWARDED_FOR - String ip=" + ip);  
                }  
            }  
            if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
                ip = request.getRemoteAddr();  
                if (log.isInfoEnabled()) {  
                    log.info("getIpAddress(HttpServletRequest) - getRemoteAddr - String ip=" + ip);  
                }  
            }  
        } else if (ip.length() > 15) {  
            String[] ips = ip.split(",");  
            for (int index = 0; index < ips.length; index++) {  
                String strIp = (String) ips[index];  
                if (!("unknown".equalsIgnoreCase(strIp))) {  
                    ip = strIp;  
                    break;  
                }  
            }  
        }  
        return ip;  
	}
	
	/**
	 * 根据ip查询用户地址
	 * @param content
	 * @param encodingString
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	public static String getAddresses(String content, String encodingString)
			throws UnsupportedEncodingException {
		// 这里调用taobao的接口
		String urlStr = "http://ip.taobao.com/service/getIpInfo.php";
		String returnStr = getResult(urlStr, content, encodingString);
		if (returnStr != null) {
			Map<Object, Object> result = jsonToMap(returnStr);
			Map<Object, Object> data = jsonToMap(result.get("data"));
			String country = (String)data.get("country");
            String area = (String)data.get("area");
			String region = (String)data.get("region");
			String city = (String)data.get("city");
			return country + area + region + city;
		}
		return "";
	}

	private static String getResult(String urlStr, String content,
			String encoding) {
		URL url = null;
		HttpURLConnection connection = null;
		try {
			url = new URL(urlStr);
			connection = (HttpURLConnection) url.openConnection();
			connection.setConnectTimeout(2000); // 设置连接超时时间，单位毫秒
			connection.setReadTimeout(2000); // 设置读取数据超时时间，单位毫秒
			connection.setDoOutput(true); // 是否打开输出流 true|false
			connection.setDoInput(true); // 是否打开输入流true|false
			connection.setRequestMethod("POST"); // 提交方法POST|GET
			connection.setUseCaches(false); // 是否缓存true|false
			connection.connect(); // 打开连接端口
			DataOutputStream out = new DataOutputStream(
					connection.getOutputStream());// 打开输出流往对端服务器写数据
			out.writeBytes(content); // 写数据,也就是提交你的表单 name=xxx&pwd=xxx
			out.flush(); // 刷新
			out.close(); // 关闭输出流
			// 往对端写完数据对端服务器返回数据,以BufferedReader流来读取
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					connection.getInputStream(), encoding));
			StringBuffer buffer = new StringBuffer();
			String line = "";
			while ((line = reader.readLine()) != null) {
				buffer.append(line);
			}
			reader.close();
			return buffer.toString();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (connection != null) {
				connection.disconnect(); // 关闭连接
			}
		}
		return null;
	}

	/**
     * json string 转换为 map 对象
     * @param jsonObj
     * @return
     */
    public static Map<Object, Object> jsonToMap(Object jsonObj) {
        JSONObject jsonObject = JSONObject.fromObject(jsonObj);
        Map<Object, Object> map = (Map)jsonObject;
        return map;
    }
}
