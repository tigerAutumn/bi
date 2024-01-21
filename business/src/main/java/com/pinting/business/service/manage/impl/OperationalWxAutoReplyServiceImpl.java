package com.pinting.business.service.manage.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.pinting.business.accounting.loan.service.LoanRelationshipService;
import com.pinting.business.dao.BsDepCash30Mapper;
import com.pinting.business.dao.BsWxAutoReplyMapper;
import com.pinting.business.dao.LnPayOrdersMapper;
import com.pinting.business.dao.LnRepayScheduleMapper;
import com.pinting.business.facade.manage.StatisticsFacade;
import com.pinting.business.hessian.manage.message.ReqMsg_OperationalWxAutoReply_SelectAutoReplyMessage;
import com.pinting.business.hessian.manage.message.ReqMsg_Statistics_StatisticsBusiness;
import com.pinting.business.hessian.manage.message.ResMsg_OperationalWxAutoReply_SelectAutoReplyMessage;
import com.pinting.business.hessian.manage.message.ResMsg_Statistics_StatisticsBusiness;
import com.pinting.business.model.BsDepCash30;
import com.pinting.business.model.BsDepCash30Example;
import com.pinting.business.model.BsWxAutoReply;
import com.pinting.business.model.BsWxAutoReplyExample;
import com.pinting.business.model.vo.DailyCheckGachaVO;
import com.pinting.business.model.vo.LoanDailyStatisticsVO;
import com.pinting.business.service.manage.BsPayOdersService;
import com.pinting.business.service.manage.BsSubAccountService;
import com.pinting.business.service.manage.BsUserService;
import com.pinting.business.service.manage.LoanService;
import com.pinting.business.service.manage.OperationalWxAutoReplyService;
import com.pinting.business.util.BeanUtils;
import com.pinting.business.util.Constants;
import com.pinting.core.hessian.service.HessianService;
import com.pinting.core.util.DateUtil;
import com.pinting.core.util.MoneyUtil;

/**
 *
 * @author SHENGUOPING
 * @date  2018年6月22日 下午12:38:01
 */
@Service
public class OperationalWxAutoReplyServiceImpl implements OperationalWxAutoReplyService {

	@Autowired
	private LoanRelationshipService  loanRelationshipService;
	@Autowired
	private BsUserService bsUserService;
	@Autowired
	private BsSubAccountService bsSubAccountService;
	@Autowired
	private LoanService loanService;
	@Autowired
	private BsDepCash30Mapper bsDepCash30Mapper;
	@Autowired
	private BsPayOdersService bsPayOdersService;
	@Autowired
	private LnPayOrdersMapper lnPayOrdersMapper;
	@Autowired
	private LnRepayScheduleMapper lnRepayScheduleMapper;
	
	@Override
	public void selectAutoReplyMessage(
			ReqMsg_OperationalWxAutoReply_SelectAutoReplyMessage req,
			ResMsg_OperationalWxAutoReply_SelectAutoReplyMessage res) {
		
/*		BsWxAutoReplyExample example = new BsWxAutoReplyExample();
		example.createCriteria().andKeywordsLike("%"+req.getAutoReplyMessage()+"%").andReplyTypeEqualTo(Constants.WX_AUTO_REPLY_TYPE_KEY);
		example.setOrderByClause("update_time desc");
		List<BsWxAutoReply> bsWxAutoReplies = bsWxAutoReplyMapper.selectByExample(example);*/
		
		List<BsWxAutoReply> bsWxAutoReplies = new ArrayList<BsWxAutoReply>();
		BsWxAutoReply bsWxAutoReply = new BsWxAutoReply();
		if (Constants.TODAY_DATA.equals(req.getAutoReplyMessage()) 
				|| Constants.TODAY_DATA_KEY.equals(req.getAutoReplyMessage())) {
			//今日数据
			List<Integer> subAccountIdList = loanRelationshipService.getSuperUserSubAccountIdList();
			//查询条件
			Map<String,Object> params = new HashMap<String,Object>();
			params.put("time",new Date());
			params.put("subAccountIdList",CollectionUtils.isEmpty(subAccountIdList)?null:subAccountIdList);
			//统计注册用户数
			int userCount = bsUserService.countResgisterUserByTime(new Date());
			//统计投资用户、投资金额、成交笔数
			Map<String,Object> subAccountCountMap = bsSubAccountService.countSubAccountByTime(params);
	    	bsWxAutoReply.setMsgType(Constants.WX_Msg_Type_text);
	    	bsWxAutoReply.setContent("今日注册用户：" +  userCount
	    			+ "\n今日投资用户：" + (Long)subAccountCountMap.get("investUserCount") 
	    			+ "\n今日投资金额：" + MoneyUtil.format((Double)subAccountCountMap.get("investAmount"))
	    			+ "\n今日成交笔数：" + (Long)subAccountCountMap.get("dealCount"));
	    	bsWxAutoReplies.add(bsWxAutoReply);
		} else if (Constants.YESTERDAY_DATA.equals(req.getAutoReplyMessage())
				|| Constants.YESTERDAY_DATA_KEY.equals(req.getAutoReplyMessage())) {
			//昨日数据
			List<Integer> subAccountIdList = loanRelationshipService.getSuperUserSubAccountIdList();
			//查询条件
			Map<String,Object> params = new HashMap<String,Object>();
			params.put("time",DateUtil.addDays(new Date(), -1));
			params.put("subAccountIdList",CollectionUtils.isEmpty(subAccountIdList)?null:subAccountIdList);
			//统计注册用户数
			int userCount = bsUserService.countResgisterUserByTime(DateUtil.addDays(new Date(), -1));
			//统计投资用户、投资金额、成交笔数
			Map<String,Object> subAccountCountMap = bsSubAccountService.countSubAccountByTime(params);
	    	bsWxAutoReply.setMsgType(Constants.WX_Msg_Type_text);
	    	bsWxAutoReply.setContent("昨日注册用户：" + userCount
	    			+ "\n昨日投资用户：" + (Long)subAccountCountMap.get("investUserCount")
	    			+ "\n昨日投资金额：" + MoneyUtil.format((Double)subAccountCountMap.get("investAmount"))
	    			+ "\n昨日成交笔数：" + (Long)subAccountCountMap.get("dealCount"));
	    	bsWxAutoReplies.add(bsWxAutoReply);
		} else if (Constants.YUN_DAI_PRODUCT_BUY.equals(req.getAutoReplyMessage())
				|| Constants.YUN_DAI_PRODUCT_BUY_KEY.equals(req.getAutoReplyMessage())) {
			//云贷产品购买查询
			List<Integer> subAccountIdList = loanRelationshipService.getSuperUserSubAccountIdList();
			//查询条件
			Map<String,Object> params = new HashMap<String,Object>();
			params.put("partner", Constants.PRODUCT_TYPE_AUTH_YUN);
			params.put("subAccountIdList",CollectionUtils.isEmpty(subAccountIdList)?null:subAccountIdList);
			//统计云贷1个月，3个月，6个月，12个月和总的购买金额
			Map<String,Object> subAccountCountMap = bsSubAccountService.countSubAccountByPartner(params);
			//云贷
			Double amount1 = (Double)subAccountCountMap.get("t1MonthAmount");
			Double amount2 = (Double)subAccountCountMap.get("t3MonthAmount");
			Double amount3 = (Double)subAccountCountMap.get("t6MonthAmount");
			Double amount4 = (Double)subAccountCountMap.get("t1YearAmount");
	    	bsWxAutoReply.setMsgType(Constants.WX_Msg_Type_text);
	    	bsWxAutoReply.setContent("云贷1个月：" + MoneyUtil.format(amount1)
	    			+ "\n云贷3个月：" + MoneyUtil.format(amount2)
	    			+ "\n云贷6个月：" + MoneyUtil.format(amount3)
	    			+ "\n云贷12个月：" + MoneyUtil.format(amount4)
	    			+ "\n云贷总金额：" + MoneyUtil.add(amount1, MoneyUtil.add(amount2,
	    							MoneyUtil.add(amount3, amount4).doubleValue())
	    							.doubleValue()));
	    	bsWxAutoReplies.add(bsWxAutoReply);
		} else if (Constants.SEVEN_DAI_PRODUCT_BUY.equals(req.getAutoReplyMessage())
				|| Constants.SEVEN_DAI_PRODUCT_BUY_KEY.equals(req.getAutoReplyMessage())) {
			//7贷产品购买查询
			List<Integer> subAccountIdList = loanRelationshipService.getSuperUserSubAccountIdList();
			//查询条件
			Map<String,Object> params = new HashMap<String,Object>();
			params.put("partner", Constants.PRODUCT_TYPE_AUTH_7);
			params.put("subAccountIdList",CollectionUtils.isEmpty(subAccountIdList)?null:subAccountIdList);
			//统计七贷1个月，3个月，6个月，12个月和总的购买金额
			Map<String,Object> subAccountCountMap = bsSubAccountService.countSubAccountByPartner(params);
			//7贷
			Double amount1_7dai = (Double)subAccountCountMap.get("t1MonthAmount");
			Double amount3_7dai = (Double)subAccountCountMap.get("t3MonthAmount");
			Double amount6_7dai = (Double)subAccountCountMap.get("t6MonthAmount");
			Double amount12_7dai = (Double)subAccountCountMap.get("t1YearAmount");
	    	bsWxAutoReply.setMsgType(Constants.WX_Msg_Type_text);
	    	bsWxAutoReply.setContent("七贷1个月：" + MoneyUtil.format(amount1_7dai)
	    			+ "\n七贷3个月：" + MoneyUtil.format(amount3_7dai)
	    			+ "\n七贷6个月：" + MoneyUtil.format(amount6_7dai)
	    			+ "\n七贷12个月：" + MoneyUtil.format(amount12_7dai)
	    			+ "\n七贷总金额：" + MoneyUtil.add(amount1_7dai, MoneyUtil.add(amount3_7dai,
	    							MoneyUtil.add(amount6_7dai, amount12_7dai).doubleValue()
	    							).doubleValue()));
	    	bsWxAutoReplies.add(bsWxAutoReply);
		}else if (Constants.FREE_PRODUCT_BUY.equals(req.getAutoReplyMessage())
				|| Constants.FREE_PRODUCT_BUY_KEY.equals(req.getAutoReplyMessage())) {
			//自由产品购买查询
			List<Integer> subAccountIdList = loanRelationshipService.getSuperUserSubAccountIdList();
			//查询条件
			Map<String,Object> params = new HashMap<String,Object>();
			params.put("partner", Constants.PRODUCT_TYPE_AUTH_FREE);
			params.put("subAccountIdList",CollectionUtils.isEmpty(subAccountIdList)?null:subAccountIdList);
			//统计自由1个月，3个月，6个月，12个月和总的购买金额
			Map<String,Object> subAccountCountMap = bsSubAccountService.countSubAccountByPartner(params);
			//自由
			Double amount1_free = (Double)subAccountCountMap.get("t1MonthAmount");
			Double amount3_free = (Double)subAccountCountMap.get("t3MonthAmount");
			Double amount6_free = (Double)subAccountCountMap.get("t6MonthAmount");
			Double amount12_free = (Double)subAccountCountMap.get("t1YearAmount");
	    	bsWxAutoReply.setMsgType(Constants.WX_Msg_Type_text);
	    	bsWxAutoReply.setContent("自由1个月：" + MoneyUtil.format(amount1_free)
	    			+ "\n自由3个月：" + MoneyUtil.format(amount3_free)
	    			+ "\n自由6个月：" + MoneyUtil.format(amount6_free)
	    			+ "\n自由12个月：" + MoneyUtil.format(amount12_free)
	    			+ "\n自由总金额：" + MoneyUtil.add(amount1_free, MoneyUtil.add(amount3_free,
	    							MoneyUtil.add(amount6_free, amount12_free).doubleValue()
	    							).doubleValue()));
	    	bsWxAutoReplies.add(bsWxAutoReply);
		} else if (Constants.YUN_DAI_LENDING.equals(req.getAutoReplyMessage())
				|| Constants.YUN_DAI_LENDING_KEY.equals(req.getAutoReplyMessage())) {
			Date date = new Date();
	    	// 默认查询当天记录
			String startTime = DateUtil.formatYYYYMMDD(date);
			String endTime = DateUtil.formatYYYYMMDD(date);
	    	// 云贷资金计划总量
	    	double yunDaiTotalAmount = loanService.queryYunDaiDailyTotalAmount(startTime, endTime);
			LoanDailyStatisticsVO paiedYunDai =  loanService.queryLoanDailyPaiedStatistics("YUN_DAI_SELF", startTime, endTime);
			LoanDailyStatisticsVO payingYunDai =  loanService.queryLoanDailyPayingStatistics("YUN_DAI_SELF", startTime, endTime);
	    	bsWxAutoReply.setMsgType(Constants.WX_Msg_Type_text);
	    	bsWxAutoReply.setContent("云贷资金计划总量：" + MoneyUtil.format(yunDaiTotalAmount)
	    			+ "\n云贷放款成功笔数：" + paiedYunDai.getPaiedCount()
	    			+ "\n云贷放款成功总金额：" + MoneyUtil.format(paiedYunDai.getPaiedTotalAmount())
	    			+ "\n云贷放款处理中笔数：" + payingYunDai.getPayingCount()
	    			+ "\n云贷放款处理中总金额：" + MoneyUtil.format(payingYunDai.getPayingTotalAmount()));
	    	bsWxAutoReplies.add(bsWxAutoReply);
		} else if (Constants.SEVEN_DAI_LENDING.equals(req.getAutoReplyMessage())
				|| Constants.SEVEN_DAI_LENDING_KEY.equals(req.getAutoReplyMessage())) {
			Date date = new Date();
	    	// 默认查询当天记录
			String startTime = DateUtil.formatYYYYMMDD(date);
			String endTime = DateUtil.formatYYYYMMDD(date);
	    	// 7贷资金计划总量
	    	double sevenTotalAmount = loanService.querySevenDailyTotalAmount(startTime, endTime);
			LoanDailyStatisticsVO paiedSeven =  loanService.queryLoanDailyPaiedStatistics("7_DAI_SELF", startTime, endTime);
			LoanDailyStatisticsVO payingSeven =  loanService.queryLoanDailyPayingStatistics("7_DAI_SELF", startTime, endTime);
	    	bsWxAutoReply.setMsgType(Constants.WX_Msg_Type_text);
	    	bsWxAutoReply.setContent("七贷资金计划总量：" + MoneyUtil.format(sevenTotalAmount)
	    			+ "\n七贷放款成功笔数：" + paiedSeven.getPaiedCount()
	    			+ "\n七贷放款成功总金额：" + MoneyUtil.format(paiedSeven.getPaiedTotalAmount())
	    			+ "\n七贷放款处理中笔数：" + payingSeven.getPayingCount()
	    			+ "\n七贷放款处理中总金额：" + MoneyUtil.format(payingSeven.getPayingTotalAmount()));
	    	bsWxAutoReplies.add(bsWxAutoReply);
		} else if (Constants.YUN_DAI_REPAY.equals(req.getAutoReplyMessage())
				|| Constants.YUN_DAI_REPAY_KEY.equals(req.getAutoReplyMessage())) {
			BsDepCash30Example example = new BsDepCash30Example();
			example.createCriteria().andCashDateGreaterThan(new Date()).andPartnerCodeEqualTo(Constants.PROPERTY_SYMBOL_YUN_DAI_SELF);
			List<BsDepCash30> list = bsDepCash30Mapper.selectByExample(example);
			//统计应还笔数
			int initCount = lnRepayScheduleMapper.queryDailyCount(Constants.PROPERTY_SYMBOL_YUN_DAI_SELF);
			//统计实际还款金额和实际还款笔数
			Map<String,Object> queryDailyMap = lnPayOrdersMapper.queryDailyCountAndSum(Constants.PROPERTY_SYMBOL_YUN_DAI_SELF);
			bsWxAutoReply.setMsgType(Constants.WX_Msg_Type_text);
	    	bsWxAutoReply.setContent("云贷今日应还款金额：" + MoneyUtil.format(list.get(0).getRepayBalance())
	    			+ "\n云贷今日应还款笔数：" + initCount
	    			+ "\n云贷今日实际还款金额：" + MoneyUtil.format((Double)queryDailyMap.get("totalRepayBalance"))
	    			+ "\n云贷今日实际还款笔数：" + queryDailyMap.get("succCount"));
	    	bsWxAutoReplies.add(bsWxAutoReply);
		} else if (Constants.SEVEN_DAI_REPAY.equals(req.getAutoReplyMessage())
				|| Constants.SEVEN_DAI_REPAY_KEY.equals(req.getAutoReplyMessage())) {
			BsDepCash30Example example = new BsDepCash30Example();
			example.createCriteria().andCashDateGreaterThan(new Date()).andPartnerCodeEqualTo(Constants.PROPERTY_SYMBOL_7_DAI_SELF);
			List<BsDepCash30> list = bsDepCash30Mapper.selectByExample(example);
			//统计应还笔数
			int initCount = lnRepayScheduleMapper.queryDailyCount(Constants.PROPERTY_SYMBOL_7_DAI_SELF);
			//统计实际还款金额和实际还款笔数
			Map<String,Object> queryDailyMap = lnPayOrdersMapper.queryDailyCountAndSum(Constants.PROPERTY_SYMBOL_7_DAI_SELF);
	    	bsWxAutoReply.setMsgType(Constants.WX_Msg_Type_text);
	    	bsWxAutoReply.setContent("七贷今日应还款金额：" + MoneyUtil.format(list.get(0).getRepayBalance())
	    			+ "\n七贷今日应还款笔数：" + initCount
	    			+ "\n七贷今日实际还款金额：" + MoneyUtil.format((Double)queryDailyMap.get("totalRepayBalance"))
	    			+ "\n七贷今日实际还款笔数：" + queryDailyMap.get("succCount"));
	    	bsWxAutoReplies.add(bsWxAutoReply);
		} else if (Constants.YUN_DAI_CASH_BALANCE.equals(req.getAutoReplyMessage())
				|| Constants.YUN_DAI_CASH_BALANCE_KEY.equals(req.getAutoReplyMessage())) {
			BsDepCash30Example example = new BsDepCash30Example();
			example.createCriteria().andCashDateGreaterThan(new Date()).andPartnerCodeEqualTo(Constants.PROPERTY_SYMBOL_YUN_DAI_SELF);
			List<BsDepCash30> list = bsDepCash30Mapper.selectByExample(example);
			Double partnerStandAmount = bsSubAccountService.querySumBgwAuthYunBalance() == null? 0d: bsSubAccountService.querySumBgwAuthYunBalance();
			Double yunCashBalance = partnerStandAmount - list.get(0).getQuitPrincipal() - list.get(0).getQuitInterest();
	    	bsWxAutoReply.setMsgType(Constants.WX_Msg_Type_text);
	    	bsWxAutoReply.setContent("云贷次日兑付差值：" + MoneyUtil.format(yunCashBalance));
	    	bsWxAutoReplies.add(bsWxAutoReply);
		} else if (Constants.SEVEN_DAI_CASH_BALANCE.equals(req.getAutoReplyMessage())
				|| Constants.SEVEN_DAI_CASH_BALANCE_KEY.equals(req.getAutoReplyMessage())) {
			BsDepCash30Example example = new BsDepCash30Example();
			example.createCriteria().andCashDateGreaterThan(new Date()).andPartnerCodeEqualTo(Constants.PROPERTY_SYMBOL_7_DAI_SELF);
			List<BsDepCash30> list = bsDepCash30Mapper.selectByExample(example);
			Double partnerStandAmount = bsSubAccountService.querySumBgwAuthSevrnBalance() == null? 0d: bsSubAccountService.querySumBgwAuthSevrnBalance();
			Double sevenCashBalance = partnerStandAmount - list.get(0).getQuitPrincipal() - list.get(0).getQuitInterest();
	    	bsWxAutoReply.setMsgType(Constants.WX_Msg_Type_text);
	    	bsWxAutoReply.setContent("七贷次日兑付差值：" + MoneyUtil.format(sevenCashBalance));
	    	bsWxAutoReplies.add(bsWxAutoReply);
		} else {
			bsWxAutoReply.setMsgType(Constants.WX_Msg_Type_text);
	    	bsWxAutoReply.setContent("1.今日数据\n2.昨日数据\n3.云贷产品购买查询\n4.七贷产品购买查询\n5.自由产品计划购买查询\n6.云贷放款查询\n7.七贷放款查询\n8.云贷实时还款查询\n9.七贷实时还款查询\n10.云贷次日兑付差值\n11.七贷次日兑付差值");
	    	bsWxAutoReplies.add(bsWxAutoReply);
		}
		res.setDataGrid(BeanUtils.classToArrayList(bsWxAutoReplies));
	}

	@Override
	public List<BsWxAutoReply> getSubscribeReplyList() {
		List<BsWxAutoReply> bsWxAutoReplies = new ArrayList<BsWxAutoReply>();
		BsWxAutoReply bsWxAutoReply = new BsWxAutoReply();
		bsWxAutoReply.setMsgType(Constants.WX_AUTO_REPLY_TYPE_SUBSCRIBE);
    	bsWxAutoReply.setContent("1.今日数据\n2.昨日数据\n3.云贷产品购买查询\n4.七贷产品购买查询\n5.自由产品计划购买查询\n6.云贷放款查询\n7.七贷放款查询\n8.云贷实时还款查询\n9.七贷实时还款查询\n10.云贷次日兑付差值\n11.七贷次日兑付差值");
    	bsWxAutoReplies.add(bsWxAutoReply);
		return bsWxAutoReplies;
	}

}
