package com.pinting.core.redis;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.Protocol;

/**
 * 连接池 （单服务器模式）
 * @author jackin
 *
 */
public class JedisSharedPool   {
	
	private JedisPoolCfg cfg;
	private List<JedisSharedInfo> infoList;
	
	private static Map<String,JedisPool> maps  = new HashMap<String,JedisPool>(); 
	
	public JedisSharedPool(JedisPoolCfg cfg,List<JedisSharedInfo> infoList){
		this.cfg = cfg;
		this.infoList = infoList;
		setPools();
		
	}
	
	
	public static JedisPool getPool(String serverName){
		return maps.get(serverName);
	}
	
	public static JedisPool getPool(int serverIndex){
		Set<String>  keys =maps.keySet();  
		Iterator<String> iterator = keys.iterator( ); 
		int k =maps.size()-1;
		while (iterator.hasNext()) {  
			Object key = iterator.next( );   
			JedisPool value = maps.get(key);   
			if(k==serverIndex){
				return value;
			}
			k--;
		}   
		return null;
	}
	
    public  void setPools() {  
    	maps.clear();
    	for(int i=0;i<infoList.size();i++){
    		JedisSharedInfo info = infoList.get(i);
	        String key = info.getServerName(); 
	        JedisPool pool = null;  
	        if(!maps.containsKey(key)) {  
	            JedisPoolConfig config = new JedisPoolConfig();  
	            config.setMaxTotal(cfg.getMaxActive()); 
	            config.setMaxIdle(cfg.getMaxIdle());  
	            config.setMaxWaitMillis(cfg.getMaxWait());
	            config.setTestOnBorrow(true);
	            config.setTestOnReturn(true);
	            try{    
	                pool = new JedisPool(config, info.getIp(), info.getPort(),Protocol.DEFAULT_TIMEOUT,info.getPassword());  
	                maps.put(key, pool);
	            } catch(Exception e) {  
	                e.printStackTrace();  
	            }  
	        }else{  
	            pool = maps.get(key);  
	        }  
    	}
    }
}
