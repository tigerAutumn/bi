package com.pinting.gateway.reapal.out.util;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.HashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pinting.gateway.reapal.out.model.resp.ReapalBaseOutResp;

import net.sf.json.JSONObject;

public class ReapalMessageUtil {

	private static Logger logger = LoggerFactory.getLogger(ReapalMessageUtil.class);
	
	private final static String respBeanPre = "com.pinting.gateway.reapal.out.model.resp.";
	
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
                // 得到property对应的getter方法  
                Method getter = property.getReadMethod();
                Object value = getter.invoke(obj);
                if(!"class".equals(key)) {
                	if(value != null && !"".equals(value.toString())) {
                    	map.put(key, value.toString());
                    }
                }
            }
        } catch (Exception e) {
            logger.info("transBean2Map Error " + e);
        }
        return map;
    }
    
    /**
     * 
     * @Title: parseResp 
     * @Description: 将json串变成对象
     * @param respStr
     * @param code
     * @return
     * @throws
     */
    public static ReapalBaseOutResp parseResp(String respStr, String code) {
    	logger.info("Reapal解密结果：【" + respStr + "】");
    	ReapalBaseOutResp respBean = null;
    	JSONObject jsonObject = JSONObject.fromObject(respStr);
    	try {
    		respBean = (ReapalBaseOutResp)JSONObject.toBean(jsonObject, Class.forName(respBeanPre + code));
    	}catch(Exception e) {
    		logger.error(e.getMessage());
    	}
        return respBean;
    }
}
