package com.pinting.business.hessian.site.message;

import java.util.Date;

import com.pinting.core.hessian.msg.ResMsg;
/**
 * 根据用户id和子产品id号查询我的单条投资记录  出参 
 * 目前没有controller用到
 * @author huangmengjian
 * @2015-2-5 下午6:48:25
 */
public class ResMsg_Invest_InfoQuery extends ResMsg {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2020672030345643455L;
	/*投资金额*/
	private double investAmount ;
	/*已投资天数*/
	private Integer investDay;
	/*起息日期*/
	private Date investBeginDate;
	/*投资结束日期*/
	private Date investOverDate;
	/*子账户id*/
	private Integer subAccountId;
	/*产品利率*/
	private double InvestRate;
	
	public Integer getSubAccountId() {
		return subAccountId;
	}


	public void setSubAccountId(Integer subAccountId) {
		this.subAccountId = subAccountId;
	}


	public double getInvestAmount() {
		return investAmount;
	}


	public void setInvestAmount(double investAmount) {
		this.investAmount = investAmount;
	}


	public Integer getInvestDay() {
		return investDay;
	}


	public void setInvestDay(Integer investDay) {
		this.investDay = investDay;
	}


	public Date getInvestBeginDate() {
		return investBeginDate;
	}


	public void setInvestBeginDate(Date investBeginDate) {
		this.investBeginDate = investBeginDate;
	}


	public Date getInvestOverDate() {
		return investOverDate;
	}


	public void setInvestOverDate(Date investOverDate) {
		this.investOverDate = investOverDate;
	}


	public double getInvestRate() {
		return InvestRate;
	}


	public void setInvestRate(double investRate) {
		InvestRate = investRate;
	}
	
	
	
}
