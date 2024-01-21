package com.pinting.business.model.vo;

import java.util.Date;

/**
 * 还款专户（归集户）代收代付
 * @author bianyatian
 * @2017-11-9 下午2:43:31
 */
public class RepayDSDFVO {
    
    private Integer rowno; //查询结果序号

	/**
     * 融资客户名称
     */
    private String userName;
    
    /**
     * 融资客户手机
     */
    private String userMobile;
    
    /**
     * 代收代付类型DS  or  DF
     */
    private String DSDFType;
    
    /**
     * 金额
     */
    private Double amount;
    
    /**
     * 执行时间
     */
    private Date doneTime;
    
    /**
     * 代收总金额
     */
    private Double DSAmount;
    
    /**
     * 代付总金额
     */
    private Double DFAmount;
    
    /**
     * 状态
     */
    private Integer status;

	/**
	 * 创建时间
	 */
	private Date createTime;

	/**
	 * 批次订单号
	 */
	private String orderNo;
    
	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserMobile() {
		return userMobile;
	}

	public void setUserMobile(String userMobile) {
		this.userMobile = userMobile;
	}

	public String getDSDFType() {
		return DSDFType;
	}

	public void setDSDFType(String dSDFType) {
		DSDFType = dSDFType;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public Date getDoneTime() {
		return doneTime;
	}

	public void setDoneTime(Date doneTime) {
		this.doneTime = doneTime;
	}

	public Integer getRowno() {
		return rowno;
	}

	public void setRowno(Integer rowno) {
		this.rowno = rowno;
	}

	public Double getDSAmount() {
		return DSAmount;
	}

	public void setDSAmount(Double dSAmount) {
		DSAmount = dSAmount;
	}

	public Double getDFAmount() {
		return DFAmount;
	}

	public void setDFAmount(Double dFAmount) {
		DFAmount = dFAmount;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}
}
