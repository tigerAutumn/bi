package com.pinting.business.model.vo;

/**
 * 管理台存量提前赎回，返回到页面的列表
 * @author bianyatian
 * @2018-1-22 上午10:04:40
 */
public class SysReturnManageInfoVO {
	
	private Integer rowno; //查询结果序号
	
	private String sendBatchId; //批次号
	
	private Integer term; //查询结果序号
	
	private Double principal; //赎回本金
	
	private Double interest; //赎回利息
	
	private Double total; //赎回总计

	public Integer getRowno() {
		return rowno;
	}

	public void setRowno(Integer rowno) {
		this.rowno = rowno;
	}

	public String getSendBatchId() {
		return sendBatchId;
	}

	public void setSendBatchId(String sendBatchId) {
		this.sendBatchId = sendBatchId;
	}

	public Integer getTerm() {
		return term;
	}

	public void setTerm(Integer term) {
		this.term = term;
	}

	public Double getPrincipal() {
		return principal;
	}

	public void setPrincipal(Double principal) {
		this.principal = principal;
	}

	public Double getInterest() {
		return interest;
	}

	public void setInterest(Double interest) {
		this.interest = interest;
	}

	public Double getTotal() {
		return total;
	}

	public void setTotal(Double total) {
		this.total = total;
	}
	
}
