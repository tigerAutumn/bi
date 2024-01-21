package com.pinting.business.service.manage;

import java.util.List;

import com.pinting.business.model.BsUserSealFile;

/**
 * 管理台签章协议进行重发签章接口
 * @project business
 * @title AgreeMentRepeatSendService.java
 * @author Dragon & cat
 * @date 2017-5-26
 * @Copyright 2015 BiGangWan.com Inc. All rights reserved.
 * @Description
 */
public interface SignRepeatSendService {
	/**
	 * 借款咨询与服务协议 签章(赞分期)
	 * @param id
	 */
	void signBorrowServicesZan(Integer id);
	/**
	 * 查询未成功的签章协议信息
	 * @param type 类型（LOAN_AGREEMENT 云贷四方协议、BORROW_SERVICES 赞分期借款咨询与服务协议）
	 * @return
	 */
	List<BsUserSealFile> querySignRepeatData(String type,Integer start ,Integer numPerPage);
	/**
	 * 统计未成功的签章协议信息
	 * @param type 类型（LOAN_AGREEMENT 云贷四方协议、BORROW_SERVICES 赞分期借款咨询与服务协议）
	 * @return
	 */
	Integer countSignRepeatData(String type);
	/**
	 * 云贷四方协议 签章
	 * @param id
	 */
	void signLoanAgreementYun(Integer id);
	
	/**
	 * 七贷四方协议 签章
	 * @param id
	 */
	void signLoanAgreement7(Integer id);
	
	/**
	 * 借款咨询与服务协议 签章(赞时贷)
	 * @param id
	 */
	void signBorrowServicesZsd(Integer id);
	
}
