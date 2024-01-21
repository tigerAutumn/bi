package com.pinting.business.service.loan.impl;

import java.util.*;

import com.pinting.business.accounting.loan.enums.PartnerEnum;
import com.pinting.gateway.hessian.message.loan.*;
import com.pinting.gateway.hessian.message.loan.model.Limit;
import com.pinting.gateway.hessian.message.zsd.B2GReqMsg_ZsdNotice_NoticeBankLimit;
import com.pinting.gateway.hessian.message.zsd.B2GResMsg_ZsdNotice_NoticeBankLimit;
import com.pinting.gateway.out.service.zsd.ZsdNoticeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.pinting.business.accounting.loan.service.LoanRelationshipService;
import com.pinting.business.dao.BsSubAccountMapper;
import com.pinting.business.dao.LnDailyAmountMapper;
import com.pinting.business.enums.PTMessageEnum;
import com.pinting.business.exception.PTMessageException;
import com.pinting.business.model.Bs19payBankExample;
import com.pinting.business.model.BsBankExample;
import com.pinting.business.model.BsSysConfig;
import com.pinting.business.model.LnDailyAmount;
import com.pinting.business.model.LnDailyAmountExample;
import com.pinting.business.model.vo.DailyAmount4LoanVO;
import com.pinting.business.service.loan.AuthBalanaceQueryService;
import com.pinting.business.service.loan.LoanQueryService;
import com.pinting.business.service.site.BankCardService;
import com.pinting.business.service.site.SysConfigService;
import com.pinting.business.util.Constants;
import com.pinting.core.util.DateUtil;
import com.pinting.core.util.MoneyUtil;
import com.pinting.gateway.hessian.message.loan.model.BankLimit;

@Service
public class LoanQueryServiceImpl implements LoanQueryService {
	
	@Autowired
	private BankCardService bankCardService;
	@Autowired
	private LnDailyAmountMapper lnDailyAmountMapper;
	@Autowired
	private BsSubAccountMapper bsSubAccountMapper;
	@Autowired
	private SysConfigService sysConfigService;
	@Autowired
	private AuthBalanaceQueryService authBalanaceQueryService;
	@Autowired
	private LoanRelationshipService loanRelationshipService;
	@Autowired
	private ZsdNoticeService zsdNoticeService;
	
	@Override
	public List<BankLimit> getBankLimitList(G2BReqMsg_BankLimit_LimitList req,
			G2BResMsg_BankLimit_LimitList res) {
		List<BankLimit> list = bankCardService.selectBankLimitList();
		res.setBankLimits(list);
		return list;
	}

	/**
	 * 赞分期获取每日可借额度
	 */
	@Override
	public void getDailyAmount(G2BReqMsg_Loan_DailyAmount req,
			G2BResMsg_Loan_DailyAmount res) throws Exception {
		/**
		 * 查询当日可用（可借）余额
		 * 1.无数据时插入后重新
		 */
		Date now =new Date();
		Date useDate = DateUtil.parseDate(DateUtil.formatYYYYMMDD(now));
		LnDailyAmountExample example = new LnDailyAmountExample();
		example.createCriteria().andUseDateEqualTo(useDate).andPartnerCodeEqualTo(Constants.PRODUCT_PROPERTY_SYMBOL_ZAN);
		
		List<LnDailyAmount> list = lnDailyAmountMapper.selectByExample(example);
		if(list == null || list.size() == 0){
			//重新查询并统计数据
			LnDailyAmount lnDailyAmount = queryDailyAmount();
			if(lnDailyAmount != null ){
				lnDailyAmountMapper.insertSelective(lnDailyAmount);
				list = lnDailyAmountMapper.selectByExample(example);
			}
		}
		if(list != null && list.size() > 0){
			LnDailyAmount dailyAmount = list.get(0);
			if(dailyAmount.getTerm1Amount() != null){
				Double amount1 = MoneyUtil.multiply(dailyAmount.getTerm1Amount(), 100).doubleValue();
				res.setAmount1(String.valueOf(amount1.intValue()));
			}else{
				res.setAmount1("0");
			}
			if(dailyAmount.getTerm2Amount() != null){
				Double amount2 = MoneyUtil.multiply(dailyAmount.getTerm2Amount(), 100).doubleValue();
				res.setAmount2(String.valueOf(amount2.intValue()));
			}else{
				res.setAmount2("0");
			}
			if(dailyAmount.getTerm3Amount() != null){
				Double amount3 = MoneyUtil.multiply(dailyAmount.getTerm3Amount(), 100).doubleValue();
				res.setAmount3(String.valueOf(amount3.intValue()));
			}else{
				res.setAmount3("0");
			}
			if(dailyAmount.getTerm4Amount() != null){
				Double amount4 = MoneyUtil.multiply(dailyAmount.getTerm4Amount(), 100).doubleValue();
				res.setAmount4(String.valueOf(amount4.intValue()));
			}else{
				res.setAmount4("0");
			}
			if(dailyAmount.getTerm5Amount() != null){
				Double amount5 = MoneyUtil.multiply(dailyAmount.getTerm5Amount(), 100).doubleValue();
				res.setAmount5(String.valueOf(amount5.intValue()));
			}else{
				res.setAmount5("0");
			}
			if(dailyAmount.getTerm6Amount() != null){
				Double amount6 = MoneyUtil.multiply(dailyAmount.getTerm6Amount(), 100).doubleValue();
				res.setAmount6(String.valueOf(amount6.intValue()));
			}else{
				res.setAmount6("0");
			}
			if(dailyAmount.getTerm9Amount() != null){
				Double amount9 = MoneyUtil.multiply(dailyAmount.getTerm9Amount(), 100).doubleValue();
				res.setAmount9(String.valueOf(amount9.intValue()));
			}else{
				res.setAmount9("0");
			}
			if(dailyAmount.getTerm12Amount() != null){
				Double amount12 = MoneyUtil.multiply(dailyAmount.getTerm12Amount(), 100).doubleValue();
				res.setAmount12(String.valueOf(amount12.intValue()));
			}else{
				res.setAmount12("0");
			}
			if(dailyAmount.getTermxAmount() != null){
				Double amountx = MoneyUtil.multiply(dailyAmount.getTermxAmount(), 100).doubleValue();
				res.setAmountNoLimit(String.valueOf(amountx.intValue()));
			}else{
				res.setAmountNoLimit("0");
			}
		}
		
	}

	@Override
	public LnDailyAmount queryDailyAmount() {
		
		Date now =new Date();
		Date useDate = DateUtil.parseDate(DateUtil.formatYYYYMMDD(now));
		
		//新增对象初始化
		LnDailyAmount lnDailyAmount = new LnDailyAmount();
		lnDailyAmount.setPartnerCode(Constants.PRODUCT_PROPERTY_SYMBOL_ZAN);
		lnDailyAmount.setTermxAmount(0d);
		lnDailyAmount.setTermxLeftAmount(0d);
		lnDailyAmount.setTerm1Amount(0d);
		lnDailyAmount.setTerm1LeftAmount(0d);
		lnDailyAmount.setTerm2Amount(0d);
		lnDailyAmount.setTerm2LeftAmount(0d);
		lnDailyAmount.setTerm3Amount(0d);
		lnDailyAmount.setTerm3LeftAmount(0d);
		lnDailyAmount.setTerm4Amount(0d);
		lnDailyAmount.setTerm4LeftAmount(0d);
		lnDailyAmount.setTerm5Amount(0d);
		lnDailyAmount.setTerm5LeftAmount(0d);
		lnDailyAmount.setTerm6Amount(0d);
		lnDailyAmount.setTerm6LeftAmount(0d);
		lnDailyAmount.setTerm9Amount(0d);
		lnDailyAmount.setTerm9LeftAmount(0d);
		lnDailyAmount.setTerm12Amount(0d);
		lnDailyAmount.setTerm12LeftAmount(0d);
		lnDailyAmount.setUseDate(useDate);
		lnDailyAmount.setCreateTime(now);
		lnDailyAmount.setUpdateTime(now);
		
		int day = 5;//站岗资金保留天数默认天数
		BsSysConfig configDay = sysConfigService.findConfigByKey(Constants.DAY_4_WAIT_LOAN); //站岗资金保留天数
		if(configDay != null){
			day = Integer.parseInt(configDay.getConfValue());	
		}
		//起息日在此日期之前的
		String minInterestBeginDate =DateUtil.formatYYYYMMDD( DateUtil.addDays(now, -day));
		List<Integer> superUserList = loanRelationshipService.getSuperUserList();
		
		if(!CollectionUtils.isEmpty(superUserList)){
			//超级理财人投资金额查询
			Double superAmount = authBalanaceQueryService.getSuperAuthBalance(superUserList);
			lnDailyAmount.setTermxAmount(superAmount);
			lnDailyAmount.setTermxLeftAmount(superAmount);
		}else{
			lnDailyAmount.setTermxAmount(0d);
			lnDailyAmount.setTermxLeftAmount(0d);
		}
		
		//BsSysConfig limit = sysConfigService.findConfigByKey(Constants.MATCH_LIMIT_AMOUNT);//债权匹配时低于该金额的不进行债权承接
		Double limitAmount = 0d;//普通理财人债权匹配时低于该金额的不进行债权承接
		/*if(limit != null){
			limitAmount = Double.valueOf(limit.getConfValue());
		}*/
		//普通理财用户投资列表
		List<DailyAmount4LoanVO> normalUserList = bsSubAccountMapper.getSumBalanceByProductType(Constants.PRODUCT_PROPERTY_SYMBOL_ZAN, 
				Constants.PRODUCT_TYPE_AUTH, superUserList, minInterestBeginDate,"no",limitAmount,null);
		if(!CollectionUtils.isEmpty(normalUserList)){
			for (DailyAmount4LoanVO dailyAmount4LoanVO : normalUserList) {
				switch (dailyAmount4LoanVO.getTerm()) {
				case 1:
					lnDailyAmount.setTerm1Amount(dailyAmount4LoanVO.getSumAmount());
					lnDailyAmount.setTerm1LeftAmount(dailyAmount4LoanVO.getSumAmount());
					break;
				case 2:
					lnDailyAmount.setTerm2Amount(dailyAmount4LoanVO.getSumAmount());
					lnDailyAmount.setTerm2LeftAmount(dailyAmount4LoanVO.getSumAmount());
					break;
				case 3:
					lnDailyAmount.setTerm3Amount(dailyAmount4LoanVO.getSumAmount());
					lnDailyAmount.setTerm3LeftAmount(dailyAmount4LoanVO.getSumAmount());
					break;
				case 4:
					lnDailyAmount.setTerm4Amount(dailyAmount4LoanVO.getSumAmount());
					lnDailyAmount.setTerm4LeftAmount(dailyAmount4LoanVO.getSumAmount());
					break;
				case 5:
					lnDailyAmount.setTerm5Amount(dailyAmount4LoanVO.getSumAmount());
					lnDailyAmount.setTerm5LeftAmount(dailyAmount4LoanVO.getSumAmount());
					break;
				case 6:
					lnDailyAmount.setTerm6Amount(dailyAmount4LoanVO.getSumAmount());
					lnDailyAmount.setTerm6LeftAmount(dailyAmount4LoanVO.getSumAmount());
					break;
				case 9:
					lnDailyAmount.setTerm9Amount(dailyAmount4LoanVO.getSumAmount());
					lnDailyAmount.setTerm9LeftAmount(dailyAmount4LoanVO.getSumAmount());
					break;
				case 12:
					lnDailyAmount.setTerm12Amount(dailyAmount4LoanVO.getSumAmount());
					lnDailyAmount.setTerm12LeftAmount(dailyAmount4LoanVO.getSumAmount());
					
					break;

				default:
					break;
				}
			}
		}
		return lnDailyAmount;
	}

	/**
	 * 统计云贷自主放款当日可用额度
	 */
	@Override
	public Double queryDesFixedDailyAmount(Date queryDate) {
		LnDailyAmountExample example = new LnDailyAmountExample();
		example.createCriteria().andUseDateEqualTo(DateUtil.parseDate(DateUtil.formatYYYYMMDD(queryDate)));
		List<LnDailyAmount> dailyAmountList= lnDailyAmountMapper.selectByExample(example);
		//未查到当日限额再查一遍并插入表
		if(CollectionUtils.isEmpty(dailyAmountList)){
			Double dailyTotal = bsSubAccountMapper.dailyMoneyCount();
			LnDailyAmount apply = new LnDailyAmount();
			apply.setPartnerCode(PartnerEnum.YUN_DAI_SELF.getCode());
			if(dailyTotal == null){
				dailyTotal = 0d;
			}
			apply.setTermxAmount(dailyTotal);
			apply.setUseDate(new Date());
			apply.setCreateTime(new Date());
			apply.setUpdateTime(new Date());
			lnDailyAmountMapper.insertSelective(apply);
			return dailyTotal;
		}else{
			return dailyAmountList.get(0).getTermxAmount();
		}
	}
	/**
	 * 统计赞时贷当日可用额度
	 */
	@Override
	public Double queryZsdDailyAmount(Date queryDate) {
		LnDailyAmountExample example = new LnDailyAmountExample();
		example.createCriteria().andPartnerCodeEqualTo(PartnerEnum.ZSD.getCode()).andUseDateEqualTo(DateUtil.parseDate(DateUtil.formatYYYYMMDD(queryDate)));
		List<LnDailyAmount> dailyAmountList= lnDailyAmountMapper.selectByExample(example);
		//未查到当日限额再查一遍并插入表
		if(CollectionUtils.isEmpty(dailyAmountList)){
//			Double dailyTotal = bsSubAccountMapper.zsdDailyMoneyCount();
//			LnDailyAmount apply = new LnDailyAmount();
//			apply.setPartnerCode(PartnerEnum.ZSD.getCode());
//			if(dailyTotal == null){
//				dailyTotal = 0d;
//			}
//			apply.setTermxAmount(dailyTotal);
//			apply.setUseDate(new Date());
//			apply.setCreateTime(new Date());
//			apply.setUpdateTime(new Date());
//			lnDailyAmountMapper.insertSelective(apply);
//			return dailyTotal;
			return -0.01d;
		}else{
			return dailyAmountList.get(0).getTermxAmount();
		}
	}

	@Override
	public void noticeZsdBankList() {
		List<BankLimit> bankList = bankCardService.selectBankLimitList();
		B2GReqMsg_ZsdNotice_NoticeBankLimit req = new B2GReqMsg_ZsdNotice_NoticeBankLimit();
		List<com.pinting.gateway.hessian.message.zsd.model.BankLimit> list = new ArrayList<>();
		for (BankLimit bank: bankList) {
			com.pinting.gateway.hessian.message.zsd.model.BankLimit zsdBankLimit = new com.pinting.gateway.hessian.message.zsd.model.BankLimit();
			Map<String, com.pinting.gateway.hessian.message.zsd.model.Limit> map = new HashMap<>();
			Map<String, Limit> bankLimit = bank.getBankLimits();
			Set<String> keys = bankLimit.keySet();
			for (String key: keys) {
				Limit limit = bankLimit.get(key);
				com.pinting.gateway.hessian.message.zsd.model.Limit zsdLimit = new com.pinting.gateway.hessian.message.zsd.model.Limit();
				zsdLimit.setBankName(limit.getBankName());
				zsdLimit.setDay(limit.getDay());
				zsdLimit.setSingle(limit.getSingle());
				map.put(key, zsdLimit);
			}
			zsdBankLimit.setBankLimits(map);
			zsdBankLimit.setPayType(bank.getPayType());
			list.add(zsdBankLimit);
		}
		req.setLimits(list);
		zsdNoticeService.noticeBankLimit(req);
	}
}
