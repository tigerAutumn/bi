package com.pinting.business.hessian.manage.message;

import com.pinting.core.hessian.msg.ReqMsg;

public class ReqMsg_BsBankCard_BankCardListQuery extends ReqMsg{

	/**
	 * 
	 */
	private static final long serialVersionUID = -4132492553300269087L;
	
	private String pageNum;
	private String numPerPage;
	/** 银行卡号**/
	private String cardNo;
	/** 用户名**/
	private String cardOwner;
	/** 手机号**/
	private String mobile;
	/** 身份证**/
	private String idCard;
	/** 银行卡类型**/
	private Integer bankId;
	/** 绑定状态 1.正常 2.禁用 3.绑定中 4.绑定失败**/
	private Integer status;
	/** 是否回款  1.是  0.否**/
	private Integer isFirst;
	/** 手机号预留号码**/
	private String obligateMobile;
	
	public String getPageNum() {
		return pageNum;
	}
	public void setPageNum(String pageNum) {
		this.pageNum = pageNum;
	}
	public String getNumPerPage() {
		return numPerPage;
	}
	public void setNumPerPage(String numPerPage) {
		this.numPerPage = numPerPage;
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
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getIdCard() {
		return idCard;
	}
	public void setIdCard(String idCard) {
		this.idCard = idCard;
	}
	public Integer getBankId() {
		return bankId;
	}
	public void setBankId(Integer bankId) {
		this.bankId = bankId;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public Integer getIsFirst() {
		return isFirst;
	}
	public void setIsFirst(Integer isFirst) {
		this.isFirst = isFirst;
	}
	public String getObligateMobile() {
		return obligateMobile;
	}
	public void setObligateMobile(String obligateMobile) {
		this.obligateMobile = obligateMobile;
	}

}
