package com.pinting.business.hessian.site.message;

import com.pinting.core.hessian.msg.ReqMsg;

public class ReqMsg_Bank_AddSave extends ReqMsg {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4379671827782405617L;
	
	private Integer userId;
	
	private String idCard;
	
	private String cardNo;
	
	private String cardOwner;
	
	private Integer bankId;
	
	private String branchName;
	
	private Integer openProvince;
	
	private Integer provinceCode;
	
	private Integer openCity;
	
	private Integer cityCode;

	private String bankName;
	
	private String cityName;
	
	private String provinceName;

	public Integer getProvinceCode() {
		return provinceCode;
	}

	public void setProvinceCode(Integer provinceCode) {
		this.provinceCode = provinceCode;
	}

	public Integer getCityCode() {
		return cityCode;
	}

	public void setCityCode(Integer cityCode) {
		this.cityCode = cityCode;
	}

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	public String getProvinceName() {
		return provinceName;
	}

	public void setProvinceName(String provinceName) {
		this.provinceName = provinceName;
	}

	public String getBranchName() {
		return branchName;
	}

	public void setBranchName(String branchName) {
		this.branchName = branchName;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}


	public String getIdCard() {
		return idCard;
	}

	public void setIdCard(String idCard) {
		this.idCard = idCard;
	}

	public String getCardNo() {
		return cardNo;
	}

	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}

	public String getCardOwner() {
		return cardOwner;
	}

	public void setCardOwner(String cardOwner) {
		this.cardOwner = cardOwner;
	}

	public Integer getBankId() {
		return bankId;
	}

	public void setBankId(Integer bankId) {
		this.bankId = bankId;
	}

	public Integer getOpenProvince() {
		return openProvince;
	}

	public void setOpenProvince(Integer openProvince) {
		this.openProvince = openProvince;
	}

	public Integer getOpenCity() {
		return openCity;
	}

	public void setOpenCity(Integer openCity) {
		this.openCity = openCity;
	}
	
	
}
