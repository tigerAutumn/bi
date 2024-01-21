package com.pinting.business.hessian.manage.message;

import com.pinting.core.hessian.msg.ResMsg;

public class ResMsg_Statistics_StatisticsBusiness extends ResMsg {
	/**
	 * 序列化编号
	 */
	private static final long serialVersionUID = -6118215237923335029L;
	
	private long tAttentionUserCount; //今日关注用户数
	private long yAttentionUserCount; //昨日关注用户数
	private long totalAttentionUserCount; //累计关注用户数	
	private long tRegisterUserCount; //今日注册用户数
	private long yRegisterUserCount; //昨日注册用户数
	private long totalRegisterUserCount; //累计注册用户数	
	private long tInvestUserCount; //今日投资用户数
	private long yInvestUserCount; //昨日投资用户数
	private long totalInvestUserCount; //累计投资用户数	
	private double tInvestAmount; //今日投资金额
	private double yInvestAmount; //昨日投资金额
	private double totalInvestAmount; //累计投资金额
	private long tDealCount; //今日成交笔数
	private long yDealCount; //昨日成交笔数
	private long totalDealCount; //累计成交笔数
	private double t7DayAmount; //今日7天投资金额-云贷
	private double t1MonthAmount; //今日30天投资金额-云贷
	private double t3MonthAmount; //今日90天投资金额-云贷
	private double t6MonthAmount; //今日180天投资金额-云贷
	private double t1YearAmount; //今日365天投资金额-云贷
	private double tAllAmountYundai; //今日总投资金额-云贷
	private double tYearAllAmount; //今日年化投资金额-云贷
	
	private double t1MonthAmount7dai;//今日30天投资金额-七贷
	private double t3MonthAmount7dai;//今日90天投资金额-七贷
	private double t6MonthAmount7dai;//今日180天投资金额-七贷
	private double t1YearAmount7dai;//今日365天投资金额-七贷
	private double tAllAmount7dai; //今日总投资金额-七贷
	private double tYearAllAmount7dai; //今日年化投资金额-七贷
	
	private double t3MonthAmountZan; //今日3个月投资金额(赞分期)
	private double t6MonthAmountZan; //今日6个月投资金额(赞分期)
	private double t9MonthAmountZan; //今日9个月投资金额(赞分期)
	private double t1YearAmountZan; //今日12个月投资金额(赞分期)
	private double tAllAmountZan; //今日总投资金额(赞分期)
	private double tYearAllAmountZan; //今日年化投资金额(赞分期)
	
	/*****************以下为业务统计总览（新增云贷存管产品）***********************/
	private double t1MonthAmountZan;//今日1个月投资金额(赞分期)
	private double t2MonthAmountZan;//今日2个月投资金额(赞分期)
	private double t4MonthAmountZan;//今日4个月投资金额(赞分期)
	private double t5MonthAmountZan;//今日5个月投资金额(赞分期)
	
	private double t7DayAuthAmount; //今日7天投资金额-云贷存管
	private double t1MonthAuthAmount; //今日30天投资金额-云贷存管
	private double t3MonthAuthAmount; //今日90天投资金额-云贷存管
	private double t6MonthAuthAmount; //今日180天投资金额-云贷存管
	private double t1YearAuthAmount; //今日365天投资金额-云贷存管
	private double tAllAuthAmountYundai; //今日总投资金额-云贷存管
	private double tYearAllAuthAmount; //今日年化投资金额-云贷存管
	
	//七贷存管
	private double t7DayAuthAmount7dai; //今日7天投资金额-七贷存管
	private double t1MonthAuthAmount7dai; //今日30天投资金额-七贷存管
	private double t3MonthAuthAmount7dai; //今日90天投资金额-七贷存管
	private double t6MonthAuthAmount7dai; //今日180天投资金额-七贷存管
	private double t1YearAuthAmount7dai; //今日365天投资金额-七贷存管
	private double tAllAuthAmount7dai; //今日总投资金额-七贷存管
	private double tYearAllAuthAmount7dai; //今日年化投资金额-七贷存管

	private double t7DayZsdAmount; //今日7天投资金额-赞时贷
	private double t1MonthZsdAmount; //今日30天投资金额-赞时贷
	private double t3MonthZsdAmount; //今日90天投资金额-赞时贷
	private double t6MonthZsdAmount; //今日180天投资金额-赞时贷
	private double t1YearZsdAmount; //今日365天投资金额-赞时贷
	private double tAllZsdAmount; //今日总投资金额-赞时贷
	private double tYearAllZsdAmount; //今日年化投资金额-赞时贷
	
	//自由站岗
	private double t7DayAuthAmount4Free; //今日7天投资金额-自由站岗
	private double t1MonthAuthAmount4Free; //今日30天投资金额-自由站岗
	private double t3MonthAuthAmount4Free; //今日90天投资金额-自由站岗
	private double t6MonthAuthAmount4Free; //今日180天投资金额-自由站岗
	private double t1YearAuthAmount4Free; //今日365天投资金额-自由站岗
	private double tAllAuthAmount4Free; //今日总投资金额-自由站岗
	private double tYearAllAuthAmount4Free; //今日年化投资金额-自由站岗
		
	public double gettAllAmountYundai() {
		return tAllAmountYundai;
	}
	public void settAllAmountYundai(double tAllAmountYundai) {
		this.tAllAmountYundai = tAllAmountYundai;
	}
	public double getT1MonthAmount7dai() {
		return t1MonthAmount7dai;
	}
	public void setT1MonthAmount7dai(double t1MonthAmount7dai) {
		this.t1MonthAmount7dai = t1MonthAmount7dai;
	}
	public double getT3MonthAmount7dai() {
		return t3MonthAmount7dai;
	}
	public void setT3MonthAmount7dai(double t3MonthAmount7dai) {
		this.t3MonthAmount7dai = t3MonthAmount7dai;
	}
	public double getT6MonthAmount7dai() {
		return t6MonthAmount7dai;
	}
	public void setT6MonthAmount7dai(double t6MonthAmount7dai) {
		this.t6MonthAmount7dai = t6MonthAmount7dai;
	}
	public double getT1YearAmount7dai() {
		return t1YearAmount7dai;
	}
	public void setT1YearAmount7dai(double t1YearAmount7dai) {
		this.t1YearAmount7dai = t1YearAmount7dai;
	}
	public double gettAllAmount7dai() {
		return tAllAmount7dai;
	}
	public void settAllAmount7dai(double tAllAmount7dai) {
		this.tAllAmount7dai = tAllAmount7dai;
	}
	public double gettYearAllAmount7dai() {
		return tYearAllAmount7dai;
	}
	public void settYearAllAmount7dai(double tYearAllAmount7dai) {
		this.tYearAllAmount7dai = tYearAllAmount7dai;
	}
	public double getT1MonthAmount() {
		return t1MonthAmount;
	}
	public void setT1MonthAmount(double t1MonthAmount) {
		this.t1MonthAmount = t1MonthAmount;
	}
	public double getT3MonthAmount() {
		return t3MonthAmount;
	}
	public void setT3MonthAmount(double t3MonthAmount) {
		this.t3MonthAmount = t3MonthAmount;
	}
	public double getT6MonthAmount() {
		return t6MonthAmount;
	}
	public void setT6MonthAmount(double t6MonthAmount) {
		this.t6MonthAmount = t6MonthAmount;
	}
	public double getT1YearAmount() {
		return t1YearAmount;
	}
	public void setT1YearAmount(double t1YearAmount) {
		this.t1YearAmount = t1YearAmount;
	}
	public double gettYearAllAmount() {
		return tYearAllAmount;
	}
	public void settYearAllAmount(double tYearAllAmount) {
		this.tYearAllAmount = tYearAllAmount;
	}
	public long gettAttentionUserCount() {
		return tAttentionUserCount;
	}
	public void settAttentionUserCount(long tAttentionUserCount) {
		this.tAttentionUserCount = tAttentionUserCount;
	}
	public long getyAttentionUserCount() {
		return yAttentionUserCount;
	}
	public void setyAttentionUserCount(long yAttentionUserCount) {
		this.yAttentionUserCount = yAttentionUserCount;
	}
	public long getTotalAttentionUserCount() {
		return totalAttentionUserCount;
	}
	public void setTotalAttentionUserCount(long totalAttentionUserCount) {
		this.totalAttentionUserCount = totalAttentionUserCount;
	}
	public long gettRegisterUserCount() {
		return tRegisterUserCount;
	}
	public void settRegisterUserCount(long tRegisterUserCount) {
		this.tRegisterUserCount = tRegisterUserCount;
	}
	public long getyRegisterUserCount() {
		return yRegisterUserCount;
	}
	public void setyRegisterUserCount(long yRegisterUserCount) {
		this.yRegisterUserCount = yRegisterUserCount;
	}
	public long getTotalRegisterUserCount() {
		return totalRegisterUserCount;
	}
	public void setTotalRegisterUserCount(long totalRegisterUserCount) {
		this.totalRegisterUserCount = totalRegisterUserCount;
	}
	public long gettInvestUserCount() {
		return tInvestUserCount;
	}
	public void settInvestUserCount(long tInvestUserCount) {
		this.tInvestUserCount = tInvestUserCount;
	}
	public long getyInvestUserCount() {
		return yInvestUserCount;
	}
	public void setyInvestUserCount(long yInvestUserCount) {
		this.yInvestUserCount = yInvestUserCount;
	}
	public long getTotalInvestUserCount() {
		return totalInvestUserCount;
	}
	public void setTotalInvestUserCount(long totalInvestUserCount) {
		this.totalInvestUserCount = totalInvestUserCount;
	}
	public double gettInvestAmount() {
		return tInvestAmount;
	}
	public void settInvestAmount(double tInvestAmount) {
		this.tInvestAmount = tInvestAmount;
	}
	public double getyInvestAmount() {
		return yInvestAmount;
	}
	public void setyInvestAmount(double yInvestAmount) {
		this.yInvestAmount = yInvestAmount;
	}
	public double getTotalInvestAmount() {
		return totalInvestAmount;
	}
	public void setTotalInvestAmount(double totalInvestAmount) {
		this.totalInvestAmount = totalInvestAmount;
	}
	public long gettDealCount() {
		return tDealCount;
	}
	public void settDealCount(long tDealCount) {
		this.tDealCount = tDealCount;
	}
	public long getyDealCount() {
		return yDealCount;
	}
	public void setyDealCount(long yDealCount) {
		this.yDealCount = yDealCount;
	}
	public long getTotalDealCount() {
		return totalDealCount;
	}
	public void setTotalDealCount(long totalDealCount) {
		this.totalDealCount = totalDealCount;
	}
	public double getT7DayAmount() {
		return t7DayAmount;
	}
	public void setT7DayAmount(double t7DayAmount) {
		this.t7DayAmount = t7DayAmount;
	}
	public double getT3MonthAmountZan() {
		return t3MonthAmountZan;
	}
	public void setT3MonthAmountZan(double t3MonthAmountZan) {
		this.t3MonthAmountZan = t3MonthAmountZan;
	}
	public double getT6MonthAmountZan() {
		return t6MonthAmountZan;
	}
	public void setT6MonthAmountZan(double t6MonthAmountZan) {
		this.t6MonthAmountZan = t6MonthAmountZan;
	}
	public double getT1YearAmountZan() {
		return t1YearAmountZan;
	}
	public void setT1YearAmountZan(double t1YearAmountZan) {
		this.t1YearAmountZan = t1YearAmountZan;
	}
	public double gettYearAllAmountZan() {
		return tYearAllAmountZan;
	}
	public void settYearAllAmountZan(double tYearAllAmountZan) {
		this.tYearAllAmountZan = tYearAllAmountZan;
	}
	public double gettAllAmountZan() {
		return tAllAmountZan;
	}
	public void settAllAmountZan(double tAllAmountZan) {
		this.tAllAmountZan = tAllAmountZan;
	}
	public double getT9MonthAmountZan() {
		return t9MonthAmountZan;
	}
	public void setT9MonthAmountZan(double t9MonthAmountZan) {
		this.t9MonthAmountZan = t9MonthAmountZan;
	}
	public double getT1MonthAmountZan() {
		return t1MonthAmountZan;
	}
	public void setT1MonthAmountZan(double t1MonthAmountZan) {
		this.t1MonthAmountZan = t1MonthAmountZan;
	}
	public double getT2MonthAmountZan() {
		return t2MonthAmountZan;
	}
	public void setT2MonthAmountZan(double t2MonthAmountZan) {
		this.t2MonthAmountZan = t2MonthAmountZan;
	}
	public double getT4MonthAmountZan() {
		return t4MonthAmountZan;
	}
	public void setT4MonthAmountZan(double t4MonthAmountZan) {
		this.t4MonthAmountZan = t4MonthAmountZan;
	}
	public double getT5MonthAmountZan() {
		return t5MonthAmountZan;
	}
	public void setT5MonthAmountZan(double t5MonthAmountZan) {
		this.t5MonthAmountZan = t5MonthAmountZan;
	}
	public double getT7DayAuthAmount() {
		return t7DayAuthAmount;
	}
	public void setT7DayAuthAmount(double t7DayAuthAmount) {
		this.t7DayAuthAmount = t7DayAuthAmount;
	}
	public double getT1MonthAuthAmount() {
		return t1MonthAuthAmount;
	}
	public void setT1MonthAuthAmount(double t1MonthAuthAmount) {
		this.t1MonthAuthAmount = t1MonthAuthAmount;
	}
	public double getT3MonthAuthAmount() {
		return t3MonthAuthAmount;
	}
	public void setT3MonthAuthAmount(double t3MonthAuthAmount) {
		this.t3MonthAuthAmount = t3MonthAuthAmount;
	}
	public double getT6MonthAuthAmount() {
		return t6MonthAuthAmount;
	}
	public void setT6MonthAuthAmount(double t6MonthAuthAmount) {
		this.t6MonthAuthAmount = t6MonthAuthAmount;
	}
	public double getT1YearAuthAmount() {
		return t1YearAuthAmount;
	}
	public void setT1YearAuthAmount(double t1YearAuthAmount) {
		this.t1YearAuthAmount = t1YearAuthAmount;
	}
	public double gettAllAuthAmountYundai() {
		return tAllAuthAmountYundai;
	}
	public void settAllAuthAmountYundai(double tAllAuthAmountYundai) {
		this.tAllAuthAmountYundai = tAllAuthAmountYundai;
	}
	public double gettYearAllAuthAmount() {
		return tYearAllAuthAmount;
	}
	public void settYearAllAuthAmount(double tYearAllAuthAmount) {
		this.tYearAllAuthAmount = tYearAllAuthAmount;
	}
	public double getT7DayAuthAmount7dai() {
		return t7DayAuthAmount7dai;
	}
	public void setT7DayAuthAmount7dai(double t7DayAuthAmount7dai) {
		this.t7DayAuthAmount7dai = t7DayAuthAmount7dai;
	}
	public double getT1MonthAuthAmount7dai() {
		return t1MonthAuthAmount7dai;
	}
	public void setT1MonthAuthAmount7dai(double t1MonthAuthAmount7dai) {
		this.t1MonthAuthAmount7dai = t1MonthAuthAmount7dai;
	}
	public double getT3MonthAuthAmount7dai() {
		return t3MonthAuthAmount7dai;
	}
	public void setT3MonthAuthAmount7dai(double t3MonthAuthAmount7dai) {
		this.t3MonthAuthAmount7dai = t3MonthAuthAmount7dai;
	}
	public double getT6MonthAuthAmount7dai() {
		return t6MonthAuthAmount7dai;
	}
	public void setT6MonthAuthAmount7dai(double t6MonthAuthAmount7dai) {
		this.t6MonthAuthAmount7dai = t6MonthAuthAmount7dai;
	}
	public double getT1YearAuthAmount7dai() {
		return t1YearAuthAmount7dai;
	}
	public void setT1YearAuthAmount7dai(double t1YearAuthAmount7dai) {
		this.t1YearAuthAmount7dai = t1YearAuthAmount7dai;
	}
	public double gettAllAuthAmount7dai() {
		return tAllAuthAmount7dai;
	}
	public void settAllAuthAmount7dai(double tAllAuthAmount7dai) {
		this.tAllAuthAmount7dai = tAllAuthAmount7dai;
	}
	public double gettYearAllAuthAmount7dai() {
		return tYearAllAuthAmount7dai;
	}
	public void settYearAllAuthAmount7dai(double tYearAllAuthAmount7dai) {
		this.tYearAllAuthAmount7dai = tYearAllAuthAmount7dai;
	}
	public double getT7DayZsdAmount() {
		return t7DayZsdAmount;
	}

	public void setT7DayZsdAmount(double t7DayZsdAmount) {
		this.t7DayZsdAmount = t7DayZsdAmount;
	}

	public double getT1MonthZsdAmount() {
		return t1MonthZsdAmount;
	}

	public void setT1MonthZsdAmount(double t1MonthZsdAmount) {
		this.t1MonthZsdAmount = t1MonthZsdAmount;
	}

	public double getT3MonthZsdAmount() {
		return t3MonthZsdAmount;
	}

	public void setT3MonthZsdAmount(double t3MonthZsdAmount) {
		this.t3MonthZsdAmount = t3MonthZsdAmount;
	}

	public double getT6MonthZsdAmount() {
		return t6MonthZsdAmount;
	}

	public void setT6MonthZsdAmount(double t6MonthZsdAmount) {
		this.t6MonthZsdAmount = t6MonthZsdAmount;
	}

	public double getT1YearZsdAmount() {
		return t1YearZsdAmount;
	}

	public void setT1YearZsdAmount(double t1YearZsdAmount) {
		this.t1YearZsdAmount = t1YearZsdAmount;
	}

	public double gettAllZsdAmount() {
		return tAllZsdAmount;
	}

	public void settAllZsdAmount(double tAllZsdAmount) {
		this.tAllZsdAmount = tAllZsdAmount;
	}

	public double gettYearAllZsdAmount() {
		return tYearAllZsdAmount;
	}

	public void settYearAllZsdAmount(double tYearAllZsdAmount) {
		this.tYearAllZsdAmount = tYearAllZsdAmount;
	}
	public double getT7DayAuthAmount4Free() {
		return t7DayAuthAmount4Free;
	}
	public void setT7DayAuthAmount4Free(double t7DayAuthAmount4Free) {
		this.t7DayAuthAmount4Free = t7DayAuthAmount4Free;
	}
	public double getT1MonthAuthAmount4Free() {
		return t1MonthAuthAmount4Free;
	}
	public void setT1MonthAuthAmount4Free(double t1MonthAuthAmount4Free) {
		this.t1MonthAuthAmount4Free = t1MonthAuthAmount4Free;
	}
	public double getT3MonthAuthAmount4Free() {
		return t3MonthAuthAmount4Free;
	}
	public void setT3MonthAuthAmount4Free(double t3MonthAuthAmount4Free) {
		this.t3MonthAuthAmount4Free = t3MonthAuthAmount4Free;
	}
	public double getT6MonthAuthAmount4Free() {
		return t6MonthAuthAmount4Free;
	}
	public void setT6MonthAuthAmount4Free(double t6MonthAuthAmount4Free) {
		this.t6MonthAuthAmount4Free = t6MonthAuthAmount4Free;
	}
	public double getT1YearAuthAmount4Free() {
		return t1YearAuthAmount4Free;
	}
	public void setT1YearAuthAmount4Free(double t1YearAuthAmount4Free) {
		this.t1YearAuthAmount4Free = t1YearAuthAmount4Free;
	}
	public double gettAllAuthAmount4Free() {
		return tAllAuthAmount4Free;
	}
	public void settAllAuthAmount4Free(double tAllAuthAmount4Free) {
		this.tAllAuthAmount4Free = tAllAuthAmount4Free;
	}
	public double gettYearAllAuthAmount4Free() {
		return tYearAllAuthAmount4Free;
	}
	public void settYearAllAuthAmount4Free(double tYearAllAuthAmount4Free) {
		this.tYearAllAuthAmount4Free = tYearAllAuthAmount4Free;
	}
	
}