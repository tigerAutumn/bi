package com.pinting.business.test.redisson;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import org.redisson.Config;
import org.redisson.Redisson;
import org.redisson.RedissonClient;
import org.redisson.core.RLock;
import org.redisson.core.RMap;

import com.pinting.business.model.BsUser;

import junit.framework.TestCase;

public class CacheTest extends TestCase {
	
protected static RedissonClient redisson;
	
	public CacheTest(){
		if(redisson == null){
			redisson = createInstance();
		}
	}
	//@Test
	public void cacheTest() throws InterruptedException, ExecutionException {
		RMap<String, BsUser> map = redisson.getMap("testMap");
		BsUser user = new BsUser();
		user.setUserName("张三");
		BsUser prevObject = map.put("123", user);
		user.setUserName("李四");
		BsUser currentObject = map.putIfAbsent("323", user);
		BsUser userRes = (BsUser)map.get("323");
		System.out.println(userRes.getUserName());
		BsUser obj = map.remove("123");

		map.fastPut("321", user);
		BsUser userRes321 = (BsUser)map.get("321");
		System.out.println(userRes321.getUserName());
		map.fastRemove("321");
		user.setUserName("王五");
		Future<BsUser> putAsyncFuture = map.putAsync("321", user);
		user.setUserName("刘六");
		Future<Boolean> fastPutAsyncFuture = map.fastPutAsync("321", user);

		/*map.fastPutAsync("321", user);
		BsUser userRes321_1 = putAsyncFuture.get();
		System.out.println(userRes321_1.getUserName());
		map.fastRemoveAsync("321");*/
		
		redisson.shutdown(60, 60, TimeUnit.SECONDS);
		
		System.out.println("..........");
	}
	
	public static void main(String[] args) throws InterruptedException, ExecutionException {
		
		
		new CacheTest().cacheTest();
	}
	
	
	public static Config createConfig() {
        String redisAddress = System.getProperty("redisAddress");
        if (redisAddress == null) {
            redisAddress = "121.43.110.214:6379";
        }
        Config config = new Config();
//        config.setCodec(new MsgPackJacksonCodec());
        config.useSentinelServers().setMasterName("mymaster").addSentinelAddress("121.43.110.214:26379", "114.215.199.67:36379").setPassword("pintingBgw");
//        config.useClusterServers().addNodeAddress("127.0.0.1:7004", "127.0.0.1:7001", "127.0.0.1:7000");
        //config.useSingleServer().setAddress(redisAddress).setPassword("pintingBgw");
        /*config.useMasterSlaveServers()
        	.setMasterAddress(redisAddress)
        	.addSlaveAddress("114.215.199.67:6379").setPassword("pintingBgw");*/
//        	.addSlaveAddress("127.0.0.1:6389");
        return config;
    }

    public static RedissonClient createInstance() {
        Config config = createConfig();
        return Redisson.create(config);
    }

}
