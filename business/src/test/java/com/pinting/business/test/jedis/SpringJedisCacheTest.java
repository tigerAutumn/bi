package com.pinting.business.test.jedis;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.pinting.core.redis.JedisClientDaoSupport;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:META-INF/spring/spr-common.xml","classpath:META-INF/spring/spr-redis-beans.xml","classpath:META-INF/remote/rm-client-gateway.xml"})
public class SpringJedisCacheTest extends JedisClientDaoSupport {
	@Autowired
	private StringRedisTemplate stringRedisTemplate;
	@Test
	public void jedisCacheTest(){
		stringRedisTemplate.opsForValue().set("myStr", "ceshiceshi");
		System.out.println(stringRedisTemplate.opsForValue().get("myStr"));
	}

}
