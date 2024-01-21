/**
 * Wenzi.com Inc.
 * Copyright (c) 2015-2025 All Rights Reserved.
 */
package com.pinting.business.service.site;

import java.util.List;

import com.pinting.business.hessian.site.message.ReqMsg_UserProductLimit_UserProductLimitAdd;
import com.pinting.business.hessian.site.message.ResMsg_UserProductLimit_UserProductLimitAdd;
import com.pinting.business.model.BsUserProductLimit;
import com.pinting.business.model.BsUserProductLimitExample;
/**
 * 
 * @author yanwl
 * @version $Id: UserProductLimitService.java, v 0.1 2016-3-16 下午12:56:26 yanwl Exp $
 */
public interface UserProductLimitService {
    /**
     * 
     * 根据条件查询用户产品限额列表
     * @param  example
     * @return List<BsUserProductLimit> 用户产品限额列表
     */
    public List<BsUserProductLimit> findUserProductLimit(BsUserProductLimitExample example);
    
    /**
     * 新增产品限额表数据
     * @param req ReqMsg_UserProductLimit_UserProductLimitAdd
     * @param res ResMsg_UserProductLimit_UserProductLimitAdd
     */
	public void userProductLimitAdd(ReqMsg_UserProductLimit_UserProductLimitAdd req,ResMsg_UserProductLimit_UserProductLimitAdd res);

}
