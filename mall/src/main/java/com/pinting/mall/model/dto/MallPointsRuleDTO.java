package com.pinting.mall.model.dto;

import java.math.BigInteger;
import java.util.Date;

/**
 * 新增积分设置数据传输对象
 * @author SHENGUOPING
 * @date  2018年5月11日 下午2:54:54
 */
public class MallPointsRuleDTO {
	
	private Integer ruleId;
	//获取场景
	private String getScene;
	//获取次数
	private String getTimes;
	//获取时间类型
	private String getTimeType;
	//积分
	private BigInteger points;
	//状态
	private String status;
	//触发开始时间
	private String triggerTimeStart;
	//触发结束时间
	private String triggerTimeEnd;
	//触发开始时间-日期
	private Date triggerStartTime;
	//触发结束时间-日期
	private Date triggerEndTime;
	
	//产品收益兑换比例
	private String ruleValueExchangeRate;
	//累计加入产品
	private String ruleValueTotalInvest;
	//累计加入产品，投资满额起始额
	private String ruleValueInvestAmountBegin;
	//累计加入产品，投资满额截至额
	private String ruleValueInvestAmountEnd;
	
	//签到初始积分
	private String ruleValueSignInitPoint;
	//签到递增积分
	private String ruleValueSignIncrementPoint;
	//签到额外奖励积分
	private String ruleValueSignAwardPoint;
	//签到初始积分(App)
	private String ruleValueSignInitPointApp;
	//签到递增积分(App)
	private String ruleValueSignIncrementPointApp;
	//签到额外奖励积分(App)
	private String ruleValueSignAwardPointApp;
	//操作人
	private Integer opUserId;
	
	
	public Integer getRuleId() {
		return ruleId;
	}
	public void setRuleId(Integer ruleId) {
		this.ruleId = ruleId;
	}
	public String getGetScene() {
		return getScene;
	}
	public void setGetScene(String getScene) {
		this.getScene = getScene;
	}
	public String getGetTimes() {
		return getTimes;
	}
	public void setGetTimes(String getTimes) {
		this.getTimes = getTimes;
	}
	public String getGetTimeType() {
		return getTimeType;
	}
	public void setGetTimeType(String getTimeType) {
		this.getTimeType = getTimeType;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getTriggerTimeStart() {
		return triggerTimeStart;
	}
	public void setTriggerTimeStart(String triggerTimeStart) {
		this.triggerTimeStart = triggerTimeStart;
	}
	public String getTriggerTimeEnd() {
		return triggerTimeEnd;
	}
	public void setTriggerTimeEnd(String triggerTimeEnd) {
		this.triggerTimeEnd = triggerTimeEnd;
	}
	public Date getTriggerStartTime() {
		return triggerStartTime;
	}
	public void setTriggerStartTime(Date triggerStartTime) {
		this.triggerStartTime = triggerStartTime;
	}
	public Date getTriggerEndTime() {
		return triggerEndTime;
	}
	public void setTriggerEndTime(Date triggerEndTime) {
		this.triggerEndTime = triggerEndTime;
	}
	public String getRuleValueExchangeRate() {
		return ruleValueExchangeRate;
	}
	public void setRuleValueExchangeRate(String ruleValueExchangeRate) {
		this.ruleValueExchangeRate = ruleValueExchangeRate;
	}
	public String getRuleValueSignInitPoint() {
		return ruleValueSignInitPoint;
	}
	public void setRuleValueSignInitPoint(String ruleValueSignInitPoint) {
		this.ruleValueSignInitPoint = ruleValueSignInitPoint;
	}
	public String getRuleValueSignIncrementPoint() {
		return ruleValueSignIncrementPoint;
	}
	public void setRuleValueSignIncrementPoint(String ruleValueSignIncrementPoint) {
		this.ruleValueSignIncrementPoint = ruleValueSignIncrementPoint;
	}
	public String getRuleValueSignAwardPoint() {
		return ruleValueSignAwardPoint;
	}
	public void setRuleValueSignAwardPoint(String ruleValueSignAwardPoint) {
		this.ruleValueSignAwardPoint = ruleValueSignAwardPoint;
	}
	public Integer getOpUserId() {
		return opUserId;
	}
	public void setOpUserId(Integer opUserId) {
		this.opUserId = opUserId;
	}
	public String getRuleValueTotalInvest() {
		return ruleValueTotalInvest;
	}
	public void setRuleValueTotalInvest(String ruleValueTotalInvest) {
		this.ruleValueTotalInvest = ruleValueTotalInvest;
	}
	public String getRuleValueInvestAmountBegin() {
		return ruleValueInvestAmountBegin;
	}
	public void setRuleValueInvestAmountBegin(String ruleValueInvestAmountBegin) {
		this.ruleValueInvestAmountBegin = ruleValueInvestAmountBegin;
	}
	public String getRuleValueInvestAmountEnd() {
		return ruleValueInvestAmountEnd;
	}
	public void setRuleValueInvestAmountEnd(String ruleValueInvestAmountEnd) {
		this.ruleValueInvestAmountEnd = ruleValueInvestAmountEnd;
	}
	public BigInteger getPoints() {
		return points;
	}
	public void setPoints(BigInteger points) {
		this.points = points;
	}

	public String getRuleValueSignInitPointApp() {
		return ruleValueSignInitPointApp;
	}

	public void setRuleValueSignInitPointApp(String ruleValueSignInitPointApp) {
		this.ruleValueSignInitPointApp = ruleValueSignInitPointApp;
	}

	public String getRuleValueSignIncrementPointApp() {
		return ruleValueSignIncrementPointApp;
	}

	public void setRuleValueSignIncrementPointApp(String ruleValueSignIncrementPointApp) {
		this.ruleValueSignIncrementPointApp = ruleValueSignIncrementPointApp;
	}

	public String getRuleValueSignAwardPointApp() {
		return ruleValueSignAwardPointApp;
	}

	public void setRuleValueSignAwardPointApp(String ruleValueSignAwardPointApp) {
		this.ruleValueSignAwardPointApp = ruleValueSignAwardPointApp;
	}
}
