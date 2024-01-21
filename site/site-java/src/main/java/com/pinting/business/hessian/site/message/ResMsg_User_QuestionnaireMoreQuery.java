package com.pinting.business.hessian.site.message;

import com.pinting.core.hessian.msg.ResMsg;

public class ResMsg_User_QuestionnaireMoreQuery extends ResMsg {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8372054465881533962L;
	
	/** 是否进行风险测评  */
	private Integer hasQuestionnaire;
	/** 风险测评类型  */
	private String assessType;
	/** 风险评测过期时间 */
	private String expireTime;
	
	public String getAssessType() {
		return assessType;
	}
	public void setAssessType(String assessType) {
		this.assessType = assessType;
	}
	public String getExpireTime() {
		return expireTime;
	}
	public void setExpireTime(String expireTime) {
		this.expireTime = expireTime;
	}
	public Integer getHasQuestionnaire() {
		return hasQuestionnaire;
	}
	public void setHasQuestionnaire(Integer hasQuestionnaire) {
		this.hasQuestionnaire = hasQuestionnaire;
	}
	
}
