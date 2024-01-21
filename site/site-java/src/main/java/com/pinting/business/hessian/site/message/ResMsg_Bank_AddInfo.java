package com.pinting.business.hessian.site.message;

import java.util.HashMap;
import java.util.List;

import com.pinting.core.hessian.msg.ResMsg;

public class ResMsg_Bank_AddInfo extends ResMsg {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5460231309631655660L;

	private String userName;
	
	private String idCard;
	
	private List<HashMap<String,Object>> bankList;
	
	private List<HashMap<String,Object>> provinceList;

	private List<HashMap<String,Object>> cityList;
	
	//出现修改绑定银行卡信息回显时用到
	private String subBranchName;
	private String cardNo;
    private Integer openProvinceId;
	private Integer bankId;
    private Integer openCityId;
	
	public String getSubBranchName() {
		return subBranchName;
	}

	public void setSubBranchName(String subBranchName) {
		this.subBranchName = subBranchName;
	}

	public String getCardNo() {
		return cardNo;
	}

	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}

	public Integer getOpenProvinceId() {
		return openProvinceId;
	}

	public void setOpenProvinceId(Integer openProvinceId) {
		this.openProvinceId = openProvinceId;
	}

	public Integer getBankId() {
		return bankId;
	}

	public void setBankId(Integer bankId) {
		this.bankId = bankId;
	}

	public Integer getOpenCityId() {
		return openCityId;
	}

	public void setOpenCityId(Integer openCityId) {
		this.openCityId = openCityId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getIdCard() {
		return idCard;
	}

	public void setIdCard(String idCard) {
		this.idCard = idCard;
	}

	public void setCityList(List<HashMap<String, Object>> cityList) {
		this.cityList = cityList;
	}

	public List<HashMap<String, Object>> getCityList() {
		return cityList;
	}
	
	public List<HashMap<String, Object>> getBankList() {
		return bankList;
	}

	public void setBankList(List<HashMap<String, Object>> bankList) {
		this.bankList = bankList;
	}

	public List<HashMap<String, Object>> getProvinceList() {
		return provinceList;
	}

	public void setProvinceList(List<HashMap<String, Object>> provinceList) {
		this.provinceList = provinceList;
	}
	
}
