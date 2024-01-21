package com.pinting.gateway.in.facade.mobile;

import java.util.Date;
import java.util.Map;

import com.pinting.business.model.vo.BonusWithdrawVO;
import com.pinting.business.service.site.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.pinting.business.accounting.finance.service.DepUserBonusWithdrawService;
import com.pinting.business.dao.BsSysConfigMapper;
import com.pinting.business.hessian.site.message.ReqMsg_UserWithdraw_CanWithdrawTime;
import com.pinting.business.hessian.site.message.ReqMsg_UserWithdraw_MyBonusWithdraw;
import com.pinting.business.hessian.site.message.ReqMsg_UserWithdraw_MyBonusWithdrawInfo;
import com.pinting.business.hessian.site.message.ResMsg_UserWithdraw_CanWithdrawTime;
import com.pinting.business.hessian.site.message.ResMsg_UserWithdraw_MyBonusWithdraw;
import com.pinting.business.hessian.site.message.ResMsg_UserWithdraw_MyBonusWithdrawInfo;
import com.pinting.business.model.BsBank;
import com.pinting.business.model.BsBankCard;
import com.pinting.business.model.BsSubAccount;
import com.pinting.business.model.BsSysConfig;
import com.pinting.business.service.manage.BsSysConfigService;
import com.pinting.business.util.Constants;
import com.pinting.core.util.DateUtil;
import com.pinting.gateway.in.util.InterfaceVersion;

@Component("appUserWithdraw")
public class WithdrawFacade {
	@Autowired
	private BsSysConfigMapper bsSysConfigMapper;
    @Autowired
    private AssetsService assetsService;
	@Autowired
	private BsSysConfigService bsSysConfigService;
    @Autowired
    private BankCardService      bankCardService;
    @Autowired
    private UserService          userService;
    @Autowired
    private SysConfigService sysConfigService;
    @Autowired
    private OrderService orderService;
    @Autowired
    private	DepUserBonusWithdrawService depUserBonusWithdrawService;
	@Autowired
	private SubAccountService subAccountService;
	
	@InterfaceVersion("CanWithdrawTime/1.0.0")
	public void canWithdrawTime(ReqMsg_UserWithdraw_CanWithdrawTime req,ResMsg_UserWithdraw_CanWithdrawTime res){
		BsSysConfig bsSysConfigB = bsSysConfigMapper.selectByPrimaryKey(Constants.WITHDRAW_TIME_BEGIN);
		BsSysConfig bsSysConfigE = bsSysConfigMapper.selectByPrimaryKey(Constants.WITHDRAW_TIME_END);
		res.setBegin(bsSysConfigB.getConfValue());
		res.setEnd(bsSysConfigE.getConfValue());
		String[] b = bsSysConfigB.getConfValue().split(":");
		String[] e = bsSysConfigE.getConfValue().split(":");
		String begin = DateUtil.formatYYYYMMDD(new Date())+" "+bsSysConfigB.getConfValue();
		String end = DateUtil.formatYYYYMMDD(new Date())+" "+bsSysConfigE.getConfValue();
		if(b.length == 2){
			begin = DateUtil.formatYYYYMMDD(new Date())+" "+bsSysConfigB.getConfValue()+":00";	
		}
		if(e.length == 2){
			end = DateUtil.formatYYYYMMDD(new Date())+" "+bsSysConfigE.getConfValue()+":00";
		}
		res.setBeginTime(begin);
		res.setEndTime(end);
		
	}
	
	
	
	
	@InterfaceVersion("MyBonusWithdrawInfo/1.0.0")
	public void myBonusWithdrawInfo(ReqMsg_UserWithdraw_MyBonusWithdrawInfo req,ResMsg_UserWithdraw_MyBonusWithdrawInfo resp){

    	BsBankCard bankCard = bankCardService.findFirstBankCardByUserId(req.getUserId());
    	if(bankCard != null && bankCard.getBankId() != null) {
    		BsBank bank = bankCardService.findBankById(bankCard.getBankId());
    		if(bank != null) {
    			resp.setLargeLogo(bank.getLargeLogo());
            	resp.setSmallLogo(bank.getSmallLogo());
    		}
    		
    		resp.setBankName(bankCard.getBankName());
    		resp.setCardNo(bankCard.getCardNo());
    		resp.setCardId(bankCard.getId());
    	}
		
    	
    	//查询当日提现限额和用户已提现金额
		BsSysConfig dayUpperLimitConfig = bsSysConfigService.findKey(Constants.DAY_WITHDRAW_UPPER_LIMIT);
		Double dayUpperLimit = Double.parseDouble(dayUpperLimitConfig.getConfValue());
		Double amount = orderService.sumWithdrawUpperLimit(req.getUserId());
		
		resp.setDayWithdrawUpperLimit(dayUpperLimit);
		resp.setUserWithdrawAmount(amount);
		//查询单笔提现上限
    	BsSysConfig bsSysConfigWithdrawLimit = sysConfigService.findConfigByKey(Constants.SINGLE_WITHDRAW_UPPER_LIMIT);
    	resp.setSingleWithdrawUpperLimit(Double.parseDouble(bsSysConfigWithdrawLimit.getConfValue()));
    
		//与购买无关，查询最小提现金额
    	BsSysConfig bsSysConfig2 = sysConfigService.findConfigByKey(Constants.BONUS_WITHDRAW_LIMIT);
    	resp.setLimitWithdraw(Double.valueOf(bsSysConfig2.getConfValue()));
    	
    	//查询奖励金账户余额
		BsSubAccount sub = userService.selectSubAccount(req.getUserId(), Constants.PRODUCT_TYPE_JLJ, Constants.SUBACCOUNT_STATUS_OPEN).get(0);
		resp.setTotalAmount(Double.valueOf(sub.getCanWithdraw()));
		
		//账户余额
		BsSubAccount jlj = subAccountService.findJLJSubAccountByUserId(req.getUserId());
		resp.setCanWithdraw(jlj.getCanWithdraw());
	}
	
	
	
	@InterfaceVersion("MyBonusWithdraw/1.0.0")
	public void myBonusWithdraw(ReqMsg_UserWithdraw_MyBonusWithdraw req,ResMsg_UserWithdraw_MyBonusWithdraw resp){
		BonusWithdrawVO result = depUserBonusWithdrawService.userBonusWithdraw(req.getUserId(), req.getBonusAmount(), req.getPayPassword(), req.getTerminalType());
		resp.setWithdrawTime(result.getTime());
		resp.setOrderNo(result.getOrderNo());
	}
	

}
