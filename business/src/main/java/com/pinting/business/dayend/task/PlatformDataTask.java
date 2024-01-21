package com.pinting.business.dayend.task;

import com.pinting.business.dao.BsSubAccountMapper;
import com.pinting.business.model.vo.*;
import com.pinting.business.service.site.ProductService;
import com.pinting.business.service.site.SubAccountService;
import com.pinting.business.service.site.UserService;
import com.pinting.business.util.CalculatorUtil;
import com.pinting.business.util.Constants;
import com.pinting.core.redis.JedisClientDaoSupport;
import com.pinting.core.util.DateUtil;
import com.pinting.core.util.MoneyUtil;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.velocity.tools.generic.NumberTool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.*;

/**
 * 平台数据定时
 * Created by cyb on 2018/3/12.
 */
@Service
public class PlatformDataTask {

    private Logger logger = LoggerFactory.getLogger(PlatformDataTask.class);
    private static JedisClientDaoSupport jsClientDaoSupport = JedisClientDaoSupport.getInstance(Constants.REDIS_SUBSYS_BUSINESS);

    private Date startTime = DateUtil.parse("2015-03-17", "yyyy-MM-dd");

    @Autowired
    private ProductService productService;
    @Autowired
    private SubAccountService subAccountService;
    @Autowired
    private UserService userService;
    @Autowired
    private BsSubAccountMapper bsSubAccountMapper;

    public void overview() {
        logger.info("平台数据：平台运营概况数据定时开始");
        NumberTool tool = new NumberTool();
        // 1. 平台运营概况
        PlatformOverviewVO platformOverview = new PlatformOverviewVO();
        // 时间
        Date now = new Date();
        // 合规运营天数
        Integer operateDays = DateUtil.getDiffeDay(now, startTime);
        // 累计成交额
        Double totalInvestAmount = productService.queryAccumulatedInvestment();
        // 累计出借额
        Double totalLoanAmount = subAccountService.sumLoanAmount();
        // 累计收益金额
        Double totalUserIncome = userService.countUserIncome();
        platformOverview.setCreateTime(now);
        platformOverview.setOperatingDays(operateDays);
        platformOverview.setTotalBuyAmount(tool.format("0.00", totalInvestAmount));
        platformOverview.setTotalLoanAmount(tool.format("0.00", totalLoanAmount));
        platformOverview.setTotalIncomeAmount(tool.format("0.00", totalUserIncome));
        jsClientDaoSupport.lpop(Constants.PLATFORM_DATA_REDIS_KEY_PLATFORMOVERVIEW);
        jsClientDaoSupport.rpush(Constants.PLATFORM_DATA_REDIS_KEY_PLATFORMOVERVIEW, JSONObject.fromObject(platformOverview).toString());
        logger.info("平台数据：平台运营概况数据定时结束");

        logger.info("平台数据：成交及出借数据统计定时开始");
        // 2. 成交及出借数据统计
        TransactionLendDataStatisticsVO transactionLendDataStatistics = new TransactionLendDataStatisticsVO();
        // 每月平台累计成交额 保存12条数据，按1月到12月的顺序存储对应累计成交额
        List<String> monthPlatformBuyAmount = new ArrayList<>();
        Calendar cal = Calendar.getInstance();
        int month = cal.get(Calendar.MONTH) + 1;
        String thisYearStr = cal.get(Calendar.YEAR) + "-01-01";
        Double historyTotalInvest =  bsSubAccountMapper.investMentOverDateMonth(thisYearStr);
        List<InvestAmountVO> dataList = bsSubAccountMapper.selectInvestAmountEachMonth();
        Double totalInvestOverMonth = historyTotalInvest;
        for (int i = 1; i <= month; i++) {
            for(InvestAmountVO vo : dataList) {
                Date date = vo.getDate();
                Calendar dateCal = Calendar.getInstance();
                dateCal.setTime(date);
                if(dateCal.get(Calendar.MONTH) + 1 == i) {
                    totalInvestOverMonth = MoneyUtil.add(totalInvestOverMonth, vo.getInvestAmount()).doubleValue();
                    monthPlatformBuyAmount.add(tool.format("0.00", totalInvestOverMonth));
                    break;
                }
            }
        }
        // 各个期限计划成交概况 按照期限正序存储对应成交额
        List<HashMap<String, Object>> eachTermProductBuyOverview = new ArrayList<>();
        List<InvestTotalGroupByProductVO> investTotalGroupByProductTermVOs = bsSubAccountMapper.investTotalGroupByProductTerm();
        for(InvestTotalGroupByProductVO vo : investTotalGroupByProductTermVOs) {
            HashMap<String, Object> planOverview = new HashMap<String, Object>();
            planOverview.put("amount", String.valueOf(vo.getInvestTotalGroupByProductAmount()));
            planOverview.put("name", vo.getProductName());
            eachTermProductBuyOverview.add(planOverview);
        }
        // 出借数据
        InterestOverview interestOverview = new InterestOverview();
        // 已赚取收益
        interestOverview.setTotalInterestAmount(tool.format("0.00", userService.countUserIncome()));
        // 待赚取收益
        interestOverview.setInvestInterestWill(tool.format("0.00", bsSubAccountMapper.investInterestWill()));
        LoanData loanData = new LoanData();
        // 自成立以来累计借贷金额（元） 同累计出借额
        loanData.setTotalLoanAmount(tool.format("0.00", totalLoanAmount));
        // 自成立以来累计借贷笔数;
        loanData.setTotalLoanNumber(subAccountService.countLoanTimes());
        // 当前待还借贷金额（元）;
        loanData.setCurrentWaitRepayAmount(tool.format("0.00", subAccountService.sumLeftAmount()));
        // 当前待还借贷笔数;
        loanData.setCurrentWaitRepayNumber(subAccountService.countLeftAmountTimes());
        // 关联关系借款余额（元）-- 0.00;
        loanData.setRelationBorrowerAmount("0.00");
        // 关联关系借款余额笔数 -- 0;
        loanData.setRelationBorrowerNumber(0);

        transactionLendDataStatistics.setCreateTime(now);
        transactionLendDataStatistics.setMonthPlatformBuyAmount(monthPlatformBuyAmount);
        transactionLendDataStatistics.setEachTermProductBuyOverview(eachTermProductBuyOverview);
        transactionLendDataStatistics.setInterestOverview(interestOverview);
        transactionLendDataStatistics.setLoanData(loanData);
        jsClientDaoSupport.lpop(Constants.PLATFORM_DATA_REDIS_KEY_TRANSACTIONLENDDATASTATISTICS);
        jsClientDaoSupport.rpush(Constants.PLATFORM_DATA_REDIS_KEY_TRANSACTIONLENDDATASTATISTICS, JSONObject.fromObject(transactionLendDataStatistics).toString());
        logger.info("平台数据：成交及出借数据统计定时结束");

    }

    public void userData() {
        logger.info("平台数据：用户数据统计定时开始");
        NumberTool tool = new NumberTool();
        // 1. 用户数据统计
        UserDataVO userData = new UserDataVO();
        // 时间
        Date now = new Date();
        // 1. 出借人用户数据
        LenderOrBorrowerData lenderData = new LenderOrBorrowerData();
        // 累计出借人数
        Integer loanNumber = subAccountService.countLoanUserTimes();
        // 当期出借人数
        Integer currentLoanUserTimes = subAccountService.countCurrentLoanUserTimes();
        // 人均累计出借金额
        Double borrowerAmount = subAccountService.sumBorrowerAmount();
        Integer loanUserTimes = subAccountService.countLoanUserTimes();
        Double a = CalculatorUtil.calculate("a/a", borrowerAmount, Double.valueOf(loanUserTimes)).doubleValue();
        // 前十大出借人出借/借款人借款金额占比%
        Double tenLargestLeftAmount = subAccountService.sumTenLargestLeftAmount();
        Double leftAmount = subAccountService.sumLeftAmount();
        Double b = MoneyUtil.multiply(MoneyUtil.divide(tenLargestLeftAmount, leftAmount, 4).doubleValue(), 100d).doubleValue();
        // 最大单一出借人出借/借款人借款余额占比%
        Double largestLeftAmount = subAccountService.sumLargestLeftAmount();
        Double c = MoneyUtil.multiply(MoneyUtil.divide(largestLeftAmount, leftAmount, 4).doubleValue(), 100d).doubleValue();
        lenderData.setTotalNumber(loanNumber);
        lenderData.setCurrentNumber(currentLoanUserTimes);
        lenderData.setEachTotalAmount(tool.format("0.00", a));
        lenderData.setTopTenAmtProportion(tool.format("0.00", b));
        lenderData.setTopAmtProportion(tool.format("0.00", c));
        // 借款人用户数据
        LenderOrBorrowerData borrowerData = new LenderOrBorrowerData();
        // 累计借款人数（人）
        Integer borrowerUserTimes = subAccountService.countBorrowerUserTimes();
        // 当期借款人数（人）
        Integer currentBorrowerUserTimes = subAccountService.countCurrentBorrowerUserTimes();
        // 人均累计借款金额（元）
        Double eachTotalAmount = CalculatorUtil.calculate("a/a", borrowerAmount, Double.valueOf(borrowerUserTimes));
        // 前十大借款人待还金额占比
        Double tenBorrowerLargestLeftAmount = subAccountService.sumTenBorrowerLargestLeftAmount();
        Double topTen = MoneyUtil.multiply(MoneyUtil.divide(tenBorrowerLargestLeftAmount, leftAmount, 4).doubleValue(), 100d).doubleValue();
        // 最大单一借款人待还金额占比
        Double borrowerLargestLeftAmount = subAccountService.sumBorrowerLargestLeftAmount();
        Double top = MoneyUtil.multiply(MoneyUtil.divide(borrowerLargestLeftAmount, leftAmount, 4).doubleValue(), 100d).doubleValue();
        borrowerData.setTotalNumber(borrowerUserTimes);
        borrowerData.setCurrentNumber(currentBorrowerUserTimes);
        borrowerData.setEachTotalAmount(tool.format("0.00", eachTotalAmount));
        borrowerData.setTopTenAmtProportion(tool.format("0.00", topTen));
        borrowerData.setTopAmtProportion(tool.format("0.00", top));
        // 年龄||性别分布
        AgeOrGenderProportion ageOrGenderProportion = new AgeOrGenderProportion();
        List<InvestorTypeVO> investorTypeAgeList = bsSubAccountMapper.investorTypeAge();
        // 18-28岁出借人年龄分布占比%
        // 29-38岁出借人年龄分布占比%
        // 40-50岁出借人年龄分布占比%
        // 50岁出借人年龄分布占比%
        for (InvestorTypeVO investorTypeVO : investorTypeAgeList) {
            if("18-28岁".equals(investorTypeVO.getInvestorTypeName())) {
                ageOrGenderProportion.setYoungLenderProportion(tool.format("0.00", investorTypeVO.getInvestorTypeRate()));
                ageOrGenderProportion.setYoungInvestorTypeNumber(investorTypeVO.getInvestorTypeNumber());
            } else if("29-39岁".equals(investorTypeVO.getInvestorTypeName())) {
                ageOrGenderProportion.setMiddleLenderProportion(tool.format("0.00", investorTypeVO.getInvestorTypeRate()));
                ageOrGenderProportion.setMiddleInvestorTypeNumber(investorTypeVO.getInvestorTypeNumber());
            } else if("40-50岁".equals(investorTypeVO.getInvestorTypeName())) {
                ageOrGenderProportion.setFortyAgeInvestorTypeNumber(investorTypeVO.getInvestorTypeNumber());
                ageOrGenderProportion.setFortyAgeLenderProportion(tool.format("0.00", investorTypeVO.getInvestorTypeRate()));
            } else if("50岁以上".equals(investorTypeVO.getInvestorTypeName())) {
                ageOrGenderProportion.setOldInvestorTypeNumber(investorTypeVO.getInvestorTypeNumber());
                ageOrGenderProportion.setOldLenderProportion(tool.format("0.00", investorTypeVO.getInvestorTypeRate()));
            }
        }
        // 男性出借人占比%
        // 女性出借人占比%
        List<InvestorTypeVO> investorTypeSexList = bsSubAccountMapper.investorTypeSex();
        for (InvestorTypeVO investorTypeVO : investorTypeSexList) {
            if("男".equals(investorTypeVO.getInvestorTypeName())) {
                ageOrGenderProportion.setMaleLender(tool.format("0.00", investorTypeVO.getInvestorTypeRate()));
                ageOrGenderProportion.setMaleNumber(investorTypeVO.getInvestorTypeNumber());
            } else {
                ageOrGenderProportion.setFemaleLender(tool.format("0.00", investorTypeVO.getInvestorTypeRate()));
                ageOrGenderProportion.setFemaleNumber(investorTypeVO.getInvestorTypeNumber());
            }
        }
        userData.setLenderData(lenderData);
        userData.setBorrowerData(borrowerData);
        userData.setAgeOrGenderProportion(ageOrGenderProportion);
        userData.setCreateTime(now);
        jsClientDaoSupport.lpop(Constants.PLATFORM_DATA_REDIS_KEY_USERDATA);
        jsClientDaoSupport.rpush(Constants.PLATFORM_DATA_REDIS_KEY_USERDATA, JSONObject.fromObject(userData).toString());
        logger.info("平台数据：用户数据统计定时结束");

        logger.info("平台数据：逾期及代偿数据统计定时开始");
        // 2. 逾期及代偿数据统计
        OverdueInfoVO overdueInfo = new OverdueInfoVO();
        // 出借人项目逾期率 0
        // 出借人金额逾期率 0
        // 借款人逾期金额
        Double lateAmount = subAccountService.sumLateAmount();
        // 借款人逾期笔数
        Integer lateNumber = subAccountService.countLateAmount();
        // 借款人逾期90天以上金额
        Double sum90LateAmount = subAccountService.sum90LateAmount();
        // 借款人逾期90天以上笔数
        Integer count90LateAmount = subAccountService.count90LateAmount();
        // 累计代偿金额
        Double sumLateNotAmount = subAccountService.sumLateNotAmount();
        // 累计代偿笔数
        Integer countLateNotAmount = subAccountService.countLateNotAmount();
        // 时间
        overdueInfo.setCreateTime(now);
        overdueInfo.setProjectOverdueRate("0.00");
        overdueInfo.setAmtOverdueRate("0.00");
        overdueInfo.setOverdueAmount(tool.format("0.00", lateAmount));
        overdueInfo.setOverdueNumber(lateNumber);
        overdueInfo.setOverdueNinnetyDaysAmount(tool.format("0.00", sum90LateAmount));
        overdueInfo.setOverdueNinnetyDaysNumber(count90LateAmount);
        overdueInfo.setTotalCompensatoryAmount(tool.format("0.00", sumLateNotAmount));
        overdueInfo.setTotalCompensatoryNumber(countLateNotAmount);
        jsClientDaoSupport.lpop(Constants.PLATFORM_DATA_REDIS_KEY_OVERDUEINFO);
        jsClientDaoSupport.rpush(Constants.PLATFORM_DATA_REDIS_KEY_OVERDUEINFO, JSONObject.fromObject(overdueInfo).toString());
        logger.info("平台数据：逾期及代偿数据统计定时结束");
    }
    
    public void manageExport(){
    	logger.info("================平台数据：管理台导出平台数据生成定时开始========================");

    	Platform4ManageVO platform4Manage = new Platform4ManageVO();
    	NumberTool tool = new NumberTool();
    	
        // 时间
        Date now = new Date();
        String year = DateUtil.formatDateTime(now, "yyyy");
        String month = DateUtil.formatDateTime(now, "MM");
        platform4Manage.setSaveDate(DateUtil.formatDateTime(now, "yyyy-MM"));
        //=====模块1====
        // 平台合规运营（天）
        Integer operateDays = DateUtil.getDiffeDay(now, startTime);
        platform4Manage.setOperatingDays(operateDays);
        // 累计成交额（元）
        Double totalInvestAmount = productService.queryAccumulatedInvestment();
        platform4Manage.setTotalBuyAmount(tool.format("0.00", totalInvestAmount));
        // 累计出借额（元）自成立以来累计借贷金额（元） 
        Double totalLoanAmount = subAccountService.sumLoanAmount();
        platform4Manage.setTotalLoanAmount(tool.format("0.00", totalLoanAmount));
        // 用户累计收益（元）
        Double totalUserIncome = userService.countUserIncome();
        platform4Manage.setTotalIncomeAmount(tool.format("0.00", totalUserIncome));
        
        //=====模块2====
        // 自成立以来累计借贷笔数（笔）
        platform4Manage.setTotalLoanNumber(subAccountService.countLoanTimes());
        // 当前待还借贷金额（元）
        platform4Manage.setCurrentWaitRepayAmount(tool.format("0.00", subAccountService.sumLeftAmount()));
        // 当前待还借贷笔数（笔）
        platform4Manage.setCurrentWaitRepayNumber(subAccountService.countLeftAmountTimes());
        // 关联关系借款余额（元）
        platform4Manage.setRelationBorrowerAmount("0.00");
        // 关联关系借款余额笔数（笔）
        platform4Manage.setRelationBorrowerNumber(0);
        
        //=====模块3====
        // 累计出借人数
        Integer loanNumber = subAccountService.countLoanUserTimes();
        // 当期出借人数
        Integer currentLoanUserTimes = subAccountService.countCurrentLoanUserTimes();
        // 人均累计出借金额
        Double borrowerAmount = subAccountService.sumBorrowerAmount();
        Integer loanUserTimes = subAccountService.countLoanUserTimes();
        Double a = CalculatorUtil.calculate("a/a", borrowerAmount, Double.valueOf(loanUserTimes)).doubleValue();
        // 前十大出借人出借/借款人借款金额占比%
        Double tenLargestLeftAmount = subAccountService.sumTenLargestLeftAmount();
        Double leftAmount = subAccountService.sumLeftAmount();
        Double b = MoneyUtil.multiply(MoneyUtil.divide(tenLargestLeftAmount, leftAmount, 4).doubleValue(), 100d).doubleValue();
        // 最大单一出借人出借/借款人借款余额占比%
        Double largestLeftAmount = subAccountService.sumLargestLeftAmount();
        Double c = MoneyUtil.multiply(MoneyUtil.divide(largestLeftAmount, leftAmount, 4).doubleValue(), 100d).doubleValue();
        platform4Manage.setTotalLenderNumber(loanNumber);
        platform4Manage.setCurrentLenderNumber(currentLoanUserTimes);
        platform4Manage.setEachLendTotalAmount(tool.format("0.00", a));
        platform4Manage.setTopTenLendAmtProportion(tool.format("0.00", b));
        platform4Manage.setTopLendAmtProportion(tool.format("0.00", c));
        
        //=====模块4====
        // 累计借款人数（人）
        Integer borrowerUserTimes = subAccountService.countBorrowerUserTimes();
        // 当期借款人数（人）
        Integer currentBorrowerUserTimes = subAccountService.countCurrentBorrowerUserTimes();
        // 人均累计借款金额（元）
        Double eachTotalAmount = CalculatorUtil.calculate("a/a", borrowerAmount, Double.valueOf(borrowerUserTimes));
        // 前十大借款人待还金额占比
        Double tenBorrowerLargestLeftAmount = subAccountService.sumTenBorrowerLargestLeftAmount();
        Double topTen = MoneyUtil.multiply(MoneyUtil.divide(tenBorrowerLargestLeftAmount, leftAmount, 4).doubleValue(), 100d).doubleValue();
        // 最大单一借款人待还金额占比
        Double borrowerLargestLeftAmount = subAccountService.sumBorrowerLargestLeftAmount();
        Double top = MoneyUtil.multiply(MoneyUtil.divide(borrowerLargestLeftAmount, leftAmount, 4).doubleValue(), 100d).doubleValue();
        platform4Manage.setTotalBorrowerNumber(borrowerUserTimes);
        platform4Manage.setCurrentBorrowerNumber(currentBorrowerUserTimes);
        platform4Manage.setEachBorrowTotalAmount(tool.format("0.00", eachTotalAmount));
        platform4Manage.setTopTenBorrowAmtProportion(tool.format("0.00", topTen));
        platform4Manage.setTopBorrowAmtProportion(tool.format("0.00", top));
        
        //=====模块5====
        // 出借人项目逾期率（%）
        platform4Manage.setProjectOverdueRate("0.00");
        // 出借人金额逾期率（%）
        platform4Manage.setAmtOverdueRate("0.00");
        
        // 借款人逾期金额（元）
        Double lateAmount = subAccountService.sumLateAmount();
        // 借款人逾期笔数（笔）
        Integer lateNumber = subAccountService.countLateAmount();
        // 借款人逾期90天以上金额（元）
        Double sum90LateAmount = subAccountService.sum90LateAmount();
        // 借款人逾期90天以上笔数（笔）
        Integer count90LateAmount = subAccountService.count90LateAmount();
        // 累计代偿金额（元）
        Double sumLateNotAmount = subAccountService.sumLateNotAmount();
        // 累计代偿笔数（笔）
        Integer countLateNotAmount = subAccountService.countLateNotAmount();
        // 时间
        platform4Manage.setProjectOverdueRate("0.00");
        platform4Manage.setAmtOverdueRate("0.00");
        platform4Manage.setOverdueAmount(tool.format("0.00", lateAmount));
        platform4Manage.setOverdueNumber(lateNumber);
        platform4Manage.setOverdueNinnetyDaysAmount(tool.format("0.00", sum90LateAmount));
        platform4Manage.setOverdueNinnetyDaysNumber(count90LateAmount);
        platform4Manage.setTotalCompensatoryAmount(tool.format("0.00", sumLateNotAmount));
        platform4Manage.setTotalCompensatoryNumber(countLateNotAmount);
        
        //=====模块6====
        //本月成交额（元）
        Double monthBuyAmount = bsSubAccountMapper.monthBuyAmount(year, month); 
        //本月成交人数（人）
        Integer monthBuyUserNumber = bsSubAccountMapper.monthBuyUserNumber(year, month);
        //本月成交笔数（笔）
        Integer monthBuyNumber = bsSubAccountMapper.monthBuyNumber(year, month);
        //本月用户收益（元）
        Double monthIncomeAmount = bsSubAccountMapper.monthIncomeAmount(year, month);
        //本月借贷金额（元）
        Double monthLoanAmount = bsSubAccountMapper.monthLoanAmount(year, month);
        //本月借贷笔数（笔）
        Integer monthLoanNumber = bsSubAccountMapper.monthLoanNumber(year, month);
        platform4Manage.setMonthBuyAmount(tool.format("0.00", monthBuyAmount));
        platform4Manage.setMonthBuyUserNumber(monthBuyUserNumber);
        platform4Manage.setMonthBuyNumber(monthBuyNumber);
        platform4Manage.setMonthIncomeAmount(tool.format("0.00",monthIncomeAmount));
        platform4Manage.setMonthLoanAmount(tool.format("0.00",monthLoanAmount));
        platform4Manage.setMonthLoanNumber(monthLoanNumber);
        
        //=====模块7====
        List<Platform4ManageProductVO> buyGroupList = bsSubAccountMapper.buyGroupList(year, month);
        platform4Manage.setBuyGroupList(buyGroupList);
        //=====模块8  年龄维度分布====
        List<InvestorTypeVO> investorTypeAgeList = bsSubAccountMapper.investorTypeAge();
        for (InvestorTypeVO investorTypeVO : investorTypeAgeList) {
            if("18-28岁".equals(investorTypeVO.getInvestorTypeName())) {
            	// 18-28岁出借人年龄分布占比%
            	platform4Manage.setAge18_28Proportion(tool.format("0.00", investorTypeVO.getInvestorTypeRate()));
            } else if("29-39岁".equals(investorTypeVO.getInvestorTypeName())) {
            	// 29-38岁出借人年龄分布占比%
            	platform4Manage.setAge29_39Proportion(tool.format("0.00", investorTypeVO.getInvestorTypeRate()));
            } else if("40-50岁".equals(investorTypeVO.getInvestorTypeName())) {
            	// 40-50岁出借人年龄分布占比%
            	platform4Manage.setAge40_50Proportion(tool.format("0.00", investorTypeVO.getInvestorTypeRate()));
            } else if("50岁以上".equals(investorTypeVO.getInvestorTypeName())) {
            	// 50岁出借人年龄分布占比%
            	platform4Manage.setAge50MoreProportion(tool.format("0.00", investorTypeVO.getInvestorTypeRate()));
            }
        }
        
        //=====模块9====订单端口，无端口加至pc
        //网页版端口占比（%）订单端口，无端口加至pc
        Double pcProportion = bsSubAccountMapper.pcProportion();
        platform4Manage.setPcProportion(tool.format("0.00", pcProportion));
        //H5端口占比（%）
        Double h5Proportion = bsSubAccountMapper.h5Proportion();
        platform4Manage.setH5Proportion(tool.format("0.00", h5Proportion));
        //app端口占比（%）
        Double appProportion = MoneyUtil.subtract(100, MoneyUtil.add(pcProportion, h5Proportion).doubleValue()).doubleValue();
        platform4Manage.setAppProportion(tool.format("0.00",appProportion));

        //=====模块10====
        List<InvestorTypeVO> investorTypeSexList = bsSubAccountMapper.investorTypeSex();
        for (InvestorTypeVO investorTypeVO : investorTypeSexList) {
            if("男".equals(investorTypeVO.getInvestorTypeName())) {
            	// 男性出借人占比%
            	platform4Manage.setSexMaleProportion(tool.format("0.00", investorTypeVO.getInvestorTypeRate()));
            } else {
            	// 女性出借人占比%
            	platform4Manage.setSexFemaleProportion(tool.format("0.00", investorTypeVO.getInvestorTypeRate()));
            }
        }
        
        //=====模块11====
        //单日最高成交额（元）
        platform4Manage.setMostDayBuyAmount(tool.format("0.00",bsSubAccountMapper.mostDayBuyAmount()));
        //单笔最高成交额（元）
        platform4Manage.setMostOneBuyAmount(tool.format("0.00",bsSubAccountMapper.mostOneBuyAmount()));
        //最快满标时间（秒）
        platform4Manage.setFastestSecond(tool.format("0.00",bsSubAccountMapper.fastestSecond()));
        //成交次数最多（次）
        platform4Manage.setMostBuyTimes(tool.format("0.00",bsSubAccountMapper.mostBuyTimes()));
        
        //=====模块12====
        List<String> richerList = bsSubAccountMapper.richerList();
        platform4Manage.setRicherList(richerList);
        
        Map<String, String> map = new HashMap<String, String>();
        map.put(platform4Manage.getSaveDate(), JSONObject.fromObject(platform4Manage).toString());
        jsClientDaoSupport.addObjOfHashMap(map, "Platform4Manage", 3600*24*365);
        
    	logger.info("================平台数据：管理台导出平台数据生成定时结束========================");
    }

    
    public static void main(String[] args) throws ParseException {
    	Map<String, Platform4ManageVO> map = new HashMap<String, Platform4ManageVO>();
    	Platform4ManageVO platform4ManageVO = new Platform4ManageVO();
    	platform4ManageVO.setOperatingDays(1234);
    	List<Platform4ManageProductVO> buyGroupList = new ArrayList<Platform4ManageProductVO>();
    	Platform4ManageProductVO pro = new Platform4ManageProductVO();
    	pro.setProductTerm(30);
    	pro.setAmount("");
    	buyGroupList.add(pro);
    	platform4ManageVO.setBuyGroupList(buyGroupList);
    	map.put("2018-04", platform4ManageVO);
        jsClientDaoSupport.rpush("Platform4Manage", JSONObject.fromObject(map).toString());
        
        Map<String, Platform4ManageVO> map2 = (Map<String, Platform4ManageVO>) jsClientDaoSupport.lrange("Platform4Manage");
        
        Platform4ManageVO platform4ManageVO2 =map2.get("2018-04");
        List<Platform4ManageProductVO> buyGroupList2 = platform4ManageVO2.getBuyGroupList();
        for (Platform4ManageProductVO platform4ManageProductVO : buyGroupList2) {
			System.out.println(platform4ManageProductVO.getProductTerm());
			System.out.println(platform4ManageProductVO.getAmount());
		}
    }
}
