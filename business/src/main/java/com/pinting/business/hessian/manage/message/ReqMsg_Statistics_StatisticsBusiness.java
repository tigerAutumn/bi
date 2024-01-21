package com.pinting.business.hessian.manage.message;

import com.pinting.core.hessian.msg.ReqMsg;

public class ReqMsg_Statistics_StatisticsBusiness extends ReqMsg {
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
}