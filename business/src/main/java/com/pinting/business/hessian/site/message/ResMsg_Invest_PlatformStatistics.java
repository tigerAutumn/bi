package com.pinting.business.hessian.site.message;

import java.util.ArrayList;
import java.util.HashMap;

import com.pinting.core.hessian.msg.ResMsg;

public class ResMsg_Invest_PlatformStatistics extends ResMsg {

	private static final long serialVersionUID = 2311100861268281033L;

	/* 平台运营概况 */
	// 合规运营天数	——新增
	private String operatingDays;
	// 累计成交额
	private String totalInvestAmount;
	// 累计出借额		——新增
	private String totalLoanAmount;
	// 累计收益金额（元）
	private String totalInterestAmount;
	// 截止时间(yyyy-MM-dd HH:mm:ss)		——新增
	private String firstTime;


	/* 成交及出借数据统计 */
	// 查询当年每月截止当月为止历史累计投资总金额 每月平台累计成交额
	private ArrayList<HashMap<String,Object>> investMentOverDateMonth;
	// 各个期限计划成交概况
	private ArrayList<HashMap<String,Object>> investTotalGroupByProductTerm;
	// 待赚取收益
	private String investInterestWill;
	// 已赚取收益 等同 累计收益金额
	// 自成立以来累计借贷金额（元） 同累计出借额	——新增
	// 自成立以来累计借贷笔数;					——新增
	private Integer totalLoanNumber;
	// 当前待还借贷金额（元）;					——新增
	private String currentWaitRepayAmount;
	// 当前待还借贷笔数t;						——新增
	private Integer currentWaitRepayNumber;
	// 关联关系借款余额（元）;					——新增
	private String relationBorrowerAmount;
	// 关联关系借款余额笔数;					——新增
	private Integer relationBorrowerNumber;
	// 截止时间(yyyy-MM-dd HH:mm:ss)		——新增
	private String secondTime;


	/* 用户数据统计 */
	// 累计出借人数						——新增
	private Integer totalNumber;
	// 当期出借人数						——新增
	private Integer currentNumber;
	// 人均累计出借金额					——新增
	private String eachTotalAmount;
	// 前十大出借人出借金额占比%		——新增
	private String topTenAmtProportion;
	// 最大单一出借人出借余额占比%		——新增
	private String topAmtProportion;
	// 累计借款人数						——新增
	private Integer totalBorrowerNumber;
	// 当期借款人数						——新增
	private Integer currentBorrowerNumber;
	// 人均累计借款金额					——新增
	private String eachBorrowerTotalAmount;
	// 前十大借款人借款金额占比%			——新增
	private String topTenBorrowerAmtProportion;
	// 最大单一借款人借款余额占比%			——新增
	private String topBorrowerAmtProportion;
	// 投资人性别比例
	private ArrayList<HashMap<String,Object>> investorTypeSex;
	// 投资人年龄段比例
	private ArrayList<HashMap<String,Object>> investorTypeAge;
	// 截止时间(yyyy-MM-dd HH:mm:ss)		——新增
	private String thirdTime;

	/* 逾期及代偿数据统计 */
	// 出借人项目逾期率			——新增
	private String projectOverdueRate;
	// 出借人金额逾期率			——新增
	private String amtOverdueRate;
	// 借款人逾期金额				——新增
	private String overdueAmount;
	// 借款人逾期笔数				——新增
	private Integer overdueNumber;
	// 借款人逾期90天以上金额		——新增
	private String overdueNinnetyDaysAmount;
	// 借款人逾期90天以上笔数		——新增
	private Integer overdueNinnetyDaysNumber;
	// 累计代偿金额				——新增
	private String totalCompensatoryAmount;
	// 累计代偿笔数				——新增
	private Integer totalCompensatoryNumber;
	// 截止时间(yyyy-MM-dd HH:mm:ss)		——新增
	private String forthTime;



	// 按照产品类型查询用户投资情况（老的）
	private ArrayList<HashMap<String,Object>> investTotalGroupByProduct;
	// 每月平台累计成交额（老的）
	private ArrayList<HashMap<String,Object>> totalInvestGroupByMonth;
	// 累计平台用户数
	private String totalRegUser;
	//钱报178产品平均年化收益
	private String averageInvestRate178;
	//普通用户平均年化收益
	private String averageInvestRateNormal;


	public String getOperatingDays() {
		return operatingDays;
	}

	public void setOperatingDays(String operatingDays) {
		this.operatingDays = operatingDays;
	}

	public String getTotalInvestAmount() {
		return totalInvestAmount;
	}

	public void setTotalInvestAmount(String totalInvestAmount) {
		this.totalInvestAmount = totalInvestAmount;
	}

	public String getTotalLoanAmount() {
		return totalLoanAmount;
	}

	public void setTotalLoanAmount(String totalLoanAmount) {
		this.totalLoanAmount = totalLoanAmount;
	}

	public String getTotalInterestAmount() {
		return totalInterestAmount;
	}

	public void setTotalInterestAmount(String totalInterestAmount) {
		this.totalInterestAmount = totalInterestAmount;
	}

	public String getFirstTime() {
		return firstTime;
	}

	public void setFirstTime(String firstTime) {
		this.firstTime = firstTime;
	}

	public ArrayList<HashMap<String, Object>> getTotalInvestGroupByMonth() {
		return totalInvestGroupByMonth;
	}

	public void setTotalInvestGroupByMonth(ArrayList<HashMap<String, Object>> totalInvestGroupByMonth) {
		this.totalInvestGroupByMonth = totalInvestGroupByMonth;
	}

	public ArrayList<HashMap<String, Object>> getInvestTotalGroupByProductTerm() {
		return investTotalGroupByProductTerm;
	}

	public void setInvestTotalGroupByProductTerm(ArrayList<HashMap<String, Object>> investTotalGroupByProductTerm) {
		this.investTotalGroupByProductTerm = investTotalGroupByProductTerm;
	}

	public String getInvestInterestWill() {
		return investInterestWill;
	}

	public void setInvestInterestWill(String investInterestWill) {
		this.investInterestWill = investInterestWill;
	}

	public Integer getTotalLoanNumber() {
		return totalLoanNumber;
	}

	public void setTotalLoanNumber(Integer totalLoanNumber) {
		this.totalLoanNumber = totalLoanNumber;
	}

	public String getCurrentWaitRepayAmount() {
		return currentWaitRepayAmount;
	}

	public void setCurrentWaitRepayAmount(String currentWaitRepayAmount) {
		this.currentWaitRepayAmount = currentWaitRepayAmount;
	}

	public Integer getCurrentWaitRepayNumber() {
		return currentWaitRepayNumber;
	}

	public void setCurrentWaitRepayNumber(Integer currentWaitRepayNumber) {
		this.currentWaitRepayNumber = currentWaitRepayNumber;
	}

	public String getRelationBorrowerAmount() {
		return relationBorrowerAmount;
	}

	public void setRelationBorrowerAmount(String relationBorrowerAmount) {
		this.relationBorrowerAmount = relationBorrowerAmount;
	}

	public Integer getRelationBorrowerNumber() {
		return relationBorrowerNumber;
	}

	public void setRelationBorrowerNumber(Integer relationBorrowerNumber) {
		this.relationBorrowerNumber = relationBorrowerNumber;
	}

	public String getSecondTime() {
		return secondTime;
	}

	public void setSecondTime(String secondTime) {
		this.secondTime = secondTime;
	}

	public Integer getTotalNumber() {
		return totalNumber;
	}

	public void setTotalNumber(Integer totalNumber) {
		this.totalNumber = totalNumber;
	}

	public Integer getCurrentNumber() {
		return currentNumber;
	}

	public void setCurrentNumber(Integer currentNumber) {
		this.currentNumber = currentNumber;
	}

	public String getEachTotalAmount() {
		return eachTotalAmount;
	}

	public void setEachTotalAmount(String eachTotalAmount) {
		this.eachTotalAmount = eachTotalAmount;
	}

	public String getTopTenAmtProportion() {
		return topTenAmtProportion;
	}

	public void setTopTenAmtProportion(String topTenAmtProportion) {
		this.topTenAmtProportion = topTenAmtProportion;
	}

	public String getTopAmtProportion() {
		return topAmtProportion;
	}

	public void setTopAmtProportion(String topAmtProportion) {
		this.topAmtProportion = topAmtProportion;
	}

	public Integer getTotalBorrowerNumber() {
		return totalBorrowerNumber;
	}

	public void setTotalBorrowerNumber(Integer totalBorrowerNumber) {
		this.totalBorrowerNumber = totalBorrowerNumber;
	}

	public Integer getCurrentBorrowerNumber() {
		return currentBorrowerNumber;
	}

	public void setCurrentBorrowerNumber(Integer currentBorrowerNumber) {
		this.currentBorrowerNumber = currentBorrowerNumber;
	}

	public String getEachBorrowerTotalAmount() {
		return eachBorrowerTotalAmount;
	}

	public void setEachBorrowerTotalAmount(String eachBorrowerTotalAmount) {
		this.eachBorrowerTotalAmount = eachBorrowerTotalAmount;
	}

	public String getTopTenBorrowerAmtProportion() {
		return topTenBorrowerAmtProportion;
	}

	public void setTopTenBorrowerAmtProportion(String topTenBorrowerAmtProportion) {
		this.topTenBorrowerAmtProportion = topTenBorrowerAmtProportion;
	}

	public String getTopBorrowerAmtProportion() {
		return topBorrowerAmtProportion;
	}

	public void setTopBorrowerAmtProportion(String topBorrowerAmtProportion) {
		this.topBorrowerAmtProportion = topBorrowerAmtProportion;
	}

	public ArrayList<HashMap<String, Object>> getInvestorTypeSex() {
		return investorTypeSex;
	}

	public void setInvestorTypeSex(ArrayList<HashMap<String, Object>> investorTypeSex) {
		this.investorTypeSex = investorTypeSex;
	}

	public ArrayList<HashMap<String, Object>> getInvestorTypeAge() {
		return investorTypeAge;
	}

	public void setInvestorTypeAge(ArrayList<HashMap<String, Object>> investorTypeAge) {
		this.investorTypeAge = investorTypeAge;
	}

	public String getThirdTime() {
		return thirdTime;
	}

	public void setThirdTime(String thirdTime) {
		this.thirdTime = thirdTime;
	}

	public String getProjectOverdueRate() {
		return projectOverdueRate;
	}

	public void setProjectOverdueRate(String projectOverdueRate) {
		this.projectOverdueRate = projectOverdueRate;
	}

	public String getAmtOverdueRate() {
		return amtOverdueRate;
	}

	public void setAmtOverdueRate(String amtOverdueRate) {
		this.amtOverdueRate = amtOverdueRate;
	}

	public String getOverdueAmount() {
		return overdueAmount;
	}

	public void setOverdueAmount(String overdueAmount) {
		this.overdueAmount = overdueAmount;
	}

	public Integer getOverdueNumber() {
		return overdueNumber;
	}

	public void setOverdueNumber(Integer overdueNumber) {
		this.overdueNumber = overdueNumber;
	}

	public String getOverdueNinnetyDaysAmount() {
		return overdueNinnetyDaysAmount;
	}

	public void setOverdueNinnetyDaysAmount(String overdueNinnetyDaysAmount) {
		this.overdueNinnetyDaysAmount = overdueNinnetyDaysAmount;
	}

	public Integer getOverdueNinnetyDaysNumber() {
		return overdueNinnetyDaysNumber;
	}

	public void setOverdueNinnetyDaysNumber(Integer overdueNinnetyDaysNumber) {
		this.overdueNinnetyDaysNumber = overdueNinnetyDaysNumber;
	}

	public String getTotalCompensatoryAmount() {
		return totalCompensatoryAmount;
	}

	public void setTotalCompensatoryAmount(String totalCompensatoryAmount) {
		this.totalCompensatoryAmount = totalCompensatoryAmount;
	}

	public Integer getTotalCompensatoryNumber() {
		return totalCompensatoryNumber;
	}

	public void setTotalCompensatoryNumber(Integer totalCompensatoryNumber) {
		this.totalCompensatoryNumber = totalCompensatoryNumber;
	}

	public String getForthTime() {
		return forthTime;
	}

	public void setForthTime(String forthTime) {
		this.forthTime = forthTime;
	}

	public ArrayList<HashMap<String, Object>> getInvestTotalGroupByProduct() {
		return investTotalGroupByProduct;
	}

	public void setInvestTotalGroupByProduct(ArrayList<HashMap<String, Object>> investTotalGroupByProduct) {
		this.investTotalGroupByProduct = investTotalGroupByProduct;
	}

	public String getTotalRegUser() {
		return totalRegUser;
	}

	public void setTotalRegUser(String totalRegUser) {
		this.totalRegUser = totalRegUser;
	}

	public ArrayList<HashMap<String, Object>> getInvestMentOverDateMonth() {
		return investMentOverDateMonth;
	}

	public void setInvestMentOverDateMonth(ArrayList<HashMap<String, Object>> investMentOverDateMonth) {
		this.investMentOverDateMonth = investMentOverDateMonth;
	}

	public String getAverageInvestRate178() {
		return averageInvestRate178;
	}

	public void setAverageInvestRate178(String averageInvestRate178) {
		this.averageInvestRate178 = averageInvestRate178;
	}

	public String getAverageInvestRateNormal() {
		return averageInvestRateNormal;
	}

	public void setAverageInvestRateNormal(String averageInvestRateNormal) {
		this.averageInvestRateNormal = averageInvestRateNormal;
	}
}
