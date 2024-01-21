package com.pinting.mall.model.dto;

import com.pinting.core.hessian.msg.ResMsg;

/**
 * 积分发放
 * Created by Gemma on 20180509
 */
public class MallIncomeResultInfo extends ResMsg {
	/**
	 * 
	 */
	private static final long serialVersionUID = 851241230280482998L;
	private int ruleId;		//规则编号
    private int acctId;		//账户编号 
    private Long points;	//积分 
    private int incomeId;	//积分行为
    private String transType; //交易类型 注册/签到/开户/风测/投资
    
	public int getRuleId() {
		return ruleId;
	}
	public void setRuleId(int ruleId) {
		this.ruleId = ruleId;
	}
	public int getAcctId() {
		return acctId;
	}
	public void setAcctId(int acctId) {
		this.acctId = acctId;
	}
	public Long getPoints() {
		return points;
	}
	public void setPoints(Long points) {
		this.points = points;
	}
	public int getIncomeId() {
		return incomeId;
	}
	public void setIncomeId(int incomeId) {
		this.incomeId = incomeId;
	}
	public String getTransType() {
		return transType;
	}
	public void setTransType(String transType) {
		this.transType = transType;
	}
}