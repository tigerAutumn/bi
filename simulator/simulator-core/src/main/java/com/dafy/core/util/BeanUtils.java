package com.dafy.core.util;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;



public class BeanUtils {
	
	public static ArrayList<HashMap<String,Object>> classToArrayList(List<?> value){
		if(value == null || value.isEmpty()){
			return null;
		}
		ArrayList<HashMap<String,Object>> resultList = new ArrayList<HashMap<String,Object>>();
		for (Object o : value) {
			HashMap<String,Object> result = transBeanMap(o);
			resultList.add(result);
		}
		return resultList;
	}
	
	public static HashMap<String, Object> transBeanMap(Object obj) {  
		  
	        if(obj == null){  
	            return null;  
	        }          
	        HashMap<String, Object> map = new HashMap<String, Object>();  
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
	  
	                    map.put(key, value);  
	                }  
	  
	            }  
	        } catch (Exception e) {  
	            System.out.println("transBean2Map Error " + e);  
	        }  
	  
	        return map;  
  
    }  
	public static HashMap<String,Object> classToHashMap(Object o){
		
		HashMap<String,Object> result = new HashMap<String,Object>();
		try {
			Class<?> sonClass  = o.getClass();
			
			
			Class<?> fatherClass = sonClass.getSuperclass();
            
            Method[] method = fatherClass.getMethods();
            //Method[] method = sonClass.getMethods();
            int index = 1;
            //遍历父类的所有的方法
            for(Method tmp : method){
            	if(tmp.getName().equals("getClass")){
            		continue;
            		
            	}
                    if(tmp.getName().contains("get")){
                    	String fileName = tmp.getName().substring(3, tmp.getName().length()).toLowerCase();
                    	Method getMethod = fatherClass.getMethod(tmp.getName(), new Class[]{});
                    	Object value = getMethod.invoke(o, new Object[]{});
                    	result.put(fileName, value);
                    }
            }
			
			//获得所有成员变量 
			Field[] declaredFields = sonClass .getDeclaredFields();
			
			for (int i = 0; i < declaredFields.length; i++) {
				
				//遍历所有成员变量     
			    Field field = declaredFields[i];
			    
			    String fieldName = field.getName();

			    String firstLetter = fieldName.substring(0, 1).toUpperCase();
	            // 获得和属性对应的getXXX()方法的名字
	            String getMethodName = "get" + firstLetter + fieldName.substring(1);
			    
			    Method getMethod = sonClass.getMethod(getMethodName, new Class[]{});
			    
			    Object value = getMethod.invoke(o, new Object[]{});
			    
				result.put(fieldName, value);
			}
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		} 
		return result;
	}
	
	public static HashMap<String,Object> classToHashMap(Class<?> classType){
		HashMap<String,Object> result = new HashMap<String,Object>();
		
		return result;
	}
	
	
	public static HashMap<String,String> classToHashMapStr(Object o){
		
		HashMap<String,String> result = new HashMap<String,String>();
		try {
			Class<?> sonClass  = o.getClass();
			
			
			Class<?> fatherClass = sonClass.getSuperclass();
            
            Method[] method = fatherClass.getMethods();
            //Method[] method = sonClass.getMethods();
            int index = 1;
            //遍历父类的所有的方法
            for(Method tmp : method){
            	if(tmp.getName().equals("getClass")){
            		continue;
            		
            	}
                    if(tmp.getName().contains("get")){
                    	String fileName = tmp.getName().substring(3, tmp.getName().length()).toLowerCase();
                    	Method getMethod = fatherClass.getMethod(tmp.getName(), new Class[]{});
                    	Object value = getMethod.invoke(o, new Object[]{});
                    	String strValue = value==null ? "" : value.toString();
                    	result.put(fileName, strValue);
                    }
            }
			
			//获得所有成员变量 
			Field[] declaredFields = sonClass .getDeclaredFields();
			
			for (int i = 0; i < declaredFields.length; i++) {
				
				//遍历所有成员变量     
			    Field field = declaredFields[i];
			    
			    String fieldName = field.getName();

			    String firstLetter = fieldName.substring(0, 1).toUpperCase();
	            // 获得和属性对应的getXXX()方法的名字
	            String getMethodName = "get" + firstLetter + fieldName.substring(1);
			    
			    Method getMethod = sonClass.getMethod(getMethodName, new Class[]{});
			    
			    Object value = getMethod.invoke(o, new Object[]{});
			    String strValue = value==null ? "" : value.toString();
				result.put(fieldName, strValue);
			}
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		} 
		return result;
	}
}
