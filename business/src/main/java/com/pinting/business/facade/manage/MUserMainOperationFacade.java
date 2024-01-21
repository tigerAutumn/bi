package com.pinting.business.facade.manage;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.pinting.business.hessian.manage.message.ReqMsg_MUserMainOperation_UserMainOperationListQuery;
import com.pinting.business.hessian.manage.message.ResMsg_MUserMainOperation_UserMainOperationListQuery;
import com.pinting.business.model.BsUserMainOperation;
import com.pinting.business.service.manage.MUserMainOperationService;
import com.pinting.business.util.BeanUtils;
import com.pinting.core.util.StringUtil;

/**
 *
 * @Project: business
 * @author yanwl
 * @Title: MUserMainOperationFacade.java
 * @date 2016-3-11 下午3:08:53
 * @Copyright: 2016 bigangwan.com Inc. All rights reserved.
 */
@Component("MUserMainOperation")
public class MUserMainOperationFacade {
	@Autowired
	private MUserMainOperationService mUserMainOperationService;
	
	public void userMainOperationListQuery(ReqMsg_MUserMainOperation_UserMainOperationListQuery req,ResMsg_MUserMainOperation_UserMainOperationListQuery res) {
		BsUserMainOperation userMainOperation = new BsUserMainOperation();
		userMainOperation.setPageNum(req.getPageNum());
		userMainOperation.setNumPerPage(req.getNumPerPage());
		if(StringUtil.isNotEmpty(req.getMobile())) {
			userMainOperation.setUserMobile(req.getMobile());
		}
		if(StringUtil.isNotEmpty(req.getSrcIp())) {
			userMainOperation.setSrcIp(req.getSrcIp());
		}
		int totalRows = mUserMainOperationService.findUserMainOperationAllCount(userMainOperation);
		if(totalRows > 0) {
			List<BsUserMainOperation> list = mUserMainOperationService.findUserMainOperationList(userMainOperation);
			res.setUserMainOperationList(BeanUtils.classToArrayList(list));
		}
		res.setTotalRows(totalRows > 0 ? totalRows : 0);
	} 
}
