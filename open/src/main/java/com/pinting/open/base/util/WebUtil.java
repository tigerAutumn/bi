package com.pinting.open.base.util;

import org.jsoup.Connection;
import org.jsoup.Jsoup;

import javax.net.ssl.*;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Map;

public class WebUtil {
	
	public static String postHttp(String url, Map<String, String> map) throws Exception {
		trustEveryone();
		Connection conn = getConn(url);
		conn.method(Connection.Method.POST);
		conn.data(map);
		Connection.Response res = conn.execute();
		//3DES解密
		String result = new DESUtil(Constants.OPENDESKEY).decryptStr(res.body());
		return result;
	}

	public static Connection getConn(String url) {
		Connection conn = Jsoup.connect(url);
		conn.userAgent("Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/29.0.1547.0 Safari/537.36");
		conn.ignoreContentType(true);
		//http链接和获取数据的超时时间为2分钟
		//一旦超时，http底层会自动重新发起请求。就会出现客户端发了一次请求，但是服务端接收到多次的情况。
		//解决方案：
		//1、超时时间设置的足够长
		//2、使用httpclient，设置defaultHttpClient.setHttpRequestRetryHandler(new DefaultHttpRequestRetryHandler(0, false))
		conn.timeout(120*1000);
		return conn;
	}

	public static void trustEveryone() {
		try {
			HttpsURLConnection.setDefaultHostnameVerifier(new HostnameVerifier() {
				public boolean verify(String hostname, SSLSession session) {
					return true;
				}
			});

			SSLContext context = SSLContext.getInstance("TLS");
			context.init(null, new X509TrustManager[] { new X509TrustManager() {
				public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
				}

				public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
				}

				public X509Certificate[] getAcceptedIssuers() {
					return new X509Certificate[0];
				}
			} }, new SecureRandom());
			HttpsURLConnection.setDefaultSSLSocketFactory(context.getSocketFactory());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

//	public static String postHttp(String url, Map<String, String> map) throws Exception {
//		HttpURLConnection conn = null;
//		PrintWriter pw = null;
//		BufferedReader in = null;
//		StringBuffer sb = new StringBuffer();
//		try {
//			StringBuffer param = new StringBuffer();
//			for(Map.Entry<String, String> entry : map.entrySet()) {
//				param.append(entry.getKey());
//				param.append("=");
//				param.append(URLEncoder.encode(entry.getValue(), "utf-8"));
//				param.append("&");
//			}
//			param.deleteCharAt(param.length()-1);
//			
//			conn = createHttpConnection(url);
//			//post请求一定要设置以下两行
//			conn.setDoOutput(true);
//			conn.setDoInput(true);
//			conn.setRequestMethod("POST");
//			conn.connect();
//			//通过post方式发送数据
//			pw = new PrintWriter(conn.getOutputStream());
//			pw.write(param.toString());
//			pw.flush();
//			//接收返回数据
//			in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "utf-8"));
//			String s = ""; 
//			while((s = in.readLine()) != null){
//				sb.append(s);
//			}
//			String result = new DESUtil(Constants.OPENDESKEY).decryptStr(sb.toString());
//			return result;
//		}catch(Exception e) {
//			throw e;
//		}finally{
//			//关闭连接
//			try {
//				if(in != null) {
//					in.close();
//				}
//				if(pw != null) {
//					pw.close();
//				}
//				if(conn != null) {
//					conn.disconnect();
//				}
//			}catch(Exception e) {
//				e.printStackTrace();
//			}
//		}
//	}
//	
//	public static String getHttp(String url, Map<String, String> map) throws Exception {
//		HttpURLConnection conn = null;
//		BufferedReader in = null;
//		StringBuffer sb = new StringBuffer();
//		try {
//			StringBuffer param = new StringBuffer();
//			for(Map.Entry<String, String> entry : map.entrySet()) {
//				param.append(entry.getKey());
//				param.append("=");
//				param.append(URLEncoder.encode(entry.getValue(), "utf-8"));
//				param.append("&");
//			}
//			param.deleteCharAt(param.length()-1);
//			//通过get方式连接
//			conn = createHttpConnection(url+"?"+param.toString());
//			conn.setRequestMethod("GET");
//			conn.connect();
//			//接收返回数据
//			in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "utf-8"));
//			String s = ""; 
//			while((s = in.readLine()) != null){
//				sb.append(s);
//			}
//			String result = new DESUtil(Constants.OPENDESKEY).decryptStr(sb.toString());
//			return result;
//		}catch(Exception e) {
//			throw e;
//		}finally{
//			//关闭连接
//			try {
//				if(in != null) {
//					in.close();
//				}
//				if(conn != null) {
//					conn.disconnect();
//				}
//			}catch(Exception e) {
//				e.printStackTrace();
//			}
//		}
//	}
//	
//	private static HttpURLConnection createHttpConnection(String url) throws Exception{
//		HttpURLConnection conn = null;
//		URL real = new URL(url);
//		conn = (HttpURLConnection) real.openConnection();
//		//配置http请求中的header
//		conn.setRequestProperty("accept", "*/*");
//		conn.setRequestProperty("connection", "Keep-Alive");
//		conn.setRequestProperty("user-agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/29.0.1547.0 Safari/537.36");
//		conn.setRequestProperty("Accept-Charset", "UTF-8");
//		//设置超时时间
//		//一旦超时，http底层会自动重新发起请求。就会出现客户端发了一次请求，但是服务端接收到多次的情况。
//		//解决方案：
//		//1、超时时间设置的足够长
//		//2、使用httpclient，设置defaultHttpClient.setHttpRequestRetryHandler(new DefaultHttpRequestRetryHandler(0, false))
//		conn.setConnectTimeout(30000); //连接超时时间为30s
//		conn.setReadTimeout(30000); //读取超时时间为30s
//		conn.setUseCaches(false);
//		return conn;
//	}
}
