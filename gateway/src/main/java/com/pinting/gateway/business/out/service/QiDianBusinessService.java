package com.pinting.gateway.business.out.service;

import com.pinting.gateway.hessian.message.qidian.G2BReqMsg_QiDian_FranchiseeRegist;
import com.pinting.gateway.hessian.message.qidian.G2BResMsg_QiDian_FranchiseeRegist;

public interface QiDianBusinessService {
	/**
	 * @Title: sendBsFranchiseeRegist 
	 * @Description: 向business注册七店店主信息并返回结果
	 * @param req G2BResMsg_QiDian_FranchiseeRegist
	 * @return G2BReqMsg_QiDian_FranchiseeRegist
	 */
	public G2BResMsg_QiDian_FranchiseeRegist sendBsFranchiseeRegist(
			G2BReqMsg_QiDian_FranchiseeRegist req);
}
