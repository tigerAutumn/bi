package com.pinting.mall.service;

import com.pinting.mall.model.MallBsWxUserInfo;



public interface MallWxUserService {
	/**
	 * 根据用户Id查询微信用户id
	 * @param userId
	 * @return
	 */
	public MallBsWxUserInfo getWxUserByUserId(Integer userId);
}
