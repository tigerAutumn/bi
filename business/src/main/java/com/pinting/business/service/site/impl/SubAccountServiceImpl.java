package com.pinting.business.service.site.impl;

import com.pinting.business.accounting.loan.enums.VIPId4PartnerEnum;
import com.pinting.business.accounting.loan.service.LoanRelationshipService;
import com.pinting.business.dao.*;
import com.pinting.business.hessian.site.message.ReqMsg_Invest_PlatformStatistics;
import com.pinting.business.hessian.site.message.ResMsg_Invest_PlatformStatistics;
import com.pinting.business.model.*;
import com.pinting.business.model.vo.*;
import com.pinting.business.service.site.SendWechatService;
import com.pinting.business.service.site.SubAccountService;
import com.pinting.business.service.site.SysConfigService;
import com.pinting.business.util.BeanUtils;
import com.pinting.business.util.Constants;
import com.pinting.core.redis.JedisClientDaoSupport;
import com.pinting.core.util.DateUtil;
import com.pinting.core.util.MoneyUtil;
import com.pinting.gateway.in.util.MethodRole;
import net.sf.json.JSONObject;
import org.apache.velocity.tools.generic.NumberTool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.*;

@Service
public class SubAccountServiceImpl implements SubAccountService{
	private final Logger logger = LoggerFactory.getLogger(SubAccountServiceImpl.class);
	@Autowired
	private BsSubAccountMapper bsSubAccountMapper;
	@Autowired
	private BsUserMapper bsUserMapper;
	@Autowired
	private BsProductMapper  bsProductMapper;
	@Autowired
	private BsAccountMapper bsAccountMapper;
	@Autowired
	private BsSubAccountPairMapper bsSubAccountPairMapper;
	@Autowired
	private LnLoanRelationMapper lnLoanRelationMapper;
	@Autowired
	private SendWechatService sendWechatService;
	@Autowired
	private SysConfigService sysConfigService;
	@Autowired
	private LoanRelationshipService loanRelationshipService;
	@Autowired
	private BsInterestTicketInfoMapper bsInterestTicketInfoMapper;

	private static JedisClientDaoSupport jsClientDaoSupport = JedisClientDaoSupport.getInstance(Constants.REDIS_SUBSYS_BUSINESS);
	
	@Override
	public boolean addSubAccount(BsSubAccount subAccount) {
		return bsSubAccountMapper.insertSelective(subAccount)==1;
	}
	
	public int addSubAccountReturnId(BsSubAccount subAccount) {
		bsSubAccountMapper.insertSelective(subAccount);
		return subAccount.getId() != null ? subAccount.getId() : 0;
	}

	@Override
	@MethodRole("APP")
	public BsSubAccount findJSHSubAccountByUserId(Integer userId) {
		
		return bsSubAccountMapper.selectJSHSubAccountByUserId(userId);
	}

	@Override
	public BsSubAccount findDEPJSHSubAccountByUserId(Integer userId) {
		return bsSubAccountMapper.selectDEPJSHSubAccountByUserId(userId);
	}

	@Override
	@MethodRole("APP")
	public BsSubAccount findJLJSubAccountByUserId(Integer userId) {
		
		return bsSubAccountMapper.selectJLJSubAccountByUserId(userId);
	}
	
	@Override
	public boolean modifySubAccountById(BsSubAccount subAccount) {
		BsSubAccountExample subAccountExample = new BsSubAccountExample();
		
		subAccountExample.createCriteria().andIdEqualTo(subAccount.getId());
		
		return bsSubAccountMapper.updateByExampleSelective(subAccount, subAccountExample)>0?true:false;
	}

	@Override
	@MethodRole("APP")
	public List<BsSubAccountVO> findMyInvestByUserIdAndType(String type,int userId,int start,int pageSize,int status) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("type", type);
		paramMap.put("userId", userId);
		paramMap.put("start", start);
		paramMap.put("pageSize", pageSize);  
		paramMap.put("status", status);  
		
		List<BsSubAccountVO> dataList = bsSubAccountMapper.selectByExamplePage(paramMap);
		return dataList.size() > 0? dataList : null;
	}
	
	@Override
	@MethodRole("APP")
	public Integer findMyInvestCountByUserId(Integer userId) {
		
		Map<String,Object> data = new HashMap<String,Object>();
		data.put("userId", userId);
		
		return bsSubAccountMapper.countMyInvestCount(data);
	}

	@Override
	public BsSubAccountVO findMyInvestByUserIdAndId(Integer userId, Integer id) {
		Map<String,Object> paramMap = new HashMap<String,Object>();
		paramMap.put("userId", userId);
		paramMap.put("id", id);
		return bsSubAccountMapper.selectByExampleByUserIdAndId(paramMap);
	}

	
	@Override
	@MethodRole("APP")
	public BsSubAccount findSubAccountById(Integer subAccountId) {
		return bsSubAccountMapper.selectByPrimaryKey(subAccountId);
	}
	
	@Override
	public boolean modifyBalancesByIncrement(BsSubAccount bsSubAccount) {
		return bsSubAccountMapper.updateBalancesByIncrement(bsSubAccount) > 0;
	}

	@Override
	public int countProductNumByProductCode(String code) {
		
		//购买人数:总人数 =  count(投资中 + 已转让） 去除相同账户
		BsSubAccount subAccount = new BsSubAccount();
		subAccount.setProductCode(code);
		return bsSubAccountMapper.countProductNumByProductCode(subAccount);
	}

	@Override
	public int countProductNumDayByProductCode(String code) {
		//根据产品码查询当日购买人数
		BsSubAccountVO subAccount = new BsSubAccountVO();
		subAccount.setProductCode(code);
		subAccount.setStartTime(DateUtil.addDays(new Date(), -1));
		subAccount.setEndTime(new Date());
		
		return bsSubAccountMapper.countProductNumDayByProductCode(subAccount);
	}

	@Override
	public double countProductAmountByProductCode(String code) {
		BsSubAccount subAccount = new BsSubAccount();
		subAccount.setProductCode(code);
		Double productAmount = bsSubAccountMapper.countProductAmountByProductCode(subAccount);
		return productAmount==null?0:productAmount;
	}

	@Override
	public double countProductDayByProductCode(String code) {
		BsSubAccountVO subAccount = new BsSubAccountVO();
		subAccount.setProductCode(code);
		subAccount.setStartTime(DateUtil.addDays(new Date(), -1));
		subAccount.setEndTime(new Date());
		
		Double productDay = bsSubAccountMapper.countProductDayByProductCode(subAccount);
		
		return productDay==null?0:productDay;
	}

	@Override
	public int countProductBuyNum(String code) {
		
		List<Integer> values = new ArrayList<Integer>();
		values.add(Constants.SUBACCOUNT_STATUS_FINANCING);
		values.add(Constants.SUBACCOUNT_STATUS_SETTLE);
		
		BsSubAccountExample example = new BsSubAccountExample();
		example.createCriteria().andProductCodeEqualTo(code).andStatusIn(values);
		return bsSubAccountMapper.countByExample(example);
	}

	@Override
	public int countSubAccountBalanceBetweenNaNAndNaN(double balance1, double balance2) {
		
		List<Integer> values = new ArrayList<Integer>();
		values.add(2);
		values.add(3);
		values.add(5);
		BsSubAccountExample example = new BsSubAccountExample();
		example.createCriteria().andBalanceBetween(balance1, balance2).andStatusIn(values);
		return bsSubAccountMapper.countByExample(example);
	}

	@Override
	public Double sumPeriodCapital() {
		Double captial = bsSubAccountMapper.sumPeriodCaptial();
		return captial==null?0.0:captial;
	}

	
	@Override
	public ArrayList<BsSubAccountVO> selectCaptialAndInvestDayByProductCode(
			String productCode) {
		
		ArrayList<BsSubAccountVO> captial1 = bsSubAccountMapper.selectCaptialAndInvestInvesting(productCode);//投资中（上个月购买）
		ArrayList<BsSubAccountVO> captial2 = bsSubAccountMapper.selectCaptialAndInvestInvesting2(productCode);//投资中（本月新购买）
		ArrayList<BsSubAccountVO> captial3 = bsSubAccountMapper.selectCaptialAndInvestTransfered(productCode);//转让中
		ArrayList<BsSubAccountVO> captial4 = bsSubAccountMapper.selectCaptialAndInvestSettled(productCode);//已结算
		
		ArrayList<BsSubAccountVO> totalCaptial = new ArrayList<BsSubAccountVO>();
		totalCaptial.addAll(captial1);
		totalCaptial.addAll(captial2);
		totalCaptial.addAll(captial3);
		totalCaptial.addAll(captial4);
		
		return totalCaptial;
	}

	@Override
	public List<BsStatisticsVO> findUserBuyOrdersList(Integer productId,Integer pageNum,Integer numPerPage) {
		BsStatisticsVO statisticsVo = new BsStatisticsVO();
		
		if(productId != null && productId.intValue() > 0){
			statisticsVo.setProductId(productId);
		}
		
		statisticsVo.setPageNum(pageNum);
		statisticsVo.setNumPerPage(numPerPage);
		statisticsVo.setOrderField("s.open_time");
		statisticsVo.setOrderDirection("desc");
		return bsUserMapper.selectUserBuyMessageList(statisticsVo);
	}

	@Override
	public int countProductBuyNumByProductId(Integer productId, Integer userId) {
		BsSubAccountVO bsSubAccount = new BsSubAccountVO();
		bsSubAccount.setProductId(productId);
		bsSubAccount.setUserId(userId);
		return bsSubAccountMapper.countProductBuyNumByProductId(bsSubAccount);
	}

	@Override
	public double countProductAmountByProductId(Integer id, Integer userId) {
		
		BsSubAccountVO bsSubAccount = new BsSubAccountVO();
		bsSubAccount.setProductId(id);
		bsSubAccount.setUserId(userId);
		Double surplusAmount = bsSubAccountMapper.countProductSurplusAmountByProductId(bsSubAccount);
		
		//surplusAmount = null，代表用户从来没有购买过，则可以使用最高额度
		if(surplusAmount == null){
			//获取产品最大可购买额度
			BsProduct product = bsProductMapper.selectByPrimaryKey(id);
			Double maxSingleInvest = product.getMaxSingleInvestAmount();//获取个人最高可购买额度，
			if(maxSingleInvest == null){//如果为null，则没有最高限额
				surplusAmount = Double.MAX_VALUE;
			}else{
				surplusAmount = product.getMaxSingleInvestAmount();
			}
		}
		
		return surplusAmount < 0? 0.0 : surplusAmount;
	}

	@Override
	public double getMarkingCosts() {
		Double markingCost = bsSubAccountMapper.sumMarkingCosts(); 
		return markingCost == null? 0:markingCost;
	}

	@Override
	public double selectTodayMarketingCosts(){
		
		Double markingCost = bsSubAccountMapper.sumTodayMarketingCosts();
		return markingCost == null? 0:markingCost;
	}

	@Override
	@MethodRole("APP")
	public void platformStatistics(
			ReqMsg_Invest_PlatformStatistics req,
			ResMsg_Invest_PlatformStatistics res) {
		//按月查今天内用户投资情况
		List<TotalInvestGroupByMonthVO> totalInvestGroupByMonthVOs = bsSubAccountMapper.selectTotalInvestGroupByMonth();
	

		List<TotalInvestGroupByMonthVO> totalInvestVOs = new ArrayList<TotalInvestGroupByMonthVO>();
		NumberTool tool = new NumberTool();
		Calendar cal = Calendar.getInstance();
		int month = cal.get(Calendar.MONTH) + 1;
		for (int i = 1; i <= month; i++) {
			TotalInvestGroupByMonthVO totalInvestGroupByMonthVO = new TotalInvestGroupByMonthVO();
			totalInvestGroupByMonthVO.setTotalInvestString("0");
			switch (i) {
			case 1:
				totalInvestGroupByMonthVO.setInvestMonth("一月");
			break;
			case 2:
				totalInvestGroupByMonthVO.setInvestMonth("二月");
			break;
			case 3:
				totalInvestGroupByMonthVO.setInvestMonth("三月");
			break;
			case 4:
				totalInvestGroupByMonthVO.setInvestMonth("四月");
			break;
			case 5:
				totalInvestGroupByMonthVO.setInvestMonth("五月");
			break;
			case 6:
				totalInvestGroupByMonthVO.setInvestMonth("六月");
			break;
			case 7:
				totalInvestGroupByMonthVO.setInvestMonth("七月");
			break;
			case 8:
				totalInvestGroupByMonthVO.setInvestMonth("八月");
			break;
			case 9:
				totalInvestGroupByMonthVO.setInvestMonth("九月");
			break;
			case 10:
				totalInvestGroupByMonthVO.setInvestMonth("十月");
			break;
			case 11:
				totalInvestGroupByMonthVO.setInvestMonth("十一月");
			break;
			case 12:
				totalInvestGroupByMonthVO.setInvestMonth("十二月");
			break;
			}
			
			for (TotalInvestGroupByMonthVO totalInvest : totalInvestGroupByMonthVOs) {
				if (String.valueOf(i).equals(totalInvest.getInvestMonth()) ) {
					totalInvestGroupByMonthVO.setTotalInvestString(tool.format("0",totalInvest.getTotalInvest()));
				}
			}
			
			totalInvestVOs.add(totalInvestGroupByMonthVO);
		}
		res.setTotalInvestGroupByMonth(BeanUtils.classToArrayList(totalInvestVOs));
		//查询用户待赚取收益总金额
		Double investInterestWill = bsSubAccountMapper.investInterestWill();
		res.setInvestInterestWill(tool.format("0.00",investInterestWill));
		
		//按照产品类型查新投资总金额
		List<InvestTotalGroupByProductVO> investTotalGroupByProductVOs = bsSubAccountMapper.investTotalGroupByProduct();
		for (InvestTotalGroupByProductVO investTotalGroupByProductVO : investTotalGroupByProductVOs) {
			investTotalGroupByProductVO.setInvestTotalGroupByProductAmountString(tool.format("0",investTotalGroupByProductVO.getInvestTotalGroupByProductAmount()));
		}
		res.setInvestTotalGroupByProduct(BeanUtils.classToArrayList(investTotalGroupByProductVOs));
		

	}

	@Override
	@MethodRole("APP")
	public List<BsSubAccountVO> selectMyInvestment(Integer userId, Integer start, Integer pageSize, Integer status) {
		List<BsSubAccountVO> result = bsSubAccountMapper.selectMyInvestment(userId, start, pageSize, status);
		return result;
	}

	@Override
	@MethodRole("APP")
	public void platformStatisticsNews(ReqMsg_Invest_PlatformStatistics req,
			ResMsg_Invest_PlatformStatistics res) {
		NumberTool tool = new NumberTool();
		Calendar cal = Calendar.getInstance();
		int month = cal.get(Calendar.MONTH) + 1;
		//分月查询当年每月的历史累计投资总金额
		List<InvestMentOverDateMonthVO> investMentOverDateMonthVOs = new ArrayList<InvestMentOverDateMonthVO>();
		int thisYear = cal.get(Calendar.YEAR);
		String thisYearStr = thisYear + "-01-01";
		Double historyTotalInvest =  bsSubAccountMapper.investMentOverDateMonth(thisYearStr);
		List<InvestAmountVO> dataList = bsSubAccountMapper.selectInvestAmountEachMonth();
		Double totalInvestOverMonth = historyTotalInvest;
		for (int i = 1; i <= month; i++) {
			for(InvestAmountVO vo : dataList) {
				Date date = vo.getDate();
				Calendar dateCal = Calendar.getInstance();
				dateCal.setTime(date);
				int thisMonth = dateCal.get(Calendar.MONTH) + 1;
				if(thisMonth == i) {
					totalInvestOverMonth = MoneyUtil.add(totalInvestOverMonth, vo.getInvestAmount()).doubleValue();
				}
			}
			InvestMentOverDateMonthVO investMentOverDateMonthVO = new InvestMentOverDateMonthVO();
			investMentOverDateMonthVO.setTotalInvestOverMonthString("0");
			investMentOverDateMonthVO.setTotalInvestOverMonthString(tool.format("0.00",totalInvestOverMonth));
			switch (i) {
			case 1:
				investMentOverDateMonthVO.setInvestMonth("一月");
			break;
			case 2:
				investMentOverDateMonthVO.setInvestMonth("二月");
			break;
			case 3:
				investMentOverDateMonthVO.setInvestMonth("三月");
			break;
			case 4:
				investMentOverDateMonthVO.setInvestMonth("四月");
			break;
			case 5:
				investMentOverDateMonthVO.setInvestMonth("五月");
			break;
			case 6:
				investMentOverDateMonthVO.setInvestMonth("六月");
			break;
			case 7:
				investMentOverDateMonthVO.setInvestMonth("七月");
			break;
			case 8:
				investMentOverDateMonthVO.setInvestMonth("八月");
			break;
			case 9:
				investMentOverDateMonthVO.setInvestMonth("九月");
			break;
			case 10:
				investMentOverDateMonthVO.setInvestMonth("十月");
			break;
			case 11:
				investMentOverDateMonthVO.setInvestMonth("十一月");
			break;
			case 12:
				investMentOverDateMonthVO.setInvestMonth("十二月");
			break;
			}
			investMentOverDateMonthVOs.add(investMentOverDateMonthVO);
		}
		res.setInvestMentOverDateMonth(BeanUtils.classToArrayList(investMentOverDateMonthVOs));
		
		//查询用户待赚取收益总金额
		Double investInterestWill = bsSubAccountMapper.investInterestWill();
		res.setInvestInterestWill(tool.format("0.00",investInterestWill));
		
		//根据端口类型查询产品平均收益率
		//钱报178
		Double averageInvestRate178 = bsProductMapper.selectAverageInvestRate("178");
		//普通用户
		Double averageInvestRateNormal = bsProductMapper.selectAverageInvestRate("NORMAL");
		res.setAverageInvestRate178(tool.format("0.00", averageInvestRate178));
		res.setAverageInvestRateNormal(tool.format("0.00", averageInvestRateNormal));
		
		//按照产品投资期限长短查询
		List<InvestTotalGroupByProductVO> investTotalGroupByProductTermVOs = bsSubAccountMapper.investTotalGroupByProductTerm();
		for (InvestTotalGroupByProductVO investTotalGroupByProductTermVO : investTotalGroupByProductTermVOs) {
			investTotalGroupByProductTermVO.setInvestTotalGroupByProductAmountString(tool.format("0",investTotalGroupByProductTermVO.getInvestTotalGroupByProductAmount()));
		}
		res.setInvestTotalGroupByProductTerm(BeanUtils.classToArrayList(investTotalGroupByProductTermVOs));
		
		//查询投资人性别比例
		List<InvestorTypeVO> investorTypeSexList = bsSubAccountMapper.investorTypeSex();
		for (InvestorTypeVO investorTypeVO : investorTypeSexList) {
			investorTypeVO.setInvestorTypeNumberString(tool.format("0",investorTypeVO.getInvestorTypeNumber()));
			investorTypeVO.setInvestorTypeRateString(tool.format("0.00",investorTypeVO.getInvestorTypeRate())+"%");
		}
		res.setInvestorTypeSex(BeanUtils.classToArrayList(investorTypeSexList));
		//查询投资人年龄段比例
		
		List<InvestorTypeVO> investorTypeAgeList = bsSubAccountMapper.investorTypeAge();
		for (InvestorTypeVO investorTypeVO : investorTypeAgeList) {
			investorTypeVO.setInvestorTypeNumberString(tool.format("0",investorTypeVO.getInvestorTypeNumber()));
			investorTypeVO.setInvestorTypeRateString(tool.format("0.00",investorTypeVO.getInvestorTypeRate())+"%");
		}
		res.setInvestorTypeAge(BeanUtils.classToArrayList(investorTypeAgeList));
		
		
	}

	@Override
	public void platformData(ReqMsg_Invest_PlatformStatistics req, ResMsg_Invest_PlatformStatistics res) {
		JSONObject platformOverviewJson = JSONObject.fromObject(jsClientDaoSupport.lrange(Constants.PLATFORM_DATA_REDIS_KEY_PLATFORMOVERVIEW).get(0));
		JSONObject transactionLendDataStatisticsJson = JSONObject.fromObject(jsClientDaoSupport.lrange(Constants.PLATFORM_DATA_REDIS_KEY_TRANSACTIONLENDDATASTATISTICS).get(0));
		JSONObject userDataJson = JSONObject.fromObject(jsClientDaoSupport.lrange(Constants.PLATFORM_DATA_REDIS_KEY_USERDATA).get(0));
		JSONObject overdueInfoJson = JSONObject.fromObject(jsClientDaoSupport.lrange(Constants.PLATFORM_DATA_REDIS_KEY_OVERDUEINFO).get(0));
		PlatformOverviewVO platformOverview = (PlatformOverviewVO) JSONObject.toBean(platformOverviewJson, PlatformOverviewVO.class);
		TransactionLendDataStatisticsVO transactionLendDataStatistics = (TransactionLendDataStatisticsVO) JSONObject.toBean(transactionLendDataStatisticsJson, TransactionLendDataStatisticsVO.class);
		UserDataVO userData = (UserDataVO) JSONObject.toBean(userDataJson, UserDataVO.class);
		OverdueInfoVO overdueInfo = (OverdueInfoVO) JSONObject.toBean(overdueInfoJson, OverdueInfoVO.class);

		NumberTool tool = new NumberTool();
		// 1. 概览
		res.setOperatingDays(String.valueOf(platformOverview.getOperatingDays()));
		res.setTotalInvestAmount(tool.format("0.00", platformOverview.getTotalBuyAmount()));
		res.setTotalInterestAmount(tool.format("0.00", platformOverview.getTotalIncomeAmount()));
		res.setTotalLoanAmount(tool.format("0.00", platformOverview.getTotalLoanAmount()));
		res.setFirstTime(DateUtil.formatDateTime(platformOverview.getCreateTime(), "yyyy-MM-dd HH:mm:ss"));

		// 2. 成交及出借数据统计
		// 每月累计平台成交额
		List<String> monthAmountList = transactionLendDataStatistics.getMonthPlatformBuyAmount();
		//分月查询当年每月的历史累计投资总金额
		List<InvestMentOverDateMonthVO> investMentOverDateMonthVOs = new ArrayList<>();
		for (int i = 1; i <= monthAmountList.size(); i++) {
			InvestMentOverDateMonthVO investMentOverDateMonthVO = new InvestMentOverDateMonthVO();
			investMentOverDateMonthVO.setTotalInvestOverMonthString("0");
			investMentOverDateMonthVO.setTotalInvestOverMonthString(tool.format("0.00", monthAmountList.get(i-1)));
			switch (i) {
				case 1:
					investMentOverDateMonthVO.setInvestMonth("一月");
					break;
				case 2:
					investMentOverDateMonthVO.setInvestMonth("二月");
					break;
				case 3:
					investMentOverDateMonthVO.setInvestMonth("三月");
					break;
				case 4:
					investMentOverDateMonthVO.setInvestMonth("四月");
					break;
				case 5:
					investMentOverDateMonthVO.setInvestMonth("五月");
					break;
				case 6:
					investMentOverDateMonthVO.setInvestMonth("六月");
					break;
				case 7:
					investMentOverDateMonthVO.setInvestMonth("七月");
					break;
				case 8:
					investMentOverDateMonthVO.setInvestMonth("八月");
					break;
				case 9:
					investMentOverDateMonthVO.setInvestMonth("九月");
					break;
				case 10:
					investMentOverDateMonthVO.setInvestMonth("十月");
					break;
				case 11:
					investMentOverDateMonthVO.setInvestMonth("十一月");
					break;
				case 12:
					investMentOverDateMonthVO.setInvestMonth("十二月");
					break;
			}
			investMentOverDateMonthVOs.add(investMentOverDateMonthVO);
		}
		res.setInvestMentOverDateMonth(BeanUtils.classToArrayList(investMentOverDateMonthVOs));
		// 各期限计划成交概况
		List<HashMap<String, Object>> planViewList = transactionLendDataStatistics.getEachTermProductBuyOverview();
		List<InvestTotalGroupByProductVO> investTotalGroupByProductTermVOs = new ArrayList<>();
		for (Object view : planViewList) {
			JSONObject planOverview = JSONObject.fromObject(view);
			InvestTotalGroupByProductVO vo = new InvestTotalGroupByProductVO();
			vo.setInvestTotalGroupByProductAmountString(tool.format("0", planOverview.get("amount")));
			vo.setInvestTotalGroupByProductAmount(Double.valueOf(planOverview.get("amount").toString()));
			vo.setProductName((String) planOverview.get("name"));
			investTotalGroupByProductTermVOs.add(vo);
		}
		res.setInvestTotalGroupByProductTerm(BeanUtils.classToArrayList(investTotalGroupByProductTermVOs));
		// 已赚取收益
		res.setInvestInterestWill(tool.format("0.00", transactionLendDataStatistics.getInterestOverview().getInvestInterestWill()));
		// 待赚取收益 概览中已经赋值
		// res.setTotalInterestAmount(tool.format("0.00", platformOverview.getTotalIncomeAmount()));
		// 自成立以来累计借贷金额（元）
		res.setTotalLoanAmount(String.valueOf(transactionLendDataStatistics.getLoanData().getTotalLoanAmount()));
		// 自成立以来累计借贷笔数;
		res.setTotalLoanNumber(transactionLendDataStatistics.getLoanData().getTotalLoanNumber());
		// 当前待还借贷金额（元）;
		res.setCurrentWaitRepayAmount(String.valueOf(transactionLendDataStatistics.getLoanData().getCurrentWaitRepayAmount()));
		// 当前待还借贷笔数;
		res.setCurrentWaitRepayNumber(transactionLendDataStatistics.getLoanData().getCurrentWaitRepayNumber());
		// 关联关系借款余额（元）-- 0.00;
		res.setRelationBorrowerAmount(String.valueOf(transactionLendDataStatistics.getLoanData().getRelationBorrowerAmount()));
		// 关联关系借款余额笔数 -- 0;
		res.setRelationBorrowerNumber(transactionLendDataStatistics.getLoanData().getRelationBorrowerNumber());
		res.setSecondTime(DateUtil.formatDateTime(transactionLendDataStatistics.getCreateTime(), "yyyy-MM-dd HH:mm:ss"));

		// 3. 用户数据统计
		LenderOrBorrowerData lenderData = userData.getLenderData();
		LenderOrBorrowerData borrowerData = userData.getBorrowerData();
		AgeOrGenderProportion ageOrGenderProportion = userData.getAgeOrGenderProportion();
		// 累计出借人数（人）
		res.setTotalNumber(lenderData.getTotalNumber());
		// 当期出借人数
		res.setCurrentNumber(lenderData.getCurrentNumber());
		// 人均累计出借金额
		res.setEachTotalAmount(String.valueOf(lenderData.getEachTotalAmount()));
		// 前十大出借人出借金额占比%
		res.setTopTenAmtProportion(String.valueOf(lenderData.getTopTenAmtProportion()));
		// 最大单一出借人出借余额占比%
		res.setTopAmtProportion(String.valueOf(lenderData.getTopAmtProportion()));
		// 累计借款人数
		res.setTotalBorrowerNumber(borrowerData.getTotalNumber());
		// 当期借款人数
		res.setCurrentBorrowerNumber(borrowerData.getCurrentNumber());
		// 人均累计借款金额
		res.setEachBorrowerTotalAmount(String.valueOf(borrowerData.getEachTotalAmount()));
		// 前十大借款人借款金额占比%
		res.setTopTenBorrowerAmtProportion(String.valueOf(borrowerData.getTopTenAmtProportion()));
		// 最大单一借款人借款余额占比%
		res.setTopBorrowerAmtProportion(String.valueOf(borrowerData.getTopAmtProportion()));
		//查询投资人性别比例
		List<InvestorTypeVO> investorTypeSexList = new ArrayList<>();
		InvestorTypeVO female = new InvestorTypeVO();
		female.setInvestorTypeName("女");
		female.setInvestorTypeRate(Double.valueOf(ageOrGenderProportion.getFemaleLender()));
		female.setInvestorTypeNumber(ageOrGenderProportion.getFemaleNumber());
		investorTypeSexList.add(female);
		InvestorTypeVO male = new InvestorTypeVO();
		male.setInvestorTypeName("男");
		male.setInvestorTypeRate(Double.valueOf(ageOrGenderProportion.getMaleLender()));
		male.setInvestorTypeNumber(ageOrGenderProportion.getMaleNumber());
		investorTypeSexList.add(male);
		for (InvestorTypeVO investorTypeVO : investorTypeSexList) {
			investorTypeVO.setInvestorTypeNumberString(tool.format("0",investorTypeVO.getInvestorTypeNumber()));
			investorTypeVO.setInvestorTypeRateString(tool.format("0.00",investorTypeVO.getInvestorTypeRate())+"%");
		}
		res.setInvestorTypeSex(BeanUtils.classToArrayList(investorTypeSexList));
		//查询投资人年龄段比例
		List<InvestorTypeVO> investorTypeAgeList = new ArrayList<>();
		InvestorTypeVO young = new InvestorTypeVO();
		young.setInvestorTypeNumber(ageOrGenderProportion.getYoungInvestorTypeNumber());
		young.setInvestorTypeRate(Double.valueOf(ageOrGenderProportion.getYoungLenderProportion()));
		young.setInvestorTypeName("18-28岁");
		investorTypeAgeList.add(young);
		InvestorTypeVO middle = new InvestorTypeVO();
		middle.setInvestorTypeNumber(ageOrGenderProportion.getMiddleInvestorTypeNumber());
		middle.setInvestorTypeRate(Double.valueOf(ageOrGenderProportion.getMiddleLenderProportion()));
		middle.setInvestorTypeName("29-39岁");
		investorTypeAgeList.add(middle);
		InvestorTypeVO fortyAge = new InvestorTypeVO();
		fortyAge.setInvestorTypeNumber(ageOrGenderProportion.getFortyAgeInvestorTypeNumber());
		fortyAge.setInvestorTypeRate(Double.valueOf(ageOrGenderProportion.getFortyAgeLenderProportion()));
		fortyAge.setInvestorTypeName("40-50岁");
		investorTypeAgeList.add(fortyAge);
		InvestorTypeVO old = new InvestorTypeVO();
		old.setInvestorTypeNumber(ageOrGenderProportion.getOldInvestorTypeNumber());
		old.setInvestorTypeRate(Double.valueOf(ageOrGenderProportion.getOldLenderProportion()));
		old.setInvestorTypeName("50岁以上");
		investorTypeAgeList.add(old);
		for (InvestorTypeVO investorTypeVO : investorTypeAgeList) {
			investorTypeVO.setInvestorTypeNumberString(tool.format("0",investorTypeVO.getInvestorTypeNumber()));
			investorTypeVO.setInvestorTypeRateString(tool.format("0.00",investorTypeVO.getInvestorTypeRate())+"%");
		}
		res.setInvestorTypeAge(BeanUtils.classToArrayList(investorTypeAgeList));
		res.setThirdTime(DateUtil.formatDateTime(userData.getCreateTime(), "yyyy-MM-dd HH:mm:ss"));


		// 4. 逾期及代偿数据统计
		// 出借人项目逾期率
		res.setProjectOverdueRate(String.valueOf(overdueInfo.getProjectOverdueRate()));
		// 出借人金额逾期率
		res.setAmtOverdueRate(String.valueOf(overdueInfo.getAmtOverdueRate()));
		// 借款人逾期金额
		res.setOverdueAmount(String.valueOf(overdueInfo.getOverdueAmount()));
		// 借款人逾期笔数
		res.setOverdueNumber(overdueInfo.getOverdueNumber());
		// 借款人逾期90天以上金额
		res.setOverdueNinnetyDaysAmount(String.valueOf(overdueInfo.getOverdueNinnetyDaysAmount()));
		// 借款人逾期90天以上笔数
		res.setOverdueNinnetyDaysNumber(overdueInfo.getOverdueNinnetyDaysNumber());
		// 累计代偿金额
		res.setTotalCompensatoryAmount(String.valueOf(overdueInfo.getTotalCompensatoryAmount()));
		// 累计代偿笔数
		res.setTotalCompensatoryNumber(overdueInfo.getTotalCompensatoryNumber());
		res.setForthTime(DateUtil.formatDateTime(overdueInfo.getCreateTime(), "yyyy-MM-dd HH:mm:ss"));


		//根据端口类型查询产品平均收益率
		//钱报178
		Double averageInvestRate178 = bsProductMapper.selectAverageInvestRate("178");
		//普通用户
		Double averageInvestRateNormal = bsProductMapper.selectAverageInvestRate("NORMAL");
		res.setAverageInvestRate178(tool.format("0.00", averageInvestRate178));
		res.setAverageInvestRateNormal(tool.format("0.00", averageInvestRateNormal));

	}

	@Override
	public Integer selectUserIdBySubAccountId(Integer subAccountId) {
		BsSubAccount bsSubAccount = bsSubAccountMapper.selectByPrimaryKey(subAccountId);
		if (bsSubAccount == null) {
			return null;
		}
		BsAccount bsAccount = bsAccountMapper.selectByPrimaryKey(bsSubAccount.getAccountId());
		if (bsAccount!=null) {
			return bsAccount.getUserId();
		}
		return null;
	}

	@Override
	public int countMyInvestBGWByUserId(Integer userId) {
		return bsSubAccountMapper.countMyInvestBGWByUserId(userId);
	}

	@Override
	public BsSubAccount findRegDSubAccountByAuthId(Integer subAccountId) {
		BsSubAccountPairExample example = new BsSubAccountPairExample();
		example.createCriteria().andAuthAccountIdEqualTo(subAccountId);
		List<BsSubAccountPair> subAccountPairs = bsSubAccountPairMapper.selectByExample(example);
		if(!CollectionUtils.isEmpty(subAccountPairs)){
			return bsSubAccountMapper.selectByPrimaryKey(subAccountPairs.get(0).getRegDAccountId());
		}
		return null;
	}

	@Override
	public void sendWechat4LastLoan(Integer subAccountId_auth) {
		/**
		 * 1、判断是否完成出借
		 * 2、是否是VIP用户
		 * 3、发送微信通知
		 */
		try {
			Map<String, Object> map = lnLoanRelationMapper.isFinishLoan(subAccountId_auth);
			Double diffAmount = MoneyUtil.subtract((Double) map.get("open_balance"), (Double) map.get("loaned_amount")).doubleValue();
			logger.info("=========判断是否是最后一笔出借：open_balance:"+(Double) map.get("open_balance")+",loaned_amount:"+(Double) map.get("loaned_amount")+"=================================================");
			if(diffAmount == 0d && (Integer)map.get("isVIP") == -1){
				Date time = (Date)map.get("open_time");
				String timeString = DateUtil.formatYYYYMMDD(time);
				sendWechatService.finishLoanSend((Integer)map.get("user_id"), String.valueOf((Double)map.get("open_balance")), 
						String.valueOf((Integer)map.get("product_id")), String.valueOf((Integer)map.get("sub_account_id")),timeString,String.valueOf((Double)map.get("loaned_amount")));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	@Override
	public Integer countMyInvestByStatus(Integer userId, String investStatus) {
		Integer count = bsSubAccountMapper.countMyInvestByStatus(userId,investStatus);
		return count;
	}
	
	
	@Override
	public List<BsSubAccountVO> bgwMyInvestByUserId(String type,int userId,int pageNum,int pageSize,String status) {
		
		int start = (pageNum <= 1) ? 0 : ((pageNum - 1) * pageSize);
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("type", type);
		paramMap.put("userId", userId);
		paramMap.put("start", start);
		paramMap.put("pageSize", pageSize);  
		paramMap.put("investStatus", status);  
		
		List<BsSubAccountVO> dataList = bsSubAccountMapper.bgwMyInvestByUserId(paramMap);
		return dataList.size() > 0? dataList : null;
	}

	@Override
	public Integer countMyInvestZanByStatus(Integer userId, String investStatus) {
		Integer count = bsSubAccountMapper.countMyInvestZanByStatus(userId,investStatus);
		return count;
	}

	@Override
	public List<BsSubAccount> findAuthAccountByUserId(Integer userId) {
		return bsSubAccountMapper.selectAuthAccountByUserId(userId);
	}

	@Override
	public BsSubAccount queryAuthAccountById(Integer authAccountId) {
		return bsSubAccountMapper.selectAuthAccountById(authAccountId);
	}

	@Override
	public Double queryInterestAmountBySubAccountId(int subAccountId) {
		BsInterestTicketInfoExample interestTicketInfoExample = new BsInterestTicketInfoExample();
		interestTicketInfoExample.createCriteria().andAuthAccountIdEqualTo(subAccountId);
		List<BsInterestTicketInfo> interestTicketInfoList = bsInterestTicketInfoMapper.selectByExample(interestTicketInfoExample);
		if(!CollectionUtils.isEmpty(interestTicketInfoList)){
			return interestTicketInfoList.get(0).getInterestAmount();
		}
		return null;
	}

	@Override
	public Double sumLoanAmount() {
		return bsSubAccountMapper.sumLoanAmount();
	}

	@Override
	public Double sumBorrowerAmount() {
		return bsSubAccountMapper.sumBorrowerAmount();
	}

	@Override
	public Integer countLoanTimes() {
		return bsSubAccountMapper.countLoanTimes();
	}

	@Override
	public Double sumLeftAmount() {
		return bsSubAccountMapper.sumLeftAmount();
	}

	@Override
	public Integer countLeftAmountTimes() {
		return bsSubAccountMapper.countLeftAmountTimes();
	}

	@Override
	public Integer countLoanUserTimes() {
		return bsSubAccountMapper.countLoanUserTimes(allSupperUser());
	}

	@Override
	public Integer countCurrentLoanUserTimes() {
		return bsSubAccountMapper.countCurrentLoanUserTimes(allSupperUser());
	}

	@Override
	public Double sumTenLargestLeftAmount() {
		return bsSubAccountMapper.sumTenLargestLeftAmount(allSupperUser());
	}

	@Override
	public Double sumLargestLeftAmount() {
		return bsSubAccountMapper.sumLargestLeftAmount(allSupperUser());
	}

	@Override
	public Integer countBorrowerUserTimes() {
		return bsSubAccountMapper.countBorrowerUserTimes();
	}

	@Override
	public Integer countCurrentBorrowerUserTimes() {
		return bsSubAccountMapper.countCurrentBorrowerUserTimes();
	}

	@Override
	public Double sumTenBorrowerLargestLeftAmount() {
		return bsSubAccountMapper.sumTenBorrowerLargestLeftAmount();
	}

	@Override
	public Double sumBorrowerLargestLeftAmount() {
		return bsSubAccountMapper.sumBorrowerLargestLeftAmount();
	}

	@Override
	public Double sumLateAmount() {
		return bsSubAccountMapper.sumLateAmount();
	}

	@Override
	public Integer countLateAmount() {
		return bsSubAccountMapper.countLateAmount();
	}

	@Override
	public Double sum90LateAmount() {
		return bsSubAccountMapper.sum90LateAmount();
	}

	@Override
	public Integer count90LateAmount() {
		return bsSubAccountMapper.count90LateAmount();
	}

	@Override
	public Double sumLateNotAmount() {
		return bsSubAccountMapper.sumLateNotAmount();
	}

	@Override
	public Integer countLateNotAmount() {
		return bsSubAccountMapper.countLateNotAmount();
	}

	private List<Integer> allSupperUser() {
		List<Integer> superUserList = new ArrayList<>();
		superUserList.addAll(supperUserZan());
		superUserList.addAll(superUserZSD());
		superUserList.addAll(supperUserYunSelf());
		superUserList.addAll(superUser7DAI());
		return superUserList;
	}

	/**
	 * 赞分期VIP用户
	 * @return
	 */
	private List<Integer> supperUserZan() {
		BsSysConfig supers = sysConfigService.findConfigByKey(Constants.SUPER_FINANCE_USER_ID);//VIP理财人账户
		return getUserIdList(supers);
	}

	/**
	 * 云贷自主放款VIP用户
	 * @return
	 */
	private List<Integer> supperUserYunSelf() {
		BsSysConfig supers = sysConfigService.findConfigByKey(VIPId4PartnerEnum.YUN_DAI_SELF.getVipIdKey());
		return getUserIdList(supers);

	}

	/**
	 * 赞时贷VIP用户
	 * @return
	 */
	private List<Integer> superUserZSD() {
		BsSysConfig supers = sysConfigService.findConfigByKey(VIPId4PartnerEnum.ZSD.getVipIdKey());
		return getUserIdList(supers);
	}

	/**
	 * 7贷VIP用户
	 * @return
	 */
	private List<Integer> superUser7DAI() {
		BsSysConfig supers = sysConfigService.findConfigByKey(VIPId4PartnerEnum.SEVEN_DAI_SELF.getVipIdKey());
		return getUserIdList(supers);
	}

	/**
	 * 获得用户ID列表
	 * @param supers
	 * @return
	 */
	public List<Integer> getUserIdList(BsSysConfig supers) {
		List<Integer> superUserIds = new ArrayList<>();
		if (supers != null) {
			String[] userStr = supers.getConfValue().split(",");
			for (String string : userStr) {
				superUserIds.add(Integer.parseInt(string));
			}
		}
		return superUserIds;
	}
}
