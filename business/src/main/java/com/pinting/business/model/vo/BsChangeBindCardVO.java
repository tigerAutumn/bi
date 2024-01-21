package com.pinting.business.model.vo;

import java.util.Date;

/**
 * 管理台更换安全卡
 * @author SHENGP
 * @date  2018年5月29日 上午9:49:53
 */
public class BsChangeBindCardVO extends PageInfoObject {

	private static final long serialVersionUID = 737634444138084876L;
	
	// 用户人脸核身验证表id
	private Integer id;
	// 姓名
	private String userName;
	// 手机号
	private String mobile;
	// 身份证号
	private String idCard;
	// 操作客服
	private String checkUser;
	
	// 审核状态
	private String checkStatus;
	// 检测结果
	private String verifyResult;
	// 申请开始时间
	private String applyStartTime;
	// 申请结束时间
	private String applyEndTime;
	// 审核开始时间
	private String checkStartTime;
	// 审核结束时间
	private String checkEndTime;
	// 申请时间
	private Date applyTime;
	// 审核时间
	private Date checkTime;
	
	private String businessType;
	
	// 分数
	private Integer score;
	// 反馈信息
	private String feedback;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
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
	public String getCheckUser() {
		return checkUser;
	}
	public void setCheckUser(String checkUser) {
		this.checkUser = checkUser;
	}
	public String getCheckStatus() {
		return checkStatus;
	}
	public void setCheckStatus(String checkStatus) {
		this.checkStatus = checkStatus;
	}
	public String getVerifyResult() {
		return verifyResult;
	}
	public void setVerifyResult(String verifyResult) {
		this.verifyResult = verifyResult;
	}
	public String getApplyStartTime() {
		return applyStartTime;
	}
	public void setApplyStartTime(String applyStartTime) {
		this.applyStartTime = applyStartTime;
	}
	public String getApplyEndTime() {
		return applyEndTime;
	}
	public void setApplyEndTime(String applyEndTime) {
		this.applyEndTime = applyEndTime;
	}
	public String getCheckStartTime() {
		return checkStartTime;
	}
	public void setCheckStartTime(String checkStartTime) {
		this.checkStartTime = checkStartTime;
	}
	public String getCheckEndTime() {
		return checkEndTime;
	}
	public void setCheckEndTime(String checkEndTime) {
		this.checkEndTime = checkEndTime;
	}
	public Date getApplyTime() {
		return applyTime;
	}
	public void setApplyTime(Date applyTime) {
		this.applyTime = applyTime;
	}
	public Date getCheckTime() {
		return checkTime;
	}
	public void setCheckTime(Date checkTime) {
		this.checkTime = checkTime;
	}
	public String getBusinessType() {
		return businessType;
	}
	public void setBusinessType(String businessType) {
		this.businessType = businessType;
	}
	public Integer getScore() {
		return score;
	}
	public void setScore(Integer score) {
		this.score = score;
	}
	public String getFeedback() {
		return feedback;
	}
	public void setFeedback(String feedback) {
		this.feedback = feedback;
	}
	
}
