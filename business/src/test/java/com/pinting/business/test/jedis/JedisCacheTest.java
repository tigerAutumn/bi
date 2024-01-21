package com.pinting.business.test.jedis;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import redis.clients.jedis.Jedis;

import com.pinting.core.redis.JedisClientDaoSupport;
import com.pinting.core.redis.sentinel.JedisSentinelSharedPool;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:META-INF/spring/spr-common.xml","classpath:META-INF/spring/spr-redis-beans.xml","classpath:META-INF/remote/rm-client-gateway.xml","classpath:META-INF/remote/rm-client-schedule.xml"})
public class JedisCacheTest extends JedisClientDaoSupport {
	
	@Test
	public void jedisCacheTest(){
		try {
			lock("lock4test");
			
			setSubSys("testSys");
			setString("nihao","key2",600);
			System.out.println("redis setup success!"+getString("key2"));
			
			TestModel m = new TestModel("ssssss", new Date(), Integer.valueOf("130"));
			addOrUpdateObj(m, "testM");
			
			TestModel resM = getObj(TestModel.class, "testM");
			
			System.out.println(resM.getI1());
			System.out.println(resM.getI2());
			System.out.println(resM.getI3());
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			unlock("lock4test");
		}
	}
	@Test
	public void printTest(){
		try {
			setSubSys("testSys");

			setString("test","repay_111");
			String result=getString("repay_*");

			System.out.println(result);
			System.out.println("redis setup success!"+ getString("key2"));
			
			Jedis jedis = JedisSentinelSharedPool.getSentinelJedis();
			
			System.out.println(">>>>>>>>>>>>>>>>>"+jedis.keys("*repay_*"));
			
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
class TestModel implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 6042939137059759148L;
	private String i1;
	private Date i2;
	private Integer i3;
	public TestModel(String i1, Date i2, Integer i3) {
		super();
		this.i1 = i1;
		this.i2 = i2;
		this.i3 = i3;
	}
	public String getI1() {
		return i1;
	}
	public void setI1(String i1) {
		this.i1 = i1;
	}
	public Date getI2() {
		return i2;
	}
	public void setI2(Date i2) {
		this.i2 = i2;
	}
	public Integer getI3() {
		return i3;
	}
	public void setI3(Integer i3) {
		this.i3 = i3;
	}
	
	
}