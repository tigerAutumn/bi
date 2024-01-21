/**
 * 
 * HttpRequest
 * @author yanwl
 * @version 1.0
 * @data 2015-11-19
 */
package com.dafy.core.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.URL;
import java.net.URLConnection;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.util.List;
import java.util.Map;

public class HttpRequest {
    /**
     * 向指定URL发送GET方法的请求
     * 
     * @param url
     *            发送请求的URL
     * @param param
     *            请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
     * @return URL 所代表远程资源的响应结果
     */
    public static String sendGet(String url, String param) {
        String result = "";
        BufferedReader in = null;
        try {
            String urlNameString = url + "?" + param;
            URL realUrl = new URL(urlNameString);
            // 打开和URL之间的连接
            URLConnection connection = realUrl.openConnection();
            // 设置通用的请求属性
            connection.setRequestProperty("accept", "*/*");
            connection.setRequestProperty("connection", "Keep-Alive");
            connection.setRequestProperty("user-agent",
                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            // 建立实际的连接
            connection.connect();
            
            //连接超时时间设置
            connection.setConnectTimeout(10000);
            
            // 获取所有响应头字段
            Map<String, List<String>> map = connection.getHeaderFields();
            // 遍历所有的响应头字段
            for (String key : map.keySet()) {
                System.out.println(key + "--->" + map.get(key));
            }
            // 定义 BufferedReader输入流来读取URL的响应
            in = new BufferedReader(new InputStreamReader(
                    connection.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            System.out.println("发送GET请求出现异常！" + e);
            e.printStackTrace();
        }
        // 使用finally块来关闭输入流
        finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        return result;
    }

    /**
     * 向指定 URL 发送POST方法的请求
     * 
     * @param url
     *            发送请求的 URL
     * @param param
     *            请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
     * @return 所代表远程资源的响应结果
     */
    public static String sendPost(String url, String param) {
        PrintWriter out = null;
        BufferedReader in = null;
        String result = "";
        try {
            URL realUrl = new URL(url);
            // 打开和URL之间的连接
            URLConnection conn = realUrl.openConnection();
            // 设置通用的请求属性
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestProperty("user-agent",
                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            // 发送POST请求必须设置如下两行
            conn.setDoOutput(true);
            conn.setDoInput(true);
            
            //连接超时时间设置
            conn.setConnectTimeout(10000);
            
            // 获取URLConnection对象对应的输出流
            out = new PrintWriter(conn.getOutputStream());
            // 发送请求参数
            out.print(param);
            // flush输出流的缓冲
            out.flush();
            // 定义BufferedReader输入流来读取URL的响应
            in = new BufferedReader(
                    new InputStreamReader(conn.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            System.out.println("发送 POST 请求出现异常！"+e);
            e.printStackTrace();
        }
        //使用finally块来关闭输出流、输入流
        finally{
            try{
                if(out!=null){
                    out.close();
                }
                if(in!=null){
                    in.close();
                }
            }
            catch(IOException ex){
                ex.printStackTrace();
            }
        }
        return result;
    }    
    

    private static byte[] request = null;

    public static String sendHttpRequest(String url,String localhost,int port) throws Exception {
    	String result = "";
        try {
        	
        	StringBuffer temp = new StringBuffer();
            temp.append("GET ");
            temp.append(url);
            temp.append(" HTTP/1.1\r\n");
            temp.append("Host: ");
            temp.append(localhost+"\r\n");
            
            temp.append("Connection: keep-alive\r\n");
            temp.append("Cache-Control: max-age=0\r\n");
            temp
                    .append("User-Agent: Mozilla/5.0 (Windows NT 5.1) AppleWebKit/536.11 (KHTML, like Gecko) Chrome/20.0.1132.47 Safari/536.11\r\n");
            temp
                    .append("Accept: text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8\r\n");
            temp.append("Accept-Encoding: gzip,deflate,sdch\r\n");
            temp.append("Accept-Language: zh-CN,zh;q=0.8\r\n");
            temp.append("Accept-Charset: GBK,utf-8;q=0.7,*;q=0.3\r\n");
            temp.append("\r\n");
            request = temp.toString().getBytes();
            
//            final SocketChannel socketChannel = SocketChannel.open(new InetSocketAddress("172.16.3.145", port));
            final SocketChannel socketChannel = SocketChannel.open(new InetSocketAddress(localhost, port));
            final Charset charset = Charset.forName("UTF-8");// 创建GBK字符集
            socketChannel.configureBlocking(false);//配置通道使用非阻塞模式

            while (!socketChannel.finishConnect()) {
                Thread.sleep(10);
            }
            //proxySocketChannel.write(charset.encode("GET " + "http://localhost:8080/feifei/helloStruts-sayHello.action HTTP/1.1" + "\r\n\r\n"));
            socketChannel.write(ByteBuffer.wrap(request));

            int read = 0;
            boolean readed = false;
            ByteBuffer buffer = ByteBuffer.allocate(10240);// 创建10240字节的缓冲
            StringBuffer sb = new StringBuffer();
            while ((read = socketChannel.read(buffer)) != -1) {
                if (read == 0 && readed) {
                    break;
                } else if (read == 0) {
                    continue;
                }
                
                buffer.flip();// flip方法在读缓冲区字节操作之前调用。
                
                result = charset.decode(buffer).toString();
                
                sb.append(result);
                
                buffer.clear();// 清空缓冲
                readed = true;
            }
            
            
            result = sb.toString();
            if(result.indexOf("{") > -1){
            	result = result.substring(result.indexOf("{"), result.lastIndexOf("}")+1);
            }
            else {
            	System.out.println(result);
            	throw new Exception("not found json data");
            }
        } catch (Exception e) {
            e.printStackTrace();
            result = "{\"isReturn\":\"数据同步拒绝连接\"}";
        }

        return result;
    }

    public static void main(String[] args) throws Exception {
//        sendHttpRequest();
    }
    
    
   /* public static void main(String[] args) {
        //发送 GET 请求
//        String s=HttpRequest.sendGet("http://172.16.3.161:8080/default/com.primeton.zckt.zcktUtil.zcktRedo.biz.ext", "idn=admin@testZckt.com");
//        System.out.println(s);
        
        //发送 POST 请求key=123&v=456
        String sr=HttpRequest.sendPost("http://172.16.3.161:8080/default/com.primeton.zckt.zcktUtil.zcktRedo.biz.ext?&N%nsysadmin&M^m000000", "");
        System.out.println(sr);
    }*/
}
 
