package com.pinting.business.hessian.site.message;

import java.util.ArrayList;
import java.util.HashMap;

import com.pinting.core.hessian.msg.ResMsg;
/**
 * 投资管理
 * @author Dragon & cat
 * @date 2017-3-17
 */
public class ResMsg_User_InvestManage extends ResMsg {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 7893052351779975018L;
	/*持有金额*/
	private	  	Double		holdAmount;
	/*当日投资收益*/
	private		Double		dayInvestEarnings;
	/*累计到账收益*/
	private		Double		cumulativeInvestEarnings;
	/*应计收益*/
	private		Double 		accruedIncome;
	
	/*计划总数*/
	private		Integer		countTotalInvestBgw;
	/*计划持有中总数*/
	private		Integer		countHoldInvestBgw;
	/*计划已完成总数*/
	private		Integer		countFinishInvestBgw;
	/*计划委托中总数*/
	private		Integer		countEntrustInvestBgw;
	
	/*分页总条数*/
	private		Integer		count;
	/*投资列表*/
	private ArrayList<HashMap<String,Object>> investList;
	
	public Double getHoldAmount() {
		return holdAmount;
	}
	public void setHoldAmount(Double holdAmount) {
		this.holdAmount = holdAmount;
	}
	public Double getDayInvestEarnings() {
		return dayInvestEarnings;
	}
	public void setDayInvestEarnings(Double dayInvestEarnings) {
		this.dayInvestEarnings = dayInvestEarnings;
	}
	public Double getCumulativeInvestEarnings() {
		return cumulativeInvestEarnings;
	}
	public void setCumulativeInvestEarnings(Double cumulativeInvestEarnings) {
		this.cumulativeInvestEarnings = cumulativeInvestEarnings;
	}
	public Double getAccruedIncome() {
		return accruedIncome;
	}
	public void setAccruedIncome(Double accruedIncome) {
		this.accruedIncome = accruedIncome;
	}
	public Integer getCountTotalInvestBgw() {
		return countTotalInvestBgw;
	}
	public void setCountTotalInvestBgw(Integer countTotalInvestBgw) {
		this.countTotalInvestBgw = countTotalInvestBgw;
	}
	public Integer getCountHoldInvestBgw() {
		return countHoldInvestBgw;
	}
	public void setCountHoldInvestBgw(Integer countHoldInvestBgw) {
		this.countHoldInvestBgw = countHoldInvestBgw;
	}
	public Integer getCountFinishInvestBgw() {
		return countFinishInvestBgw;
	}
	public void setCountFinishInvestBgw(Integer countFinishInvestBgw) {
		this.countFinishInvestBgw = countFinishInvestBgw;
	}
	public Integer getCount() {
		return count;
	}
	public void setCount(Integer count) {
		this.count = count;
	}
	public ArrayList<HashMap<String, Object>> getInvestList() {
		return investList;
	}
	public void setInvestList(ArrayList<HashMap<String, Object>> investList) {
		this.investList = investList;
	}
	public Integer getCountEntrustInvestBgw() {
		return countEntrustInvestBgw;
	}
	public void setCountEntrustInvestBgw(Integer countEntrustInvestBgw) {
		this.countEntrustInvestBgw = countEntrustInvestBgw;
	}
}
