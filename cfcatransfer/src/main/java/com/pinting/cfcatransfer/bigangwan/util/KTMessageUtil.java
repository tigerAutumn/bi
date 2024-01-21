/**
 * Wenzi.com Inc.
 * Copyright (c) 2015-2025 All Rights Reserved.
 */
package com.pinting.cfcatransfer.bigangwan.util;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.Date;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import com.pinting.cfcatransfer.bigangwan.model.cfca.KTBaseReq;
import com.pinting.cfcatransfer.bigangwan.model.cfca.KTBaseResp;
import com.pinting.core.util.DateUtil;
import com.pinting.core.util.StringUtil;

/**
 * 废除，CFCA已提供消息对象以及转换！/(ㄒoㄒ)/~~
 * @author Baby shark love blowing wind
 * @version $Id: KTMessageUtil.java, v 0.1 2015-9-14 下午3:26:42 BabyShark Exp $
 */
public class KTMessageUtil {

    /**
     * 请求对象转为xml格式字符串
     * 
     * @param req
     * @return
     */
    public static String buildReq2Xml(KTBaseReq req) {
        if (req == null) {
            return null;
        }
        Document document = DocumentHelper.createDocument();
        Element root = document.addElement("Request");
        Element headRoot = root.addElement("Head");
        Element bodyRoot = root.addElement("Body");
        headRoot.addElement("TxType").addText(req.getTxType());

        try {
            BeanInfo beanInfo = Introspector.getBeanInfo(req.getClass());
            PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
            for (PropertyDescriptor property : propertyDescriptors) {
                String key = property.getName();
                if (!"class".equals(key) && !"TxType".equals(key)) {
                    // 得到property对应的getter方法  
                    Method getter = property.getReadMethod();
                    Object value = getter.invoke(req);
                    bodyRoot.addElement(
                        StringUtil.upperCase(key.substring(0, 1)) + key.substring(1)).addText(
                        value.toString());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        String xml = document.asXML();
        return xml;
    }

    /**
     * 返回的xml字符串转成响应对象
     * 
     * @param xml
     * @return 失败或xml为空则返回null
     */
    public static KTBaseResp parseXml2Resp(String xml, KTBaseResp resp) {
        if (StringUtil.isEmpty(xml)) {
            return null;
        }
        try {
            Document document = DocumentHelper.parseText(xml);
            Element root = document.getRootElement();
            Element head = root.element("Head");
            Element body = root.element("Body");
            //head赋值
            resp.setTxType(head.elementText("TxType"));
            String txTime = head.elementText("TxTime");
            Date txTimeParseDate = null;
            if (!StringUtil.isEmpty(txTime)) {
                try {
                    txTimeParseDate = DateUtil.parse(txTime, "yyyy/MM/dd HH:mm:ss");
                } catch (RuntimeException e) {
                    txTimeParseDate = DateUtil.parse(txTime, "yyyyMMddHHmmss");
                }
            }
            resp.setTxTime(txTimeParseDate);
            resp.setResultCode(head.elementText("ResultCode"));
            resp.setResultMessage(head.elementText("ResultMessage"));
            //body赋值
            BeanInfo beanInfo = Introspector.getBeanInfo(resp.getClass());
            PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
            for (PropertyDescriptor property : propertyDescriptors) {
                String key = property.getName();
                if (!"class".equals(key)) {
                    Method setter = property.getWriteMethod();
                    Class type = property.getPropertyType();
                    String value = body.elementText(StringUtil.upperCase(key.substring(0, 1))
                                                    + key.substring(1));
                    if (type.equals(Date.class)) {
                        Date parseDate = null;
                        if (!StringUtil.isEmpty(value)) {
                            try {
                                parseDate = DateUtil.parse(value, "yyyy/MM/dd HH:mm:ss");
                            } catch (RuntimeException e) {
                                parseDate = DateUtil.parse(value, "yyyyMMddHHmmss");
                            }
                        }
                        setter.invoke(resp, parseDate);
                    } else {
                        setter.invoke(resp, value);
                    }
                }
            }
            return resp;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
