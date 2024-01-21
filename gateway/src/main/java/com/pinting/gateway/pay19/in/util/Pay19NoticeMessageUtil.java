/**
 * Wenzi.com Inc.
 * Copyright (c) 2015-2025 All Rights Reserved.
 */
package com.pinting.gateway.pay19.in.util;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Method;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pinting.core.util.StringUtil;
import com.pinting.gateway.pay19.out.util.Pay19HttpUtil;
import com.pinting.gateway.util.Pay19KeyedDigestMD5;

/**
 * 
 * @author Baby shark love blowing wind
 * @version $Id: Pay19MessageUtil.java, v 0.1 2015-8-6 上午10:56:15 BabyShark Exp $
 */
public class Pay19NoticeMessageUtil {
    private static Logger log = LoggerFactory.getLogger(Pay19NoticeMessageUtil.class);

    /**
     * 对象转换成Map
     * 
     * @param obj
     * @return
     */
    public static HashMap<String, String> transBeanMap(Object obj) {
        HashMap<String, String> map = new HashMap<String, String>();
        if (obj == null) {
            return map;
        }
        try {
            BeanInfo beanInfo = Introspector.getBeanInfo(obj.getClass());
            PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
            for (PropertyDescriptor property : propertyDescriptors) {
                String key = property.getName();
                // 过滤class属性  
                if (!key.equals("class")) {
                    // 得到property对应的getter方法  
                    Method getter = property.getReadMethod();
                    Object value = getter.invoke(obj);
                    map.put(key, value != null ? URLDecoder.decode(value.toString(), "UTF-8") : "");
                }
            }
        } catch (Exception e) {
            log.info("transBean2Map Error " + e);
        }
        return map;

    }
    
    /**
     * 对象转换成Map(过滤属性值为空的属性)
     * 
     * @param obj
     * @return
     */
    public static HashMap<String, String> transBeanMapRide(Object obj) {
        HashMap<String, String> map = new HashMap<String, String>();
        if (obj == null) {
            return map;
        }
        try {
            BeanInfo beanInfo = Introspector.getBeanInfo(obj.getClass());
            PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
            for (PropertyDescriptor property : propertyDescriptors) {
                String key = property.getName();
                // 过滤class属性  
                if (!key.equals("class")) {
                    // 得到property对应的getter方法  
                    Method getter = property.getReadMethod();
                    Object value = getter.invoke(obj);
                    if(value != null && isNotBlank(value.toString())) {
                    	map.put(key, URLDecoder.decode(value.toString(), "UTF-8"));
                    }
                }
            }
        } catch (Exception e) {
            log.info("transBean2Map Error " + e);
        }
        return map;

    }

    /**
     * 验签
     * @param paramsMap
     * @return
     */
    public static boolean checkVerifyString(Map<String, String> paramsMap) {
        log.info("19付通知请求数据：" + paramsMap);
        String pay19Verifystring = paramsMap.remove("verifystring");
        if (StringUtil.isEmpty(pay19Verifystring)) {
            pay19Verifystring = paramsMap.remove("verifyString");
        }
        if (StringUtil.isEmpty(pay19Verifystring)) {
            pay19Verifystring = paramsMap.remove("hmac");
        }
        if (StringUtil.isEmpty(pay19Verifystring)) {
            return false;
        }
        String verifystring = buildVerifyString(paramsMap);
        if (pay19Verifystring.equals(verifystring)) {
            return true;
        }
        return false;
    }

    /**
     * 消息摘要明文组装及加密
     * @param req
     * @param reqMap 
     * @return
     */
    public static String buildVerifyString(Map<String, String> paramsMap) {
        StringBuffer text = new StringBuffer();
        if (paramsMap.containsKey("sysOrderId") && StringUtil.isEmpty("sysOrderId")) {//代付特殊处理
            text.append("mxOrderId=").append(paramsMap.get("mxOrderId")).append("&retCode=")
                .append(paramsMap.get("retCode")).append("&");
        } else {
            Set<String> keySet = paramsMap.keySet();
            Object[] keyArr = keySet.toArray();
            Arrays.sort(keyArr);//对key进行ASCII排序
            for (Object key : keyArr) {
                try {
                    text.append(key).append("=")
                        .append(URLEncoder.encode(paramsMap.get(key), "UTF-8")).append("&");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            }
        }

        String hash = StringUtil.substring(text.toString(), 0, text.length() - 1);
        log.info("19Pay消息摘要明文：【" + hash + "】");
        String verifyString = StringUtil.lowerCase(Pay19KeyedDigestMD5.getKeyedDigest(hash,
            Pay19HttpUtil.merchant_key));
        log.info("19Pay消息摘要密文：【" + verifyString + "】");
        return verifyString;
    }
    
    /**
     * 对字符串进行MD5加密
     * @param req
     * @param reqMap 
     * @return
     */
    public static String buildVerifyString(String str) {
        log.info("19Pay消息摘要明文：【" + str + "】");
        String verifyString = StringUtil.lowerCase(Pay19KeyedDigestMD5.getKeyedDigest(str,
            Pay19HttpUtil.merchant_key));
        log.info("19Pay消息摘要密文：【" + verifyString + "】");
        return verifyString;
    }
    
    /**
     * 
     * @Title: isNotBlank
     * @Description: 验证字符串是否为空
     * @param @param str
     * @param @return
     * @return boolean
     * @throws
     */
    public static boolean isNotBlank(String str) {
        int length;

        if ((str == null) || ((length = str.length()) == 0) || "null".equals(str)) {
            return false;
        }

        for (int i = 0; i < length; i++) {
            if (!Character.isWhitespace(str.charAt(i))) {
                return true;
            }
        }

        return false;
    }

}
