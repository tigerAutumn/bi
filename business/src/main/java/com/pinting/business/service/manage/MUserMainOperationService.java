package com.pinting.business.service.manage;

import java.util.List;

import com.pinting.business.model.BsUserMainOperation;

/**
 * 用户关键业务信息接口
 *
 * @Project: business
 * @author yanwl
 * @Title: MUserMainOperationService.java
 * @date 2016-3-8 下午2:58:55
 * @Copyright: 2016 bigangwan.com Inc. All rights reserved.
 */
public interface MUserMainOperationService {
	/**
	 * 分页查询用户关键业务信息
	 * @param userMainOperation
	 * @return 用户关键业务列表
	 */
	public List<BsUserMainOperation> findUserMainOperationList(BsUserMainOperation userMainOperation);
	
	/**
	 * 查询用户关键业务信息总数
	 * @param userMainOperation
	 * @return 用户关键业务总数
	 */
	public int findUserMainOperationAllCount(BsUserMainOperation userMainOperation);
}
