package com.pinting.util;

import com.pinting.core.util.StringUtil;
import org.apache.http.*;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
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
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;

public class HttpClientUtil {

    private static Logger log = LoggerFactory.getLogger(HttpClientUtil.class);

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
            System.out.println("发起通讯，参数：" + params.toString());
            Iterator<String> it = params.keySet().iterator();
            while (it.hasNext()) {
                String key = it.next();
                String value = params.get(key);
                // System.out.println("key:"+key+" value:"+value);
                NameValuePair param = new BasicNameValuePair(key, value);
                sendParams.add(param);
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
                System.out.println("响应返回码：" + status.getStatusCode());
                //判断是否需要继续跳转
                while (status.getStatusCode() >= HttpStatus.SC_MULTIPLE_CHOICES
                       && status.getStatusCode() < HttpStatus.SC_BAD_REQUEST) {

                    Header locationHeader = response.getFirstHeader("Location");
                    if (locationHeader != null) {
                        System.out.println("重定向跳转url：" + locationHeader.getValue());
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
                    System.out.println("连接对方地址成功，开始接收返回数据。。。");
                    // 连接成功,获得数据
                    ety = response.getEntity();
                    if (ety != null) {
                        br = new BufferedReader(new InputStreamReader(ety.getContent()));
                        String line = null;
                        while ((line = br.readLine()) != null) {
                            System.out.println("返回一行= " + StringUtil.left(line, 200) + "...");
                            sb.append(line);
                        }
                    }
                    // System.out.println(sb.toString());
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

    public static String sendRequestGet(String url, Map<String, String> params) {

        // 访问一个链接，提交数据，获得返回的结果
        Iterator<String> it = params.keySet().iterator();
        if(!params.isEmpty()){
            url += "?";
            while(it.hasNext()){
                String key = it.next();
                String value = params.get(key);
                url += key + "=" + value + "&";
            }
            url = url.substring(0, url.length() - 1);
        }

        // get方式
        HttpGet getMethod = new HttpGet(url);
        
        // 设置超时时间
        RequestConfig.Builder requestBuilder = RequestConfig.custom();
        requestBuilder.setConnectionRequestTimeout(120 * 1000);
        requestBuilder.setConnectTimeout(120 * 1000);
        requestBuilder.setSocketTimeout(120 * 1000);
        getMethod.setConfig(requestBuilder.build());

        StringBuffer sb = new StringBuffer();

        // 尝试3次
        int times = 0;

        while (times < 1) {
            BufferedReader br = null;
            HttpEntity ety = null;
            try {
                HttpResponse response = hc.execute(getMethod);
                StatusLine status = response.getStatusLine();

                //判断是否需要继续跳转
                while (status.getStatusCode() >= HttpStatus.SC_MULTIPLE_CHOICES
                       && status.getStatusCode() < HttpStatus.SC_BAD_REQUEST) {

                    Header locationHeader = response.getFirstHeader("Location");
                    if (locationHeader != null) {
                        System.out.println("重定向跳转url：" + locationHeader.getValue());
                        getMethod = new HttpGet(locationHeader.getValue());
                        // 设置超时时间
                        getMethod.setConfig(requestBuilder.build());
                        response = hc.execute(getMethod);
                        status = response.getStatusLine();
                        getMethod.releaseConnection();//关闭请求
                    } else {
                        break;
                    }
                }

                if (status.getStatusCode() == HttpStatus.SC_OK) {
                    System.out.println("连接对方地址成功，开始接收返回数据。。。");
                    // 连接成功,获得数据
                    ety = response.getEntity();
                    if (ety != null) {
                        br = new BufferedReader(new InputStreamReader(ety.getContent()));
                        String line = null;
                        while ((line = br.readLine()) != null) {
                            System.out.println("返回一行= " + line);
                            sb.append(line);
                        }
                    }
                    // System.out.println(sb.toString());
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


}
