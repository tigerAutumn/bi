package com.pinting.open.pojo.response.asset;

import java.util.List;

import com.pinting.open.base.response.Response;
import com.pinting.open.pojo.model.asset.InvestAccount;
/**
 * 我的投资_明细Response
 * @author Dragon & cat
 * @date 2016-8-30
 */
public class MyInvestmentEntrustResponse extends Response {

	
	private Integer totalPage; //港湾计划总页数
	
	private List<InvestAccount> investAccounts;  //我的投资列表-港湾计划
	
	private Integer totalPageEntrust; //委托计划总页数
	
	private List<InvestAccount> investAccountsEntrust;  //我的投资列表-委托计划


	public Integer getTotalPage() {
		return totalPage;
	}

	public void setTotalPage(Integer totalPage) {
		this.totalPage = totalPage;
	}

	public List<InvestAccount> getInvestAccounts() {
		return investAccounts;
	}

	public void setInvestAccounts(List<InvestAccount> investAccounts) {
		this.investAccounts = investAccounts;
	}

	public Integer getTotalPageEntrust() {
		return totalPageEntrust;
	}

	public void setTotalPageEntrust(Integer totalPageEntrust) {
		this.totalPageEntrust = totalPageEntrust;
	}

	public List<InvestAccount> getInvestAccountsEntrust() {
		return investAccountsEntrust;
	}

	public void setInvestAccountsEntrust(List<InvestAccount> investAccountsEntrust) {
		this.investAccountsEntrust = investAccountsEntrust;
	}

}
