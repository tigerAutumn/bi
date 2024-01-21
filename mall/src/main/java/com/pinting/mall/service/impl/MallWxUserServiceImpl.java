package com.pinting.mall.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pinting.mall.dao.MallBsWxUserInfoMapper;
import com.pinting.mall.model.MallBsWxUserInfo;
import com.pinting.mall.model.MallBsWxUserInfoExample;
import com.pinting.mall.service.MallWxUserService;



@Service
public class MallWxUserServiceImpl implements MallWxUserService{
	@Autowired
	private MallBsWxUserInfoMapper bsWxUserInfoMapper;
	
	@Override
	public MallBsWxUserInfo getWxUserByUserId(Integer userId) {
		MallBsWxUserInfoExample example = new MallBsWxUserInfoExample();
		example.createCriteria().andUserIdEqualTo(userId);
		
		List<MallBsWxUserInfo> bsUserList = bsWxUserInfoMapper.selectByExample(example);
		if(bsUserList != null && bsUserList.size() > 0)
		{
			return bsUserList.get(0);
		}
		return null;
	}
}
