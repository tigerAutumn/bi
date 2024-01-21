package com.pinting.gateway.in.facade;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.pinting.business.enums.PTMessageEnum;
import com.pinting.business.exception.PTMessageException;
import com.pinting.business.model.BsUser;
import com.pinting.business.model.DafyUserExt;
import com.pinting.business.service.manage.BsSpecialJnlService;
import com.pinting.business.service.site.UserService;
import com.pinting.business.util.Constants;
import com.pinting.gateway.in.service.DafyUserService;
import com.pinting.gateway.in.util.DafyInConstant;
import com.pinting.gateway.hessian.message.dafy.G2BReqMsg_DafyRegister_RealCertificateResult;
import com.pinting.gateway.hessian.message.dafy.G2BResMsg_DafyRegister_RealCertificateResult;

/**
 * 客户达飞实名认证相关处理类
 * 
 * @Project: business
 * @Title: DafyRegisterFacade.java
 * @author dingpf
 * @date 2015-2-13 下午12:15:19
 * @Copyright: 2015 BiGangWan.com Inc. All rights reserved.
 */
@Component("DafyRegister")
public class DafyRegisterFacade {

	@Autowired
	private DafyUserService dafyUserService;
	@Autowired
	private UserService userService;
	@Autowired
	private BsSpecialJnlService bsSpecialJnlService;
	
	private Logger log = LoggerFactory.getLogger(DafyRegisterFacade.class);
	
	public void realCertificateResult(G2BReqMsg_DafyRegister_RealCertificateResult req,
			G2BResMsg_DafyRegister_RealCertificateResult res) {
		List<String> customerIdList = req.getCustomerIdList();
		String result = req.getResult();
		if(DafyInConstant.DAFY_RESULT_NOTICE_SUCCESS.equals(result)){
			for (String customerId : customerIdList) {//更新成功的客户
				try {
					DafyUserExt dafyUser = dafyUserService.findDafyUserByCustomerId(customerId);
					if(dafyUser != null ){
						//根据达飞客户号，更新用户注册信息
						BsUser user = new BsUser();
						user.setId(dafyUser.getUserId());
						user.setIsBindName(Constants.IS_BIND_NAME_YES);
						userService.updateBsUser(user);
					}else{
						throw new PTMessageException(PTMessageEnum.DAFY_REGIST_USER_NOT_EXIST);
					}
				} catch (Exception e) {
					log.error("=============达飞实名认证通知调用失败,customerId = " + customerId);
					bsSpecialJnlService.addSpecialJnl("【达飞实名认证通知】", "达飞客户号为:" + customerId + "的用户实名认证通知失败" );
				}
			}
			
		}
	}
}
