package com.pinting.business.facade.site;

import com.pinting.business.hessian.site.message.*;
import com.pinting.business.model.BsAccountJnl;
import com.pinting.business.model.BsSubAccount;
import com.pinting.business.model.BsSysConfig;
import com.pinting.business.model.vo.BsSubAccountVO;
import com.pinting.business.service.site.AccountService;
import com.pinting.business.service.site.SubAccountService;
import com.pinting.business.service.site.SysConfigService;
import com.pinting.business.util.Constants;
import com.pinting.core.util.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @Project: business
 * @Title: AccountFacade.java
 * @author Dong Wenkai
 * @date 2015-1-21 下午6:56:55
 * @Copyright: 2015 BiGangWan.com Inc. All rights reserved.
 */
@Component("Account")
public class AccountFacade{
	@Autowired
	private AccountService accountService;
	@Autowired
	private SubAccountService subAccountService;
	@Autowired
	private SysConfigService sysConfigService;
	
	public void accountJnlListQuery(ReqMsg_Account_AccountJnlListQuery req, ResMsg_Account_AccountJnlListQuery resp) {
		List<BsAccountJnl> bsAccountJnlList=accountService.findAccountJnlByUserId(req.getUserId(), req.getPageIndex(), req.getPageSize());
		ArrayList<HashMap<String, Object>> accountJnls=null;
		if(bsAccountJnlList != null && bsAccountJnlList.size() > 0){
			accountJnls=new ArrayList<HashMap<String,Object>>();
			for (BsAccountJnl bsAccountJnl : bsAccountJnlList) {
				HashMap<String, Object> mapAccountJnl=new HashMap<String, Object>();
				/**
				 * 以下循环：
				 * transTime	交易时间	必填	Date
				 * transName		交易名称		必填	Double
				 * transAmount		交易金额		可选	String
				 * afterAvialableBlance		剩余金额		可选	String
				 * cdFlag1		借贷		1代表借 2代表贷
				 */
				mapAccountJnl.put("transTime", bsAccountJnl.getTransTime());
				mapAccountJnl.put("transName", bsAccountJnl.getTransName());
				mapAccountJnl.put("transAmount", bsAccountJnl.getTransAmount());
				mapAccountJnl.put("afterAvialableBlance", bsAccountJnl.getAfterAvialableBalance1());
				mapAccountJnl.put("cdFlag1", bsAccountJnl.getCdFlag1());
				accountJnls.add(mapAccountJnl);
			}
		}
		int pageSize=req.getPageSize();
		resp.setTotalCount((int)Math.ceil((double)accountService.findAccountJnlCountByUserId(req.getUserId())/pageSize));
		resp.setTransPrincipals(accountJnls);
		resp.setPageIndex(req.getPageIndex());
		
	}
	
	/**
	 * 根据主键id查询subAccount
	 * @param req 入参
	 * @param resp 出参
	 */
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

			//赞分期新旧协议时间区分标志
			BsSysConfig configUser = sysConfigService.findConfigByKey(Constants.ZAN_AGREEMENT_DATE_4_NEW);
			if(configUser != null) {
				String zanAgreementDate = configUser.getConfValue();
				boolean zanAgreementFlag = false;
				if(DateUtil.getDiffeDay(subAccount.getInterestBeginDate(), DateUtil.parseDate(zanAgreementDate)) < 0) {
					zanAgreementFlag = false;
				}else {
					zanAgreementFlag = true;
				}
				resp.setZanAgreementDate(zanAgreementFlag);

			}

		}

		//根据id查询该用户子账户数据-存管产品-出借金额
		BsSubAccount authSubAccount = subAccountService.queryAuthAccountById(req.getId());
		if(authSubAccount != null) {
			resp.setOpenBalance(authSubAccount.getOpenBalance());
		}
		
		Integer userId = subAccountService.selectUserIdBySubAccountId(req.getId());
		if (userId != null) {
			resp.setUserId(userId);
		}
	}

	/**
	 * 根据子账户编号，查询产品购买成功后的加息收益金额
	 * @param req
	 * @param res
     */
	public void interestAmountQuery(ReqMsg_Account_InterestAmountQuery req, ResMsg_Account_InterestAmountQuery res) {
		Double interestAmount = subAccountService.queryInterestAmountBySubAccountId(req.getId());
		if(interestAmount != null) {
			res.setInterestAmount(interestAmount);
		}else {
			res.setInterestAmount(0d);
		}
	}

	/**
	 * 审核SubAccountId是否属于该用户
	 */
	public void checkUserIdSubAccountId(ReqMsg_Account_CheckUserIdSubAccountId req,ResMsg_Account_CheckUserIdSubAccountId res){
		
		BsSubAccountVO subAccount = subAccountService.findMyInvestByUserIdAndId(req.getUserId(), req.getSubAccountId());
		if(subAccount == null){
			res.setSubAccountId(-1);
		}else{
			res.setSubAccountId(subAccount.getId());
		}
	}
	
	
}
