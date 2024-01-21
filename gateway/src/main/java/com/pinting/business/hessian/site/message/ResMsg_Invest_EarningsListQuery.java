package com.pinting.business.hessian.site.message;

import java.util.ArrayList;
import java.util.HashMap;

import com.pinting.core.hessian.msg.ResMsg;
/**
 * 根据userId查询投资收益明细 入参
 * @author shiyulong
 * @2015-12-18 下午3:04:47
 */
public class ResMsg_Invest_EarningsListQuery extends ResMsg {


	/**
	 * 
	 */
	private static final long serialVersionUID = 2353986265786143079L;
	/*用户id*/
	private Integer userId;
	/*总收益金额，暂时未用*/
	private Double totalEarnings;
	/*总条数*/
	private Integer totalCount;
	/*起始页*/
	private Integer pageIndex;
	
	public Integer getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(Integer totalCount) {
		this.totalCount = totalCount;
	}

	public Integer getPageIndex() {
		return pageIndex;
	}

	public void setPageIndex(Integer pageIndex) {
		this.pageIndex = pageIndex;
	}
	/**
	 * 以下循环：
	 * earningsDate	投资时间	必填	Date
	 * amount		金额		必填	Double
	 * remark		备注		可选	String
	 */
	private ArrayList<HashMap<String, Object>> investEarnings;

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public Double getTotalEarnings() {
		return totalEarnings;
	}

	public void setTotalEarnings(Double totalEarnings) {
		this.totalEarnings = totalEarnings;
	}

	public ArrayList<HashMap<String, Object>> getInvestEarnings() {
		return investEarnings;
	}

	public void setInvestEarnings(ArrayList<HashMap<String, Object>> investEarnings) {
		this.investEarnings = investEarnings;
	}

}
