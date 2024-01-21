package com.pinting.business.dayend.task;

import com.pinting.business.accounting.finance.service.DepUserBonusGrantService;
import com.pinting.business.accounting.finance.service.impl.process.DepUserBonusGrant4BuyProcess;
import com.pinting.business.accounting.loan.enums.PartnerEnum;
import com.pinting.business.accounting.loan.model.BaseAccount;
import com.pinting.business.accounting.loan.service.LoanAccountService;
import com.pinting.business.accounting.loan.service.LoanRelationshipService;
import com.pinting.business.dao.*;
import com.pinting.business.model.*;
import com.pinting.business.service.manage.BsSubAccountService;
import com.pinting.business.service.site.SubAccountService;
import com.pinting.business.service.site.SysConfigService;
import com.pinting.business.util.Constants;
import com.pinting.core.util.DateUtil;
import com.pinting.core.util.MoneyUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;
/**
 * 站岗资金自动转出到余额 
 * 每天检查有哪些站岗户的站岗时间到期，分别把这些站岗户资金转到用户结算户余额里面
 * 向用户发起通知
 * 
 * @author Dragon & cat
 * @date 2016-8-26
 */
@Service
public class AuthAccountTurnToBalanceTask {
	private Logger log = LoggerFactory.getLogger(AuthAccountTurnToBalanceTask.class);
	@Autowired
	private SysConfigService sysConfigService;
	@Autowired
	private BsSubAccountMapper bsSubAccountMapper;
	@Autowired
	private BsAccountMapper bsAccountMapper;
	@Autowired
	private BsStandbyReturnMapper bsStandbyReturnMapper;
	@Autowired
	private BsUserTransDetailMapper bsUserTransDetailMapper;
	@Autowired
	private LoanAccountService loanAccountService;
	@Autowired
	private BsSubAccountService bsSubAccountService;
	@Autowired
	private LoanRelationshipService loanRelationshipService;
	@Autowired
	private BsUserMapper bsUserMapper;
	@Autowired
	private DepUserBonusGrantService depUserBonusGrantService;
	@Autowired
	private LnLoanRelationMapper lnLoanRelationMapper;
	@Autowired
	private SubAccountService subAccountService;
	@Autowired
	private LnCreditTransferMapper lnCreditTransferMapper;
	/**
	 * 任务执行
	 */
	public void execute() {
		// 定时任务【站岗资金自动转出到余额 】
		try {
			auth2jsh();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		//定时任务【赞分期产品站岗时间到期，发放奖励金】
		try {
			userBonusGrant4Zan();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	
	private void userBonusGrant4Zan() {
		log.info("==================定时任务【赞分期产品站岗时间到期，发放奖励金】开始====================");
		//只结算站岗到期的奖励金
		BsSysConfig config = sysConfigService.findConfigByKey(Constants.DAY_4_WAIT_LOAN);
		Date date=new Date();//取时间 
	    Calendar  calendar = new  GregorianCalendar(); 
	    calendar.setTime(date); 
	    calendar.add(calendar.DATE,Integer.parseInt("-"+config.getConfValue()));
	    date=calendar.getTime();
	    Map<String, Object> paramMap = new HashMap<String, Object>();
	    
	    
	    //判断用户的起息日在此时间及之后，否则，不发放奖励金
		Date diffDate = DateUtil.parseDate(Constants.ZAN_BONUSGRANT_DIFFERENT_DATE);
		if(date.compareTo(diffDate) >= 0){
			paramMap.put("interestBeginDate", date);
		    //VIP理财人列表
		    List<Integer> superUserList = loanRelationshipService.getSuperUserList();
		    paramMap.put("userIdList", superUserList);
		    List<Map<String, Object>> needPay = lnLoanRelationMapper.selectNeedPayBonusGrant(paramMap);
			if(needPay!=null && needPay.size()>0){
				for (Map<String, Object> map : needPay) {
					 DepUserBonusGrant4BuyProcess process = new DepUserBonusGrant4BuyProcess();
					 process.setUserBonusGrantService(depUserBonusGrantService);
					 process.setAmount((Double) map.get("amount"));
					 process.setBonusGrantType(depUserBonusGrantService.getBonusGrantTypeByUserId((Integer) map.get("user_id")));
					 process.setReferrerUserId((Integer) map.get("recommend_id"));
					 process.setSelfUserId((Integer) map.get("user_id"));
					 process.setSubAccountId((Integer) map.get("regd_id"));
					 process.setPropertySymbol(Constants.PRODUCT_PROPERTY_SYMBOL_ZAN);
					 new Thread(process).start();
				}
			}
		}
		log.info("==================定时任务【赞分期产品站岗时间到期，发放奖励金】结束====================");
	}
	
	// 定时任务【站岗资金自动转出到余额 】
	private void auth2jsh() {
		log.info("==================定时任务【站岗资金自动转出到余额 】开始====================");
		BsSysConfig config = sysConfigService.findConfigByKey(Constants.DAY_4_WAIT_LOAN);
		Date date=new Date();//取时间 
	    Calendar  calendar = new  GregorianCalendar(); 
	    calendar.setTime(date); 
	    calendar.add(calendar.DATE,Integer.parseInt("-"+config.getConfValue()));
	    date=calendar.getTime(); 
	    //VIP理财人列表
	    List<Integer> superUserList = loanRelationshipService.getSuperUserList();
	    
	    List<Integer> accounts = new ArrayList<Integer>();
	    BsAccountExample example = new BsAccountExample();
	    example.createCriteria().andUserIdIn(superUserList);
	    List<BsAccount> accountIds = bsAccountMapper.selectByExample(example);
	    for (BsAccount bsAccount : accountIds) {
	    	accounts.add(bsAccount.getId());
		}
		try {
			//查询站岗资金户到期的子账户
			BsSubAccountExample bsSubAccountExample = new BsSubAccountExample();
			bsSubAccountExample.createCriteria().andProductTypeEqualTo(Constants.PRODUCT_TYPE_AUTH).andStatusEqualTo(Constants.SUBACCOUNT_STATUS_FINANCING).andAccountIdNotIn(accounts).andInterestBeginDateLessThanOrEqualTo(date).andAvailableBalanceGreaterThan(0.0);
			List<BsSubAccount> subAccountList = bsSubAccountMapper.selectByExample(bsSubAccountExample);
			
			if (!CollectionUtils.isEmpty(subAccountList)) {
				for (BsSubAccount bsSubAccount : subAccountList) {
					if (bsSubAccount.getAvailableBalance()>0) {	
							//站岗户结算户 并记账
							try {
								BsAccount bsAccount =  bsAccountMapper.selectByPrimaryKey(bsSubAccount.getAccountId());
								BaseAccount baseAccount = new BaseAccount();
								baseAccount.setPartner(PartnerEnum.ZAN);
								baseAccount.setInvestorUserId(bsAccount.getUserId());
								baseAccount.setAmount(bsSubAccount.getAvailableBalance());
								loanAccountService.chargeAuthActBack(baseAccount, bsSubAccount.getId());
								
								BsUser user = bsUserMapper.selectByPrimaryKey(bsAccount.getUserId());
								
								//用户可提现余额更新
								BsUser bsUser = new BsUser();
								bsUser.setId(bsAccount.getUserId());
								bsUser.setCanWithdraw(MoneyUtil.add(user.getCanWithdraw(), bsSubAccount.getAvailableBalance()).setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue());
								bsUserMapper.updateByPrimaryKeySelective(bsUser);
								
								//记录站岗户转余额登记表bs_standby_return
								BsStandbyReturn bsStandbyReturn = new BsStandbyReturn();
								bsStandbyReturn.setUserId(bsAccount.getUserId());
								bsStandbyReturn.setAuthSubAccountId(bsSubAccount.getId());
								bsStandbyReturn.setAmount(bsSubAccount.getAvailableBalance());
								bsStandbyReturn.setOutTime(new Date());
								bsStandbyReturn.setCreateTime(new Date());
								bsStandbyReturn.setUpdateTime(new Date());
								bsStandbyReturnMapper.insertSelective(bsStandbyReturn);
								
								//记录流水表bs_user_trans_detail
								BsUserTransDetail userTransDetail = new BsUserTransDetail();
								userTransDetail.setUserId(bsAccount.getUserId());
								userTransDetail.setCardNo(null);
								userTransDetail.setTransType(Constants.Trans_TYPE_AUTH_ACCOUNT_TURN_TO_BALANCE);
								userTransDetail.setTransStatus(Constants.Trans_STATUS_SUCCESS);
								userTransDetail.setTransDesc(null);
								userTransDetail.setOrderNo(null);
								userTransDetail.setReturnCode(null);
								userTransDetail.setReturnMsg(null);
								userTransDetail.setNote(null);
								userTransDetail.setCreateTime(new Date());
								userTransDetail.setUpdateTime(new Date());
								userTransDetail.setAmount(bsSubAccount.getAvailableBalance());
								bsUserTransDetailMapper.insertSelective(userTransDetail);
								
							
								//微信消息通知
								
								//查询对应的REG_D户
								BsSubAccount regdBsSubAccountt =  subAccountService.findRegDSubAccountByAuthId(bsSubAccount.getId());
								//查询债转信息表
								LnCreditTransferExample lnCreditTransferExample = new LnCreditTransferExample();
								lnCreditTransferExample.createCriteria().andInSubAccountIdEqualTo(regdBsSubAccountt.getId());
								List<LnCreditTransfer> lnCreditTransferList = lnCreditTransferMapper.selectByExample(lnCreditTransferExample);
								
								Double inAmountTotal = 0d;
								Double amountTotal = 0d;
								
								for (LnCreditTransfer lnCreditTransfer : lnCreditTransferList) {
									inAmountTotal = MoneyUtil.add(inAmountTotal, lnCreditTransfer.getInAmount()).doubleValue();
									amountTotal = MoneyUtil.add(amountTotal, lnCreditTransfer.getAmount()).doubleValue();
								} 
								
								Double transferAmount = MoneyUtil.defaultRound(inAmountTotal - amountTotal).doubleValue();
								
								List<Integer> userIds = new ArrayList<Integer>();
								userIds.add(bsAccount.getUserId());
								
								try {
									Date openTime = bsSubAccount.getOpenTime();
									SimpleDateFormat dateFmt= new SimpleDateFormat("yyyy-MM-dd");
									String timeString = dateFmt.format(openTime);
									//微信消息通知
									bsSubAccountService.chargeAuthActBackMessage(2, null, userIds, String.valueOf(bsSubAccount.getOpenBalance()), MoneyUtil.format(MoneyUtil.subtract(bsSubAccount.getOpenBalance(), bsSubAccount.getAvailableBalance()).setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue()),null,null, String.valueOf(bsSubAccount.getProductId()), String.valueOf(bsSubAccount.getId()), timeString);
									//APP消息通知
									bsSubAccountService.chargeAuthActBackMessage(3, null, userIds, String.valueOf(bsSubAccount.getOpenBalance()), MoneyUtil.format(MoneyUtil.subtract(bsSubAccount.getOpenBalance(), bsSubAccount.getAvailableBalance()).setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue()),String.valueOf(amountTotal),String.valueOf(transferAmount), String.valueOf(bsSubAccount.getProductId()), String.valueOf(bsSubAccount.getId()), timeString);
								}catch (Exception e) {
									log.error("==================定时任务【站岗资金自动转出到余额 】推送消息:"+ bsSubAccount.getId()+"推送消息失败====================", e);
									continue;
								}
							} catch (Exception e) {
								log.error("==================定时任务【站岗资金自动转出到余额 】失败:"+ bsSubAccount.getId()+"记账失败====================", e);
								continue;
							}
	

					}

				}
			}
			
			log.info("==================定时任务【站岗资金自动转出到余额 】结束====================");
		} catch (Exception e) {
			log.error("==================定时任务【站岗资金自动转出到余额 】失败====================", e);
		}
		
	}
}
