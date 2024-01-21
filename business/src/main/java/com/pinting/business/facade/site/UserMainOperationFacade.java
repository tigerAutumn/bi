package com.pinting.business.facade.site;

import java.util.Date;
import java.util.List;

import com.pinting.core.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.pinting.business.hessian.site.message.ReqMsg_UserMainOperation_UserMainOperationAdd;
import com.pinting.business.hessian.site.message.ResMsg_UserMainOperation_UserMainOperationAdd;
import com.pinting.business.model.BsUser;
import com.pinting.business.model.BsUserMainOperation;
import com.pinting.business.service.manage.BsUserService;
import com.pinting.business.service.site.UserMainOperationService;
import com.pinting.business.service.site.UserService;

/**
 * hessian通信处理业务逻辑
 *
 * @Project: business
 * @author yanwl
 * @Title: UserMainOperationFacade.java
 * @date 2016-3-8 下午2:58:55
 * @Copyright: 2016 bigangwan.com Inc. All rights reserved.
 */
@Component("UserMainOperation")
public class UserMainOperationFacade {
	@Autowired
	private BsUserService userService;
	@Autowired
	private UserMainOperationService userMainOperationService;
    @Autowired
    private UserService       uService;
	
	public void userMainOperationAdd(ReqMsg_UserMainOperation_UserMainOperationAdd req,ResMsg_UserMainOperation_UserMainOperationAdd res) {
		if(null != req.getUserId()) {
			BsUser user = userService.findUserByUserId(req.getUserId());
			if(user != null) {
				BsUserMainOperation userMainOperation = new BsUserMainOperation();
				userMainOperation.setCreateTime(new Date());
				userMainOperation.setReferer(req.getReferer());
				userMainOperation.setSrcAgent(req.getSrcAgent());
				userMainOperation.setSrcIp(req.getSrcIp());
				userMainOperation.setUrl(req.getUrl());
				userMainOperation.setUserId(req.getUserId());
				userMainOperation.setUserMobile(user.getMobile());

				userMainOperationService.saveUserMainOperation(userMainOperation);
			}
		} else if(StringUtil.isNotBlank(req.getMobile())) {
			BsUser bsUser = uService.findUserByMobile(req.getMobile());
			if(null == bsUser) {
				bsUser = uService.findUserByNick(req.getMobile());
			}
			if(null != bsUser) {
				BsUserMainOperation userMainOperation = new BsUserMainOperation();
				userMainOperation.setCreateTime(new Date());
				userMainOperation.setReferer(req.getReferer());
				userMainOperation.setSrcAgent(req.getSrcAgent());
				userMainOperation.setSrcIp(req.getSrcIp());
				userMainOperation.setUrl(req.getUrl());
				userMainOperation.setUserId(bsUser.getId());
				userMainOperation.setUserMobile(bsUser.getMobile());
				userMainOperationService.saveUserMainOperation(userMainOperation);
			}
		}
	}
}
