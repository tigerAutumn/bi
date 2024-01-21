/**
 * Wenzi.com Inc.
 * Copyright (c) 2015-2025 All Rights Reserved.
 */
package com.pinting.gateway.pay19.out.util;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pinting.core.util.StringUtil;
import com.pinting.gateway.enums.PTMessageEnum;
import com.pinting.gateway.exception.PTMessageException;
import com.pinting.gateway.pay19.out.enums.Pay4AnotherUrl;
import com.pinting.gateway.pay19.out.model.resp.BaseResp;
import com.pinting.gateway.util.JsonLibUtil;
import com.pinting.gateway.util.Pay19KeyedDigestMD5;

/**
 * 
 * @author Baby shark love blowing wind
 * @version $Id: Pay19MessageUtil.java, v 0.1 2015-8-6 上午10:56:15 BabyShark Exp $
 */
public class Pay19MessageUtil {
    private static Logger       log            = LoggerFactory.getLogger(Pay19MessageUtil.class);
    private final static String respBeanPre    = "com.pinting.gateway.pay19.out.model.resp.";
    private final static String respBeanSuffix = "Resp";

    /**
     * 对象转换成Map
     * 
     * @param obj
     * @return
     */
    public static HashMap<String, String> transBeanMap(Object obj) {
        if (obj == null) {
            return null;
        }
        HashMap<String, String> map = new HashMap<String, String>();
        try {
            BeanInfo beanInfo = Introspector.getBeanInfo(obj.getClass());
            PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
            for (PropertyDescriptor property : propertyDescriptors) {
                String key = property.getName();
                // 过滤class属性  
                if (!key.equals("class") && !key.equals("verifystring")
                    && !key.equals("verifyString") && !key.equals("hmac")) {
                    // 得到property对应的getter方法  
                    Method getter = property.getReadMethod();
                    Object value = getter.invoke(obj);
                    map.put(key, value != null ? value.toString() : "");
                }
            }
        } catch (Exception e) {
            log.info("transBean2Map Error " + e);
        }
        return map;

    }

    /**
     * 解析响应报文，验签
     * @param respStr
     * @param code
     * @return 
     */
    @SuppressWarnings({ "unchecked", "rawtypes" })
    public static BaseResp parseResp(String respStr, String code) {
        BaseResp respBean = null;
        //解析报文转换成Map
        Map<String, String> paramsMap = new HashMap<String, String>();
        String[] respParams = respStr.split("&");
        for (String param : respParams) {
            String[] keyValue = param.split("=");
            paramsMap.put(keyValue[0], keyValue.length > 1 ? keyValue[1] : "");
        }
        //如果是代付，不校验消息摘要
        if (Pay4AnotherUrl.find(code) == null && !checkVerifyString(paramsMap)) {
            throw new PTMessageException(PTMessageEnum.RETURN_MSG_HASH_ERROR);
        }
        try {
            String beanName = StringUtil.upperCase(String.valueOf(code.charAt(0)))
                              + StringUtil.substring(code, 1, code.length() - 3);
            Class beanClass = Class.forName(respBeanPre + beanName + respBeanSuffix);
            //判断是否需要嵌套转换
            String listName = null;
            String listClassName = null;
            Field[] fields = beanClass.getDeclaredFields();
            for (Field field : fields) {
                String fieldClassName = field.getType().getName();
                if (List.class.getName().equals(fieldClassName)) {
                    listName = field.getName();//获取列表数据变量名
                    Type type = field.getGenericType();//获取List列表变量类型
                    if (type instanceof ParameterizedType) {
                        ParameterizedType pt = (ParameterizedType) type;
                        Class<?> c = (Class<?>) pt.getActualTypeArguments()[0];
                        listClassName = c.getName();
                    }
                    break;
                }
            }
            if (listName != null) {//如果有信息列表（嵌套转换）
                if (listClassName == null) {//如果为null，是model定义有问题，没有把泛型加入进来，需要定义泛型类型
                    throw new Exception("列表数据需定义泛型，请检查");
                }
                if (StringUtil.isEmpty(paramsMap.get(listName))) {
                    paramsMap.put(listName, "[]");
                }
                String jsonStr = JsonLibUtil.bean2Json(paramsMap);
                respBean = (BaseResp) JsonLibUtil.json2Bean(jsonStr, beanClass, listName,
                    Class.forName(listClassName));

            } else {
                String jsonStr = JsonLibUtil.bean2Json(paramsMap);
                respBean = (BaseResp) JsonLibUtil.json2Bean(jsonStr, beanClass);
            }

        } catch (Exception e) {
            log.error("parse response message error! cause by : ", e);
        }
        return respBean;
    }
    
    /**
     * 解析响应报文，验签(为空的属性不进行校验)
     * @param respStr
     * @param code
     * @return 
     */
    @SuppressWarnings({ "unchecked", "rawtypes" })
	public static BaseResp parseRespRide(String respStr, String code) {
        BaseResp respBean = null;
        //解析报文转换成Map
        Map<String, String> paramsMap = new HashMap<String, String>();
        String[] respParams = respStr.split("&");
        for (String param : respParams) {
            String[] keyValue = param.split("=");
            if(keyValue.length > 1 && isNotBlank(keyValue[1])) {
            	paramsMap.put(keyValue[0], keyValue[1]);
            }
        }
        //如果是代付，不校验消息摘要
        if (Pay4AnotherUrl.find(code) == null && !checkVerifyString(paramsMap)) {
        	throw new PTMessageException(PTMessageEnum.RETURN_MSG_HASH_ERROR);
        }
        try {
            String beanName = StringUtil.upperCase(String.valueOf(code.charAt(0)))
                              + StringUtil.substring(code, 1, code.length() - 3);
            Class beanClass = Class.forName(respBeanPre + beanName + respBeanSuffix);
            //判断是否需要嵌套转换
            String listName = null;
            String listClassName = null;
            Field[] fields = beanClass.getDeclaredFields();
            for (Field field : fields) {
                String fieldClassName = field.getType().getName();
                if (List.class.getName().equals(fieldClassName)) {
                    listName = field.getName();//获取列表数据变量名
                    Type type = field.getGenericType();//获取List列表变量类型
                    if (type instanceof ParameterizedType) {
                        ParameterizedType pt = (ParameterizedType) type;
                        Class<?> c = (Class<?>) pt.getActualTypeArguments()[0];
                        listClassName = c.getName();
                    }
                    break;
                }
            }
            if (listName != null) {//如果有信息列表（嵌套转换）
                if (listClassName == null) {//如果为null，是model定义有问题，没有把泛型加入进来，需要定义泛型类型
                    throw new Exception("列表数据需定义泛型，请检查");
                }
                if (StringUtil.isEmpty(paramsMap.get(listName))) {
                    paramsMap.put(listName, "[]");
                }
                String jsonStr = JsonLibUtil.bean2Json(paramsMap);
                respBean = (BaseResp) JsonLibUtil.json2Bean(jsonStr, beanClass, listName,
                    Class.forName(listClassName));

            } else {
                String jsonStr = JsonLibUtil.bean2Json(paramsMap);
                respBean = (BaseResp) JsonLibUtil.json2Bean(jsonStr, beanClass);
            }

        } catch (Exception e) {
            log.error("parse response message error! cause by : ", e);
        }
        return respBean;
    }

    /**
     * 验签
     * @param paramsMap
     * @return
     */
    private static boolean checkVerifyString(Map<String, String> paramsMap) {
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
        String verifystring = buildVerifyString(paramsMap, Pay19KeyedDigestMD5.encode_UTF8);
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
    public static String buildVerifyString(Map<String, String> paramsMap, String encode) {
        StringBuffer text = new StringBuffer();
        Set<String> keySet = paramsMap.keySet();
        Object[] keyArr = keySet.toArray();
        Arrays.sort(keyArr);//对key进行ASCII排序

        for (Object key : keyArr) {
            text.append(key).append("=").append(paramsMap.get(key)).append("&");
        }
        String hash = StringUtil.substring(text.toString(), 0, text.length() - 1);
        log.info("19Pay消息摘要明文：【" + hash + "】");
        String verifyString = StringUtil.lowerCase(Pay19KeyedDigestMD5.getKeyedDigest(hash,
            Pay19HttpUtil.merchant_key, encode));
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
