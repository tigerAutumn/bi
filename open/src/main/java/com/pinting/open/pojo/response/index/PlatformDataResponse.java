package com.pinting.open.pojo.response.index;

import java.util.List;

import com.pinting.open.base.response.Response;
import com.pinting.open.pojo.model.index.InvestMentOverDateMonthVO;
import com.pinting.open.pojo.model.index.InvestTotalGroupByProductVO;
import com.pinting.open.pojo.model.index.InvestorTypeVO;
import com.pinting.open.pojo.model.index.TotalInvestVO;

public class PlatformDataResponse extends Response {

	// 累计投资额
	private String totalInvestAmount;
	// 累计平台用户数
	private String totalRegUser;
	// 累计赚取收益
	private String totalInterestAmount;
	// 累计投资额(按月分组)
	private List<TotalInvestVO> totalInvestGroupByMonth;
	// 用户待领取收益
	private String investInterestWill;  
	// 按照产品类型查询用户投资情况
	private List<InvestTotalGroupByProductVO> investTotalGroupByProduct;
	// 当年每月的历史累计投资总金额
	private List<InvestMentOverDateMonthVO> totalInvestList;
	// 币港湾平均收益率
	private String averageInvestRateNormal;
	// 投资人性别比例
	private List<InvestorTypeVO> sexList;
	// 投资人年龄比例
	private List<InvestorTypeVO> ageList;
	
	public List<InvestorTypeVO> getSexList() {
		return sexList;
	}
	public void setSexList(List<InvestorTypeVO> sexList) {
		this.sexList = sexList;
	}
	public List<InvestorTypeVO> getAgeList() {
		return ageList;
	}
	public void setAgeList(List<InvestorTypeVO> ageList) {
		this.ageList = ageList;
	}
	public String getAverageInvestRateNormal() {
		return averageInvestRateNormal;
	}
	public void setAverageInvestRateNormal(String averageInvestRateNormal) {
		this.averageInvestRateNormal = averageInvestRateNormal;
	}
	public List<InvestMentOverDateMonthVO> getTotalInvestList() {
		return totalInvestList;
	}
	public void setTotalInvestList(List<InvestMentOverDateMonthVO> totalInvestList) {
		this.totalInvestList = totalInvestList;
	}
	public String getTotalInvestAmount() {
		return totalInvestAmount;
	}
	public void setTotalInvestAmount(String totalInvestAmount) {
		this.totalInvestAmount = totalInvestAmount;
	}
	public String getTotalRegUser() {
		return totalRegUser;
	}
	public void setTotalRegUser(String totalRegUser) {
		this.totalRegUser = totalRegUser;
	}
	public String getTotalInterestAmount() {
		return totalInterestAmount;
	}
	public void setTotalInterestAmount(String totalInterestAmount) {
		this.totalInterestAmount = totalInterestAmount;
	}
	public List<TotalInvestVO> getTotalInvestGroupByMonth() {
		return totalInvestGroupByMonth;
	}
	public void setTotalInvestGroupByMonth(List<TotalInvestVO> totalInvestGroupByMonth) {
		this.totalInvestGroupByMonth = totalInvestGroupByMonth;
	}
	public String getInvestInterestWill() {
		return investInterestWill;
	}
	public void setInvestInterestWill(String investInterestWill) {
		this.investInterestWill = investInterestWill;
	}
	public List<InvestTotalGroupByProductVO> getInvestTotalGroupByProduct() {
		return investTotalGroupByProduct;
	}
	public void setInvestTotalGroupByProduct(List<InvestTotalGroupByProductVO> investTotalGroupByProduct) {
		this.investTotalGroupByProduct = investTotalGroupByProduct;
	}
}
