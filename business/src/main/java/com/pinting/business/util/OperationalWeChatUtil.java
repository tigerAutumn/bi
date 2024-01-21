package com.pinting.business.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Date;
import java.util.HashMap;

import com.pinting.business.enums.RedisLockEnum;
import com.pinting.business.model.BsSysConfig;
import com.pinting.core.redis.JedisClientDaoSupport;
import com.pinting.core.util.GlobEnvUtil;
import com.pinting.core.util.StringUtil;

import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class OperationalWeChatUtil {
	private static JedisClientDaoSupport jsClientDaoSupport = JedisClientDaoSupport.getInstance(Constants.REDIS_SUBSYS_BUSINESS);

	private static final Logger log = LoggerFactory.getLogger(OperationalWeChatUtil.class);

    public static final String ENCODE      = "UTF-8";
    public static String appid = "";
	public static String secret = "";
   
    public static String token = ""; // 与接口配置信息中的Token要一致

    //String getAccessTokenUrl = "https://api.weixin.qq.com/sns/oauth2/access_token";
	private static final String tokenUrl = "https://api.weixin.qq.com/cgi-bin/token";//获取token的链接
	private static final String ticketUrl = "https://api.weixin.qq.com/cgi-bin/ticket/getticket";//获取jsTicket的链接
    //private static AccessToken accessToken;//AccessToken对象
    //private static JSApiTicket ticket;//JSApiTicket对象
    
    //静态加载，business,site,manage 都有可能先调用，所以这3者的placeholder.properties文件
	static {
		appid = GlobEnvUtil.get("wechat.operational.appid");
		secret = GlobEnvUtil.get("wechat.operational.secret");
		token = GlobEnvUtil.get("wechat.operational.token");
	}
	
	//private static Object accessTokenSyncObj = new Object();
	//private static Object ticketSyncObj = new Object();
	/**
	 * 确保access token已经获取
	 */
	private static void ensureAccessToken(String requstFlag){
		//如果token已经有，且还在有效期内，不需要再次获取
		OperationalAccessToken redisAccessToken = getRedisAccessToken();
		try {
			jsClientDaoSupport.lock(RedisLockEnum.LOCK_ACCESSTOKEN_SYNC.getKey());
			if(redisAccessToken != null){
				long current = System.currentTimeMillis();
				long last = redisAccessToken.getLastTime().getTime();
				if((current - last) / 1000 + 30*60 < redisAccessToken.getExpire()){
					return;
				}
			}
		} finally {
			jsClientDaoSupport.unlock(RedisLockEnum.LOCK_ACCESSTOKEN_SYNC.getKey());
		}	
		
		//第一次访问或者access token 已经过期，需要重新获取
		
        StringBuffer sb = new StringBuffer();
        sb.append("&appid=" + appid + "&secret=" + secret + "&grant_type=client_credential");
		log.info("微信appid：{}", appid);
        String retJson = "";
        try {
        	retJson = sendGet(tokenUrl, sb.toString(), false, "UTF-8");
	        JSONObject jo = JSONObject.fromObject(retJson);
	        log.info("微信AccessToken：{}", jo.toString());
	        try {
				jsClientDaoSupport.lock(RedisLockEnum.LOCK_ACCESSTOKEN_SYNC.getKey());
				
				try {
					OperationalAccessToken accessToken = new OperationalAccessToken(jo.getString("access_token"), Integer.valueOf(jo.getString("expires_in")), new Date());
					jsClientDaoSupport.addOrUpdateObj(accessToken, "opwx_access_token", 7500);
				} catch (Exception e) {
					e.printStackTrace();
				}
			} finally {
				jsClientDaoSupport.unlock(RedisLockEnum.LOCK_ACCESSTOKEN_SYNC.getKey());
			}
	        
        } catch (IOException e) {
            e.printStackTrace();
        }
	}

	private static void ensureAccessToken(String requstFlag, String wxAppId, String wxSecret){

		log.info("AccessToken传入的appId参数:"+wxAppId);
		log.info("AccessToken传入的secret参数:"+wxSecret);

		//如果token已经有，且还在有效期内，不需要再次获取
		OperationalAccessToken redisAccessToken = getRedisAccessToken();
		try {
			jsClientDaoSupport.lock(RedisLockEnum.LOCK_ACCESSTOKEN_SYNC.getKey());
			if(redisAccessToken != null){
				long current = System.currentTimeMillis();
				long last = redisAccessToken.getLastTime().getTime();
				if((current - last) / 1000 + 30*60 < redisAccessToken.getExpire()){
					return;
				}
			}
		} finally {
			jsClientDaoSupport.unlock(RedisLockEnum.LOCK_ACCESSTOKEN_SYNC.getKey());
		}	
		
		//第一次访问或者access token 已经过期，需要重新获取
		
        StringBuffer sb = new StringBuffer();
        sb.append("&appid=" + wxAppId + "&secret=" + wxSecret + "&grant_type=client_credential");
        String retJson = "";
        try {
        	retJson = sendGet(tokenUrl, sb.toString(), false, "UTF-8");

			log.info("发送get请求返回的json数据："+retJson.toString());

	        JSONObject jo = JSONObject.fromObject(retJson);
	        
	        try {
				jsClientDaoSupport.lock(RedisLockEnum.LOCK_ACCESSTOKEN_SYNC.getKey());
				
				try {
					OperationalAccessToken accessToken = new OperationalAccessToken(jo.getString("access_token"), Integer.valueOf(jo.getString("expires_in")), new Date());
					jsClientDaoSupport.addOrUpdateObj(accessToken, "opwx_access_token", 7500);
				} catch (Exception e) {
					e.printStackTrace();
				}
			} finally {
				jsClientDaoSupport.unlock(RedisLockEnum.LOCK_ACCESSTOKEN_SYNC.getKey());
			}
	        
        } catch (IOException e) {
            e.printStackTrace();
        }
	}

	/**
	 * 确保jsapiTicket已经获取
	 */
	private static void ensureJSApiTicket(String requstFlag){
		//如果ticket已经有，且还在有效期内，不需要再次获取
		OperationalJSApiTicket redisJSApiTicket = getRedisTicket();
		try {
			jsClientDaoSupport.lock(RedisLockEnum.LOCK_TICKET_SYNC.getKey());
			if(redisJSApiTicket != null){
				long current = System.currentTimeMillis();
				long last = redisJSApiTicket.getLastTime().getTime();
				if((current - last) / 1000 + 30*60 < redisJSApiTicket.getExpire()){
					return;
				}
			}
		} finally {
			jsClientDaoSupport.unlock(RedisLockEnum.LOCK_TICKET_SYNC.getKey());
		}	
		
		//第一次访问或者jsapiTicket 已经过期，需要重新获取
        StringBuffer sb = new StringBuffer();
        ensureAccessToken(requstFlag);
        sb.append("access_token=" + getRedisAccessToken().getAccessToken() + "&type=jsapi");
        String jsapiTicket = "";
        try {
            jsapiTicket = sendGet(ticketUrl, sb.toString(), false, "UTF-8");
            JSONObject jo = JSONObject.fromObject(jsapiTicket);
            
            try {
    			jsClientDaoSupport.lock(RedisLockEnum.LOCK_TICKET_SYNC.getKey());
    			
    			try {
    				OperationalJSApiTicket ticket = new OperationalJSApiTicket(jo.getString("ticket"), Integer.valueOf(jo.getString("expires_in")), new Date());
					jsClientDaoSupport.addOrUpdateObj(ticket, "opwx_ticket", 7500);
				} catch (Exception e) {
					e.printStackTrace();
				}
				
    		} finally {
    			jsClientDaoSupport.unlock(RedisLockEnum.LOCK_TICKET_SYNC.getKey());
    		}
            
        } catch (IOException e) {
            e.printStackTrace();
        }
        
	}

	private static void ensureJSApiTicket(String requstFlag, String wxAppId, String wxSecret){
		//如果ticket已经有，且还在有效期内，不需要再次获取
		OperationalJSApiTicket redisJSApiTicket = getRedisTicket();
		try {
			jsClientDaoSupport.lock(RedisLockEnum.LOCK_TICKET_SYNC.getKey());
			if(redisJSApiTicket != null){
				long current = System.currentTimeMillis();
				long last = redisJSApiTicket.getLastTime().getTime();
				if((current - last) / 1000 + 30*60 < redisJSApiTicket.getExpire()){
					return;
				}
			}
		} finally {
			jsClientDaoSupport.unlock(RedisLockEnum.LOCK_TICKET_SYNC.getKey());
		}	
		
		//第一次访问或者jsapiTicket 已经过期，需要重新获取
        StringBuffer sb = new StringBuffer();
        ensureAccessToken(requstFlag, wxAppId, wxSecret);
        sb.append("access_token=" + getRedisAccessToken().getAccessToken() + "&type=jsapi");
        String jsapiTicket = "";
        try {
            jsapiTicket = sendGet(ticketUrl, sb.toString(), false, "UTF-8");
            JSONObject jo = JSONObject.fromObject(jsapiTicket);
            
            try {
    			jsClientDaoSupport.lock(RedisLockEnum.LOCK_TICKET_SYNC.getKey());
    			
    			try {
    				OperationalJSApiTicket ticket = new OperationalJSApiTicket(jo.getString("ticket"), Integer.valueOf(jo.getString("expires_in")), new Date());
					jsClientDaoSupport.addOrUpdateObj(ticket, "opwx_ticket", 7500);
				} catch (Exception e) {
					e.printStackTrace();
				}
				
    		} finally {
    			jsClientDaoSupport.unlock(RedisLockEnum.LOCK_TICKET_SYNC.getKey());
    		}
            
        } catch (IOException e) {
            e.printStackTrace();
        }
        
	}

    public static String getJsapiTicket(String requstFlag) {
    	ensureJSApiTicket(requstFlag);
    	return (String) getRedisTicket().getTicket();
    }

    /**
     * 获得access_token,确保是有效的
     * @return
     */
    public static String getAccessToken(String requstFlag) {
    	ensureAccessToken(requstFlag);
        return (String) getRedisAccessToken().getAccessToken();
    }

    /**
     * 获得access_token,确保是有效的
     * @return
     */
    public static String getAccessToken(String requstFlag, String wxAppId, String wxSecret) {
    	ensureAccessToken(requstFlag, wxAppId, wxSecret);
        return (String) getRedisAccessToken().getAccessToken();
    }
    
    public static String getJsapiTicket(String requstFlag, String wxAppId, String wxSecret) {
    	ensureJSApiTicket(requstFlag, wxAppId, wxSecret);
    	return (String) getRedisTicket().getTicket();
    }
    
    /**
     * 发送 HTTP GET 请求
     * @param url
     *          请求的 URL 地址
     * @param param
     *          请求的参数内容
     * @param isForm
     *          是否是form请求
     * @param encode
     *          编码方式，null为默认编码
     * @return
     * @throws IOException 
     */
    public static String sendGet(String url, String param, boolean isForm, String encode)
                                                                                         throws IOException {
        String result = "";

        // 打开连接，并设置参数
        URL httpUrl = new URL(url);
        HttpURLConnection conn = (HttpURLConnection) httpUrl.openConnection();
        conn.setDoInput(true); // 设置可输入
        conn.setDoOutput(true);// 设置可输出
        conn.setRequestMethod("GET");//设置请求方式为GET
        if (isForm)
            conn.setRequestProperty("Content-type", "application/x-www-form-urlencoded");
        conn.connect();

        // 输出
        write(conn.getOutputStream(), param, encode);
        conn.getOutputStream().flush();
        conn.getOutputStream().close();

        //输入
        result = read(conn.getInputStream(), 1024, ENCODE);
        conn.getInputStream().close();

        // 关闭连接
        conn.disconnect();

        return result;
    }

    /**
     * 发送 HTTP POST 请求
     * @param url
     *          请求的 URL 地址
     * @param param
     *          请求的参数内容
     * @param isForm
     *          是否是form请求
     * @param encode
     *          编码方式，null为默认编码
     * @return
     * @throws IOException 
     */
    public static String sendPost(String url, String param, boolean isForm, String encode)
                                                                                          throws IOException {
        String result = "";

        // 打开连接，并设置参数
        URL httpUrl = new URL(url);
        HttpURLConnection conn = (HttpURLConnection) httpUrl.openConnection();
        conn.setDoInput(true); // 设置可输入
        conn.setDoOutput(true);// 设置可输出
        conn.setRequestMethod("POST");//设置请求方式为POST
        if (isForm)
            conn.setRequestProperty("Content-type", "application/x-www-form-urlencoded");
        conn.connect();

        // 输出
        write(conn.getOutputStream(), param, encode);
        conn.getOutputStream().flush();
        conn.getOutputStream().close();

        //输入
        result = read(conn.getInputStream(), 1024, ENCODE);
        conn.getInputStream().close();

        // 关闭连接
        conn.disconnect();

        return result;
    }

    /**
     * 指定输入流和编码方式输入指定信息
     * @param out
     * @param msg
     * @param encode
     * @throws IOException 
     */
    public static void write(OutputStream out, String msg, String encode) throws IOException {
        byte[] b = msg.getBytes(encode);
        out.write(b);
        out.flush();
    }

    /**
     * 指定缓存大小，编码方式读取输入流
     * @param in
     *          输入流
     * @param length
     *          缓存大小
     * @param encode
     *          编码方式
     * @return
     * @throws IOException
     */
    public static String read(InputStream in, int length, String encode) throws IOException {
        StringBuilder result = new StringBuilder();

        byte[] b = new byte[length];
        int len = -1;
        while (-1 != (len = in.read(b)))
            result.append(new String(b, 0, len, encode));

        return result.toString();
    }
    
    public static OperationalAccessToken getRedisAccessToken(){
    	OperationalAccessToken redisAccessToken = null;
		try {
			redisAccessToken = jsClientDaoSupport.getObj(OperationalAccessToken.class, "opwx_access_token");
		} catch (Exception e) {
			e.printStackTrace();
		}
    	return redisAccessToken;
    }
    
    public static OperationalJSApiTicket getRedisTicket(){
    	OperationalJSApiTicket redisAccessToken = null;
		try {
			redisAccessToken = jsClientDaoSupport.getObj(OperationalJSApiTicket.class, "opwx_ticket");
		} catch (Exception e) {
			e.printStackTrace();
		}
    	return redisAccessToken;
    }
    
}

/**
 * AccessToken存储类
 * @author Changzai Zhou
 * 2015-08-27 17:10:11
 */
class OperationalAccessToken implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 5930487996574277930L;
	private String accessToken;//access_token
	private int expire;//有效时间（s）
	private Date lastTime;//最后一次获得的时间
	
	public OperationalAccessToken(String accessToken, int expire, Date lastTime) {
		super();
		this.accessToken = accessToken;
		this.expire = expire;
		this.lastTime = lastTime;
	}
	public String getAccessToken() {
		return accessToken;
	}
	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}
	public int getExpire() {
		return expire;
	}
	public void setExpire(int expire) {
		this.expire = expire;
	}
	public Date getLastTime() {
		return lastTime;
	}
	public void setLastTime(Date lastTime) {
		this.lastTime = lastTime;
	}
	
	
}

/**
 * JSApiTicket存储类
 * @author Changzai Zhou
 * 2015-10-28 10:25:12
 */
class OperationalJSApiTicket implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -2049580439149196406L;
	private String ticket;//jsapiTicket
	private int expire;//有效时间
	private Date lastTime;//最后一次获得的时间
	
	public OperationalJSApiTicket(String ticket, int expire, Date lastTime) {
		super();
		this.ticket = ticket;
		this.expire = expire;
		this.lastTime = lastTime;
	}
	public String getTicket() {
		return ticket;
	}
	public void setTicket(String ticket) {
		this.ticket = ticket;
	}
	public int getExpire() {
		return expire;
	}
	public void setExpire(int expire) {
		this.expire = expire;
	}
	public Date getLastTime() {
		return lastTime;
	}
	public void setLastTime(Date lastTime) {
		this.lastTime = lastTime;
	}
	
}
