package com.pinting.open.pojo.response.asset;

import java.util.List;

import com.pinting.open.base.response.Response;
import com.pinting.open.pojo.model.asset.Bonuss;

public class MyBonusResponse extends Response {

	private Integer totalPage;   //总页数
	private Boolean haveSpecial; //true为存在未成功的转余额记录
	private Double specialBonusAmount; //未成功的转余额的金额
	private Double bonus;	// 奖励金
	
	/**
	 * 以下循环：
	 * earningsDate	投资时间	必填	Date
	 * amount		金额		必填	Double
	 * remark		备注		可选	String
	 */
	private List<Bonuss> bonuss;

	public Double getBonus() {
		return bonus;
	}

	public void setBonus(Double bonus) {
		this.bonus = bonus;
	}

	public Integer getTotalPage() {
		return totalPage;
	}

	public void setTotalPage(Integer totalPage) {
		this.totalPage = totalPage;
	}


	public Boolean getHaveSpecial() {
		return haveSpecial;
	}

	public void setHaveSpecial(Boolean haveSpecial) {
		this.haveSpecial = haveSpecial;
	}

	public Double getSpecialBonusAmount() {
		return specialBonusAmount;
	}

	public void setSpecialBonusAmount(Double specialBonusAmount) {
		this.specialBonusAmount = specialBonusAmount;
	}

	public List<Bonuss> getBonuss() {
		return bonuss;
	}

	public void setBonuss(List<Bonuss> bonuss) {
		this.bonuss = bonuss;
	}
	
}
