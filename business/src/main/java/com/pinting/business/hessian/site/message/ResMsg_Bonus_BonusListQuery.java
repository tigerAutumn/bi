package com.pinting.business.hessian.site.message;

import java.util.ArrayList;
import java.util.HashMap;

import com.pinting.core.hessian.msg.ResMsg;

/**
 * 用户奖励金列表查询 出参
 * @author yanwenlong
 * @2015-12-22 下午3:19:28
 */
public class ResMsg_Bonus_BonusListQuery extends ResMsg {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1038511873141858002L;
	/*用户id*/
	private Integer userId;
	/*用户奖励金明细表时间*/
	private Double totalEarnings;
	/*总条数*/
	private Integer totalCount;
	/*页数*/
	private Integer pageIndex;
	/*true为存在未成功的转余额记录*/
	private String haveSpecial;
	/*未成功的转余额的金额*/
	private Double specialBonusAmount;
	/*当前奖励金*/
	private Double bonus;
	
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
	private ArrayList<HashMap<String, Object>> bonuss;

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

	public ArrayList<HashMap<String, Object>> getBonuss() {
		return bonuss;
	}

	public void setBonuss(ArrayList<HashMap<String, Object>> bonuss) {
		this.bonuss = bonuss;
	}

	public Double getSpecialBonusAmount() {
		return specialBonusAmount;
	}

	public void setSpecialBonusAmount(Double specialBonusAmount) {
		this.specialBonusAmount = specialBonusAmount;
	}

	public String getHaveSpecial() {
		return haveSpecial;
	}

	public void setHaveSpecial(String haveSpecial) {
		this.haveSpecial = haveSpecial;
	}

	public Double getBonus() {
		return bonus;
	}

	public void setBonus(Double bonus) {
		this.bonus = bonus;
	}
	
}
