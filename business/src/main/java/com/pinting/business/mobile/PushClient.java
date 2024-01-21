package com.pinting.business.mobile;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import com.pinting.business.enums.RedisLockEnum;
import com.pinting.business.util.Constants;
import com.pinting.core.redis.JedisClientDaoSupport;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class PushClient {
	private static Logger log = LoggerFactory.getLogger(PushClient.class);
	private static JedisClientDaoSupport jsClientDaoSupport = JedisClientDaoSupport.getInstance(Constants.REDIS_SUBSYS_BUSINESS);
	
	// The user agent
	protected final String USER_AGENT = "Mozilla/5.0";

	// This object is used for sending the post request to Umeng
	protected HttpClient client = new DefaultHttpClient();
	
	// The host
	protected static final String host = "https://msgapi.umeng.com";
	
	// The upload path
	protected static final String uploadPath = "/upload";
	
	// The post path
	protected static final String postPath = "/api/send";

	public String send(UmengNotification msg) throws Exception {

		StringBuffer result = new StringBuffer();
		try {
			jsClientDaoSupport.lock(RedisLockEnum.LOCK_UMENG_SYNC.getKey());

			String timestamp = Integer.toString((int)(System.currentTimeMillis() / 1000));
			msg.setPredefinedKeyValue("timestamp", timestamp);
	        String url = host + postPath;
	        String postBody = msg.getPostBody();
	        String sign = DigestUtils.md5Hex(("POST" + url + postBody + msg.getAppMasterSecret()).getBytes("utf8"));
	        url = url + "?sign=" + sign;
	        HttpPost post = new HttpPost(url);
	        post.setHeader("User-Agent", USER_AGENT);
	        StringEntity se = new StringEntity(postBody, "UTF-8");
	        post.setEntity(se);
	        // Send the post request and get the response
			log.info("umeng send URL : {}", url);
	        HttpResponse response = client.execute(post);
	        int status = response.getStatusLine().getStatusCode();
	        log.info("Response Code : " + status);
	        BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
	        String line = "";
	        while ((line = rd.readLine()) != null) {
	            result.append(line);
	        }
	        log.info(result.toString());
	        if (status == 200) {
	            log.info("Notification sent successfully.");
	        } else {
	            log.info("Failed to send the notification!");
	            log.info("推送状态码错误信息："+StatusCode.getDesciption(String.valueOf(status)));
	            if(status == 500) {
	            	log.info("推送500错误码具体错误信息："+getErrorMsg(result.toString()));
	            }
	        }
		} finally {
			jsClientDaoSupport.unlock(RedisLockEnum.LOCK_UMENG_SYNC.getKey());
		}
		return result.toString();
    }

	// Upload file with device_tokens to Umeng
	public String uploadContents(String appkey,String appMasterSecret,String contents) throws Exception {
		// Construct the json string
		JSONObject uploadJson = new JSONObject();
		uploadJson.put("appkey", appkey);
		String timestamp = Integer.toString((int)(System.currentTimeMillis() / 1000));
		uploadJson.put("timestamp", timestamp);
		uploadJson.put("content", contents);
		// Construct the request
		String url = host + uploadPath;
		String postBody = uploadJson.toString();
		String sign = DigestUtils.md5Hex(("POST" + url + postBody + appMasterSecret).getBytes("utf8"));
		url = url + "?sign=" + sign;
		HttpPost post = new HttpPost(url);
		post.setHeader("User-Agent", USER_AGENT);
		StringEntity se = new StringEntity(postBody, "UTF-8");
		post.setEntity(se);
		// Send the post request and get the response
		HttpResponse response = client.execute(post);
		int status = response.getStatusLine().getStatusCode();
		log.info("Response Code : " + status);
		BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
		StringBuffer result = new StringBuffer();
		String line = "";
		while ((line = rd.readLine()) != null) {
			result.append(line);
		}
		log.info(result.toString());
		// Decode response string and get file_id from it
		JSONObject respJson = new JSONObject(result.toString());
		String ret = respJson.getString("ret");
		if (!ret.equals("SUCCESS")) {
			if(status != 200) {
				log.info("上传文件状态码错误信息："+StatusCode.getDesciption(String.valueOf(status)));
	            if(status == 500) {
	            	log.info("上传文件500错误码具体错误信息："+getErrorMsg(result.toString()));
	            }
			}
			throw new Exception("Failed to upload file");
		}
		JSONObject data = respJson.getJSONObject("data");
		String fileId = data.getString("file_id");
		// Set file_id into rootJson using setPredefinedKeyValue
		
		return fileId;
	}
	
	private String getErrorMsg(String json) {
		String errorMsg = "";
		try {
			JSONObject respJson = new JSONObject(json);
			String ret = respJson.getString("ret");
			if (ret.equals("FAIL")) {
				JSONObject data = respJson.getJSONObject("data");
				String code = data.getString("error_code");
				errorMsg = StatusCode.getDesciption(code);
			}
    	}catch(Exception e) {
    		errorMsg = "返回数据json格式有误";
    		e.printStackTrace();
    	}
		return errorMsg;
	}
}
