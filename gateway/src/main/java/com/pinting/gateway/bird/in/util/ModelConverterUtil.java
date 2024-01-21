/**
 * business.com Inc.
 * Copyright (c) 2015 All Rights Reserved.
 */
package com.pinting.gateway.bird.in.util;

import com.pinting.gateway.enums.PTMessageEnum;
import com.pinting.gateway.exception.PTMessageException;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.HashMap;

/**
 * 
 * @author dingpf
 * @version $Id: ModelConverterUtil.java, v 0.1 2015-5-12 下午3:44:42 dingpf Exp $
 */
public class ModelConverterUtil<Q, S> {

    /** 字段排除 */
    private String[]                excludeConfiger = {};
    /** 字段对应 （key转换后字段 ---> value待转换字段）*/
    private HashMap<String, String> includeMapper   = new HashMap<String, String>();

    /**
     * model转换
     * 
     * @param q 待转model
     * @param s 转换后model
     * @return 
     */
    @SuppressWarnings("unchecked")
    public void convertModel(Q q, S s) {
        HashMap<String, Object> beanMap = transBean2Map(q);
        s = (S) transMap2Bean(beanMap, s);
    }

    public HashMap<String, Object> transBean2Map(Object obj) {

        if (obj == null) {
            return null;
        }
        HashMap<String, Object> map = new HashMap<String, Object>();
        try {
            BeanInfo beanInfo = Introspector.getBeanInfo(obj.getClass());
            PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
            for (PropertyDescriptor property : propertyDescriptors) {
                String key = property.getName();
                boolean isExclude = false;
                for (String excludeKey : excludeConfiger) {
                    if (excludeKey.equals(key)) {
                        isExclude = true;
                        break;
                    }
                }
                // 过滤class属性  
                if (!key.equals("class") && !isExclude) {
                    // 得到property对应的getter方法  
                    Method getter = property.getReadMethod();
                    Object value = getter.invoke(obj);

                    map.put(key, value);
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return map;

    }

    public Object transMap2Bean(HashMap<String, Object> map, Object bean) {

        if (bean == null) {
            return null;
        }
        if (map == null) {
            return bean;
        }
        try {
            BeanInfo beanInfo = Introspector.getBeanInfo(bean.getClass());
            PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
            for (PropertyDescriptor property : propertyDescriptors) {
                String key = property.getName();
                String mapperKey = includeMapper.get(key);
                // 过滤class属性  
                if (!key.equals("class")) {
                    // 得到property对应的getter方法  
                    Method setter = property.getWriteMethod();
                    if (mapperKey != null && !mapperKey.isEmpty()) {
                        setter.invoke(bean, map.get(mapperKey));
                    } else {
                        if (map.get(key) != null && setter != null) {
                            setter.invoke(bean, map.get(key));
                        }
                    }
                }

            }
        } catch (Exception e) {
            throw new PTMessageException(PTMessageEnum.SYSTEM_ERROR, e.getMessage());
        }

        return bean;

    }

    /**
     * Getter method for property <tt>excludeConfiger</tt>.
     * 
     * @return property value of excludeConfiger
     */
    public String[] getExcludeConfiger() {
        return excludeConfiger;
    }

    /**
     * Setter method for property <tt>excludeConfiger</tt>.
     * 
     * @param excludeConfiger value to be assigned to property excludeConfiger
     */
    public void setExcludeConfiger(String[] excludeConfiger) {
        this.excludeConfiger = excludeConfiger;
    }

    /**
     * Getter method for property <tt>includeMapper</tt>.
     * 
     * @return property value of includeMapper
     */
    public HashMap<String, String> getIncludeMapper() {
        return includeMapper;
    }

    /**
     * Setter method for property <tt>includeMapper</tt>.
     * 
     * @param includeMapper value to be assigned to property includeMapper
     */
    public void setIncludeMapper(HashMap<String, String> includeMapper) {
        this.includeMapper = includeMapper;
    }

}
