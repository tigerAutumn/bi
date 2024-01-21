package com.pinting.business.model.vo;

import java.util.Date;
import java.util.List;

/**
 * 
 * @author SHENGUOPING
 * @date  2017年10月20日 下午3:03:17
 */
public class DepServiceFeeSelectVO extends PageInfoObject {

	private static final long serialVersionUID = 7942959153469213526L;

	private String productName;	//产品名称
	
	private Integer productTerm;	//产品期限
	
	private Date buyBeginTime;	//购买开始日期
	
	private Date buyEndTime;	//购买结束日期
	
	private Integer accountStatus;   //账户状态
	
	private List<Object> agentIds;//渠道编号
	
	private String nonAgentId;//非渠道编号
	
	private Integer agentId;

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public Integer getProductTerm() {
		return productTerm;
	}

	public void setProductTerm(Integer productTerm) {
		this.productTerm = productTerm;
	}

	public Date getBuyBeginTime() {
		return buyBeginTime;
	}

	public void setBuyBeginTime(Date buyBeginTime) {
		this.buyBeginTime = buyBeginTime;
	}

	public Date getBuyEndTime() {
		return buyEndTime;
	}

	public void setBuyEndTime(Date buyEndTime) {
		this.buyEndTime = buyEndTime;
	}

	public Integer getAccountStatus() {
		return accountStatus;
	}

	public void setAccountStatus(Integer accountStatus) {
		this.accountStatus = accountStatus;
	}

	public List<Object> getAgentIds() {
		return agentIds;
	}

	public void setAgentIds(List<Object> agentIds) {
		this.agentIds = agentIds;
	}

	public String getNonAgentId() {
		return nonAgentId;
	}

	public void setNonAgentId(String nonAgentId) {
		this.nonAgentId = nonAgentId;
	}

	public Integer getAgentId() {
		return agentId;
	}

	public void setAgentId(Integer agentId) {
		this.agentId = agentId;
	}

}
