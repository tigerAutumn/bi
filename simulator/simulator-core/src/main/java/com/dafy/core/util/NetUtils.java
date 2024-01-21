package com.dafy.core.util;

import com.google.gson.Gson;
import net.sf.json.JSONArray;
import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicHeader;
import org.apache.http.protocol.HTTP;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.*;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * by shark 2016/11/04
 */
public class NetUtils {
    private static Logger log = LoggerFactory.getLogger(NetUtils.class);
    public static final String ENC_UTF8 = "UTF-8";
    private static final String CONTENT_JSON = "application/json;charset=";
    private static final String CONTENT_XML = "application/xml;charset=";
    private static final int DEFAULT_CONNECT_TIMEOUT = 3;  // 默认超时时间
    public static final String REQUEST_POST = "POST";
    public static final String REQUEST_PUT = "PUT";
    public static final String REQUEST_GET = "GET";
    public static final String REQUEST_DELETE = "DELETE";
    /**
     * 从流中获取数据
     *
     * @param inputStream 流
     * @return 字符串
     * @throws IOException 异常
     */
    public static String getStringFromInput(InputStream inputStream) throws IOException {
        ByteArrayOutputStream outSteam = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int len = -1;
        while ((len = inputStream.read(buffer)) != -1) {
            outSteam.write(buffer, 0, len);
        }
        byte[] bytes = outSteam.toByteArray();
        String result = new String(bytes);
        outSteam.close();
        return result;
    }

    /**
     * 以POST发送json请求
     *
     * @param url    url
     * @param data   json数据
     * @param encode 编码
     * @return 返回响应数据
     */
    public static String sendJsonDataByPost(String url, String data, String encode) throws IOException {
        String content = CONTENT_JSON + (!StringUtils.isNotEmpty(encode) ? ENC_UTF8 : encode);
        return sendDataByPost(url, data, encode, content, DEFAULT_CONNECT_TIMEOUT);
    }

    /**
     * 以POST发送json请求
     *
     * @param url            url
     * @param data           json数据
     * @param encode         编码
     * @param connectTimeOut 超时时间
     * @return 返回响应数据
     */
    public static String sendJsonDataByPost(String url, String data, String encode, int connectTimeOut) throws IOException {
        String content = CONTENT_JSON + (!StringUtils.isNotEmpty(encode) ? ENC_UTF8 : encode);
        return sendDataByPost(url, data, encode, content, connectTimeOut);
    }

    /**
     * 以PUT发送json请求
     *
     * @param url            url
     * @param data           json数据
     * @param encode         编码
     * @param connectTimeOut 超时时间
     * @return 返回响应数据
     */
    public static String sendJsonDataByPut(String url, String data, String encode, int connectTimeOut) throws IOException {
        String content = CONTENT_JSON + (!StringUtils.isNotEmpty(encode) ? ENC_UTF8 : encode);
        return sendDataByPut(url, data, encode, content, connectTimeOut);
    }

    
    /**
     * 以PUT发送json请求
     *
     * @param url            url
     * @param data           json数据
     * @param encode         编码
     * @param connectTimeOut 超时时间
     * @return 返回响应数据
     */
    public static String sendJsonDataByPut(String url, String data, String encode, int connectTimeOut,String token) throws IOException {
        String content = CONTENT_JSON + (!StringUtils.isNotEmpty(encode) ? ENC_UTF8 : encode);
        return sendDataByPut(url, data, encode, content, connectTimeOut,token);
    }
    
    /**
     * 以POST发送xml请求
     *
     * @param url    url
     * @param data   xml数据
     * @param encode 编码
     * @return 返回响应数据
     */
    public static String sendXmlDataByPost(String url, String data, String encode) throws IOException {
        String content = CONTENT_XML + (!StringUtils.isNotEmpty(encode) ? ENC_UTF8 : encode);
        return sendDataByPost(url, data, encode, content, DEFAULT_CONNECT_TIMEOUT);
    }

    /**
     * 以POST发送xml请求
     *
     * @param url            url
     * @param data           xml数据
     * @param encode         编码
     * @param connectTimeOut 超时时间
     * @return 返回响应数据
     */
    public static String sendXmlDataByPost(String url, String data, String encode, int connectTimeOut) throws IOException {
        String content = CONTENT_XML + (!StringUtils.isNotEmpty(encode) ? ENC_UTF8 : encode);
        return sendDataByPost(url, data, encode, content, connectTimeOut);
    }
    /**
     * post请求
     * @param postUrl
     * @param params
     * @param connectTimeOut
     * @param header
     * @return
     */
    public static String sendHFDataByPost(String postUrl, Map<String, String> params, int connectTimeOut, Map<String, String> header){
    	 String content = "";
    	 URL url = null;
    	 HttpURLConnection httpURLConnection = null;
    	 BufferedInputStream bis = null;
    	 ByteArrayOutputStream bos = null;
    	 PrintWriter printWriter = null;
    	 InputStream is = null;
         try {
             url = new URL(postUrl);
             httpURLConnection = (HttpURLConnection) url.openConnection();
             
             httpURLConnection.setRequestMethod(REQUEST_POST);
             httpURLConnection.setDoOutput(true);
             httpURLConnection.setDoInput(true);
             httpURLConnection.setUseCaches(false);
             
             httpURLConnection.setConnectTimeout(connectTimeOut*1000);
             httpURLConnection.setReadTimeout(connectTimeOut*1000);
             printWriter = new PrintWriter(httpURLConnection.getOutputStream());
             printWriter.write(getParamsForHF(params));
             printWriter.flush();
             
             is = httpURLConnection.getInputStream();
             //开始获取数据
             bis = new BufferedInputStream(is);
             bos = new ByteArrayOutputStream();
             int len;
             byte[] arr = new byte[1024];
             while((len=bis.read(arr))!= -1){
                 bos.write(arr,0,len);
                 bos.flush();
             }
             
             content = bos.toString(ENC_UTF8);
         } catch (Exception e) {
        	//连接超时,读取数据超时异常,返回处理中.
 			log.error("请求恒丰异常：" ,e);
 		 } finally {
             try {
                 if ( is != null ) {
                     is.close();
                 }
                 if(bos != null) {
                     bos.close();
                 }
                 if(bis != null) {
                     bis.close();
                 }
             } catch (IOException e) {
                 e.printStackTrace();
             }
        	 if(printWriter != null) {
        		 printWriter.close();
        	 }
             if(httpURLConnection != null){
        	    httpURLConnection.disconnect();
             }
         }  
         return content;
    }
    
    /**
     * 以POST发送请求
     *
     * @param url         url
     * @param data        数据
     * @param encode      编码
     * @param contentType content类型
     * @return 返回响应数据
     * @throws IOException
     */
    private static String sendDataByPost(String url, String data, String encode, String contentType, int connectTimeOut) throws IOException {
        String content = null;
        HttpPost post = new HttpPost(url);
        try {
            StringEntity entity = new StringEntity(data, encode);
            entity.setContentType(contentType);
            entity.setContentEncoding(new BasicHeader(HTTP.CONTENT_TYPE, contentType));
            post.setEntity(entity);
            RequestConfig config = getRequestConfig(connectTimeOut);
            post.setConfig(config);
            HttpClient httpclient = HttpClients.createDefault();
            HttpResponse response = httpclient.execute(post);
            Integer statusCode = response.getStatusLine().getStatusCode();
            log.info("响应状态码 = " + statusCode);
            content = statusCode + "";
            if (statusCode == HttpStatus.SC_OK || statusCode == HttpStatus.SC_INTERNAL_SERVER_ERROR) {
                content += getResponseString(response, encode);
            }
        } finally {
            post.releaseConnection();
        }
        return content;
    }

    /**
     * 以PUT发送请求
     *
     * @param url         url
     * @param data        数据
     * @param encode      编码
     * @param contentType content类型
     * @return 返回响应数据
     * @throws IOException
     */
    private static String sendDataByPut(String url, String data, String encode, String contentType, int connectTimeOut) throws IOException {
        String content = null;
        HttpPut put = new HttpPut(url);
        try {
            StringEntity entity = new StringEntity(data, encode);
            entity.setContentType(contentType);
            entity.setContentEncoding(new BasicHeader(HTTP.CONTENT_TYPE, contentType));
            put.setEntity(entity);
            RequestConfig config = getRequestConfig(connectTimeOut);
            put.setConfig(config);
            HttpClient httpclient = HttpClients.createDefault();
            HttpResponse response = httpclient.execute(put);
            Integer statusCode = response.getStatusLine().getStatusCode();
            log.info("响应状态码 = " + statusCode);
            content = statusCode + "";
            if (statusCode == HttpStatus.SC_OK || statusCode == HttpStatus.SC_INTERNAL_SERVER_ERROR) {
                content += getResponseString(response, encode);
            }
        } finally {
            put.releaseConnection();
        }
        return content;
    }
    
    
    
    /**
     * 以PUT发送请求
     * Header 带token
     * @param url         url
     * @param data        数据
     * @param encode      编码
     * @param contentType content类型
     * @return 返回响应数据
     * @throws IOException
     */
    private static String sendDataByPut(String url, String data, String encode, String contentType, int connectTimeOut,String token) throws IOException {
        String content = null;
        HttpPut put = new HttpPut(url);
        try {
            StringEntity entity = new StringEntity(data, encode);
            entity.setContentType(contentType);
            entity.setContentEncoding(new BasicHeader(HTTP.CONTENT_TYPE, contentType));
            put.setEntity(entity);
            RequestConfig config = getRequestConfig(connectTimeOut);
            put.setConfig(config);
            log.info("Authorization = " + token);
           
            put.addHeader("Authorization",token);
            HttpClient httpclient = HttpClients.createDefault();
            HttpResponse response = httpclient.execute(put);
            Integer statusCode = response.getStatusLine().getStatusCode();
            log.info("响应状态码 = " + statusCode);
            content = statusCode + "";
            if (statusCode == HttpStatus.SC_OK || statusCode == HttpStatus.SC_INTERNAL_SERVER_ERROR) {
                content += getResponseString(response, encode);
            }
        } finally {
            put.releaseConnection();
        }
        return content;
    }

    /**
     * DETETE请求
     * @param url
     * @param params
     * @param connectTimeOut
     * @param header
     * @return
     */
    public static String sendDataByDelete(String url, Map<String, String> params, int connectTimeOut, Map<String, String> header) {
        String body = null;
        HttpDelete delete = new HttpDelete(url);
        delete.setConfig(getRequestConfig(connectTimeOut));
        if(header != null && !header.isEmpty()){
            for (String key : header.keySet()) {
                delete.setHeader(key, header.get(key));
            }
        }
        try {
            // delete请求
            delete.setURI(new URI(delete.getURI().toString() + "?" + getParams(params)));
            // 发送请求
            HttpResponse httpresponse = HttpClients.createDefault().execute(delete);
            // 获取返回数据
            Integer statusCode = httpresponse.getStatusLine().getStatusCode();
            log.info("响应状态码 = " + statusCode);
            body = statusCode + "";
            if (statusCode == HttpStatus.SC_OK || statusCode == HttpStatus.SC_INTERNAL_SERVER_ERROR) {
                body += getResponseString(httpresponse, ENC_UTF8);
            }
        } catch (Exception e) {
            log.error("httpclient调用异常，信息：{}", e);
        } finally {
            delete.releaseConnection();
        }
        return body;
    }

    /**
     * get请求
     * @param url
     * @param params
     * @param connectTimeOut
     * @param header
     * @return
     */
    public static String sendDataByGet(String url, Map<String, String> params, int connectTimeOut, Map<String, String> header) {
        String body = null;
        HttpGet httpget = new HttpGet(url);
        httpget.setConfig(getRequestConfig(connectTimeOut));
        if(header != null && !header.isEmpty()){
            for (String key : header.keySet()) {
                httpget.setHeader(key, header.get(key));
            }
        }
        try {
            // Get请求
        	String urlString = httpget.getURI().toString() + "?" + getParams(params);
        	log.info("请求URL = " + urlString);
            httpget.setURI(new URI(urlString));
            // 发送请求
            HttpResponse httpresponse = HttpClients.createDefault().execute(httpget);
            // 获取返回数据

            Integer statusCode = httpresponse.getStatusLine().getStatusCode();
            log.info("响应状态码 = " + statusCode);
            body = statusCode + "";
            if (statusCode == HttpStatus.SC_OK || statusCode == HttpStatus.SC_INTERNAL_SERVER_ERROR) {
                body += getResponseString(httpresponse, ENC_UTF8);
            }
        } catch (Exception e) {
            log.error("httpclient调用异常，信息：{}", e);
        } finally {
            httpget.releaseConnection();
        }
        return body;
    }

    /**
     * get请求，返回HttpResponse对象，调用方判断statusCode
     * @param url
     * @param params
     * @param connectTimeOut
     * @param header
     * @return
     */
    public static HttpResponse sendDataByGetRes(String url, Map<String, String> params, int connectTimeOut, Map<String, String> header) {
        String body = null;
        HttpGet httpget = new HttpGet(url);
        httpget.setConfig(getRequestConfig(connectTimeOut));
        if(header != null && !header.isEmpty()){
            for (String key : header.keySet()) {
                httpget.setHeader(key, header.get(key));
            }
        }
        HttpResponse httpRespons = null;
        try {
            // Get请求
            httpget.setURI(new URI(httpget.getURI().toString() + "?" + getParams(params)));
            System.err.println("请求参数：{}" + httpget.getURI());
            // 发送请求
            httpRespons = HttpClients.createDefault().execute(httpget);
        } catch (Exception e) {
            log.error("httpclient调用异常，信息：{}", e);
        }
        return httpRespons;
    }
    
    /**
     * 返回参数格式 汇丰报文参数(为null或者为空不上送)
     * @param params
     * @return
     */
    public static String getParamsForHF(Map<String, String> params){
        String result = "";
        if (params != null) {
            StringBuffer strb = new StringBuffer();
            for (String key : params.keySet()) {
            	String temp = "";
            	
            	Object object = params.get(key);
            	if (object instanceof Integer ) {
            		temp =   String.valueOf(object);
				}else if (object instanceof Double ) {
					temp = new DecimalFormat("0.00").format((object));
				}else if (object instanceof Date) {
                    temp = DateUtil.format((Date) object);
                    log.info("返回参数模式（DATE）："+temp);
                    try {
                        temp = URLEncoder.encode(temp, ENC_UTF8);
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                } else if (object instanceof ArrayList ) {
					temp = JSONArray.fromObject(object).toString();
					
					try {
						temp = URLEncoder.encode(temp, ENC_UTF8);
					} catch (UnsupportedEncodingException e) {
						e.printStackTrace();
					}
				} else if (object instanceof LinkedHashMap) {
					Gson gson = new Gson();
					temp =gson.toJson(object);
				} else {
					
					temp = params.get(key);
				}
            	/*String encodeStr ="";
            	try {
            		encodeStr = URLEncoder.encode(temp, "UTF-8");
          		} catch (UnsupportedEncodingException e) {
          			e.printStackTrace();
          		}*/
            	if( temp != null && !"".equals(temp)) {
            		strb.append(key).append("=").append(temp).append("&");
            	}
            }
            result = strb.substring(0, strb.length() - 1);
        }
        return result;
    }
    
    /**
     * 返回参数格式
     * @param params
     * @return
     */
    public static String getParams(Map<String, String> params){
        String result = "";
        if (params != null) {
            StringBuffer strb = new StringBuffer();
            for (String key : params.keySet()) {
            	String temp = "";
            	Object object = params.get(key);
            	if (object instanceof Integer ) {
            		temp =   String.valueOf(object);
	        		try {
	                     temp = URLEncoder.encode(temp, "UTF-8");
	                } catch (UnsupportedEncodingException e) {
	                     // TODO Auto-generated catch block
	                     e.printStackTrace();
	                }
				}else if (object instanceof Double ) {
            		temp =   String.valueOf(object);
				}else if (object instanceof Date) {
					
                    temp = DateUtil.format((Date) object);
                    log.info("返回参数模式（DATE）："+temp);
                    try {
                        temp = URLEncoder.encode(temp, "UTF-8");
                    } catch (UnsupportedEncodingException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }else if (object instanceof ArrayList ) {
					temp = JSONArray.fromObject(object).toString();
					try {
						temp = URLEncoder.encode(temp, "UTF-8");
					} catch (UnsupportedEncodingException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}else {
					temp = params.get(key);
				}
            	/*String encodeStr ="";
            	try {
            		encodeStr = URLEncoder.encode(temp, "UTF-8");
          		} catch (UnsupportedEncodingException e) {
          			e.printStackTrace();
          		}*/
                strb.append(key).append("=").append(temp).append("&");
            }
            result = strb.substring(0, strb.length() - 1);
        }
        return result;
    }

    /**
     * 获取RequestConfig的配置
     *
     * @return RequestConfig
     */
    private static RequestConfig getRequestConfig(int connectTimeOut) {
        return RequestConfig.custom()
                .setSocketTimeout(connectTimeOut * 1000)
                .setConnectTimeout(connectTimeOut * 1000)
                .build();
    }

    /**
     * 获取响应信息
     */
    public static String getResponseString(HttpResponse response, String encoding) throws IOException {
        String content = null;
        BufferedReader in = new BufferedReader(new InputStreamReader(response.getEntity().getContent(), encoding));
        StringBuffer sb = new StringBuffer("");
        String line = "";
        while ((line = in.readLine()) != null) {
            sb.append(line);
        }
        in.close();
        content = sb.toString();
        return content;
    }

    public static String getHostAddress() {
        String localIp = null;
        try {
            localIp = InetAddress.getLocalHost().getHostAddress();
        } catch (Exception e) {
            return null;
        }
        return localIp;
    }
}
