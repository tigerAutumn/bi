package com.pinting.manage.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.pinting.business.model.BsDepCash30;
import com.pinting.business.model.BsDepCash30Example;
import com.pinting.business.model.BsRevenueTransDetail;
import com.pinting.business.model.BsServiceFee;
import com.pinting.business.model.BsSysConfig;
import com.pinting.business.model.BsSysSubAccount;
import com.pinting.business.model.BsUser;
import com.pinting.business.model.LnDepositionTarget;
import com.pinting.business.model.LnDepositionTargetExample;
import com.pinting.business.model.LnLoan;
import com.pinting.business.model.LnLoanExample;
import com.pinting.business.model.LnLoanRelation;
import com.pinting.business.model.LnLoanRelationExample;
import com.pinting.business.model.LnPayOrders;
import com.pinting.business.model.LnPayOrdersExample;
import com.pinting.business.model.LnPayOrdersJnl;
import com.pinting.business.model.LnRepaySchedule;
import com.pinting.business.model.LnRepayScheduleExample;
import com.pinting.business.model.LnSubAccount;
import com.pinting.business.model.LnSubAccountExample;
import com.pinting.business.model.LnUser;
import com.pinting.business.model.MUser;
import com.pinting.business.model.MUserOpRecord;
import com.pinting.business.model.vo.*;
import com.pinting.business.service.manage.*;
import com.pinting.business.service.site.RegularSiteService;
import com.pinting.util.ReturnDWZAjax;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.pinting.business.accounting.loan.enums.DepTargetEnum;
import com.pinting.business.accounting.loan.enums.LoanStatus;
import com.pinting.business.accounting.loan.enums.PartnerEnum;
import com.pinting.business.accounting.loan.model.BaseAccount;
import com.pinting.business.accounting.loan.model.InvestorAuthYunInfo;
import com.pinting.business.accounting.loan.service.DepFixedLoanPaymentService;
import com.pinting.business.accounting.service.impl.process.SignSeal4BorrowServicesZsdProcess;
import com.pinting.business.accounting.service.impl.process.SignSeal4ZsdLoanAgreementProcess;
import com.pinting.business.dao.BsDepCash30Mapper;
import com.pinting.business.dao.BsPayOrdersMapper;
import com.pinting.business.dao.BsUserMapper;
import com.pinting.business.dao.LnDepositionTargetMapper;
import com.pinting.business.dao.LnLoanMapper;
import com.pinting.business.dao.LnLoanRelationMapper;
import com.pinting.business.dao.LnPayOrdersMapper;
import com.pinting.business.dao.MUserOpRecordMapper;
import com.pinting.business.enums.BAOFOOTransTypeEnum;
import com.pinting.business.enums.PTMessageEnum;
import com.pinting.business.enums.PayPathEnum;
import com.pinting.business.enums.PayPlatformEnum;
import com.pinting.business.enums.RedisLockEnum;
import com.pinting.business.enums.TransTypeEnum;
import com.pinting.business.exception.PTMessageException;
import com.pinting.business.hessian.manage.message.*;
import com.pinting.business.util.AddressUtil;
import com.pinting.business.util.BeanUtils;
import com.pinting.business.util.CalculatorUtil;
import com.pinting.core.cookie.CookieManager;
import com.pinting.core.hessian.service.HessianService;
import com.pinting.core.util.ConstantUtil;
import com.pinting.core.util.DateUtil;
import com.pinting.core.util.GlobEnvUtil;
import com.pinting.core.util.MoneyUtil;
import com.pinting.core.util.StringUtil;
import com.pinting.manage.enums.CookieEnums;
import com.pinting.util.Constants;
import com.pinting.util.ExportUtil;

@Controller
public class StatisticsController {
	
	@Autowired
	@Qualifier("dispatchService")
	private HessianService statisticsService;
	@Autowired
	private AgentService agentService;
	@Autowired
	private BsLoanRelativeRepayJnlService bsLoanRelativeRepayJnlService;
	@Autowired
	private BsUserMapper bsUserMapper;
	@Autowired
	private MStatisticsService mStatisticsService;
	@Autowired
	private BsSysBalanceDailySnapService bsSysBalanceDailySnapService;
	@Autowired
	private LoanService loanService;
	@Autowired
	private LnLoanRelationMapper  lnLoanRelationMapper;
	@Autowired
	private BsDepCash30Mapper bsDepCash30Mapper;
	@Autowired
	private RegularSiteService regularSiteService;
	@Autowired
	private LnLoanMapper lnLoanMapper;
	@Autowired
	private BsSubAccountService bsSubAccountService;
	@Autowired
	private BsPayOdersService bsPayOdersService;
	@Autowired
	private MUserService mUserService;
	@Autowired
	private LnDepositionTargetMapper lnDepositionTargetMapper;
	@Autowired
	private LnPayOrdersMapper lnPayOrdersMapper;
	@Autowired
	private DepFixedLoanPaymentService depFixedLoanPaymentService;
	@Autowired
	private MUserOpRecordService mUserOpRecordService;
	@Autowired
	private LoanBillService loanBillService;
	
	private Logger log = LoggerFactory.getLogger(StatisticsController.class);

	/**
	 * 投资购买查询
	 * @return
	 */
	@RequestMapping("/statistics/buyMessage/query/index")
	public String buyStatisticsInit(ReqMsg_Statistics_BuyMessageListQuery req,HttpServletRequest request,Map<String, Object> model){
		Integer pageNum = req.getPageNum();
		Integer numPerPage = req.getNumPerPage();
		if(pageNum <= 0 || numPerPage <= 0){
			pageNum = Constants.MANAGE_DEFAULT_PAGENUM;
			numPerPage = Constants.MANAGE_DEFAULT_NUMPERPAGE;
			req.setPageNum(pageNum);
			req.setNumPerPage(numPerPage);
		}
		if(request.getParameter("orderDirection")!=null&&request.getParameter("orderField")!=null)
		{
			req.setOrderDirection(request.getParameter("orderDirection"));
			req.setOrderField(request.getParameter("orderField"));
			model.put("orderField", request.getParameter("orderField"));
			model.put("orderDirection", request.getParameter("orderDirection"));
		}else
		{
			req.setOrderDirection("desc");
			req.setOrderField("openTime");
			model.put("orderField", req.getOrderField());
			model.put("orderDirection", req.getOrderDirection());
		}
		ResMsg_Statistics_BuyMessageListQuery res = (ResMsg_Statistics_BuyMessageListQuery) statisticsService.handleMsg(req);

		int agentTotal = agentService.findAgentsTotalRows();
		model.put("agentTotal", agentTotal);
		
		model.put("sumBalanceAmount", res.getSumBalanceAmount());
		model.put("userBuyMessage", res.getUserBuyMessageList());
		model.put("pageNum", req.getPageNum());
		model.put("numPerPage", res.getNumPerPage());
		model.put("totalRows", res.getTotalRows());
		model.put("productList", res.getProductList());
		model.put("buyBankTypeLists", res.getBuyBankTypeList());
		model.put("rates", res.getRateList());
		model.put("req", req);
		if(req.getOrderField()!=null)
		{
			model.put(req.getOrderField(), req.getOrderDirection());
			
		}
		//产品累计购买人数、金额统计
		List<HashMap<String, Object>> list = res.getProductList();
		int  investNum = 0;
		double currTotalAmount = 0.0;
		if(list!=null && list.size()>0){
			for (HashMap<String, Object> hashMap : list) {
				investNum += (Integer) hashMap.get("investNum") == null ? 0 : (Integer) hashMap.get("investNum");
				currTotalAmount += (Double) hashMap.get("currTotalAmount") == null ? 0 : (Double) hashMap.get("currTotalAmount");
			}
			model.put("investNum",investNum);
			model.put("currTotalAmount", currTotalAmount);
		}

		return "statistics/buyMessage/buyMessage_index";
	}
	
	/**
	 * 用户投资债权查看
	 * @param model
	 * @param
	 * @return
	 */
	@RequestMapping("/statistics/buyMessage/userMatch")
	public String userMatch(HttpServletRequest request,HashMap<String,Object> model){
		Integer subAccountId = Integer.valueOf(request.getParameter("subAccountId"));
		model.put("list", bsLoanRelativeRepayJnlService.getMatchListBySubAccountId(subAccountId));
		return "statistics/buyMessage/user_match";
	}

	/**
	 * 财务投资购买查询（云贷存管） 查看用户投资的债权
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping("/statistics/buyMessage/depUserMatch")
	public String depUserMatch(HttpServletRequest request,HashMap<String,Object> model){
		Integer subAccountId = Integer.valueOf(request.getParameter("subAccountId"));
		int count = regularSiteService.queryDepClaimsCountBySubAccountId(subAccountId);
		if(count > 0) {
			List<DetailsOfDebtVO> list = regularSiteService.queryDepClaimsListBySubAccountId(subAccountId, 0, Integer.MAX_VALUE);
			model.put("list", list);
		}
		return "statistics/buyMessage/dep_user_match";
	}
	
	/**
	 * 币港湾投资收益统计查询
	 * @param req
	 * @param model
	 * @return
	 */
	@RequestMapping("/account/cashSchedule/index")
	public String profitLossInit(ReqMsg_MAccount_CashScheduleListQuery req,HashMap<String,Object> model){
		
		ResMsg_MAccount_CashScheduleListQuery res = (ResMsg_MAccount_CashScheduleListQuery) statisticsService.handleMsg(req);
		res.setBeginTime(req.getBeginTime());
		res.setOverTime(req.getOverTime());
		model.put("product", res);
		model.put("accountList", res.getValueList());
		
		model.put("totalCashBaseAmount30", res.getTotalCashBaseAmount30());
		model.put("totalBashInterestAmount30", res.getTotalBashInterestAmount30());
		
		return "/account/cashSchedule/index";
	}
	/**
	 *  历史统计信息表查询（折线图）
	 * @param req
	 * @param request
	 * @param response
	 * @param model
	 * @param type
	 * @return
	 */

	@RequestMapping("/statistics/{type}/index")
	public String productInit(ReqMsg_Statistics_StatisticsListQuery req, HttpServletRequest request,
			HttpServletResponse response, Map<String, Object> model,@PathVariable String type) {
		String name = type;
		createChatDate(req,model);
		return  "/statistics/"+name+"/index";
	}
	/**
	 *  各个理财产品购买次数统计
	 * @param req
	 * @param request
	 * @param response
	 * @param model
	 * @param type   column:柱状图，Product：折线图
	 * @return
	 */

	@RequestMapping("/statistics/ProductBuyCount/{type}/index")
	public String productBuyCountInit(ReqMsg_Statistics_StatisticsListQuery req, HttpServletRequest request,
			HttpServletResponse response, Map<String, Object> model,@PathVariable String type) {
		for(int n=1;n<6;n++)
		{
			req.setType(n+"ProductBuyCount");
			ResMsg_Statistics_StatisticsListQuery resp =(ResMsg_Statistics_StatisticsListQuery) statisticsService.handleMsg(req);
			List<HashMap<String, Object>> mm = resp.getStatisticsList();
			String data ="";
			if(mm != null && mm.size() >0){
				for(int i=0;i<mm.size();i++)
				{
					data +="," +mm.get(i).get("value").toString();
				}
				model.put("data"+n, data.substring(1));
			}
			String typeName= resp.getStatisticsList().get(0).get("typeName").toString();
			model.put("name"+n, "\'" + typeName.substring(0, typeName.length()-6) + "\'");	
		}
		model.put("startTime", Constants.CHARTS_START_TIME);
		model.put("yAxistext", "\'" + "次" + "\'");
		return  "/statistics/ProductBuyCount/"+type+"_index";
	}
	
	/**
	 * 组装折线图数据格式
	 * @param req
	 * @param model
	 */
	public void createChatDate(ReqMsg_Statistics_StatisticsListQuery req,Map<String, Object> model){
		ResMsg_Statistics_StatisticsListQuery resp=(ResMsg_Statistics_StatisticsListQuery) statisticsService.handleMsg(req);
		List<HashMap<String, Object>> mm = resp.getStatisticsList();
		String num ="";
		String data ="0";
		if(mm != null && mm.size() >0){
			for(int i=0;i<mm.size();i++)
			{
				num +="," +mm.get(i).get("value").toString();
			}
			data = num.substring(1);
		}
		if(req.getType().contains("Num"))
		{
		model.put("yAxistext", "\'" + "人" + "\'");
		}else
		{
			model.put("yAxistext", "\'" + "元" + "\'");
		}
		model.put("nums", data);
		String typeName= resp.getStatisticsList().get(0).get("typeName").toString();
		model.put("startTime", Constants.CHARTS_START_TIME);
		if(typeName.contains("1个月理财"))
		{
			model.put("startTime", Constants.CHARTS_START_ONETIME);
		}else if(typeName.contains("1个月活动理财"))
		{
			model.put("startTime", Constants.CHARTS_START_ACONETIME);
		}
			
		model.put("titletext",typeName+"统计");
		model.put("seriestext", "\'" + typeName + "\'");
	}
	
	/**
	 * 投资回款查询
	 * @param req
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping("/statistics/investmentBackSection/query/index")
	public String investmentInit(ReqMsg_Statistics_SelectUserInvestmentPaybackList req, HttpServletRequest request,Map<String, Object> model) {
		Integer pageNum = req.getPageNum();
		Integer numPerPage = req.getNumPerPage();
		if(pageNum <= 0 || numPerPage <= 0){
			pageNum = Constants.MANAGE_DEFAULT_PAGENUM;
			numPerPage = Constants.MANAGE_DEFAULT_NUMPERPAGE;
			req.setPageNum(pageNum);
			req.setNumPerPage(numPerPage);
		}
		
		if(request.getParameter("orderDirection")!=null && request.getParameter("orderField")!=null) {
			req.setOrderDirection(request.getParameter("orderDirection"));
			req.setOrderField(request.getParameter("orderField"));
			model.put("orderField", request.getParameter("orderField"));
			model.put("orderDirection", request.getParameter("orderDirection"));
		} else {
			req.setOrderDirection("desc");
			req.setOrderField("investEndTime");// investEndTime 结算时间
			model.put("orderField", req.getOrderField());
			model.put("orderDirection", req.getOrderDirection());
		}
		ResMsg_Statistics_SelectUserInvestmentPaybackList res = (ResMsg_Statistics_SelectUserInvestmentPaybackList) statisticsService.handleMsg(req);
		
		model.put("userBackSection", res.getUserBackSectionList());
		model.put("pageNum", req.getPageNum());
		model.put("numPerPage", res.getNumPerPage());
		model.put("totalRows", res.getTotalRows());
		model.put("sumSettlementAmount", res.getSumSettlementAmount());
		model.put("settlementAmount", req.getSettlementAmount());
		model.put("req", req);
		model.put("agents", res.getAgentList());
		model.put("rates", res.getRateList());
		model.put("products", res.getProductList());
		model.put("buyBankTypeLists", res.getBuyBankTypeList());
		if(req.getOrderField()!=null) {
			model.put(req.getOrderField(), req.getOrderDirection());
		}
		//产品累计购买人数、金额统计
		List<HashMap<String, Object>> list = res.getProductList();
		int  investNum = 0;
		double currTotalAmount = 0.0;
		if(list!=null && list.size()>0) {
			for (HashMap<String, Object> hashMap : list) {
				investNum += (Integer) hashMap.get("investNum") == null ? 0 : (Integer) hashMap.get("investNum");
				currTotalAmount += (Double) hashMap.get("currTotalAmount") == null ? 0 : (Double) hashMap.get("currTotalAmount");
			}
			model.put("investNum",investNum);
			model.put("currTotalAmount", currTotalAmount);
		}

		return "statistics/investment/backSection_index";
	}
	
	/**
	 *  业务统计总览
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping("/statistics/business/index")
	public String businessStatistics(HttpServletRequest request,ReqMsg_Statistics_StatisticsBusiness req,
			HttpServletResponse response, Map<String, Object> model) {
		ResMsg_Statistics_StatisticsBusiness res = (ResMsg_Statistics_StatisticsBusiness) statisticsService.handleMsg(req);
		
		model.put("tAttentionUserCount", res.gettAttentionUserCount());
		model.put("yAttentionUserCount", res.getyAttentionUserCount());
		model.put("totalAttentionUserCount", res.getTotalAttentionUserCount());
		model.put("tRegisterUserCount", res.gettRegisterUserCount());
		model.put("yRegisterUserCount", res.getyRegisterUserCount());
		model.put("totalRegisterUserCount", res.getTotalRegisterUserCount());
		model.put("tDealCount", res.gettDealCount());
		model.put("yDealCount", res.getyDealCount());
		model.put("totalDealCount", res.getTotalDealCount());
		model.put("tInvestAmount", res.gettInvestAmount());
		model.put("yInvestAmount", res.getyInvestAmount());
		model.put("totalInvestAmount", res.getTotalInvestAmount());
		model.put("tInvestUserCount", res.gettInvestUserCount());
		model.put("yInvestUserCount", res.getyInvestUserCount());
		model.put("totalInvestUserCount", res.getTotalInvestUserCount());
		
		// 1. 老云贷、老7贷、赞分期、赞时贷上线自由站岗户需求，对应数据查询删除，加快响应速度。
		// 2. 新增自由产品计划
		
		// 自有产品计划
		model.put("t7DayAuthAmount4Free", res.getT7DayAuthAmount4Free());
		model.put("t1MonthAuthAmount4Free", res.getT1MonthAuthAmount4Free());
		model.put("t3MonthAuthAmount4Free", res.getT3MonthAuthAmount4Free());
		model.put("t6MonthAuthAmount4Free", res.getT6MonthAuthAmount4Free());
		model.put("t1YearAuthAmount4Free", res.getT1YearAuthAmount4Free());
		model.put("tYearAllAuthAmount4Free", res.gettYearAllAuthAmount4Free());
		model.put("tAllAuthAmount4Free", res.gettAllAuthAmount4Free());
		log.info("t7DayAuthAmount4Free{}t1MonthAuthAmount4Free{}t3MonthAuthAmount4Free{}t6MonthAuthAmount4Free{}t1YearAuthAmount4Free{}"
				+ "tYearAllAuthAmount4Free{}tAllAuthAmount4Free{}",
				res.getT7DayAuthAmount4Free(), res.getT1MonthAuthAmount4Free(), res.getT3MonthAuthAmount4Free(), res.getT6MonthAuthAmount4Free(),
				res.getT1YearAuthAmount4Free(), res.gettYearAllAuthAmount4Free(), res.gettAllAuthAmount4Free());	
		
		//云贷存管
		model.put("t7DayAuthAmount", res.getT7DayAuthAmount());
		model.put("t1MonthAuthAmount", res.getT1MonthAuthAmount());
		model.put("t3MonthAuthAmount", res.getT3MonthAuthAmount());
		model.put("t6MonthAuthAmount", res.getT6MonthAuthAmount());
		model.put("t1YearAuthAmount", res.getT1YearAuthAmount());
		model.put("tYearAllAuthAmount", res.gettYearAllAuthAmount());
		model.put("tAllAuthAmountYundai", res.gettAllAuthAmountYundai());
		log.info("t7DayAuthAmount{}t1MonthAuthAmount{}t3MonthAuthAmount{}t6MonthAuthAmount{}t1YearAuthAmount{}tYearAllAuthAmount{}tAllAuthAmountYundai{}",
				res.getT7DayAuthAmount(), res.getT1MonthAuthAmount(), res.getT3MonthAuthAmount(), res.getT6MonthAuthAmount(),
				res.getT1YearAuthAmount(), res.gettYearAllAuthAmount(), res.gettAllAuthAmountYundai());		
		
		//七贷存管
		model.put("t1MonthAuthAmount7dai", res.getT1MonthAuthAmount7dai());
		model.put("t3MonthAuthAmount7dai", res.getT3MonthAuthAmount7dai());
		model.put("t6MonthAuthAmount7dai", res.getT6MonthAuthAmount7dai());
		model.put("t1YearAuthAmount7dai", res.getT1YearAuthAmount7dai());
		model.put("tAllAuthAmount7dai", res.gettAllAuthAmount7dai());
		model.put("tYearAllAuthAmount7dai", res.gettYearAllAuthAmount7dai());
		log.info("t1MonthAuthAmount7dai{}t3MonthAuthAmount7dai{}t6MonthAuthAmount7dai{}t1YearAuthAmount7dai{}tAllAuthAmount7dai{}{}",
				res.getT1MonthAuthAmount7dai(), res.getT3MonthAuthAmount7dai(), res.getT6MonthAuthAmount7dai(), res.getT1YearAuthAmount7dai(),
				res.gettAllAuthAmount7dai(), res.gettYearAllAuthAmount7dai());	
		
		return  "/statistics/business/index";
	}
	
	/**
	 * 财务总账查询
	 * @param request
	 * @param req
	 * @param response
	 * @param model
     * @return
     */
	@RequestMapping("/statistics/financeTotal/index")
	public String financeTotal(HttpServletRequest request,ReqMsg_Statistics_ThdFinanceTotal req,
			HttpServletResponse response, Map<String, Object> model) {

		//查询宝付账户
		ResMsg_Statistics_ThdFinanceTotal baofooRes = (ResMsg_Statistics_ThdFinanceTotal) statisticsService.handleMsg(req);

		model.put("sysAccBalance", baofooRes.getSysAccBalance());
		model.put("accBalance", baofooRes.getAccBalance());
		model.put("proAccBalance", baofooRes.getProAccBalance());
		model.put("retAccBalance", baofooRes.getRetAccBalance());
		model.put("bonusAccBalance", baofooRes.getBonusAccBalance());
		model.put("redPaktAccBalance", baofooRes.getRedPaktAccBalance());
		model.put("sysFreezeAccBalance", baofooRes.getSysFreezeAccBalance());
		model.put("pro7AccBalance", baofooRes.getPro7AccBalance());
		model.put("ret7AccBalance", baofooRes.getRet7AccBalance());
		model.put("proAuthZanAccBalance", baofooRes.getProAuthZanAccBalance());
		model.put("proZanAccBalance", baofooRes.getProZanAccBalance());
		model.put("retZanAccBalance", baofooRes.getRetZanAccBalance());
		model.put("marginZanAccBalance", baofooRes.getMarginZanAccBalance());
		model.put("revenueZanAccBalance", baofooRes.getRevenueZanAccBalance());
		model.put("revenueBgwAccBalance", baofooRes.getRevenueBgwAccBalance());
		model.put("feeBgwAccBalance", baofooRes.getFeeBgwAccBalance());
		model.put("revenueBgwYunAccBalance", baofooRes.getRevenueBgwYunAccBalance());
		model.put("revenueYunAccBalance", baofooRes.getRevenueYunAccBalance());
		model.put("repayAccBalance", baofooRes.getRepayAccBalance());
		model.put("revenueBgw7AccBalance", baofooRes.getRevenueBgw7AccBalance());
		model.put("revenue7AccBalance", baofooRes.getRevenue7AccBalance());
		
		//宝付赞时贷相关
		model.put("marginZsdAccBalance", baofooRes.getMarginZsdAccBalance());
		model.put("revenueBgwZsdAccBalance", baofooRes.getRevenueBgwZsdAccBalance());
		model.put("revenueZsdAccBalance", baofooRes.getRevenueZsdAccBalance());
		model.put("bgwAuthZsdAccBalance", baofooRes.getBgwAuthZsdAccBalance());
		model.put("bgwRegZsdAccBalance", baofooRes.getBgwRegZsdAccBalance());
		model.put("bgwReturnZsdAccBalance", baofooRes.getBgwReturnZsdAccBalance());
		
		//赞时贷产品站岗红包
		Double bgwAuthRedZsdAccBalance = bsSubAccountService.sumRedAccBalanceByType(Constants.PRODUCT_TYPE_RED_ZSD);
		model.put("bgwAuthRedZsdAccBalance", bgwAuthRedZsdAccBalance);
		
		//查询恒丰账户
		ReqMsg_Statistics_DepFinanceTotal depReq = new ReqMsg_Statistics_DepFinanceTotal();
		ResMsg_Statistics_DepFinanceTotal depRes = (ResMsg_Statistics_DepFinanceTotal) statisticsService.handleMsg(depReq);

		model.put("depSysAccBalance", depRes.getDepSysAccBalance());
		model.put("depRedPaktAccBalance", depRes.getDepRedPaktAccBalance());
		model.put("depUserAccBalance", depRes.getDepUserAccBalance());
		model.put("depProRegAccBalance", depRes.getDepProRegAccBalance());
		model.put("depRetAccBalance", depRes.getDepRetAccBalance());
		model.put("depAuthYunAccBalance", depRes.getDepAuthYunAccBalance());
		model.put("depRedAccBalance", depRes.getDepRedAccBalance());
		model.put("depHeadFeeAccBalance", depRes.getDepHeadFeeAccBalance());
		model.put("depOtherFeeAccBalance", depRes.getDepOtherFeeAccBalance());
		model.put("depRevenueZanAccBalance", depRes.getDepRevenueZanAccBalance());
		model.put("deprevenueYunAccBalance", depRes.getDeprevenueYunAccBalance());
		model.put("depRegZanAccBalance", depRes.getDepRegZanAccBalance());
		model.put("depRetZanAccBalance", depRes.getDepRetZanAccBalance());
		model.put("depAuthZanAccBalance", depRes.getDepAuthZanAccBalance());
		model.put("depAdvanceAccBalance", depRes.getDepAdvanceAccBalance());
		//新增七贷
		model.put("depProReg7AccBalance", depRes.getDepProReg7AccBalance());
		model.put("depRet7AccBalance", depRes.getDepRet7AccBalance());
		model.put("depAuth7AccBalance", depRes.getDepAuth7AccBalance());
		model.put("depRed7AccBalance", depRes.getDepRed7AccBalance());
		model.put("deprevenue7AccBalance", depRes.getDeprevenue7AccBalance());
		//恒丰赞时贷相关
		model.put("depHeadFeeZsdAccBalance", depRes.getDepHeadFeeZsdAccBalance());
		model.put("depRevenueZsdAccBalance", depRes.getDepRevenueZsdAccBalance());
		
		ResMsg_RedPacket_BudgetStat redPaktRes = (ResMsg_RedPacket_BudgetStat) statisticsService.handleMsg(new ReqMsg_RedPacket_BudgetStat());
		// 红包已使用
		model.put("usedRedPaktAmount",redPaktRes.getUsedRedPaktAmount());
		model.put("badloansZanAccBalance", redPaktRes.getBadloansZanAccBalance());

		// 新增借款人余额
		model.put("sumLoanBalance", depRes.getSumLoanBalance());

		// 新增自由站岗户业务数据
		model.put("depFreeAccBalance", depRes.getDepFreeAccBalance()); //自由产品户余额
		model.put("depAuthFreeAccBalance", depRes.getDepAuthFreeAccBalance()); // 自由站岗户余额
		model.put("depRedFreeAccBalance", depRes.getDepRedFreeAccBalance()); // 自有产品站岗红包

		return  "/statistics/financeTotal/index";
	}
	
	/**
	 * 币港湾日终账务查询
	 * @param req
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping("/statistics/bgwDailySnap/index")
	public String index(HttpServletRequest request, HttpServletResponse response,
			Map<String, Object> model, ReqMsg_Statistics_BGWDailySnapQuery req){
		ResMsg_Statistics_BGWDailySnapQuery res = new ResMsg_Statistics_BGWDailySnapQuery();
		if(StringUtil.isNotBlank(request.getParameter("toPropertySymbol"))){
			req = new ReqMsg_Statistics_BGWDailySnapQuery();
			req.setPropertySymbol(request.getParameter("toPropertySymbol"));
		}
		if(StringUtil.isBlank(req.getPropertySymbol())){
			req.setPropertySymbol("BGW");
		}
		bsSysBalanceDailySnapService.getListByTimePropertySymbol(req, res);
		model.put("count", res.getTotalRows());
		model.put("list", res.getValueList());
		model.put("req", req);
		return "/statistics/bgwDailySnap/index";
	}
	
	/**
	 * 恒丰日终账务查询
	 * @param req
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping("/statistics/hfDailySnap/index")
	public String hfDailySnap(ReqMsg_Statistics_HFDailySnapQuery req, HttpServletRequest request, HashMap<String,Object> model) {
		Integer pageNum = req.getPageNum();
		Integer numPerPage = req.getNumPerPage();
		if (pageNum <= 0 || numPerPage <= 0) {
			pageNum = Constants.MANAGE_DEFAULT_PAGENUM;
			numPerPage = Constants.MANAGE_DEFAULT_NUMPERPAGE;
			req.setPageNum(pageNum);
			req.setNumPerPage(numPerPage);
		}
		ResMsg_Statistics_HFDailySnapQuery res = (ResMsg_Statistics_HFDailySnapQuery) statisticsService.handleMsg(req);
		model.put("pageNum", req.getPageNum());
		model.put("numPerPage", res.getNumPerPage());
		model.put("totalRows", res.getTotalRows());
		model.put("hfDailySnapList", res.getValueList());
		return "/statistics/hfDailySnap/index";
	}
	
	/**
	 *  用户投资概览
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping("/statistics/userInvest/index")
	public String userInvest(HttpServletRequest request,ReqMsg_Statistics_UserInvestQuery req,
			HttpServletResponse response, Map<String, Object> model) {
		Integer pageNum = req.getPageNum();
		Integer numPerPage = req.getNumPerPage();
		if (pageNum <= 0 || numPerPage <= 0) {
			pageNum = Constants.MANAGE_DEFAULT_PAGENUM;
			numPerPage = Constants.MANAGE_DEFAULT_NUMPERPAGE;
			req.setPageNum(pageNum);
			req.setNumPerPage(numPerPage);
		}
		ResMsg_Statistics_UserInvestQuery res = (ResMsg_Statistics_UserInvestQuery) statisticsService.handleMsg(req);
		
		model.put("userInvestList", res.getUserInvestList());
		model.put("totalRows", res.getTotalRows());
		model.put("req", req);
		return  "/statistics/userInvest/user_invest_index";
	}
	
	/**
	 *  用户投资导出
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping("/statistics/userInvest/exportXls")
	public void userInvestExport(HttpServletRequest request,ReqMsg_Statistics_UserInvestQuery req,
			HttpServletResponse response, Map<String, Object> model) {
		List<Map<String,List<Object>>> list = new ArrayList<Map<String,List<Object>>>();
		req.setPageNum(1);
		req.setNumPerPage(Integer.MAX_VALUE);
		ResMsg_Statistics_UserInvestQuery res = (ResMsg_Statistics_UserInvestQuery) statisticsService.handleMsg(req);
		
		List<HashMap<String,Object>> datas = res.getUserInvestList();
		//设置标题
		list.add(titles());
		//设置导出excel内容
		if(!CollectionUtils.isEmpty(datas)) {
			int i = 0;
			for (HashMap<String,Object> data : datas) {
				Map<String,List<Object>> datasMap = new HashMap<String, List<Object>>();
				List<Object> obj = new ArrayList<Object>();
				
				obj.add(DateUtil.formatYYYYMMDD((Date)data.get("date")));
				obj.add(data.get("todayUserNum"));
				obj.add(data.get("todayInvestNum"));
				obj.add(data.get("todayInvestAmount"));
				obj.add(data.get("todayAnnualAmount"));
				obj.add(data.get("totalUserNum"));
				obj.add(data.get("totalInvestNum"));
				obj.add(data.get("totalInvestAmount"));
				obj.add(data.get("totalAnnualAmount"));
				
				datasMap.put("userInvest"+(++i), obj);
				
				list.add(datasMap);
			}
		}
		
		try {
			ExportUtil.exportExcel(response, request, "用户投资概览", list);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private Map<String, List<Object>> titles(){
		Map<String,List<Object>> titleMap = new HashMap<String, List<Object>>();
		List<Object> titles = new ArrayList<Object>();
		titles.add("日期");
		titles.add("新增投资用户数");
		titles.add("新增投资笔数");
		titles.add("新增投资金额");
		titles.add("新增年化");
		titles.add("投资用户数");
		titles.add("投资笔数");
		titles.add("投资金额");
		titles.add("年化金额");
		titleMap.put("title", titles);
		return titleMap;
	}
	
	/**
	 *  用户注册概览
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping("/statistics/register/index")
	public String userRegistView(HttpServletRequest request,ReqMsg_Statistics_RegisterViewQuery req,
			HttpServletResponse response, Map<String, Object> model) {
		Integer pageNum = req.getPageNum();
		Integer numPerPage = req.getNumPerPage();
		if (pageNum <= 0 || numPerPage <= 0) {
			pageNum = Constants.MANAGE_DEFAULT_PAGENUM;
			numPerPage = Constants.MANAGE_DEFAULT_NUMPERPAGE;
			req.setPageNum(pageNum);
			req.setNumPerPage(numPerPage);
		}
		ResMsg_Statistics_RegisterViewQuery res = (ResMsg_Statistics_RegisterViewQuery) statisticsService.handleMsg(req);
		
		model.put("userRegustList", res.getUserRegisterList());
		model.put("totalRows", res.getTotalRows());
		model.put("req", req);
		return  "/statistics/userRegist/regist_index";
	}
	
	
	
	/**
	 * 委托计划-投资购买查询
	 * @return
	 */
	@RequestMapping("/statistics/buyMessage/query/index_entrust")
	public String buyStatisticsEntrustInit(UserBuyMessageEntrustVO req,HttpServletRequest request,Map<String, Object> model){
		req.setMobile(StringUtil.trimToEmpty(req.getMobile()));
		req.setUserName(StringUtil.trimToEmpty(req.getUserName()));
		req.setOrderNo(StringUtil.trimToEmpty(req.getOrderNo()));
        String flag = request.getParameter("flag");
		Integer pageNum = req.getPageNum();
		Integer numPerPage = req.getNumPerPage();
		if (pageNum <= 0 || numPerPage <= 0) {
			pageNum = Constants.MANAGE_DEFAULT_PAGENUM;
			numPerPage = Constants.MANAGE_DEFAULT_NUMPERPAGE;
			req.setPageNum(pageNum);
			req.setNumPerPage(numPerPage);
		}
		if (request.getParameter("orderDirection") != null && request.getParameter("orderField") != null) {
			req.setOrderDirection(request.getParameter("orderDirection"));
			req.setOrderField(request.getParameter("orderField"));
			model.put("orderField", request.getParameter("orderField"));
			model.put("orderDirection", request.getParameter("orderDirection"));
		} else {
			req.setOrderDirection("desc");
			req.setOrderField("repay_time");
			model.put("orderField", req.getOrderField());
			model.put("orderDirection", req.getOrderDirection());
		}
		if (req.getOrderField() != null) {
			model.put(req.getOrderField(), req.getOrderDirection());
			model.put("orderField", req.getOrderField());
			model.put("orderDirection", req.getOrderDirection());
		}
		model.put("req", req);
		model.put("pageNum", req.getPageNum());
		model.put("numPerPage", req.getNumPerPage());
		if("query".equals(flag)) {
            if (StringUtil.isNotEmpty(req.getAgentIds())) {
                String[] agentIds = req.getAgentIds().split(",");
                if (agentIds.length > 0) {
                    List<Object> ids = new ArrayList<Object>();
                    for (String str : agentIds) {
                        if (StringUtil.isNotEmpty(str)) {
                            ids.add(Integer.valueOf(str));
                        }
                    }
                    req.setAgentIdsObj(ids);
                }
            }

            List<UserBuyMessageEntrustVO> res = new ArrayList<UserBuyMessageEntrustVO>();
            Integer totalRows = bsUserMapper.countUserBuyMessageEntrustList(req);
            Double sumBalanceAmount = 0d;
            if (totalRows > 0) {
                res = bsUserMapper.selectUserBuyMessageEntrustList(req);
                sumBalanceAmount = bsUserMapper.sumUserBuyMessageEntrustList(req);
            }


            model.put("entrustList", res);
            model.put("totalRows", totalRows);
            model.put("sumBalanceAmount", sumBalanceAmount);
            int agentTotal = agentService.findAgentsTotalRows();
            model.put("agentTotal", agentTotal);

            //产品累计购买人数、金额统计
            List<HashMap<String, Object>> list = null;
            int investNum = 0;
            double currTotalAmount = 0.0;
            if (list != null && list.size() > 0) {
                for (HashMap<String, Object> hashMap : list) {
                    investNum += (Integer) hashMap.get("investNum") == null ? 0 : (Integer) hashMap.get("investNum");
                    currTotalAmount += (Double) hashMap.get("currTotalAmount") == null ? 0 : (Double) hashMap.get("currTotalAmount");
                }
                model.put("investNum", investNum);
                model.put("currTotalAmount", currTotalAmount);
            }
        }
		return "statistics/buyMessage/index_entrust";
	}
	
	/**
	 * 导出Excel
	 * @return
	 */
	@RequestMapping("/statistics/buyMessage/entrust_export_xls")
	public void stageOrderExportExcel(UserBuyMessageEntrustVO req, HttpServletRequest request, HttpServletResponse response) {
		req.setMobile(StringUtil.trimToEmpty(req.getMobile()));
		req.setUserName(StringUtil.trimToEmpty(req.getUserName()));
		req.setOrderNo(StringUtil.trimToEmpty(req.getOrderNo()));
		
		req.setNumPerPage(0);

		if(request.getParameter("orderDirection")!=null&&request.getParameter("orderField")!=null)
		{
			req.setOrderDirection(request.getParameter("orderDirection"));
			req.setOrderField(request.getParameter("orderField"));
		}else
		{
			req.setOrderDirection("desc");
			req.setOrderField("repay_time");
		}
	    
		if(StringUtil.isNotEmpty(req.getAgentIds())) {
			String[] agentIds = req.getAgentIds().split(",");
			if(agentIds.length > 0) {
				List<Object> ids = new ArrayList<Object>();
				for (String str : agentIds) {
					if(StringUtil.isNotEmpty(str)) {
						ids.add(Integer.valueOf(str));
					}
				}
				req.setAgentIdsObj(ids);
			}
		}
		
		
		List<UserBuyMessageEntrustVO> res = new ArrayList<UserBuyMessageEntrustVO>();
		Integer totalRows = bsUserMapper.countUserBuyMessageEntrustList(req);

		if (totalRows > 0) {
			req.setNumPerPage(totalRows);
			res = bsUserMapper.selectUserBuyMessageEntrustList(req);

	        ArrayList<HashMap<String, Object>> dataGrid = BeanUtils.classToArrayList(res);
	        List<Map<String,List<Object>>> excelList = new ArrayList<Map<String,List<Object>>>();
	        Map<String,List<Object>> titleMap = new HashMap<String, List<Object>>();
	        List<Object> titles = new ArrayList<Object>();
	        titles.add("用户ID");
	        titles.add("手机号");
	        titles.add("姓名");
	        titles.add("资产合作方");
	        titles.add("订单号");
	        titles.add("期限");
	        titles.add("利率");
	        titles.add("出借本金");
	        titles.add("投资状态");
	        titles.add("出借日期");
	        titles.add("结算日期");
	        titles.add("渠道来源");

	        titleMap.put("title", titles);
	        excelList.add(titleMap);
	        
            int i = 0;
            if (!CollectionUtils.isEmpty(dataGrid)) {
                for (HashMap<String, Object> data : dataGrid) {
                    Map<String,List<Object>> datasMap = new HashMap<String, List<Object>>();
                    List<Object> objs = new ArrayList<Object>();
                    
                    objs.add(data.get("userId") == null ? "" : data.get("userId"));
                    objs.add(data.get("mobile") == null ? "" : data.get("mobile"));
                    objs.add(data.get("userName") == null ? "" : data.get("userName"));
                    if ("ZAN".equals(data.get("propertySymbol"))) {
                    	objs.add("赞分期");
					}else if ("7_DAI".equals(data.get("propertySymbol"))) {
						objs.add("七贷");
					}else {
						objs.add("云贷");
					}
                    
                    objs.add(data.get("orderNo") == null ? "" : data.get("orderNo"));
                    objs.add(data.get("term") == null ? "" : data.get("term")+"个月");
                    objs.add(data.get("baseRate") == null ? "" : data.get("baseRate")+"%");
                    objs.add(data.get("amount") == null ? "" : MoneyUtil.format((Double)data.get("amount")));
                    
                    if("SUCCESS".equals(data.get("status"))) {
                        objs.add(data.get("status") == null ? "" : "投资中");
                    } else if("REPAID".equals(data.get("status"))) {
                        objs.add(data.get("status") == null ? "" : "已结算");
                    }else {
                        objs.add("");
                    }
                    
                    objs.add(data.get("loanTime") == null ? "" : DateUtil.format((Date)data.get("loanTime")));
                    if("REPAID".equals(data.get("status"))) {
                    	objs.add(data.get("repayTime") == null ? "" : DateUtil.format((Date)data.get("repayTime")));
                    }else {
                    	objs.add("");
					} 
                
                    objs.add(data.get("agentName") == null ? "无" : data.get("agentName"));
                   
                    datasMap.put("order"+i, objs);
                    i++;
                    excelList.add(datasMap);
                }
            }
            try {
                ExportUtil.exportExcel(response, request, "委托计划投资购买导出", excelList);
            } catch (Exception e) {
                e.printStackTrace();
            }
		    
		}
	    
	    
	}

	/**
	 * 财务投资购买查询(包含赞分期)
	 * @param req
	 * @param request
	 * @param model
     * @return
     */
	@RequestMapping("/statistics/buyMessage/finance/query/index")
	public String buyMessageForZan(ReqMsg_Statistics_BuyMessageListQuery req,HttpServletRequest request,Map<String, Object> model){
		req.setProductCode(StringUtil.isBlank(req.getProductCode()) ? null : req.getProductCode().trim());
		req.setBuyBankCard(StringUtil.isBlank(req.getBuyBankCard()) ? null : req.getBuyBankCard().trim());
		req.setBindBankCard(StringUtil.isBlank(req.getBindBankCard()) ? null : req.getBindBankCard().trim());
		req.setUserName(StringUtil.isBlank(req.getUserName()) ? null : req.getUserName().trim());
		req.setIdCard(StringUtil.isBlank(req.getIdCard()) ? null : req.getIdCard().trim());
		req.setMobile(StringUtil.isBlank(req.getMobile()) ? null : req.getMobile().trim());
		req.setOrderNo(StringUtil.isBlank(req.getOrderNo()) ? null : req.getOrderNo().trim());
		req.setAgentName(StringUtil.isBlank(req.getAgentName()) ? null : req.getAgentName().trim());
		req.setBuyBankType(StringUtil.isBlank(req.getBuyBankType()) ? null : req.getBuyBankType().trim());
		req.setAgentIds(StringUtil.isBlank(req.getAgentIds()) ? null : req.getAgentIds().trim());
		req.setNonAgentId(StringUtil.isBlank(req.getNonAgentId()) ? null : req.getNonAgentId().trim());
		req.setPropertySymbol(StringUtil.isBlank(req.getPropertySymbol()) ? null : req.getPropertySymbol().trim());

		Integer pageNum = req.getPageNum();
		Integer numPerPage = req.getNumPerPage();
		if(pageNum <= 0 || numPerPage <= 0){
			pageNum = Constants.MANAGE_DEFAULT_PAGENUM;
			numPerPage = Constants.MANAGE_DEFAULT_NUMPERPAGE;
			req.setPageNum(pageNum);
			req.setNumPerPage(numPerPage);
		}
		if(request.getParameter("orderDirection")!=null&&request.getParameter("orderField")!=null)
		{
			req.setOrderDirection(request.getParameter("orderDirection"));
			req.setOrderField(request.getParameter("orderField"));
			model.put("orderField", request.getParameter("orderField"));
			model.put("orderDirection", request.getParameter("orderDirection"));
		}else
		{
			req.setOrderDirection("desc");
			req.setOrderField("openTime");
			model.put("orderField", req.getOrderField());
			model.put("orderDirection", req.getOrderDirection());
		}
		ResMsg_Statistics_BuyMessageListQuery res = new ResMsg_Statistics_BuyMessageListQuery();
		mStatisticsService.buyMessageListQuery(req, res);

		int agentTotal = agentService.findAgentsTotalRows();
		model.put("agentTotal", agentTotal);

		model.put("sumBalanceAmount", res.getSumBalanceAmount());
		model.put("userBuyMessage", res.getUserBuyMessageList());
		model.put("pageNum", req.getPageNum());
		model.put("numPerPage", res.getNumPerPage());
		model.put("totalRows", res.getTotalRows());
		model.put("productList", res.getProductList());
		model.put("buyBankTypeLists", res.getBuyBankTypeList());
		model.put("rates", res.getRateList());
		model.put("req", req);
		if(req.getOrderField()!=null)
		{
			model.put(req.getOrderField(), req.getOrderDirection());

		}
		//产品累计购买人数、金额统计
		List<HashMap<String, Object>> list = res.getProductList();
		int  investNum = 0;
		double currTotalAmount = 0.0;
		if(list!=null && list.size()>0){
			for (HashMap<String, Object> hashMap : list) {
				investNum += (Integer) hashMap.get("investNum") == null ? 0 : (Integer) hashMap.get("investNum");
				currTotalAmount += (Double) hashMap.get("currTotalAmount") == null ? 0 : (Double) hashMap.get("currTotalAmount");
			}
			model.put("investNum",investNum);
			model.put("currTotalAmount", currTotalAmount);
		}

		return "statistics/buyMessage/buyMessage_index_zan";
	}

	/**
	 * 投资购买查询(存管后)
	 * @param req
	 * @param request
	 * @param model
     * @return
     */
	@RequestMapping("/statistics/buyMessage/queryDep/index")
	public String investmentBuyMessageForDep(ReqMsg_Statistics_BuyMessageDepListQuery req, HttpServletRequest request, Map<String, Object> model) {
		req.setMobile(StringUtil.isBlank(req.getMobile()) ? null : req.getMobile().trim());
		req.setUserName(StringUtil.isBlank(req.getUserName()) ? null : req.getUserName().trim());
		req.setOrderNo(StringUtil.isBlank(req.getOrderNo()) ? null : req.getOrderNo().trim());
		req.setPropertySymbol(StringUtil.isBlank(req.getPropertySymbol()) ? null : req.getPropertySymbol().trim());

		req.setAgentName(StringUtil.isBlank(req.getAgentName()) ? null : req.getAgentName().trim());
		req.setAgentIds(StringUtil.isBlank(req.getAgentIds()) ? null : req.getAgentIds().trim());
		req.setNonAgentId(StringUtil.isBlank(req.getNonAgentId()) ? null : req.getNonAgentId().trim());
		Date yesterday = DateUtil.addDays(new Date(), -1);
		req.setInvestBuyStartTime(StringUtil.isBlank(req.getInvestBuyStartTime()) ? DateUtil.formatYYYYMMDD(yesterday) : req.getInvestBuyStartTime());
		req.setInvestBuyEndTime(StringUtil.isBlank(req.getInvestBuyEndTime()) ? DateUtil.formatYYYYMMDD(yesterday) : req.getInvestBuyEndTime());
		req.setInvestFinishStartTime(StringUtil.isBlank(req.getInvestFinishStartTime()) ? "" : req.getInvestFinishStartTime());
		req.setInvestFinishEndTime(StringUtil.isBlank(req.getInvestFinishEndTime()) ? "" : req.getInvestFinishEndTime());
		
		Integer pageNum = req.getPageNum();
		Integer numPerPage = req.getNumPerPage();
		if(pageNum <= 0 || numPerPage <= 0) {
			pageNum = Constants.MANAGE_DEFAULT_PAGENUM;
			numPerPage = Constants.MANAGE_DEFAULT_NUMPERPAGE;
			req.setPageNum(pageNum);
			req.setNumPerPage(numPerPage);
		}
		
		ResMsg_Statistics_BuyMessageDepListQuery res = new ResMsg_Statistics_BuyMessageDepListQuery();
		mStatisticsService.depInvestmentBuyMessageListQuery(req, res);

		int agentTotal = agentService.findAgentsTotalRows();
		model.put("agentTotal", agentTotal);

		model.put("sumBuyMessageAmount", res.getSumBuyMessageAmount());
		model.put("userBuyMessage", res.getUserBuyMessageList());
		model.put("pageNum", req.getPageNum());
		model.put("numPerPage", res.getNumPerPage());
		model.put("totalRows", res.getTotalRows());
		model.put("req", req);
		return "statistics/buyMessage/investment_buyMessage_depIndex";
	}

	/**
	 * 投资购买查询(存管后)  --异步加载
	 * @param req
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/statistics/buyMessage/queryDep/SumBalnace")
	@ResponseBody
	public Map<String, Object> depInvestmentSumBalnace(ReqMsg_Statistics_BuyMessageDepListQuery req, HttpServletRequest request, HttpServletResponse response){
		
		Map<String, Object> model = new HashMap<>();
		req.setPropertySymbol(StringUtil.isBlank(request.getParameter("propertySymbol")) ? null : request.getParameter("propertySymbol").trim());
		
		String productType = null;
		if(Constants.PROPERTY_SYMBOL_ZSD.equals(req.getPropertySymbol())) {
			productType = Constants.PRODUCT_TYPE_AUTH_ZSD;
		}else if(Constants.PRODUCT_PROPERTY_SYMBOL_7_DAI_SELF.equals(req.getPropertySymbol())) {
			productType = Constants.PRODUCT_TYPE_AUTH_7;
		}else if(Constants.PROPERTY_SYMBOL_YUN_DAI_SELF.equals(req.getPropertySymbol())) {
			productType = Constants.PRODUCT_TYPE_AUTH_YUN;
		}
		//如果资产方选择为空，则购买总金额为0
		if (productType == null) {
			model.put("sumBuyMessageAsyncAmount", 0d);
			return model;
		}
		//投资购买查询(存管后)
		BsStatisticsDepVO statisticsVo = new BsStatisticsDepVO();
		statisticsVo.setMobile(req.getMobile());
		statisticsVo.setUserName(req.getUserName());
		statisticsVo.setOrderNo(req.getOrderNo());
		if(StringUtil.isNotEmpty(req.getAgentIds())) {
			String[] agentIds = req.getAgentIds().split(",");
			if(agentIds.length > 0) {
				List<Object> ids = new ArrayList<Object>();
				for (String str : agentIds) {
					if(StringUtil.isNotEmpty(str)) {
						ids.add(Integer.valueOf(str));
					}
				}
				statisticsVo.setAgentIds(ids);
			}
		}
		statisticsVo.setNonAgentId(req.getNonAgentId());
		statisticsVo.setBeginTime(req.getBuyBeginTime());
		statisticsVo.setEndTime(req.getBuyEndTime());
		statisticsVo.setSettleAccountsBeginTime(req.getInvestBeginTime());
		statisticsVo.setSettleAccountsEndTime(req.getInvestEndTime());
		statisticsVo.setPropertySymbol(req.getPropertySymbol());
		statisticsVo.setProductType(productType);
		statisticsVo.setAccountStatus(req.getAccountStatus());
		statisticsVo.setTerm(req.getTerm());
		Double sumBuyMessageAsyncAmount = bsUserMapper.selectInvestmentBuySumBalanceForDep(statisticsVo);

		model.put("sumBuyMessageAsyncAmount", sumBuyMessageAsyncAmount == null ? 0d: sumBuyMessageAsyncAmount);
		return model;
	}
	
	/**
	 * 菜单更名为存管后-出借匹配查询
	 * @param req
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping("/statistics/buyMessage/finance/query/depIndex")
	public String buyMessageForDep(ReqMsg_Statistics_BuyMessageDepListQuery req,HttpServletRequest request,Map<String, Object> model){
		req.setProductCode(StringUtil.isBlank(req.getProductCode()) ? null : req.getProductCode().trim());
		req.setBuyBankCard(StringUtil.isBlank(req.getBuyBankCard()) ? null : req.getBuyBankCard().trim());
		req.setBindBankCard(StringUtil.isBlank(req.getBindBankCard()) ? null : req.getBindBankCard().trim());
		req.setUserName(StringUtil.isBlank(req.getUserName()) ? null : req.getUserName().trim());
		req.setIdCard(StringUtil.isBlank(req.getIdCard()) ? null : req.getIdCard().trim());
		req.setMobile(StringUtil.isBlank(req.getMobile()) ? null : req.getMobile().trim());
		req.setOrderNo(StringUtil.isBlank(req.getOrderNo()) ? null : req.getOrderNo().trim());
		req.setAgentName(StringUtil.isBlank(req.getAgentName()) ? null : req.getAgentName().trim());
		req.setBuyBankType(StringUtil.isBlank(req.getBuyBankType()) ? null : req.getBuyBankType().trim());
		req.setAgentIds(StringUtil.isBlank(req.getAgentIds()) ? null : req.getAgentIds().trim());
		req.setNonAgentId(StringUtil.isBlank(req.getNonAgentId()) ? null : req.getNonAgentId().trim());
		req.setPropertySymbol(StringUtil.isBlank(req.getPropertySymbol()) ? null : req.getPropertySymbol().trim());
		req.setBeginBuyAmount(StringUtil.isBlank(req.getBeginBuyAmount())? null : req.getBeginBuyAmount().trim());
		req.setEndBuyAmount(StringUtil.isBlank(req.getEndBuyAmount())? null : req.getEndBuyAmount().trim());
		Integer pageNum = req.getPageNum();
		Integer numPerPage = req.getNumPerPage();
		if(pageNum <= 0 || numPerPage <= 0){
			pageNum = Constants.MANAGE_DEFAULT_PAGENUM;
			numPerPage = Constants.MANAGE_DEFAULT_NUMPERPAGE;
			req.setPageNum(pageNum);
			req.setNumPerPage(numPerPage);
		}
		
		// 默认查询当天记录
		Date date = new Date();
		if (req.getInvestBeginTime() == null && req.getBuyBeginTime() == null) {
			req.setInvestBeginTime(req.getInvestBeginTime() == null ? DateUtil.parseDate(DateUtil.formatYYYYMMDD(date)) : req.getInvestBeginTime());
			req.setInvestEndTime(req.getInvestEndTime() == null ? DateUtil.parseDate(DateUtil.formatYYYYMMDD(date)) : req.getInvestEndTime());
		}

		ResMsg_Statistics_BuyMessageDepListQuery res = new ResMsg_Statistics_BuyMessageDepListQuery();
		mStatisticsService.depBuyMessageListQuery(req, res);
		int agentTotal = agentService.findAgentsTotalRows();
		model.put("agentTotal", agentTotal);
		
		if (StringUtil.isEmpty(req.getPropertySymbol())) {			
			model.put("sumBalanceAmount", 0d);
		} else {
			model.put("sumBalanceAmount", res.getSumBalanceAmount());
		}
		model.put("userBuyMessage", res.getUserBuyMessageList());
		model.put("pageNum", req.getPageNum());
		model.put("numPerPage", res.getNumPerPage());
		model.put("totalRows", res.getTotalRows());
		model.put("req", req);

		//云贷存管-财务投资购买查询 当日匹配总金额
		if (StringUtil.isEmpty(req.getPartnerCode())) {
			model.put("matchTotalAmountForDay", 0d);
		}

		if(req.getOrderField()!=null)
		{
			model.put(req.getOrderField(), req.getOrderDirection());

		}
		return "statistics/buyMessage/buyMessage_index_dep";
	}
	
	
	/**
	 * 财务投资购买查询（云贷存管）-异步加载投资
	 * @param req
	 * @param request
	 * @param model
	 * @return
	 */
	
	@RequestMapping("/statistics/buyMessage/finance/query/depSumBalnace")
	@ResponseBody
	public Map<String, Object> depSumBalnace(ReqMsg_Statistics_BuyMessageDepListQuery req,HttpServletRequest request, HttpServletResponse response){
		
		Map<String, Object> model = new HashMap<>();

		req.setPropertySymbol(StringUtil.isBlank(request.getParameter("propertySymbol")) ? null : request.getParameter("propertySymbol").trim());
		
		String productType = null;
		if(Constants.PROPERTY_SYMBOL_ZSD.equals(req.getPropertySymbol())) {
			productType = Constants.PRODUCT_TYPE_AUTH_ZSD;
		}else if(Constants.PRODUCT_PROPERTY_SYMBOL_7_DAI_SELF.equals(req.getPropertySymbol())) {
			productType = Constants.PRODUCT_TYPE_AUTH_7;
		}else if(Constants.PROPERTY_SYMBOL_ZAN.equals(req.getPropertySymbol())) {
			productType = Constants.PRODUCT_TYPE_AUTH;
		}else if(Constants.PROPERTY_SYMBOL_YUN_DAI_SELF.equals(req.getPropertySymbol())) {
			productType = Constants.PRODUCT_TYPE_AUTH_YUN;
		} else if(Constants.PROPERTY_SYMBOL_FREE.equals(req.getPropertySymbol())) {
			productType = Constants.PRODUCT_TYPE_AUTH_FREE;
		}

		//云贷存管-财务投资购买-匹配金额
		BsStatisticsDepVO statisticsVo = new BsStatisticsDepVO();
		statisticsVo.setMobile(req.getMobile());
		statisticsVo.setUserName(req.getUserName());
		statisticsVo.setOrderNo(req.getOrderNo());
		if(StringUtil.isNotEmpty(req.getAgentIds())) {
			String[] agentIds = req.getAgentIds().split(",");
			if(agentIds.length > 0) {
				List<Object> ids = new ArrayList<Object>();
				for (String str : agentIds) {
					if(StringUtil.isNotEmpty(str)) {
						ids.add(Integer.valueOf(str));
					}
				}
				statisticsVo.setAgentIds(ids);
			}
		}
		statisticsVo.setNonAgentId(req.getNonAgentId());
		statisticsVo.setBeginTime(req.getBuyBeginTime());
		statisticsVo.setEndTime(req.getBuyEndTime());
		statisticsVo.setSettleAccountsBeginTime(req.getInvestBeginTime());
		statisticsVo.setSettleAccountsEndTime(req.getInvestEndTime());
		statisticsVo.setPropertySymbol(req.getPropertySymbol());
		statisticsVo.setProductType(productType);
		statisticsVo.setAccountStatus(req.getAccountStatus());
		statisticsVo.setTerm(req.getTerm());
		
		//云贷存管-财务投资购买查询 当日匹配总金额
		if (productType == null) {
			model.put("sumBalanceAmount", 0d);
		} else {			
			Double sumBalanceAmount = bsUserMapper.selectUserBuySumBalanceForDep(statisticsVo);
			model.put("sumBalanceAmount", sumBalanceAmount == null ? 0d: sumBalanceAmount);
		}
		
		//云贷存管-财务投资购买查询 借款总金额
		if (StringUtil.isEmpty(req.getPartnerCode())) {
			model.put("matchTotalAmountForDay", 0d);
		} else {			
			Double matchTotalAmountForDay = 0d;
			matchTotalAmountForDay = lnLoanMapper.selectMatchTotalAmountForDay(req.getPartnerCode());
			model.put("matchTotalAmountForDay", matchTotalAmountForDay == null ? 0d: matchTotalAmountForDay);
		}
		return model;
	}
	
	



	// ======================= 2017-10-11 放款日常管理 start =======================

	/**
	 * 放款日常管理 list
	 * @param request
	 * @param response
	 * @param model
     * @return
     */
	@RequestMapping("/statistics/loanDaily/index")
	public String revenueCollectionRepayYun(HttpServletRequest request, HttpServletResponse response, Map<String, Object> model) {
		LoanDailyVO record = new LoanDailyVO();

		String mobile = request.getParameter("mobile");
		String userName = request.getParameter("userName");
		String idCard = request.getParameter("idCard");

		String orderNo = request.getParameter("orderNo");
		String loanStatus = request.getParameter("loanStatus");
		String lnUserId = request.getParameter("lnUserId");

		String bankCardNo = request.getParameter("bankCardNo");
		String depositionId = request.getParameter("depositionId");
		String partnerCode = request.getParameter("partnerCode");

		String partnerLoanId = request.getParameter("partnerLoanId");
		
		String startTime = request.getParameter("startTime");
		String endTime = request.getParameter("endTime");
		String pageNum = request.getParameter("pageNum");
		String numPerPage = request.getParameter("numPerPage");

		if (StringUtil.isEmpty(pageNum) || StringUtil.isEmpty(numPerPage)) {
			pageNum = String.valueOf(Constants.MANAGE_DEFAULT_PAGENUM);
			numPerPage = String.valueOf(Constants.MANAGE_DEFAULT_NUMPERPAGE);
		}
		Date date = new Date();
		// 默认查询当天记录
		startTime = StringUtil.isNotEmpty(startTime) == false ? DateUtil.formatYYYYMMDD(date) : startTime;
		endTime = StringUtil.isNotEmpty(endTime) == false ? DateUtil.formatYYYYMMDD(date) : endTime;

		if(StringUtils.isNotEmpty(mobile)) {
			record.setMobile(mobile.trim());
		}
		if(StringUtils.isNotEmpty(userName)) {
			record.setUserName(userName.trim());
		}
		if(StringUtils.isNotEmpty(idCard)) {
			record.setIdCard(idCard.trim());
		}

		if(StringUtils.isNotEmpty(orderNo)) {
			record.setOrderNo(orderNo.trim());
		}
		if(StringUtils.isNotEmpty(loanStatus)) {
			record.setLoanStatus(loanStatus);
		}
		if(StringUtils.isNotEmpty(lnUserId)) {
			record.setLnUserId(Integer.valueOf(lnUserId));
		}

		if(StringUtils.isNotEmpty(bankCardNo)) {
			record.setBankCardNo(bankCardNo.trim());
		}

		if(StringUtils.isNotEmpty(depositionId)) {
			record.setDepositionId(Integer.parseInt(depositionId));
		}
		if(StringUtils.isNotEmpty(partnerCode)) {
			record.setPartnerCode(partnerCode);
		}
		if(StringUtils.isNotEmpty(partnerLoanId)) {
			record.setPartnerLoanId(partnerLoanId);
		}
		record.setStartTime(startTime);
		record.setEndTime(endTime);

		// 统计
		int count = loanService.queryLoanDailyCount(record);

		record.setPageNum(Integer.valueOf(pageNum));
		record.setNumPerPage(Integer.valueOf(numPerPage));
		record.setTotalRows(count);

		// 列表查询
		if(count > 0 ) {
			List<LoanDailyVO> resultList = loanService.queryLoanDailyInfo(record);
			model.put("totalRows", count);
			model.put("loanDailyList", resultList);
		}
		// 将数据返回给页面
		model.put("mobile", mobile);
		model.put("userName", userName);
		model.put("idCard", idCard);
		model.put("orderNo", orderNo);
		model.put("loanStatus", loanStatus);
		model.put("lnUserId", lnUserId);
		model.put("bankCardNo", bankCardNo);
		model.put("depositionId", depositionId);
		model.put("partnerCode", partnerCode);
		model.put("partnerLoanId", partnerLoanId);
		model.put("startTime", startTime);
		model.put("endTime", endTime);
		model.put("pageNum", pageNum);
		model.put("numPerPage", numPerPage);
		// 赞分期资金计划总量
		double zanTotalAmount =  loanService.queryZanDailyTotalAmount(startTime, endTime);
		model.put("zanTotalAmount", zanTotalAmount);
		// 云贷资金计划总量
		double yunDaiTotalAmount = loanService.queryYunDaiDailyTotalAmount(startTime, endTime);
		model.put("yunDaiTotalAmount", yunDaiTotalAmount);
		// 赞时贷资金计划总量
		double zsdTotalAmount = loanService.queryZsdDailyTotalAmount(startTime, endTime);
		model.put("zsdTotalAmount", zsdTotalAmount);
		// 7贷资金计划总量
		double sevenTotalAmount = loanService.querySevenDailyTotalAmount(startTime, endTime);
		model.put("sevenTotalAmount", sevenTotalAmount);

		// 放款成功的金额、笔数统计
		LoanDailyStatisticsVO paiedZan =  loanService.queryLoanDailyPaiedStatistics("ZAN", startTime, endTime);
		// 赞分期放款成功笔数
		model.put("zanPaiedCount", paiedZan.getPaiedCount());
		// 赞分期放款成功总金额
		model.put("zanPaiedTotalAmount", paiedZan.getPaiedTotalAmount());

		LoanDailyStatisticsVO paiedYunDai =  loanService.queryLoanDailyPaiedStatistics("YUN_DAI_SELF", startTime, endTime);
		// 云贷放款成功笔数
		model.put("yunDaiPaiedCount", paiedYunDai.getPaiedCount());
		// 云贷放款成功总金额
		model.put("yunDaiPaiedTotalAmount", paiedYunDai.getPaiedTotalAmount());

		LoanDailyStatisticsVO paiedZsd =  loanService.queryLoanDailyPaiedStatistics("ZSD", startTime, endTime);
		// 赞时贷放款成功笔数
		model.put("zsdPaiedCount", paiedZsd.getPaiedCount());
		// 赞时贷放款成功总金额
		model.put("zsdPaiedTotalAmount", paiedZsd.getPaiedTotalAmount());

		LoanDailyStatisticsVO paiedSeven =  loanService.queryLoanDailyPaiedStatistics("7_DAI_SELF", startTime, endTime);
		// 7贷放款成功笔数
		model.put("sevenPaiedCount", paiedSeven.getPaiedCount());
		// 7贷放款成功总金额
		model.put("sevenPaiedTotalAmount", paiedSeven.getPaiedTotalAmount());

		//放款处理中的金额、笔数统计
		LoanDailyStatisticsVO payingZan =  loanService.queryLoanDailyPayingStatistics("ZAN", startTime, endTime);
		// 赞分期放款处理中笔数
		model.put("zanPayingCount", payingZan.getPayingCount());
		// 赞分期放款处理中总金额
		model.put("zanPayingTotalAmount", payingZan.getPayingTotalAmount());

		LoanDailyStatisticsVO payingYunDai =  loanService.queryLoanDailyPayingStatistics("YUN_DAI_SELF", startTime, endTime);
		// 云贷放款处理中笔数
		model.put("yunDaiPayingCount", payingYunDai.getPayingCount());
		// 云贷放款处理中总金额
		model.put("yunDaiPayingTotalAmount", payingYunDai.getPayingTotalAmount());

		LoanDailyStatisticsVO payingZsd =  loanService.queryLoanDailyPayingStatistics("ZSD", startTime, endTime);
		// 赞时贷放款处理中笔数
		model.put("zsdPayingCount", payingZsd.getPayingCount());
		// 赞时贷放款处理中总金额
		model.put("zsdPayingTotalAmount", payingZsd.getPayingTotalAmount());

		LoanDailyStatisticsVO payingSeven =  loanService.queryLoanDailyPayingStatistics("7_DAI_SELF", startTime, endTime);
		// 7贷放款处理中笔数
		model.put("sevenPayingCount", payingSeven.getPayingCount());
		// 7贷放款处理中总金额
		model.put("sevenPayingTotalAmount", payingSeven.getPayingTotalAmount());

		return "/statistics/loanDaily/index";
	}

	/**
	 * 放款日常管理 导出excel
	 * @param request
	 * @param response
     */
	@RequestMapping("/statistics/loanDaily/export_xls")
	public void loanDailyExportExcel(HttpServletRequest request, HttpServletResponse response) {
		LoanDailyVO record = new LoanDailyVO();
		String mobile = request.getParameter("mobile");
		String userName = request.getParameter("userName");
		String idCard = request.getParameter("idCard");
		String orderNo = request.getParameter("orderNo");
		String loanStatus = request.getParameter("loanStatus");
		String lnUserId = request.getParameter("lnUserId");
		String bankCardNo = request.getParameter("bankCardNo");
		String depositionId = request.getParameter("depositionId");
		String partnerCode = request.getParameter("partnerCode");
		String startTime = request.getParameter("startTime");
		String endTime = request.getParameter("endTime");

		if(StringUtils.isNotEmpty(mobile)) {
			record.setMobile(mobile.trim());
		}
		if(StringUtils.isNotEmpty(userName)) {
			record.setUserName(userName.trim());
		}
		if(StringUtils.isNotEmpty(idCard)) {
			record.setIdCard(idCard.trim());
		}

		if(StringUtils.isNotEmpty(orderNo)) {
			record.setOrderNo(orderNo.trim());
		}
		if(StringUtils.isNotEmpty(loanStatus)) {
			record.setLoanStatus(loanStatus);
		}
		if(StringUtils.isNotEmpty(lnUserId)) {
			record.setLnUserId(Integer.valueOf(lnUserId));
		}
		if(StringUtils.isNotEmpty(bankCardNo)) {
			record.setBankCardNo(bankCardNo.trim());
		}
		if(StringUtils.isNotEmpty(depositionId)) {
			record.setDepositionId(Integer.parseInt(depositionId));
		}
		if(StringUtils.isNotEmpty(partnerCode)) {
			record.setPartnerCode(partnerCode);
		}

		Date date = new Date();
		// 默认查询当天记录
		startTime = StringUtil.isNotEmpty(startTime) == false ? DateUtil.formatYYYYMMDD(date) : startTime;
		endTime = StringUtil.isNotEmpty(endTime) == false ? DateUtil.formatYYYYMMDD(date) : endTime;
		record.setStartTime(startTime);
		record.setEndTime(endTime);
		record.setPageNum(1);

		// 统计
		int count = loanService.queryLoanDailyCount(record);
		record.setNumPerPage(count);

		List<LoanDailyVO> resultList = new ArrayList<LoanDailyVO>();
		if(count > 0) {
			resultList = loanService.queryLoanDailyInfo(record);
		}

		List<Map<String,List<Object>>> excelList = new ArrayList<Map<String,List<Object>>>();
		Map<String,List<Object>> titleMap = new HashMap<String, List<Object>>();
		List<Object> titles = new ArrayList<Object>();
		titles.add("合作方");
		titles.add("申请日期");
		titles.add("放款时间");
		//titles.add("订单号");
		//titles.add("放款金额");
		titles.add("放款本金");
		titles.add("服务费");
		titles.add("标的编号");
		titles.add("资产端借款编号");
		titles.add("借款人姓名");
		//titles.add("手机号码");
		titles.add("银行卡号");
		titles.add("订单状态");
		titles.add("退票标志");
		titleMap.put("title", titles);
		excelList.add(titleMap);

		if(!CollectionUtils.isEmpty(resultList)) {
			int i = 0;
			for (LoanDailyVO data : resultList) {
				Map<String,List<Object>> datasMap = new HashMap<String, List<Object>>();
				List<Object> obj = new ArrayList<Object>();
				if(StringUtils.isNotEmpty(data.getPartnerCode())) {
					if("YUN_DAI_SELF".equals(data.getPartnerCode())) {
						obj.add("云贷");
					}else if("ZAN".equals(data.getPartnerCode())) {
						obj.add("赞分期");
					}else if("ZSD".equals(data.getPartnerCode())) {
						obj.add("赞时贷");
					}else if("7_DAI_SELF".equals(data.getPartnerCode())) {
						obj.add("7贷");
					}
				}else {
					obj.add("");
				}
				obj.add(DateUtil.formatYYYYMMDD(data.getCreateTime()));
				obj.add(data.getLoanTime() == null ? "" : DateUtil.formatYYYYMMDD(data.getLoanTime()));
//				obj.add(data.getOrderNo());
				obj.add(data.getApproveAmount());
				obj.add(data.getHeadFee()==null?0:data.getHeadFee());
				obj.add(data.getDepositionId());
				obj.add(data.getPartnerLoanId());
//				obj.add(data.getMobile());
				obj.add(data.getUserName());
				obj.add(data.getBankCardNo() == null ? "" : data.getBankCardNo().substring(0, 4)+"****"+data.getBankCardNo().substring( data.getBankCardNo().length()-4, data.getBankCardNo().length()));
				obj.add(getOrderStatus(data.getOrderStatus()));
				obj.add(data.getPartnerRepayId() == null ? "" : "已退票");
				datasMap.put("loanDaily"+(++i), obj);
				excelList.add(datasMap);
			}
		}

		try {
			ExportUtil.exportExcel(response, request, "放款日常数据导出", excelList);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	
	
	/**
	 * 放款日常管理 list--用于退票功能
	 * @param request
	 * @param response
	 * @param model
     * @return
     */
	@RequestMapping("/statistics/loanDaily/refundIndex")
	public String revenueCollectionRepayForRefund(HttpServletRequest request, HttpServletResponse response, Map<String, Object> model) {
		LoanDailyVO record = new LoanDailyVO();

		String mobile = request.getParameter("mobile");
		String userName = request.getParameter("userName");
		String idCard = request.getParameter("idCard");

		String orderNo = request.getParameter("orderNo");
		String loanStatus = request.getParameter("loanStatus");
		String lnUserId = request.getParameter("lnUserId");

		String bankCardNo = request.getParameter("bankCardNo");
		String depositionId = request.getParameter("depositionId");
		String partnerCode = request.getParameter("partnerCode");

		String partnerLoanId = request.getParameter("partnerLoanId");
		
		String startTime = request.getParameter("startTime");
		String endTime = request.getParameter("endTime");
		String pageNum = request.getParameter("pageNum");
		String numPerPage = request.getParameter("numPerPage");

		if (StringUtil.isEmpty(pageNum) || StringUtil.isEmpty(numPerPage)) {
			pageNum = String.valueOf(Constants.MANAGE_DEFAULT_PAGENUM);
			numPerPage = String.valueOf(Constants.MANAGE_DEFAULT_NUMPERPAGE);
		}
		
		Date lastDay = DateUtil.addDays(new Date(), -1);
		// 默认查询昨天，放款中，云贷的记录
		startTime = StringUtil.isNotEmpty(startTime) == false ? DateUtil.formatYYYYMMDD(lastDay) : startTime;
		endTime = StringUtil.isNotEmpty(endTime) == false ? DateUtil.formatYYYYMMDD(lastDay) : endTime;
		partnerCode = StringUtils.isEmpty(partnerCode) ? PartnerEnum.YUN_DAI_SELF.getCode() : partnerCode;
		loanStatus = StringUtils.isEmpty(loanStatus) ? "PAYING" : loanStatus;
		
		if(StringUtils.isNotEmpty(mobile)) {
			record.setMobile(mobile.trim());
		}
		if(StringUtils.isNotEmpty(userName)) {
			record.setUserName(userName.trim());
		}
		if(StringUtils.isNotEmpty(idCard)) {
			record.setIdCard(idCard.trim());
		}

		if(StringUtils.isNotEmpty(orderNo)) {
			record.setOrderNo(orderNo.trim());
		}
		if(StringUtils.isNotEmpty(loanStatus)) {
			record.setLoanStatus(loanStatus);
		}
		if(StringUtils.isNotEmpty(lnUserId)) {
			record.setLnUserId(Integer.valueOf(lnUserId));
		}

		if(StringUtils.isNotEmpty(bankCardNo)) {
			record.setBankCardNo(bankCardNo.trim());
		}

		if(StringUtils.isNotEmpty(depositionId)) {
			record.setDepositionId(Integer.parseInt(depositionId));
		}
		if(StringUtils.isNotEmpty(partnerCode)) {
			record.setPartnerCode(partnerCode);
		}
		if(StringUtils.isNotEmpty(partnerLoanId)) {
			record.setPartnerLoanId(partnerLoanId);
		}
		record.setStartTime(startTime);
		record.setEndTime(endTime);

		// 统计
		int count = loanService.queryLoanDailyCount(record);

		record.setPageNum(Integer.valueOf(pageNum));
		record.setNumPerPage(Integer.valueOf(numPerPage));
		record.setTotalRows(count);

		// 列表查询
		if(count > 0 ) {
			List<LoanDailyVO> resultList = loanService.queryLoanDailyInfo(record);
			model.put("totalRows", count);
			model.put("loanDailyList", resultList);
		}
		// 将数据返回给页面
		model.put("mobile", mobile);
		model.put("userName", userName);
		model.put("idCard", idCard);
		model.put("orderNo", orderNo);
		model.put("loanStatus", loanStatus);
		model.put("lnUserId", lnUserId);
		model.put("bankCardNo", bankCardNo);
		model.put("depositionId", depositionId);
		model.put("partnerCode", partnerCode);
		model.put("partnerLoanId", partnerLoanId);
		model.put("startTime", startTime);
		model.put("endTime", endTime);
		model.put("pageNum", pageNum);
		model.put("numPerPage", numPerPage);
		// 赞分期资金计划总量
		double zanTotalAmount =  loanService.queryZanDailyTotalAmount(startTime, endTime);
		model.put("zanTotalAmount", zanTotalAmount);
		// 云贷资金计划总量
		double yunDaiTotalAmount = loanService.queryYunDaiDailyTotalAmount(startTime, endTime);
		model.put("yunDaiTotalAmount", yunDaiTotalAmount);
		// 赞时贷资金计划总量
		double zsdTotalAmount = loanService.queryZsdDailyTotalAmount(startTime, endTime);
		model.put("zsdTotalAmount", zsdTotalAmount);
		// 7贷资金计划总量
		double sevenTotalAmount = loanService.querySevenDailyTotalAmount(startTime, endTime);
		model.put("sevenTotalAmount", sevenTotalAmount);
		
		// 放款成功的金额、笔数统计
		LoanDailyStatisticsVO paiedZan =  loanService.queryLoanDailyPaiedStatistics("ZAN", startTime, endTime);
		// 赞分期放款成功笔数
		model.put("zanPaiedCount", paiedZan.getPaiedCount());
		// 赞分期放款成功总金额
		model.put("zanPaiedTotalAmount", paiedZan.getPaiedTotalAmount());

		LoanDailyStatisticsVO paiedYunDai =  loanService.queryLoanDailyPaiedStatistics("YUN_DAI_SELF", startTime, endTime);
		// 云贷放款成功笔数
		model.put("yunDaiPaiedCount", paiedYunDai.getPaiedCount());
		// 云贷放款成功总金额
		model.put("yunDaiPaiedTotalAmount", paiedYunDai.getPaiedTotalAmount());

		LoanDailyStatisticsVO paiedZsd =  loanService.queryLoanDailyPaiedStatistics("ZSD", startTime, endTime);
		// 赞时贷放款成功笔数
		model.put("zsdPaiedCount", paiedZsd.getPaiedCount());
		// 赞时贷放款成功总金额
		model.put("zsdPaiedTotalAmount", paiedZsd.getPaiedTotalAmount());
		
		LoanDailyStatisticsVO paiedSeven =  loanService.queryLoanDailyPaiedStatistics("7_DAI_SELF", startTime, endTime);
		// 7贷放款成功笔数
		model.put("sevenPaiedCount", paiedSeven.getPaiedCount());
		// 7贷放款成功总金额
		model.put("sevenPaiedTotalAmount", paiedSeven.getPaiedTotalAmount());
		
		//放款处理中的金额、笔数统计
		LoanDailyStatisticsVO payingZan =  loanService.queryLoanDailyPayingStatistics("ZAN", startTime, endTime);
		// 赞分期放款处理中笔数
		model.put("zanPayingCount", payingZan.getPayingCount());
		// 赞分期放款处理中总金额
		model.put("zanPayingTotalAmount", payingZan.getPayingTotalAmount());

		LoanDailyStatisticsVO payingYunDai =  loanService.queryLoanDailyPayingStatistics("YUN_DAI_SELF", startTime, endTime);
		// 云贷放款处理中笔数
		model.put("yunDaiPayingCount", payingYunDai.getPayingCount());
		// 云贷放款处理中总金额
		model.put("yunDaiPayingTotalAmount", payingYunDai.getPayingTotalAmount());

		LoanDailyStatisticsVO payingZsd =  loanService.queryLoanDailyPayingStatistics("ZSD", startTime, endTime);
		// 赞时贷放款处理中笔数
		model.put("zsdPayingCount", payingZsd.getPayingCount());
		// 赞时贷放款处理中总金额
		model.put("zsdPayingTotalAmount", payingZsd.getPayingTotalAmount());
		
		LoanDailyStatisticsVO payingSeven =  loanService.queryLoanDailyPayingStatistics("7_DAI_SELF", startTime, endTime);
		// 7贷放款处理中笔数
		model.put("sevenPayingCount", payingSeven.getPayingCount());
		// 7贷放款处理中总金额
		model.put("sevenPayingTotalAmount", payingSeven.getPayingTotalAmount());
		return "/statistics/loanDaily/refund_index";
	}

	
	/**
	 * 放款日常管理--退票
	 * @param request
	 * @param response
	 * @param loanId
	 * @param partnerLoanId
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/statistics/loanDaily/refundTicket")
	public Map<String, Object> refundTicketOperate(HttpServletRequest request, HttpServletResponse response,
												   String loanId, String partnerLoanId) {
		Map<String, Object> result = checkIsExecuteRefund(loanId, partnerLoanId);
		// 订单校验
		result.put("statusCode", "200"); 
		String isExecuteRefund = (String)result.get("isExecuteRefund");
		if ("true".equals(isExecuteRefund)) {
			LnLoanExample loanExample = new LnLoanExample();
			loanExample.createCriteria().andPartnerLoanIdEqualTo(partnerLoanId).andIdEqualTo(Integer.parseInt(loanId))
				.andStatusEqualTo(LoanStatus.LOAN_STATUS_PAYING.getCode());
			List<LnLoan> loanList = lnLoanMapper.selectByExample(loanExample);
			if (!CollectionUtils.isEmpty(loanList)) {
				try {
					depFixedLoanPaymentService.doRefundTicket(loanList.get(0));
				} catch (Exception e) {
					log.error("管理台执行人工退票异常：{}", e);
					result.put("statusCode", "302"); // 执行退票异常
					result.put("message", StringUtil.isEmpty(e.getMessage()) ? "执行退票异常" : e.getMessage());
				}
			}
		} else {
			result.put("statusCode", "303"); // 订单校验不通过
			return result;
		}
		// 存操作记录
		CookieManager cookie = new CookieManager(request);
		String mUserId = cookie.getValue(CookieEnums._MANAGE_PLAT_FORM.getName(),
				CookieEnums._MANAGE_PLAT_FORM_USER_ID.getName(), true);
		MUser mUser = mUserService.findMUser(Integer.parseInt(mUserId));
		MUserOpRecord userOpRecord = new MUserOpRecord();
		userOpRecord.setOpUserId(mUser.getId());
		userOpRecord.setFunctionName("退票功能");
		userOpRecord.setOpContent(mUser.getName()+"执行退票");
		userOpRecord.setIp(AddressUtil.getIp(request));
		mUserOpRecordService.addMUserOpRecord(userOpRecord);
		log.info("method refundTicketOperate statusCode：{}", result.get("statusCode"));
		return result;
	}

	/**
	 * 放款日常管理--债券回退
	 * @param request
	 * @param loanId
	 * @param partnerLoanId
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/statistics/loanDaily/backLoanDebtFinancing")
	public Map<String, Object> backLoanDebtFinancingOperate(HttpServletRequest request, String loanId, String partnerLoanId) {
		Map<String, Object> result = new HashMap<>();
		result.put("statusCode", "200");

        LnLoanExample loanExample = new LnLoanExample();
        loanExample.createCriteria().andPartnerLoanIdEqualTo(partnerLoanId).andIdEqualTo(Integer.parseInt(loanId))
                .andStatusEqualTo(LoanStatus.LOAN_STATUS_CHECKED.getCode());
        List<LnLoan> loanList = lnLoanMapper.selectByExample(loanExample);
        if (!CollectionUtils.isEmpty(loanList)) {
            try {
                depFixedLoanPaymentService.backLoanDebtFinancing(loanList.get(0));
            } catch (Exception e) {
                log.error("管理台执行人工债券回退异常：{}", e);
                result.put("statusCode", "302");
                result.put("message", e.getMessage());
            }
        } else {
            result.put("statusCode", "303");
            return result;
        }

		// 存操作记录
		CookieManager cookie = new CookieManager(request);
		String mUserId = cookie.getValue(CookieEnums._MANAGE_PLAT_FORM.getName(),
				CookieEnums._MANAGE_PLAT_FORM_USER_ID.getName(), true);
		MUser mUser = mUserService.findMUser(Integer.parseInt(mUserId));
		MUserOpRecord userOpRecord = new MUserOpRecord();
		userOpRecord.setOpUserId(mUser.getId());
		userOpRecord.setFunctionName("债券回退功能");
		userOpRecord.setOpContent(mUser.getName()+"执行债券回退");
		userOpRecord.setIp(AddressUtil.getIp(request));
		mUserOpRecordService.addMUserOpRecord(userOpRecord);
		log.info("method backLoanBondOperate statusCode：{}", result.get("statusCode"));
		return result;
	}
	
	private Map<String, Object> checkIsExecuteRefund(String loanId, String partnerLoanId) {
		Map<String, Object> map = new HashMap<>();
		map.put("isExecuteRefund", "false");
		LnLoanExample loanExample = new LnLoanExample();
		loanExample.createCriteria().andPartnerLoanIdEqualTo(partnerLoanId).andIdEqualTo(Integer.parseInt(loanId))
			.andStatusEqualTo(LoanStatus.LOAN_STATUS_PAYING.getCode());
		List<LnLoan> loanList = lnLoanMapper.selectByExample(loanExample);
		// 借贷关系
		LnLoanRelationExample relationExample = new LnLoanRelationExample();
		relationExample.createCriteria().andLoanIdEqualTo(Integer.parseInt(loanId));
		List<LnLoanRelation> loanRelationList = lnLoanRelationMapper.selectByExample(relationExample);
		// 借贷关系加PAYING状态
		LnLoanRelationExample relationStatusExample = new LnLoanRelationExample();
		relationStatusExample.createCriteria().andLoanIdEqualTo(Integer.parseInt(loanId)).andStatusEqualTo(Constants.LOAN_RELATION_STATUS_PAYING);
		List<LnLoanRelation> loanRelationStatusList = lnLoanRelationMapper.selectByExample(relationStatusExample);
		if (!CollectionUtils.isEmpty(loanList) && !CollectionUtils.isEmpty(loanRelationList)
				&& !CollectionUtils.isEmpty(loanRelationStatusList) && loanRelationList.size() == loanRelationStatusList.size()) {
			LnLoan lnLoan = loanList.get(0);
			// 存管标的校验
			LnDepositionTargetExample depositionTargetExample = new LnDepositionTargetExample();
			depositionTargetExample.createCriteria().andLoanIdEqualTo(Integer.parseInt(loanId));
			List<LnDepositionTarget>  depositionTargetList = lnDepositionTargetMapper.selectByExample(depositionTargetExample);
			// 订单表校验
			LnPayOrdersExample payOrdersExample = new LnPayOrdersExample();
			payOrdersExample.createCriteria().andOrderNoEqualTo(lnLoan.getPayOrderNo());
			List<LnPayOrders>  payOrdersList = lnPayOrdersMapper.selectByExample(payOrdersExample);
			if (!CollectionUtils.isEmpty(depositionTargetList) && !CollectionUtils.isEmpty(payOrdersList)) {
				LnPayOrders lnPayOrders = payOrdersList.get(0);
				if (!(Constants.PROPERTY_SYMBOL_YUN_DAI_SELF.equals(lnPayOrders.getPartnerCode()) ||
						Constants.PRODUCT_PROPERTY_SYMBOL_7_DAI_SELF.equals(lnPayOrders.getPartnerCode()))) {
					map.put("errCode", "512");
					map.put("errMsg", "资产合作方非云贷非7贷");
					return map;
				}
				LnDepositionTarget lnDepositionTarget = depositionTargetList.get(0);
				Double loanAmountWithoutHeadFee = CalculatorUtil.calculate("a-a", lnLoan.getApproveAmount(), lnLoan.getHeadFee());
				Double leftAmountTotal = loanService.getRelationAmountByLoanIdAndStatus(loanId, Constants.LOAN_RELATION_STATUS_PAYING);
				// 订单金额=借款金额   和  借款金额=标的金额   和   借款金额=借贷关系剩余本金和
				if (lnLoan.getApproveAmount().compareTo(lnDepositionTarget.getTotalLimit()) != 0) {
					map.put("errCode", "513");
					map.put("errMsg", "借款金额和存管标的额度不一致");
					return map;
				}
				if (lnPayOrders.getAmount().compareTo(loanAmountWithoutHeadFee) != 0) {
					map.put("errCode", "514");
					map.put("errMsg", "订单金额和借款金额不一致");
					return map;
				}
				if (lnLoan.getApproveAmount().compareTo(leftAmountTotal) != 0) {
					map.put("errCode", "515");
					map.put("errMsg", "借款金额和借贷关系剩余本金不一致");
					return map;
				}
			} else {
				map.put("errCode", "511");
				map.put("errMsg", "存管标的信息或者订单信息不正确");
				return map;
			}
		} else {
			map.put("errCode", "510");
			map.put("errMsg", "借款状态或者借贷关系状态非法");
			return map;
		}
		map.put("isExecuteRefund", "true");
		return map;
	}
	
	
	// 获取订单状态
	private static String getOrderStatus(Integer orderStatus) {
		String statusStr = "";
		if (orderStatus != null) {
			int status = orderStatus;
			if (status == 1) {
				statusStr = "创建";
			} else if (status == 2) {
				statusStr = "通讯失败";
			} else if (status == 3) {
				statusStr = "预下单成功";
			} else if (status == 4) {
				statusStr = "预下单失败";
			} else if (status == 5) {
				statusStr = "支付处理中";
			}else if (status == 6) {
				statusStr = "支付成功";
			}else if (status == 7) {
				statusStr = "支付失败";
			}
		}
		return statusStr;
	}

	// ======================= 2017-10-11 放款日常管理 end =======================

	
	// ======================= 2018-01-04 还款日常管理（系统日常对账轧差信息） start =======================
		
	/**
	 * 还款日常管理 list，（原先默认不查询现改为默认查询当天）
	 * @param request
	 * @param response
	 * @param model
     * @return
     */
	@RequestMapping("/statistics/gachaDaily/index")
	public String dailyCheckGacha(HttpServletRequest request, HttpServletResponse response, Map<String, Object> model,
								  String queryDataFlag) {
		DailyCheckGachaVO record = new DailyCheckGachaVO();
		Date date = new Date();
		// 默认查询当天记录
		String startTime =  DateUtil.formatYYYYMMDD(date);
		String endTime = DateUtil.formatYYYYMMDD(date);
		//默认查询辅商户代扣还款
		String transType = BAOFOOTransTypeEnum.DEPREPAY_ASSIST_WITHHOLD_REPAY.getCode();
		record.setStartTime(startTime);
		record.setEndTime(endTime);
		record.setTransType(transType);
		// 统计
		int count = 0;
		if(StringUtil.isNotBlank(queryDataFlag) && "QUERYDATA".equals(queryDataFlag)) {
			count = bsPayOdersService.queryGachaCheckDailyCount(record);
		}
		record.setPageNum(Constants.MANAGE_DEFAULT_PAGENUM);
		record.setNumPerPage(Constants.MANAGE_100_NUMPERPAGE);
		record.setTotalRows(count);
		// 列表查询
		List<DailyCheckGachaVO> resultList = new ArrayList<DailyCheckGachaVO>();
		if(StringUtil.isNotBlank(queryDataFlag) && "QUERYDATA".equals(queryDataFlag)) {
			if(count > 0 ) {
				resultList = bsPayOdersService.queryGachaCheckDailyInfo(record);
				model.put("totalRows", count);
				model.put("gachaDailyList", resultList);
			} else {
				model.put("totalRows", 0);
			}
		}
		model.put("pageNum", String.valueOf(Constants.MANAGE_DEFAULT_PAGENUM));
		model.put("numPerPage", String.valueOf(Constants.MANAGE_100_NUMPERPAGE));
		model.put("startTime", startTime);
		model.put("endTime", endTime);
		model.put("transType", transType);
		return "/statistics/gachaDaily/index";
	}
	
	/**
	 * 还款日常管理 list
	 * @param request
	 * @param response
	 * @param model
     * @return
     */
	@RequestMapping("/statistics/gachaDaily/gachaIndex")
	public String dailyCheckGachaIndex(HttpServletRequest request, HttpServletResponse response, Map<String, Object> model) {
		String startTime = request.getParameter("startTime");
		String endTime = request.getParameter("endTime");
		String pageNum = request.getParameter("pageNum");
		String numPerPage = request.getParameter("numPerPage");
		if (StringUtil.isEmpty(pageNum) || StringUtil.isEmpty(numPerPage)) {
			pageNum = String.valueOf(Constants.MANAGE_DEFAULT_PAGENUM);
			numPerPage = String.valueOf(Constants.MANAGE_100_NUMPERPAGE);
		}
	
		DailyCheckGachaVO record = new DailyCheckGachaVO();
		String partnerCode = request.getParameter("partnerCode");
		String orderNo = request.getParameter("orderNo");
		String thdOrderNo = request.getParameter("thdOrderNo");
		String transType = request.getParameter("transType");
		String paymentChannelId = request.getParameter("paymentChannelId");
		String status = request.getParameter("status");

		if (StringUtils.isNotEmpty(partnerCode)) {
			record.setPartnerCode(partnerCode);
		}
		if (StringUtils.isNotEmpty(orderNo)) {
			record.setOrderNo(orderNo.trim());
		}
		if (StringUtils.isNotEmpty(thdOrderNo)) {
			record.setThdOrderNo(thdOrderNo.trim());
		}
		if (StringUtils.isNotEmpty(transType)) {
			record.setTransType(transType);
		}
		if (StringUtil.isNotEmpty(paymentChannelId)) {
			record.setPaymentChannelId(paymentChannelId);
		}
		if (StringUtils.isNotEmpty(status)) {
			record.setStatus(status);
		}
		record.setStartTime(startTime);
		record.setEndTime(endTime);
		record.setTransType(transType == null ? BAOFOOTransTypeEnum.DEPREPAY_ASSIST_WITHHOLD_REPAY.getCode() : transType);

		// 统计
		int count = bsPayOdersService.queryGachaCheckDailyCount(record);
		record.setPageNum(Integer.valueOf(pageNum));
		record.setNumPerPage(Integer.valueOf(numPerPage));
		record.setTotalRows(count);

		// 列表查询
		if(count > 0 ) {
			List<DailyCheckGachaVO> resultList = bsPayOdersService.queryGachaCheckDailyInfo(record);
			model.put("totalRows", count);
			model.put("gachaDailyList", resultList);
			// 还款金额统计
			Double totalRepayBalance = bsPayOdersService.queryGachaCheckDailySum(record);
			model.put("totalRepayBalance", totalRepayBalance);
		} else {
			model.put("totalRows", 0);
		}
		// 将数据返回给页面
		model.put("partnerCode", partnerCode);
		model.put("orderNo", orderNo);
		model.put("thdOrderNo", thdOrderNo);
		model.put("transType", transType);
		model.put("paymentChannelId", paymentChannelId);
		model.put("status", status);
		model.put("startTime", startTime);
		model.put("endTime", endTime);
		model.put("pageNum", pageNum);
		model.put("numPerPage", numPerPage);
		return "/statistics/gachaDaily/index";
	}
	
	/**
	 * 还款日常管理 导出excel
	 * @param request
	 * @param response
     */
	@RequestMapping("/statistics/gachaDaily/exportXls")
	public void dailyCheckGachaExportExcel(HttpServletRequest request, HttpServletResponse response) {
		DailyCheckGachaVO record = new DailyCheckGachaVO();
		String partnerCode = request.getParameter("partnerCode");
		String orderNo = request.getParameter("orderNo");
		String thdOrderNo = request.getParameter("thdOrderNo");
		String transType = request.getParameter("transType");
		String paymentChannelId = request.getParameter("paymentChannelId");
		String status = request.getParameter("status");

		String startTime = request.getParameter("startTime");
		String endTime = request.getParameter("endTime");
		
		Date date = new Date();
		// 默认查询当天记录
		startTime = StringUtil.isEmpty(startTime) ? DateUtil.formatYYYYMMDD(date) : startTime;
		endTime = StringUtil.isEmpty(endTime) ? DateUtil.formatYYYYMMDD(date) : endTime;
		// 默认查询辅商户代扣还款
		transType = transType == null ? BAOFOOTransTypeEnum.DEPREPAY_ASSIST_WITHHOLD_REPAY.getCode() : transType;

		if (StringUtils.isNotEmpty(partnerCode)) {
			record.setPartnerCode(partnerCode);
		}
		if (StringUtils.isNotEmpty(orderNo)) {
			record.setOrderNo(orderNo.trim());
		}
		if (StringUtils.isNotEmpty(thdOrderNo)) {
			record.setThdOrderNo(thdOrderNo.trim());
		}
		if (StringUtils.isNotEmpty(transType)) {
			record.setTransType(transType);
		}
		if (StringUtil.isNotEmpty(paymentChannelId)) {
			record.setPaymentChannelId(paymentChannelId);
		}
		if (StringUtils.isNotEmpty(status)) {
			record.setStatus(status);
		}
		record.setStartTime(startTime);
		record.setEndTime(endTime);

		// 统计
		int count = bsPayOdersService.queryGachaCheckDailyCount(record);
		record.setPageNum(1);
		record.setNumPerPage(count);
		List<DailyCheckGachaVO> resultList = new ArrayList<>();
		if(count > 0) {
			resultList = bsPayOdersService.queryGachaCheckDailyInfo(record);
		}

		List<Map<String,List<Object>>> excelList = new ArrayList<Map<String,List<Object>>>();
		Map<String,List<Object>> titleMap = new HashMap<String, List<Object>>();
		List<Object> titles = new ArrayList<Object>();
		titles.add("序号");
		titles.add("合作方");
		titles.add("商户通道");
		titles.add("交易类型");
		titles.add("币港湾订单号");
		titles.add("合作方订单号");
		titles.add("订单金额");
		titles.add("状态");
		titles.add("状态描述");
		titles.add("请求时间");
		titles.add("完成时间");
		titleMap.put("title", titles);
		excelList.add(titleMap);

		if(!CollectionUtils.isEmpty(resultList)) {
			int i = 0;
			for (DailyCheckGachaVO data : resultList) {
				Map<String,List<Object>> datasMap = new HashMap<String, List<Object>>();
				List<Object> obj = new ArrayList<Object>();
				obj.add(data.getSerialNo());
				obj.add(data.getPartnerCode());
				obj.add(data.getPaymentChannelId());
				if (BAOFOOTransTypeEnum.getEnumByCode(data.getTransType()) == null) {
					obj.add("未知的业务类型");
				} else {
					obj.add(BAOFOOTransTypeEnum.getEnumByCode(data.getTransType()).getDescription());
				}
				obj.add(data.getOrderNo());
				obj.add(data.getThdOrderNo());
				obj.add(data.getTransAmount()==null ? 0: data.getTransAmount());
				obj.add(data.getStatus());
				obj.add(data.getStatusRemark());
				obj.add(data.getRequestTime() == null ? "" : DateUtil.format(data.getRequestTime()));
				obj.add(data.getFinishTime() == null ? "" : DateUtil.format(data.getFinishTime()));
				datasMap.put("gachaDaily"+(++i), obj);
				excelList.add(datasMap);
			}
		}
		try {
			ExportUtil.exportExcel(response, request, "还款日常管理导出", excelList);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// ======================= 2018-01-04 还款日常管理（系统日常对账轧差信息） end =======================
	
	
	/**
	 * 云贷存管未来30天兑付查询
	 * @param req
	 * @param model
	 * @return
	 */
	@RequestMapping("/account/depCash30/index")
	public String depCash30(String partnerCode,HttpServletRequest request, HttpServletResponse response,Map<String, Object> model){
		

		if (StringUtil.isBlank(partnerCode)) {
			partnerCode = Constants.PROPERTY_SYMBOL_YUN_DAI_SELF;
		}
		
		BsDepCash30Example example = new BsDepCash30Example();
		example.createCriteria().andCashDateGreaterThan(new Date()).andPartnerCodeEqualTo(partnerCode);
		List<BsDepCash30> list = bsDepCash30Mapper.selectByExample(example);
		
		Double vipAmount = 0d;
		Double vipStandAmount =0d;
		Double partnerStandAmount =0d;
		if (Constants.PROPERTY_SYMBOL_YUN_DAI_SELF.equals(partnerCode)) {
			vipAmount = lnLoanRelationMapper.depVipDeptsAmount();
			vipStandAmount = lnLoanRelationMapper.depVipStandAmount();
			partnerStandAmount = bsSubAccountService.querySumBgwAuthYunBalance(); // 云贷站岗户余额
		}else if (Constants.PROPERTY_SYMBOL_ZSD.equals(partnerCode)) {
			vipAmount = lnLoanRelationMapper.depVipDeptsZsdAmount();
			vipStandAmount = lnLoanRelationMapper.depVipStandZsdAmount();
			partnerStandAmount = bsSubAccountService.querySumBgwAuthZsdBalance(); 
		}else if (Constants.PRODUCT_PROPERTY_SYMBOL_7_DAI_SELF.equals(partnerCode)) {
			vipAmount = lnLoanRelationMapper.depVipDeptsLoan7Amount();
			vipStandAmount = lnLoanRelationMapper.depVipStandLoan7Amount();
			partnerStandAmount = bsSubAccountService.querySumBgwAuthSevrnBalance(); // 7贷站岗户余额
		}else if (Constants.PROPERTY_SYMBOL_FREE.equals(partnerCode)) {
			vipAmount = lnLoanRelationMapper.depVipDeptsLoanFreeAmount();
			vipStandAmount = lnLoanRelationMapper.depVipStandLoanFreeAmount();
			partnerStandAmount = bsSubAccountService.querySumBgwAuthFreeBalance(); // 自由站岗户
		}

		model.put("partnerCode", partnerCode);
		model.put("depCash", list);
		model.put("vipAmount", vipAmount);
		model.put("vipStandAmount", vipStandAmount);
		model.put("partnerStandAmount", partnerStandAmount);
		return "/account/depCash30/index";
	}

	// ======================= 2018-07-09 日常业务-借款账单管理 start =======================

	/**
	 * 日常业务-借款账单管理列表
	 * @param request
	 * @param response
	 * @param model
     * @return
     */
	@RequestMapping("/statistics/getLoanBill/index")
	public String getLoanBill(HttpServletRequest request, HttpServletResponse response, Map<String, Object> model) {
		LoanBillVO record = new LoanBillVO();
		String partnerCode = request.getParameter("partnerCode");
		String repayType = request.getParameter("repayType");
		String partnerLoanId = request.getParameter("partnerLoanId");
		String partnerRepayId = request.getParameter("partnerRepayId");
		String planDateStart = request.getParameter("planDateStart");
		String planDateEnd = request.getParameter("planDateEnd");
		String pageNum = request.getParameter("pageNum");
		String numPerPage = request.getParameter("numPerPage");

		if (StringUtil.isEmpty(pageNum) || StringUtil.isEmpty(numPerPage)) {
			pageNum = String.valueOf(Constants.MANAGE_DEFAULT_PAGENUM);
			numPerPage = String.valueOf(Constants.MANAGE_DEFAULT_NUMPERPAGE);
		}
		Date date = new Date();
		boolean queryFlag =StringUtil.isNotEmpty(planDateEnd) && StringUtil.isNotEmpty(planDateStart) ? true : false;
		
		// 默认查询7天记录
		planDateStart = StringUtil.isNotEmpty(planDateStart)? planDateStart :DateUtil.formatYYYYMMDD(DateUtil.addDays(date, -6));
		planDateEnd = StringUtil.isNotEmpty(planDateEnd)? planDateEnd : DateUtil.formatYYYYMMDD(date);

		if(StringUtils.isNotEmpty(partnerCode)) {
			record.setPartnerCode(partnerCode);
		}
		if(StringUtils.isNotEmpty(repayType)) {
			record.setRepayType(repayType);
		}else{
			record.setRepayType("INIT");
		}
		if(StringUtils.isNotEmpty(partnerLoanId)) {
			record.setPartnerLoanId(partnerLoanId.trim());
		}
		if(StringUtils.isNotEmpty(partnerRepayId)) {
			record.setPartnerRepayId(partnerRepayId.trim());
		}
		record.setPlanDateStart(planDateStart);
		record.setPlanDateEnd(planDateEnd);

		if (queryFlag) {
			// 时间入参为空时，表示从菜单进入，不统计
			int count = loanBillService.queryLoanBillCount(record);
			record.setPageNum(Integer.valueOf(pageNum));
			record.setNumPerPage(Integer.valueOf(numPerPage));
			record.setTotalRows(count);

			// 列表查询
			if(count > 0 ) {
				List<LoanBillVO> resultList = loanBillService.queryLoanBillList(record);
				model.put("loanBillList", resultList);
			}
			model.put("totalRows", count);
		}else{
			model.put("totalRows", 0);
		}

		// 将数据返回给页面
		model.put("partnerCode", partnerCode);
		model.put("repayType", repayType);
		model.put("partnerLoanId", partnerLoanId);
		model.put("partnerRepayId", partnerRepayId);
		model.put("planDateStart", planDateStart);
		model.put("planDateEnd", planDateEnd);
		model.put("pageNum", pageNum);
		model.put("numPerPage", numPerPage);

		return "/statistics/loanbill/index";
	}

	/**
	 * 日常业务-借款账单管理 导出excel
	 * @param request
	 * @param response
	 */
	@RequestMapping("/statistics/loanBill/export_xls")
	public void loanBillExportExcel(HttpServletRequest request, HttpServletResponse response) {
		LoanBillVO record = new LoanBillVO();
		String partnerCode = request.getParameter("partnerCode");
		String repayType = request.getParameter("repayType");
		String partnerLoanId = request.getParameter("partnerLoanId");
		String partnerRepayId = request.getParameter("partnerRepayId");
		String planDateStart = request.getParameter("planDateStart");
		String planDateEnd = request.getParameter("planDateEnd");

		Date date = new Date();
		// 默认查询7天记录
		planDateStart = StringUtil.isNotEmpty(planDateStart) == false ? DateUtil.formatYYYYMMDD(DateUtil.addDays(date, -6)) : planDateStart;
		planDateEnd = StringUtil.isNotEmpty(planDateEnd) == false ? DateUtil.formatYYYYMMDD(date) : planDateEnd;

		if(StringUtils.isNotEmpty(partnerCode)) {
			record.setPartnerCode(partnerCode);
		}
		if(StringUtils.isNotEmpty(repayType)) {
			record.setRepayType(repayType);
		}else{
			record.setRepayType("INIT");
		}
		if(StringUtils.isNotEmpty(partnerLoanId)) {
			record.setPartnerLoanId(partnerLoanId.trim());
		}
		if(StringUtils.isNotEmpty(partnerRepayId)) {
			record.setPartnerRepayId(partnerRepayId.trim());
		}
		record.setPlanDateStart(planDateStart);
		record.setPlanDateEnd(planDateEnd);
		record.setPageNum(1);

		// 统计
		int count = loanBillService.queryLoanBillCount(record);
		record.setNumPerPage(count);

		List<LoanBillVO> resultList = new ArrayList<LoanBillVO>();
		if(count > 0) {
			resultList = loanBillService.queryLoanBillList(record);
		}

		List<Map<String,List<Object>>> excelList = new ArrayList<Map<String,List<Object>>>();
		Map<String,List<Object>> titleMap = new HashMap<String, List<Object>>();
		List<Object> titles = new ArrayList<Object>();
		titles.add("资产方");
		titles.add("借款产品");
		titles.add("账单日");
		titles.add("借款编号");
		titles.add("账单编号");
		titles.add("账单状态");
		titles.add("账单本金");
		titles.add("账单利息");
		titles.add("还款时间");
		titles.add("还款类型");
		titles.add("还款总额");
		titles.add("还款本金");
		titles.add("还款利息");
		titleMap.put("title", titles);
		excelList.add(titleMap);
		if(!CollectionUtils.isEmpty(resultList)) {
			int i = 0;
			for (LoanBillVO data : resultList) {
				Map<String,List<Object>> datasMap = new HashMap<String, List<Object>>();
				List<Object> obj = new ArrayList<Object>();
				if(StringUtils.isNotEmpty(data.getPartnerCode())) {
					if("YUN_DAI_SELF".equals(data.getPartnerCode())) {
						obj.add("云贷");
					}else if("7_DAI_SELF".equals(data.getPartnerCode())) {
						obj.add("7贷");
					}
				}else {
					obj.add("");
				}

				if(StringUtils.isNotEmpty(data.getPartnerCode())) {
					if("YUN_DAI_SELF".equals(data.getPartnerCode())) {
						if("REPAY_ANY_TIME".equals(data.getPartnerBusinessFlag())) {
							obj.add("现金循环贷");
						}else if ("FIXED_INSTALLMENT".equals(data.getPartnerBusinessFlag())) {
							obj.add("等额本息");
						}else if ("FIXED_PRINCIPAL_INTEREST".equals(data.getPartnerBusinessFlag())) {
							obj.add("等本等息");
						}
					}else {
						if("REPAY_ANY_TIME".equals(data.getPartnerBusinessFlag())) {
							obj.add("随借随还");
						}else {
							obj.add(data.getPartnerBusinessFlag());
						}
					}
				}else {
					obj.add("");
				}
				obj.add(DateUtil.formatYYYYMMDD(data.getPlanDate()));
				obj.add(data.getPartnerLoanId());
				obj.add(data.getPartnerRepayId());

				if(StringUtils.isNotEmpty(data.getStatus())) {
					if("INIT".equals(data.getStatus())) {
						obj.add("未还款");
					}else if("REPAIED".equals(data.getStatus())) {
						obj.add("已还款");
					}else if("LATE_NOT".equals(data.getStatus())) {
						obj.add("逾期未还款");
					}
				}else {
					obj.add("");
				}
				obj.add(data.getSchedulePrincipalPlanAmount());
				obj.add(data.getScheduleInterestPlanAmount());
				obj.add(DateUtil.format(data.getDoneTime()));
				if(StringUtils.isNotEmpty(data.getRepayType())) {
					if("NORMA_REPAY".equals(data.getRepayType())) {
						obj.add("正常还款");
					}else if("COMPENSATE_REPAY".equals(data.getRepayType())) {
						obj.add("代偿还款");
					}else if("OFFLINE_REPAY".equals(data.getRepayType())) {
						obj.add("线下还款");
					}
				}else {
					obj.add("");
				}
				obj.add(data.getRepayTotal());
				obj.add(data.getRepayPrincipalDoneAmount());
				obj.add(data.getRepayInterestDoneAmount());
				datasMap.put("loanBill"+(++i), obj);
				excelList.add(datasMap);
			}
		}

		try {
			ExportUtil.exportExcel(response, request, "借款账单管理", excelList);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	// ======================= 2017-07-09 日常业务-借款账单管理 end =======================

}
