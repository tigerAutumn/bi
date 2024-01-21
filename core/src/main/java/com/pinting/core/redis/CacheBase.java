package com.pinting.core.redis;

import java.util.List;

import com.pinting.core.util.StringUtil;

/**
 * @ClassName CacheBase
 * @author Jackin
 */
public abstract class CacheBase {

	private String splitChar ="|";//分隔符
	
    private String subSys="";
    
    protected  String PRE_LOCKKEY = "PTLOCKKEY";//锁健前缀
    
    public String getSubSys(){
    	return subSys;
    }
    
    public void setSubSys(String subSys){
    	this.subSys = subSys;
    }
    
    protected String getSubSysKey(String key){
    	return subSys+splitChar+key.toLowerCase();
    }
    
    protected String getTableKey(String tableName){
    	return subSys+splitChar+tableName.toLowerCase();
    }
    
    protected String getRecordKey(String tableName,String lookUpKey){
    	
    	if(subSys.equals("")){
			try {
				throw new Exception("请设定子系统");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
    	return subSys+splitChar+tableName.toLowerCase()+splitChar+lookUpKey.toLowerCase();
    }
    
    protected String getOriKey(String recordKey){
    	String[] ss = StringUtil.split(recordKey, splitChar);
    	return ss[ss.length-1];
    }
    
    /**
     * 新增或更新对象
     * @Method: addOrUpdateObj 
     * @param obj
     * @param lookUpKey
     * @param expire 过期时间
     * @return
     */
    public abstract long addOrUpdateObj(Object obj,String lookUpKey,int expire);
    
    /**
     * 新增或更新对象，不过期
     * @Method: addOrUpdateObj 
     * @param obj
     * @param lookUpKey
     * @return
     */
    public  long addOrUpdateObj(Object obj,String lookUpKey){
    	return addOrUpdateObj(obj,lookUpKey,-1);
    }
    /**
     * 获取对象
     * @Method: getObj 
     * @param t
     * @param lookUpKey
     * @return
     */
    public abstract <T extends Object> T getObj(Class<T> t,String lookUpKey);
    
    /**
     * 更新对象，比上面的效率稍微高些；
     * @Method: updateObj 
     * @param t
     * @param lookUpKey
     * @return
     */
    public abstract <T extends Object> long updateObj(T t,String lookUpKey);
    
    /**
     * 获取和这个对象相关的所有记录
     * @Method: selectAll 
     * @param t
     * @return
     */
    public abstract <T extends Object> List<T> getAllObjs(Class<T> t);
    
    /**
     * 删除一个对象
     * @Method: deleteObj 
     * @param t
     * @param lookUpKey
     * @return
     */
    public abstract  <T extends Object>  long deleteObj(Class<T> t,String lookUpKey);
    
    /**
     * 删除所有对象
     * @Method: deleteAllObjOfSer 
     * @param t
     * @return
     */
    public abstract <T extends Object> long deleteAllObjs(Class<T> t);
    
    
    
}
