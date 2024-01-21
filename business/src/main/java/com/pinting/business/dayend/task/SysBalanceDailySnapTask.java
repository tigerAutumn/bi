package com.pinting.business.dayend.task;

import com.alibaba.fastjson.JSON;
import com.pinting.business.accounting.loan.enums.HFAccountTypeEnum;
import com.pinting.business.accounting.loan.enums.LoanStatus;
import com.pinting.business.dao.*;
import com.pinting.business.enums.BAOFOOAccountType;
import com.pinting.business.enums.BGWHFAccountType;
import com.pinting.business.model.*;
import com.pinting.business.model.vo.HFBalanceDetailVO;
import com.pinting.business.model.vo.LoanBalanceVO;
import com.pinting.business.model.vo.SysBalanceDailySnapVO;
import com.pinting.business.service.manage.BsSysConfigService;
import com.pinting.business.service.site.SpecialJnlService;
import com.pinting.business.util.CalculatorUtil;
import com.pinting.business.util.Constants;
import com.pinting.core.util.ConstantUtil;
import com.pinting.core.util.DateUtil;
import com.pinting.core.util.MoneyUtil;
import com.pinting.core.util.StringUtil;
import com.pinting.gateway.hessian.message.hfbank.B2GReqMsg_HFBank_QueryAccountLeftAmountInfo;
import com.pinting.gateway.hessian.message.hfbank.B2GResMsg_HFBank_QueryAccountLeftAmountInfo;
import com.pinting.gateway.out.service.HfbankTransportService;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;
/**
 * 系统余额快照记录表
 * @author bianyatian
 * @2017-6-8 上午11:35:43
 */
@Service
public class SysBalanceDailySnapTask {
	private Logger logger = LoggerFactory.getLogger(SysBalanceDailySnapTask.class);
	@Autowired
	private HfbankTransportService hfbankTransportService;
	@Autowired
	private BsSysBalanceDailySnapMapper bsSysBalanceDailySnapMapper;
	@Autowired
	private BsSysSubAccountMapper bsSysSubAccountMapper;
	@Autowired
    private SpecialJnlService specialJnlService;
	@Autowired
	private BsSubAccountMapper bsSubAccountMapper;
	@Autowired
	private BsRedPacketInfoMapper redPacketInfoMapper;
	@Autowired
	private LnBadDetailMapper lnBadDetailMapper;
	@Autowired
	private LnLoanMapper lnLoanMapper;
	@Autowired
	private LnSubAccountMapper lnSubAccountMapper;
	@Autowired
	private BsSysConfigService bsSysConfigService;
	
	public void execute() {
		Date today = DateUtil.parseDate(DateUtil.formatYYYYMMDD(new Date()));
		//恒丰账户查询和存储
		for (HFAccountTypeEnum enums : HFAccountTypeEnum.values()) {
			try {
				saveHfAccSnap(enums, today);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		try {
			//币港湾自有子账户
			saveBGWAccAnap(Constants.SYS_ACCOUNT_DEP_JSH, today);
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			//币港湾红包子账户
			saveBGWAccAnap(Constants.SYS_ACCOUNT_DEP_REDPACKET, today);
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			//币港湾对赞分期营收子账户
			saveBGWAccAnap(Constants.SYS_ACCOUNT_DEP_BGW_REVENUE_ZAN, today);
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			//币港湾对云贷营收子账户
			saveBGWAccAnap(Constants.SYS_ACCOUNT_DEP_BGW_REVENUE_YUN, today);
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			//币港湾砍头息子账户
			saveBGWAccAnap(Constants.SYS_ACCOUNT_DEP_HEADFEE_YUN, today);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		try {
			//币港湾对赞时贷营收子账户
			saveBGWAccAnap(Constants.SYS_ACCOUNT_DEP_BGW_REVENUE_ZSD, today);
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			//币港湾对赞时贷砍头息子账户
			saveBGWAccAnap(Constants.SYS_ACCOUNT_DEP_HEADFEE_ZSD, today);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		try {
			//币港湾对7贷营收子账户
			saveBGWAccAnap(Constants.SYS_ACCOUNT_DEP_BGW_REVENUE_7, today);
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			//币港湾对7贷砍头息子账户
			saveBGWAccAnap(Constants.SYS_ACCOUNT_DEP_HEADFEE_7, today);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		try {
			//币港湾其他费用户
			saveBGWAccAnap(Constants.SYS_ACCOUNT_DEP_OTHER_FEE, today);
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			//币港湾垫付金子账户
			saveBGWAccAnap(Constants.SYS_ACCOUNT_DEP_ADVANCE, today);
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			//借款人余额
			saveLoanBalanceSnap(Constants.SYS_ACCOUNT_LN_USER_BALANCE, today);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		//============================管理台日终账务查询需求和对账S=============================================
		// 币港湾账户存储(不包含云贷产品站岗红包)
		for (BGWHFAccountType enums : BGWHFAccountType.values()) {
			try {
				saveBGWHFAccSnap(enums, today);
			} catch (Exception e) {
				logger.error("币港湾余额快照存储异常", e);
			}
		}
		
		// 云贷产品站岗红包
		try {
			saveBGWRedAccSnap(Constants.SYS_ACC_RED_YUN, today);
		} catch (Exception e) {
			logger.error("币港湾云贷产品站岗红包余额快照存储异常", e);
		}
		// 赞时贷产品站岗红包
		try {
			saveBGWRedAccSnap(Constants.SYS_ACC_RED_ZSD, today);
		} catch (Exception e) {
			logger.error("币港湾云贷产品站岗红包余额快照存储异常", e);
		}
		// 7贷产品站岗红包
		try {
			saveBGWRedAccSnap(Constants.SYS_ACC_RED_7, today);
		} catch (Exception e) {
			logger.error("币港湾云贷产品站岗红包余额快照存储异常", e);
		}

		// 自由产品站岗红包-币港湾恒丰账户
		try {
			saveBGWRedAccSnap(Constants.SYS_ACC_RED_FREE, today);
		} catch (Exception e) {
			logger.error("币港湾自有产品站岗红包余额快照存储异常", e);
		}

		try {
			//自由站岗户余额-币港湾恒丰账户
			saveBGWRedAccSnap(Constants.SYS_ACC_AUTH_FREE, today);
		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			//自由产品户余额-币港湾恒丰账户
			saveBGWHFAccSnap(Constants.SYS_ACC_BGW_AUTH_FREE, today);
		} catch (Exception e) {
			e.printStackTrace();
		}

		// 宝付账户存储(不包含用户利息冻结余额、赞分期坏账户余额、红包已支出、用户未提奖励金)
		for (BAOFOOAccountType enums : BAOFOOAccountType.values()) {
			try {
				saveBaoFooAccSnap(enums, today);
			} catch (Exception e) {
				logger.error("币港湾余额快照存储异常", e);
			}
		}
		// 用户利息冻结余额
		try {
			saveBaoFooOtherAccSnap(Constants.SYS_ACC_USER_INTEREST, today);
		} catch (Exception e) {
			logger.error("用户利息冻结余额余额快照存储异常", e);
		}
		// 赞分期坏账户余额
		try {
			saveBaoFooOtherAccSnap(Constants.SYS_ACC_BAD_LOANS_ZAN, today);
		} catch (Exception e) {
			logger.error("赞分期坏账户余额余额快照存储异常", e);
		}
		// 红包已支出
		try {
			saveBaoFooOtherAccSnap(Constants.SYS_ACC_THD_REDPACKET, today);
		} catch (Exception e) {
			logger.error("红包已支出余额快照存储异常", e);
		}
		// 用户未提奖励金
		try {
			saveBaoFooOtherAccSnap(Constants.PRODUCT_TYPE_JLJ, today);
		} catch (Exception e) {
			logger.error("用户未提奖励金余额快照存储异常", e);
		}

		// 自有子账户对账
		try {
			doSysJshCheck(today);
		} catch (Exception e) {
			logger.error("doSysJshCheck异常", e);
		}
		
		// 手续费子账户对账
		try {
			doSysFeeCheck(today);
		} catch (Exception e) {
			logger.error("doSysFeeCheck异常", e);
		}
		
		// 抵用金子账户对账
		try {
			doSysRedCheck(today);
		} catch (Exception e) {
			logger.error("doSysRedCheck异常", e);
		}
		
		// 垫付金子账户对账
		try {
			doSysAdvanceCheck(today);
		} catch (Exception e) {
			logger.error("doSysAdvanceCheck异常", e);
		}
		//============================管理台日终账务查询需求和对账E=============================================

		// 系统账户余额短信告警
		try {
			jshBalanceAlarmMessage();
		} catch (Exception e) {
			logger.error("jshBalanceAlarmMessage异常", e);
		}

	}

	/**
	 * 系统账户余额短信告警
	 * 检查系统子账户bs_sys_sub_account表中，宝付系统结算户（JSH）的可用余额available_balance，是否小于系统配置表
	 * bs_sys_config中JSH_BALANCE_ALARM_VALUE宝付系统结算户余额告警阈值，如果小于给财务发送告警短信，反之不发送；
	 */
	private void jshBalanceAlarmMessage() {
		BsSysSubAccountExample ssaExample = new BsSysSubAccountExample();
		ssaExample.createCriteria().andCodeEqualTo(Constants.SYS_ACCOUNT_JSH);
		List<BsSysSubAccount> ssaList = bsSysSubAccountMapper.selectByExample(ssaExample);
		Double alarmValue = 500000d;
		if(CollectionUtils.isNotEmpty(ssaList)) {
			BsSysConfig sysConfig = bsSysConfigService.findKey(Constants.JSH_BALANCE_ALARM_VALUE);
			if(sysConfig != null) {
				alarmValue = Double.valueOf(sysConfig.getConfValue());
			}
			if(ssaList.get(0).getAvailableBalance() < alarmValue) {
				specialJnlService.warnAppoint4Fail(ssaList.get(0).getAvailableBalance(),
						"宝付账户系统账户余额已不足" + MoneyUtil.format(alarmValue) + "元，请及时充值", "",
						"宝付系统结算户余额不足", false, Constants.FINANCE_MOBILE);
			}

		}
	}

	
	/**
	 * 自有子账户对账
	 * @param today
	 */
	private void doSysJshCheck(Date today) {
		// 恒丰自有子账户
		BsSysBalanceDailySnapExample hfExample = new BsSysBalanceDailySnapExample();
		hfExample.createCriteria().andCodeEqualTo(HFAccountTypeEnum.HF_ACC_TYPE_JSH.getCode()).andSnapDateEqualTo(today);
		List<BsSysBalanceDailySnap> hfList = bsSysBalanceDailySnapMapper.selectByExample(hfExample);

		// 恒丰自有子账户余额小于10000元，余额告警
		if(!CollectionUtils.isEmpty(hfList)) {
			if(hfList.get(0).getBalance() < 10000) {
				specialJnlService.warnAppoint4Fail(hfList.get(0).getBalance(),
						"恒丰自有子账户余额为：" + hfList.get(0).getBalance() + "元小于10000.0元，可能影响理财人到期退出，请及时充值", "",
						"恒丰自有子账户余额不足", false, Constants.PRODUCT_OPERATOR_MOBILE, Constants.FINANCE_MOBILE);
			}
		}

		// 币港湾自有子账户
		BsSysBalanceDailySnapExample bgwExample = new BsSysBalanceDailySnapExample();
		bgwExample.createCriteria().andCodeEqualTo(Constants.SYS_ACCOUNT_DEP_JSH).andSnapDateEqualTo(today);
		List<BsSysBalanceDailySnap> bgwList = bsSysBalanceDailySnapMapper.selectByExample(bgwExample);
		if(!CollectionUtils.isEmpty(hfList) && !CollectionUtils.isEmpty(bgwList)){
			if (hfList.get(0).getBalance().compareTo(bgwList.get(0).getBalance()) != 0) {
				//告警
				Double diffAmount = MoneyUtil.subtract(hfList.get(0).getBalance(), bgwList.get(0).getBalance()).doubleValue();
				String detail = "平台自有子账户对账不一致：差值["+ diffAmount + "] = " + "恒丰自有子账户[" + hfList.get(0).getBalance() + "] - " +
				"币港湾自有子账户[" + bgwList.get(0).getBalance() + "]";
				specialJnlService.warn4Fail(diffAmount, detail, null, "平台自有子账户对账不一致", false);
			}
		} 
	}
	
	/**
	 * 手续费子账户对账
	 * @param today
	 */
	private void doSysFeeCheck(Date today) {
		// 恒丰手续费子账户
		String[] hfFee = new String[] {HFAccountTypeEnum.HF_ACC_TYPE_FEE.getCode(), HFAccountTypeEnum.HF_ACC_TYPE_FEE_TRANSIT.getCode()};
		Double hfBalance = 0d;
		for (int i = 0; i < hfFee.length; i++) {
			BsSysBalanceDailySnapExample hfExample = new BsSysBalanceDailySnapExample();
			hfExample.createCriteria().andCodeEqualTo(hfFee[i]).andSnapDateEqualTo(today);
			List<BsSysBalanceDailySnap> hfList = bsSysBalanceDailySnapMapper.selectByExample(hfExample);
			if (!CollectionUtils.isEmpty(hfList)) {				
				hfBalance = MoneyUtil.add(hfBalance, hfList.get(0).getBalance()).doubleValue();
			}
		}
		// 币港湾手续费子账户
		String[] bgwFee = new String[] {Constants.SYS_ACCOUNT_DEP_BGW_REVENUE_ZAN, Constants.SYS_ACCOUNT_DEP_BGW_REVENUE_YUN,
				Constants.SYS_ACCOUNT_DEP_BGW_REVENUE_ZSD, Constants.SYS_ACCOUNT_DEP_BGW_REVENUE_7,
				Constants.SYS_ACCOUNT_DEP_HEADFEE_YUN, Constants.SYS_ACCOUNT_DEP_HEADFEE_ZSD, 
				Constants.SYS_ACCOUNT_DEP_HEADFEE_7, Constants.SYS_ACCOUNT_DEP_OTHER_FEE};
		Double bgwBalance = 0d;
		Map<String, Object> feeMap = new HashMap<>();
		for (int i = 0; i < bgwFee.length; i++) {			
			BsSysBalanceDailySnapExample bgwExample = new BsSysBalanceDailySnapExample();
			bgwExample.createCriteria().andCodeEqualTo(bgwFee[i]).andSnapDateEqualTo(today);
			List<BsSysBalanceDailySnap> bgwList = bsSysBalanceDailySnapMapper.selectByExample(bgwExample);
			if (!CollectionUtils.isEmpty(bgwList)) {
				bgwBalance = MoneyUtil.add(bgwBalance, bgwList.get(0).getBalance()).doubleValue();
				feeMap.put(bgwFee[i], bgwList.get(0).getBalance());
			}
		}
		// 币港湾手续费匹配中的砍头息
		Double bgwHeadFee = lnLoanMapper.selectHeadFeeSum(LoanStatus.LOAN_STATUS_PAYING.getCode(), null, null);
		bgwBalance = CalculatorUtil.calculate("a+a", bgwBalance, bgwHeadFee);
		if (hfBalance.compareTo(bgwBalance) != 0) {
			//告警
			Double diffAmount = MoneyUtil.subtract(hfBalance, bgwBalance).doubleValue();
			String detail = "平台手续费子账户对账不一致：差值["+ diffAmount + "]。 " + "对账公式：币港湾对赞分期营收子账户[" + (Double)feeMap.get(Constants.SYS_ACCOUNT_DEP_BGW_REVENUE_ZAN) + "] + "
					+ "币港湾对云贷营收子账户[" + (Double)feeMap.get(Constants.SYS_ACCOUNT_DEP_BGW_REVENUE_YUN) + "] + "
					+ "币港湾对赞时贷营收子账户[" + (Double)feeMap.get(Constants.SYS_ACCOUNT_DEP_BGW_REVENUE_ZSD) + "] + "
					+ "币港湾对7贷营收子账户[" + (Double)feeMap.get(Constants.SYS_ACCOUNT_DEP_BGW_REVENUE_7) + "] + "
					+ "云贷砍头息子账户[" + (Double)feeMap.get(Constants.SYS_ACCOUNT_DEP_HEADFEE_YUN) + "] + "
					+ "赞时贷砍头息子账户[" + (Double)feeMap.get(Constants.SYS_ACCOUNT_DEP_HEADFEE_ZSD) + "] + "
					+ "7贷砍头息子账户[" + (Double)feeMap.get(Constants.SYS_ACCOUNT_DEP_HEADFEE_7) + "] + "
					+ "其他费用户[" + (Double)feeMap.get(Constants.SYS_ACCOUNT_DEP_OTHER_FEE) + "]"
					+ " = 币港湾手续费账户[" + bgwBalance + "]";
			specialJnlService.warn4Fail(diffAmount, detail, null, "平台手续费子账户对账不一致", false);
		}
		
	}
	
	/**
	 * 抵用金子账户对账
	 * @param today
	 */
	private void doSysRedCheck(Date today) {
		// 恒丰抵用金子账户
		BsSysBalanceDailySnapExample hfExample = new BsSysBalanceDailySnapExample();
		hfExample.createCriteria().andCodeEqualTo(HFAccountTypeEnum.HF_ACC_TYPE_COUPON.getCode()).andSnapDateEqualTo(today);
		List<BsSysBalanceDailySnap> hfList = bsSysBalanceDailySnapMapper.selectByExample(hfExample);
		// 币港湾抵用金子账户
		BsSysBalanceDailySnapExample bgwExample = new BsSysBalanceDailySnapExample();
		bgwExample.createCriteria().andCodeEqualTo(Constants.SYS_ACCOUNT_DEP_REDPACKET).andSnapDateEqualTo(today);
		List<BsSysBalanceDailySnap> bgwList = bsSysBalanceDailySnapMapper.selectByExample(bgwExample);
		// 币港湾站岗户红包金额
		BsSysBalanceDailySnapExample bgwRedExample = new BsSysBalanceDailySnapExample();
		bgwRedExample.createCriteria().andCodeIn(Arrays.asList(new String[]{Constants.SYS_ACC_RED_YUN, Constants.SYS_ACC_RED_ZSD, Constants.SYS_ACC_RED_7}))
			.andAccountTypeEqualTo(Constants.SYS_SNAP_ACC_TYPE_CW_HF).andSnapDateEqualTo(today);
		List<BsSysBalanceDailySnap> bgwRedList = bsSysBalanceDailySnapMapper.selectByExample(bgwRedExample);
		Double bgwRedBalance = 0d;
		if(!CollectionUtils.isEmpty(hfList) && !CollectionUtils.isEmpty(bgwList) && !CollectionUtils.isEmpty(bgwRedList)){
			Double bgwRedFreezeAmount = CalculatorUtil.calculate("a+a+a", bgwRedList.get(0).getFreezeBalance(), bgwRedList.get(1).getFreezeBalance(), bgwRedList.get(2).getFreezeBalance());
			logger.info("币港湾站岗户红包金额(匹配中):" + bgwRedFreezeAmount);
			bgwRedBalance = CalculatorUtil.calculate("a-a", bgwList.get(0).getBalance(), bgwRedFreezeAmount);  
			if (hfList.get(0).getBalance().compareTo(bgwRedBalance) != 0) {
				//告警
				Double diffAmount = MoneyUtil.subtract(hfList.get(0).getBalance(), bgwRedBalance).doubleValue();
				String detail = "平台抵用金子账户对账不一致：差值["+ diffAmount + "]。 " + "对账公式：恒丰抵用金账户[" + hfList.get(0).getBalance() + "] = " +
						"币港湾红包户余额[" + bgwList.get(0).getBalance() + "] - " + "云贷匹配中红包[" + bgwRedList.get(0).getFreezeBalance() + "] - " + 
						"赞时贷匹配中红包[" + bgwRedList.get(1).getFreezeBalance() + "] - " + 
						"7贷匹配中红包[" + bgwRedList.get(2).getFreezeBalance() + "]" ;
				specialJnlService.warn4Fail(diffAmount, detail, null, "平台抵用金子账户对账不一致", false);
			}
		} 
	}
	
	/**
	 * 垫付金子账户对账
	 * @param today
	 */
	private void doSysAdvanceCheck(Date today) {
		// 恒丰垫付金账户
		String[] hfAdvance = new String[] {HFAccountTypeEnum.HF_ACC_TYPE_HF_ADVANCE.getCode(), HFAccountTypeEnum.HF_ACC_TYPE_ADVANCE_TRANSIT.getCode()};
		Double hfBalance = 0d;
		for (int i = 0; i < hfAdvance.length; i++) {
			BsSysBalanceDailySnapExample hfExample = new BsSysBalanceDailySnapExample();
			hfExample.createCriteria().andCodeEqualTo(hfAdvance[i]).andSnapDateEqualTo(today);
			List<BsSysBalanceDailySnap> hfList = bsSysBalanceDailySnapMapper.selectByExample(hfExample);
			if (!CollectionUtils.isEmpty(hfList)) {				
				hfBalance = MoneyUtil.add(hfBalance, hfList.get(0).getBalance()).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
			}
		}
		// 币港湾垫付金账户
		BsSysBalanceDailySnapExample bgwExample = new BsSysBalanceDailySnapExample();
		bgwExample.createCriteria().andCodeEqualTo(Constants.SYS_ACCOUNT_DEP_ADVANCE).andSnapDateEqualTo(today);
		List<BsSysBalanceDailySnap> bgwList = bsSysBalanceDailySnapMapper.selectByExample(bgwExample);
		if(!CollectionUtils.isEmpty(bgwList)){
			if (hfBalance.compareTo(bgwList.get(0).getBalance()) != 0) {
				//告警
				Double diffAmount = MoneyUtil.subtract(hfBalance, bgwList.get(0).getBalance()).doubleValue();
				String detail = "平台垫付金子账户对账不一致：差值["+ diffAmount + "] = " + "恒丰垫付金账户[" + hfBalance + "] - " +
					"币港湾垫付金账户[" + bgwList.get(0).getBalance() + "]";
				specialJnlService.warn4Fail(diffAmount, detail, null, "平台垫付金子账户对账不一致", false);
			}
		}
	}
	
	/**
	 * 
	 * @param code
	 * @param today
	 */
	private void saveBGWAccAnap(String code, Date today) {
		try {
			BsSysBalanceDailySnapExample example = new BsSysBalanceDailySnapExample();
			example.createCriteria().andCodeEqualTo(code).andSnapDateEqualTo(today);
			List<BsSysBalanceDailySnap> list = bsSysBalanceDailySnapMapper.selectByExample(example);
			if(CollectionUtils.isEmpty(list)){
				BsSysSubAccount tempAct = bsSysSubAccountMapper.selectByCode(code);
				if(tempAct != null){
					BsSysBalanceDailySnap sysBalanceDailySnap = new BsSysBalanceDailySnap();
					sysBalanceDailySnap.setAccountType(Constants.SYS_SNAP_ACC_TYPE_BGW);
					sysBalanceDailySnap.setAvailableBalance(tempAct.getAvailableBalance());
					sysBalanceDailySnap.setBalance(tempAct.getBalance());
					sysBalanceDailySnap.setFreezeBalance(tempAct.getFreezeBalance());
					sysBalanceDailySnap.setCode(code);
					sysBalanceDailySnap.setSnapDate(today);
					sysBalanceDailySnap.setUpdateTime(new Date());
					sysBalanceDailySnap.setCreateTime(new Date());
					bsSysBalanceDailySnapMapper.insertSelective(sysBalanceDailySnap);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}	
	}
	
	/**
	 * 币港湾恒丰余额快照存储
	 * @param code
	 * @param today
     */
	private void saveBGWHFAccSnap(String code, Date today) {
		try {
			BsSysBalanceDailySnapExample example = new BsSysBalanceDailySnapExample();
			example.createCriteria().andCodeEqualTo(code).andSnapDateEqualTo(today);
			List<BsSysBalanceDailySnap> list = bsSysBalanceDailySnapMapper.selectByExample(example);
			if(CollectionUtils.isEmpty(list)){
				BsSysSubAccount tempAct = bsSysSubAccountMapper.selectByCode(code);
				if(tempAct != null) {
					BsSysBalanceDailySnap sysBalanceDailySnap = new BsSysBalanceDailySnap();
					sysBalanceDailySnap.setAccountType(Constants.SYS_SNAP_ACC_TYPE_CW_HF);
					sysBalanceDailySnap.setAvailableBalance(tempAct.getAvailableBalance());
					sysBalanceDailySnap.setBalance(tempAct.getBalance());
					sysBalanceDailySnap.setFreezeBalance(tempAct.getFreezeBalance());
					sysBalanceDailySnap.setCode(code);
					sysBalanceDailySnap.setSnapDate(today);
					sysBalanceDailySnap.setUpdateTime(new Date());
					sysBalanceDailySnap.setCreateTime(new Date());
					bsSysBalanceDailySnapMapper.insertSelective(sysBalanceDailySnap);
				}
			}
		} catch (Exception e) {
			logger.error("币港湾恒丰余额快照存储异常", e);
		}
	}

	private void saveBGWHFAccSnap(BGWHFAccountType bgwAccType, Date today) {
		try {
			BsSysBalanceDailySnapExample example = new BsSysBalanceDailySnapExample();
			example.createCriteria().andCodeEqualTo(bgwAccType.getCode()).andSnapDateEqualTo(today);
			List<BsSysBalanceDailySnap> list = bsSysBalanceDailySnapMapper.selectByExample(example);
			if(CollectionUtils.isEmpty(list)){
				SysBalanceDailySnapVO vo = null;
				BsSysSubAccount tempAct = null;
				// 云贷、赞分期、赞时贷、7贷站岗户余额查询bs_sub_account表的站岗户
				if(BGWHFAccountType.ACC_TYPE_BGW_AUTH_YUN.getCode().equals(bgwAccType.getCode())) {
					vo = bsSubAccountMapper.selectSumAuthYunBalance();
				}else if(BGWHFAccountType.ACC_TYPE_BGW_AUTH_ZAN.getCode().equals(bgwAccType.getCode())) {
					vo = bsSubAccountMapper.selectBgwAuthZanBalance();
				}else if(BGWHFAccountType.ACC_TYPE_BGW_AUTH_ZSD.getCode().equals(bgwAccType.getCode())) {
					vo = bsSubAccountMapper.selectSumAuthZsdBalance();
				}else if(BGWHFAccountType.ACC_TYPE_BGW_AUTH_7.getCode().equals(bgwAccType.getCode())) {
					vo = bsSubAccountMapper.selectSumAuthSevenBalance();
				}else {
					tempAct = bsSysSubAccountMapper.selectByCode(bgwAccType.getCode());
				}

				if(BGWHFAccountType.ACC_TYPE_BGW_AUTH_ZSD.getCode().equals(bgwAccType.getCode())) {
					BsSysBalanceDailySnap sysBalanceDailySnap = new BsSysBalanceDailySnap();
					sysBalanceDailySnap.setAccountType(Constants.SYS_SNAP_ACC_TYPE_CW_HF);
					sysBalanceDailySnap.setAvailableBalance(0d);
					sysBalanceDailySnap.setBalance(0d);
					sysBalanceDailySnap.setFreezeBalance(0d);
					sysBalanceDailySnap.setCode(bgwAccType.getCode());
					sysBalanceDailySnap.setSnapDate(today);
					sysBalanceDailySnap.setUpdateTime(new Date());
					sysBalanceDailySnap.setCreateTime(new Date());
					bsSysBalanceDailySnapMapper.insertSelective(sysBalanceDailySnap);
				}else {
					if(vo != null) {
						BsSysBalanceDailySnap sysBalanceDailySnap = new BsSysBalanceDailySnap();
						sysBalanceDailySnap.setAccountType(Constants.SYS_SNAP_ACC_TYPE_CW_HF);
						sysBalanceDailySnap.setAvailableBalance(vo.getAvailableBalance());
						sysBalanceDailySnap.setBalance(vo.getBalance());
						sysBalanceDailySnap.setFreezeBalance(vo.getFreezeBalance());
						sysBalanceDailySnap.setCode(bgwAccType.getCode());
						sysBalanceDailySnap.setSnapDate(today);
						sysBalanceDailySnap.setUpdateTime(new Date());
						sysBalanceDailySnap.setCreateTime(new Date());
						bsSysBalanceDailySnapMapper.insertSelective(sysBalanceDailySnap);
					}
				}

				if(tempAct != null) {
					BsSysBalanceDailySnap sysBalanceDailySnap = new BsSysBalanceDailySnap();
					sysBalanceDailySnap.setAccountType(Constants.SYS_SNAP_ACC_TYPE_CW_HF);
					sysBalanceDailySnap.setAvailableBalance(tempAct.getAvailableBalance());
					sysBalanceDailySnap.setBalance(tempAct.getBalance());
					sysBalanceDailySnap.setFreezeBalance(tempAct.getFreezeBalance());
					sysBalanceDailySnap.setCode(bgwAccType.getCode());
					sysBalanceDailySnap.setSnapDate(today);
					sysBalanceDailySnap.setUpdateTime(new Date());
					sysBalanceDailySnap.setCreateTime(new Date());
					bsSysBalanceDailySnapMapper.insertSelective(sysBalanceDailySnap);
				}
			}
		} catch (Exception e) {
			logger.error("币港湾恒丰余额快照存储异常", e);
		}
	}
	
	/**
	 * 云贷产品站岗红包余额快照存储
	 * @param code
	 * @param today
     */
	private void saveBGWRedAccSnap(String code, Date today) {
		try {
			BsSysBalanceDailySnapExample example = new BsSysBalanceDailySnapExample();
			example.createCriteria().andCodeEqualTo(code).andSnapDateEqualTo(today);
			List<BsSysBalanceDailySnap> list = bsSysBalanceDailySnapMapper.selectByExample(example);
			if(CollectionUtils.isEmpty(list)){
				Double availableBalance = 0d;
				Double freezeBalance = 0d;
				if (Constants.SYS_ACC_RED_YUN.equals(code)) {
					availableBalance = bsSubAccountMapper.countRedAccBalanceByType("RED");
					freezeBalance = bsSubAccountMapper.countRedAccFreezeBalanceByType("RED");
				} else {					
					availableBalance = bsSubAccountMapper.countRedAccBalanceByType(code);
					freezeBalance = bsSubAccountMapper.countRedAccFreezeBalanceByType(code);
				}
				BsSysBalanceDailySnap sysBalanceDailySnap = fillSysBalanceDailySnap(Constants.SYS_SNAP_ACC_TYPE_CW_HF, code, today);
				sysBalanceDailySnap.setBalance(MoneyUtil.add(availableBalance, freezeBalance)
						.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());
				sysBalanceDailySnap.setAvailableBalance(availableBalance);
				sysBalanceDailySnap.setFreezeBalance(freezeBalance);
				bsSysBalanceDailySnapMapper.insertSelective(sysBalanceDailySnap);
			}
		} catch (Exception e) {
			logger.error("币港湾云贷产品站岗红包余额快照存储异常", e);
		}
	}
	
	/**
	 * 宝付余额快照存储
	 * @param baofooAccType
	 * @param today
     */
	private void saveBaoFooAccSnap(BAOFOOAccountType baofooAccType, Date today) {
		try {
			BsSysBalanceDailySnapExample example = new BsSysBalanceDailySnapExample();
			example.createCriteria().andCodeEqualTo(baofooAccType.getCode()).andSnapDateEqualTo(today);
			List<BsSysBalanceDailySnap> list = bsSysBalanceDailySnapMapper.selectByExample(example);
			if(CollectionUtils.isEmpty(list)){
				BsSysSubAccount tempAct = bsSysSubAccountMapper.selectByCode(baofooAccType.getCode());
				if(tempAct != null) {
					BsSysBalanceDailySnap sysBalanceDailySnap = new BsSysBalanceDailySnap();
					sysBalanceDailySnap.setAccountType(Constants.SYS_SNAP_ACC_TYPE_CW_BAOFOO);
					sysBalanceDailySnap.setAvailableBalance(tempAct.getAvailableBalance());
					sysBalanceDailySnap.setBalance(tempAct.getBalance());
					sysBalanceDailySnap.setFreezeBalance(tempAct.getFreezeBalance());
					sysBalanceDailySnap.setCode(baofooAccType.getCode());
					sysBalanceDailySnap.setSnapDate(today);
					sysBalanceDailySnap.setUpdateTime(new Date());
					sysBalanceDailySnap.setCreateTime(new Date());
					bsSysBalanceDailySnapMapper.insertSelective(sysBalanceDailySnap);
				}
			}
		} catch (Exception e) {
			logger.error("宝付余额快照存储异常", e);
		}
	}
	
	private void saveBaoFooOtherAccSnap(String code, Date today) {
		try {
			BsSysBalanceDailySnapExample example = new BsSysBalanceDailySnapExample();
			example.createCriteria().andCodeEqualTo(code).andSnapDateEqualTo(today);
			List<BsSysBalanceDailySnap> list = bsSysBalanceDailySnapMapper.selectByExample(example);
			if(CollectionUtils.isEmpty(list)){
				BsSysBalanceDailySnap sysBalanceDailySnap = fillSysBalanceDailySnap(Constants.SYS_SNAP_ACC_TYPE_CW_BAOFOO, code, today);
				switch (code) {
				case Constants.PRODUCT_TYPE_JLJ:
					sysBalanceDailySnap.setBalance(bsSubAccountMapper.countBonusAccBalance());
					sysBalanceDailySnap.setAvailableBalance(0d);
					sysBalanceDailySnap.setFreezeBalance(0d);
					break;
				case Constants.SYS_ACC_THD_REDPACKET:
					sysBalanceDailySnap.setBalance(redPacketInfoMapper.selectUsedRedPaktAmount());
					sysBalanceDailySnap.setAvailableBalance(0d);
					sysBalanceDailySnap.setFreezeBalance(0d);
					break;
				case Constants.SYS_ACC_USER_INTEREST:
					BsSysSubAccount tempAct = bsSysSubAccountMapper.selectByCode(Constants.SYS_ACCOUNT_JSH);
					sysBalanceDailySnap.setBalance(tempAct==null? 0d:tempAct.getFreezeBalance());
					sysBalanceDailySnap.setAvailableBalance(0d);
					sysBalanceDailySnap.setFreezeBalance(tempAct==null? 0d:tempAct.getFreezeBalance());
					break;
				case Constants.SYS_ACC_BAD_LOANS_ZAN:
					sysBalanceDailySnap.setBalance(lnBadDetailMapper.selectBadloansZanAmount());
					sysBalanceDailySnap.setAvailableBalance(0d);
					sysBalanceDailySnap.setFreezeBalance(0d);
					break;
				default:
					sysBalanceDailySnap.setBalance(0d);
					sysBalanceDailySnap.setAvailableBalance(0d);
					sysBalanceDailySnap.setFreezeBalance(0d);
					break;
				}
				bsSysBalanceDailySnapMapper.insertSelective(sysBalanceDailySnap);
			}
		} catch (Exception e) {
			logger.error("宝付其他账户余额快照存储异常", e);
		}
	}
	
	/**
	 * 填充余额快照信息
	 * @return
	 */
	private BsSysBalanceDailySnap fillSysBalanceDailySnap(String accType, String code, Date today) {
		BsSysBalanceDailySnap sysBalanceDailySnap = new BsSysBalanceDailySnap();
		sysBalanceDailySnap.setAccountType(accType);
		sysBalanceDailySnap.setCode(code);
		sysBalanceDailySnap.setSnapDate(today);
		sysBalanceDailySnap.setUpdateTime(new Date());
		sysBalanceDailySnap.setCreateTime(new Date());
		return sysBalanceDailySnap;
	}
	
	/**
	 * 恒丰账户存储
	 * 根据日期和类型查询数据，不存在则调恒丰接口，查询数据，校验存储
	 * @param hfAccTypeEnum
	 * @param today
	 */
	private void saveHfAccSnap(HFAccountTypeEnum hfAccTypeEnum, Date today) {
		try {
			BsSysBalanceDailySnapExample example = new BsSysBalanceDailySnapExample();
			example.createCriteria().andCodeEqualTo(hfAccTypeEnum.getCode())
				.andSnapDateEqualTo(today);
			List<BsSysBalanceDailySnap> list = bsSysBalanceDailySnapMapper.selectByExample(example);
			if(CollectionUtils.isEmpty(list)){
				HFBalanceDetailVO balanceVO = getHFAccountBalace(hfAccTypeEnum);
				if(balanceVO == null){
					//第二次查询
					balanceVO = getHFAccountBalace(hfAccTypeEnum);
					if(balanceVO == null){
						//第三次查询
						balanceVO = getHFAccountBalace(hfAccTypeEnum);
						if(balanceVO == null){
							//告警
							specialJnlService.warn4Fail(null, "【系统余额快照】恒丰账户余额查询，失败："+hfAccTypeEnum.getDescription(), 
				        			"", "恒丰账户"+hfAccTypeEnum.getDescription()+"查询失败",true);
						}
					}
				}
				if(balanceVO != null){
					BsSysBalanceDailySnap sysBalanceDailySnap = new BsSysBalanceDailySnap();
					sysBalanceDailySnap.setAccountType(Constants.SYS_SNAP_ACC_TYPE_HFBANK);
					sysBalanceDailySnap.setBalance(MoneyUtil.add(Double.valueOf(balanceVO.getBalance()),Double.valueOf(balanceVO.getFrozen_amount())).doubleValue());
					sysBalanceDailySnap.setAvailableBalance(Double.valueOf(balanceVO.getBalance()));
					sysBalanceDailySnap.setFreezeBalance(Double.valueOf(balanceVO.getFrozen_amount()));
					sysBalanceDailySnap.setCode(hfAccTypeEnum.getCode());
					sysBalanceDailySnap.setSnapDate(today);
					sysBalanceDailySnap.setUpdateTime(new Date());
					sysBalanceDailySnap.setCreateTime(new Date());
					bsSysBalanceDailySnapMapper.insertSelective(sysBalanceDailySnap);
				}
				
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	/**
	 * 恒丰账户余额查询
	 * @param accTypeEnum
	 * @return
     */
	private HFBalanceDetailVO getHFAccountBalace(HFAccountTypeEnum accTypeEnum) {
		B2GReqMsg_HFBank_QueryAccountLeftAmountInfo req = new B2GReqMsg_HFBank_QueryAccountLeftAmountInfo();
		req.setAcct_type(accTypeEnum.getHfcode());
		req.setAccount("01");//账户编号-01-平台
		B2GResMsg_HFBank_QueryAccountLeftAmountInfo res = hfbankTransportService.queryAccountLeftAmountInfo(req);
		if (ConstantUtil.BSRESCODE_SUCCESS.equals(res.getResCode()) && StringUtil.isNotBlank(res.getData())) {
			HFBalanceDetailVO vo = JSON.parseObject(res.getData(),HFBalanceDetailVO.class);
			return vo;
		}else {
			logger.error("==========【系统余额快照】恒丰账户余额查询："+accTypeEnum.getDescription()+",失败："+res.getResMsg()+"============================");
		}
		return null;
	}

	/**
	 * 借款人余额快照
	 * @param code
	 * @param today
     */
	private void saveLoanBalanceSnap(String code, Date today) {
		try {
			BsSysBalanceDailySnapExample example = new BsSysBalanceDailySnapExample();
			example.createCriteria().andCodeEqualTo(code).andSnapDateEqualTo(today);
			List<BsSysBalanceDailySnap> list = bsSysBalanceDailySnapMapper.selectByExample(example);
			if(CollectionUtils.isEmpty(list)){
				LoanBalanceVO loanBalanceVO = lnSubAccountMapper.selectSumLoanBalanceByDay(today);
				BsSysBalanceDailySnap sysBalanceDailySnap = new BsSysBalanceDailySnap();
				sysBalanceDailySnap.setAccountType(Constants.SYS_SNAP_ACC_TYPE_BGW);
				sysBalanceDailySnap.setAvailableBalance(loanBalanceVO.getSumBalance());
				sysBalanceDailySnap.setBalance(loanBalanceVO.getSumBalance());
				sysBalanceDailySnap.setFreezeBalance(loanBalanceVO.getSumFreezeBalance());
				sysBalanceDailySnap.setCode(code);
				sysBalanceDailySnap.setSnapDate(today);
				sysBalanceDailySnap.setUpdateTime(new Date());
				sysBalanceDailySnap.setCreateTime(new Date());
				bsSysBalanceDailySnapMapper.insertSelective(sysBalanceDailySnap);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
