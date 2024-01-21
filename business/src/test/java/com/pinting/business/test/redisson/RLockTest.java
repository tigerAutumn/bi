package com.pinting.business.test.redisson;

import java.util.concurrent.TimeUnit;

import junit.framework.TestCase;

import org.junit.Test;
import org.redisson.Config;
import org.redisson.Redisson;
import org.redisson.RedissonClient;
import org.redisson.RedissonLock;
import org.redisson.core.RLock;

import com.pinting.business.test.TestBase;

public class RLockTest extends TestCase {
	protected static RedissonClient redisson;
	
	public RLockTest(){
		if(redisson == null){
			redisson = createInstance();
		}
	}
	//@Test
	public void lockTest() {
		RLock lock = redisson.getLock("lock4Buy");
		lock.lock();
		try {
			//lock.tryLock(3, TimeUnit.SECONDS);
			long startTime = System.currentTimeMillis();
			System.out.println(startTime);
			Thread.sleep(1000);
			long endTime = System.currentTimeMillis();
			System.out.println(endTime - startTime);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if(lock.isLocked()){
				lock.unlock();
			}
		}
		redisson.shutdown();
	}
	
	public static void main(String[] args) {
		
		
		new RLockTest().lockTest();
	}
	
	
	public static Config createConfig() {
        String redisAddress = System.getProperty("redisAddress");
        if (redisAddress == null) {
            redisAddress = "121.43.110.214:6379";
        }
        Config config = new Config();
//        config.setCodec(new MsgPackJacksonCodec());
//        config.useSentinelServers().setMasterName("mymaster").addSentinelAddress("127.0.0.1:26379", "127.0.0.1:26389");
//        config.useClusterServers().addNodeAddress("127.0.0.1:7004", "127.0.0.1:7001", "127.0.0.1:7000");
        //config.useSingleServer().setAddress(redisAddress).setPassword("pintingBgw");
        config.useMasterSlaveServers()
        	.setMasterAddress(redisAddress)
        	.addSlaveAddress("114.215.199.67:6379").setPassword("pintingBgw");
//        	.addSlaveAddress("127.0.0.1:6389");
        return config;
    }

    public static RedissonClient createInstance() {
        Config config = createConfig();
        return Redisson.create(config);
    }
	
	

}
