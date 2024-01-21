package com.pinting.gateway.util;

import com.pinting.core.util.StringUtil;
import com.pinting.gateway.dafy.out.util.DafyOutConstant;
import org.apache.http.*;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.HttpClientUtils;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class HttpClientUtil {

    private static Logger log = LoggerFactory.getLogger(HttpClientUtil.class);

    private static HttpClient hc;
    static {
        hc = HttpClientBuilder.create().setSSLSocketFactory(getEnableSSLSocketFactory()).build();
    }

    // 重置连接
    public static void resetConnection() {
        if (hc != null) {
            HttpClientUtils.closeQuietly(hc);
        }
        hc = HttpClientBuilder.create().setSSLSocketFactory(getEnableSSLSocketFactory()).build();
    }

    // 返回字符串数据
    public static String sendRequest(String url, Map<String, String> params) {
    	log.info("发起通讯，地址：" + url);
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
        	log.info("发起通讯，参数：" + params.toString());
            Iterator<String> it = params.keySet().iterator();
            while (it.hasNext()) {
                String key = it.next();
                String value = params.get(key);
                // log.info("key:"+key+" value:"+value);
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
                log.info("响应返回码：" + status.getStatusCode());
                //判断是否需要继续跳转
                while (status.getStatusCode() >= HttpStatus.SC_MULTIPLE_CHOICES
                       && status.getStatusCode() < HttpStatus.SC_BAD_REQUEST) {

                    Header locationHeader = response.getFirstHeader("Location");
                    if (locationHeader != null) {
                    	log.info("重定向跳转url：" + locationHeader.getValue());
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
                	log.info("连接对方地址成功，开始接收返回数据。。。");
                    // 连接成功,获得数据
                    ety = response.getEntity();
                    if (ety != null) {
                        br = new BufferedReader(new InputStreamReader(ety.getContent()));
                        String line = null;
                        while ((line = br.readLine()) != null) {
                        	if(params != null && !DafyOutConstant.GET_LOAN_RELATION_NEW.equals(params.get("transCode"))) {
                        		log.info("返回一行= " + StringUtil.left(line, 200) + "...");
                        	}
                            sb.append(line);
                        }
                    }
                    // log.info(sb.toString());
                } else{
                	log.info("响应异常。。。");
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
                    	log.info("重定向跳转url：" + locationHeader.getValue());
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
                	log.info("连接对方地址成功，开始接收返回数据。。。");
                    // 连接成功,获得数据
                    ety = response.getEntity();
                    if (ety != null) {
                        br = new BufferedReader(new InputStreamReader(ety.getContent()));
                        String line = null;
                        while ((line = br.readLine()) != null) {
                        	if(!DafyOutConstant.GET_LOAN_RELATION_NEW.equals(params.get("transCode"))){
                        		log.info("返回一行= " + line);
                        	}
                            sb.append(line);
                        }
                    }
                    // log.info(sb.toString());
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
    // /**
    // * 下载文件
    // * @param url 文件url
    // * @param destPath 存储路径
    // * @param destName 存储文件名
    // */
    // public static void download(String url, String destPath, String
    // destName){
    //
    // HttpClient client = QKApplication.getHttpClient();
    // HttpGet get = new HttpGet(url);
    // HttpResponse response;
    //
    // InputStream is = null;
    // FileOutputStream fos = null;
    // try {
    // response = client.execute(get);
    // HttpEntity entity = response.getEntity();
    // is = entity.getContent();
    // fos = null;
    // if (is != null) {
    // new File(destPath).mkdirs();
    // File file = new File(destPath + destName);
    // fos = new FileOutputStream(file);
    //
    // byte[] buf = new byte[1024];
    // int count = -1;
    // while ((count = is.read(buf)) != -1) {
    // fos.write(buf, 0, count);
    // fos.flush();
    // }
    // }
    // } catch (ClientProtocolException e) {
    // e.printStackTrace();
    // } catch (IOException e) {
    // e.printStackTrace();
    // } finally{
    // if(fos != null){
    // try {
    // fos.close();
    // } catch (IOException e) {
    // e.printStackTrace();
    // }
    // }
    // try {
    // client.getConnectionManager().shutdown();
    // } catch (Exception e) {
    // e.printStackTrace();
    // }
    // }
    //
    // }

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

    /**
     * 实现JDK1.7支持TLSv1.2
     * @return
     */
    public static SSLSocketFactory getEnableSSLSocketFactory(){
        SSLSocketFactory ssf = null;
        try{
            SSLContext ctx = SSLContext.getInstance("TLSv1.2");
            X509TrustManager tm = new X509TrustManager() {
                @Override
                public void checkClientTrusted(X509Certificate[] chain,
                                               String authType) throws CertificateException {
                }
                @Override
                public void checkServerTrusted(X509Certificate[] chain,
                                               String authType) throws CertificateException {
                }
                @Override
                public X509Certificate[] getAcceptedIssuers() {
                    return null;
                }
            };
            ctx.init(null, new TrustManager[]{tm}, null);
            ssf = new SSLSocketFactory(
                    ctx,
                    new String[]{"TLSv1.1", "TLSv1.2"},
                    null,
                    SSLSocketFactory.BROWSER_COMPATIBLE_HOSTNAME_VERIFIER);
        }catch(Exception e) {
            e.printStackTrace();
        }
        return  ssf;
    }

}
