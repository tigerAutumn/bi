package com.pinting.business.hessian.manage.message;

import java.util.Date;

import com.pinting.core.hessian.msg.ReqMsg;

/**
 * 杭商信息服务费查询
 * @author SHENGUOPING
 * @date  2017年10月19日 下午5:06:34
 */
public class ReqMsg_MAccount_QueryHsDepServiceFeeList extends ReqMsg {

	private static final long serialVersionUID = -6918032844757250029L;
	
	/**
	 * 产品名称
	 */
	private String productName;
	
	/**
	 * 产品期限
	 */
	private Integer productTerm;
	
	private Date buyBeginTime; // 购买起始时间

	private Date buyEndTime; // 购买结束时间
	
	/**
	 * 账户状态
	 */
	private Integer accountStatus;
	
	private Integer agentId;       //渠道id
	
	private String agentIds;
	
	private String nonAgentId;
	
	private int pageNum;

    /** 每页显示的记录数(默认为20条,可以通过set改变其数量)*/
    private int numPerPage = 20;

    public int getPageNum() {
        return pageNum;
    }

    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
    }

    public int getNumPerPage() {
        return numPerPage;
    }

    public void setNumPerPage(int numPerPage) {
        this.numPerPage = numPerPage;
    }

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

	public Integer getAgentId() {
		return agentId;
	}

	public void setAgentId(Integer agentId) {
		this.agentId = agentId;
	}

	public String getAgentIds() {
		return agentIds;
	}

	public void setAgentIds(String agentIds) {
		this.agentIds = agentIds;
	}

	public String getNonAgentId() {
		return nonAgentId;
	}

	public void setNonAgentId(String nonAgentId) {
		this.nonAgentId = nonAgentId;
	}
    
}
