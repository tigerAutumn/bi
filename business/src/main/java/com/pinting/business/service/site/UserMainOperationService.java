package com.pinting.business.service.site;

import com.pinting.business.model.BsUserMainOperation;

/**
 * 用户关键业务信息接口
 *
 * @Project: business
 * @author yanwl
 * @Title: UserMainOperationService.java
 * @date 2016-3-8 下午2:58:55
 * @Copyright: 2016 bigangwan.com Inc. All rights reserved.
 */
public interface UserMainOperationService {
	/**
	 * 新增用户关键业务信息
	 * @param userMainOperation
	 * @return 新增成功影响行数
	 */
	public int saveUserMainOperation(BsUserMainOperation userMainOperation);
}
