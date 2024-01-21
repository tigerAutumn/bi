package com.pinting.gateway.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @Project: gateway
 * @Title: TestBase.java
 * @author Zhou Changzai
 * @date 2015-2-12 下午3:31:08
 * @Copyright: 2015 BiGangWan.com Inc. All rights reserved.
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:META-INF/spring/spr-common.xml","classpath:META-INF/remote/rm-client-business.xml",
		"classpath:META-INF/remote/rm-srv-business.xml","classpath:META-INF/spring/spr-interceptor.xml",
		"classpath:META-INF/spring/spr-redis-beans.xml"})
public class TestBase {
	@Test
	public void testDummy(){
	}
}

