package com.pinting.util;

import java.util.HashMap;
import java.util.Map;

import org.jsoup.Connection;
import org.jsoup.Jsoup;

import net.sf.json.JSONObject;

public class HttpUtil {

	public static JSONObject postHttp(String url, Map<String, String> map) throws Exception{
		Connection conn = getConn(url);
		conn.method(Connection.Method.POST);
		conn.data(map);
		Connection.Response res = conn.execute();
		return JSONObject.fromObject(res.body());
	}

	public static String postHttpTime(String url, Map<String, String> map, Integer time) throws Exception{
		Connection conn = getConn(url);
		if (time != null) {
			conn.timeout(time);
		}
		conn.method(Connection.Method.POST);
		conn.data(map);
		Connection.Response res = conn.execute();
		return res.body();
	}

	public static JSONObject postHttp(String url, Map<String, String> map, Integer time) throws Exception{
		Connection conn = getConn(url);
		if (time != null) {
			conn.timeout(time);
		}
		conn.method(Connection.Method.POST);
		conn.data(map);
		Connection.Response res = conn.execute();
		System.out.println(res.body());
		return JSONObject.fromObject(res.body());
	}

	public static JSONObject postHttpNoParam(String url) throws Exception{
		Connection conn = getConn(url);
		Connection.Response res = conn.execute();
		return JSONObject.fromObject(res.body());
	}

	public static Connection getConn(String url) {
		Connection conn = Jsoup.connect(url);
		conn.userAgent("Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/29.0.1547.0 Safari/537.36");
		conn.timeout(30000);
		return conn;
	}
	
	public static JSONObject postHttpJson(String url, Map<String, String> map) throws Exception{
		if(map.size() > 0) {
			Connection conn = getConn(url);
			conn.ignoreContentType(true);
			conn.method(Connection.Method.POST);
			conn.data(map);
			Connection.Response res = conn.execute();
			return JSONObject.fromObject(res.body());
		}
		return null;
	}
	
}
