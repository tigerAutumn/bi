package com.pinting.business.util;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pinting.business.model.vo.AddressInfos;
import com.pinting.core.util.StringUtil;

/**
 * 归属地查询
 * @author bianyatian
 * @2016-11-3 下午6:58:39
 */
public class AddressUtil {
	
	private static final Logger log = LoggerFactory.getLogger(AddressUtil.class);
	
	/**
	 * 身份证查询归属地
	 * @param cardNo
	 * @return
	 */
	public static String idCardNoAddress(String cardNo){
    	URL url = null;  
    	String address = "";
        try {
        	try {
        		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();   
                DocumentBuilder builder = factory.newDocumentBuilder();  
                  
                url = new URL("http://api.k780.com:88/?app=idcard.get&idcard="+cardNo+"&appkey=10003&sign=b59bc3ef6191eb9f747dd4e83c99f2a4&format=xml");  
                  
                Document doc = builder.parse(url.openStream());  
                NodeList node = doc.getElementsByTagName("result");   
                for(int i=0;i<node.getLength();i++){
                    if(doc.getElementsByTagName("att").item(i).getFirstChild() != null){  
                    	address = doc.getElementsByTagName("att").item(i).getFirstChild().getNodeValue();  
                    }
                } 
			} catch (Exception e) {
				e.printStackTrace();
			}
            //百度api
            if(StringUtil.isBlank(address)){
            	BufferedReader reader = null;
                String result = null;
                StringBuffer sbf = new StringBuffer();

                try {
                    url = new URL("http://apis.baidu.com/apistore/idservice/id?id="+cardNo);
                    HttpURLConnection connection = (HttpURLConnection) url
                            .openConnection();
                    connection.setRequestMethod("GET");
                    // 填入apikey到HTTP header
                    connection.setRequestProperty("apikey",  "c62c3f416fdb246808ffc6f0812eb73b");
                    connection.connect();
                    InputStream is = connection.getInputStream();
                    reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
                    String strRead = null;
                    while ((strRead = reader.readLine()) != null) {
                        sbf.append(strRead);
                        sbf.append("\r\n");
                    }
                    reader.close();
                    result = "["+sbf.toString()+"]";
                    JSONArray jsonArray = JSONArray.fromObject(result);  
                    if(result.contains("0,")){
                    	List<AddressInfos> list = (List) JSONArray.toCollection(jsonArray,  
                        		AddressInfos.class);
                    	if(CollectionUtils.isNotEmpty(list)){
                    		address = list.get(0).getRetData().getAddress();
                    	}
                    }else if(result.contains("-1,")){
                    	address = "身份证不合法";
                    }
                    
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
              
		} catch (Exception e) {
			e.printStackTrace();
		}
        if(StringUtil.isBlank(address)){
    		address = "未查询到身份证归属地";
    	}
    	return address;
    }
	
	/**
	 * 手机号归属地
	 * @param mobile
	 * @return
	 */
	public static String mobileAddress(String mobile){
    	URL url = null;  
    	String address = "";
    	try {
    		BufferedReader reader = null;
            String result = null;
            StringBuffer sbf = new StringBuffer();
    		url = new URL("http://www.ip138.com:8080/search.asp?mobile="+mobile+"&action=mobile");
            HttpURLConnection connection = (HttpURLConnection) url
                    .openConnection();
            connection.setRequestMethod("GET");
            connection.connect();
            InputStream is = connection.getInputStream();
            reader = new BufferedReader(new InputStreamReader(is, "GBK"));
            String strRead = null;
            boolean flag=false;
            while ((strRead = reader.readLine()) != null) {
                if(StringUtil.isNotBlank(strRead) && (strRead.contains("卡号归属地") || flag)){
                	flag = true;
                	address = strRead.replace("<TD","").replace("class","").replace("align=\"center\"","").replace("tdc2","")
            				.replace("width=\"138\"", "").replace("</TD>", "").replace("noswap", "").replace("</TD>", "")
            				.replace("<td", "").replace("</td>", "").replace("<!", "").replace("--", "").replace(">", "").replace("width", "")
            				.replace("\"", "").replace(">", "").replace("&nbsp;", "").replace("=", "").replace("卡号归属地", "")
            				.replace("*", "").replace(" ", "").replace("	", "");
            		if(StringUtil.isNotBlank(address)){
            			return address;
            		}
            	}
            }
            reader.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
    	if(StringUtil.isBlank(address)){
    		try {
    			BufferedReader reader = null;
                String result = null;
                StringBuffer sbf = new StringBuffer();
        		url = new URL("http://apis.baidu.com/showapi_open_bus/mobile/find?num="+mobile);
                HttpURLConnection connection = (HttpURLConnection) url
                        .openConnection();
                connection.setRequestMethod("GET");
                // 填入apikey到HTTP header
                connection.setRequestProperty("apikey",  "c62c3f416fdb246808ffc6f0812eb73b");
                connection.connect();
                InputStream is = connection.getInputStream();
                reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
                String strRead = null;
                while ((strRead = reader.readLine()) != null) {
                    sbf.append(strRead);
                    sbf.append("\r\n");
                }
                reader.close();
                result = "["+sbf.toString()+"]";
                JSONArray jsonArray = JSONArray.fromObject(result);  
                List<AddressInfos> list = (List) JSONArray.toCollection(jsonArray,  
                		AddressInfos.class);  
                if(CollectionUtils.isNotEmpty(list) && list.get(0).getShowapi_res_body() != null){
                	 address = list.get(0).getShowapi_res_body().getProv() + list.get(0).getShowapi_res_body().getCity();
                }
    		} catch (Exception e) {
    			e.printStackTrace();
    		}
    	}
    	
    	
    	if(StringUtil.isBlank(address)){
    		try {
    			BufferedReader reader = null;
                String result = null;
                StringBuffer sbf = new StringBuffer();
        		url = new URL("http://apis.baidu.com/apistore/mobilephoneservice/mobilephone?tel="+mobile);
                HttpURLConnection connection = (HttpURLConnection) url
                        .openConnection();
                connection.setRequestMethod("GET");
                // 填入apikey到HTTP header
                connection.setRequestProperty("apikey",  "c62c3f416fdb246808ffc6f0812eb73b");
                connection.connect();
                InputStream is = connection.getInputStream();
                reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
                String strRead = null;
                while ((strRead = reader.readLine()) != null) {
                    sbf.append(strRead);
                    sbf.append("\r\n");
                }
                reader.close();
                result = "["+sbf.toString()+"]";
                JSONArray jsonArray = JSONArray.fromObject(result);  
                List<AddressInfos> list = (List) JSONArray.toCollection(jsonArray,  
                		AddressInfos.class);  
                if(CollectionUtils.isNotEmpty(list) && list.get(0).getRetData() != null){
                	 address = list.get(0).getRetData().getProvince();
                }
    		} catch (Exception e) {
    			e.printStackTrace();
    		}
    	}
    	
    	if(StringUtil.isBlank(address)){
    		address = "未查询到手机号归属地";
    	}
    	return address;
	}
	
	/**
	 * 银行卡归属地
	 * @param bankCardNo
	 * @return
	 */
	public static String bankCardAddress(String bankCardNo){
		String address = "";
		try {
			URL url = null;
			PrintWriter out = null;
			BufferedReader in = null;
			String param = "card="+bankCardNo;
            url = new URL("http://www.chakahao.com/bankcha.asp");
            // 打开和URL之间的连接
            URLConnection conn = url.openConnection();
            // 设置通用的请求属性
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestProperty("user-agent",
                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            // 发送POST请求必须设置如下两行
            conn.setDoOutput(true);
            conn.setDoInput(true);
            // 获取URLConnection对象对应的输出流
            out = new PrintWriter(conn.getOutputStream());
            // 发送请求参数
            out.print(param);
            // flush输出流的缓冲
            out.flush();
            // 定义BufferedReader输入流来读取URL的响应
            in = new BufferedReader(
                    new InputStreamReader(conn.getInputStream(), "GBK"));
            String line;
            while ((line = in.readLine()) != null) {
            	
            	if(line.contains("<p><b>银行卡所在地区: <font color=red>")){
            		address = line.replace("<p><b>银行卡所在地区: <font color=red>", "").replace("</font></b></p>", "").replace("--", "").replace(" ", "");
            	}
            } 
		} catch (Exception e) {
			e.printStackTrace();
		}
		if(StringUtil.isBlank(address)){
			try {
				URL url = null;
				PrintWriter out = null;
				BufferedReader in = null;
				String param = "card="+bankCardNo;
	            url = new URL("http://cha.yinhangkadata.com/");
	            URLConnection conn = url.openConnection();
	            conn.setRequestProperty("accept", "*/*");
	            conn.setRequestProperty("connection", "Keep-Alive");
	            conn.setRequestProperty("user-agent",
	                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
	            conn.setDoOutput(true);
	            conn.setDoInput(true);
	            out = new PrintWriter(conn.getOutputStream());
	            out.print(param);
	            out.flush();
	            in = new BufferedReader(
	                    new InputStreamReader(conn.getInputStream(), "GBK"));
	            String line;
	            while ((line = in.readLine()) != null) {
	            	if(line.contains("<p><b>银行卡所在地区: <font color=red>")){
	            		address = line.replace("<p><b>银行卡所在地区: <font color=red>", "").replace("</font></b></p>", "").replace("--", "").replace(" ", "");
	            	}
	            } 
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		if(StringUtil.isBlank(address)){
			try {
				URL url = null;
				PrintWriter out = null;
				BufferedReader in = null;
				String param = "card="+bankCardNo;
	            url = new URL("http://www.qusocha.com/");
	            URLConnection conn = url.openConnection();
	            conn.setRequestProperty("accept", "*/*");
	            conn.setRequestProperty("connection", "Keep-Alive");
	            conn.setRequestProperty("user-agent",
	                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
	            conn.setDoOutput(true);
	            conn.setDoInput(true);
	            out = new PrintWriter(conn.getOutputStream());
	            out.print(param);
	            out.flush();
	            in = new BufferedReader(
	                    new InputStreamReader(conn.getInputStream(), "GBK"));
	            String line;
	            while ((line = in.readLine()) != null) {
	            	if(line.contains("<b>银行卡所在地区: <font color=red>")){
	            		address = line.replace("<b>银行卡所在地区: <font color=red>", "").replace("</font></b><br>", "").replace("--", "").replace(" ", "").trim();
	            	}
	            } 
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		if(StringUtil.isBlank(address)){
    		address = "未查询到银行卡归属地";
    	}
		return address;
	}
	
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
	
	public static void main(String[] args) {
		//System.out.println(AddressUtil.bankCardAddress("6212261202009414401"));
		System.out.println(AddressUtil.mobileAddress("15990147161"));
	}

}
