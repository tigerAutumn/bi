package com.pinting.gateway.util;

import com.google.gson.Gson;
import com.pinting.core.util.DateUtil;
import net.sf.json.JSONArray;

import org.apache.http.*;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.HttpClientUtils;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class HttpClientUtil4HF {

    private static Logger logger = LoggerFactory.getLogger(HttpClientUtil4HF.class);

    private static HttpClient hc;
    static {
        hc = HttpClientBuilder.create().build();
    }

    // 重置连接
    public static void resetConnection() {
        if (hc != null) {
            HttpClientUtils.closeQuietly(hc);
        }
        hc = HttpClientBuilder.create().build();
    }
    
    // 返回字符串数据
    public static String sendRequest(String url, Map<String, String> params) {

        // 访问一个链接，提交数据，获得返回的结果
        // post方式
        HttpPost postMethod = new HttpPost(url);

        // 设置超时时间
        RequestConfig.Builder requestBuilder = RequestConfig.custom();
        requestBuilder.setConnectionRequestTimeout(120 * 1000);
        requestBuilder.setConnectTimeout(120 * 1000);
        requestBuilder.setSocketTimeout(120 * 1000);
        postMethod.setConfig(requestBuilder.build());

        ArrayList<NameValuePair> sendParams = new ArrayList<NameValuePair>();
        if (params != null && !params.isEmpty()) {
            Iterator<String> it = params.keySet().iterator();
            while (it.hasNext()) {
                String key = it.next();
                String value = "";
                //参数预处理
                Object object = params.get(key);
            	if (object instanceof Integer ) {
            		value =   String.valueOf(object);
    			} else if ( object instanceof Double ) {
    				value = new DecimalFormat("0.00").format((object));
    			} else if ( object instanceof Date) {
    				value = DateUtil.format((Date) object);
                } else if ( object instanceof ArrayList ) {
                	value = JSONArray.fromObject(object).toString();
    			} else if ( object instanceof LinkedHashMap) {
    				Gson gson = new Gson();
    				value =gson.toJson(object);
    			} else {
    				value = params.get(key);
    			}
            	if( value != null && !"".equals(value)) {
            		NameValuePair param = new BasicNameValuePair(key, value);
            		sendParams.add(param);
            	}
            }
        }
        
        // 得到一个设置了参数的一个Form entity
        UrlEncodedFormEntity entity;
        StringBuffer sb = new StringBuffer();
        
        // 尝试3次
        int times = 0;
        
        while (times < 1) {
            BufferedReader br = null;
            HttpEntity ety = null;
            try {
                entity = new UrlEncodedFormEntity(sendParams, "UTF-8");
                postMethod.setEntity(entity);
                HttpResponse response = hc.execute(postMethod);
                StatusLine status = response.getStatusLine();
                logger.info("响应返回码：" + status.getStatusCode());
                //判断是否需要继续跳转 300<= status <400
                while (status.getStatusCode() >= HttpStatus.SC_MULTIPLE_CHOICES
                       && status.getStatusCode() < HttpStatus.SC_BAD_REQUEST) {

                    Header locationHeader = response.getFirstHeader("Location");
                    if (locationHeader != null) {
                        logger.info("重定向跳转url：" + locationHeader.getValue());
                        postMethod = new HttpPost(locationHeader.getValue());
                        // 设置超时时间
                        postMethod.setConfig(requestBuilder.build());
                        response = hc.execute(postMethod);
                        status = response.getStatusLine();
                        postMethod.releaseConnection();//关闭请求
                    } else {
                        break;
                    }
                }

                if (status.getStatusCode() == HttpStatus.SC_OK) {
                    // 连接成功,获得数据
                    ety = response.getEntity();
                    if (ety != null) {
                        br = new BufferedReader(new InputStreamReader(ety.getContent()));
                        String line = null;
                        while ((line = br.readLine()) != null) {
                            sb.append(line);
                        }
                    }
                } else{
                	System.out.println("响应异常。。。");
                	times++;
                	postMethod.abort();
                }
                break;
            } catch (Exception e) {
                e.printStackTrace();
                times++;
                if (times == 2) {
                    throw new RuntimeException("网络连接错误");
                }
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e1) {
                    e1.printStackTrace();
                }
            } finally {
                if (br != null) {
                    try {
                        br.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                if (ety != null) {
                    EntityUtils.consumeQuietly(ety);//关闭实体
                }
            }
        }

        return sb.toString();
    }

    /**
     * 
     * @Title: sendFormRequest
     * @Description: 发生请求，已表单的形式提交
     * @param @param url
     * @param @param params
     * @param @return
     * @return String
     * @throws
     */
    public static String sendFormRequest(String url, Map<String, String> params) {
        List<String> keys = new ArrayList<String>(params.keySet());
        StringBuffer sbHtml = new StringBuffer();
        sbHtml.append("<form id=\"19pay\" name=\"19pay\" action=\"" + url + "\" method=\"post\">");

        for (int i = 0; i < keys.size(); i++) {
            String name = (String) keys.get(i);
            String value = (String) params.get(name);

            sbHtml.append("<input type=\"hidden\" name=\"" + name + "\" value=\"" + value + "\"/>");
        }

        //submit按钮控件请不要含有name属性
        sbHtml.append("<input type=\"submit\" value=\"submit\" style=\"display:none;\"></form>");
        sbHtml.append("<script>document.forms['19pay'].submit();</script>");

        return sbHtml.toString();
    }

}
