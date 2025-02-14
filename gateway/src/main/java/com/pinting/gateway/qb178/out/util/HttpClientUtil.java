package com.pinting.gateway.qb178.out.util;

import com.pinting.core.redis.JedisClientDaoSupport;
import com.pinting.core.util.GlobEnvUtil;
import com.pinting.core.util.StringUtil;
import com.pinting.gateway.qb178.out.model.AccessToken;
import com.pinting.gateway.util.Constants;
import com.pinting.gateway.util.JacksonUtil;
import org.apache.commons.codec.binary.Base64;
import org.apache.http.*;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.CookieSpecs;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.config.RequestConfig.Builder;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.config.ConnectionConfig;
import org.apache.http.config.SocketConfig;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.CodingErrorAction;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

/**
 * 功能说明:http协议控制器
 * <p> 系统版本: v1.0<br>
 * 开发人员: luopeng12856
 * 开发时间: 2016—05-26 上午10:12 <br>
 */
public class HttpClientUtil {

	private static Logger log = LoggerFactory.getLogger(HttpClientUtil.class);
	private static JedisClientDaoSupport jsClientDaoSupport = JedisClientDaoSupport.getInstance(Constants.REDIS_SUBSYS_GATEWAY);

	private static String APPKEY    = "1c78b4b4-11d2-4362-921e-b041a1a4cf00";
	private static String APPSECRET = "d3982f75-e34f-4dcd-a82f-703083224e04";
	/**
	 * 编码格式.
	 */
	public static final String CHARSET = "UTF-8";
	public static final String METHOD_POST = "POST";
	public static final String METHOD_GET = "GET";

	/**
	 * HTTP HEADER字段 Authorization应填充字符串Bearer
	 */
	public static final String BEARER = "Bearer ";
	/**
	 * 环境
	 */
	public static String URL="https://sitapi.hscloud.cn";
	/**
	 * 环境
	 */
	public static final String OPENURL="http://open.hscloud.cn";

	/**
	 * HTTP HEADER字段 Authorization应填充字符串BASIC
	 */
	public static final String BASIC = "Basic ";


	private static CloseableHttpClient httpClient = null;
	/** 连接超时时间 */
	public final static int connectTimeout = 15000;

	/** socket连接超时时间 */
	public final static int socketTimeout = 20000;

	/** 发送请求相应时间 */
	public final static int requestTimeout = 15000;
	/** 允许管理器限制最大连接数 ，还允许每个路由器针对某个主机限制最大连接数。 */
	public static PoolingHttpClientConnectionManager cm = new PoolingHttpClientConnectionManager();
	/**
	 * 最大连接数
	 */
	public final static int MAX_TOTAL_CONNECTIONS = 500;
	/**
	 * 每个路由最大连接数 访问每个目标机器 算一个路由 默认 2个
	 */
	public final static int MAX_ROUTE_CONNECTIONS = 80;

	static {
		try {
			if (Constants.GATEWAY_SERVER_MODE_PROD.equals(GlobEnvUtil.get("server.mode"))) {
				APPKEY = GlobEnvUtil.get("qb178.app.key");
				APPSECRET = GlobEnvUtil.get("qb178.app.secret");
				URL = GlobEnvUtil.get("qb178.app.url");
			}
		}catch (Exception e){
			e.printStackTrace();
		}
		cm.setDefaultMaxPerRoute(MAX_ROUTE_CONNECTIONS);// 设置最大路由数
		cm.setMaxTotal(MAX_TOTAL_CONNECTIONS);// 最大连接数

		/**
		 * 大量的构造器设计模式，很多的配置都不建议直接new出来，而且相关的API也有所改动，例如连接参数，
		 * 以前是直接new出HttpConnectionParams对象后通过set方法逐一设置属性， 现在有了构造器，可以通过如下方式进行构造：
		 * SocketConfig.custom().setSoTimeout(100000).build();
		 */
		SocketConfig socketConfig = SocketConfig.custom().setTcpNoDelay(true)
				.build();
		cm.setDefaultSocketConfig(socketConfig);
		RequestConfig defaultRequestConfig = RequestConfig.custom()
				.setCookieSpec(CookieSpecs.BEST_MATCH)
				.setExpectContinueEnabled(true)
				.setStaleConnectionCheckEnabled(true).setRedirectsEnabled(true)
				.build();
		// CodingErrorAction.IGNORE指示通过删除错误输入、向输出缓冲区追加 coder
		// 的替换值和恢复编码操作来处理编码错误的操作。
		ConnectionConfig connectionConfig = ConnectionConfig.custom()
				.setCharset(Consts.UTF_8)
				.setMalformedInputAction(CodingErrorAction.IGNORE)
				.setUnmappableInputAction(CodingErrorAction.IGNORE).build();
		httpClient = HttpClients.custom().setConnectionManager(cm)
				.setDefaultRequestConfig(defaultRequestConfig)
				.setDefaultConnectionConfig(connectionConfig).build();
	}



	public static String sendPost(String url, Map<String, String> params,
			String charSet, String charsetReturn, HttpHost proxy,
			String authorization, String interfacename) {
		try {
			HttpPost post = new HttpPost(url);
			Builder builder = RequestConfig.custom();
			if (proxy != null) {
				builder.setProxy(proxy);
				RequestConfig requestConfig = builder
						.setSocketTimeout(socketTimeout)
						.setConnectTimeout(connectTimeout)
						.setConnectionRequestTimeout(requestTimeout)
						.setExpectContinueEnabled(false)
						.setRedirectsEnabled(true).build();
				post.setConfig(requestConfig);
			}

			post.setHeader("Content-Type", "application/x-www-form-urlencoded");
			post.setHeader("Authorization", StringUtil.isEmpty(authorization)?HttpClientUtil.BEARER + getAccessToken():authorization);
			List<NameValuePair> nvps = new ArrayList<NameValuePair>();
			StringBuffer sb = new StringBuffer();
			if (params != null) {
				int n = 0;
				for (Entry<String, String> set : params.entrySet()) {
					if (n == 0) {
						n++;
						sb.append(set.getKey() + "=" + set.getValue());
					} else {
						sb.append("&" + set.getKey() + "=" + set.getValue());
					}
					nvps.add(new BasicNameValuePair(set.getKey(), set
							.getValue()));
				}
			}
			post.setEntity(new UrlEncodedFormEntity(nvps, charSet));
			LogUtils.log("\n功能名称："+interfacename+"\n"+ "post  url = ["
					+ (url.endsWith("?") ? url : url + "?") + sb.toString()
					+ "]", log);

			HttpResponse response = httpClient.execute(post);
			int status = response.getStatusLine().getStatusCode();
			HttpEntity entity = null;
			try {
				entity = response.getEntity();
				if (entity != null) {
					String result = EntityUtils.toString(entity, charsetReturn);
					LogUtils.log("result = " + result, log);
					return result;

				}
			} catch (Exception e) {
				LogUtils.log("HttpClient   请求 http状态码 status = [" + status
						+ "]  获取HttpEntity ", e, log);
			} finally {
				if (entity != null) {
					entity.getContent().close();
				}
			}
		} catch (ClientProtocolException e) {
			LogUtils.log("HttpClient   请求  ClientProtocolException ", e, log);
		} catch (IOException e) {
			LogUtils.log("HttpClient   请求  IOException ", e, log);
		}
		return null;
	}

	/**
	 * get请求
	 * 
	 * @param url
	 * @param params
	 * @param charSet
	 * @return
	 */
	public static String sendGet(String url, Map<String, String> params,
			String charSet, HttpHost proxy, String authorization,
			String interfacename) {
		try {
			StringBuffer urlbuf = new StringBuffer(url);
			if (params != null) {
				int n = 0;
				for (Entry<String, String> set : params.entrySet()) {
					if (!urlbuf.toString().contains("?")) {
						urlbuf.append("?");
					}
					if (n != 0) {
						urlbuf.append("&");
					}
					urlbuf.append(set.getKey()).append("=")
							.append(set.getValue());
					n++;
				}
			}
			LogUtils.log("\n功能名称："+interfacename+"\n"+ "post  url = [" + urlbuf.toString()+"]", log);
			HttpGet get = new HttpGet(urlbuf.toString());
			get.setHeader("Content-Type", "application/x-www-form-urlencoded");
			get.setHeader("Authorization", StringUtil.isEmpty(authorization)?HttpClientUtil.BEARER + getAccessToken():authorization);
			// HttpUriRequest get = new HttpGet(urlbuf.toString());
			Builder builder = RequestConfig.custom();
			if (proxy != null) {
				builder.setProxy(proxy);
			}

			RequestConfig defaultConfig = builder
					.setSocketTimeout(socketTimeout)
					.setConnectTimeout(connectTimeout)
					.setConnectionRequestTimeout(requestTimeout)
					.setExpectContinueEnabled(false).setRedirectsEnabled(true)
					.build();
			get.setConfig(defaultConfig);

			HttpResponse response = httpClient.execute(get);

			int status = response.getStatusLine().getStatusCode();
			HttpEntity entity = null;
			try {
				entity = response.getEntity();
				if (entity != null) {
					String result = EntityUtils.toString(entity, charSet);
					LogUtils.log("status = [" + status +"]  result = " + result, log);
					return result;

				}
			} catch (Exception e) {
				LogUtils.log("HttpClient   请求 http状态码 status = [" + status
						+ "]  ", e, log);
			} finally {
				if (entity != null) {
					entity.getContent().close();
				}
			}
		} catch (ClientProtocolException e) {
			LogUtils.log("HttpClient   请求  ClientProtocolException ", e, log);
		} catch (IOException e) {
			LogUtils.log("HttpClient   请求  IOException ", e, log);
		}
		return null;
	}
	
	/**
	 * cifangf 是对"App Key:App Secret"进行 Base64 编码后的字符串（区分大小写，包含冒号，但不包含双引号,采用
	 * UTF-8 编码）。 例如: Authorization: Basic eHh4LUtleS14eHg6eHh4LXNlY3JldC14eHg=
	 * 其中App Key和App Secret可在开放平台上创建应用后获取。
	 */
	public static String Base64(String appkey,String  appsecret,String basic) throws UnsupportedEncodingException {
		String str = appkey+":"+appsecret;
		byte[] encodeBase64 = Base64.encodeBase64(str
				.getBytes(HttpClientUtil.CHARSET));
		LogUtils.log("\n功能名称：AppKey:AppSecret Base64编码"+"\n"+new String(encodeBase64),log);
		return basic+new String(encodeBase64);
	}

	/**
	 * token获取每12小时
	 * @return
     */
	public static String getAccessToken(){
		try {
			String accessTokenStr = "";
			try {
				//accessTokenStr = jsClientDaoSupport.getString("qb178_access_token");
			}catch (Exception e){
				e.printStackTrace();
			}
			if(StringUtil.isEmpty(accessTokenStr)){
				// 客户端凭证模式 获取令牌
				Map<String, String> token_map = new HashMap<String, String>();
				token_map.put("grant_type", "client_credentials");// 客户端凭证模式时，必须为“client_credentials”；
				String tokenResult = HttpClientUtil.sendPost(HttpClientUtil.URL + "/oauth2/oauth2/token", token_map,
						HttpClientUtil.CHARSET, HttpClientUtil.CHARSET, null, HttpClientUtil.Base64(APPKEY, APPSECRET, BASIC),
						"获取公共令牌");
				// 共有令牌返回数据json解析
				AccessToken accesstoken = JacksonUtil.json2Bean(tokenResult, AccessToken.class);
				if(accesstoken != null){
					accessTokenStr = accesstoken.getAccess_token();
					try {
						jsClientDaoSupport.setString(accesstoken.getAccess_token(), "qb178_access_token", 3600 * 1);//每1小时重新获取
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
			log.info("获得钱报178公共令牌："+accessTokenStr);
			return accessTokenStr;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

	}



	public static class LogUtils {

		public static void log(String msg, Logger log) {
			System.out.println(msg);
			log.info(msg);
		}

		public static void log(String msg, Exception e, Logger log) {
			System.out.println(msg + " 异常 message = [" + e.getMessage() + "]");
			log.info(msg + " 异常 message = [" + e.getMessage() + "]", e);
		}

		public static void error(String msg, Exception e, Logger log) {
			System.out.println(msg + " 异常 message = [" + e.getMessage() + "]");
			log.error(msg + " 异常 message = [" + e.getMessage() + "]", e);
		}

	}

	public static void main(String[] args){
		getAccessToken();
	}


}
