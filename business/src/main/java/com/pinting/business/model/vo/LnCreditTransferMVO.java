package com.pinting.business.model.vo;

import com.pinting.business.model.LnCreditTransfer;

/**
 * 管理台债权转让统计列表显示
 * @author bianyatian
 * @2016-12-2 下午2:23:59
 */
public class LnCreditTransferMVO extends LnCreditTransfer {
	
	private Integer rowno; //查询结果序号
	
	private String outUserName; //转出理财用户姓名
	
	private String inUserName; //转入（承接）理财用户姓名
	
	private String outUserMobile; //出让人手机号（账户）
    
	private String inUserMobile; //受让人手机号（账户）
	
	private Double serviceAmount; // 公司手续费（存管后债权转让，云贷赞时贷） 
	
	private Double outAmount; //应付老客户利息（存管后债权转让，云贷赞时贷）
	
	private Double totalAmount; //本利合计
	
	private String partnerCode; //YUN_DAI_SELF /ZSD

	private String partnerBusinessFlag; //借款产品标识

	public String getOutUserName() {
		return outUserName;
	}

	public void setOutUserName(String outUserName) {
		this.outUserName = outUserName;
	}

	public String getInUserName() {
		return inUserName;
	}

	public void setInUserName(String inUserName) {
		this.inUserName = inUserName;
	}

	public String getOutUserMobile() {
		return outUserMobile;
	}

	public void setOutUserMobile(String outUserMobile) {
		this.outUserMobile = outUserMobile;
	}

	public String getInUserMobile() {
		return inUserMobile;
	}

	public void setInUserMobile(String inUserMobile) {
		this.inUserMobile = inUserMobile;
	}

	public Double getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(Double totalAmount) {
		this.totalAmount = totalAmount;
	}

	public Integer getRowno() {
		return rowno;
	}

	public void setRowno(Integer rowno) {
		this.rowno = rowno;
	}

	public Double getServiceAmount() {
		return serviceAmount;
	}

	public void setServiceAmount(Double serviceAmount) {
		this.serviceAmount = serviceAmount;
	}

	public Double getOutAmount() {
		return outAmount;
	}

	public void setOutAmount(Double outAmount) {
		this.outAmount = outAmount;
	}

	public String getPartnerCode() {
		return partnerCode;
	}

	public void setPartnerCode(String partnerCode) {
		this.partnerCode = partnerCode;
	}

	public String getPartnerBusinessFlag() {
		return partnerBusinessFlag;
	}

	public void setPartnerBusinessFlag(String partnerBusinessFlag) {
		this.partnerBusinessFlag = partnerBusinessFlag;
	}
}
