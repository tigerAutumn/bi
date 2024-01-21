package com.pinting.gateway.qidian.in.service;

import com.pinting.gateway.qidian.in.model.FranchiseeRegistReqModel;
import com.pinting.gateway.qidian.in.model.FranchiseeRegistResModel;
import com.pinting.gateway.qidian.in.model.LoginReqModel;
import com.pinting.gateway.qidian.in.model.LoginResModel;

public interface QiDianService {
	
	/**
	 * 登录	
	 * @param req LoginReqModel
	 * @return  LoginResModel
	 */
	public LoginResModel login(LoginReqModel req);
	
	/**
	 * 店主注册
	 * @param req FranchiseeRegistReqModel
	 * @return FranchiseeRegistResModel
	 */
	public FranchiseeRegistResModel franchiseeRegist(FranchiseeRegistReqModel req);
	

}
