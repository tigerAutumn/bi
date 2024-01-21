package com.pinting.business.facade.site;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import com.pinting.business.hessian.site.message.ReqMsg_UserProductLimit_UserProductLimitAdd;
import com.pinting.business.hessian.site.message.ReqMsg_UserProductLimit_UserProductLimitQuery;
import com.pinting.business.hessian.site.message.ResMsg_UserProductLimit_UserProductLimitAdd;
import com.pinting.business.hessian.site.message.ResMsg_UserProductLimit_UserProductLimitQuery;
import com.pinting.business.model.BsUserProductLimit;
import com.pinting.business.model.BsUserProductLimitExample;
import com.pinting.business.service.site.UserProductLimitService;

/**
 * 用户产品限额
 * 
 * @Project: business
 * @author yanwl
 * @Title: UserProductLimitFacade.java
 * @date 2016-3-15 下午5:02:36
 * @Copyright: 2016 bigangwan.com Inc. All rights reserved.
 */
@Component("UserProductLimit")
public class UserProductLimitFacade {
	@Autowired
	private UserProductLimitService userProductLimitService;
	
	public void userProductLimitQuery(ReqMsg_UserProductLimit_UserProductLimitQuery req,ResMsg_UserProductLimit_UserProductLimitQuery res) {
		BsUserProductLimitExample example = new BsUserProductLimitExample();
		example.createCriteria().andUserIdEqualTo(req.getUserId()).andProductIdEqualTo(req.getProductId());
		
		List<BsUserProductLimit> list = userProductLimitService.findUserProductLimit(example);
		if(!CollectionUtils.isEmpty(list)) {
			if(list.get(0) == null) {
				res.setLeftAmount(0);
			}else {
				res.setLeftAmount(list.get(0).getLeftAmount());
			}
			
		}else {
			res.setLeftAmount(0);
		}
	}
	
	public void userProductLimitAdd(ReqMsg_UserProductLimit_UserProductLimitAdd req,ResMsg_UserProductLimit_UserProductLimitAdd res) {
		userProductLimitService.userProductLimitAdd(req,res);
	}
}
