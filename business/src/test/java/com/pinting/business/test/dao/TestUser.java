package com.pinting.business.test.dao;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.pinting.business.dao.BsUserMapper;
import com.pinting.business.service.site.AccountService;
import com.pinting.business.test.TestBase;

public class TestUser extends TestBase{
	@Autowired
	private BsUserMapper userMapper;
	@Autowired
	private AccountService accountService;
	
	@Before
	public void before(){
//		BsUserExample example = new BsUserExample();
//		example.createCriteria().andIdGreaterThan(0);
//		//删除所有数据
//		userMapper.deleteByExample(example);
	}
	
	@Test
	public void testInsert(){
//		BsUser user = new BsUser();
//		user.setEmail("email");
//		user.setNick("nick");
//		user.setMobile("mobile");
//		user.setPassword("password");
//		user.setStatus(1);
//		userMapper.insert(user);
//		BsUserExample example = new BsUserExample();
//		example.createCriteria().andNickEqualTo("nick");
//		Assert.assertEquals("email", userMapper.selectByExample(example).get(0).getEmail());
	}
	
	@Test
	public void testQuery(){
		//Assert.assertEquals("zcz", userMapper.selectByPrimaryKey(1).getNick());
		Assert.assertNull("aaaa", accountService.findAccountJnlByUserId(1000, 1, 1));
	}
	
}
