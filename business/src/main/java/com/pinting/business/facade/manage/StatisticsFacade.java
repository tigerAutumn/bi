package com.pinting.business.facade.manage;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import com.pinting.business.accounting.loan.service.LoanRelationshipService;
import com.pinting.business.accounting.service.PayOrdersService;
import com.pinting.business.enums.PTMessageEnum;
import com.pinting.business.exception.PTMessageException;
import com.pinting.business.hessian.manage.message.ReqMsg_Statistics_AgentPerformance;
import com.pinting.business.hessian.manage.message.ReqMsg_Statistics_AgentQuery;
import com.pinting.business.hessian.manage.message.ReqMsg_Statistics_AgentUserQuery;
import com.pinting.business.hessian.manage.message.ReqMsg_Statistics_BAOFOODailySnapQuery;
import com.pinting.business.hessian.manage.message.ReqMsg_Statistics_BGWDailySnapQuery;
import com.pinting.business.hessian.manage.message.ReqMsg_Statistics_BuyMessageListQuery;
import com.pinting.business.hessian.manage.message.ReqMsg_Statistics_DepFinanceTotal;
import com.pinting.business.hessian.manage.message.ReqMsg_Statistics_FinanceTotal;
import com.pinting.business.hessian.manage.message.ReqMsg_Statistics_HFDailySnapQuery;
import com.pinting.business.hessian.manage.message.ReqMsg_Statistics_RegisterViewQuery;
import com.pinting.business.hessian.manage.message.ReqMsg_Statistics_SelectUserInvestmentPaybackList;
import com.pinting.business.hessian.manage.message.ReqMsg_Statistics_StatisticsBusiness;
import com.pinting.business.hessian.manage.message.ReqMsg_Statistics_StatisticsListQuery;
import com.pinting.business.hessian.manage.message.ReqMsg_Statistics_ThdFinanceTotal;
import com.pinting.business.hessian.manage.message.ReqMsg_Statistics_UserInvestQuery;
import com.pinting.business.hessian.manage.message.ReqMsg_Statistics_WXAccountDetailQuery;
import com.pinting.business.hessian.manage.message.ResMsg_Statistics_AgentPerformance;
import com.pinting.business.hessian.manage.message.ResMsg_Statistics_AgentQuery;
import com.pinting.business.hessian.manage.message.ResMsg_Statistics_AgentUserQuery;
import com.pinting.business.hessian.manage.message.ResMsg_Statistics_BAOFOODailySnapQuery;
import com.pinting.business.hessian.manage.message.ResMsg_Statistics_BGWDailySnapQuery;
import com.pinting.business.hessian.manage.message.ResMsg_Statistics_BuyMessageListQuery;
import com.pinting.business.hessian.manage.message.ResMsg_Statistics_DepFinanceTotal;
import com.pinting.business.hessian.manage.message.ResMsg_Statistics_FinanceTotal;
import com.pinting.business.hessian.manage.message.ResMsg_Statistics_HFDailySnapQuery;
import com.pinting.business.hessian.manage.message.ResMsg_Statistics_RegisterViewQuery;
import com.pinting.business.hessian.manage.message.ResMsg_Statistics_SelectUserInvestmentPaybackList;
import com.pinting.business.hessian.manage.message.ResMsg_Statistics_StatisticsBusiness;
import com.pinting.business.hessian.manage.message.ResMsg_Statistics_StatisticsListQuery;
import com.pinting.business.hessian.manage.message.ResMsg_Statistics_ThdFinanceTotal;
import com.pinting.business.hessian.manage.message.ResMsg_Statistics_UserInvestQuery;
import com.pinting.business.hessian.manage.message.ResMsg_Statistics_WXAccountDetailQuery;
import com.pinting.business.model.BsAgent;
import com.pinting.business.model.BsPayOrders;
import com.pinting.business.model.BsProduct;
import com.pinting.business.model.BsSysBalanceDailySnap;
import com.pinting.business.model.BsSysConfig;
import com.pinting.business.model.BsUserInvestView;
import com.pinting.business.model.BsUserRegistView;
import com.pinting.business.model.vo.AgentStatInfoVO;
import com.pinting.business.model.vo.AgentUserInfoVO;
import com.pinting.business.model.vo.BAOFOODailySnapVO;
import com.pinting.business.model.vo.BGWDailySnapVO;
import com.pinting.business.model.vo.BsStatisticsVO;
import com.pinting.business.model.vo.BsUserInvestViewVO;
import com.pinting.business.model.vo.BsUserRegistViewVO;
import com.pinting.business.model.vo.HFBankPayOrderVO;
import com.pinting.business.model.vo.HFDailySnapVO;
import com.pinting.business.service.manage.AgentService;
import com.pinting.business.service.manage.BsSubAccountService;
import com.pinting.business.service.manage.BsSysBalanceDailySnapService;
import com.pinting.business.service.manage.BsSysConfigService;
import com.pinting.business.service.manage.BsUserRegisterService;
import com.pinting.business.service.manage.BsUserService;
import com.pinting.business.service.manage.MBsSysSubAccountService;
import com.pinting.business.service.manage.MStatisticsService;
import com.pinting.business.service.manage.MUserInvestViewService;
import com.pinting.business.service.manage.MWxUserService;
import com.pinting.business.service.site.ProductService;
import com.pinting.business.util.BeanUtils;
import com.pinting.business.util.CalculatorUtil;
import com.pinting.business.util.Constants;
import com.pinting.core.util.ConstantUtil;
import com.pinting.core.util.DateUtil;
import com.pinting.core.util.MoneyUtil;
import com.pinting.core.util.StringUtil;
import com.pinting.gateway.hessian.message.dafy.B2GReqMsg_Account_QueryWXAccountDetail;
import com.pinting.gateway.hessian.message.dafy.B2GResMsg_Account_QueryWXAccountDetail;
import com.pinting.gateway.hessian.message.dafy.model.Data;
import com.pinting.gateway.out.service.DafyTransportService;

/**
 * 统计管理
 * 
 * @Project: business
 * @Title: StatisticsFacade.java
 * @author Huang MengJian
 * @date 2015-2-27 下午3:39:54
 * @Copyright: 2015 BiGangWan.com Inc. All rights reserved.
 */
@Component("Statistics")
public class StatisticsFacade {

	@Autowired
	private MStatisticsService mStatisticsService;
	@Autowired
	private DafyTransportService dafyTransportService;
	@Autowired
	private AgentService agentService;
	@Autowired
	private PayOrdersService payOrdersService;
	@Autowired
	private BsUserService bsUserService;
	@Autowired
	private BsSubAccountService bsSubAccountService;
	@Autowired
	private MWxUserService mWxUserService;
	@Autowired
	private MBsSysSubAccountService mBsSysSubAccountService;
	@Autowired
	private ProductService productService;
	@Autowired
	private BsSysConfigService bsSysConfigService;
	@Autowired
	private MUserInvestViewService userInvestViewService;
	@Autowired
	private BsUserRegisterService bsUserRegisterService;
	@Autowired
	private LoanRelationshipService  loanRelationshipService;
	@Autowired
	private BsSysBalanceDailySnapService bsSysBalanceDailySnapService;
	
	private Logger log = LoggerFactory.getLogger(StatisticsFacade.class);

	public void statisticsListQuery(ReqMsg_Statistics_StatisticsListQuery req,
			ResMsg_Statistics_StatisticsListQuery res) {
		ArrayList<HashMap<String, Object>> valueList = BeanUtils
				.classToArrayList(mStatisticsService.findMStatisticsByType(req
						.getType()));
		res.setStatisticsList(valueList);

	}

	public void wXAccountDetailQuery(
			ReqMsg_Statistics_WXAccountDetailQuery req,
			ResMsg_Statistics_WXAccountDetailQuery res) {

		B2GReqMsg_Account_QueryWXAccountDetail req2 = new B2GReqMsg_Account_QueryWXAccountDetail();

		req2.setStartDate(req.getStartDate());
		req2.setEndDate(req.getEndDate());
		req2.setPageIndex(req.getPageNum());//页码
		req2.setPageNum(req.getNumPerPage());//每页条数
		req2.setTransType(req.getTransType());

		B2GResMsg_Account_QueryWXAccountDetail resp = dafyTransportService
				.wXAccountDetailQuery(req2);
		if (!ConstantUtil.BSRESCODE_SUCCESS.equals(resp.getResCode())) {
			throw new PTMessageException(PTMessageEnum.DAFY_WX_ACCOUNT_DETAIL_FAIL,
					res.getResMsg());
		} else {
			res.setBalance(resp.getBalance());
			res.setCount(resp.getCount());
			res.setCurrPage(resp.getCurrPage());
			List<Data> valueList = resp.getData();
			List<HashMap<String, Object>> data = BeanUtils
					.classToArrayList(valueList);
			res.setData(data);
		}
	}

	/**
	 * 投资购买查询
	 * @param req
	 * @param res
     */
	public void buyMessageListQuery(ReqMsg_Statistics_BuyMessageListQuery req,
			ResMsg_Statistics_BuyMessageListQuery res) {

		Integer pageNum = req.getPageNum();
		Integer numPerPage = req.getNumPerPage();
		int totalRows = mStatisticsService.CountBuyMessage(req.getOrderNo(),
				req.getBeginBuyAmount(), req.getEndBuyAmount(),
				req.getBuyBeginTime(), req.getBuyEndTime(),
				req.getInvestBeginTime(), req.getInvestEndTime(),
				req.getBindBankCard(), req.getBuyBankCard(), req.getIdCard(),
				req.getAccountStatus(), req.getMobile(), req.getUserName(),
				req.getTerm(), req.getProductCode(),req.getAgentName(),
				req.getBuyBankType(),req.getAgentId(),req.getRate(),req.getStartRate(),req.getEndRate(),
				req.getAgentIds(),req.getNonAgentId(),req.getPropertySymbol());

		if (totalRows > 0) {
			List<BsStatisticsVO> valueList = mStatisticsService
					.findUserBuyMessageList(req.getOrderNo(),
							req.getBeginBuyAmount(), req.getEndBuyAmount(),
							req.getBuyBeginTime(), req.getBuyEndTime(),
							req.getInvestBeginTime(), req.getInvestEndTime(),
							req.getBindBankCard(), req.getBuyBankCard(),
							req.getIdCard(), req.getProductCode(),req.getAgentName(),
							req.getAccountStatus(), req.getMobile(),
							req.getUserName(), req.getTerm(), req.getPageNum(),
							req.getNumPerPage(), req.getOrderDirection(),
							req.getOrderField(),req.getBuyBankType(),req.getAgentId(),
							req.getRate(),req.getStartRate(),req.getEndRate(),req.getAgentIds(),req.getNonAgentId(),req.getPropertySymbol());

			ArrayList<HashMap<String, Object>> buyMessageList = BeanUtils
					.classToArrayList(valueList);

			res.setUserBuyMessageList(buyMessageList);

			double sumBalanceAmount = mStatisticsService.findUserBuySumBalance(
					req.getBeginBuyAmount(), req.getEndBuyAmount(),
					req.getBuyBeginTime(), req.getBuyEndTime(),
					req.getInvestBeginTime(), req.getInvestEndTime(),
					req.getBindBankCard(), req.getBuyBankCard(),
					req.getIdCard(), req.getAccountStatus(), req.getMobile(),
					req.getUserName(), req.getTerm(), req.getProductCode(),
					req.getBuyBankType(),req.getAgentId(),req.getRate(),
					req.getStartRate(),req.getEndRate(),req.getAgentIds(),req.getNonAgentId(),req.getPropertySymbol());
			res.setSumBalanceAmount(sumBalanceAmount);
		}
		res.setTotalRows(totalRows);
		res.setNumPerPage(numPerPage);
		res.setPageNum(pageNum);
		
		List<BsPayOrders> buyBankTypeList = payOrdersService.findBuyBankTypeList();
		List<BsProduct> rates = productService.selectProductRateList();
		BsProduct product = productService.sumCurrTotalAmountAndInvestNum();
		List<BsProduct> products = new ArrayList<>();
		products.add(product);
		res.setProductList(BeanUtils.classToArrayList(products));
		res.setBuyBankTypeList(BeanUtils.classToArrayList(buyBankTypeList));
		res.setRateList(BeanUtils.classToArrayList(rates));
		List<BsSysConfig> sysConfigList = bsSysConfigService.findList();
		res.setSysConfigs(BeanUtils.classToArrayList(sysConfigList));
	}
	
	/**
	 * 渠道列表查询 及统计
	 * @param req
	 * @param res
	 */
	public void agentQuery(ReqMsg_Statistics_AgentQuery req, ResMsg_Statistics_AgentQuery res) {
		Integer pageNum = req.getPageNum();
		Integer numPerPage = req.getNumPerPage();
		int totalRows = agentService.countAgentStatList();
		if (totalRows > 0) {
			if (req.getQueryDefaultPageFlag() != null && req.getQueryDefaultPageFlag().equals("DEFAULTPAGE")) {
				List<AgentStatInfoVO> agentStatInfoVOs = agentService.findAgentStatList(pageNum, numPerPage + 1);
				ArrayList<HashMap<String, Object>> agentList = BeanUtils
						.classToArrayList(agentStatInfoVOs);
				res.setAgentList(agentList);
			} else {
				List<AgentStatInfoVO> agentStatInfoVOs = agentService.findAgentStatList(pageNum, 200);
				ArrayList<HashMap<String, Object>> agentList = BeanUtils
						.classToArrayList(agentStatInfoVOs);
				res.setAgentList(agentList);
			}
		}
		res.setTotalRows(totalRows - 1);
		res.setNumPerPage(numPerPage);
		res.setPageNum(pageNum);
	}
	/**
	 * 渠道用户查询 及统计
	 * @param req
	 * @param res
	 */
	public void agentUserQuery(ReqMsg_Statistics_AgentUserQuery req, ResMsg_Statistics_AgentUserQuery res) {
		Integer pageNum = req.getPageNum();
		Integer numPerPage = req.getNumPerPage();
		Integer agentId = req.getAgentId();
		String mobile = req.getMobile();
		String userName = req.getUserName();
		String investFlag = req.getInvestFlag();
		String sregistTime = req.getSregistTime();
		String eregistTime = req.getEregistTime();
		
		int totalRows = agentService.countAgentUserList(agentId,userName,mobile,investFlag,sregistTime,eregistTime);
		if (totalRows > 0) {
			List<AgentUserInfoVO> agentUserInfoVOs = agentService.findAgentUserList(agentId, userName,mobile,investFlag,sregistTime,eregistTime, pageNum, numPerPage);
			ArrayList<HashMap<String, Object>> agentUserList = BeanUtils
					.classToArrayList(agentUserInfoVOs);
			res.setAgentUserList(agentUserList);
		}
		res.setTotalRows(totalRows);
		res.setNumPerPage(numPerPage);
		res.setPageNum(pageNum);
	}
	
	/**
	 * 投资回款查询
	 * @param req
	 * @param res
	 */
	public void selectUserInvestmentPaybackList(ReqMsg_Statistics_SelectUserInvestmentPaybackList req,
			ResMsg_Statistics_SelectUserInvestmentPaybackList res) {
		Integer pageNum = req.getPageNum();//BuyMessageListQuery
		Integer numPerPage = req.getNumPerPage();
		int totalRows = mStatisticsService.selectCountSumInvestment(
				req.getBeginAmount(), req.getEndAmount(),
				req.getBeginTime(), req.getEndTime(),
				req.getSettleAccountsBeginTime(), req.getSettleAccountsEndTime(),
				req.getBindBankCard(), req.getBuyBankCard(), req.getBindBankName(), req.getIdCard(),
				req.getAccountStatus(), req.getMobile(), req.getUserName(),
				req.getTerm(), req.getProductId(), 
				req.getBeginSettlementAmount(), req.getEndSettlementAmount(), 
				req.getOrderNo(), req.getAgentId(),req.getRate(), req.getBuyBankType());
		
		if (totalRows > 0) {
			List<BsStatisticsVO> valueList = mStatisticsService
					.findUserInvestment(
							req.getBeginAmount(), req.getEndAmount(), 
							req.getBeginTime(), req.getEndTime(), 
							req.getBindBankCard(), req.getBindBankName(), 
							req.getSettleAccountsBeginTime(), req.getSettleAccountsEndTime(), 
							req.getAccountStatus(), req.getOrderNo(), 
							req.getProductId(), req.getAgentName(), 
							req.getBeginSettlementAmount(), req.getEndSettlementAmount(), 
							req.getIdCard(), req.getMobile(), req.getUserName(), 
							req.getPageNum(), req.getNumPerPage(), 
							req.getOrderDirection(), req.getOrderField(), 
							req.getOrderStatus(), req.getAgentId(),req.getTerm(),req.getRate(), req.getBuyBankType());

			ArrayList<HashMap<String, Object>> backSectionList = BeanUtils
					.classToArrayList(valueList);

			res.setUserBackSectionList(backSectionList);

			double sumSettlementAmount = mStatisticsService.selectUserSumInvestment(
					req.getBeginAmount(), req.getEndAmount(),
					req.getBeginTime(), req.getEndTime(),
					req.getSettleAccountsBeginTime(), req.getSettleAccountsEndTime(),
					req.getBindBankCard(), req.getBuyBankCard(), req.getBindBankName(),
					req.getIdCard(), req.getAccountStatus(), req.getMobile(),
					req.getUserName(), req.getTerm(), req.getProductId(), 
					req.getBeginSettlementAmount(), req.getEndSettlementAmount(), 
					req.getOrderNo(), req.getAgentId(),req.getRate(), req.getBuyBankType());
			res.setSumSettlementAmount(sumSettlementAmount);
		}
		res.setTotalRows(totalRows);
		res.setNumPerPage(numPerPage);
		res.setPageNum(pageNum);
		res.setSettlementAmount(req.getSettlementAmount() == null ? 0d : req.getSettlementAmount());
		
		List<BsProduct> products = mStatisticsService.findProductList();
		res.setProductList(BeanUtils.classToArrayList(products));
		
		List<BsAgent> agents = agentService.agentNameGroupByList(new BsAgent());
		res.setAgentList(BeanUtils.classToArrayList(agents));
		
		List<BsProduct> rates = productService.selectProductRateList();
		res.setRateList(BeanUtils.classToArrayList(rates));
		
		List<BsPayOrders> buyBankTypeList = payOrdersService.findBuyBankTypeList();
		res.setBuyBankTypeList(BeanUtils.classToArrayList(buyBankTypeList));
		
	}
	
	public void statisticsBusiness(ReqMsg_Statistics_StatisticsBusiness req,ResMsg_Statistics_StatisticsBusiness res) {
		
		List<Integer> subAccountIdList = loanRelationshipService.getSuperUserSubAccountIdList();
		//查询条件
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("todayTime",new Date());
		params.put("yesterdayTime",DateUtil.addDays(new Date(), -1));
		params.put("subAccountIdList",CollectionUtils.isEmpty(subAccountIdList)?null:subAccountIdList);
		//统计关注用户数
		Map<String,Object> wxUserCountMap = mWxUserService.countWxUserNum(params);
		res.settAttentionUserCount((Long)wxUserCountMap.get("tAttentionUserCount"));
		res.setyAttentionUserCount((Long)wxUserCountMap.get("yAttentionUserCount"));
		res.setTotalAttentionUserCount((Long)wxUserCountMap.get("totalAttentionUserCount"));
		
		//统计注册用户数
		Map<String,Object> userCountMap = bsUserService.countResgisterUser(params);
		res.settRegisterUserCount((Long)userCountMap.get("tRegisterUserCount"));
		res.setyRegisterUserCount((Long)userCountMap.get("yRegisterUserCount"));
		res.setTotalRegisterUserCount((Long)userCountMap.get("totalRegisterUserCount"));
		
		//统计投资用户、投资金额、成交笔数
		Map<String,Object> subAccountCountMap = bsSubAccountService.countSubAccount(params);
		res.settDealCount((Long)subAccountCountMap.get("tDealCount"));
		res.setyDealCount((Long)subAccountCountMap.get("yDealCount"));
		res.setTotalDealCount((Long)subAccountCountMap.get("totalDealCount"));
		res.settInvestAmount((Double)subAccountCountMap.get("tInvestAmount"));
		res.setyInvestAmount((Double)subAccountCountMap.get("yInvestAmount"));
		res.setTotalInvestAmount((Double)subAccountCountMap.get("totalInvestAmount"));
		res.settInvestUserCount((Long)subAccountCountMap.get("tInvestUserCount"));
		res.setyInvestUserCount((Long)subAccountCountMap.get("yInvestUserCount"));
		res.setTotalInvestUserCount((Long)subAccountCountMap.get("totalInvestUserCount"));
		
		// 1. 老云贷、老7贷、赞分期、赞时贷上线自由站岗户需求，对应数据查询删除，加快响应速度。
		// 2. 新增自由产品计划
		Double authAmount1_free = (Double)subAccountCountMap.get("t1MonthAuthAmount4Free");
		Double authAmount2_free = (Double)subAccountCountMap.get("t3MonthAuthAmount4Free");
		Double authAmount3_free = (Double)subAccountCountMap.get("t6MonthAuthAmount4Free");
		Double authAmount4_free = (Double)subAccountCountMap.get("t1YearAuthAmount4Free");
		Double t7DayAuthAmount_free = (Double)subAccountCountMap.get("t7DayAuthAmount4Free");
		
		Double yearAuthAmount_30_free = MoneyUtil.divide(MoneyUtil.multiply(authAmount1_free,30).doubleValue(), 365, 2).doubleValue();
		Double yearAuthAmount_90_free = MoneyUtil.divide(MoneyUtil.multiply(authAmount2_free,90).doubleValue(), 365, 2).doubleValue();
		Double yearAuthAmount_180_free = MoneyUtil.divide(MoneyUtil.multiply(authAmount3_free,180).doubleValue(), 365, 2).doubleValue();
		Double yearAuthAmount_7_free = MoneyUtil.divide(MoneyUtil.multiply(t7DayAuthAmount_free,7).doubleValue(), 365, 2).doubleValue();
		Double yearAuthAmount_365_free = MoneyUtil.divide(MoneyUtil.multiply(authAmount4_free,365).doubleValue(), 365, 2).doubleValue();
		
		//自由产品计划年化
		Double sumAuthAmoutFree = CalculatorUtil.calculate("a+a+a+a+a", yearAuthAmount_30_free, yearAuthAmount_90_free, yearAuthAmount_180_free, yearAuthAmount_365_free, yearAuthAmount_7_free);
		res.setT1MonthAuthAmount4Free(authAmount1_free);
		res.setT3MonthAuthAmount4Free(authAmount2_free);
		res.setT6MonthAuthAmount4Free(authAmount3_free);
		res.setT1YearAuthAmount4Free(authAmount4_free);
		res.setT7DayAuthAmount4Free(t7DayAuthAmount_free);
		res.settYearAllAuthAmount4Free(sumAuthAmoutFree);
		res.settAllAuthAmount4Free(CalculatorUtil.calculate("a+a+a+a+a", authAmount1_free, authAmount2_free, authAmount3_free, authAmount4_free, t7DayAuthAmount_free));

		
		//云贷存管
		Double authAmount1 = (Double)subAccountCountMap.get("t1MonthAuthAmount");
		Double authAmount2 = (Double)subAccountCountMap.get("t3MonthAuthAmount");
		Double authAmount3 = (Double)subAccountCountMap.get("t6MonthAuthAmount");
		Double authAmount4 = (Double)subAccountCountMap.get("t1YearAuthAmount");
		Double t7DayAuthAmount = (Double)subAccountCountMap.get("t7DayAuthAmount");
		
		Double yearAuthAmount_30 = MoneyUtil.divide(MoneyUtil.multiply(authAmount1,30).doubleValue(), 365, 2).doubleValue();
		Double yearAuthAmount_90 = MoneyUtil.divide(MoneyUtil.multiply(authAmount2,90).doubleValue(), 365, 2).doubleValue();
		Double yearAuthAmount_180 = MoneyUtil.divide(MoneyUtil.multiply(authAmount3,180).doubleValue(), 365, 2).doubleValue();
		Double yearAuthAmount_7 = MoneyUtil.divide(MoneyUtil.multiply(t7DayAuthAmount,7).doubleValue(), 365, 2).doubleValue();
		Double yearAuthAmount_365 = MoneyUtil.divide(MoneyUtil.multiply(authAmount4,365).doubleValue(), 365, 2).doubleValue();
		
		//云贷存管年化
		Double sumAuthAmout = CalculatorUtil.calculate("a+a+a+a+a", yearAuthAmount_30, yearAuthAmount_90, yearAuthAmount_180, yearAuthAmount_365, yearAuthAmount_7);
		res.setT1MonthAuthAmount(authAmount1);
		res.setT3MonthAuthAmount(authAmount2);
		res.setT6MonthAuthAmount(authAmount3);
		res.setT1YearAuthAmount(authAmount4);
		res.setT7DayAuthAmount(t7DayAuthAmount);
		res.settYearAllAuthAmount(sumAuthAmout);
		res.settAllAuthAmountYundai(CalculatorUtil.calculate("a+a+a+a+a", authAmount1, authAmount2, authAmount3, authAmount4, t7DayAuthAmount));
				
		//7贷存管
		Double authAmount1_7dai = (Double)subAccountCountMap.get("t1MonthAuthAmount7dai");
		Double authAmount3_7dai = (Double)subAccountCountMap.get("t3MonthAuthAmount7dai");
		Double authAmount6_7dai = (Double)subAccountCountMap.get("t6MonthAuthAmount7dai");
		Double authAmount12_7dai = (Double)subAccountCountMap.get("t1YearAuthAmount7dai");
		Double t7DayAuthAmount_7dai = (Double)subAccountCountMap.get("t7DayAuthAmount7dai");
		
		Double yearAuthAmount_1_7dai = MoneyUtil.divide(MoneyUtil.multiply(authAmount1_7dai,30).doubleValue(), 365, 2).doubleValue();
		Double yearAuthAmount_3_7dai = MoneyUtil.divide(MoneyUtil.multiply(authAmount3_7dai,90).doubleValue(), 365, 2).doubleValue();
		Double yearAuthAmount_6_7dai = MoneyUtil.divide(MoneyUtil.multiply(authAmount6_7dai,180).doubleValue(), 365, 2).doubleValue();
		Double yearAuthAmount_365_7dai = MoneyUtil.divide(MoneyUtil.multiply(authAmount12_7dai,365).doubleValue(), 365, 2).doubleValue();
		Double yearAuthAmount_7_7dai = MoneyUtil.divide(MoneyUtil.multiply(t7DayAuthAmount_7dai,7).doubleValue(), 365, 2).doubleValue();
		
		//7贷存管年化
		Double sumAuthAmout_7dai = CalculatorUtil.calculate("a+a+a+a+a", yearAuthAmount_1_7dai, yearAuthAmount_3_7dai, yearAuthAmount_6_7dai, yearAuthAmount_365_7dai, yearAuthAmount_7_7dai);
		res.setT1MonthAuthAmount7dai(authAmount1_7dai);
		res.setT3MonthAuthAmount7dai(authAmount3_7dai);
		res.setT6MonthAuthAmount7dai(authAmount6_7dai);
		res.setT1YearAuthAmount7dai(authAmount12_7dai);
		res.setT7DayAuthAmount7dai(t7DayAuthAmount_7dai);
		res.settAllAuthAmount7dai(CalculatorUtil.calculate("a+a+a+a+a", authAmount1_7dai, authAmount3_7dai, authAmount6_7dai, authAmount12_7dai, t7DayAuthAmount_7dai));
		res.settYearAllAuthAmount7dai(sumAuthAmout_7dai);

	}
	
	public void financeTotal(ReqMsg_Statistics_FinanceTotal req,ResMsg_Statistics_FinanceTotal res) {
		//统计奖励金户余额
		double bonusAccBalance = bsSubAccountService.countBonusAccBalance(); // 用户未转奖励金
		res.setBonusAccBalance(bonusAccBalance);
		
		//统计投资用户、投资金额、成交笔数
		Map<String,Object> sysSubAccountCountMap = mBsSysSubAccountService.countSysSubAccountBalance();
		res.setSysAccBalance((Double)sysSubAccountCountMap.get("sysAccBalance"));
		res.setSysFreezeAccBalance((Double)sysSubAccountCountMap.get("sysFreezeAccBalance"));
		res.setAccBalance((Double)sysSubAccountCountMap.get("accBalance"));
		res.setProAccBalance((Double)sysSubAccountCountMap.get("proAccBalance"));
		res.setRetAccBalance((Double)sysSubAccountCountMap.get("retAccBalance"));
		res.setRedPaktAccBalance((Double)sysSubAccountCountMap.get("redPaktAccBalance"));
		res.setPro7AccBalance((Double)sysSubAccountCountMap.get("pro7AccBalance"));
		res.setRet7AccBalance((Double)sysSubAccountCountMap.get("ret7AccBalance"));
		// 赞分期相关
		res.setProAuthZanAccBalance((Double)sysSubAccountCountMap.get("proAuthZanAccBalance"));
		res.setProZanAccBalance((Double)sysSubAccountCountMap.get("proZanAccBalance"));
		res.setRetZanAccBalance((Double)sysSubAccountCountMap.get("retZanAccBalance"));
		res.setMarginZanAccBalance((Double)sysSubAccountCountMap.get("marginZanAccBalance"));
		res.setRevenueZanAccBalance((Double)sysSubAccountCountMap.get("revenueZanAccBalance"));
		res.setBadloansZanAccBalance((Double)sysSubAccountCountMap.get("badloansZanAccBalance"));
		res.setRevenueBgwAccBalance((Double)sysSubAccountCountMap.get("revenueBgwAccBalance"));
		res.setFeeBgwAccBalance((Double)sysSubAccountCountMap.get("feeBgwAccBalance"));
		
	}
	
	/**
	 * 宝付日终账务查询
	 * @param req
	 * @param res
	 */
	public void baofooDailySnapQuery(ReqMsg_Statistics_BGWDailySnapQuery req, ResMsg_Statistics_BGWDailySnapQuery res) {
		Integer pageNum = req.getPageNum();
		Integer numPerPage = req.getNumPerPage();
		BGWDailySnapVO vo = new BGWDailySnapVO();
		vo.setPageNum(pageNum);
		vo.setNumPerPage(numPerPage);
		int totalRows = bsSysBalanceDailySnapService.queryBaofooSysBalanceDailySnapCount();
		if(totalRows > 0) {
			List<HashMap<String, Object>> resultList = bsSysBalanceDailySnapService.findBaofooSysBalanceDailySnap(vo);
			res.setValueList(resultList);
		}
		res.setPageNum(req.getPageNum());
		res.setNumPerPage(req.getNumPerPage());
		res.setTotalRows(totalRows);
	}
	
	/**
	 * 币港湾日终账务查询
	 * @param req
	 * @param res
	 */
	public void bgwDailySnapQuery(ReqMsg_Statistics_BGWDailySnapQuery req, ResMsg_Statistics_BGWDailySnapQuery res) {
		Integer pageNum = req.getPageNum();
		Integer numPerPage = req.getNumPerPage();
		BGWDailySnapVO vo = new BGWDailySnapVO();
		vo.setPageNum(pageNum);
		vo.setNumPerPage(numPerPage);
		int totalRows = bsSysBalanceDailySnapService.queryBgwSysBalanceDailySnapCount();
		if(totalRows > 0) {
			List<HashMap<String, Object>> resultList = bsSysBalanceDailySnapService.findBgwSysBalanceDailySnap(vo);
			res.setValueList(resultList);
		}
		res.setPageNum(req.getPageNum());
		res.setNumPerPage(req.getNumPerPage());
		res.setTotalRows(totalRows);
	}
	
	/**
	 * 恒丰日终账务查询
	 * @param req
	 * @param res
	 */
	public void hFDailySnapQuery(ReqMsg_Statistics_HFDailySnapQuery req, ResMsg_Statistics_HFDailySnapQuery res) {
		Integer pageNum = req.getPageNum();
		Integer numPerPage = req.getNumPerPage();
		HFDailySnapVO vo = new HFDailySnapVO();
		vo.setPageNum(pageNum);
		vo.setNumPerPage(numPerPage);
		int totalRows = bsSysBalanceDailySnapService.queryHfSysBalanceDailySnapCount();
		if(totalRows > 0) {
			List<HashMap<String, Object>> resultList = bsSysBalanceDailySnapService.findHfSysBalanceDailySnap(vo);
			res.setValueList(resultList);
		}
		res.setPageNum(req.getPageNum());
		res.setNumPerPage(req.getNumPerPage());
		res.setTotalRows(totalRows);
	}
	
	/**
	 * 财务总账查询(宝付)
	 * @param req
	 * @param res
	 */
	public void thdFinanceTotal(ReqMsg_Statistics_ThdFinanceTotal req, ResMsg_Statistics_ThdFinanceTotal res) {
		//统计奖励金户余额
		double bonusAccBalance = bsSubAccountService.countBonusAccBalance(); // 用户未转奖励金
		res.setBonusAccBalance(bonusAccBalance);
		// 查看ResMsg_Statistics_ThdFinanceTotal对应注释
		Map<String,Object> sysSubAccountCountMap = mBsSysSubAccountService.countThdSysSubAccountBalance();
		res.setSysAccBalance((Double)sysSubAccountCountMap.get("sysAccBalance"));
		res.setSysFreezeAccBalance((Double)sysSubAccountCountMap.get("sysFreezeAccBalance"));
		res.setAccBalance((Double)sysSubAccountCountMap.get("accBalance"));
		res.setProAccBalance((Double)sysSubAccountCountMap.get("proAccBalance"));
		res.setRetAccBalance((Double)sysSubAccountCountMap.get("retAccBalance"));
		res.setRedPaktAccBalance((Double)sysSubAccountCountMap.get("redPaktAccBalance"));
		res.setPro7AccBalance((Double)sysSubAccountCountMap.get("pro7AccBalance"));
		res.setRet7AccBalance((Double)sysSubAccountCountMap.get("ret7AccBalance"));
		res.setProAuthZanAccBalance((Double)sysSubAccountCountMap.get("proAuthZanAccBalance"));
		res.setProZanAccBalance((Double)sysSubAccountCountMap.get("proZanAccBalance"));
		res.setRetZanAccBalance((Double)sysSubAccountCountMap.get("retZanAccBalance"));
		res.setMarginZanAccBalance((Double)sysSubAccountCountMap.get("marginZanAccBalance"));
		res.setRevenueZanAccBalance((Double)sysSubAccountCountMap.get("revenueZanAccBalance"));
		res.setBadloansZanAccBalance((Double)sysSubAccountCountMap.get("badloansZanAccBalance"));
		res.setRevenueBgwAccBalance((Double)sysSubAccountCountMap.get("revenueBgwAccBalance"));
		res.setFeeBgwAccBalance((Double)sysSubAccountCountMap.get("feeBgwAccBalance"));
		res.setRevenueBgwYunAccBalance((Double)sysSubAccountCountMap.get("revenueBgwYunAccBalance"));
		res.setRevenueYunAccBalance((Double)sysSubAccountCountMap.get("revenueYunAccBalance"));
		res.setRepayAccBalance((Double)sysSubAccountCountMap.get("repayAccBalance"));
		res.setRevenueBgw7AccBalance((Double)sysSubAccountCountMap.get("revenueBgw7AccBalance"));
		res.setRevenue7AccBalance((Double)sysSubAccountCountMap.get("revenue7AccBalance"));

		//赞时贷相关
		Double sumBgwAuthZsdBalance = bsSubAccountService.querySumBgwAuthZsdBalance(); //赞时贷站岗户余额
		res.setBgwAuthZsdAccBalance(sumBgwAuthZsdBalance == null ? 0d : sumBgwAuthZsdBalance);
		res.setBgwRegZsdAccBalance((Double)sysSubAccountCountMap.get("bgwRegZsdAccBalance"));
		res.setBgwReturnZsdAccBalance((Double)sysSubAccountCountMap.get("bgwReturnZsdAccBalance"));
		res.setBgwAuthRedZsdAccBalance((Double)sysSubAccountCountMap.get("bgwAuthRedZsdAccBalance"));

		res.setRevenueBgwZsdAccBalance((Double)sysSubAccountCountMap.get("revenueBgwZsdAccBalance"));
		res.setMarginZsdAccBalance((Double)sysSubAccountCountMap.get("marginZsdAccBalance"));
		res.setRevenueZsdAccBalance((Double)sysSubAccountCountMap.get("revenueZsdAccBalance"));		
	}
	
	/**
	 * 财务总账查询(恒丰)
	 * @param req
	 * @param res
	 */
	public void depFinanceTotal(ReqMsg_Statistics_DepFinanceTotal req, ResMsg_Statistics_DepFinanceTotal res) {
		Double redAccBalance = bsSubAccountService.countRedAccBalance(); // 云贷产品站岗红包
		res.setDepRedAccBalance(redAccBalance);
		Double red7AccBalance = bsSubAccountService.countRed7AccBalance(); // 七贷产品站岗红包
		res.setDepRed7AccBalance(red7AccBalance);

		Double sumBgwAuthYunBalance = bsSubAccountService.querySumBgwAuthYunBalance(); // 云贷站岗户余额
		Double sumBgwAuthSevrnBalance = bsSubAccountService.querySumBgwAuthSevrnBalance(); // 7贷站岗户余额
		Double sumBgwAuthZanBalance = bsSubAccountService.querySumBgwAuthZanBalance(); // 赞分期站岗户余额
		
		// 查看ResMsg_Statistics_DepFinanceTotal对应注释
		Map<String,Object> sysDepSubAccountCountMap = mBsSysSubAccountService.countDepSysSubAccountBalance();
		res.setDepSysAccBalance((Double)sysDepSubAccountCountMap.get("depSysAccBalance"));
		res.setDepUserAccBalance((Double)sysDepSubAccountCountMap.get("depUserAccBalance"));
		res.setDepAuthYunAccBalance(sumBgwAuthYunBalance == null ? 0d : sumBgwAuthYunBalance);
		res.setDepAuthZanAccBalance(sumBgwAuthZanBalance == null ? 0d : sumBgwAuthZanBalance);
		res.setDepHeadFeeAccBalance((Double)sysDepSubAccountCountMap.get("depHeadFeeAccBalance"));
		res.setDepOtherFeeAccBalance((Double)sysDepSubAccountCountMap.get("depOtherFeeAccBalance"));
		res.setDepProRegAccBalance((Double)sysDepSubAccountCountMap.get("depProRegAccBalance"));
		res.setDepRedPaktAccBalance((Double)sysDepSubAccountCountMap.get("depRedPaktAccBalance"));
		res.setDepRegZanAccBalance((Double)sysDepSubAccountCountMap.get("depRegZanAccBalance"));
		res.setDepRetZanAccBalance((Double)sysDepSubAccountCountMap.get("depRetZanAccBalance"));
		res.setDepRetAccBalance((Double)sysDepSubAccountCountMap.get("depRetAccBalance"));
		res.setDeprevenueYunAccBalance((Double)sysDepSubAccountCountMap.get("deprevenueYunAccBalance"));
		res.setDepRevenueZanAccBalance((Double)sysDepSubAccountCountMap.get("depRevenueZanAccBalance"));
		res.setDepAdvanceAccBalance((Double)sysDepSubAccountCountMap.get("depAdvanceAccBalance"));
		res.setDepRedPaktAccBalance((Double)sysDepSubAccountCountMap.get("depRedPaktAccBalance"));
		//新增七贷
		res.setDepProReg7AccBalance((Double)sysDepSubAccountCountMap.get("depProReg7AccBalance"));
		res.setDepRet7AccBalance((Double)sysDepSubAccountCountMap.get("depRet7AccBalance"));
		res.setDepAuth7AccBalance(sumBgwAuthSevrnBalance == null ? 0d : sumBgwAuthSevrnBalance);
		res.setDeprevenue7AccBalance((Double)sysDepSubAccountCountMap.get("deprevenue7AccBalance"));
		
		//赞时贷相关
		res.setDepHeadFeeZsdAccBalance((Double)sysDepSubAccountCountMap.get("depHeadFeeZsdAccBalance"));
		res.setDepRevenueZsdAccBalance((Double)sysDepSubAccountCountMap.get("depRevenueZsdAccBalance"));

		// 财务总账查询（恒丰）–新增借款人余额
		Double sumLoanBalance = bsSubAccountService.querySumLoanBalance();
		res.setSumLoanBalance(sumLoanBalance);

		// 新增自由站岗户业务数据
		res.setDepFreeAccBalance((Double)sysDepSubAccountCountMap.get("depFreeAccBalance")); // 自由产品户余额
		Double depAuthFreeAccBalance = bsSubAccountService.querySumBgwAuthFreeBalance(); // 自由站岗户余额
		res.setDepAuthFreeAccBalance(depAuthFreeAccBalance);
		Double depRedFreeAccBalance = bsSubAccountService.querySumRedFreeAccBalance(); // 自有产品站岗红包
		res.setDepRedFreeAccBalance(depRedFreeAccBalance);

	}
	
	
	/**
	 * 渠道业绩统计
	 * @param req
	 * @param res
	 */
	public void agentPerformance(ReqMsg_Statistics_AgentPerformance req, ResMsg_Statistics_AgentPerformance res) {
		int totalRows = agentService.countAgentStatList();
		if (totalRows > 0) {
			if (req.getQueryDefaultPageFlag() != null && req.getQueryDefaultPageFlag().equals("DEFAULTPAGE")) {
				List<AgentStatInfoVO> agentStatInfoVO = agentService.findPerformanceList(req.getBeginTime(), req.getOverTime(), req.getDept(), req.getTerminal(),
						req.getPageNum(), req.getNumPerPage(), req.getOrderDirection(), req.getOrderField());
				// 1、获得所有渠道ID(31个)
				Map<Integer, Integer> map = new HashMap<Integer, Integer>();
				for (AgentStatInfoVO agentStatInfoVO2 : agentStatInfoVO) {
					map.put(agentStatInfoVO2.getId(), agentStatInfoVO2.getId());
				}
				// 2、渠道循环
				List<AgentStatInfoVO> agentVoList = new ArrayList<AgentStatInfoVO>(); // 先保存渠道ID、再存放处理过的对象
				for (Integer agentId : map.keySet()) {
					AgentStatInfoVO infoVO = new AgentStatInfoVO();
					infoVO.setId(agentId);
					agentVoList.add(infoVO);
				}
				// 年化交易金额 (-7、1、3、6、12产品年化收益总计) proceedsBalance 7天+1个月  (30/365) + 3个月 (90/365) + 6个月 (180/365) + 12个月
				for (AgentStatInfoVO vo1 : agentVoList) { // 所有渠道ID 31
					for (AgentStatInfoVO vo2 : agentStatInfoVO) { // 按渠道id、期限分组查询数据  39
						if(vo1.getId() == vo2.getId()) {
							vo1.setAgentName(vo2.getAgentName());
							vo1.setPageViewTimes(vo2.getPageViewTimes());
							vo1.setRegistUserCount(vo2.getRegistUserCount());
							vo1.setCreateTime(vo2.getCreateTime());
							vo1.setDept(vo2.getDept());
							vo1.setInvestUserCount(vo2.getInvestUserCount());
							vo1.setTransItemCount(vo1.getTransItemCount() == null ? (vo2.getTransItemCount() == null ? 0 : vo2.getTransItemCount()) :
								(vo1.getTransItemCount() + (vo2.getTransItemCount() == null ? 0 : vo2.getTransItemCount())));
							vo1.setTransAmountCount(vo1.getTransAmountCount() == null ? (vo2.getTransAmountCount() == null ? 0 : vo2.getTransAmountCount()) : 
								(vo1.getTransAmountCount() + (vo2.getTransAmountCount() == null ? 0 : vo2.getTransAmountCount())));
//							vo1.setInvestUserCount(vo1.getInvestUserCount() == null ? (vo2.getInvestUserCount() == null ? 0 : vo2.getInvestUserCount()) : 
//								(vo1.getInvestUserCount() + (vo2.getInvestUserCount() == null ? 0 : vo2.getInvestUserCount())));
							vo1.setProceedsBalanceTotal(vo1.getOneProceedsBalance() == null ? (vo2.getOneProceedsBalance() == null ? 0 : vo2.getOneProceedsBalance()) :
								(vo1.getOneProceedsBalance() + (vo2.getOneProceedsBalance() == null ? 0 : vo2.getOneProceedsBalance())));
							if(vo2.getTerm() != null) {
								switch (vo2.getTerm()) {
								case -7:
//									vo1.setOneInvestUserCount(vo2.getInvestUserCount()); // 投资用户数
									vo1.setSevenDayTransItemCount(vo2.getTransItemCount()); // 交易笔数
									vo1.setSevenDayBalance(vo2.getTransAmountCount()); // 交易金额
									vo1.setSevenDayProceedsBalance(vo2.getTransAmountCount()*7/365); // 年化交易金额
									break;
								
								case 1:
//									vo1.setOneInvestUserCount(vo2.getInvestUserCount()); // 投资用户数
									vo1.setOneTransItemCount(vo2.getTransItemCount()); // 交易笔数
									vo1.setOneMonthBalance(vo2.getTransAmountCount()); // 交易金额
									vo1.setOneProceedsBalance(vo2.getTransAmountCount()*30/365); // 年化交易金额
									break;
								case 3:
//									vo1.setThreeInvestUserCount(vo2.getInvestUserCount());
									vo1.setThreeTransItemCount(vo2.getTransItemCount());
									vo1.setThreeMonthBalance(vo2.getTransAmountCount());
									vo1.setThreeProceedsBalance(vo2.getTransAmountCount()*90/365);
									break;
								case 6:
//									vo1.setSixInvestUserCount(vo2.getInvestUserCount());
									vo1.setSixTransItemCount(vo2.getTransItemCount());
									vo1.setSixMonthBalance(vo2.getTransAmountCount());
									vo1.setSixProceedsBalance(vo2.getTransAmountCount()*180/365);
									break;
								case 12:
//									vo1.setTwelveInvestUserCount(vo2.getInvestUserCount());
									vo1.setTwelveTransItemCount(vo2.getTransItemCount());
									vo1.setTwelveMonthBalance(vo2.getTransAmountCount());
									vo1.setTwelveProceedsBalance(vo2.getTransAmountCount());
									break;
								default:
									break;
								}
								Double oneProceedsBalance = vo1.getOneProceedsBalance() != null ? vo1.getOneProceedsBalance() : 0;
								Double threeProceedsBalance = vo1.getThreeProceedsBalance() != null ? vo1.getThreeProceedsBalance() : 0;
								Double sixProceedsBalance = vo1.getSixProceedsBalance() != null ? vo1.getSixProceedsBalance() : 0;
								Double twelveProceedsBalance = vo1.getTwelveProceedsBalance() != null ? vo1.getTwelveProceedsBalance() : 0;
								Double sevenDayProceedsBalance = vo1.getSevenDayProceedsBalance() != null ? vo1.getSevenDayProceedsBalance() : 0;
								vo1.setProceedsBalance(MoneyUtil.add(oneProceedsBalance,
										MoneyUtil.add(threeProceedsBalance,
												MoneyUtil.add(sixProceedsBalance,
														MoneyUtil.add(twelveProceedsBalance,sevenDayProceedsBalance).doubleValue()).doubleValue()).doubleValue()).doubleValue());
								/*vo1.setProceedsBalance((vo1.getOneProceedsBalance() != null ? vo1.getOneProceedsBalance() : 0)
										+ (vo1.getThreeProceedsBalance() != null ? vo1.getThreeProceedsBalance() : 0) 
										+ (vo1.getSixProceedsBalance() != null ? vo1.getSixProceedsBalance() : 0) 
										+ (vo1.getTwelveProceedsBalance() != null ? vo1.getTwelveProceedsBalance() : 0)
										+ (vo1.getSevenDayProceedsBalance() != null ? vo1.getSevenDayProceedsBalance() : 0));*/
							} else {
								vo1.setProceedsBalance(0.00);
							}
						}
					}
				}
				// 部门排序   compare(obj1, obj2)第一个参数小于、等于或大于第二个参数分别返回负整数、零或正整数
				try {
					Collections.sort(agentVoList, new Comparator<AgentStatInfoVO>() {
			            public int compare(AgentStatInfoVO o1, AgentStatInfoVO o2) {
			            	if(StringUtils.isBlank(o1.getDept()) && !StringUtils.isBlank(o2.getDept())) {
			            		return 1;
			            	} else if(!StringUtils.isBlank(o1.getDept()) && StringUtils.isBlank(o2.getDept())){
			            		return -1;
			            	} else {
			            		return o1.getDept().compareTo(o2.getDept());
			            	}
			            }
			        });
				} catch (Exception e) {
					log.error("======请给渠道添加对应的部门======");
				}
				
				if(StringUtil.isNotBlank(req.getOrderDirection()) && "asc".equals(req.getOrderDirection())) { // 年化交易额 升序、降序排序
					Collections.sort(agentVoList, new Comparator<AgentStatInfoVO>() {
						@Override
						public int compare(AgentStatInfoVO o1, AgentStatInfoVO o2) {
							/*if(o1.getProceedsBalance() > o2.getProceedsBalance()){
								return -1;
							} else {
								return 1;
							}*/
							return o1.getProceedsBalance().compareTo(o2.getProceedsBalance());
						}
					});
				} else if(StringUtil.isNotBlank(req.getOrderDirection()) && "desc".equals(req.getOrderDirection())) {
					Collections.sort(agentVoList, new Comparator<AgentStatInfoVO>() {
						@Override
						public int compare(AgentStatInfoVO o1, AgentStatInfoVO o2) {
							/*if(o1.getProceedsBalance() > o2.getProceedsBalance()){
								return 1;
							} else {
								return -1;
							}*/
							return o2.getProceedsBalance().compareTo(o1.getProceedsBalance());
						}
					});
				}
				double total = 0.00;
				for (int i = 0;i<agentVoList.size();i++) {
					double proceedsBalance= agentVoList.get(i).getProceedsBalance();
					total = MoneyUtil.add(total,proceedsBalance).doubleValue();
					res.setProceedsBalanceTotal(total);
				}
				res.setValueList(BeanUtils.classToArrayList(agentVoList));
			} else { // 默认显示200条记录,每页按200记录数查询
				List<AgentStatInfoVO> agentStatInfoVO = agentService.findPerformanceList(req.getBeginTime(), req.getOverTime(), req.getDept(), 
						req.getPageNum(), 200, req.getOrderDirection(), req.getOrderField());
				// 1、获得所有渠道ID(31个)
				Map<Integer, Integer> map = new HashMap<Integer, Integer>();
				for (AgentStatInfoVO agentStatInfoVO2 : agentStatInfoVO) {
					map.put(agentStatInfoVO2.getId(), agentStatInfoVO2.getId());
				}
				// 2、渠道循环
				List<AgentStatInfoVO> agentVoList = new ArrayList<AgentStatInfoVO>(); // 存放处理过的对象
				for (Integer agentId : map.keySet()) {
					AgentStatInfoVO infoVO = new AgentStatInfoVO();
					infoVO.setId(agentId);
					agentVoList.add(infoVO);
				}
				for (AgentStatInfoVO vo1 : agentVoList) { // 所有渠道ID 31
					for (AgentStatInfoVO vo2 : agentStatInfoVO) { // 按渠道id、期限分组查询数据  39
						if(vo1.getId() == vo2.getId()) {
							vo1.setAgentName(vo2.getAgentName());
							vo1.setPageViewTimes(vo2.getPageViewTimes());
							vo1.setRegistUserCount(vo2.getRegistUserCount());
							vo1.setCreateTime(vo2.getCreateTime());
							vo1.setDept(vo2.getDept());
							vo1.setInvestUserCount(vo2.getInvestUserCount());
							vo1.setTransItemCount(vo1.getTransItemCount() == null ? (vo2.getTransItemCount() == null ? 0 : vo2.getTransItemCount()) :
								(vo1.getTransItemCount() + (vo2.getTransItemCount() == null ? 0 : vo2.getTransItemCount())));
							vo1.setTransAmountCount(vo1.getTransAmountCount() == null ? (vo2.getTransAmountCount() == null ? 0 : vo2.getTransAmountCount()) : 
								(vo1.getTransAmountCount() + (vo2.getTransAmountCount() == null ? 0 : vo2.getTransAmountCount())));
//							vo1.setInvestUserCount(vo1.getInvestUserCount() == null ? (vo2.getInvestUserCount() == null ? 0 : vo2.getInvestUserCount()) : 
//								(vo1.getInvestUserCount() + (vo2.getInvestUserCount() == null ? 0 : vo2.getInvestUserCount())));
							vo1.setProceedsBalanceTotal(vo1.getOneProceedsBalance() == null ? (vo2.getOneProceedsBalance() == null ? 0 : vo2.getOneProceedsBalance()) :
								(MoneyUtil.add(vo1.getOneProceedsBalance(), (vo2.getOneProceedsBalance() == null ? 0 : vo2.getOneProceedsBalance())).doubleValue()));
							if(vo2.getTerm() != null) {
								switch (vo2.getTerm()) {
								case -7:
//									vo1.setOneInvestUserCount(vo2.getInvestUserCount()); // 投资用户数
									vo1.setSevenDayTransItemCount(vo2.getTransItemCount()); // 交易笔数
									vo1.setSevenDayBalance(vo2.getTransAmountCount()); // 交易金额
									Double amount_7 = MoneyUtil.divide(MoneyUtil.multiply(vo2.getTransAmountCount(),7).doubleValue(), 365, 2).doubleValue();
									vo1.setSevenDayProceedsBalance(amount_7); // 年化交易金额
									//vo1.setSevenDayProceedsBalance(vo2.getTransAmountCount()*7/365);
									break;
								
								case 1:
//									vo1.setOneInvestUserCount(vo2.getInvestUserCount()); // 投资用户数
									vo1.setOneTransItemCount(vo2.getTransItemCount()); // 交易笔数
									vo1.setOneMonthBalance(vo2.getTransAmountCount()); // 交易金额
									Double amount_1 = MoneyUtil.divide(MoneyUtil.multiply(vo2.getTransAmountCount(),30).doubleValue(), 365, 2).doubleValue();
									//vo1.setOneProceedsBalance(vo2.getTransAmountCount()*30/365); 
									vo1.setOneProceedsBalance(amount_1); // 年化交易金额
									break;// Double.parseDouble(MoneyUtil.format())
								case 3:
//									vo1.setThreeInvestUserCount(vo2.getInvestUserCount());
									vo1.setThreeTransItemCount(vo2.getTransItemCount());
									vo1.setThreeMonthBalance(vo2.getTransAmountCount());
									Double amount_3 = MoneyUtil.divide(MoneyUtil.multiply(vo2.getTransAmountCount(),90).doubleValue(), 365, 2).doubleValue();
									vo1.setThreeProceedsBalance(amount_3);
									break;
								case 6:
//									vo1.setSixInvestUserCount(vo2.getInvestUserCount());
									vo1.setSixTransItemCount(vo2.getTransItemCount());
									vo1.setSixMonthBalance(vo2.getTransAmountCount());
									Double amount_6 = MoneyUtil.divide(MoneyUtil.multiply(vo2.getTransAmountCount(),180).doubleValue(), 365, 2).doubleValue();
									vo1.setSixProceedsBalance(amount_6);
									break;
								case 12:
//									vo1.setTwelveInvestUserCount(vo2.getInvestUserCount());
									vo1.setTwelveTransItemCount(vo2.getTransItemCount());
									vo1.setTwelveMonthBalance(vo2.getTransAmountCount());
									vo1.setTwelveProceedsBalance(vo2.getTransAmountCount());
									break;
								default:
									break;
								}
								
								Double balance_1 = vo1.getOneProceedsBalance() != null ? vo1.getOneProceedsBalance() : 0;
								Double balance_3 = vo1.getThreeProceedsBalance() != null ? vo1.getThreeProceedsBalance() : 0;
								Double balance_6 = vo1.getSixProceedsBalance() != null ? vo1.getSixProceedsBalance() : 0;
								Double balance_12 = vo1.getTwelveProceedsBalance() != null ? vo1.getTwelveProceedsBalance() : 0;
								Double balance_7 = vo1.getSevenDayProceedsBalance() != null ? vo1.getSevenDayProceedsBalance() : 0;
								vo1.setProceedsBalance(MoneyUtil.add(balance_1,
										MoneyUtil.add(balance_3,
												MoneyUtil.add(balance_6,
														MoneyUtil.add(balance_12,balance_7).doubleValue()).doubleValue()).doubleValue()).doubleValue());
								/*vo1.setProceedsBalance((vo1.getOneProceedsBalance() != null ? vo1.getOneProceedsBalance() : 0)
										+ (vo1.getThreeProceedsBalance() != null ? vo1.getThreeProceedsBalance() : 0) 
										+ (vo1.getSixProceedsBalance() != null ? vo1.getSixProceedsBalance() : 0) 
										+ (vo1.getTwelveProceedsBalance() != null ? vo1.getTwelveProceedsBalance() : 0)
										+ (vo1.getSevenDayProceedsBalance() != null ? vo1.getSevenDayProceedsBalance() : 0));*/
							} else {
								vo1.setProceedsBalance(0.00);
							}
						}
					}
				}
				// 部门排序   
				try {
					Collections.sort(agentVoList, new Comparator<AgentStatInfoVO>() {
			            public int compare(AgentStatInfoVO o1, AgentStatInfoVO o2) {
			            	if(StringUtils.isBlank(o1.getDept()) && !StringUtils.isBlank(o2.getDept())) {
			            		return 1;
			            	} else if(!StringUtils.isBlank(o1.getDept()) && StringUtils.isBlank(o2.getDept())){
			            		return -1;
			            	} else {
			            		return o1.getDept().compareTo(o2.getDept());
			            	}
			            }
			        });
				} catch (Exception e) {
					log.error("======请给渠道添加对应的部门======");
				}
				
				if(StringUtil.isNotBlank(req.getOrderDirection()) && "asc".equals(req.getOrderDirection())) { // 年化交易额 升序、降序排序
					Collections.sort(agentVoList, new Comparator<AgentStatInfoVO>() {
						@Override
						public int compare(AgentStatInfoVO o1, AgentStatInfoVO o2) {
							/*if(o1.getProceedsBalance() > o2.getProceedsBalance()){
								return -1;
							} else {
								return 1;
							}*/
							return o1.getProceedsBalance().compareTo(o2.getProceedsBalance());
						}
					});
				} else if(StringUtil.isNotBlank(req.getOrderDirection()) && "desc".equals(req.getOrderDirection())) {
					Collections.sort(agentVoList, new Comparator<AgentStatInfoVO>() {
						@Override
						public int compare(AgentStatInfoVO o1, AgentStatInfoVO o2) {
							/*if(o1.getProceedsBalance() > o2.getProceedsBalance()){
								return 1;
							} else {
								return -1;
							}*/
							return o2.getProceedsBalance().compareTo(o1.getProceedsBalance());
						}
					});
				}
				double total = 0.00;
				for (int i = 0;i<agentVoList.size();i++) {
					double proceedsBalance= agentVoList.get(i).getProceedsBalance();
					total= MoneyUtil.add(total, proceedsBalance).doubleValue();
					res.setProceedsBalanceTotal(total);
				}
				res.setValueList(BeanUtils.classToArrayList(agentVoList));
			}
		}
		List<BsAgent> agents = agentService.findAgentDeptList(new BsAgent());
		res.setAgentList(BeanUtils.classToArrayList(agents));
		res.setTotalRows(totalRows);
		res.setPageNum(req.getPageNum());
		res.setNumPerPage(req.getNumPerPage());
	}
	
	public void userInvestQuery(ReqMsg_Statistics_UserInvestQuery req,ResMsg_Statistics_UserInvestQuery res) {
		BsUserInvestViewVO userInvestViewVO = new BsUserInvestViewVO();
		userInvestViewVO.setNumPerPage(req.getNumPerPage());
		userInvestViewVO.setPageNum(req.getPageNum());
		if(req.getStartTime() != null) {
			userInvestViewVO.setStartTime(req.getStartTime());
		}
		if(req.getEndTime() != null) {
			userInvestViewVO.setEndTime(req.getEndTime());
		}
		
		int totalRows = userInvestViewService.findUserInvestAllCount(userInvestViewVO);
		if(totalRows > 0) {
			List<BsUserInvestView> list = userInvestViewService.findUserInvestList(userInvestViewVO);
			res.setUserInvestList(BeanUtils.classToArrayList(list));
		}
		res.setTotalRows(totalRows);
	}
	
	
	public void registerViewQuery(ReqMsg_Statistics_RegisterViewQuery req, ResMsg_Statistics_RegisterViewQuery res){
		BsUserRegistViewVO bsUserRegistViewVO = new BsUserRegistViewVO();
		bsUserRegistViewVO.setPageNum(req.getPageNum());
		bsUserRegistViewVO.setNumPerPage(req.getNumPerPage());
		if(req.getStartTime() != null) {
			bsUserRegistViewVO.setStartTime(req.getStartTime());
		}
		if(req.getEndTime() != null) {
			bsUserRegistViewVO.setEndTime(req.getEndTime());
		}
		int totalRows = bsUserRegisterService.findRegistViewAllCount(bsUserRegistViewVO);
		if(totalRows > 0) {
			List<BsUserRegistView> list = bsUserRegisterService.findRegistViewAllList(bsUserRegistViewVO);
			res.setUserRegisterList(BeanUtils.classToArrayList(list));
		}
		res.setTotalRows(totalRows);
	}
	
}
