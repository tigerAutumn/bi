package com.pinting.business.model.vo;

import com.pinting.business.model.BsRedPacketApply;

/**
 * 
 * @author bianyatian
 * @2016-3-1 下午5:13:05
 */
public class BsRedPacketApplyVO extends BsRedPacketApply {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4364399915965031779L;
	
	/** 已通过申请的预算总额 */
	private Double checkAmount; //状态为UNCHECKED 待审核，PASS 已通过的金额
	
	private Double canPutAmount; //可发放
	
	/** 申请人  */
	private String name;
	
	/** 审核人 */
	private String checkerName;
	
	private Integer mid;
	
	/** 可用余额 */
	private Double availableAmount;
	
	/** 发给用户的，逾期的红包额 */
	private Double expiryAmount;
	
	/** 停用或过期的批次 未发送给用户的红包额 */
	private Double disableUnSendAmount;

	public Double getCanPutAmount() {
		return canPutAmount;
	}

	public void setCanPutAmount(Double canPutAmount) {
		this.canPutAmount = canPutAmount;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCheckerName() {
		return checkerName;
	}

	public void setCheckerName(String checkerName) {
		this.checkerName = checkerName;
	}

	public Integer getMid() {
		return mid;
	}

	public void setMid(Integer mid) {
		this.mid = mid;
	}

	public Double getCheckAmount() {
		return checkAmount;
	}

	public void setCheckAmount(Double checkAmount) {
		this.checkAmount = checkAmount;
	}

	public Double getAvailableAmount() {
		return availableAmount;
	}

	public void setAvailableAmount(Double availableAmount) {
		this.availableAmount = availableAmount;
	}

	public Double getExpiryAmount() {
		return expiryAmount;
	}

	public void setExpiryAmount(Double expiryAmount) {
		this.expiryAmount = expiryAmount;
	}

	public Double getDisableUnSendAmount() {
		return disableUnSendAmount;
	}

	public void setDisableUnSendAmount(Double disableUnSendAmount) {
		this.disableUnSendAmount = disableUnSendAmount;
	}
}
