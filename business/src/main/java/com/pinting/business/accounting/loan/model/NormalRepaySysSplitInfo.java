package com.pinting.business.accounting.loan.model;

import com.pinting.business.accounting.loan.enums.PartnerEnum;
import com.pinting.business.coreflow.core.enums.BusinessTypeEnum;


/**
 * 存管-主动还款系统分账处理
 * @author bianyatian
 * @2017-4-6 上午10:19:19
 */
public class NormalRepaySysSplitInfo {
	
	private Integer lnPayOrdersId; //订单表id
	
	private Integer loanId; //借款编号

	private String partnerRepayId; //合作方计划还款编号
	
	private String orderNo; //还款支付单号
    
    private Double repayAmount; //还款总金额(还款本金+利息+逾期本利)   -- 云贷分期产品不使用该字段进行还款分账
    
    private Integer billBizInfoId;
    
    private PartnerEnum partnerEnum; //合作方
    
    private BusinessTypeEnum businessTypeEnum;//业务类型
    
	public Integer getLnPayOrdersId() {
		return lnPayOrdersId;
	}

	public void setLnPayOrdersId(Integer lnPayOrdersId) {
		this.lnPayOrdersId = lnPayOrdersId;
	}

	public Integer getLoanId() {
		return loanId;
	}

	public void setLoanId(Integer loanId) {
		this.loanId = loanId;
	}

	public String getPartnerRepayId() {
		return partnerRepayId;
	}

	public void setPartnerRepayId(String partnerRepayId) {
		this.partnerRepayId = partnerRepayId;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public Double getRepayAmount() {
		return repayAmount;
	}

	public void setRepayAmount(Double repayAmount) {
		this.repayAmount = repayAmount;
	}

	public Integer getBillBizInfoId() {
		return billBizInfoId;
	}

	public void setBillBizInfoId(Integer billBizInfoId) {
		this.billBizInfoId = billBizInfoId;
	}

	public PartnerEnum getPartnerEnum() {
		return partnerEnum;
	}

	public void setPartnerEnum(PartnerEnum partnerEnum) {
		this.partnerEnum = partnerEnum;
	}

	public BusinessTypeEnum getBusinessTypeEnum() {
		return businessTypeEnum;
	}

	public void setBusinessTypeEnum(BusinessTypeEnum businessTypeEnum) {
		this.businessTypeEnum = businessTypeEnum;
	}
	
}

