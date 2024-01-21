package com.pinting.business.hessian.site.message;

import java.util.ArrayList;
import java.util.HashMap;

import com.pinting.core.hessian.msg.ResMsg;

public class ResMsg_Invest_PlatformStatistics extends ResMsg {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2311100861268281033L;
	
	// 累计投资额
	private String totalInvestAmount;
	// 累计平台用户数
	private String totalRegUser;
	// 累计赚取收益
	private String totalInterestAmount;
	// 累计投资额(按月分组)
	private ArrayList<HashMap<String,Object>> totalInvestGroupByMonth;
	// 用户待领取收益
	private String investInterestWill;  
	// 按照产品类型查询用户投资情况
	private ArrayList<HashMap<String,Object>> investTotalGroupByProduct;
	
	// 查询当年每月截止当月为止历史累计投资总金额
	private ArrayList<HashMap<String,Object>> investMentOverDateMonth;
	//钱报178产品平均年化收益
	private String averageInvestRate178;
	//普通用户平均年化收益
	private String averageInvestRateNormal;
	
	// 按照产品类型查询用户投资情况
	private ArrayList<HashMap<String,Object>> investTotalGroupByProductTerm;
	// 投资人性别比例
	private ArrayList<HashMap<String,Object>> investorTypeSex;
	// 投资人年龄段比例
	private ArrayList<HashMap<String,Object>> investorTypeAge;
	
	
    public String getTotalInvestAmount() {
        return totalInvestAmount;
    }

    public void setTotalInvestAmount(String totalInvestAmount) {
        this.totalInvestAmount = totalInvestAmount;
    }

    public String getTotalInterestAmount() {
        return totalInterestAmount;
    }

    public void setTotalInterestAmount(String totalInterestAmount) {
        this.totalInterestAmount = totalInterestAmount;
    }

    public String getTotalRegUser() {
        return totalRegUser;
    }

    public void setTotalRegUser(String totalRegUser) {
        this.totalRegUser = totalRegUser;
    }

    public ArrayList<HashMap<String, Object>> getTotalInvestGroupByMonth() {
		return totalInvestGroupByMonth;
	}

	public void setTotalInvestGroupByMonth(
			ArrayList<HashMap<String, Object>> totalInvestGroupByMonth) {
		this.totalInvestGroupByMonth = totalInvestGroupByMonth;
	}

	public String getInvestInterestWill() {
		return investInterestWill;
	}

	public void setInvestInterestWill(String investInterestWill) {
		this.investInterestWill = investInterestWill;
	}

	public ArrayList<HashMap<String,Object>> getInvestTotalGroupByProduct() {
		return investTotalGroupByProduct;
	}

	public void setInvestTotalGroupByProduct(
			ArrayList<HashMap<String,Object>> investTotalGroupByProduct) {
		this.investTotalGroupByProduct = investTotalGroupByProduct;
	}

	public ArrayList<HashMap<String, Object>> getInvestMentOverDateMonth() {
		return investMentOverDateMonth;
	}

	public void setInvestMentOverDateMonth(
			ArrayList<HashMap<String, Object>> investMentOverDateMonth) {
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

	public ArrayList<HashMap<String, Object>> getInvestTotalGroupByProductTerm() {
		return investTotalGroupByProductTerm;
	}

	public void setInvestTotalGroupByProductTerm(
			ArrayList<HashMap<String, Object>> investTotalGroupByProductTerm) {
		this.investTotalGroupByProductTerm = investTotalGroupByProductTerm;
	}

	public ArrayList<HashMap<String, Object>> getInvestorTypeSex() {
		return investorTypeSex;
	}

	public void setInvestorTypeSex(
			ArrayList<HashMap<String, Object>> investorTypeSex) {
		this.investorTypeSex = investorTypeSex;
	}

	public ArrayList<HashMap<String, Object>> getInvestorTypeAge() {
		return investorTypeAge;
	}

	public void setInvestorTypeAge(
			ArrayList<HashMap<String, Object>> investorTypeAge) {
		this.investorTypeAge = investorTypeAge;
	}

	
}
