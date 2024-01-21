package com.pinting.business.model.vo;


import java.util.Date;

import com.pinting.business.model.BsAgent;


public class AgentStatInfoVO extends BsAgent{

	/**
	 * 
	 */
	private static final long serialVersionUID = -6734079144957636558L;
	
	/** 注册用户数 */
	private Integer registUserCount;
	
	/** 投资用户数 */
	private Integer investUserCount;
	
	private Integer oneInvestUserCount;
	
	private Integer threeInvestUserCount;
	
	private Integer sixInvestUserCount;
	
	private Integer twelveInvestUserCount; 
	
	/** 交易总笔数 */
	private Integer transItemCount;
	
	private Integer sevenDayTransItemCount;
	
	private Integer oneTransItemCount;
	
	private Integer threeTransItemCount;
	
	private Integer sixTransItemCount;
	
	private Integer twelveTransItemCount;
	
	/** 交易总金额 */
	private Double transAmountCount;
	
	private Double oneTransAmountCount;
	
	private Double threeTransAmountCount;
	
	private Double sixTransAmountCount;
	
	private Double twelveTransAmountCount;
	
	/** 产品期限 */
	private Integer term;
	
	/** 7天产品购买金额合计 */
	private Double sevenDayBalance;
	
	/** 1个月产品购买金额合计 */
	private Double oneMonthBalance;
	
	/** 3个月产品购买金额合计 */
	private Double threeMonthBalance;
	
	/** 6个月产品购买金额合计 */
	private Double sixMonthBalance;
	
	/** 12个月产品购买金额合计 */
	private Double twelveMonthBalance;
	
	/** 年化交易金额 (1、3、6、12产品年化收益总计) */
	private Double proceedsBalance;
	
	private Double sevenDayProceedsBalance;
	
	private Double oneProceedsBalance;
	
	private Double threeProceedsBalance;
	
	private Double sixProceedsBalance;
	
	private Double twelveProceedsBalance;
	
	/** 开始时间 */
	private Date beginTime;
	
	/** 结束时间 */
	private Date overTime;
	
	/** 年化金额合计 */
	private Double proceedsBalanceTotal;

	// 终端
	private String terminal;
	
	public Integer getRegistUserCount() {
		return registUserCount;
	}

	public void setRegistUserCount(Integer registUserCount) {
		this.registUserCount = registUserCount;
	}

	public Integer getInvestUserCount() {
		return investUserCount;
	}

	public void setInvestUserCount(Integer investUserCount) {
		this.investUserCount = investUserCount;
	}

	public Integer getOneInvestUserCount() {
		return oneInvestUserCount;
	}

	public void setOneInvestUserCount(Integer oneInvestUserCount) {
		this.oneInvestUserCount = oneInvestUserCount;
	}

	public Integer getThreeInvestUserCount() {
		return threeInvestUserCount;
	}

	public void setThreeInvestUserCount(Integer threeInvestUserCount) {
		this.threeInvestUserCount = threeInvestUserCount;
	}

	public Integer getSixInvestUserCount() {
		return sixInvestUserCount;
	}

	public void setSixInvestUserCount(Integer sixInvestUserCount) {
		this.sixInvestUserCount = sixInvestUserCount;
	}

	public Integer getTwelveInvestUserCount() {
		return twelveInvestUserCount;
	}

	public void setTwelveInvestUserCount(Integer twelveInvestUserCount) {
		this.twelveInvestUserCount = twelveInvestUserCount;
	}

	public Integer getTransItemCount() {
		return transItemCount;
	}

	public void setTransItemCount(Integer transItemCount) {
		this.transItemCount = transItemCount;
	}

	public Integer getOneTransItemCount() {
		return oneTransItemCount;
	}

	public void setOneTransItemCount(Integer oneTransItemCount) {
		this.oneTransItemCount = oneTransItemCount;
	}

	public Integer getThreeTransItemCount() {
		return threeTransItemCount;
	}

	public void setThreeTransItemCount(Integer threeTransItemCount) {
		this.threeTransItemCount = threeTransItemCount;
	}

	public Integer getSixTransItemCount() {
		return sixTransItemCount;
	}

	public void setSixTransItemCount(Integer sixTransItemCount) {
		this.sixTransItemCount = sixTransItemCount;
	}

	public Integer getTwelveTransItemCount() {
		return twelveTransItemCount;
	}

	public void setTwelveTransItemCount(Integer twelveTransItemCount) {
		this.twelveTransItemCount = twelveTransItemCount;
	}

	public Double getTransAmountCount() {
		return transAmountCount;
	}

	public void setTransAmountCount(Double transAmountCount) {
		this.transAmountCount = transAmountCount;
	}

	public Double getOneTransAmountCount() {
		return oneTransAmountCount;
	}

	public void setOneTransAmountCount(Double oneTransAmountCount) {
		this.oneTransAmountCount = oneTransAmountCount;
	}

	public Double getThreeTransAmountCount() {
		return threeTransAmountCount;
	}

	public void setThreeTransAmountCount(Double threeTransAmountCount) {
		this.threeTransAmountCount = threeTransAmountCount;
	}

	public Double getSixTransAmountCount() {
		return sixTransAmountCount;
	}

	public void setSixTransAmountCount(Double sixTransAmountCount) {
		this.sixTransAmountCount = sixTransAmountCount;
	}

	public Double getTwelveTransAmountCount() {
		return twelveTransAmountCount;
	}

	public void setTwelveTransAmountCount(Double twelveTransAmountCount) {
		this.twelveTransAmountCount = twelveTransAmountCount;
	}

	public Integer getTerm() {
		return term;
	}

	public void setTerm(Integer term) {
		this.term = term;
	}

	public Double getOneMonthBalance() {
		return oneMonthBalance;
	}

	public void setOneMonthBalance(Double oneMonthBalance) {
		this.oneMonthBalance = oneMonthBalance;
	}

	public Double getThreeMonthBalance() {
		return threeMonthBalance;
	}

	public void setThreeMonthBalance(Double threeMonthBalance) {
		this.threeMonthBalance = threeMonthBalance;
	}

	public Double getSixMonthBalance() {
		return sixMonthBalance;
	}

	public void setSixMonthBalance(Double sixMonthBalance) {
		this.sixMonthBalance = sixMonthBalance;
	}

	public Double getTwelveMonthBalance() {
		return twelveMonthBalance;
	}

	public void setTwelveMonthBalance(Double twelveMonthBalance) {
		this.twelveMonthBalance = twelveMonthBalance;
	}

	public Double getProceedsBalance() {
		return proceedsBalance;
	}

	public void setProceedsBalance(Double proceedsBalance) {
		this.proceedsBalance = proceedsBalance;
	}

	public Double getOneProceedsBalance() {
		return oneProceedsBalance;
	}

	public void setOneProceedsBalance(Double oneProceedsBalance) {
		this.oneProceedsBalance = oneProceedsBalance;
	}

	public Double getThreeProceedsBalance() {
		return threeProceedsBalance;
	}

	public void setThreeProceedsBalance(Double threeProceedsBalance) {
		this.threeProceedsBalance = threeProceedsBalance;
	}

	public Double getSixProceedsBalance() {
		return sixProceedsBalance;
	}

	public void setSixProceedsBalance(Double sixProceedsBalance) {
		this.sixProceedsBalance = sixProceedsBalance;
	}

	public Double getTwelveProceedsBalance() {
		return twelveProceedsBalance;
	}

	public void setTwelveProceedsBalance(Double twelveProceedsBalance) {
		this.twelveProceedsBalance = twelveProceedsBalance;
	}

	public Date getBeginTime() {
		return beginTime;
	}

	public void setBeginTime(Date beginTime) {
		this.beginTime = beginTime;
	}

	public Date getOverTime() {
		return overTime;
	}

	public void setOverTime(Date overTime) {
		this.overTime = overTime;
	}

	public Double getProceedsBalanceTotal() {
		return proceedsBalanceTotal;
	}

	public void setProceedsBalanceTotal(Double proceedsBalanceTotal) {
		this.proceedsBalanceTotal = proceedsBalanceTotal;
	}

	public Double getSevenDayBalance() {
		return sevenDayBalance;
	}

	public void setSevenDayBalance(Double sevenDayBalance) {
		this.sevenDayBalance = sevenDayBalance;
	}

	public Double getSevenDayProceedsBalance() {
		return sevenDayProceedsBalance;
	}

	public void setSevenDayProceedsBalance(Double sevenDayProceedsBalance) {
		this.sevenDayProceedsBalance = sevenDayProceedsBalance;
	}

	public Integer getSevenDayTransItemCount() {
		return sevenDayTransItemCount;
	}

	public void setSevenDayTransItemCount(Integer sevenDayTransItemCount) {
		this.sevenDayTransItemCount = sevenDayTransItemCount;
	}

	public String getTerminal() {
		return terminal;
	}

	public void setTerminal(String terminal) {
		this.terminal = terminal;
	}


}
