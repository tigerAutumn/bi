package com.pinting.gateway.in.facade.mobile;

import com.pinting.business.hessian.site.message.ReqMsg_UserMainOperation_UserMainOperationAdd;
import com.pinting.business.hessian.site.message.ResMsg_UserMainOperation_UserMainOperationAdd;
import com.pinting.business.model.BsUser;
import com.pinting.business.model.BsUserMainOperation;
import com.pinting.business.service.manage.BsUserService;
import com.pinting.business.service.site.UserMainOperationService;
import com.pinting.business.service.site.UserService;
import com.pinting.core.util.StringUtil;
import com.pinting.gateway.in.util.InterfaceVersion;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

/**
 * hessian通信处理业务逻辑
 *
 * @Project: business
 * @author yanwl
 * @Title: UserMainOperationFacade.java
 * @date 2016-3-8 下午2:58:55
 * @Copyright: 2016 bigangwan.com Inc. All rights reserved.
 */
@Component("appUserMainOperation")
public class UserMainOperationFacade {
	@Autowired
	private BsUserService userService;
	@Autowired
	private UserMainOperationService userMainOperationService;
    @Autowired
    private UserService       uService;

	private Logger logger = LoggerFactory.getLogger(UserMainOperationFacade.class);

	@InterfaceVersion("UserMainOperationAdd/1.0.0")
	public void userMainOperationAdd(ReqMsg_UserMainOperation_UserMainOperationAdd req,ResMsg_UserMainOperation_UserMainOperationAdd res) {
		logger.info("APP币港湾用户核心业务拦截（登录、修改密码、绑卡、充值、提现、购买、奖励金转余额），请求参数：{}", JSONObject.fromObject(req));

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
		logger.info("APP币港湾用户核心业务拦截（登录、修改密码、绑卡、充值、提现、购买、奖励金转余额），结束：{}", JSONObject.fromObject(req));
	}
}
