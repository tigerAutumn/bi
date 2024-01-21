package com.pinting.business.facade.site;

import com.pinting.business.accounting.finance.service.DepUserBonusWithdrawService;
import com.pinting.business.accounting.finance.service.UserBonusExtractService;
import com.pinting.business.accounting.service.UserWithdrawService;
import com.pinting.business.dao.BsSpecialJnlMapper;
import com.pinting.business.hessian.site.message.*;
import com.pinting.business.model.*;
import com.pinting.business.service.manage.BsSysConfigService;
import com.pinting.business.service.site.*;
import com.pinting.business.util.BusinessMoneyUtil;
import com.pinting.business.util.Constants;
import com.pinting.core.util.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @Project: business
 * @Title: BonusFacade.java
 * @author Dong Wenkai
 * @date 2015-1-21 下午6:44:02
 * @Copyright: 2015 BiGangWan.com Inc. All rights reserved.
 */
@Component("Bonus")
public class BonusFacade{
	@Autowired
	private BonusService bonusService;
	@Autowired
	private UserWithdrawService userWithdrawService;
	@Autowired
	private UserBonusExtractService bonusToJSHService;
	@Autowired
	private BsSpecialJnlMapper bsSpecialJnlMapper;
	@Autowired
	private SubAccountService subAccountService;
	@Autowired
	private BankCardService bankCardService;
	@Autowired
	private UserService userService;
	@Autowired
	private SysConfigService sysConfigService;
	@Autowired
	private DepUserBonusWithdrawService depUserBonusWithdrawService;
	@Autowired
	private OrderService orderService;

	/**
	 * 获取用户奖励金列表
	 * @param req 
	 * @param resp
	 */
	public void recommendBonusListQuery(ReqMsg_Bonus_RecommendBonusListQuery req, ResMsg_Bonus_RecommendBonusListQuery resp) {
		//是否可提现标志
		boolean withdrawFlag = req.getWithdrawFlag() != null && Constants.CAN_WITHDRAW_BONUS.equals(req.getWithdrawFlag()) ? true : false;
		
		List<BsDailyBonus> bsDailyBonusList=bonusService.findDailyBonusByUserId(req.getUserId(), req.getPageIndex(), req.getPageSize(), withdrawFlag);
		ArrayList<HashMap<String, Object>> bonuss=null;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		if(bsDailyBonusList != null && bsDailyBonusList.size() > 0){
			bonuss=new ArrayList<HashMap<String,Object>>();
			for (BsDailyBonus bsDailyBonus : bsDailyBonusList) {
				HashMap<String, Object> mapBonus=new HashMap<String, Object>();
				mapBonus.put("earningsDate", bsDailyBonus.getTime());
				mapBonus.put("amount", bsDailyBonus.getBonus());
				mapBonus.put("bonusType", bsDailyBonus.getType());
				mapBonus.put("note", bsDailyBonus.getNote());
				mapBonus.put("useTime", bsDailyBonus.getUseTime());
				mapBonus.put("ticketApr", bsDailyBonus.getTicketApr());
				mapBonus.put("productName", bsDailyBonus.getProductName());
				if (bsDailyBonus.getUseTime() != null && !"".equals(bsDailyBonus.getUseTime())) {
					String useTime  = sdf.format(bsDailyBonus.getUseTime());
					mapBonus.put("detail", useTime+" 使用"+BusinessMoneyUtil.format2Point(bsDailyBonus.getTicketApr())+"%加息券，加入"+bsDailyBonus.getProductName()+"，加息收益已入奖励金账户。");
				}
				bonuss.add(mapBonus);
			}
		}
//		BsSpecialJnl special = bsSpecialJnlMapper.selectBonusByUserId(req.getUserId());
		Double amount = orderService.sumPayingAmountByTransType(req.getUserId(), Constants.TRANS_BONUS_WITHDRAW, Constants.ORDER_STATUS_PAYING);
		if(amount == null || amount <= 0d){
			resp.setHaveSpecial("false");
		}else{
			resp.setHaveSpecial("true");
			resp.setSpecialBonusAmount(amount);
		}
		
		Integer userId = req.getUserId();
		/*Double amount = req.getAmount(); */
		/*BsUserAssetVO bsUserAsset = userService.findUserAssetByUserId(req.getUserId());
	    HashMap<String, Object> userAsset = BeanUtils.transBeanMap(bsUserAsset);
	    if (userAsset != null && userAsset.size() > 0) {
	    	resp.setBonus((Double) userAsset.get("currentBonus"));
	    } else {
            throw new PTMessageException(PTMessageEnum.NO_DATA_FOUND);
        }*/
		
		BsSubAccount jljAccount = subAccountService.findJLJSubAccountByUserId(req.getUserId());//用户子账户奖励金户
		if(jljAccount != null){
			resp.setBonus((Double)jljAccount.getCanWithdraw());
		}
		
		int pageSize=req.getPageSize();
		resp.setTotalCount((int)Math.ceil((double)bonusService.findDailyBonusCountByUserId(req.getUserId(), withdrawFlag)/pageSize));
		resp.setBonuss(bonuss);
		resp.setPageIndex(req.getPageIndex());
		
	}
	
	/**
	 * 用户奖励金列表查询
	 * @param req
	 * @param resp
	 */
	public void bonusListQuery(ReqMsg_Bonus_BonusListQuery req, ResMsg_Bonus_BonusListQuery resp) {
		//是否可提现标志
		boolean withdrawFlag = req.getWithdrawFlag() != null && Constants.CAN_WITHDRAW_BONUS.equals(req.getWithdrawFlag()) ? true : false;
		
		List<BsDailyBonus> bsDailyBonusList=bonusService.findDailyBonusByUserId(req.getUserId(), req.getPageIndex(), req.getPageSize(), withdrawFlag);
		ArrayList<HashMap<String, Object>> bonuss=null;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		if(bsDailyBonusList != null && bsDailyBonusList.size() > 0){
			bonuss=new ArrayList<HashMap<String,Object>>();
			for (BsDailyBonus bsDailyBonus : bsDailyBonusList) {
				HashMap<String, Object> mapBonus=new HashMap<String, Object>();
				mapBonus.put("earningsDate", bsDailyBonus.getTime());
				mapBonus.put("amount", bsDailyBonus.getBonus());
				mapBonus.put("bonusType", bsDailyBonus.getType());
				mapBonus.put("note", bsDailyBonus.getNote());
				mapBonus.put("useTime", bsDailyBonus.getUseTime());
				mapBonus.put("ticketApr", bsDailyBonus.getTicketApr());
				mapBonus.put("productName", bsDailyBonus.getProductName());
				if (bsDailyBonus.getUseTime() != null && !"".equals(bsDailyBonus.getUseTime())) {
					String useTime  = sdf.format(bsDailyBonus.getUseTime());
					mapBonus.put("detail", useTime+" 使用"+BusinessMoneyUtil.format2Point(bsDailyBonus.getTicketApr())+"%加息券，加入"+bsDailyBonus.getProductName()+"，加息收益已入奖励金账户。");
				}
				bonuss.add(mapBonus);
			}
		}
		
		int pageSize=req.getPageSize();
		resp.setTotalCount((int)Math.ceil((double)bonusService.findDailyBonusCountByUserId(req.getUserId(), withdrawFlag)/pageSize));
		resp.setBonuss(bonuss);
		resp.setPageIndex(req.getPageIndex());
	}
	
	public void bonusWithdraw(ReqMsg_Bonus_BonusWithdraw req, ResMsg_Bonus_BonusWithdraw resp){
		Integer userId = req.getUserId();
		Double amount = req.getAmount();
		String pass = req.getPayPassword();
		//提现申请
		userWithdrawService.userWithdraw(userId, amount, pass);
	}
	
	/**
	 * 奖励金转余额
	 * @param req
	 * @param res
	 */
	public void bonusToJSH(ReqMsg_Bonus_BonusToJSH req, ResMsg_Bonus_BonusToJSH res){
		Integer userId = req.getUserId();
		/*Double amount = req.getAmount(); */
		BsSubAccount suAcccount = subAccountService.findJLJSubAccountByUserId(userId);
		/*BsUserAssetVO bsUserAsset = userService.findUserAssetByUserId(req.getUserId());
	    HashMap<String, Object> userAsset = BeanUtils.transBeanMap(bsUserAsset);*/
	    if (suAcccount != null) {
	    	if(suAcccount.getCanWithdraw() <= 0){
	    		res.setFlag(false);
	            res.setResMsg("未抱歉，暂无可提现的奖励金");
	    	}else{
	    		//奖励金转账户余额，具体操作，查看service
		    	res.setFlag(bonusToJSHService.transBonusToJSH(userId, (Double) suAcccount.getCanWithdraw()));
	    	}
	    	
	    } else {
           res.setFlag(false);
           res.setResMsg("未获取到用户信息，请重试");
        }
		
	}

	/**
	 * 奖励金提现页面信息
	 * @param req
	 * @param res
     */
	public void withdrawPageInfo(ReqMsg_Bonus_WithdrawPageInfo req, ResMsg_Bonus_WithdrawPageInfo res) {
		BsBankCard bankCard = bankCardService.findFirstBankCardByUserId(req.getUserId());
		if(bankCard != null && bankCard.getBankId() != null) {
			BsBank bank = bankCardService.findBankById(bankCard.getBankId());
			if(bank != null) {
				res.setLargeLogo(bank.getLargeLogo());
				res.setSmallLogo(bank.getSmallLogo());
			}
			res.setBankId(bankCard.getBankId());
			res.setBankName(bankCard.getBankName());
			res.setCardNo(bankCard.getCardNo());
			res.setCardId(bankCard.getId());
		}

		//查询用户账户余额
		BsSubAccount sub = subAccountService.findJLJSubAccountByUserId(req.getUserId());
		res.setCan_withdraw(sub.getCanWithdraw());

		DateUtil.firstDayOfMonth();
		//查询最小提现金额
		BsSysConfig config = sysConfigService.findConfigByKey(Constants.BONUS_WITHDRAW_LIMIT);
		res.setWithdrawLimit(config.getConfValue());
	}

	public void withdraw(ReqMsg_Bonus_Withdraw req, ResMsg_Bonus_Withdraw res){
		//提现申请
		depUserBonusWithdrawService.userBonusWithdraw(req.getUserId(), req.getAmount(), req.getPassword(), req.getTerminalType());
	}

}
