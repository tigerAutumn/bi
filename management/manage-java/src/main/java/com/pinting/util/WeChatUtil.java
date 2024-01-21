package com.pinting.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class WeChatUtil {
	public static final String ENCODE      = "UTF-8";
	/**
     * 发送 HTTP GET 请求
     * @param url
     *          请求的 URL 地址
     * @param param
     *          请求的参数内容
     * @param isForm
     *          是否是form请求
     * @param encode
     *          编码方式，null为默认编码
     * @return
     * @throws IOException 
     */
    public static String sendGet(String url, String param, boolean isForm, String encode)
                                                                                         throws IOException {
        String result = "";

        // 打开连接，并设置参数
        URL httpUrl = new URL(url);
        HttpURLConnection conn = (HttpURLConnection) httpUrl.openConnection();
        conn.setDoInput(true); // 设置可输入
        conn.setDoOutput(true);// 设置可输出
        conn.setRequestMethod("GET");//设置请求方式为GET
        if (isForm)
            conn.setRequestProperty("Content-type", "application/x-www-form-urlencoded");
        conn.connect();

        // 输出
        write(conn.getOutputStream(), param, encode);
        conn.getOutputStream().flush();
        conn.getOutputStream().close();

        //输入
        result = read(conn.getInputStream(), 1024, ENCODE);
        conn.getInputStream().close();

        // 关闭连接
        conn.disconnect();

        return result;
    }

    /**
     * 发送 HTTP POST 请求
     * @param url
     *          请求的 URL 地址
     * @param param
     *          请求的参数内容
     * @param isForm
     *          是否是form请求
     * @param encode
     *          编码方式，null为默认编码
     * @return
     * @throws IOException 
     */
    public static String sendPost(String url, String param, boolean isForm, String encode)
                                                                                          throws IOException {
        String result = "";

        // 打开连接，并设置参数
        URL httpUrl = new URL(url);
        HttpURLConnection conn = (HttpURLConnection) httpUrl.openConnection();
        conn.setDoInput(true); // 设置可输入
        conn.setDoOutput(true);// 设置可输出
        conn.setRequestMethod("POST");//设置请求方式为POST
        if (isForm)
            conn.setRequestProperty("Content-type", "application/x-www-form-urlencoded");
        conn.connect();

        // 输出
        write(conn.getOutputStream(), param, encode);
        conn.getOutputStream().flush();
        conn.getOutputStream().close();

        //输入
        result = read(conn.getInputStream(), 1024, ENCODE);
        conn.getInputStream().close();

        // 关闭连接
        conn.disconnect();

        return result;
    }

    /**
     * 指定输入流和编码方式输入指定信息
     * @param out
     * @param msg
     * @param encode
     * @throws IOException 
     */
    public static void write(OutputStream out, String msg, String encode) throws IOException {
        byte[] b = msg.getBytes(encode);
        out.write(b);
        out.flush();
    }

    /**
     * 指定缓存大小，编码方式读取输入流
     * @param in
     *          输入流
     * @param length
     *          缓存大小
     * @param encode
     *          编码方式
     * @return
     * @throws IOException
     */
    public static String read(InputStream in, int length, String encode) throws IOException {
        StringBuilder result = new StringBuilder();

        byte[] b = new byte[length];
        int len = -1;
        while (-1 != (len = in.read(b)))
            result.append(new String(b, 0, len, encode));

        return result.toString();
    }
}
