package com.pinting.core.redis.sentinel;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisSentinelPool;

/**
 * 连接池（哨兵模式）
 * @Project: core
 * @Title: JedisSentinelSharedPool.java
 * @author dingpf
 * @date 2016-5-31 上午11:01:31
 * @Copyright: 2016 BiGangWan.com Inc. All rights reserved.
 */
public class JedisSentinelSharedPool   {
	private static Logger logger = LoggerFactory.getLogger(JedisSentinelSharedPool.class);
	private static JedisSentinelSharedPool jedisSentinelSharedPool;
    private JedisSentinelPool jedisSentinelPool;
	 
    public JedisSentinelSharedPool(JedisSentinelPool jedisSentinelPool) {
		jedisSentinelSharedPool = this;
		jedisSentinelSharedPool.jedisSentinelPool = jedisSentinelPool;
    }

	public static Jedis getSentinelJedis() {
		HostAndPort currHost = jedisSentinelSharedPool.jedisSentinelPool.getCurrentHostMaster();
		if(currHost != null){
			logger.info("当前redis服务：[" + currHost.getHost() + ":" + currHost.getPort() + "]");
		}
		return jedisSentinelSharedPool.jedisSentinelPool.getResource();
	}
    
}
