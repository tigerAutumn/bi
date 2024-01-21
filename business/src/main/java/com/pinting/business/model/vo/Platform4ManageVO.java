package com.pinting.business.model.vo;

import java.util.Date;
import java.util.List;

public class Platform4ManageVO {
	// 统计日期"年-月 2018-03"
    private String saveDate;
	//=====模块1====
	// 平台合规运营（天）
    private int operatingDays;
    // 累计成交额（元）
    private String totalBuyAmount;
    // 累计出借额（元）自成立以来累计借贷金额（元） 
    private String totalLoanAmount;
    // 用户累计收益（元）
    private String totalIncomeAmount;
    
	//=====模块2====
    // 自成立以来累计借贷笔数（笔）
    private Integer totalLoanNumber;
    // 当前待还借贷金额（元）
    private String currentWaitRepayAmount;
    // 当前待还借贷笔数（笔）
    private Integer currentWaitRepayNumber;
    // 关联关系借款余额（元）
    private String relationBorrowerAmount;
    // 关联关系借款余额笔数（笔）
    private Integer relationBorrowerNumber;
    
    //=====模块3====
    // 累计出借人数（人）
    private Integer totalLenderNumber;
    // 当期出借人数（人）
    private Integer currentLenderNumber;
    // 人均累计出借金额（元）累计借贷金额/累计出借人数
    private String eachLendTotalAmount;
    // 前十大出借人出借余额占比（%）
    private String topTenLendAmtProportion;
    // 最大单一出借人出借余额占比（%）
    private String topLendAmtProportion;
    
    //=====模块4====
    // 累计借款人数（人）
    private Integer totalBorrowerNumber;
    // 当期借款人数（人）
    private Integer currentBorrowerNumber;
    // 人均累计借款金额（元）累计借贷金额/累计借款人数
    private String eachBorrowTotalAmount;
    // 前十大借款人待还金额占比（%）
    private String topTenBorrowAmtProportion;
    // 最大单一借款人待还金额占比（%）
    private String topBorrowAmtProportion;
    
    //=====模块5====
    // 出借人项目逾期率（%）
    private String projectOverdueRate;
    // 出借人金额逾期率（%）
    private String amtOverdueRate;
    // 借款人逾期金额（元）
    private String overdueAmount;
    // 借款人逾期笔数（笔）
    private Integer overdueNumber;
    // 借款人逾期90天以上金额（元）
    private String overdueNinnetyDaysAmount;
    // 借款人逾期90天以上笔数（笔）
    private Integer overdueNinnetyDaysNumber;
    // 累计代偿金额（元）
    private String totalCompensatoryAmount;
    // 累计代偿笔数（笔）
    private Integer totalCompensatoryNumber;
    
    //=====模块6====
    //本月成交额（元）
    private String monthBuyAmount;
    //本月成交人数（人）
    private Integer monthBuyUserNumber;
    //本月成交笔数（笔）
    private Integer monthBuyNumber;
    //本月用户收益（元）
    private String monthIncomeAmount;
    //本月借贷金额（元）
    private String monthLoanAmount;
    //本月借贷笔数（笔）
    private Integer monthLoanNumber;
    
    //=====模块7====
    //本月各期限计划成交额
    private List<Platform4ManageProductVO> buyGroupList;
    
    //=====模块8  年龄维度分布====
    //年龄层次18-28占比（%）
    private String age18_28Proportion;
    //年龄层次29-39占比（%）
    private String age29_39Proportion;
    //年龄层次40-50占比（%）
    private String age40_50Proportion;
    //年龄层次50+占比（%）
    private String age50MoreProportion;
    
    //=====模块9====
    //网页版端口占比（%）订单端口，无端口加至pc
    private String pcProportion;
    //H5端口占比（%）
    private String h5Proportion;
    //app端口占比（%）
    private String appProportion;
    
    //=====模块10====
    //性别男占比（%）
    private String sexMaleProportion;
    //性别女占比（%）
    private String sexFemaleProportion;

    //=====模块11====
    //单日最高成交额（元）
    private String mostDayBuyAmount;
    //单笔最高成交额（元）
    private String mostOneBuyAmount;
    //最快满标时间（秒）
    private String fastestSecond;
    //成交次数最多（次）
    private String mostBuyTimes;
    
    //=====模块12 币港湾土豪榜：前五名（累计）====
    //币港湾土豪   王，男，100.00
    private List<String> richerList;
    
	public String getSaveDate() {
		return saveDate;
	}
	public void setSaveDate(String saveDate) {
		this.saveDate = saveDate;
	}
	public int getOperatingDays() {
		return operatingDays;
	}
	public void setOperatingDays(int operatingDays) {
		this.operatingDays = operatingDays;
	}
	public String getTotalBuyAmount() {
		return totalBuyAmount;
	}
	public void setTotalBuyAmount(String totalBuyAmount) {
		this.totalBuyAmount = totalBuyAmount;
	}
	public String getTotalLoanAmount() {
		return totalLoanAmount;
	}
	public void setTotalLoanAmount(String totalLoanAmount) {
		this.totalLoanAmount = totalLoanAmount;
	}
	public String getTotalIncomeAmount() {
		return totalIncomeAmount;
	}
	public void setTotalIncomeAmount(String totalIncomeAmount) {
		this.totalIncomeAmount = totalIncomeAmount;
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
	public Integer getTotalLenderNumber() {
		return totalLenderNumber;
	}
	public void setTotalLenderNumber(Integer totalLenderNumber) {
		this.totalLenderNumber = totalLenderNumber;
	}
	public Integer getCurrentLenderNumber() {
		return currentLenderNumber;
	}
	public void setCurrentLenderNumber(Integer currentLenderNumber) {
		this.currentLenderNumber = currentLenderNumber;
	}
	public String getEachLendTotalAmount() {
		return eachLendTotalAmount;
	}
	public void setEachLendTotalAmount(String eachLendTotalAmount) {
		this.eachLendTotalAmount = eachLendTotalAmount;
	}
	public String getTopTenLendAmtProportion() {
		return topTenLendAmtProportion;
	}
	public void setTopTenLendAmtProportion(String topTenLendAmtProportion) {
		this.topTenLendAmtProportion = topTenLendAmtProportion;
	}
	public String getTopLendAmtProportion() {
		return topLendAmtProportion;
	}
	public void setTopLendAmtProportion(String topLendAmtProportion) {
		this.topLendAmtProportion = topLendAmtProportion;
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
	public String getEachBorrowTotalAmount() {
		return eachBorrowTotalAmount;
	}
	public void setEachBorrowTotalAmount(String eachBorrowTotalAmount) {
		this.eachBorrowTotalAmount = eachBorrowTotalAmount;
	}
	public String getTopTenBorrowAmtProportion() {
		return topTenBorrowAmtProportion;
	}
	public void setTopTenBorrowAmtProportion(String topTenBorrowAmtProportion) {
		this.topTenBorrowAmtProportion = topTenBorrowAmtProportion;
	}
	public String getTopBorrowAmtProportion() {
		return topBorrowAmtProportion;
	}
	public void setTopBorrowAmtProportion(String topBorrowAmtProportion) {
		this.topBorrowAmtProportion = topBorrowAmtProportion;
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
	public String getMonthBuyAmount() {
		return monthBuyAmount;
	}
	public void setMonthBuyAmount(String monthBuyAmount) {
		this.monthBuyAmount = monthBuyAmount;
	}
	public Integer getMonthBuyUserNumber() {
		return monthBuyUserNumber;
	}
	public void setMonthBuyUserNumber(Integer monthBuyUserNumber) {
		this.monthBuyUserNumber = monthBuyUserNumber;
	}
	public Integer getMonthBuyNumber() {
		return monthBuyNumber;
	}
	public void setMonthBuyNumber(Integer monthBuyNumber) {
		this.monthBuyNumber = monthBuyNumber;
	}
	public String getMonthIncomeAmount() {
		return monthIncomeAmount;
	}
	public void setMonthIncomeAmount(String monthIncomeAmount) {
		this.monthIncomeAmount = monthIncomeAmount;
	}
	public String getMonthLoanAmount() {
		return monthLoanAmount;
	}
	public void setMonthLoanAmount(String monthLoanAmount) {
		this.monthLoanAmount = monthLoanAmount;
	}
	public Integer getMonthLoanNumber() {
		return monthLoanNumber;
	}
	public void setMonthLoanNumber(Integer monthLoanNumber) {
		this.monthLoanNumber = monthLoanNumber;
	}
	public List<Platform4ManageProductVO> getBuyGroupList() {
		return buyGroupList;
	}
	public void setBuyGroupList(List<Platform4ManageProductVO> buyGroupList) {
		this.buyGroupList = buyGroupList;
	}
	public String getAge18_28Proportion() {
		return age18_28Proportion;
	}
	public void setAge18_28Proportion(String age18_28Proportion) {
		this.age18_28Proportion = age18_28Proportion;
	}
	public String getAge29_39Proportion() {
		return age29_39Proportion;
	}
	public void setAge29_39Proportion(String age29_39Proportion) {
		this.age29_39Proportion = age29_39Proportion;
	}
	public String getAge40_50Proportion() {
		return age40_50Proportion;
	}
	public void setAge40_50Proportion(String age40_50Proportion) {
		this.age40_50Proportion = age40_50Proportion;
	}
	public String getAge50MoreProportion() {
		return age50MoreProportion;
	}
	public void setAge50MoreProportion(String age50MoreProportion) {
		this.age50MoreProportion = age50MoreProportion;
	}
	public String getPcProportion() {
		return pcProportion;
	}
	public void setPcProportion(String pcProportion) {
		this.pcProportion = pcProportion;
	}
	public String getH5Proportion() {
		return h5Proportion;
	}
	public void setH5Proportion(String h5Proportion) {
		this.h5Proportion = h5Proportion;
	}
	public String getAppProportion() {
		return appProportion;
	}
	public void setAppProportion(String appProportion) {
		this.appProportion = appProportion;
	}
	public String getSexMaleProportion() {
		return sexMaleProportion;
	}
	public void setSexMaleProportion(String sexMaleProportion) {
		this.sexMaleProportion = sexMaleProportion;
	}
	public String getSexFemaleProportion() {
		return sexFemaleProportion;
	}
	public void setSexFemaleProportion(String sexFemaleProportion) {
		this.sexFemaleProportion = sexFemaleProportion;
	}
	public String getMostDayBuyAmount() {
		return mostDayBuyAmount;
	}
	public void setMostDayBuyAmount(String mostDayBuyAmount) {
		this.mostDayBuyAmount = mostDayBuyAmount;
	}
	public String getMostOneBuyAmount() {
		return mostOneBuyAmount;
	}
	public void setMostOneBuyAmount(String mostOneBuyAmount) {
		this.mostOneBuyAmount = mostOneBuyAmount;
	}
	public String getFastestSecond() {
		return fastestSecond;
	}
	public void setFastestSecond(String fastestSecond) {
		this.fastestSecond = fastestSecond;
	}
	public String getMostBuyTimes() {
		return mostBuyTimes;
	}
	public void setMostBuyTimes(String mostBuyTimes) {
		this.mostBuyTimes = mostBuyTimes;
	}
	public List<String> getRicherList() {
		return richerList;
	}
	public void setRicherList(List<String> richerList) {
		this.richerList = richerList;
	}
    
}
