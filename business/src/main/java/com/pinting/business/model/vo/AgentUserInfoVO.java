package com.pinting.business.model.vo;


import com.pinting.business.model.BsUser;


public class AgentUserInfoVO extends BsUser{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 2578217179155523142L;
	private Integer transItemCount;
	private Double transAmountCount;
	
	
	public Integer getTransItemCount() {
		return transItemCount;
	}
	public void setTransItemCount(Integer transItemCount) {
		this.transItemCount = transItemCount;
	}
	public Double getTransAmountCount() {
		return transAmountCount;
	}
	public void setTransAmountCount(Double transAmountCount) {
		this.transAmountCount = transAmountCount;
	}

}
