package com.pinting.business.model.vo;

import java.util.Date;

/**
 * 管理台宝付代付归集户配置
 * @author SHENGUOPING
 * @date  2018年7月11日 下午17:22:59
 */
public class SysPayLimitVO {

	private Integer id;
	
	private Integer rowno;
	
	// 操作人
	private String operateName;
	
	private Date updateTime;
	
	private Date modifyTime;
	// 业务类型
	private String payBusinessType;
	// 时间类型
	private String timeType;
	// 开始时间
	private String timeStart;
	// 结束时间
	private String timeEnd;
	// 限制类型
	private String limitType;
	// 限制条件
	private String limitEquleType;
	// 限制值
	private String limitVaule;
	// 是否删除：NO否
	private String isDelete;
	
	private Integer mUserId;
	
	// 更新操作ID
	private String ruleId;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getRowno() {
		return rowno;
	}
	public void setRowno(Integer rowno) {
		this.rowno = rowno;
	}
	public String getOperateName() {
		return operateName;
	}
	public void setOperateName(String operateName) {
		this.operateName = operateName;
	}
	public Date getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	public Date getModifyTime() {
		return modifyTime;
	}
	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}
	public String getPayBusinessType() {
		return payBusinessType;
	}
	public void setPayBusinessType(String payBusinessType) {
		this.payBusinessType = payBusinessType;
	}
	public String getTimeType() {
		return timeType;
	}
	public void setTimeType(String timeType) {
		this.timeType = timeType;
	}
	
	public String getTimeStart() {
		return timeStart;
	}
	public void setTimeStart(String timeStart) {
		this.timeStart = timeStart;
	}
	public String getTimeEnd() {
		return timeEnd;
	}
	public void setTimeEnd(String timeEnd) {
		this.timeEnd = timeEnd;
	}
	public String getLimitVaule() {
		return limitVaule;
	}
	public void setLimitVaule(String limitVaule) {
		this.limitVaule = limitVaule;
	}
	public String getLimitType() {
		return limitType;
	}
	public void setLimitType(String limitType) {
		this.limitType = limitType;
	}
	public String getLimitEquleType() {
		return limitEquleType;
	}
	public void setLimitEquleType(String limitEquleType) {
		this.limitEquleType = limitEquleType;
	}
	public String getIsDelete() {
		return isDelete;
	}
	public void setIsDelete(String isDelete) {
		this.isDelete = isDelete;
	}
	public Integer getmUserId() {
		return mUserId;
	}
	public void setmUserId(Integer mUserId) {
		this.mUserId = mUserId;
	}
	public String getRuleId() {
		return ruleId;
	}
	public void setRuleId(String ruleId) {
		this.ruleId = ruleId;
	}
	
}
