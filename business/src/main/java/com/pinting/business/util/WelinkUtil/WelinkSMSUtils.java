package com.pinting.business.util.WelinkUtil;
/**
 * Wenzi.com Inc.
 * Copyright (c) 2015-2025 All Rights Reserved.
 */


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pinting.core.util.StringUtil;


/**
 * 百分短信:账号:dlbgw888密码:CS98qVZK   产品编码：1012812  企业代码：空网页版的登录：web.51welink.com
 * 
 * 微网短信
 * 账号：dlbgw888
 * 密码：CS98qVZK 
 * 产品编码：1012812    
 * 企业代码：空  
 *  @2016-4-6 上午10:38:11
 */
public class WelinkSMSUtils {
    private static final Logger LOG     = LoggerFactory.getLogger(WelinkSMSUtils.class);
    private static String       URL     = "http://cf.51welink.com/submitdata/service.asmx/";
    private static String       SNAME   = "dlbgw888";                                       //测试账号："dlbgw888"
    private static String       SPWD    = "CS98qVZK ";                                       //测试密码："CS98qVZK"
    private static String       SCORPID = "";
    private static String       SPRDID  = "1012812";                                        //测试产品号："1012812"

    /**
     * 普通短信发送
     * 
     * @param mobile 手机号，多个手机号可以用","分隔
     * @param message 短信内容
     * @return 成功返回true，否则返回false
     */
    public static boolean generalSend(String mobile, String message) {
        URL = "http://cf.51welink.com/submitdata/service.asmx/";
        SNAME = "dlbgw888";
        SPWD = "CS98qVZK";
        SCORPID = "";
        SPRDID = "1012812";
        String postData = getPostData(mobile, message);
        String postUrl = getPostUrl(PostUrlEnums.SMS_G_SUBMIT);
        String res = SMS(postData, postUrl);
        LOG.info("【文字短信发送】,目标:" + mobile + ",内容:" + message);
        return parseXMLAndCheck(res);
    }

    /**
     * 语音短信发送
     * 
     * @param mobile 手机号，多个手机号可以用","分隔
     * @param message 短信内容
     * @return 成功返回true，否则返回false
     */
    public static boolean generalSendSound(String mobile, String message) {
        URL = "http://cf.51welink.com/submitdata/service.asmx/";
        SNAME = "dlwenzi8";
        SPWD = "lfDwp36M";
        SCORPID = "";
        SPRDID = "1012818";
        String postData = getPostData(mobile, message);
        String postUrl = getPostUrl(PostUrlEnums.SMS_G_SUBMIT);
        String res = SMS(postData, postUrl);
        LOG.info("【语音短信发送】,目标:" + mobile + ",内容:" + message);
        return parseXMLAndCheck(res);
    }

    /**
     * 发送
     * 
     * @param postData
     * @param postUrl
     * @return
     */
    private static String SMS(String postData, String postUrl) {
        try {
            //发送POST请求
            URL url = new URL(postUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            conn.setRequestProperty("Connection", "Keep-Alive");
            conn.setUseCaches(false);
            conn.setDoOutput(true);

            conn.setRequestProperty("Content-Length", "" + postData.length());
            OutputStreamWriter out = new OutputStreamWriter(conn.getOutputStream(), "UTF-8");
            out.write(postData);
            out.flush();
            out.close();

            //获取响应状态
            if (conn.getResponseCode() != HttpURLConnection.HTTP_OK) {
                LOG.error("connect failed!");
                return "";
            }
            //获取响应内容体
            String line, result = "";
            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream(),
                "utf-8"));
            while ((line = in.readLine()) != null) {
                result += line + "\n";
            }
            in.close();
            return result;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 获取短信服务端url
     * 
     * @param postUrlEnums
     * @return
     */
    private static String getPostUrl(PostUrlEnums postUrlEnums) {
        StringBuffer buffer = new StringBuffer();
        buffer.append(URL).append(postUrlEnums.getCode());
        return buffer.toString();
    }

    /**
     * 获取短信发送参数
     * 
     * @param mobile
     * @param message
     * @return
     */
    private static String getPostData(String mobile, String message) {
        StringBuffer buffer = new StringBuffer();
        try {
            buffer.append("sname=").append(SNAME).append("&spwd=").append(SPWD).append("&scorpid=")
                .append(SCORPID).append("&sprdid=").append(SPRDID).append("&sdst=").append(mobile)
                .append("&smsg=").append(URLEncoder.encode(message, "UTF-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return buffer.toString();
    }

    /**
     * 解析短信服务器响应报文，并校验是否发送成功
     * 
     * @param xml
     * @return
     */
    public static Boolean parseXMLAndCheck(String xml) {
        boolean isSuccess = false;
        if (!StringUtil.isEmpty(xml)) {
            try {
                Document document = DocumentHelper.parseText(xml);
                Element rootElm = document.getRootElement();
                Element memberElm = rootElm.element("State");
                String state = memberElm.getText();
                if ("0".equals(state)) {
                    isSuccess = true;
                    LOG.info("SMS send success");
                } else {
                    LOG.error("SMS send error : " + rootElm.element("MsgState").getText());
                }
            } catch (DocumentException e) {
                e.printStackTrace();
            }
        }
        return isSuccess;
    }

    public static void main(String[] args) {
        System.out.println(generalSend("15990147161,15868157902", "【币港湾】	周年庆狂欢专场！新手加息3%，老用户加息1.2%！50万抢完即止，购买点击http://t.cn/RGD56Y6 。退订回T"));
    }

}
