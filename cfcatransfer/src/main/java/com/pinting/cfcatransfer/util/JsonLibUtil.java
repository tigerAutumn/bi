package com.pinting.cfcatransfer.util;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.pinting.core.json.JsonValueProcessorImpl;

import net.sf.ezmorph.object.DateMorpher;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import net.sf.json.util.JSONUtils;

public class JsonLibUtil {
	 /**
     * 将对象序列化成json字符串
     * @param obj
     * @return
     */
    public static String bean2Json(Object obj){
    	JsonConfig config = new JsonConfig();
		config.registerJsonValueProcessor(Date.class,
				new JsonValueProcessorImpl());
        JSONObject jsonObject = JSONObject.fromObject(obj, config);
        return jsonObject.toString();
    }
    
    /**
     * 将json字符串反序列化为对象
     * @param jsonStr
     * @param objClass 反序列化为该类的对象
     * @return
     */
    @SuppressWarnings("unchecked")
    public static <T> T json2Bean(String jsonStr,Class<T> objClass){
    	// String 转 json
    	JsonConfig config = new JsonConfig();
    	config.registerJsonValueProcessor(Date.class, new JsonValueProcessorImpl());
    	//替换换行符为空
    	jsonStr = replaceString(jsonStr);
    	JSONObject jsonObject = JSONObject.fromObject(jsonStr, config);
    	// json 转 javabean
    	String[] dateFmts = new String[] { "yyy-MM-dd HH:mm:ss", "yyyy-MM-dd", "yyyy年MM月dd日 HH时mm分ss秒" };
    	JSONUtils.getMorpherRegistry().registerMorpher(new DateMorpher(dateFmts));
    	
        return (T)JSONObject.toBean(jsonObject, objClass);
    }
    
    @SuppressWarnings({ "unchecked", "rawtypes" })
	public static <T> T json2Bean(String jsonStr,Class<T> objClass, String listName, Class listClass){
    	// String 转 json
    	JsonConfig config = new JsonConfig();
    	config.registerJsonValueProcessor(Date.class, new JsonValueProcessorImpl());
    	jsonStr = jsonStr.replace("\"[", "[").replace("]\"", "]").replace("\\", "");
    	JSONObject jsonObject = JSONObject.fromObject(jsonStr, config);
    	// json 转 javabean
    	String[] dateFmts = new String[] { "yyy-MM-dd HH:mm:ss", "yyyy-MM-dd", "yyyy年MM月dd日 HH时mm分ss秒" };
    	JSONUtils.getMorpherRegistry().registerMorpher(new DateMorpher(dateFmts));
    	
    	Map<String,Class> map = new HashMap<String, Class>();
    	map.put(listName, listClass);
    	
        return (T)JSONObject.toBean(jsonObject, objClass, map);
    }
    
    /**
     * 替换换行符为空
     * @param sContent
     * @return
     */
    private static String replaceString(String sContent) {
//    	String separator = (String) java.security.AccessController.doPrivileged(
//                new sun.security.action.GetPropertyAction("line.separator"));
    	if (sContent == null) { return sContent; }
//    	if (sContent.contains(separator)) {
//    		sContent = sContent.replace(separator, "");
//    	}       
    	sContent = sContent.replaceAll("\r", "").replaceAll("\n", "");
    	sContent = sContent.trim();
    	return sContent;
    }
}
