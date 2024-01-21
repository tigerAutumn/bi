package com.pinting.open.pojo.response.asset;

import java.util.Date;
import java.util.List;

import com.pinting.open.base.response.Response;
import com.pinting.open.pojo.model.asset.InvestAccount;

public class MyInvestmentResponse  extends Response{
	

	private Integer totalPage;   //总页数
	
	private Integer processingNum;//当前用户处理中订单的数量

	/**
	 * 	//投资金额
    private Double balance; 
    //投资开始日期
    private Date interestBeginDate;
	//投资结束日期
	private Date investEndTime;
	//投资剩余天数
	private Integer investDay;
	//现利息基数
    private Double productRate;
    //投资状态
    private Integer status;
	 */
	private List<InvestAccount> investAccounts;  //我的投资列表

	
	
	

	public Integer getTotalPage() {
		return totalPage;
	}

	public void setTotalPage(Integer totalPage) {
		this.totalPage = totalPage;
	}

	public Integer getProcessingNum() {
		return processingNum;
	}

	public void setProcessingNum(Integer processingNum) {
		this.processingNum = processingNum;
	}

	public List<InvestAccount> getInvestAccounts() {
		return investAccounts;
	}

	public void setInvestAccounts(List<InvestAccount> investAccounts) {
		this.investAccounts = investAccounts;
	}

	
}
