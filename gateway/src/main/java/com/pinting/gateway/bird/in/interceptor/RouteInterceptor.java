package com.pinting.gateway.bird.in.interceptor;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import javax.servlet.ServletInputStream;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import net.sf.json.JSONObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.pinting.gateway.zsd.in.util.ZsdInConstant;

/**
 *  
 * @project gateway
 * @title RouteInterceptor.java
 * @author Dragon & cat
 * @date 2017-9-1
 * @Copyright 2015 BiGangWan.com Inc. All rights reserved.
 * @Description
 */
public class RouteInterceptor  extends HandlerInterceptorAdapter {
	 private Logger log = LoggerFactory.getLogger(RouteInterceptor.class);
	 
	    @Override
	    public boolean preHandle(HttpServletRequest request,
	                             HttpServletResponse response, Object handler) throws Exception {            // body中前台传入的参数  
	    	String method = request.getMethod();
	    	log.info("拦截器拦截到请求类型："+method);
	    	if ("POST".equals(method) || "PUT".equals(method)) {
	    		String bodyParam = getBodyString(request);  
	            log.info("拦截器拦截到request参数："+bodyParam);
	            String requestPathInfo = request.getPathInfo();
	            log.info("拦截器拦截到pathInfo："+requestPathInfo);
	            JSONObject json = JSONObject.fromObject(bodyParam);  
	            String clientKey = (String) json.get("clientKey");
				if (ZsdInConstant.CLIENTKEY.equals(clientKey)) {
					requestPathInfo = requestPathInfo.replace("rest", "rest_zsd");
					request.getRequestDispatcher("/remote"+requestPathInfo).forward(request, response);
					return false;   
				}
			}else if ("GET".equals(method)) {
				String clientKey = request.getParameter("clientKey");
				log.info("拦截器拦截到clientKey："+clientKey);
				String requestPathInfo = request.getPathInfo();
	            log.info("拦截器拦截到pathInfo："+requestPathInfo);
				if (ZsdInConstant.CLIENTKEY.equals(clientKey)) {
					requestPathInfo = requestPathInfo.replace("rest", "rest_zsd");
					request.getRequestDispatcher("/remote"+requestPathInfo).forward(request, response);
					return false;   
				}
			}
            return true;  
        }
	    
	    
	    /** 
	     * 获取请求Body 
	     * 
	     * @param request 
	     * @return 
	     */  
	    public static String getBodyString(final ServletRequest request) {  
	        StringBuilder sb = new StringBuilder();  
	        InputStream inputStream = null;  
	        BufferedReader reader = null;  
	        try {  
	            inputStream = cloneInputStream(request.getInputStream());  
	            reader = new BufferedReader(new InputStreamReader(inputStream, Charset.forName("UTF-8")));  
	            String line = "";  
	            while ((line = reader.readLine()) != null) {  
	                sb.append(line);  
	            }  
	        }  
	        catch (IOException e) {  
	            e.printStackTrace();  
	        }  
	        finally {  
	            if (inputStream != null) {  
	                try {  
	                    inputStream.close();  
	                }  
	                catch (IOException e) {  
	                    e.printStackTrace();  
	                }  
	            }  
	            if (reader != null) {  
	                try {  
	                    reader.close();  
	                }  
	                catch (IOException e) {  
	                    e.printStackTrace();  
	                }  
	            }  
	        }  
	        return sb.toString();  
	    }  
	  
	    /** 
	     * Description: 复制输入流</br> 
	     *  
	     * @param inputStream 
	     * @return</br> 
	     */  
	    public static InputStream cloneInputStream(ServletInputStream inputStream) {  
	        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();  
	        byte[] buffer = new byte[1024];  
	        int len;  
	        try {  
	            while ((len = inputStream.read(buffer)) > -1) {  
	                byteArrayOutputStream.write(buffer, 0, len);  
	            }  
	            byteArrayOutputStream.flush();  
	        }  
	        catch (IOException e) {  
	            e.printStackTrace();  
	        }  
	        InputStream byteArrayInputStream = new ByteArrayInputStream(byteArrayOutputStream.toByteArray());  
	        return byteArrayInputStream;  
	    }  
	    
	    
	    
}
