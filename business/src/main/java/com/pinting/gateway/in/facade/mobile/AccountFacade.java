package com.pinting.gateway.in.facade.mobile;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.pinting.business.hessian.site.message.ReqMsg_Account_SubAccountById;
import com.pinting.business.hessian.site.message.ResMsg_Account_SubAccountById;
import com.pinting.business.model.BsSubAccount;
import com.pinting.business.service.site.SubAccountService;
import com.pinting.gateway.in.util.InterfaceVersion;

/**
 * @Project: business
 * @Title: AccountFacade.java
 * @author Dong Wenkai
 * @date 2015-1-21 下午6:56:55
 * @Copyright: 2015 BiGangWan.com Inc. All rights reserved.
 */
@Component("appAccount")
public class AccountFacade{
	@Autowired
	private SubAccountService subAccountService;
	
	@InterfaceVersion("SubAccountById/1.0.0")
	public void subAccountById(ReqMsg_Account_SubAccountById req,ResMsg_Account_SubAccountById resp){
		BsSubAccount subAccount = subAccountService.findSubAccountById(req.getId());
		if(subAccount != null){
			resp.setAccountId(subAccount.getAccountId());
			resp.setAccumulationInerest(subAccount.getAccumulationInerest());
			resp.setAvailableBalance(subAccount.getAvailableBalance());
			resp.setBalance(subAccount.getBalance());
			resp.setBankCard(subAccount.getBankCard());
			resp.setCanWithdraw(subAccount.getCanWithdraw());
			resp.setCheckStatus(subAccount.getCheckStatus());
			resp.setCloseTime(subAccount.getCloseTime());
			resp.setCode(subAccount.getCode());
			resp.setExtraRate(subAccount.getExtraRate());
			resp.setFreezeBalance(subAccount.getFreezeBalance());
			resp.setId(subAccount.getId());
			resp.setInterestBeginDate(subAccount.getInterestBeginDate());
			resp.setLastCalInterestDate(subAccount.getLastCalInterestDate());
			resp.setLastFinishInterestDate(subAccount.getLastFinishInterestDate());
			resp.setLastTransDate(subAccount.getLastTransDate());
			resp.setNote(subAccount.getNote());
			resp.setOpenBalance(subAccount.getOpenBalance());
			resp.setOpenTime(subAccount.getOpenTime());
			resp.setProductCode(subAccount.getProductCode());
			resp.setProductId(subAccount.getProductId());
			resp.setProductName(subAccount.getProductName());
			resp.setProductRate(subAccount.getProductRate());
			resp.setProductType(subAccount.getProductType());
			resp.setStatus(subAccount.getStatus());
			resp.setTransStatus(subAccount.getTransStatus());
			resp.setTransferTime(subAccount.getTransferTime());
		}
	}
	
}
