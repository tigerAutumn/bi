package com.pinting.business.util;



import java.io.IOException;

import org.apache.http.client.fluent.Executor;
import org.apache.http.client.fluent.Request;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;


/**
 * HTTP 封装工具类
 * @author Hanley
 *
 */
public class HttpHelper {
	
	/**
	 * 设置连接池最大并发连接
	 */
	private int maxTotal = 100;
	/**
	 * 设置单个路由最大并发数，覆盖默认值2
	 */
	private int maxRoute = 10;
	
	private Executor executor;
	/**
	 * 读取超时时间,默认30秒
	 */
	private int socketTimeout = 30 * 1000;
	
	public HttpHelper() {
		
		CloseableHttpClient httpclient = createDefaultHttpClient(this.maxTotal, this.maxRoute);
		this.executor = Executor.newInstance(httpclient);
		
	}
	
	public HttpHelper(int maxRoute) {
		CloseableHttpClient httpclient = createDefaultHttpClient(this.maxTotal, maxRoute);
		this.executor = Executor.newInstance(httpclient);
	}
	
	private CloseableHttpClient createDefaultHttpClient(int maxTotal, int maxRoute) {
		PoolingHttpClientConnectionManager connManage = new PoolingHttpClientConnectionManager();
		connManage.setMaxTotal(maxTotal);
		connManage.setDefaultMaxPerRoute(maxRoute);
		
		CloseableHttpClient httpclient = HttpClients.custom()
		.setConnectionManager(connManage)
		.build();
		
		return httpclient;
	}
	
	public String doPost(String uri, String params, String charset) throws IOException {
		return new String(executor.execute( Request.Post(uri).socketTimeout(socketTimeout).body(new StringEntity(params, charset))).returnContent().asBytes(),charset);
		//return new String(executor.execute( Request.Post(uri).socketTimeout(socketTimeout).bodyString(params, ContentType.APPLICATION_FORM_URLENCODED)).returnContent().asBytes(),charset);
	}
	
	public String doPost(String uri, byte[] params, String charset) throws IOException {
		return new String(executor.execute( Request.Post(uri).socketTimeout(socketTimeout).bodyByteArray(params)).returnContent().asBytes(), charset);
	}
	
	public String doGetAsString(String uri, String params) throws IOException {
		return executor.execute(Request.Get(uri + "?" + params).socketTimeout(socketTimeout)).returnContent().asString();
	}
	
}
